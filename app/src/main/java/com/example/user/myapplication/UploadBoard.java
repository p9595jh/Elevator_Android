package com.example.user.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Iterator;

public class UploadBoard extends AsyncTask<JSONObject, String, String> {

    final String upLoadServerUri = MainActivity.SERVER_ADDRESS + "/handleWrite/insert";
    Activity activity;
    String boardtype;

    public UploadBoard(Activity activity) {
        this.activity = activity;
    }

    @Override
    protected String doInBackground(JSONObject... objects) {

        HttpURLConnection conn = null;
        DataOutputStream dos = null;
        String lineEnd = "\r\n";
        String twoHyphens = "--";
        String boundary = "*****";
        int bytesRead, bytesAvailable, bufferSize;
        byte[] buffer;
        int maxBufferSize = 1 * 1024 * 1024;

        JSONObject jsonObject = objects[0];
        Iterator<String> itr = jsonObject.keys();

        try {
            URL url = new URL(upLoadServerUri);
            conn = (HttpURLConnection) url.openConnection();
            conn.setDoInput(true); // Allow Inputs
            conn.setDoOutput(true); // Allow Outputs
            conn.setUseCaches(false); // Don't use a Cached Copy
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Connection", "Keep-Alive");
            conn.setRequestProperty("ENCTYPE", "multipart/form-data");
            conn.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);

            dos = new DataOutputStream(conn.getOutputStream());

            while (itr.hasNext()) {
                dos.writeBytes(twoHyphens + boundary + lineEnd);

                String key = itr.next();
                String value = (String) jsonObject.get(key);

                if ( (key.equals("image") || key.equals("music")) && new File(value).isFile() ) {
                    dos.writeBytes("Content-Disposition: form-data; name=\"" + key + "\";filename=\"" + key + "\"" + lineEnd);
                    dos.writeBytes(lineEnd);

                    File file = new File(value);
                    FileInputStream fileInputStream = new FileInputStream(file);
                    bytesAvailable = fileInputStream.available();

                    bufferSize = Math.min(bytesAvailable, maxBufferSize);
                    buffer = new byte[bufferSize];

                    // read file and write it into form...
                    bytesRead = fileInputStream.read(buffer, 0, bufferSize);

                    while (bytesRead > 0) {
                        dos.write(buffer, 0, bufferSize);
                        bytesAvailable = fileInputStream.available();
                        bufferSize = Math.min(bytesAvailable, maxBufferSize);
                        bytesRead = fileInputStream.read(buffer, 0, bufferSize);
                    }
                    fileInputStream.close();
                }
                else {
                    if ( key.equals("boardtype") ) boardtype = value;
                    dos.writeBytes("Content-Disposition: form-data; name=\"" + key + "\"" + lineEnd);
                    dos.writeBytes(lineEnd);
                    dos.writeBytes(value);
                }
                dos.writeBytes(lineEnd);
            }
            // send multipart form data necesssary after file data...
            dos.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);

            // Responses from the server (code and message)
            int serverResponseCode = conn.getResponseCode();
            String serverResponseMessage = conn.getResponseMessage();

            StringBuilder response;
            if (serverResponseCode == 200) {
                BufferedReader input = new BufferedReader(new InputStreamReader(conn.getInputStream()), 8192);
                response = new StringBuilder();
                String strLine = null;
                while ((strLine = input.readLine()) != null)
                    response.append(strLine);

                input.close();

                //close the streams //
                dos.flush();
                dos.close();
                return response.toString();
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(String jsonString) {
        if ( jsonString == null ) {
            Toast.makeText(activity.getApplicationContext(), "글작성 오류: jsonString is null", Toast.LENGTH_LONG).show();
            return;
        }
        try {
            JSONObject jsonObject = new JSONObject(jsonString);
            String msg = jsonObject.getString("msg");
            if ( msg.equals("success") ) {
                Toast.makeText(activity.getApplicationContext(), "작성 완료", Toast.LENGTH_LONG).show();
                Intent intent = null;
                if ( boardtype.equals("free") ) {
                    intent = new Intent(activity.getApplicationContext(), FreeActivity.class);
                }
                else if ( boardtype.equals("music") ) {
                    intent = new Intent(activity.getApplicationContext(), MusicActivity.class);
                }
                else {
                    intent = new Intent(activity.getApplicationContext(), SubBoardActivity.class);
                    intent.putExtra("boardType", boardtype);
                }
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                activity.startActivity(intent);
            }
            else {
                Toast.makeText(activity.getApplicationContext(), "글작성 오류: " + msg, Toast.LENGTH_LONG).show();
                return;
            }
        }
        catch(JSONException e) {
            Toast.makeText(activity.getApplicationContext(), "글작성 오류: jsonException", Toast.LENGTH_LONG).show();
            return;
        }
    }

}

package com.example.user.myapplication;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class CommentAdapter extends BaseAdapter {
    ArrayList<Comment> list;
    Context context;
    int resource;
    Bitmap bitmap;

    public CommentAdapter(ArrayList<Comment> list, Context context, int resource) {
        this.list = list;
        this.context = context;
        this.resource = resource;
    }

    public int getCount() {
        return list.size();
    }

    public Object getItem(int i) {
        return list.get(i);
    }

    public long getItemId(int i) {
        return i;
    }

    public  View getView(int i, View view, ViewGroup viewGroup) {
        if ( view == null ) {
            LayoutInflater intflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = intflater.inflate(resource, viewGroup, false);
        }

        ImageView image = (ImageView) view.findViewById(R.id.suggest_view_image);
        TextView id = (TextView) view.findViewById(R.id.suggest_view_id);
        TextView date = (TextView) view.findViewById(R.id.suggest_view_date);
        TextView text = (TextView) view.findViewById(R.id.suggest_view_text);

        final int $i = i;
        Thread th = new Thread() {
            public void run() {
                try {
                    URL url = new URL(MainActivity.SERVER_ADDRESS + "/images/profileimages/" + list.get($i).getId());
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setDoInput(true);
                    conn.connect();

                    InputStream is = conn.getInputStream();
                    bitmap = BitmapFactory.decodeStream(is);
                }
                catch(MalformedURLException e) {
                    e.printStackTrace();
                }
                catch(IOException e) {
                    e.printStackTrace();
                }
            }
        };
        th.start();
        try {
            th.join();
            image.setImageBitmap(bitmap);
        }
        catch(InterruptedException e) {
            e.printStackTrace();
        }

        id.setText(list.get(i).getNickname());
        date.setText(list.get(i).getDate());
        text.setText(list.get(i).getComment());

        return view;
    }
}

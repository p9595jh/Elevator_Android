package com.example.user.myapplication;

import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ContentActivity extends AppCompatActivity {
    MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contentpage);

        Intent intent = getIntent();
        final int num = intent.getIntExtra("num", 0);
        final String type = intent.getStringExtra("type");
        String title = intent.getStringExtra("title");
        String id = intent.getStringExtra("id");
        String date = intent.getStringExtra("date");
        String nickname = intent.getStringExtra("nickname");
        String content = intent.getStringExtra("content");
        String audio = intent.getStringExtra("audio");
        int hit = intent.getIntExtra("hit", 0);
        int recommend = intent.getIntExtra("recommend", 0);
        ArrayList<Comment> comment = (ArrayList<Comment>) intent.getSerializableExtra("comment");
        int grade = intent.getIntExtra("grade", 0);
        ArrayList<String> gradeby = (ArrayList<String>) intent.getSerializableExtra("gradeby");

        TextView titleView = (TextView) findViewById(R.id.content_title);
        TextView nicknameView = (TextView) findViewById(R.id.content_nickname);
        TextView gradeView = (TextView) findViewById(R.id.content_grade);
        TextView dateView = (TextView) findViewById(R.id.content_date);
        TextView hitView = (TextView) findViewById(R.id.content_hit);
        TextView contentView = (TextView) findViewById(R.id.content_content);
        Button recommendButton = (Button) findViewById(R.id.content_recommend);

        final SeekBar seekBar = (SeekBar) findViewById(R.id.music_seekbar);
        Button musicPlay = findViewById(R.id.music_play);
        Button musicPause = findViewById(R.id.music_pause);
        Button musicStop = findViewById(R.id.music_stop);
        final TextView musicTime = findViewById(R.id.music_time);

        titleView.setText(title);
        nicknameView.setText(nickname);
        gradeView.setText("평점 " + grade);
        dateView.setText(date);
        hitView.setText("조회 " + hit);
        contentView.setText(content);
        recommendButton.setText("추천 " + recommend);

        RadioGroup gradeGroup = (RadioGroup) findViewById(R.id.radiogroup);
        RadioButton gradeRadioButton[] = new RadioButton[5];
        int[] rbtnValue = { R.id.grade_1, R.id.grade_2, R.id.grade_3, R.id.grade_4, R.id.grade_5 };
        for (int i=0; i<gradeRadioButton.length; i++) {
            gradeRadioButton[i] = (RadioButton) findViewById(rbtnValue[i]);
        }
        Button gradeButton = (Button) findViewById(R.id.content_grade_button);
        Button boardRequest = (Button) findViewById(R.id.board_request);
        final EditText writeComment = (EditText) findViewById(R.id.content_writecomment_text);
        Button writeCommentButton = (Button) findViewById(R.id.content_writecomment_button);

        final UserLoginData userLoginData = UserLoginData.getInstance();
        writeCommentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ( writeComment.getText().toString().isEmpty() ) {
                    Toast.makeText(getApplicationContext(), "비어있습니다", Toast.LENGTH_LONG).show();
                    return;
                }
                String comment = writeComment.getText().toString();
                JSONObject jsonObject = new JSONObject();
                try {
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    String date = sdf.format(new Date(System.currentTimeMillis()));

                    jsonObject.put("board", type);
                    jsonObject.put("num", num);
                    jsonObject.put("id", userLoginData.getId());
                    jsonObject.put("nickname", userLoginData.getNickname());
                    jsonObject.put("writedata", date);
                    jsonObject.put("comment", comment);
                }
                catch(JSONException e) {
                    e.printStackTrace();
                }
                new InsertCommentData(ContentActivity.this).execute(jsonObject);
            }
        });
        recommendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put("id", userLoginData.getId());
                    jsonObject.put("boardtype", type);
                    jsonObject.put("num", num);
                }
                catch(JSONException e) {
                    e.printStackTrace();
                }
                new InsertRecommendData(ContentActivity.this).execute(jsonObject);
            }
        });

        CommentAdapter adapter = new CommentAdapter(comment, ContentActivity.this, R.layout.suggestlistview);
        ListView listView = findViewById(R.id.content_commentlist);
        listView.setAdapter(adapter);
        listView.setDividerHeight(10);

        if (adapter == null) {
            // pre-condition
            return;
        }

        int totalHeight = 0;
        int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(), View.MeasureSpec.AT_MOST);

        for (int i = 0; i < adapter.getCount(); i++) {
            View listItem = adapter.getView(i, null, listView);
            listItem.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (adapter.getCount() - 1));
        listView.setLayoutParams(params);
        listView.requestLayout();

        if ( !UserLoginData.didLogin() ) {
            LinearLayout layout = findViewById(R.id.comment_layout);
            layout.setVisibility(View.GONE);
        }
        if ( type.equals("music") ) {
            recommendButton.setVisibility(View.GONE);

            mediaPlayer = new MediaPlayer();
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            try {
                mediaPlayer.setDataSource(MainActivity.SERVER_ADDRESS + "/" + audio);
                mediaPlayer.prepare();
            } catch(IOException e) {
                e.printStackTrace();
                Log.d("aboutMusic", e.getMessage());
            } catch(Exception e) {
                e.printStackTrace();
                Log.d("aboutMusic", e.getMessage());
            }

            seekBar.setVisibility(ProgressBar.VISIBLE);
            seekBar.setMax(mediaPlayer.getDuration());
            seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    if ( fromUser ) mediaPlayer.seekTo(progress);
                    int m = progress / 60000;
                    int s = (progress % 60000) / 1000;
                    String strTime = String.format("%02d:%02d", m, s);
                    musicTime.setText(strTime);
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {}

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {}
            });

            final Thread mThread = new Thread() {
                public void run() {
                    while (mediaPlayer.isPlaying() && !Thread.currentThread().isInterrupted()) {
                        try {
                            Thread.sleep(1000);
                        } catch(InterruptedException e) {}
                        seekBar.setProgress(mediaPlayer.getCurrentPosition());
                    }
                }
            };

            musicPlay.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    if ( mediaPlayer.isPlaying() ) {
                        Toast.makeText(getApplicationContext(), "이미 재생 중입니다", Toast.LENGTH_LONG).show();
                        return;
                    }
                    mediaPlayer.start();
                    mThread.start();
                }
            });
            musicPause.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    mediaPlayer.pause();
                }
            });
            musicStop.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    mediaPlayer.stop();
                    try {
                        mediaPlayer.prepare();
                    } catch(IOException e) {
                        e.printStackTrace();
                    }
                    mediaPlayer.seekTo(0);
                    seekBar.setProgress(0);
                    mThread.interrupt();
                }
            });

            if ( userLoginData.didLogin() ) {
                if ( userLoginData.getId().equals(id) && grade >= 3 && gradeby.size() >= 1 && userLoginData.getBoardRequest() == 0 ) {
                    boardRequest.setVisibility(View.GONE);
                    // 앱에서 게시판 생성요청 버튼 임시 삭제
                }
                else {
                    boardRequest.setVisibility(View.GONE);
                }
            }
            else {
                boardRequest.setVisibility(View.GONE);
            }
        }
        else {
            gradeView.setVisibility(View.GONE);
            gradeGroup.setVisibility(View.GONE);
            gradeButton.setVisibility(View.GONE);
            boardRequest.setVisibility(View.GONE);
            seekBar.setVisibility(View.GONE);
            musicPlay.setVisibility(View.GONE);
            musicPause.setVisibility(View.GONE);
            musicStop.setVisibility(View.GONE);
            musicTime.setVisibility(View.GONE);
        }

        AboutSideBar.init(this);

    }
}

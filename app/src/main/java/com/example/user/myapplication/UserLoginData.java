package com.example.user.myapplication;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class UserLoginData {
    private String id;
    private String nickname;
    private String joindate;
    private String genre;
    private String introduction;
    private boolean stop;
    private int boardRequest;
    private String email;
    private String pw;
    private ArrayList<String> subscribes;
    private String live;

    private static final UserLoginData instance = new UserLoginData();

    private UserLoginData() {
        id = null;
        nickname = null;
        joindate = null;
        genre = null;
        introduction = null;
    }

    public static UserLoginData getInstance() {
        return instance;
    }

    public static boolean didLogin() {
        return instance.getId() != null;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getJoindate() {
        return joindate;
    }

    public void setJoindate(String joindate) {
        this.joindate = joindate;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public boolean isStop() {
        return stop;
    }

    public void setStop(boolean stop) {
        this.stop = stop;
    }

    public int getBoardRequest() {
        return boardRequest;
    }

    public void setBoardRequest(int boardRequest) {
        this.boardRequest = boardRequest;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPw() {
        return pw;
    }

    public void setPw(String pw) {
        this.pw = pw;
    }

    public ArrayList<String> getSubscribes() {
        return subscribes;
    }

    public void setSubscribes(ArrayList<String> subscribes) {
        this.subscribes = subscribes;
    }

    public String getLive() {
        return live;
    }

    public void setLive(String live) {
        this.live = live;
    }

    public SubscribesAdapter getSubscribesAdapter(Context context, int resource) {
        return new SubscribesAdapter(subscribes, context, resource);
    }

    public static class SubscribesAdapter extends BaseAdapter {
        private ArrayList<String> list;
        private Context context;
        private int resource;

        public SubscribesAdapter(ArrayList<String> list, Context context, int resource) {
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

        public View getView(int i, View view, ViewGroup viewGroup) {
            if ( view == null ) {
                LayoutInflater intflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                view = intflater.inflate(resource, viewGroup, false);
            }

            TextView title = (TextView) view.findViewById(R.id.subscribe_subcontent_listview);
            title.setText(list.get(i));

            return view;
        }
    }
}

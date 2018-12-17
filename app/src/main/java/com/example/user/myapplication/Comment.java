package com.example.user.myapplication;

import java.io.Serializable;

public class Comment implements Serializable {
    private int num;
    private String id;
    private String nickname;
    private String date;
    private String comment;
    private int img;

    public Comment(int num, String id, String nickname, String date, String comment, int img) {
        this.num = num;
        this.id = id;
        this.nickname = nickname;
        this.date = date;
        this.comment = comment;
        this.img = img;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }
}

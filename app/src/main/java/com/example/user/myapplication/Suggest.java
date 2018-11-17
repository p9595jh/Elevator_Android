package com.example.user.myapplication;

public class Suggest {
    private int img;
    private String id;
    private String nickname;
    private String comment;
    private String writedata;
    private int num;

    public Suggest(int img, String id, String nickname, String comment, String writedata, int num) {
        this.img = img;
        this.id = id;
        this.nickname = nickname;
        this.comment = comment;
        this.writedata = writedata;
        this.num = num;
    }

    public int getImag() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
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

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getWritedata() {
        return writedata;
    }

    public void setWritedata(String writedata) {
        this.writedata = writedata;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }
}

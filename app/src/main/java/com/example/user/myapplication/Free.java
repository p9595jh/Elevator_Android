package com.example.user.myapplication;

public class Free {
    private String title;
    private String id;
    private String date;
    private int num;
    private String nickname;
    private String content;
    private int hit;
    private int recommend;
    private CommentList comment;

    public Free(String title, String id, String date, int num, String nickname, String content, int hit, int recommend, CommentList comment) {
        this.title = title;
        this.id = id;
        this.date = date;
        this.num = num;
        this.nickname = nickname;
        this.content = content;
        this.hit = hit;
        this.recommend = recommend;
        this.comment = comment;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getHit() {
        return hit;
    }

    public void setHit(int hit) {
        this.hit = hit;
    }

    public int getRecommend() {
        return recommend;
    }

    public void setRecommend(int recommend) {
        this.recommend = recommend;
    }

    public CommentList getComment() {
        return comment;
    }

    public void setComment(CommentList comment) {
        this.comment = comment;
    }
}

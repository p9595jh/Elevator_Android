package com.example.user.myapplication;

import java.util.ArrayList;

public class Music {
    private String id;
    private String nickname;
    private String title;
    private String content;
    private String tag;
    private int grade;
    private boolean boardRequest;
    private String date;
    private int hit;
    private int num;
    private String audio;
    private CommentList comment;
    private GradeByList gradeby;

    public Music(String id, String nickname, String title, String content, String tag, int grade, boolean boardRequest, String date, int hit, int num, String audio, CommentList comment, GradeByList gradeby) {
        this.id = id;
        this.nickname = nickname;
        this.title = title;
        this.content = content;
        this.tag = tag;
        this.grade = grade;
        this.boardRequest = boardRequest;
        this.date = date;
        this.hit = hit;
        this.num = num;
        this.audio = audio;
        this.comment = comment;
        this.gradeby = gradeby;
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

    public boolean isBoardRequest() {
        return boardRequest;
    }

    public void setBoardRequest(boolean boardRequest) {
        this.boardRequest = boardRequest;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getHit() {
        return hit;
    }

    public void setHit(int hit) {
        this.hit = hit;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public String getAudio() {
        return audio;
    }

    public void setAudio(String audio) {
        this.audio = audio;
    }

    public CommentList getComment() {
        return comment;
    }

    public void setComment(CommentList comment) {
        this.comment = comment;
    }

    public GradeByList getGradeby() {
        return gradeby;
    }

    public void setGradeby(GradeByList gradeby) {
        this.gradeby = gradeby;
    }
}

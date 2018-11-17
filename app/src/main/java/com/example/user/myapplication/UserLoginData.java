package com.example.user.myapplication;

public class UserLoginData {
    private String id;
    private String nickname;
    private String joindate;
    private String genre;
    private String introduction;

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
}

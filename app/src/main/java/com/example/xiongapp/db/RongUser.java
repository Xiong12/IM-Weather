package com.example.xiongapp.db;

/**
 * Created by Administrator on 2017/4/7 0007.
 */

public class RongUser {
    private String userId;
    private String name;
    private String portraitUri;

    public RongUser(String userId, String name, String portraitUri) {
        this.userId = userId;
        this.name = name;
        this.portraitUri = portraitUri;
    }
    public RongUser(){
    }
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPortraitUri() {
        return portraitUri;
    }

    public void setPortraitUri(String portraitUri) {
        this.portraitUri = portraitUri;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

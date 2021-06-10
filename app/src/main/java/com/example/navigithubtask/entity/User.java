package com.example.navigithubtask.entity;

import com.google.gson.annotations.SerializedName;

public class User {
    @SerializedName("login")
    String name;
    @SerializedName("id")
    long id;
    @SerializedName("avatar_url")
    String photoUrl;

    public String getName() {
        return name;
    }

    public long getId() {
        return id;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }
}

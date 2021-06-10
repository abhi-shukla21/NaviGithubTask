package com.example.navigithubtask.entity;

import com.google.gson.annotations.SerializedName;

public class PullRequest {
    @SerializedName("url")
    String url;
    @SerializedName("id")
    long id;
    @SerializedName("title")
    String title;
    @SerializedName("user")
    User user;

}

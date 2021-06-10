package com.example.navigithubtask.entity;

import com.google.gson.annotations.SerializedName;

public class PullRequest {
    @SerializedName("url")
    String url;
    @SerializedName("id")
    long id;
    @SerializedName("title")
    String title;
    @SerializedName("created_at")
    String createdAt;
    @SerializedName("closed_at")
    String closedAt;
    @SerializedName("user")
    User user;

    public String getUrl() {
        return url;
    }

    public long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public User getUser() {
        return user;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public String getClosedAt() {
        return closedAt;
    }
}

package com.example.navigithubtask;

import com.example.navigithubtask.entity.PullRequest;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;

public interface GithubService {

    @GET("repos/{user}/{repo}/pulls")
    Call<List<PullRequest>> getPullRequests(@Path("user") String user,
                                            @Path("repo") String repo,
                                            @QueryMap Map<String, String> options);

}

package com.example.navigithubtask;

import com.example.navigithubtask.entity.PullRequest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GithubRepository {
    private GithubService githubService;

    public GithubRepository(GithubService githubService) {
        this.githubService = githubService;
    }

    public void getPullClosedRequests(String user, String repo, int perPage, int pageNum, Callback<List<PullRequest>> callback) {
        Map<String, String> queryParams = new HashMap<>();
        queryParams.put("state", "closed");
        queryParams.put("per_page", String.valueOf(perPage));
        queryParams.put("page", String.valueOf(pageNum));
        Call<List<PullRequest>> request = githubService.getPullRequests(user, repo, queryParams);
        request.enqueue(callback);

    }



}

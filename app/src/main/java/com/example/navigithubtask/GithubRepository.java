package com.example.navigithubtask;

import com.example.navigithubtask.entity.PullRequest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GithubRepository {
    private GithubService githubService;
    private Executor executor = Executors.newSingleThreadExecutor();

    public GithubRepository(GithubService githubService) {
        this.githubService = githubService;
    }

    public void getPullClosedRequests(String user, String repo, int perPage, int pageNum) {
        Map<String, String> queryParams = new HashMap<>();
        queryParams.put("state", "closed");
        queryParams.put("per_page", String.valueOf(perPage));
        queryParams.put("page", String.valueOf(pageNum));
        Call<List<PullRequest>> request = githubService.getPullRequests(user, repo, queryParams);
        request.enqueue(new Callback<List<PullRequest>>() {
            @Override
            public void onResponse(Call<List<PullRequest>> call, Response<List<PullRequest>> response) {
                // TODO post value to live data
            }

            @Override
            public void onFailure(Call<List<PullRequest>> call, Throwable t) {
                // TODO show error
            }
        });

    }



}

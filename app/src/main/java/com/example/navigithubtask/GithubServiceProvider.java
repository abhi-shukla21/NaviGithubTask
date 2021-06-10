package com.example.navigithubtask;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class GithubServiceProvider {
    private static GithubService sGithubService;

    public GithubService getGithubService() {
        if (sGithubService == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("https://api.github.com/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            sGithubService = retrofit.create(GithubService.class);
        }
        return sGithubService;
    }

}


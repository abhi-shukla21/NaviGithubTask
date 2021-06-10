package com.example.navigithubtask;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class PullRequestViewModelFactory extends ViewModelProvider.NewInstanceFactory {
    private String user;
    private String repo;
    public PullRequestViewModelFactory(String user, String repo){
        this.user = user;
        this.repo = repo;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T)new PullRequestsViewModel(user, repo);
    }
}

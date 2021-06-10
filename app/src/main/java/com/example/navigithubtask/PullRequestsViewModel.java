package com.example.navigithubtask;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.navigithubtask.entity.PullRequest;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PullRequestsViewModel extends ViewModel {
    private MutableLiveData<List<PullRequest>> prLiveData = new MutableLiveData<>();
    private MutableLiveData<Status> statusLiveData = new MutableLiveData<>(Status.IDLE);
    private GithubRepository githubRepository;
    private String user;
    private String repo;
    private int pageSize = 30;
    private int pageNum = 1;

    public PullRequestsViewModel(GithubRepository githubRepository, String user, String repo) {
        this.githubRepository = githubRepository;
        this.user = user;
        this.repo = repo;
    }

    public LiveData<List<PullRequest>> getPullRequestLiveData() {
        return prLiveData;
    }

    public LiveData<Status> getStatusLiveData(){
        return statusLiveData;
    }

    public void fetchNext() {
        if(statusLiveData.getValue() == Status.IDLE) {
            statusLiveData.postValue(Status.LOADING);
            githubRepository.getPullClosedRequests(user, repo, pageSize, pageNum, callback);
        }
    }

    private Callback<List<PullRequest>> callback = new Callback<List<PullRequest>>() {
        @Override
        public void onResponse(Call<List<PullRequest>> call, Response<List<PullRequest>> response) {
            if (response.isSuccessful()) {
                prLiveData.getValue().addAll(response.body());
                prLiveData.postValue(prLiveData.getValue());
                pageNum++;
            }
            statusLiveData.postValue(Status.IDLE);
        }

        @Override
        public void onFailure(Call<List<PullRequest>> call, Throwable t) {
            // TODO show error
            statusLiveData.postValue(Status.IDLE);
        }
    };
}

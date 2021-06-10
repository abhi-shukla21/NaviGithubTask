package com.example.navigithubtask;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.example.navigithubtask.entity.PullRequest;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();

    private final String USER = "abhi-shukla21"; // Change this to fetch PRs for a different user
    private final String REPO = "NaviGithubTask"; // Change this to fetch PRs for a different repo

    private PullRequestsViewModel pullRequestsViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        pullRequestsViewModel = new ViewModelProvider(this, new PullRequestViewModelFactory(USER, REPO)).get(PullRequestsViewModel.class);
        RecyclerView recyclerView = findViewById(R.id.rv_pulls);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        LiveData<List<PullRequest>> pullRequestLiveData = pullRequestsViewModel.getPullRequestLiveData();
        LiveData<Status> statusLiveData = pullRequestsViewModel.getStatusLiveData();
        PullRequestsAdapter adapter = new PullRequestsAdapter(pullRequestLiveData.getValue());
        recyclerView.setAdapter(adapter);
        statusLiveData.observe(this, new Observer<Status>() {
            @Override
            public void onChanged(Status status) {
                if (status == Status.LOADING) {
                    // TODO show progress bar
                } else if (status == Status.IDLE) {
                    // TODO hide progress bar
                }
            }
        });
        pullRequestLiveData.observe(this, new Observer<List<PullRequest>>() {
            @Override
            public void onChanged(List<PullRequest> pullRequests) {
                adapter.notifyDataSetChanged();
                Log.d(TAG, "New list size: " + adapter.getItemCount());
            }
        });

        if (pullRequestLiveData.getValue().isEmpty()) {
            pullRequestsViewModel.fetchNext();
        }

    }
}
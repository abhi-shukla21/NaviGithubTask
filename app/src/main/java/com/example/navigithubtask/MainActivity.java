package com.example.navigithubtask;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.navigithubtask.entity.PullRequest;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();

    private final String USER = "abhi-shukla21"; // Change this to fetch PRs for a different user
    private final String REPO = "NaviGithubTask"; // Change this to fetch PRs for a different repo

    private PullRequestsViewModel pullRequestsViewModel;
    private LiveData<List<PullRequest>> pullRequestLiveData;
    private LiveData<Status> statusLiveData;
    private LinearLayoutManager layoutManager;
    private RecyclerView recyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView repoTitleTextView = findViewById(R.id.tv_repo_title);
        repoTitleTextView.setText(getString(R.string.repo_title, USER, REPO));
        pullRequestsViewModel = new ViewModelProvider(this, new PullRequestViewModelFactory(USER, REPO)).get(PullRequestsViewModel.class);
        recyclerView = findViewById(R.id.rv_pulls);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                layoutManager.getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);
        pullRequestLiveData = pullRequestsViewModel.getPullRequestLiveData();
        statusLiveData = pullRequestsViewModel.getStatusLiveData();
        PullRequestsAdapter adapter = new PullRequestsAdapter(pullRequestLiveData.getValue());
        recyclerView.setAdapter(adapter);
        statusLiveData.observe(this, new Observer<Status>() {
            @Override
            public void onChanged(Status status) {
                if (status == Status.LOADING) {
                    adapter.showLoading();
                } else if (status == Status.IDLE || status == Status.EOF) {
                    adapter.hideLoading();
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
        initSrcollListener();
    }

    private void initSrcollListener() {
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull @NotNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (statusLiveData.getValue() == Status.IDLE) {
                    if (layoutManager != null && layoutManager.findLastCompletelyVisibleItemPosition()
                            == recyclerView.getAdapter().getItemCount() - 3) {
                        pullRequestsViewModel.fetchNext();
                    }
                }
            }
        });
    }
}
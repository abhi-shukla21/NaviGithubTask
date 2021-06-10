package com.example.navigithubtask;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.navigithubtask.entity.PullRequest;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class PullRequestsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private int VIEW_TYPE_PR = 0;
    private int VIEW_TYPE_LOADING = 1;
    private List<PullRequest> pullRequests;
    private boolean isLoading = false;
    private int loadingItemPos;
    private PullRequest loadingItem = new PullRequest();

    public PullRequestsAdapter(List<PullRequest> pullRequests) {
        this.pullRequests = pullRequests;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_LOADING) {
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_loading, parent, false);
            return new LoadingViewHolder(v);
        } else {
            View v = LayoutInflater.from(parent.getContext()).
                    inflate(R.layout.item_pull_request, parent, false);
            return new PullRequestViewHolder(v);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (pullRequests.get(position) != loadingItem) {
            ((PullRequestViewHolder) holder).bind(pullRequests.get(position));
        }
    }

    @Override
    public int getItemCount() {
        return pullRequests.size();
    }

    @Override
    public int getItemViewType(int position) {
        return pullRequests.get(position) == loadingItem ? VIEW_TYPE_LOADING : VIEW_TYPE_PR;
    }

    public void showLoading() {
        isLoading = true;
        pullRequests.add(loadingItem);
        loadingItemPos = pullRequests.size() - 1;
        notifyItemInserted(loadingItemPos);
    }

    public void hideLoading() {
        isLoading = false;
        pullRequests.remove(loadingItem);
        notifyItemRemoved(loadingItemPos);
    }

    static class PullRequestViewHolder extends RecyclerView.ViewHolder {
        private TextView title, created, closed, author;
        private ImageView authorAvatar;

        public PullRequestViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.tv_pr_title);
            created = itemView.findViewById(R.id.tv_pr_created);
            closed = itemView.findViewById(R.id.tv_pr_closed);
            author = itemView.findViewById(R.id.tv_author_name);
            authorAvatar = itemView.findViewById(R.id.iv_author_avatar);

        }

        public void bind(PullRequest pullRequest) {
            title.setText(pullRequest.getTitle());
            created.setText(pullRequest.getCreatedAt());
            closed.setText(pullRequest.getClosedAt());
            author.setText(pullRequest.getUser().getName());
            Glide.with(itemView.getContext()).load(pullRequest.getUser().getPhotoUrl()).centerCrop().into(authorAvatar);
        }
    }

    static class LoadingViewHolder extends RecyclerView.ViewHolder {

        public LoadingViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
        }
    }
}

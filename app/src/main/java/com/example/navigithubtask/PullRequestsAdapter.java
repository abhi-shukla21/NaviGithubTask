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

import java.util.List;

public class PullRequestsAdapter extends RecyclerView.Adapter<PullRequestsAdapter.PullRequestViewHolder> {

    private List<PullRequest> pullRequests;

    public PullRequestsAdapter(List<PullRequest> pullRequests) {
        this.pullRequests = pullRequests;
    }


    @NonNull
    @Override
    public PullRequestViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.item_pull_request, parent, false);
        return new PullRequestViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull PullRequestsAdapter.PullRequestViewHolder holder, int position) {
        holder.bind(pullRequests.get(position));
    }

    @Override
    public int getItemCount() {
        return pullRequests.size();
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
}

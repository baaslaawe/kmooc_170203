package com.nile.kmooc.view.view_holders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.joanzapata.iconify.widget.IconImageView;
import com.nile.kmooc.discussion.DiscussionComment;
import com.nile.kmooc.discussion.DiscussionThread;
import com.nile.kmooc.util.ResourceUtil;

public class DiscussionSocialLayoutViewHolder extends RecyclerView.ViewHolder {

    public final IconImageView threadVoteIconImageView;
    public final TextView threadVoteTextView;
    public final View voteViewContainer;
    public final IconImageView threadFollowIconImageView;
    public final TextView threadFollowTextView;
    public final View threadFollowContainer;

    public DiscussionSocialLayoutViewHolder(View itemView) {
        super(itemView);

        voteViewContainer = itemView.
                findViewById(com.nile.kmooc.R.id.discussion_responses_action_bar_vote_container);

        threadVoteTextView = (TextView) itemView.
                findViewById(com.nile.kmooc.R.id.discussion_responses_action_bar_vote_count_text_view);
        threadVoteIconImageView = (IconImageView) itemView.
                findViewById(com.nile.kmooc.R.id.discussion_responses_action_bar_vote_icon_view);

        threadFollowTextView = (TextView) itemView.
                findViewById(com.nile.kmooc.R.id.discussion_responses_action_bar_follow_text_view);

        threadFollowIconImageView = (IconImageView) itemView.
                findViewById(com.nile.kmooc.R.id.discussion_responses_action_bar_follow_icon_view);
        threadFollowContainer = itemView.
                findViewById(com.nile.kmooc.R.id.discussion_responses_action_bar_follow_container);
    }

    public void setDiscussionThread(final DiscussionThread discussionThread) {
        threadVoteTextView.setText(ResourceUtil.getFormattedStringForQuantity(
                threadVoteTextView.getResources(), com.nile.kmooc.R.plurals.discussion_responses_action_bar_vote_text, discussionThread.getVoteCount()));
        threadVoteIconImageView.setIconColorResource(discussionThread.isVoted() ?
                com.nile.kmooc.R.color.edx_brand_primary_base : com.nile.kmooc.R.color.edx_brand_gray_base);

        threadFollowContainer.setVisibility(View.VISIBLE);

        if (discussionThread.isFollowing()) {
            threadFollowTextView.setText(com.nile.kmooc.R.string.forum_unfollow);
            threadFollowIconImageView.setIconColorResource(com.nile.kmooc.R.color.edx_brand_primary_base);
        } else {
            threadFollowTextView.setText(com.nile.kmooc.R.string.forum_follow);
            threadFollowIconImageView.setIconColorResource(com.nile.kmooc.R.color.edx_brand_gray_base);
        }
    }

    public void setDiscussionResponse(final DiscussionComment discussionResponse) {
        threadVoteTextView.setText(ResourceUtil.getFormattedStringForQuantity(
                threadVoteTextView.getResources(), com.nile.kmooc.R.plurals.discussion_responses_action_bar_vote_text, discussionResponse.getVoteCount()));
        threadVoteIconImageView.setIconColorResource(discussionResponse.isVoted() ?
                com.nile.kmooc.R.color.edx_brand_primary_base : com.nile.kmooc.R.color.edx_brand_gray_base);
    }
}

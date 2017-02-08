package com.nile.kmooc.view.adapters;

import android.content.Context;
import android.support.v4.view.ViewCompat;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;

import com.google.inject.Inject;
import com.nile.kmooc.core.IEdxEnvironment;

import com.nile.kmooc.discussion.DiscussionTopicDepth;

public class DiscussionTopicsAdapter extends BaseListAdapter<DiscussionTopicDepth> {

    private final int childPadding;

    @Inject
    public DiscussionTopicsAdapter(Context context, IEdxEnvironment environment) {
        super(context, com.nile.kmooc.R.layout.row_discussion_topic, environment);
        childPadding = context.getResources().getDimensionPixelOffset(com.nile.kmooc.R.dimen.edx_margin);
    }

    @Override
    public void render(BaseViewHolder tag, DiscussionTopicDepth discussionTopic) {
        ViewHolder holder = (ViewHolder) tag;
        holder.discussionTopicNameTextView.setText(discussionTopic.getDiscussionTopic().getName());
        ViewCompat.setPaddingRelative(holder.discussionTopicNameTextView, childPadding * (1 + discussionTopic.getDepth()), childPadding, childPadding, childPadding);
    }

    @Override
    public BaseViewHolder getTag(View convertView) {
        return new ViewHolder(convertView);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
    }

    private static class ViewHolder extends BaseViewHolder {
        final TextView discussionTopicNameTextView;

        private ViewHolder(View view) {
            this.discussionTopicNameTextView = (TextView) view.findViewById(com.nile.kmooc.R.id.discussion_topic_name_text_view);
        }
    }

}

package com.nile.kmooc.view.adapters;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.joanzapata.iconify.widget.IconImageView;

public class DiscussionReportViewHolder {

    ViewGroup reportLayout;
    private IconImageView reportIconImageView;
    private TextView reportTextView;

    public DiscussionReportViewHolder(View itemView) {
        reportLayout = (ViewGroup) itemView.
                findViewById(com.nile.kmooc.R.id.discussion_responses_action_bar_report_container);
        reportIconImageView = (IconImageView) itemView.
                findViewById(com.nile.kmooc.R.id.discussion_responses_action_bar_report_icon_view);
        reportTextView = (TextView) itemView.
                findViewById(com.nile.kmooc.R.id.discussion_responses_action_bar_report_text_view);

    }

    public void setReported(boolean isReported) {
        int reportStringResId = isReported ? com.nile.kmooc.R.string.discussion_responses_reported_label :
                com.nile.kmooc.R.string.discussion_responses_report_label;
        reportTextView.setText(reportTextView.getResources().getString(reportStringResId));

        int iconColor = isReported ? com.nile.kmooc.R.color.edx_brand_primary_base : com.nile.kmooc.R.color.edx_brand_gray_base;
        reportIconImageView.setIconColorResource(iconColor);
    }
}

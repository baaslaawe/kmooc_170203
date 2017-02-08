package com.nile.kmooc.discussion;

import android.support.annotation.StringRes;

import com.nile.kmooc.interfaces.TextResourceProvider;

/**
 * Filter options for discussion posts.
 */
public enum DiscussionPostsFilter implements TextResourceProvider {
    ALL(com.nile.kmooc.R.string.discussion_posts_filter_all_posts, ""),
    UNREAD(com.nile.kmooc.R.string.discussion_posts_filter_unread_posts, "unread"),
    UNANSWERED(com.nile.kmooc.R.string.discussion_posts_filter_unanswered_posts, "unanswered");

    @StringRes
    private final int textRes;
    private final String queryParamValue;

    DiscussionPostsFilter(@StringRes int textRes, String queryParamValue) {
        this.textRes = textRes;
        this.queryParamValue = queryParamValue;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getTextResource() {
        return textRes;
    }

    /**
     * Get the value of the query parameter.
     *
     * @return The query parameter string
     */
    public String getQueryParamValue() {
        return queryParamValue;
    }
}

package com.nile.kmooc.view;

import android.support.annotation.NonNull;

import roboguice.inject.ContentView;

@ContentView(com.nile.kmooc.R.layout.activity_find_courses)
public class WebViewExploreSubjectsActivity extends WebViewFindCoursesActivity {
    @NonNull
    @Override
    protected String getInitialUrl() {
        return environment.getConfig().getCourseDiscoveryConfig().getWebViewConfig().getExploreSubjectsUrl();
    }
}

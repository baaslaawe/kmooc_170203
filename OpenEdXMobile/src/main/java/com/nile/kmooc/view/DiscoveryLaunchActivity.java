package com.nile.kmooc.view;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;

import com.nile.kmooc.base.BaseFragmentActivity;
import com.nile.kmooc.databinding.ActivityDiscoveryLaunchBinding;
import com.nile.kmooc.module.analytics.ISegment;

public class DiscoveryLaunchActivity extends BaseFragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final ActivityDiscoveryLaunchBinding binding = DataBindingUtil.setContentView(this, com.nile.kmooc.R.layout.activity_discovery_launch);
        if (environment.getConfig().getCourseDiscoveryConfig().isCourseDiscoveryEnabled()) {
            binding.discoverCourses.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    environment.getSegment().trackDiscoverCoursesClicked();
                    environment.getRouter().showFindCourses(DiscoveryLaunchActivity.this);
                }
            });
        } else {
            binding.discoverCourses.setVisibility(View.GONE);
        }
        if (environment.getConfig().getCourseDiscoveryConfig().isExploreSubjectsEnabled()) {
            binding.exploreSubjects.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    environment.getSegment().trackExploreSubjectsClicked();
                    environment.getRouter().showExploreSubjects(DiscoveryLaunchActivity.this);
                }
            });
        } else {
            binding.exploreSubjects.setVisibility(View.INVISIBLE);
        }
        environment.getSegment().trackScreenView(ISegment.Screens.LAUNCH_ACTIVITY);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (environment.getLoginPrefs().getUsername() != null) {
            finish();
            environment.getRouter().showMyCourses(this);
        }
    }

    @Override
    protected boolean createOptionsMenu(Menu menu) {
        return false; // Disable menu inherited from BaseFragmentActivity
    }
}

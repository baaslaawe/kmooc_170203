package com.nile.kmooc.view.my_videos;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.WindowManager;

import com.nile.kmooc.base.BaseVideosDownloadStateActivity;
import com.nile.kmooc.view.adapters.StaticFragmentPagerAdapter;
import com.nile.kmooc.module.analytics.ISegment;

public class MyVideosActivity extends BaseVideosDownloadStateActivity {

    private View offlineBar;
    private StaticFragmentPagerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.nile.kmooc.R.layout.activity_myvideos_tab);
        setSupportActionBar((Toolbar) findViewById(com.nile.kmooc.R.id.toolbar));

        // configure slider layout. This should be called only once and
        // hence is shifted to onCreate() function
        configureDrawer();

        offlineBar = findViewById(com.nile.kmooc.R.id.offline_bar);

        environment.getSegment().trackScreenView(ISegment.Screens.MY_VIDEOS);

        // now init the tabs
        initializeTabs();

        // Full-screen video in landscape.
        if (isLandscape()) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
            setActionBarVisible(false);
        }
    }

    private void initializeTabs() {
        ViewPager pager = (ViewPager) findViewById(com.nile.kmooc.R.id.pager);
        adapter = new StaticFragmentPagerAdapter(getSupportFragmentManager(),
                new StaticFragmentPagerAdapter.Item(MyAllVideosFragment.class,
                        getText(com.nile.kmooc.R.string.my_all_videos)),
                new StaticFragmentPagerAdapter.Item(MyRecentVideosFragment.class,
                        getText(com.nile.kmooc.R.string.my_recent_videos))
        );
        pager.setAdapter(adapter);
        TabLayout tabLayout = (TabLayout) findViewById(com.nile.kmooc.R.id.tabs);
        if (tabLayout != null) {
            tabLayout.setTabsFromPagerAdapter(adapter);
            tabLayout.setOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(pager));
            pager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        }
    }

    @Override
    protected void onOffline() {
        super.onOffline();
        offlineBar.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onOnline() {
        super.onOnline();
        offlineBar.setVisibility(View.GONE);
    }
}

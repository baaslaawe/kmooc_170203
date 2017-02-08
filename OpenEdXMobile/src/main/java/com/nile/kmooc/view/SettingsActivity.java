package com.nile.kmooc.view;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.nile.kmooc.base.BaseSingleFragmentActivity;

public class SettingsActivity extends BaseSingleFragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(getString(com.nile.kmooc.R.string.settings_txt));
        configureDrawer();
    }

    @Override
    public Fragment getFirstFragment() {
        return new SettingsFragment();
    }

}

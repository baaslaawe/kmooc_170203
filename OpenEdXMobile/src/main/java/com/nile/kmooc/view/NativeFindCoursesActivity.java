package com.nile.kmooc.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;

import com.nile.kmooc.base.BaseSingleFragmentActivity;
import com.nile.kmooc.module.analytics.ISegment;
import com.nile.kmooc.view.dialog.NativeFindCoursesFragment;

public class NativeFindCoursesActivity extends BaseSingleFragmentActivity {

    public static Intent newIntent(@NonNull Context context) {
        return new Intent(context, NativeFindCoursesActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(getString(com.nile.kmooc.R.string.find_courses_title));
        environment.getSegment().trackScreenView(ISegment.Screens.FIND_COURSES);
        if (environment.getLoginPrefs().getUsername() != null) {
            configureDrawer();
        }
    }

    @Override
    public Fragment getFirstFragment() {
        return new NativeFindCoursesFragment();
    }
}

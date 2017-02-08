package com.nile.kmooc.view;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;

import com.google.inject.Inject;
import com.nile.kmooc.module.prefs.LoginPrefs;

import com.nile.kmooc.base.BaseFragmentActivity;
import com.nile.kmooc.databinding.ActivityLaunchBinding;
import com.nile.kmooc.module.analytics.ISegment;

import android.content.Intent;
import android.net.Uri;

public class LaunchActivity extends BaseFragmentActivity {

    @Inject
    LoginPrefs loginPrefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final ActivityLaunchBinding binding = DataBindingUtil.setContentView(this, com.nile.kmooc.R.layout.activity_launch);
        binding.signInTv.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(environment.getRouter().getLogInIntent());
            }
        });
        binding.signUpBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
//                environment.getSegment().trackUserSignUpForAccount();
//                startActivity(environment.getRouter().getRegisterIntent());
                Uri uri = Uri.parse("https://www.kmooc.kr/register");
                Intent browse = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(browse);
            }
        });
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

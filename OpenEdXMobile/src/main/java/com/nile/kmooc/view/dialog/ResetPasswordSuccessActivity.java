package com.nile.kmooc.view.dialog;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.View.OnClickListener;

import com.nile.kmooc.base.BaseAppActivity;
import com.nile.kmooc.databinding.ActivityPasswordResetSuccessBinding;
import com.nile.kmooc.util.IntentFactory;

public class ResetPasswordSuccessActivity extends BaseAppActivity {

    @NonNull
    public static Intent newIntent() {
        return IntentFactory.newIntentForComponent(ResetPasswordSuccessActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final ActivityPasswordResetSuccessBinding binding = DataBindingUtil.setContentView(this, com.nile.kmooc.R.layout.activity_password_reset_success);
        binding.positiveButton.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                finish();
            }
        });
    }
}

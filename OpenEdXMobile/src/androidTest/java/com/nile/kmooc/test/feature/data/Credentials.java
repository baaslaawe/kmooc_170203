package com.nile.kmooc.test.feature.data;

import android.support.annotation.NonNull;

import com.nile.kmooc.util.Config;
import com.nile.kmooc.util.ResourceUtil;

import java.util.UUID;

public class Credentials {
    @NonNull
    public final String email;
    @NonNull
    public final String password;
    @NonNull
    public final String username;

    public Credentials(@NonNull String email, @NonNull String password) {
        this.email = email;
        this.password = password;
        this.username = email.split("@")[0];
    }

    public static Credentials freshCredentials(@NonNull Config config) {
        String randomUdid = UUID.randomUUID().toString();
        String testEmail = ResourceUtil.getFormattedString(
                config.getEndToEndConfig().getEmailTemplate(),
                "unique_id", randomUdid).toString();
        return new Credentials(testEmail, randomUdid);
    }
}

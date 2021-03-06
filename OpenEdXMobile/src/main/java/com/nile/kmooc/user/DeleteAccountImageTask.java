package com.nile.kmooc.user;

import android.content.Context;
import android.support.annotation.NonNull;

import com.google.inject.Inject;
import com.nile.kmooc.module.prefs.LoginPrefs;

import com.nile.kmooc.event.ProfilePhotoUpdatedEvent;
import com.nile.kmooc.task.Task;

import de.greenrobot.event.EventBus;

public class DeleteAccountImageTask extends
        Task<Void> {

    @Inject
    private UserService userService;

    @Inject
    private LoginPrefs loginPrefs;

    @NonNull
    private final String username;

    public DeleteAccountImageTask(@NonNull Context context, @NonNull String username) {
        super(context);
        this.username = username;
    }


    public Void call() throws Exception {
        userService.deleteProfileImage(username).execute();
        return null;
    }

    @Override
    protected void onSuccess(Void response) throws Exception {
        EventBus.getDefault().post(new ProfilePhotoUpdatedEvent(username, null));
        // Delete the logged in user's ProfileImage
        loginPrefs.setProfileImage(username, null);
    }
}

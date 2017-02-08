package com.nile.kmooc.user;

import android.content.Context;
import android.graphics.Rect;
import android.net.Uri;
import android.support.annotation.NonNull;

import com.google.inject.Inject;

import com.nile.kmooc.event.ProfilePhotoUpdatedEvent;
import com.nile.kmooc.task.Task;
import com.nile.kmooc.third_party.crop.CropUtil;

import java.io.File;

import de.greenrobot.event.EventBus;

public class SetAccountImageTask extends
        Task<Void> {

    @Inject
    private UserAPI userAPI;

    @NonNull
    private final String username;

    @NonNull
    private final Uri uri;

    @NonNull
    private final Rect cropRect;

    public SetAccountImageTask(@NonNull Context context, @NonNull String username, @NonNull Uri uri, @NonNull Rect cropRect) {
        super(context);
        this.username = username;
        this.uri = uri;
        this.cropRect = cropRect;
    }


    public Void call() throws Exception {
        final File cropped = new File(context.getExternalCacheDir(), "cropped-image" + System.currentTimeMillis() + ".jpg");
        CropUtil.crop(getContext(), uri, cropRect, 500, 500, cropped);
        userAPI.setProfileImage(username, cropped).execute();
        return null;
    }

    @Override
    protected void onSuccess(Void response) throws Exception {
        EventBus.getDefault().post(new ProfilePhotoUpdatedEvent(username, uri));
    }
}

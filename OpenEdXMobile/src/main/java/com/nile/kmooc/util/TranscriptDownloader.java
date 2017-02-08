package com.nile.kmooc.util;

import android.content.Context;

import com.google.inject.Inject;
import com.nile.kmooc.logger.Logger;
import com.nile.kmooc.services.ServiceManager;

import roboguice.RoboGuice;

public abstract class TranscriptDownloader implements Runnable {

    private String srtUrl;
    @Inject
    ServiceManager localApi;
    private final Logger logger = new Logger(TranscriptDownloader.class.getName());

    public TranscriptDownloader(Context context, String url) {
        this.srtUrl = url;
        RoboGuice.getInjector(context).injectMembers(this);
    }

    @Override
    public void run() {
        try {
            String response = localApi.downloadTranscript(srtUrl);
            onDownloadComplete(response);
        } catch (Exception localException) {
            handle(localException);
            logger.error(localException);
        }
    }

    public abstract void handle(Exception ex);

    public abstract void onDownloadComplete(String response);
}

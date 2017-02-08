package com.nile.kmooc.task;

import android.content.Context;
import android.support.annotation.NonNull;

import com.nile.kmooc.model.api.SyncLastAccessedSubsectionResponse;
import com.nile.kmooc.services.ServiceManager;

public abstract class GetLastAccessedTask extends Task<SyncLastAccessedSubsectionResponse> {

    @NonNull
    String courseId;
    public GetLastAccessedTask(@NonNull Context context, @NonNull String courseId) {
        super(context);
        this.courseId = courseId;
    }

    @Override
    public SyncLastAccessedSubsectionResponse call() throws Exception{
        ServiceManager api = environment.getServiceManager();
        SyncLastAccessedSubsectionResponse res = api.getLastAccessedSubsection(courseId);
        return res;
    }
}

package com.nile.kmooc.task;

import android.content.Context;
import android.support.annotation.NonNull;

import com.nile.kmooc.model.api.EnrolledCoursesResponse;

import java.util.List;

public class GetAllDownloadedVideosTask extends Task<List<EnrolledCoursesResponse>> {
    public GetAllDownloadedVideosTask(@NonNull Context context) {
        super(context);
    }

    @Override
    public List<EnrolledCoursesResponse> call() throws Exception {
        return environment.getStorage().getDownloadedCoursesWithVideoCountAndSize();
    }
}

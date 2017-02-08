package com.nile.kmooc.task;

import android.content.Context;
import android.support.annotation.NonNull;

import com.nile.kmooc.services.ServiceManager;
import com.nile.kmooc.model.api.EnrolledCoursesResponse;
import com.nile.kmooc.model.api.HandoutModel;

public abstract class GetHandoutTask extends Task<HandoutModel> {
    @NonNull
    EnrolledCoursesResponse enrollment;
    public GetHandoutTask(@NonNull Context context, @NonNull EnrolledCoursesResponse enrollment) {
        super(context);
        this.enrollment = enrollment;
    }

    @Override
    public HandoutModel call() throws Exception{
        ServiceManager api = environment.getServiceManager();

        // return instant data from cache if available
        HandoutModel model = api.getHandout
                (enrollment.getCourse().getCourse_handouts(), true);
        if (model == null) {
            model = api.getHandout(enrollment.getCourse()
                    .getCourse_handouts(), false);;
        }

        return model;
    }

}

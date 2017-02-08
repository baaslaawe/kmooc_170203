/*
 * GetEnrolledCourseTask
 *
 * This task is used to get the course information for a single course from the api which does not
 * have an endpoint to retrieve data for a single course. It uses the endpoint that returns a list
 * of enrolled courses and then parses out the course that matches the given course id.
 */

package com.nile.kmooc.task;

import android.content.Context;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.nile.kmooc.model.api.EnrolledCoursesResponse;

public abstract class GetEnrolledCourseTask extends Task<EnrolledCoursesResponse> {

    @NonNull
    private final String courseId;

    public GetEnrolledCourseTask(@NonNull Context context, @NonNull String courseId) {
        super(context);
        if (TextUtils.isEmpty(courseId)) throw new IllegalArgumentException();
        this.courseId = courseId;
    }

    @Override
    public EnrolledCoursesResponse call() throws Exception {
        for (EnrolledCoursesResponse course : environment.getServiceManager().getEnrolledCourses(false)) {
            if (course.getCourse().getId().equals(courseId)) {
                return course;
            }
        }
        throw new RuntimeException("Course not found in enrolled courses response");
    }
}

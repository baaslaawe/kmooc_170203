/*
 * CourseDetailActivity
 *
 * Activity that holds the fragments related to the course detail.
 */

package com.nile.kmooc.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;

import com.nile.kmooc.base.BaseSingleFragmentActivity;
import com.nile.kmooc.course.CourseDetail;

public class CourseDetailActivity extends BaseSingleFragmentActivity {

    public static Intent newIntent(@NonNull Context context, @NonNull CourseDetail courseDetail) {
        return new Intent(context, CourseDetailActivity.class)
                .putExtra(CourseDetailFragment.COURSE_DETAIL, courseDetail);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(com.nile.kmooc.R.string.find_courses_title);
        //environment.getSegment().trackScreenView(ISegment.Screens.???? + CourseDetail.course_id); //TODO Course Detail Screen, figure out what information to send.
    }

    @Override
    public Fragment getFirstFragment() {
        return new CourseDetailFragment();
    }
}

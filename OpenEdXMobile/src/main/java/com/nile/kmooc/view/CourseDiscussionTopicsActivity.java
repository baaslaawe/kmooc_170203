package com.nile.kmooc.view;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.google.inject.Inject;
import com.nile.kmooc.base.BaseSingleFragmentActivity;
import com.nile.kmooc.model.api.EnrolledCoursesResponse;

import com.nile.kmooc.module.analytics.ISegment;

import roboguice.inject.InjectExtra;

public class CourseDiscussionTopicsActivity extends BaseSingleFragmentActivity {

    @Inject
    private CourseDiscussionTopicsFragment discussionFragment;

    @InjectExtra(Router.EXTRA_COURSE_DATA)
    private EnrolledCoursesResponse courseData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        blockDrawerFromOpening();
        environment.getSegment().trackScreenView(ISegment.Screens.FORUM_VIEW_TOPICS,
                courseData.getCourse().getId(), null, null);
    }

    @Override
    public Fragment getFirstFragment() {
        if (courseData != null) {
            Bundle bundle = new Bundle();
            bundle.putSerializable(Router.EXTRA_COURSE_DATA, courseData);
            discussionFragment.setArguments(bundle);
        }
        discussionFragment.setRetainInstance(true);
        return discussionFragment;
    }

    @Override
    protected void onStart() {
        super.onStart();
        setTitle(getString(com.nile.kmooc.R.string.discussion_topics_title));
    }
}

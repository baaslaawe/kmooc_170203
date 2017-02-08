package com.nile.kmooc.view.adapters;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.nile.kmooc.model.course.BlockType;
import com.nile.kmooc.model.course.CourseComponent;
import com.nile.kmooc.model.course.DiscussionBlockModel;
import com.nile.kmooc.model.course.VideoBlockModel;
import com.nile.kmooc.view.CourseUnitOnlyOnYoutubeFragment;
import com.nile.kmooc.model.api.EnrolledCoursesResponse;
import com.nile.kmooc.model.course.HtmlBlockModel;
import com.nile.kmooc.util.Config;
import com.nile.kmooc.view.CourseUnitDiscussionFragment;
import com.nile.kmooc.view.CourseUnitEmptyFragment;
import com.nile.kmooc.view.CourseUnitFragment;
import com.nile.kmooc.view.CourseUnitMobileNotSupportedFragment;
import com.nile.kmooc.view.CourseUnitVideoFragment;
import com.nile.kmooc.view.CourseUnitWebViewFragment;

import java.util.List;

public class CourseUnitPagerAdapter extends FragmentStatePagerAdapter {
    private Config config;
    private List<CourseComponent> unitList;
    private EnrolledCoursesResponse courseData;
    private CourseUnitFragment.HasComponent callback;

    public CourseUnitPagerAdapter(FragmentManager manager,
                                  Config config,
                                  List<CourseComponent> unitList,
                                  EnrolledCoursesResponse courseData,
                                  CourseUnitFragment.HasComponent callback) {
        super(manager);
        this.config = config;
        this.unitList = unitList;
        this.courseData = courseData;
        this.callback = callback;
    }

    public CourseComponent getUnit(int pos) {
        if (pos >= unitList.size())
            pos = unitList.size() - 1;
        if (pos < 0)
            pos = 0;
        return unitList.get(pos);
    }

    /**
     * @return True if given unit is a video unit (not an only on YouTube unit)
     */
    public static boolean isCourseUnitVideo(CourseComponent unit) {
        return (unit instanceof VideoBlockModel && ((VideoBlockModel) unit).getData().encodedVideos.getPreferredVideoInfo() != null);
    }

    @Override
    public Fragment getItem(int pos) {
        CourseComponent unit = getUnit(pos);
        CourseUnitFragment unitFragment;
        //FIXME - for the video, let's ignore studentViewMultiDevice for now
        if (isCourseUnitVideo(unit)) {
            unitFragment = CourseUnitVideoFragment.newInstance((VideoBlockModel) unit, (pos < unitList.size()), (pos > 0));
        } else if (unit instanceof VideoBlockModel && ((VideoBlockModel) unit).getData().encodedVideos.getYoutubeVideoInfo() != null) {
            unitFragment = CourseUnitOnlyOnYoutubeFragment.newInstance(unit);
        } else if (config.isDiscussionsEnabled() && unit instanceof DiscussionBlockModel) {
            unitFragment = CourseUnitDiscussionFragment.newInstance(unit, courseData);
        } else if (!unit.isMultiDevice()) {
            unitFragment = CourseUnitMobileNotSupportedFragment.newInstance(unit);
        } else if (unit.getType() != BlockType.VIDEO &&
                unit.getType() != BlockType.HTML &&
                unit.getType() != BlockType.OTHERS &&
                unit.getType() != BlockType.DISCUSSION &&
                unit.getType() != BlockType.PROBLEM) {
            unitFragment = CourseUnitEmptyFragment.newInstance(unit);
        } else if (unit instanceof HtmlBlockModel) {
            unitFragment = CourseUnitWebViewFragment.newInstance((HtmlBlockModel) unit);
        }

        //fallback
        else {
            unitFragment = CourseUnitMobileNotSupportedFragment.newInstance(unit);
        }

        unitFragment.setHasComponentCallback(callback);

        return unitFragment;
    }

    @Override
    public int getCount() {
        return unitList.size();
    }
}

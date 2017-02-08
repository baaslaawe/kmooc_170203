package com.nile.kmooc.view.common;

import com.nile.kmooc.model.course.CourseComponent;

/**
 * Created by hanning on 6/9/15.
 */
public interface RunnableCourseComponent extends Runnable{
    CourseComponent getCourseComponent();
}

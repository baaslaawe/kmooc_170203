package com.nile.kmooc.view.adapters;

import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.nile.kmooc.model.api.CourseEntry;
import com.nile.kmooc.util.images.TopAnchorFillWidthTransformation;

public class CourseCardViewHolder extends BaseListAdapter.BaseViewHolder {

    @LayoutRes
    public static int LAYOUT = com.nile.kmooc.R.layout.row_course_list;

    private final ImageView courseImage;
    private final TextView courseTitle;
    private final TextView courseRun;
    private final TextView startingFrom;
    private final View newCourseContent;

    public CourseCardViewHolder(View convertView) {
        this.courseTitle = (TextView) convertView
                .findViewById(com.nile.kmooc.R.id.course_name);
        this.courseRun = (TextView) convertView
                .findViewById(com.nile.kmooc.R.id.course_run);
        this.startingFrom = (TextView) convertView
                .findViewById(com.nile.kmooc.R.id.starting_from);
        this.courseImage = (ImageView) convertView
                .findViewById(com.nile.kmooc.R.id.course_image);
        this.newCourseContent = convertView
                .findViewById(com.nile.kmooc.R.id.new_course_content_layout);
    }

    public void setCourseTitle(@NonNull String title) {
        courseTitle.setText(title);
    }

    public void setCourseImage(@NonNull String imageUrl) {
        Glide.with(courseImage.getContext())
                .load(imageUrl)
                .placeholder(com.nile.kmooc.R.drawable.placeholder_course_card_image)
                .transform(new TopAnchorFillWidthTransformation(courseImage.getContext()))
                .into(courseImage);
    }

    public void setHasUpdates(@NonNull CourseEntry courseData, @NonNull View.OnClickListener listener) {
        startingFrom.setVisibility(View.GONE);
        newCourseContent.setVisibility(View.VISIBLE);
        newCourseContent.setTag(courseData);
        newCourseContent.setOnClickListener(listener);
    }

    public void setDescription(@NonNull String description, @NonNull String formattedDate) {
        newCourseContent.setVisibility(View.GONE);
        startingFrom.setVisibility(View.VISIBLE);
        courseRun.setText(description);
        startingFrom.setText(formattedDate);
    }
}

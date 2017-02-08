package com.nile.kmooc.view.adapters;

import android.content.Context;
import android.os.SystemClock;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.nile.kmooc.core.IEdxEnvironment;
import com.nile.kmooc.model.api.CourseEntry;
import com.nile.kmooc.model.api.EnrolledCoursesResponse;
import com.nile.kmooc.util.images.TopAnchorFillWidthTransformation;

import com.nile.kmooc.util.MemoryUtil;

public abstract class MyAllVideoCourseAdapter extends BaseListAdapter<EnrolledCoursesResponse> {
    private long lastClickTime;

    public MyAllVideoCourseAdapter(Context context, IEdxEnvironment environment) {
        super(context, com.nile.kmooc.R.layout.row_myvideo_course_list, environment);
        lastClickTime = 0;
    }

    @Override
    public void render(BaseViewHolder tag, EnrolledCoursesResponse enrollment) {
        ViewHolder holder = (ViewHolder) tag;

        CourseEntry courseData = enrollment.getCourse();
        holder.courseTitle.setText(courseData.getName());

        String code = courseData.getOrg()+ " | " + courseData.getNumber();
        holder.schoolCode.setText(code);
        String videos=enrollment.getVideoCountReadable() + ",";
        holder.no_of_videos.setText(videos);
        holder.size_of_videos.setText(MemoryUtil.format(getContext(), enrollment.size));

        Glide.with(getContext())
                .load(courseData.getCourse_image(environment.getConfig().getApiHostURL()))
                .placeholder(com.nile.kmooc.R.drawable.placeholder_course_card_image)
                .transform(new TopAnchorFillWidthTransformation(getContext()))
                .into(holder.courseImage);
    }

    @Override
    public BaseViewHolder getTag(View convertView) {
        ViewHolder holder = new ViewHolder();
        holder.courseTitle = (TextView) convertView
                .findViewById(com.nile.kmooc.R.id.course_name);
        holder.schoolCode = (TextView) convertView
                .findViewById(com.nile.kmooc.R.id.school_code);
        holder.courseImage = (ImageView) convertView
                .findViewById(com.nile.kmooc.R.id.course_image);
        holder.no_of_videos = (TextView) convertView
                .findViewById(com.nile.kmooc.R.id.no_of_videos);
        holder.size_of_videos = (TextView) convertView
                .findViewById(com.nile.kmooc.R.id.size_of_videos);
        return holder;
    }

    private static class ViewHolder extends BaseViewHolder {
        ImageView courseImage;
        TextView courseTitle;
        TextView schoolCode;
        TextView no_of_videos;
        TextView size_of_videos;
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View arg1, int position, long arg3) {
        //This has been used so that if user clicks continuously on the screen,
        //two activities should not be opened
        long currentTime = SystemClock.elapsedRealtime();
        if (currentTime - lastClickTime > MIN_CLICK_INTERVAL) {
            lastClickTime = currentTime;
            EnrolledCoursesResponse model = (EnrolledCoursesResponse)adapterView.getItemAtPosition(position);
            if(model!=null) onItemClicked(model);
        }
    }

    public abstract void onItemClicked(EnrolledCoursesResponse model);
}

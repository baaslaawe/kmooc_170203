package com.nile.kmooc.view.my_videos;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.google.inject.Inject;
import com.nile.kmooc.base.BaseFragment;
import com.nile.kmooc.core.IEdxEnvironment;
import com.nile.kmooc.logger.Logger;
import com.nile.kmooc.model.api.EnrolledCoursesResponse;
import com.nile.kmooc.util.AppConstants;
import com.nile.kmooc.view.Router;
import com.nile.kmooc.view.VideoListActivity;
import com.nile.kmooc.view.adapters.MyAllVideoCourseAdapter;

import com.nile.kmooc.module.analytics.ISegment;
import com.nile.kmooc.module.storage.DownloadCompletedEvent;
import com.nile.kmooc.module.storage.DownloadedVideoDeletedEvent;
import com.nile.kmooc.task.GetAllDownloadedVideosTask;

import java.util.List;

import de.greenrobot.event.EventBus;

public class MyAllVideosFragment extends BaseFragment {

    private MyAllVideoCourseAdapter myCoursesAdaptor;
    protected final Logger logger = new Logger(getClass().getName());
    private GetAllDownloadedVideosTask getAllDownloadedVideosTask;

    @Inject
    protected IEdxEnvironment environment;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        environment.getSegment().trackScreenView(ISegment.Screens.MY_VIDEOS_ALL);
        EventBus.getDefault().register(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(com.nile.kmooc.R.layout.fragment_my_all_videos, container, false);

        ListView myCourseList = (ListView) view.findViewById(com.nile.kmooc.R.id.videos_course_list);
        myCourseList.setEmptyView(view.findViewById(com.nile.kmooc.R.id.empty_list_view));

        myCoursesAdaptor = new MyAllVideoCourseAdapter(getActivity(), environment) {
            @Override
            public void onItemClicked(EnrolledCoursesResponse model) {
                AppConstants.myVideosDeleteMode = false;
                Intent videoIntent = new Intent(getActivity(), VideoListActivity.class);
                videoIntent.putExtra(Router.EXTRA_COURSE_DATA, model);
                startActivity(videoIntent);
            }
        };


        // Add empty views to cause dividers to render at the top and bottom of the list
        myCourseList.addHeaderView(new View(getContext()), null, false);
        myCourseList.addFooterView(new View(getContext()), null, false);
        myCourseList.setAdapter(myCoursesAdaptor);
        myCourseList.setOnItemClickListener(myCoursesAdaptor);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        addMyAllVideosData();
    }

    @Override
    public void onStop() {
        super.onStop();
        if (getAllDownloadedVideosTask != null) {
            getAllDownloadedVideosTask.cancel(true);
            getAllDownloadedVideosTask = null;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    protected void addMyAllVideosData() {
        if (myCoursesAdaptor == null) {
            return;
        }
        if (getAllDownloadedVideosTask != null) {
            getAllDownloadedVideosTask.cancel(true);
        }
        getAllDownloadedVideosTask = new GetAllDownloadedVideosTask(getActivity()) {
            @Override
            protected void onSuccess(List<EnrolledCoursesResponse> enrolledCoursesResponses) throws Exception {
                super.onSuccess(enrolledCoursesResponses);
                myCoursesAdaptor.clear();
                if (enrolledCoursesResponses != null) {
                    for (EnrolledCoursesResponse m : enrolledCoursesResponses) {
                        if (m.isIs_active()) {
                            myCoursesAdaptor.add(m);
                        }
                    }
                }
            }
        };
        getAllDownloadedVideosTask.execute();
    }

    public void onEventMainThread(DownloadedVideoDeletedEvent e) {
        addMyAllVideosData();
    }
    public void onEventMainThread(DownloadCompletedEvent e) {
        addMyAllVideosData();
    }
}

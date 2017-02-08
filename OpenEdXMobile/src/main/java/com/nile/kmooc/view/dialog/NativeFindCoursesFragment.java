package com.nile.kmooc.view.dialog;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.google.inject.Inject;
import com.nile.kmooc.base.BaseFragment;
import com.nile.kmooc.core.IEdxEnvironment;
import com.nile.kmooc.http.CallTrigger;
import com.nile.kmooc.view.common.TaskProgressCallback;

import com.nile.kmooc.course.CourseAPI;
import com.nile.kmooc.course.CourseDetail;
import com.nile.kmooc.http.ErrorHandlingCallback;
import com.nile.kmooc.model.Page;
import com.nile.kmooc.view.adapters.FindCoursesListAdapter;
import com.nile.kmooc.view.adapters.InfiniteScrollUtils;

import retrofit2.Call;

public class NativeFindCoursesFragment extends BaseFragment {

    @Inject
    private CourseAPI courseAPI;

    @Inject
    IEdxEnvironment environment;

    @Nullable
    private Call<Page<CourseDetail>> call;

    @Nullable
    private ViewHolder viewHolder;

    private int nextPage = 1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(com.nile.kmooc.R.layout.fragment_find_courses,
                container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.viewHolder = new ViewHolder(view);
        viewHolder.listView.setVisibility(View.GONE);
        viewHolder.loadingIndicator.setVisibility(View.VISIBLE);
        final FindCoursesListAdapter adapter = new FindCoursesListAdapter(getActivity(), environment) {
            @Override
            public void onItemClicked(CourseDetail model) {
                environment.getRouter().showCourseDetail(getActivity(), model);
            }
        };
        // Add empty views to cause a dividers to render at the top and bottom of the list
        viewHolder.listView.addHeaderView(new View(getContext()), null, false);
        viewHolder.listView.addFooterView(new View(getContext()), null, false);
        InfiniteScrollUtils.configureListViewWithInfiniteList(viewHolder.listView, adapter, new InfiniteScrollUtils.PageLoader<CourseDetail>() {
            @Override
            public void loadNextPage(@NonNull final InfiniteScrollUtils.PageLoadCallback<CourseDetail> callback) {
                if (null != call) {
                    call.cancel();
                }
                call = courseAPI.getCourseList(nextPage);
                call.enqueue(new ErrorHandlingCallback<Page<CourseDetail>>(getActivity(),
                        CallTrigger.LOADING_UNCACHED, (TaskProgressCallback) null) {
                    @Override
                    protected void onResponse(@NonNull final Page<CourseDetail> coursesPage) {
                        callback.onPageLoaded(coursesPage);
                        ++nextPage;
                        if (null != viewHolder) {
                            viewHolder.listView.setVisibility(View.VISIBLE);
                            viewHolder.loadingIndicator.setVisibility(View.GONE);
                        }
                    }

                    @Override
                    protected void onFailure(@NonNull final Throwable error) {
                        callback.onError();
                        nextPage = 1;
                        if (null != viewHolder) {
                            viewHolder.loadingIndicator.setVisibility(View.GONE);
                        }
                    }
                });
            }
        });
        viewHolder.listView.setOnItemClickListener(adapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        this.viewHolder = null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (null != call) {
            call.cancel();
        }
    }

    public static class ViewHolder {
        public final ListView listView;
        public final View loadingIndicator;

        public ViewHolder(View view) {
            this.listView = (ListView) view.findViewById(com.nile.kmooc.R.id.course_list);
            this.loadingIndicator = view.findViewById(com.nile.kmooc.R.id.loading_indicator);
        }
    }
}

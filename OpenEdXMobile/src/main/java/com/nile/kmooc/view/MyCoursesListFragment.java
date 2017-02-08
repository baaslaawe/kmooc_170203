package com.nile.kmooc.view;

import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nile.kmooc.base.BaseFragment;
import com.nile.kmooc.base.MainApplication;
import com.nile.kmooc.core.IEdxEnvironment;
import com.nile.kmooc.event.EnrolledInCourseEvent;
import com.nile.kmooc.exception.AuthException;
import com.nile.kmooc.http.HttpResponseStatusException;
import com.nile.kmooc.http.HttpStatus;
import com.nile.kmooc.interfaces.NetworkObserver;
import com.nile.kmooc.loader.CoursesAsyncLoader;
import com.nile.kmooc.logger.Logger;
import com.nile.kmooc.model.api.EnrolledCoursesResponse;
import com.nile.kmooc.module.prefs.LoginPrefs;
import com.nile.kmooc.task.RestoreVideosCacheDataTask;
import com.nile.kmooc.util.NetworkUtil;
import com.nile.kmooc.util.ViewAnimationUtil;
import com.nile.kmooc.view.adapters.MyCoursesAdapter;

import com.nile.kmooc.databinding.FragmentMyCoursesListBinding;
import com.nile.kmooc.databinding.PanelFindCourseBinding;

import com.nile.kmooc.interfaces.NetworkSubject;
import com.nile.kmooc.loader.AsyncTaskResult;
import com.nile.kmooc.module.analytics.ISegment;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import de.greenrobot.event.EventBus;

public class MyCoursesListFragment extends BaseFragment implements NetworkObserver, LoaderManager.LoaderCallbacks<AsyncTaskResult<List<EnrolledCoursesResponse>>> {

    private static final int MY_COURSE_LOADER_ID = 0x905000;

    private MyCoursesAdapter adapter;
    private FragmentMyCoursesListBinding binding;
    private final Logger logger = new Logger(getClass().getSimpleName());
    private boolean refreshOnResume = false;

    @Inject
    private IEdxEnvironment environment;

    @Inject
    private LoginPrefs loginPrefs;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adapter = new MyCoursesAdapter(getActivity(), environment) {
            @Override
            public void onItemClicked(EnrolledCoursesResponse model) {
                environment.getRouter().showCourseDashboardTabs(getActivity(), environment.getConfig(), model, false);
            }

            @Override
            public void onAnnouncementClicked(EnrolledCoursesResponse model) {
                environment.getRouter().showCourseDashboardTabs(getActivity(), environment.getConfig(), model, true);
            }
        };
        environment.getSegment().trackScreenView(ISegment.Screens.MY_COURSES);
        EventBus.getDefault().register(this);

        // Restore cache of the courses for which the user has downloaded any videos
        RestoreVideosCacheDataTask.executeInstanceIfNeeded(MainApplication.application);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, com.nile.kmooc.R.layout.fragment_my_courses_list, container, false);
        binding.swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Hide the progress bar as swipe layout has its own progress indicator
                binding.loadingIndicator.getRoot().setVisibility(View.GONE);
                binding.noCourseTv.setVisibility(View.GONE);
                loadData(false);
            }
        });
        binding.swipeContainer.setColorSchemeResources(com.nile.kmooc.R.color.edx_brand_primary_accent,
                com.nile.kmooc.R.color.edx_brand_gray_x_back, com.nile.kmooc.R.color.edx_brand_gray_x_back,
                com.nile.kmooc.R.color.edx_brand_gray_x_back);
        if (environment.getConfig().getCourseDiscoveryConfig().isCourseDiscoveryEnabled()) {
            // As per docs, the footer needs to be added before adapter is set to the ListView
            addFindCoursesFooter();
        }
        // Add empty views to cause dividers to render at the top and bottom of the list
        binding.myCourseList.addHeaderView(new View(getContext()), null, false);
        binding.myCourseList.addFooterView(new View(getContext()), null, false);
        binding.myCourseList.setAdapter(adapter);
        binding.myCourseList.setOnItemClickListener(adapter);
        if (!(NetworkUtil.isConnected(getActivity()))) {
            onOffline();
        } else {
            onOnline();
        }
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        loadData(true);
    }

    @Override
    public Loader<AsyncTaskResult<List<EnrolledCoursesResponse>>> onCreateLoader(int i, Bundle bundle) {
        return new CoursesAsyncLoader(getActivity());
    }

    @Override
    public void onLoadFinished(Loader<AsyncTaskResult<List<EnrolledCoursesResponse>>> asyncTaskResultLoader, AsyncTaskResult<List<EnrolledCoursesResponse>> result) {
        adapter.clear();
        if (result.getEx() != null) {
            if (result.getEx() instanceof AuthException) {
                loginPrefs.clear();
                getActivity().finish();
            } else if (result.getEx() instanceof HttpResponseStatusException &&
                    ((HttpResponseStatusException) result.getEx()).getStatusCode() == HttpStatus.UNAUTHORIZED) {
                environment.getRouter().forceLogout(
                        getContext(),
                        environment.getSegment(),
                        environment.getNotificationDelegate());
            } else {
                logger.error(result.getEx());
            }
        } else if (result.getResult() != null) {
            ArrayList<EnrolledCoursesResponse> newItems = new ArrayList<EnrolledCoursesResponse>(result.getResult());

            ((MyCoursesListActivity) getActivity()).updateDatabaseAfterDownload(newItems);

            if (result.getResult().size() > 0) {
                adapter.setItems(newItems);
                adapter.notifyDataSetChanged();
            }
        }
        binding.swipeContainer.setRefreshing(false);
        binding.loadingIndicator.getRoot().setVisibility(View.GONE);
        if (adapter.isEmpty() && !environment.getConfig().getCourseDiscoveryConfig().isCourseDiscoveryEnabled()) {
            binding.myCourseList.setVisibility(View.GONE);
            binding.noCourseTv.setVisibility(View.VISIBLE);
        } else {
            binding.myCourseList.setVisibility(View.VISIBLE);
            binding.noCourseTv.setVisibility(View.GONE);
        }
    }

    @Override
    public void onLoaderReset(Loader<AsyncTaskResult<List<EnrolledCoursesResponse>>> asyncTaskResultLoader) {
        adapter.clear();
        adapter.notifyDataSetChanged();
        binding.myCourseList.setVisibility(View.GONE);
        binding.loadingIndicator.getRoot().setVisibility(View.VISIBLE);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (refreshOnResume) {
            loadData(true);
            refreshOnResume = false;
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        hideOfflinePanel();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof NetworkSubject) {
            ((NetworkSubject) activity).registerNetworkObserver(this);
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        if (getActivity() instanceof NetworkSubject) {
            ((NetworkSubject) getActivity()).unregisterNetworkObserver(this);
        }
    }

    @Override
    public void onOnline() {
        if (binding.offlineBar != null && binding.swipeContainer != null) {
            binding.offlineBar.setVisibility(View.GONE);
            hideOfflinePanel();
            binding.swipeContainer.setEnabled(true);
        }
    }

    @Override
    public void onOffline() {
        binding.offlineBar.setVisibility(View.VISIBLE);
        showOfflinePanel();
        //Disable swipe functionality and hide the loading view
        binding.swipeContainer.setEnabled(false);
        binding.swipeContainer.setRefreshing(false);
    }

    @SuppressWarnings("unused")
    public void onEventMainThread(EnrolledInCourseEvent event) {
        refreshOnResume = true;
    }

    protected void loadData(boolean showProgress) {
        if (showProgress) {
            binding.loadingIndicator.getRoot().setVisibility(View.VISIBLE);
            binding.noCourseTv.setVisibility(View.GONE);
        }
        getLoaderManager().restartLoader(MY_COURSE_LOADER_ID, null, this);
    }

    private void showOfflinePanel() {
        ViewAnimationUtil.showMessageBar(binding.offlinePanel);
    }

    private void hideOfflinePanel() {
        ViewAnimationUtil.stopAnimation(binding.offlinePanel);
        if (binding.offlinePanel.getVisibility() == View.VISIBLE) {
            binding.offlinePanel.setVisibility(View.GONE);
        }
    }

    private void addFindCoursesFooter() {
        final PanelFindCourseBinding footer = DataBindingUtil.inflate(LayoutInflater.from(getActivity()), com.nile.kmooc.R.layout.panel_find_course, binding.myCourseList, false);
        binding.myCourseList.addFooterView(footer.getRoot(), null, false);
        footer.courseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                environment.getSegment().trackUserFindsCourses();
                environment.getRouter().showFindCourses(getActivity());
            }
        });
    }

}

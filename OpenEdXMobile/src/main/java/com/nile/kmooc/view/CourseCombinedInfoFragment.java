package com.nile.kmooc.view;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ProgressBar;
import android.widget.Switch;

import com.facebook.Settings;
import com.facebook.widget.LikeView;
import com.google.inject.Inject;
import com.nile.kmooc.base.BaseFragment;
import com.nile.kmooc.core.IEdxEnvironment;
import com.nile.kmooc.logger.Logger;
import com.nile.kmooc.social.facebook.FacebookProvider;
import com.nile.kmooc.task.GetAnnouncementTask;
import com.nile.kmooc.util.StandardCharsets;
import com.nile.kmooc.util.WebViewUtil;
import com.nile.kmooc.view.custom.URLInterceptorWebViewClient;

import com.nile.kmooc.model.api.AnnouncementsModel;
import com.nile.kmooc.model.api.EnrolledCoursesResponse;
import com.nile.kmooc.module.facebook.IUiLifecycleHelper;
import com.nile.kmooc.view.custom.EdxWebView;

import java.util.ArrayList;
import java.util.List;

public class CourseCombinedInfoFragment extends BaseFragment {

    static final String TAG = CourseCombinedInfoFragment.class.getCanonicalName();

    private final Logger logger = new Logger(getClass().getName());

    private EdxWebView announcementWebView;
    private View notificationSettingRow;
    private Switch notificationSwitch;

    private EnrolledCoursesResponse courseData;
    private List<AnnouncementsModel> savedAnnouncements;
    private IUiLifecycleHelper uiHelper;

    @Inject
    protected IEdxEnvironment environment;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        logger.debug("created: " + getClass().getName());

        Settings.sdkInitialize(getActivity());

        uiHelper = IUiLifecycleHelper.Factory.getInstance(getActivity(), null);
        uiHelper.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(com.nile.kmooc.R.layout.fragment_course_combined_info, container, false);

        announcementWebView = (EdxWebView) view.findViewById(com.nile.kmooc.R.id.announcement_webview);
        URLInterceptorWebViewClient client = new URLInterceptorWebViewClient(
                getActivity(), announcementWebView);
        // treat every link as external link in this view, so that all links will open in external browser
        client.setAllLinksAsExternal(true);

        notificationSettingRow = view.findViewById(com.nile.kmooc.R.id.notification_setting_row);
        notificationSwitch = (Switch) view.findViewById(com.nile.kmooc.R.id.notification_switch);

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (savedInstanceState != null) {

            try {
                savedAnnouncements = savedInstanceState.getParcelableArrayList(Router.EXTRA_ANNOUNCEMENTS);
            } catch (Exception ex) {
                logger.error(ex);
            }

        }

        try {
            final Bundle bundle = getArguments();
            courseData = (EnrolledCoursesResponse) bundle.getSerializable(Router.EXTRA_COURSE_DATA);
            FacebookProvider fbProvider = new FacebookProvider();

            if (courseData != null) {
                //Create the inflater used to create the announcement list
                if (savedAnnouncements == null) {
                    loadAnnouncementData(courseData);
                } else {
                    populateAnnouncements(savedAnnouncements);
                }
            }

            if (environment.getConfig().isNotificationEnabled()
                    && courseData != null && courseData.getCourse() != null) {
                notificationSettingRow.setVisibility(View.VISIBLE);
                final String courseId = courseData.getCourse().getId();
                final String subscriptionId = courseData.getCourse().getSubscription_id();
                boolean isSubscribed = environment.getNotificationDelegate().isSubscribedByCourseId(courseId);
                notificationSwitch.setChecked(isSubscribed);
                notificationSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        environment.getNotificationDelegate().changeNotificationSetting(
                                courseId, subscriptionId, isChecked);
                    }
                });
            } else {
                notificationSwitch.setOnCheckedChangeListener(null);
                notificationSettingRow.setVisibility(View.GONE);
            }
        } catch (Exception ex) {
            logger.error(ex);
        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        LikeView.handleOnActivityResult(getActivity(), requestCode, resultCode, data);
        //
        uiHelper.onActivityResult(requestCode, resultCode, data, null);

    }

    @Override
    public void onResume() {
        super.onResume();
        uiHelper.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        uiHelper.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        uiHelper.onDestroy();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        if (savedAnnouncements != null) {
            outState.putParcelableArrayList(Router.EXTRA_ANNOUNCEMENTS, new ArrayList<Parcelable>(savedAnnouncements));
        }
        uiHelper.onSaveInstanceState(outState);

    }

    private void loadAnnouncementData(EnrolledCoursesResponse enrollment) {
        GetAnnouncementTask task = new GetAnnouncementTask(getActivity(), enrollment) {

            @Override
            public void onException(Exception ex) {
                super.onException(ex);
                showEmptyAnnouncementMessage();
            }

            @Override
            public void onSuccess(List<AnnouncementsModel> announcementsList) {
                try {

                    savedAnnouncements = announcementsList;
                    populateAnnouncements(savedAnnouncements);

                } catch (Exception ex) {
                    logger.error(ex);
                    showEmptyAnnouncementMessage();
                }
            }
        };
        ProgressBar progressBar = (ProgressBar) getView().findViewById(com.nile.kmooc.R.id.loading_indicator);
        task.setProgressDialog(progressBar);
        task.execute();

    }

    private void populateAnnouncements(List<AnnouncementsModel> announcementsList) {
        if (announcementsList != null && announcementsList.size() > 0) {
            hideEmptyAnnouncementMessage();

            StringBuilder buff = WebViewUtil.getIntialWebviewBuffer(getActivity(), logger);

            buff.append("<body>");
            for (AnnouncementsModel model : announcementsList) {
                buff.append("<div class=\"header\">");
                buff.append(model.getDate());
                buff.append("</div>");
                buff.append("<div class=\"separator\"></div>");
                buff.append("<div>");
                buff.append(model.getContent());
                buff.append("</div>");
            }
            buff.append("</body>");

            announcementWebView.clearCache(true);
            announcementWebView.loadDataWithBaseURL(environment.getConfig().getApiHostURL(), buff.toString(), "text/html", StandardCharsets.UTF_8.name(), null);
        } else {
            showEmptyAnnouncementMessage();
        }
    }

    public void showEmptyAnnouncementMessage() {
        try {
            if (getView() != null) {
                getView().findViewById(com.nile.kmooc.R.id.no_announcement_tv).setVisibility(View.VISIBLE);
            }
        } catch (Exception e) {
            logger.error(e);
        }

    }

    private void hideEmptyAnnouncementMessage() {
        try {
            if (getView() != null) {
                getView().findViewById(com.nile.kmooc.R.id.no_announcement_tv).setVisibility(View.GONE);
            }
        } catch (Exception e) {
            logger.error(e);
        }
    }
}

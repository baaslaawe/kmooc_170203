package com.nile.kmooc.view;

import android.app.Activity;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.facebook.Session;
import com.facebook.SessionState;
import com.google.inject.Inject;
import com.nile.kmooc.BuildConfig;
import com.nile.kmooc.base.BaseFragment;
import com.nile.kmooc.base.BaseFragmentActivity;
import com.nile.kmooc.core.IEdxEnvironment;
import com.nile.kmooc.event.AccountDataLoadedEvent;
import com.nile.kmooc.event.ProfilePhotoUpdatedEvent;
import com.nile.kmooc.http.CallTrigger;
import com.nile.kmooc.logger.Logger;
import com.nile.kmooc.model.api.ProfileModel;
import com.nile.kmooc.module.analytics.ISegment;
import com.nile.kmooc.module.facebook.IUiLifecycleHelper;
import com.nile.kmooc.module.prefs.LoginPrefs;
import com.nile.kmooc.profiles.UserProfileActivity;
import com.nile.kmooc.user.Account;
import com.nile.kmooc.user.ProfileImage;
import com.nile.kmooc.user.UserAPI;
import com.nile.kmooc.user.UserService;
import com.nile.kmooc.util.Config;
import com.nile.kmooc.util.EmailUtil;
import com.nile.kmooc.util.ResourceUtil;
import com.nile.kmooc.view.my_videos.MyVideosActivity;

import com.nile.kmooc.databinding.DrawerNavigationBinding;

import java.util.HashMap;
import java.util.Map;

import de.greenrobot.event.EventBus;
import retrofit2.Call;


public class NavigationFragment extends BaseFragment {

    private static final String TAG = "NavigationFragment";
    private DrawerNavigationBinding drawerNavigationBinding;
    private final Logger logger = new Logger(getClass().getName());
    @Nullable
    private Call<Account> getAccountCall;
    @Nullable
    private ProfileImage profileImage;
    ProfileModel profile;
    @Nullable
    ImageView imageView;
    private IUiLifecycleHelper uiLifecycleHelper;

    @Inject
    private UserService userService;

    @Inject
    IEdxEnvironment environment;
    @Inject
    Config config;
    @Inject
    LoginPrefs loginPrefs;

    private Session.StatusCallback callback = new Session.StatusCallback() {
        @Override
        public void call(Session session, SessionState state, Exception exception) {
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        uiLifecycleHelper = IUiLifecycleHelper.Factory.getInstance(getActivity(), callback);
        uiLifecycleHelper.onCreate(savedInstanceState);
        profile = loginPrefs.getCurrentUserProfile();
        if (config.isUserProfilesEnabled() && profile != null && profile.username != null) {
            getAccountCall = userService.getAccount(profile.username);
            getAccountCall.enqueue(new UserAPI.AccountDataUpdatedCallback(
                    getActivity(),
                    profile.username,
                    CallTrigger.LOADING_UNCACHED,
                    null, // Disable global loading indicator
                    null)); // Disable global error message overlay
        }
        EventBus.getDefault().register(this);
    }

    private void loadProfileImage(@NonNull ProfileImage profileImage, @NonNull ImageView imageView) {
        if (profileImage.hasImage()) {
            Glide.with(NavigationFragment.this)
                    .load(profileImage.getImageUrlLarge())
                    .into(imageView);
        } else {
            Glide.with(NavigationFragment.this)
                    .load(com.nile.kmooc.R.drawable.profile_photo_placeholder)
                    .into(imageView);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        drawerNavigationBinding = DataBindingUtil.inflate(inflater, com.nile.kmooc.R.layout.drawer_navigation, container, false);
        if (config.isUserProfilesEnabled()) {
            if (null != profileImage) {
                loadProfileImage(profileImage, drawerNavigationBinding.profileImage);
            }
            if (profile != null && profile.username != null) {
                drawerNavigationBinding.profileImage.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final BaseFragmentActivity act = (BaseFragmentActivity) getActivity();
                        act.closeDrawer();

                        if (!(act instanceof UserProfileActivity)) {
                            environment.getRouter().showUserProfileWithNavigationDrawer(getActivity(), profile.username);

                            if (!(act instanceof MyCoursesListActivity)) {
                                act.finish();
                            }
                        }
                    }
                });
            }
        } else {
            drawerNavigationBinding.profileImage.setVisibility(View.GONE);
            drawerNavigationBinding.navigationHeaderLayout.setClickable(false);
            drawerNavigationBinding.navigationHeaderLayout.setForeground(null);
        }

        drawerNavigationBinding.drawerOptionMyCourses.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Activity act = getActivity();
                ((BaseFragmentActivity) act).closeDrawer();

                if (!(act instanceof MyCoursesListActivity)) {
                    environment.getRouter().showMyCourses(act);
                    act.finish();
                }
            }
        });

        drawerNavigationBinding.drawerOptionMyVideos.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Activity act = getActivity();
                ((BaseFragmentActivity) act).closeDrawer();

                if (!(act instanceof MyVideosActivity)) {
                    environment.getRouter().showMyVideos(act);
                    //Finish need not be called if the current activity is MyCourseListing
                    // as on returning back from FindCourses,
                    // the student should be returned to the MyCourses screen
                    if (!(act instanceof MyCoursesListActivity)) {
                        act.finish();
                    }
                }
            }
        });

        if (environment.getConfig().getCourseDiscoveryConfig().isCourseDiscoveryEnabled()) {
            drawerNavigationBinding.drawerOptionFindCourses.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    ISegment segIO = environment.getSegment();
                    segIO.trackUserFindsCourses();
                    FragmentActivity act = getActivity();
                    ((BaseFragmentActivity) act).closeDrawer();
                    if (!(act instanceof WebViewFindCoursesActivity || act instanceof NativeFindCoursesActivity)) {
                        environment.getRouter().showFindCourses(act);

                        //Finish need not be called if the current activity is MyCourseListing
                        // as on returning back from FindCourses,
                        // the student should be returned to the MyCourses screen
                        if (!(act instanceof MyCoursesListActivity)) {
                            act.finish();
                        }
                    }
                }
            });
        } else {
            drawerNavigationBinding.drawerOptionFindCourses.setVisibility(View.GONE);
        }

        drawerNavigationBinding.drawerOptionMySettings.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Activity act = getActivity();
                ((BaseFragmentActivity) act).closeDrawer();

                if (!(act instanceof SettingsActivity)) {
                    environment.getRouter().showSettings(act);

                    if (!(act instanceof MyCoursesListActivity)) {
                        act.finish();
                    }
                }
            }
        });

        drawerNavigationBinding.drawerOptionSubmitFeedback.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                String to = environment.getConfig().getFeedbackEmailAddress();
                String subject = getString(com.nile.kmooc.R.string.email_subject);

                String osVersionText = String.format("%s %s", getString(com.nile.kmooc.R.string.android_os_version), android.os.Build.VERSION.RELEASE);
                String appVersionText = String.format("%s %s", getString(com.nile.kmooc.R.string.app_version), BuildConfig.VERSION_NAME);
                String deviceModelText = String.format("%s %s", getString(com.nile.kmooc.R.string.android_device_model), Build.MODEL);
                String feedbackText = getString(com.nile.kmooc.R.string.insert_feedback);
                String body = osVersionText + "\n" + appVersionText + "\n" + deviceModelText + "\n\n" + feedbackText;
                EmailUtil.openEmailClient(getActivity(), to, subject, body, environment.getConfig());
            }
        });


        if (profile != null) {
            if (profile.name != null) {
                drawerNavigationBinding.nameTv.setText(profile.name);
            }
            if (profile.email != null) {
                drawerNavigationBinding.emailTv.setText(profile.email);
            }
            Map<String,CharSequence> map = new HashMap<>();
            map.put("username", profile.name);
            map.put("email", profile.email);
            drawerNavigationBinding.userInfoLayout.setContentDescription(ResourceUtil.getFormattedString(getResources(), com.nile.kmooc.R.string.navigation_header, map));
        }

        drawerNavigationBinding.logoutButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                environment.getRouter().performManualLogout(getActivity(), environment.getSegment(), environment.getNotificationDelegate());
            }
        });

        drawerNavigationBinding.tvVersionNo.setText(String.format("%s %s %s",
                getString(com.nile.kmooc.R.string.label_version), BuildConfig.VERSION_NAME, environment.getConfig().getEnvironmentDisplayName()));

        return drawerNavigationBinding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();
        uiLifecycleHelper.onResume();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        uiLifecycleHelper.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onPause() {
        super.onPause();
        uiLifecycleHelper.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        uiLifecycleHelper.onDestroy();
        if (null != getAccountCall) {
            getAccountCall.cancel();
        }
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        imageView = null;
    }

    @Override
    public void onStop() {
        super.onStop();
        uiLifecycleHelper.onStop();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        uiLifecycleHelper.onSaveInstanceState(outState);
    }

    @SuppressWarnings("unused")
    public void onEventMainThread(@NonNull ProfilePhotoUpdatedEvent event) {
        if (event.getUsername().equalsIgnoreCase(profile.username)) {
            if (null == event.getUri()) {
                Glide.with(NavigationFragment.this)
                        .load(com.nile.kmooc.R.drawable.profile_photo_placeholder)
                        .into(drawerNavigationBinding.profileImage);
            } else {
                Glide.with(NavigationFragment.this)
                        .load(event.getUri())
                        .skipMemoryCache(true) // URI is re-used in subsequent events; disable caching
                        .diskCacheStrategy(DiskCacheStrategy.NONE)
                        .into(drawerNavigationBinding.profileImage);
            }
        }
    }

    @SuppressWarnings("unused")
    public void onEventMainThread(@NonNull AccountDataLoadedEvent event) {
        final Account account = event.getAccount();
        if (account.getUsername().equalsIgnoreCase(profile.username)) {
            profileImage = account.getProfileImage();
            if (drawerNavigationBinding.profileImage != null) {
                loadProfileImage(account.getProfileImage(), drawerNavigationBinding.profileImage);
            }
        }
    }
}

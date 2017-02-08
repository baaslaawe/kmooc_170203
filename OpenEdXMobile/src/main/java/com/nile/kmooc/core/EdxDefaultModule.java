package com.nile.kmooc.core;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.inject.AbstractModule;
import com.nile.kmooc.module.notification.NotificationDelegate;

import com.nile.kmooc.authentication.LoginService;
import com.nile.kmooc.base.MainApplication;
import com.nile.kmooc.course.CourseService;
import com.nile.kmooc.discussion.DiscussionService;
import com.nile.kmooc.discussion.DiscussionTextUtils;
import com.nile.kmooc.http.Api;
import com.nile.kmooc.http.IApi;
import com.nile.kmooc.http.OkHttpUtil;
import com.nile.kmooc.http.RestApiManager;
import com.nile.kmooc.http.serialization.ISO8601DateTypeAdapter;
import com.nile.kmooc.http.serialization.JsonPageDeserializer;
import com.nile.kmooc.model.Page;
import com.nile.kmooc.module.analytics.ISegment;
import com.nile.kmooc.module.analytics.ISegmentEmptyImpl;
import com.nile.kmooc.module.analytics.ISegmentImpl;
import com.nile.kmooc.module.analytics.ISegmentTracker;
import com.nile.kmooc.module.analytics.ISegmentTrackerImpl;
import com.nile.kmooc.module.db.IDatabase;
import com.nile.kmooc.module.db.impl.IDatabaseImpl;
import com.nile.kmooc.module.download.IDownloadManager;
import com.nile.kmooc.module.download.IDownloadManagerImpl;
import com.nile.kmooc.module.notification.DummyNotificationDelegate;
import com.nile.kmooc.module.storage.IStorage;
import com.nile.kmooc.module.storage.Storage;
import com.nile.kmooc.util.AppUpdateUtils;
import com.nile.kmooc.user.UserService;
import com.nile.kmooc.util.BrowserUtil;
import com.nile.kmooc.util.Config;
import com.nile.kmooc.util.MediaConsentUtils;

import de.greenrobot.event.EventBus;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;

public class EdxDefaultModule extends AbstractModule {
    //if your module requires a context, add a constructor that will be passed a context.
    private Context context;

    //with RoboGuice 3.0, the constructor for AbstractModule will use an `Application`, not a `Context`
    public EdxDefaultModule(Context context) {
        this.context = context;
    }

    @Override
    public void configure() {
        Config config = new Config(context);

        bind(IDatabase.class).to(IDatabaseImpl.class);
        bind(IStorage.class).to(Storage.class);
        bind(ISegmentTracker.class).to(ISegmentTrackerImpl.class);
        if (config.getSegmentConfig().isEnabled()) {
            bind(ISegment.class).to(ISegmentImpl.class);
        } else {
            bind(ISegment.class).to(ISegmentEmptyImpl.class);
        }

        bind(IDownloadManager.class).to(IDownloadManagerImpl.class);

        bind(OkHttpClient.class).toInstance(OkHttpUtil.getOAuthBasedClient(context));

        if (MainApplication.RETROFIT_ENABLED) {
            bind(IApi.class).to(RestApiManager.class);
        } else {
            bind(IApi.class).to(Api.class);
        }

        bind(NotificationDelegate.class).to(DummyNotificationDelegate.class);

        bind(IEdxEnvironment.class).to(EdxEnvironment.class);

        bind(LinearLayoutManager.class).toProvider(LinearLayoutManagerProvider.class);

        bind(EventBus.class).toInstance(EventBus.getDefault());

        bind(Gson.class).toInstance(new GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .registerTypeAdapterFactory(ISO8601DateTypeAdapter.FACTORY)
                .registerTypeAdapter(Page.class, new JsonPageDeserializer())
                .serializeNulls()
                .create());

        bind(Retrofit.class).toProvider(RetrofitProvider.class);

        bind(LoginService.class).toProvider(LoginService.Provider.class);
        bind(CourseService.class).toProvider(CourseService.Provider.class);
        bind(DiscussionService.class).toProvider(DiscussionService.Provider.class);
        bind(UserService.class).toProvider(UserService.Provider.class);

        requestStaticInjection(BrowserUtil.class, MediaConsentUtils.class,
                DiscussionTextUtils.class, AppUpdateUtils.class);
    }
}

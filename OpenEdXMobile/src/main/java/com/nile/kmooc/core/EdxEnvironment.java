package com.nile.kmooc.core;


import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.nile.kmooc.module.db.IDatabase;
import com.nile.kmooc.module.download.IDownloadManager;
import com.nile.kmooc.module.notification.NotificationDelegate;
import com.nile.kmooc.module.prefs.LoginPrefs;
import com.nile.kmooc.module.prefs.UserPrefs;
import com.nile.kmooc.module.storage.IStorage;
import com.nile.kmooc.services.ServiceManager;
import com.nile.kmooc.view.Router;

import com.nile.kmooc.module.analytics.ISegment;
import com.nile.kmooc.util.Config;

import de.greenrobot.event.EventBus;

@Singleton
public class EdxEnvironment implements IEdxEnvironment {

    @Inject
    IDatabase database;

    @Inject
    IStorage storage;

    @Inject
    IDownloadManager downloadManager;

    @Inject
    UserPrefs userPrefs;

    @Inject
    LoginPrefs loginPrefs;

    @Inject
    ISegment segment;

    @Inject
    NotificationDelegate notificationDelegate;

    @Inject
    Router router;

    @Inject
    Config config;

    @Inject
    ServiceManager serviceManager;

    @Inject
    EventBus eventBus;

    @Override
    public IDatabase getDatabase() {
        return database;
    }

    @Override
    public IDownloadManager getDownloadManager() {
        return downloadManager;
    }

    @Override
    public UserPrefs getUserPrefs() {
        return userPrefs;
    }

    @Override
    public LoginPrefs getLoginPrefs() {
        return loginPrefs;
    }

    @Override
    public ISegment getSegment() {
        return segment;
    }

    @Override
    public NotificationDelegate getNotificationDelegate() {
        return notificationDelegate;
    }

    @Override
    public Router getRouter() {
        return router;
    }

    @Override
    public Config getConfig() {
        return config;
    }

    @Override
    public IStorage getStorage() {
        return storage;
    }

    @Override
    public ServiceManager getServiceManager() {
        return serviceManager;
    }

    public EventBus getEventBus() {
        return eventBus;
    }
}

package com.nile.kmooc.core;


import com.nile.kmooc.module.db.IDatabase;
import com.nile.kmooc.module.notification.NotificationDelegate;
import com.nile.kmooc.services.ServiceManager;
import com.nile.kmooc.module.analytics.ISegment;
import com.nile.kmooc.module.download.IDownloadManager;
import com.nile.kmooc.module.prefs.LoginPrefs;
import com.nile.kmooc.module.prefs.UserPrefs;
import com.nile.kmooc.module.storage.IStorage;
import com.nile.kmooc.util.Config;
import com.nile.kmooc.view.Router;

/**
 * TODO - we should decompose this class into environment setting and service provider settings.
 */
public interface IEdxEnvironment {

    IDatabase getDatabase();

    IStorage getStorage();

    IDownloadManager getDownloadManager();

    UserPrefs getUserPrefs();

    LoginPrefs getLoginPrefs();

    ISegment getSegment();

    NotificationDelegate getNotificationDelegate();

    Router getRouter();

    Config getConfig();

    ServiceManager getServiceManager();
}

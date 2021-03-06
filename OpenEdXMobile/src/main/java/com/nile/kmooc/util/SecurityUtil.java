package com.nile.kmooc.util;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;

import com.nile.kmooc.logger.Logger;
import com.nile.kmooc.module.db.DbStructure;
import com.nile.kmooc.module.prefs.PrefManager;

import java.io.File;
import java.util.Collections;

/**
 * Utility class dealing with the security of a user's personal information data.
 */
public class SecurityUtil {
    private static final Logger logger = new Logger(SecurityUtil.class);

    // Make this class non-instantiable
    private SecurityUtil() {
        throw new UnsupportedOperationException();
    }

    /**
     * Clears the app's data directory, external storage directory and shared preferences,
     * with the exceptions of downloaded videos and videos database.
     *
     * @param context The current context.
     */
    public static void clearUserData(@NonNull Context context) {
        // Clear the data directory
        PackageManager packageManager = context.getPackageManager();
        try {
            PackageInfo packageInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
            File dataDir = new File(packageInfo.applicationInfo.dataDir);
            File[] filesList = dataDir.listFiles();
            if (filesList != null) {
                for (final File child : filesList) {
                    FileUtil.deleteRecursive(child, Collections.singletonList(DbStructure.NAME));
                }
            }
        } catch (PackageManager.NameNotFoundException e) {
            // Should never happen as we've given our app's package name to getPackageInfo function.
            logger.error(e);
        }

        // Now clear the App's external storage directory
        File externalAppDir = FileUtil.getExternalAppDir(context);
        File[] filesList = externalAppDir.listFiles();
        if (filesList != null) {
            for (final File child : filesList) {
                FileUtil.deleteRecursive(child, Collections.singletonList(AppConstants.Directories.VIDEOS));
            }
        }

        // Now clear the shared preferences
        PrefManager.nukeSharedPreferences();
    }
}

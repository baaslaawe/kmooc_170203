package com.nile.kmooc.test.feature;

import android.support.test.runner.AndroidJUnit4;

import com.nile.kmooc.base.MainApplication;
import com.nile.kmooc.core.EdxEnvironment;
import org.junit.Before;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public abstract class FeatureTest {
    protected EdxEnvironment environment;

    @Before
    public void setup() {
        // Ensure we are not logged in
        final MainApplication application = MainApplication.instance();
        environment = application.getInjector().getInstance(EdxEnvironment.class);
        environment.getLoginPrefs().clear();
        environment.getSegment().resetIdentifyUser();
    }
}

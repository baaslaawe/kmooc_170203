package com.nile.kmooc.test.feature;

import com.nile.kmooc.test.feature.data.Credentials;
import com.nile.kmooc.test.feature.interactor.AppInteractor;

import org.junit.Test;

public class RegisterFeatureTest extends FeatureTest {
    @Test
    public void afterRegistering_withFreshCredentials_myCoursesScreenIsDisplayed() {
        new AppInteractor()
                .launchApp()
                .observeLandingScreen()
                .navigateToRegistrationScreen()
                .observeRegistrationScreen()
                .createAccount(Credentials.freshCredentials(environment.getConfig()))
                .observeMyCoursesScreen();
    }
}

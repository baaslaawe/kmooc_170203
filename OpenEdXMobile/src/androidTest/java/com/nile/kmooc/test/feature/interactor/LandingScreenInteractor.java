package com.nile.kmooc.test.feature.interactor;

import android.support.test.espresso.ViewInteraction;
import android.support.test.espresso.action.ViewActions;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isCompletelyDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

public class LandingScreenInteractor {

    public LandingScreenInteractor observeLandingScreen() {
        // Look for launch_screen_logo view which (we assume) is only present on the landing screen
        onView(withId(com.nile.kmooc.R.id.edx_logo)).check(matches(isCompletelyDisplayed()));
        onRegistrationView().check(matches(isCompletelyDisplayed()));
        onLogInView().check(matches(isCompletelyDisplayed()));
        return this;
    }

    public RegistrationScreenInteractor navigateToRegistrationScreen() {
        onRegistrationView().perform(ViewActions.click());
        return new RegistrationScreenInteractor();
    }

    public LogInScreenInteractor navigateToLogInScreen() {
        onLogInView().perform(ViewActions.click());
        return new LogInScreenInteractor();
    }

    private ViewInteraction onRegistrationView() {
        return onView(withText(com.nile.kmooc.R.string.sign_up_and_learn));
    }

    private ViewInteraction onLogInView() {
        return onView(withText(com.nile.kmooc.R.string.already_have_an_account));
    }
}

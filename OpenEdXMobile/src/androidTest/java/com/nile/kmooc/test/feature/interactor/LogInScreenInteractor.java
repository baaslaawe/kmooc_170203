package com.nile.kmooc.test.feature.interactor;

import android.support.test.espresso.Espresso;
import android.support.test.espresso.ViewInteraction;

import com.nile.kmooc.base.MainApplication;
import com.nile.kmooc.test.feature.data.Credentials;
import com.nile.kmooc.test.feature.matcher.ActionBarMatcher;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isCompletelyDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
import static android.support.test.espresso.matcher.ViewMatchers.withHint;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.allOf;

public class LogInScreenInteractor {

    public LogInScreenInteractor observeLogInScreen() {
        final MainApplication app = MainApplication.instance();
        final CharSequence title = app.getResources().getString(com.nile.kmooc.R.string.login_title);
        onView(allOf(ActionBarMatcher.isInActionBar(), withText(title.toString()))).check(matches(isCompletelyDisplayed()));
        onUsernameView().check(matches(isCompletelyDisplayed()));
        onPasswordView().check(matches(isCompletelyDisplayed()));
        onLogInButton().check(matches(isCompletelyDisplayed()));
        return this;
    }

    public MyCoursesScreenInteractor logIn(Credentials credentials) {
        onUsernameView().perform(replaceText(credentials.email));
        onPasswordView().perform(replaceText(credentials.password));
        onLogInButton().perform(click());
        return new MyCoursesScreenInteractor();
    }

    public LandingScreenInteractor navigateBack() {
        Espresso.pressBack();
        return new LandingScreenInteractor();
    }

    private ViewInteraction onUsernameView() {
        return onView(withHint(com.nile.kmooc.R.string.email_username));
    }

    private ViewInteraction onPasswordView() {
        return onView(withHint(com.nile.kmooc.R.string.password));
    }

    private ViewInteraction onLogInButton() {
        return onView(withContentDescription(com.nile.kmooc.R.string.login_btn));
    }
}

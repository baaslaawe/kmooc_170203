package com.nile.kmooc.test.feature.interactor;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static com.nile.kmooc.test.feature.matcher.ActionBarMatcher.isInActionBar;
import static org.hamcrest.CoreMatchers.allOf;

public class NavigationDrawerInteractor {
    public static NavigationDrawerInteractor open() {
        onView(allOf(isInActionBar(), withContentDescription(com.nile.kmooc.R.string.label_open_navigation_menu))).perform(click());
        return new NavigationDrawerInteractor();
    }

    public LogInScreenInteractor logOut() {
        onView(withText(com.nile.kmooc.R.string.logout)).perform(click());
        return new LogInScreenInteractor();
    }
}

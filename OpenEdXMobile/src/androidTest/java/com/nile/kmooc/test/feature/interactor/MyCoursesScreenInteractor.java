package com.nile.kmooc.test.feature.interactor;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isCompletelyDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static com.nile.kmooc.test.feature.matcher.ActionBarMatcher.isInActionBar;
import static org.hamcrest.CoreMatchers.allOf;

public class MyCoursesScreenInteractor {
    public MyCoursesScreenInteractor observeMyCoursesScreen() {
        // Look for "My Courses" title which (we assume) is only present on the landing screen
        onView(allOf(isInActionBar(), withText(com.nile.kmooc.R.string.label_my_courses))).check(matches(isCompletelyDisplayed()));
        return this;
    }

    public NavigationDrawerInteractor openNavigationDrawer() {
        return NavigationDrawerInteractor.open();
    }
}

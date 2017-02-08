package com.nile.kmooc.test.feature.interactor;

import android.support.test.espresso.ViewInteraction;

import com.nile.kmooc.base.MainApplication;
import com.nile.kmooc.test.feature.data.Credentials;
import com.nile.kmooc.util.Config;
import com.nile.kmooc.util.ResourceUtil;
import org.hamcrest.CoreMatchers;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isCompletelyDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withTagValue;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static com.nile.kmooc.test.feature.matcher.ActionBarMatcher.isInActionBar;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.instanceOf;

public class RegistrationScreenInteractor {
    public RegistrationScreenInteractor observeRegistrationScreen() {
        final MainApplication app = MainApplication.instance();
        final CharSequence title = ResourceUtil.getFormattedString(app.getResources(),
                com.nile.kmooc.R.string.register_title, "platform_name",
                app.getInjector().getInstance(Config.class).getPlatformName());
        onView(allOf(isInActionBar(), withText(title.toString()))).check(matches(isCompletelyDisplayed()));
        return this;
    }

    public MyCoursesScreenInteractor createAccount(Credentials credentials) {
        onEmailView().perform(typeText(credentials.email), closeSoftKeyboard());
        onNameView().perform(typeText("Test Account"), closeSoftKeyboard());
        onUsernameView().perform(typeText(credentials.username), closeSoftKeyboard());
        onPasswordView().perform(typeText(credentials.password), closeSoftKeyboard());
        onCountryView().perform(click());
        onData(allOf(is(instanceOf(String.class)))).atPosition(1).perform(click());
        onCreateAccountButton().perform(click());
        return new MyCoursesScreenInteractor();
    }

    private ViewInteraction onEmailView() {
        return onView(withTagValue(is((Object) "email")));
    }

    private ViewInteraction onNameView() {
        return onView(withTagValue(is((Object) "name")));
    }

    private ViewInteraction onUsernameView() {
        return onView(withTagValue(is((Object) "username")));
    }

    private ViewInteraction onPasswordView() {
        return onView(withTagValue(is((Object) "password")));
    }

    private ViewInteraction onCountryView() {
        return onView(withTagValue(is((Object) "country")));
    }

    private ViewInteraction onCreateAccountButton() {
        return onView(withId(com.nile.kmooc.R.id.create_account_tv));
    }
}

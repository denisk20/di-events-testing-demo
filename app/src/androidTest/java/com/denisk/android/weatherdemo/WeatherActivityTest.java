package com.denisk.android.weatherdemo;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;
import com.denisk.android.weatherdemo.managers.IPreferencesManager;
import dagger.Module;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.inject.Inject;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.doesNotExist;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

/**
 * @author denisk
 * @since 8/3/15.
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class WeatherActivityTest  {

    @Rule
    public ActivityTestRule<WeatherActivity> mActivityRule = new ActivityTestRule(WeatherActivity.class);

    @Module(
            injects = WeatherActivityTest.class,
            complete = false
    )
    static class WeatherActivityTestModule {
    }

    @Inject IPreferencesManager preferencesManager;

    @Before
    public void setUp() throws Exception {

        WeatherActivity activity = mActivityRule.getActivity();

        activity.objectGraph
                .plus(new WeatherActivityTestModule())
                .inject(this);

        preferencesManager.clear();
    }

    @Test
    public void testBaseUi() {
        onView(withText(R.string.temp)).check(matches(isDisplayed()));
        onView(withText(R.string.temp_high)).check(matches(isDisplayed()));
        onView(withText(R.string.temp_low)).check(matches(isDisplayed()));
        onView(withText(R.string.pressure)).check(matches(isDisplayed()));
        onView(withText(R.string.humidity)).check(matches(isDisplayed()));
        onView(withText(R.string.wind)).check(matches(isDisplayed()));
        onView(withText(R.string.wind_direction)).check(matches(isDisplayed()));

        onView(withId(R.id.fab)).check(matches(isDisplayed()));
        onView(withId(R.id.city_selector)).check(matches(isDisplayed()));
    }

    @Test
    public void testDropdownText() throws Exception {
        onView(withText("Lviv")).check(doesNotExist());
        onView(withId(R.id.city_selector)).perform(click());
        onView(withText("Lviv")).check(matches(isDisplayed()));
    }
}
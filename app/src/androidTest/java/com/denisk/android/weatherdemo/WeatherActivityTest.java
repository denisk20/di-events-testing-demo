package com.denisk.android.weatherdemo;

import android.test.ActivityInstrumentationTestCase2;
import com.denisk.android.weatherdemo.managers.IPreferencesManager;
import dagger.Module;

import javax.inject.Inject;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.doesNotExist;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.not;

/**
 * @author denisk
 * @since 8/3/15.
 */
public class WeatherActivityTest extends ActivityInstrumentationTestCase2<WeatherActivity> {
    public WeatherActivityTest(Class<WeatherActivity> activityClass) {
        super(activityClass);
    }

    public WeatherActivityTest() {
        this(WeatherActivity.class);
    }

    @Module(
            injects = WeatherActivityTest.class,
            complete = false
    )
    static class WeatherActivityTestModule {
    }

    @Inject IPreferencesManager preferencesManager;

    @Override
    public void setUp() throws Exception {
        super.setUp();

        WeatherActivity activity = getActivity();
        activity.objectGraph.plus(new WeatherActivityTestModule()).inject(this);

        preferencesManager.clear();
    }

    public void testProgressBarAppears() throws Exception {
        onView(withId(R.id.progress)).check(matches(not(isDisplayed())));
        onView(withId(R.id.fab)).perform(click());
        onView(withId(R.id.progress)).check(matches(isDisplayed()));
    }

    public void testDropdownText() throws Exception {
        onView(withText("Lviv")).check(doesNotExist());
        onView(withId(R.id.city_selector)).perform(click());
        onView(withText("Lviv")).check(matches(isDisplayed()));
    }
}

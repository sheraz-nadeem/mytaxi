package com.mytaxi.sheraz;


import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.IdlingRegistry;
import androidx.test.espresso.IdlingResource;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.SmallTest;
import com.mytaxi.sheraz.ui.modules.home.view.HomeActivity;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.Matchers.allOf;

@SmallTest
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentationTest {

    private IdlingResource mIdlingResource;

    /**
     * Launch the activity under test, ActivityTestRule is now deprecated
     */

    @BeforeClass
    private void launchActivity() {
        ActivityScenario<HomeActivity> activityScenario = ActivityScenario.launch(HomeActivity.class);
        activityScenario.onActivity(new ActivityScenario.ActivityAction<HomeActivity>() {
            @Override
            public void perform(HomeActivity activity) {
                mIdlingResource = activity.getIdlingResourceInTest();
                IdlingRegistry.getInstance().register(mIdlingResource);
            }
        });
    }

    @Test
    private void verifyDataLoaded() {

        onView(withId(R.id.rvTaxiList)).check(matches(isDisplayed()));

        onData(allOf()).inAdapterView(withId(R.id.rvTaxiList)).atPosition(5)
                .perform(click());
    }

    @After
    private void unregisterIdlingResource() {
        if (mIdlingResource != null) {
            IdlingRegistry.getInstance().unregister(mIdlingResource);
        }
    }
}

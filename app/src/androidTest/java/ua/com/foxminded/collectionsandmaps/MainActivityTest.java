package ua.com.foxminded.collectionsandmaps;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.swipeLeft;
import static androidx.test.espresso.action.ViewActions.swipeRight;
import static androidx.test.espresso.matcher.ViewMatchers.assertThat;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.notNullValue;

@RunWith(AndroidJUnit4ClassRunner.class)
public class MainActivityTest {

    @Rule
    public ActivityScenarioRule<MainActivity> activityRule =
            new ActivityScenarioRule<>(MainActivity.class);
    //TODO: ActivityScenarioRule does not launch the activity

    @Test
    public void testTabsAreClickable() {
        onView(withText("MAPS")).perform(click());
        onView(withText("COLLECTIONS")).perform(click());
        //TODO: Compare current fragment args?
    }

    @Test
    public void testFragmentsAreSwiped() {
        onView(withId(R.id.viewPager)).perform(swipeLeft()).perform(swipeRight());
        //TODO: Compare current fragment args?
    }
}
package ua.com.foxminded.collectionsandmaps.ui;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.swipeLeft;
import static androidx.test.espresso.action.ViewActions.swipeRight;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import ua.com.foxminded.collectionsandmaps.R;

@RunWith(AndroidJUnit4ClassRunner.class)
public class MainActivityTest {

    @Rule
    public ActivityScenarioRule<MainActivity> scenarioRule = new ActivityScenarioRule<>(MainActivity.class);

    @Test
    public void testTabsAreClickable() {
        onView(withText("MAPS")).perform(click());
        onView(withText("COLLECTIONS")).perform(click());
    }

    @Test
    public void testFragmentsAreSwiped() {
        onView(withId(R.id.viewPager)).perform(swipeLeft()).perform(swipeRight());
    }
}
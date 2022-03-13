package ua.com.foxminded.collectionsandmaps.ui.benchmark;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.swipeUp;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isClickable;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import androidx.test.ext.junit.rules.ActivityScenarioRule;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import ua.com.foxminded.collectionsandmaps.CustomItemMatchers;
import ua.com.foxminded.collectionsandmaps.R;
import ua.com.foxminded.collectionsandmaps.models.DaggerTestComponent;
import ua.com.foxminded.collectionsandmaps.ui.App;
import ua.com.foxminded.collectionsandmaps.ui.MainActivity;

public class FragmentTest {

    @Rule
    public ActivityScenarioRule<MainActivity> scenarioRule = new ActivityScenarioRule<>(MainActivity.class);

    @Before
    public void setUp() {
        App.setAppComponent(DaggerTestComponent.create());
    }

    @Test
    public void testButton() {
        onView(withId(R.id.startButton))
                .check(matches(isClickable()))
                .check(matches(withText("START")));
    }

    @Test
    public void testTextInputLayoutError() {
        onView(withId(R.id.startButton)).perform(click());
        onView(withId(R.id.textInputLayoutOperations))
                .check(matches(CustomItemMatchers.hasTextInputLayoutErrorText("Invalid input")));
    }

    @Test
    public void testInitCalculations() {
        onView(withId(R.id.textInputEditTextOperations)).perform(typeText("1000000"));
        onView(withId(R.id.startButton)).check(matches(withText("START")));
        onView(withId(R.id.startButton)).perform(click());
        onView(withId(R.id.viewPager)).perform(swipeUp());
//        onView(withText(R.string.endingCalc))
//                .inRoot(new CustomItemMatchers.ToastMatcher())
//                .check(matches(isDisplayed()))
//                .check(matches(withText("Calculations ended")));
    }

    @Test
    public void testStopCalculations() {
        onView(withId(R.id.textInputEditTextOperations)).perform(typeText("1000000"));
        onView(withId(R.id.startButton)).perform(click());
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        onView(withId(R.id.startButton)).check(matches(withText("STOP")));
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        onView(withId(R.id.startButton)).perform(click());
        onView(withId(R.id.startButton)).check(matches(withText("START")));
        onView(withText(R.string.stopCalc))
                .inRoot(new CustomItemMatchers.ToastMatcher())
                .check(matches(isDisplayed()))
                .check(matches(withText("Calculations stopped")));
    }
}

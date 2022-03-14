package ua.com.foxminded.collectionsandmaps.ui.benchmark;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.swipeUp;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.contrib.RecyclerViewActions.scrollToPosition;
import static androidx.test.espresso.matcher.ViewMatchers.hasDescendant;
import static androidx.test.espresso.matcher.ViewMatchers.isClickable;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.not;

import java.util.concurrent.atomic.AtomicReference;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
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

    @Test
    public void testValidItems() {
        final AtomicReference<RecyclerView> recyclerView = new AtomicReference<>();
        scenarioRule.getScenario().onActivity(activity ->
                recyclerView.set(activity.findViewById(R.id.recyclerView)));
        final int count = recyclerView.get().getAdapter().getItemCount();
        final GridLayoutManager layoutManager = (GridLayoutManager) recyclerView.get().getLayoutManager();

        for (int i = 0; i < count; i++) {
            checkLastCompletelyVisibleItem(i, layoutManager);
            onView(withId(R.id.recyclerView))
                    .check(matches(CustomItemMatchers.atRecyclerViewPosition(
                            i, hasDescendant(allOf(withId(R.id.calcTime), withText("N/A ms")))
                    )))
                    .check(matches(CustomItemMatchers.atRecyclerViewPosition(
                            i, hasDescendant(allOf(withId(R.id.progressBar), not(isDisplayed())))
                    )));
        }

        onView(withId(R.id.recyclerView)).perform(scrollToPosition(0));
        onView(withId(R.id.textInputEditTextOperations)).perform(typeText("1000000"));
        onView(withId(R.id.startButton)).perform(click());

        for (int i = 0; i < count; i++) {
            checkLastCompletelyVisibleItem(i, layoutManager);
            onView(withId(R.id.recyclerView))
                    .check(matches(CustomItemMatchers.atRecyclerViewPosition(
                            i, hasDescendant(allOf(withId(R.id.progressBar), isDisplayed()))
                    )));
        }

        onView(withId(R.id.recyclerView)).perform(scrollToPosition(0));
        try {
            Thread.sleep(count * 1500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        for (int i = 0; i < count; i++) {
            checkLastCompletelyVisibleItem(i, layoutManager);
            onView(withId(R.id.recyclerView))
                    .check(matches(CustomItemMatchers.atRecyclerViewPosition(
                            i, hasDescendant(allOf(withId(R.id.calcTime), withText("1000 ms")))
                    )))
                    .check(matches(CustomItemMatchers.atRecyclerViewPosition(
                            i, hasDescendant(allOf(withId(R.id.progressBar), not(isDisplayed())))
                    )));
        }
    }

    private void checkLastCompletelyVisibleItem(int i, GridLayoutManager layoutManager) {
        if (i >= layoutManager.findLastCompletelyVisibleItemPosition()) {
            onView(withId(R.id.viewPager)).perform(swipeUp());
        }
    }
}

package ua.com.foxminded.collectionsandmaps;

import android.os.Bundle;

import androidx.fragment.app.FragmentFactory;
import androidx.fragment.app.testing.FragmentScenario;
import androidx.test.espresso.IdlingRegistry;
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isClickable;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4ClassRunner.class)
public class CollectionsFragmentTest {

    //TODO: try to correctly implement FragmentScenario. Again

    @Before
    public void setUp() {
        IdlingRegistry.getInstance().register(EspressoIdlingResource.getIdlingResource());
        Bundle bundle = new Bundle();
        bundle.putInt("arg_type", 0);
        FragmentScenario<CollectionsFragment> fragmentScenario = FragmentScenario.launchInContainer(
                CollectionsFragment.class,
                bundle,
                new FragmentFactory()
        );
    }

    @After
    public void tearDown() {
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.getIdlingResource());
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
        onView(withId(R.id.textInputEditTextOperations)).perform(typeText("3000000"));
        onView(withId(R.id.startButton)).perform(click());
        onView(withId(R.id.startButton)).check(matches(withText("STOP")));
        onView(withId(R.id.startButton)).check(matches(withText("START")));
        //TODO: implement IdlingResource correctly
        /*onView(withText(R.string.startingCalc))
                .inRoot(new CustomItemMatchers.ToastMatcher())
                .check(matches(isDisplayed()))
                .check(matches(withText("Starting calculations")));
        onView(withText(R.string.endingCalc))
                .inRoot(new CustomItemMatchers.ToastMatcher())
                .check(matches(isDisplayed()))
                .check(matches(withText("Calculations ended")));*/
    }
}
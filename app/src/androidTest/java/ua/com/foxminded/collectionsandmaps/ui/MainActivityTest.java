package ua.com.foxminded.collectionsandmaps.ui;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.swipeLeft;
import static androidx.test.espresso.action.ViewActions.swipeRight;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.junit.Assert.assertEquals;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import ua.com.foxminded.collectionsandmaps.R;

@RunWith(AndroidJUnit4ClassRunner.class)
public class MainActivityTest {

    @Rule
    public ActivityScenarioRule<MainActivity> scenarioRule = new ActivityScenarioRule<>(MainActivity.class);

    private void checkSelection(int position) {
        scenarioRule.getScenario().onActivity(activity -> {
            final TabLayout tabLayout = activity.findViewById(R.id.tabLayout);
            final ViewPager2 viewPager2 = activity.findViewById(R.id.viewPager);
            assertEquals(position, tabLayout.getSelectedTabPosition());
            assertEquals(position, viewPager2.getCurrentItem());
        });
    }

    @Test
    public void testTabsAreClickable() {
        onView(withText("MAPS")).perform(click());
        checkSelection(1);
        onView(withText("COLLECTIONS")).perform(click());
        checkSelection(0);
    }

    @Test
    public void testFragmentsAreSwiped() {
        onView(withId(R.id.viewPager)).perform(swipeLeft());
        checkSelection(1);
        onView(withId(R.id.viewPager)).perform(swipeRight());
        checkSelection(0);
    }
}

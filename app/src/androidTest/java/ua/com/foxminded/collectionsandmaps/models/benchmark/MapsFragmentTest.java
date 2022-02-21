package ua.com.foxminded.collectionsandmaps.models.benchmark;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner;
import androidx.viewpager2.widget.ViewPager2;

import org.junit.Before;
import org.junit.Rule;
import org.junit.runner.RunWith;

import ua.com.foxminded.collectionsandmaps.R;
import ua.com.foxminded.collectionsandmaps.ui.MainActivity;

@RunWith(AndroidJUnit4ClassRunner.class)
public class MapsFragmentTest extends CollectionsFragmentTest {

    @Rule
    public ActivityScenarioRule<MainActivity> scenarioRule = new ActivityScenarioRule<>(MainActivity.class);

    @Before
    public void setUp() {
        scenarioRule.getScenario().onActivity(activity -> {
            ViewPager2 viewPager = activity.findViewById(R.id.viewPager);
            viewPager.setCurrentItem(1, false);
        });
    }

    @Override
    public void testButton() {
        super.testButton();
    }

    @Override
    public void testTextInputLayoutError() {
        super.testTextInputLayoutError();
    }

    @Override
    public void testInitCalculations() {
        super.testInitCalculations();
    }

    @Override
    public void testStopCalculations() {
        super.testStopCalculations();
    }
}
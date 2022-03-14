package ua.com.foxminded.collectionsandmaps.ui.benchmark;

import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner;
import androidx.viewpager2.widget.ViewPager2;

import org.junit.Before;
import org.junit.runner.RunWith;

import ua.com.foxminded.collectionsandmaps.R;

@RunWith(AndroidJUnit4ClassRunner.class)
public class MapsFragmentTest extends FragmentTest {

    @Before
    public void setUp() {
        super.setUp();
        scenarioRule.getScenario().onActivity(activity -> {
            final ViewPager2 viewPager = activity.findViewById(R.id.viewPager);
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

    @Override
    public void testValidItems() {
        super.testValidItems();
    }
}

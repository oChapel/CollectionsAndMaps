package ua.com.foxminded.collectionsandmaps;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import ua.com.foxminded.collectionsandmaps.ui.MainActivityTest;
import ua.com.foxminded.collectionsandmaps.ui.benchmark.MapsFragmentTest;
import ua.com.foxminded.collectionsandmaps.ui.benchmark.CollectionsFragmentTest;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        MainActivityTest.class,
        CollectionsFragmentTest.class,
        MapsFragmentTest.class
})
public class SuiteAndroidTestClass {
}

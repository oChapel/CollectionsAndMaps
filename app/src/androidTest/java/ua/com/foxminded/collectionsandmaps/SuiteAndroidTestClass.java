package ua.com.foxminded.collectionsandmaps;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import ua.com.foxminded.collectionsandmaps.models.benchmark.CollectionsFragmentTest;
import ua.com.foxminded.collectionsandmaps.models.benchmark.MapsFragmentTest;
import ua.com.foxminded.collectionsandmaps.ui.benchmark.MainActivityTest;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        MainActivityTest.class,
        CollectionsFragmentTest.class,
        MapsFragmentTest.class
})
public class SuiteAndroidTestClass {
}

package ua.com.foxminded.collectionsandmaps;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import ua.com.foxminded.collectionsandmaps.ui.CollectionsFragmentTest;
import ua.com.foxminded.collectionsandmaps.ui.MainActivityTest;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        MainActivityTest.class,
        CollectionsFragmentTest.class
})
public class SuiteAndroidTestClass {
}
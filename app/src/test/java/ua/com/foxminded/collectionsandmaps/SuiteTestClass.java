package ua.com.foxminded.collectionsandmaps;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        CollectionsBenchmarkTest.class,
        MapsBenchmarkTest.class,
        CollectionsViewModelTest.class
})
public class SuiteTestClass {
}
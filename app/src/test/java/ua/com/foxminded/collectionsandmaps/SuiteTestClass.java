package ua.com.foxminded.collectionsandmaps;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import ua.com.foxminded.collectionsandmaps.models.benchmark.CollectionsBenchmarkTest;
import ua.com.foxminded.collectionsandmaps.models.benchmark.MapsBenchmarkTest;
import ua.com.foxminded.collectionsandmaps.ui.benchmark.CollectionsViewModelTest;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        CollectionsBenchmarkTest.class,
        MapsBenchmarkTest.class,
        CollectionsViewModelTest.class
})
public class SuiteTestClass {
}

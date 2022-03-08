package ua.com.foxminded.collectionsandmaps.ui;

import android.app.Application;

import ua.com.foxminded.collectionsandmaps.models.BenchmarkComponent;

public class App extends Application {

    private static BenchmarkComponent benchmarkComponent;

    public static void setAppComponent(BenchmarkComponent component) {
        App.benchmarkComponent = component;
    }

    public static BenchmarkComponent getComponent() {
        return benchmarkComponent;
    }
}

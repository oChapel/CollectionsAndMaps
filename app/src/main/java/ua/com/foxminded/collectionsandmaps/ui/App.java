package ua.com.foxminded.collectionsandmaps.ui;

import android.app.Application;

public class App extends Application {

    private static int componentInt;

    public static int getComponentInt() {
        return componentInt;
    }

    public static void setComponentInt(int componentInt) {
        App.componentInt = componentInt;
    }
}

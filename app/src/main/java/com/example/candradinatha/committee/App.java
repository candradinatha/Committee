package com.example.candradinatha.committee;

import android.app.Application;

public class App extends Application {
    private static App instance = null;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }

    public static App getInstance() {
        return instance;
    }
}

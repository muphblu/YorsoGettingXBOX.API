package ru.innopolis.yorsogettingxbox;

import android.app.Application;

import timber.log.Timber;

public class MainApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        }
    }
}

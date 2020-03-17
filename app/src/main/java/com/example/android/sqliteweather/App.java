package com.example.android.sqliteweather;

import android.app.Application;
import android.content.SharedPreferences;

import com.example.android.sqliteweather.data.room.AppDatabase;
import com.example.android.sqliteweather.data.room.ViewedLocation;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import androidx.preference.PreferenceManager;
import androidx.room.Room;

public class App extends Application {
    private AppDatabase db;

    /** It is not allowed to execute Room database operations on the main thread,
     * and {@link android.os.AsyncTask} is deprecated,
     * so this field stores an {@link Executor} which contains a special thread
     * dedicated to executing database operations. */
    private Executor dbExecutor;

    /** We must store the listener in a field, otherwise {@link SharedPreferences} loses it. */
    private SharedPreferences.OnSharedPreferenceChangeListener prefListener;

    /**
     * Insert {@code locationName} into the history of locations viewed by the user.
     * Run this method in any thread.
     * @param locationName
     */
    private void insertViewedLocation(final String locationName) {
        /* run in the dedicated thread */
        dbExecutor.execute(new Runnable() {
            @Override
            public void run() {
                db.viewedLocationDao().insert(new ViewedLocation(locationName));
            }
        });
    }

    @Override
    public void onCreate() {
        super.onCreate();
        db = Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, "main").build();
        dbExecutor = Executors.newSingleThreadExecutor();
        PreferenceManager.setDefaultValues(this, R.xml.prefs, false);
        final SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        /** After the application is installed, the database is empty.
         * Insert the location name from the preferences into the database. */
        final String locationName = prefs.getString(
                getString(R.string.pref_location_key), null);
        if (locationName != null) {
            insertViewedLocation(locationName);
        }
        prefListener = new SharedPreferences.OnSharedPreferenceChangeListener() {
            @Override
            public void onSharedPreferenceChanged(SharedPreferences prefs, String key) {
                if (key.equals(getString(R.string.pref_location_key))) {
                    final String locationName = prefs.getString(key, null);
                    if (locationName != null) {
                        insertViewedLocation(locationName);
                    }
                }
            }
        };
        prefs.registerOnSharedPreferenceChangeListener(prefListener);
    }

    public AppDatabase getDb() {
        return db;
    }
}

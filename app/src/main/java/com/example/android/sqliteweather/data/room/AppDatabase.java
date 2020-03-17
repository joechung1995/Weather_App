package com.example.android.sqliteweather.data.room;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {ViewedLocation.class},
        version = 1,
        exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    public abstract ViewedLocationDao viewedLocationDao();
}

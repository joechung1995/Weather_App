package com.example.android.sqliteweather.data.room;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

@Dao
public interface ViewedLocationDao {
    @Query("select * from viewed_location order by name")
    LiveData<List<ViewedLocation>> getAll();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(ViewedLocation viewedLocation);
}

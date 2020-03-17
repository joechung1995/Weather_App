package com.example.android.sqliteweather.data.room;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "viewed_location",
        indices = {@Index(value = {"name"}, unique = true)})
public class ViewedLocation {
    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name = "name")
    public String name;

    public ViewedLocation(String name) {
        this.name = name;
    }
}

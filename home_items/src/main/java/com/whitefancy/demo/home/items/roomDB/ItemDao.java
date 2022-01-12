package com.whitefancy.demo.home.items.roomDB;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface ItemDao {
    @Query("select distinct type from item")
    List<String> getTypes();

    @Query("select distinct place from item")
    List<String> getPlaces();

    @Query("select * from item order by create_date+deadline desc")
    List<Item> getAll();

    @Query("select * from item where place in (:places)")
    List<Item> loadAllByPlaces(String[] places);

    @Query("select * from item where type in (:types)")
    List<Item> loadAllByTypes(String[] types);

    @Insert
    void insertAll(Item... items);

    @Delete
    void delete(Item user);
}

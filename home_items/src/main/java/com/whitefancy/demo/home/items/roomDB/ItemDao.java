package com.whitefancy.demo.home.items.roomDB;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface ItemDao {
    @Query("select distinct type from item")
    List<String> getTypes();

    @Query("select distinct place from item")
    List<String> getPlaces();

    @Query("select img_url from item")
    List<String> getImages();

    @Query("select * from item order by create_date+deadline desc")
    List<Item> getAll();

    @Query("select * from item where place in (:places)")
    List<Item> loadAllByPlaces(String[] places);

    @Query("select * from item where type in (:types)")
    List<Item> loadAllByTypes(String[] types);

    @Query("select type,count(*) as count from item group by type")
    List<Summary> loadSummary();

    @Insert(onConflict = OnConflictStrategy.REPLACE)

        //如果您只想将整个对象替换为新对象，则只需指定OnConflictStrategy
    void insertAll(Item... items);


    @Update
    void updateAll(Item... items);


    @Delete
    void delete(Item user);
}

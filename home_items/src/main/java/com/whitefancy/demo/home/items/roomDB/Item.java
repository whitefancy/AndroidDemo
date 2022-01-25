package com.whitefancy.demo.home.items.roomDB;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;


@Entity
public class Item {
    @PrimaryKey(autoGenerate = true)
    public int id;
    public String name;
    public String type;
    public Integer lifetime;
    public String place;
    @ColumnInfo(name = "create_ts")
    public Integer createTs;
    @ColumnInfo(name = "img_url")
    public String imgUrl;

}

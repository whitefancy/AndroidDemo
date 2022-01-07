package com.whitefancy.demo.home.items.roomDB;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;


@Entity
public class Item {
    @PrimaryKey(autoGenerate = true)
    public int id;
    public String name;
    public String type;
    public Integer deadline;
    public String place;
    @ColumnInfo(name = "create_date")
    public Date createDate;
    @ColumnInfo(name = "img_url")
    public String imgUrl;

}

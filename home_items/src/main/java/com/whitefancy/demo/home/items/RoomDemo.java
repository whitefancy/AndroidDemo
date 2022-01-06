package com.whitefancy.demo.home.items;

public class RoomDemo {


}
//Room 包含 3 个主要组件：
//
//数据库：包含数据库持有者，并作为应用已保留的持久关系型数据的底层连接的主要接入点。
//
//使用 @Database 注释的类应满足以下条件：
//
//是扩展 RoomDatabase 的抽象类。
//在注释中添加与数据库关联的实体列表。
//包含具有 0 个参数且返回使用 @Dao 注释的类的抽象方法。
//在运行时，您可以通过调用 Room.databaseBuilder() 或 Room.inMemoryDatabaseBuilder() 获取 Database 的实例。
//
//Entity：表示数据库中的表。
//
//DAO：包含用于访问数据库的方法。
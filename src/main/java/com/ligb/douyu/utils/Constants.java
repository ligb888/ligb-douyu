package com.ligb.douyu.utils;

public interface Constants {

    interface Mongo {
        Boolean USE_MONGO = false;        // 是否使用mongo
        String CONNECT_STRING = "mongodb://localhost:27017";
        String DATABASE = "bullet_screen";  // mongo数据库名称
        String COLLECTION = "collection_name";     // 集合名称
    }

    interface BulletScreenReceive {
        String GROUP_ID = "-9999";   // 弹幕分组，海量弹幕模式
        int LEVEL_LIMIT = 1;         // 忽略几级以下的弹幕
    }
}

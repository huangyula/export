package com.obm.hy.export.helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {
    //数据库的版本
    public  static int DB_VERSION = 1;
    //数据库名
    public  static String DB_NAME = "";
    
    private Context mContext;
    
    //我们直接用super调用父类的构造方法，这样我们在实例化DBHelper的时候只需要传入一个上下文参数就可以了
    public DBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        this.mContext = context;
    }
    //数据库不存在的时候，调用这个方法
    @Override
    public void onCreate(SQLiteDatabase db) {
//        createTables(db,0,0);
    }


    //版本号发生变化的时候，调用这个方法
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
    
}
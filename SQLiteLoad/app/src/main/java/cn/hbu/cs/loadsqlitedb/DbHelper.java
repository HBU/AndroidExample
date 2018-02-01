package cn.hbu.cs.loadsqlitedb;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by David on 2018/2/1.
 */

public class DbHelper extends SQLiteOpenHelper{
    /**
     *
     * @param context 上下文
     * @param name 数据库的名字
     * @param factory 数据库工厂，null
     * @param version  数据库的版本
     */
    public DbHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
    }
    public Cursor onQuery(SQLiteDatabase db, int id) {          //通过id查询学生
        Cursor cursor;
        String selectQuery =  "SELECT "  +  City.KEY_PROVINCE +
                " FROM " + City.TABLE + " where "+ City.KEY_ID + " = "+id;
        Log.d("test",selectQuery);
        cursor = db.rawQuery(selectQuery,null);
        return cursor;
    }
    public Cursor onQueryProvince(SQLiteDatabase db) {          //查询省份
        Cursor cursor;
        String selectQuery =  "SELECT "  +  City.KEY_PROVINCE +
                " FROM " + City.TABLE ;
        cursor = db.rawQuery(selectQuery,null);
        return cursor;
    }
    public Cursor QueryByProvince(SQLiteDatabase db, String province) {     //通过省查询城市
        Cursor cursor=null;
        String selectQuery =  "SELECT " +  City.KEY_CITY + "," + City.KEY_CODE+
                " FROM " + City.TABLE + " where "+ City.KEY_PROVINCE + " = "+"\""+province+"\"";
        cursor = db.rawQuery(selectQuery, null);

        return cursor;
    }
    public Cursor QueryByCode(SQLiteDatabase db,String code){            //通过城市代码查询城市和省份
        Cursor cursor=null;
        String selectQuery =  "SELECT " +  City.KEY_PROVINCE + "," + City.KEY_CITY +
                " FROM " + City.TABLE + " where "+ City.KEY_CODE + " = "+"\""+code+"\"";
        cursor = db.rawQuery(selectQuery, null);
        return cursor;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

}

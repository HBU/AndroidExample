package cn.hbu.cs.loadsqlitedb;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

/**
 * Created by David on 2018/2/1.
 */

public class DbManager {
    public static final String DB_NAME = "city.db"; //数据库名字
    public static final String PACKAGE_NAME ="cn.hbu.cs.loadsqlitedb";//包名
    public static final String DB_PATH = "/data" + Environment.getDataDirectory().getAbsolutePath() + "/" + PACKAGE_NAME;   //数据库的绝对路径( /data/data/com.*.*(package name))
    private SQLiteDatabase db;
    private Context context;

    public DbManager(Context context) {
        this.context = context;
    }

    //对外提供的打开数据库接口
    public void openDataBase() {
        this.db = this.openDataBase(DB_PATH + "/databases");
    }

    //获取打开后的数据库
    public SQLiteDatabase getDb() {
        return this.db;
    }

    // 本地打开数据方法
    private SQLiteDatabase openDataBase(String filePath) {
        try {
            File file = new File(filePath);
            if (!file.exists()) { //判断文件是否存在
                //通过输入流和输出流，把数据库拷贝到"filePath"下
                file.mkdir();
                File file2=new File(filePath+"/"+DB_NAME);
                if (!file2.exists()) {
                    InputStream is = context.getResources().openRawResource(R.raw.city);//获取输入流，使用R.raw.test资源
                    FileOutputStream fos = new FileOutputStream(file2);
                    byte[] buffer = new byte[1024];
                    int readCount;
                    while ((readCount = is.read(buffer)) > 0) {
                        fos.write(buffer, 0, readCount);
                    }
                    fos.close();
                    is.close();
                }
            }
//打开数据库
            SQLiteDatabase db =new DbHelper(context,"city.db",null,2).getWritableDatabase();
            return db;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    //关闭数据库
    public  void closeDataBase()
    {
        if(this.db!=null)
            db.close();
    }
}

package com.simonluo.daidai_weather.utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;
import android.util.Log;

import com.simonluo.daidai_weather.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * 城市数据库管理类
 * Created by 333 on 2016/4/14.
 */
public class CityDBManager {
    private static String TAG = CityDBManager.class.getSimpleName();
    private final int BUFFER_SIZE = 400000;
    public static final String DB_NAME = "china_city.db";
    public static final String PACKAGE_NAME = "com.simonluo.daidai_weather";
    //在手机里存放数据库的位置(/data/data/com.simonluo.daidai_weathe/china_city.db)
    public static final String DB_PATH = "/data" + Environment.getDataDirectory().getAbsolutePath() + "/" + PACKAGE_NAME;
    private SQLiteDatabase database;
    private Context context;

    public CityDBManager(Context context) {
        this.context = context;
    }

    public SQLiteDatabase getDatabase(){
        return database;
    }

    public void setDatabase(SQLiteDatabase database){
        this.database = database;
    }

    public void openDatabase() {
        Log.e(TAG, DB_PATH + "/" + DB_NAME);
        this.database = this.openDatabase(DB_PATH + "/" + DB_NAME);
    }

    protected SQLiteDatabase openDatabase(String dbFile){
        try{
            if (!(new File(dbFile).exists())){
                InputStream is = this.context.getResources().openRawResource(R.raw.china_city);
                FileOutputStream fos = new FileOutputStream(dbFile);
                byte[] buffer = new byte[BUFFER_SIZE];
                int count = 0;
                while ((count = is.read(buffer)) > 0){
                    fos.write(buffer, 0, count);
                }
                fos.close();
                is.close();
            }
            SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(dbFile, null);
            return db;
        }catch (FileNotFoundException e){
            Log.e("Database", "File not found");
            e.printStackTrace();
        } catch (IOException e) {
            Log.e("Database", "IO exception");
            e.printStackTrace();
        }
        return null;
    }

    public void closeDatabase(){
        this.database.close();
    }
}

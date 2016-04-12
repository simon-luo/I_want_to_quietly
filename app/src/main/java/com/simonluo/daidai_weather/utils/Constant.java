package com.simonluo.daidai_weather.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.simonluo.daidai_weather.base.BaseApplication;

/**
 * Created by 333 on 2016/4/11.
 */
public class Constant {
    public static final String CHANGE_ICONS = "change_icons"; //切换图标
    public static final String CLEAR_CACHE = "clear_cache"; //清空缓存
    public static final String AUTO_UPDATE = "auto_update"; //自动更新时长
    public static final String CITY_NAME = "city_name"; //选择城市
    public static final String HOUR = "hour"; //当前小时

    public static final String KEY = "04348d762488444ca65c84950eaf58d0";// 和风天气 key

    public static int ONE_HOUR = 3600;

    private static Constant sInstance;

    private SharedPreferences mPrefs;

    public static Constant getInstance(){
        if (sInstance == null){
            sInstance = new Constant(BaseApplication.mContext);
        }
        return sInstance;
    }

    private Constant(Context context){
        mPrefs = context.getSharedPreferences("constant", Context.MODE_PRIVATE);
    }

    public Constant putBoolean(String key, boolean value) {
        mPrefs.edit().putBoolean(key, value).apply();
        return this;
    }


    public boolean getBoolean(String key, boolean def) {
        return mPrefs.getBoolean(key, def);
    }


    public Constant putInt(String key, int value) {
        mPrefs.edit().putInt(key, value).apply();
        return this;
    }


    public int getInt(String key, int defValue) {
        return mPrefs.getInt(key, defValue);
    }


    public Constant putString(String key, String value) {
        mPrefs.edit().putString(key, value).apply();
        return this;
    }


    public String getString(String key, String defValue) {
        return mPrefs.getString(key, defValue);
    }
}

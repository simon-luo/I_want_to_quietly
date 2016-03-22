package com.simonluo.daidai_weather.base;

import android.app.Application;
import android.content.Context;

/**
 * Created by 333 on 2016/3/22.
 */
public class BaseApplication extends Application {
    public static String cacheDir = "";
    public static Context mContext = null;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();

    }
}

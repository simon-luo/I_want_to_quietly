package com.simonluo.daidai_weather.base;

import android.app.Application;
import android.content.Context;
import android.os.Environment;
import android.util.Log;

import com.simonluo.daidai_weather.utils.CrashHandler;
import com.simonluo.daidai_weather.utils.RetrofitSingleton;

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
        RetrofitSingleton.init(mContext);
        CrashHandler.init(new CrashHandler(mContext));

        try {
            if (mContext.getExternalCacheDir() != null && ExistSDCard()){
                cacheDir = mContext.getExternalCacheDir().toString();
                Log.d("SDCard",cacheDir);
            }else {
                cacheDir = mContext.getCacheDir().toString();
            }
        }catch (Exception e){
            cacheDir = mContext.getCacheDir().toString();
        }

    }

    private boolean ExistSDCard() {
        if (android.os.Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
            return true;
        }else {
            return false;
        }
    }
}

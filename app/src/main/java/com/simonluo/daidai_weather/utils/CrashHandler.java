package com.simonluo.daidai_weather.utils;

import android.content.Context;

/**
 * Created by 333 on 2016/4/1.
 */
public class CrashHandler implements Thread.UncaughtExceptionHandler{
    private static Thread.UncaughtExceptionHandler defaultHandler = null;
    private Context context = null;
    private final String TAG = CrashHandler.class.getSimpleName();

    public CrashHandler(Context context) {
        this.context = context;
    }

    /**
     * 初始化，设置该CrashHandler为程序的默认处理器
     * @param crashHandler
     */
    public static void init(CrashHandler crashHandler){
        defaultHandler = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(crashHandler);
    }

    @Override
    public void uncaughtException(Thread thread, Throwable ex) {

    }
}

package com.simonluo.daidai_weather.utils;

import android.util.Log;

import com.simonluo.daidai_weather.base.BaseApplication;

/**
 * Created by 333 on 2016/4/1.
 */
public class WLog {
    public static final String PATH = BaseApplication.cacheDir + "/Log";
    public static final String LOG_FILE_NAME = "log.txt";

    /**
     * 是否写入日志文件
     */
    public static final boolean LOG_WRITE_TO_FILE = true;

    /**
     * 错误信息
     * @param TAG
     * @param msg
     */
    public static final void e(String TAG, String msg){
        Log.e(TAG, msg);
        if (LOG_WRITE_TO_FILE){
            writeToFile("e", TAG, msg);
        }
    }

    /**
     * 警告信息
     * @param TAG
     * @param msg
     */
    public final static void w(String TAG, String msg) {
        Log.w(TAG, msg);
        if (LOG_WRITE_TO_FILE)
            writeToFile("w", TAG, msg);
    }

    /**
     * 调试信息
     * @param TAG
     * @param msg
     */
    public final static void d(String TAG, String msg) {
        Log.d(TAG, msg);
        if (LOG_WRITE_TO_FILE)
            writeToFile("d", TAG, msg);
    }

    /**
     * 提示信息
     * @param TAG
     * @param msg
     */
    public final static void i(String TAG, String msg) {
        Log.i(TAG, msg);
        if (LOG_WRITE_TO_FILE)
            writeToFile("i", TAG, msg);
    }

    private static void writeToFile(String logType, String tag, String msg) {
//        isExist(PATH);
//        String needWriteMessage = "\r\n"
//                +
    }
}

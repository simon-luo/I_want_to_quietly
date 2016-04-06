package com.simonluo.daidai_weather.utils;

import android.util.Log;

import com.simonluo.daidai_weather.base.BaseApplication;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

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

    /**
     * 将日志写入到文件中
     * @param logType
     * @param tag
     * @param msg
     */
    private static void writeToFile(String logType, String tag, String msg) {
        isExist(PATH);
        String needWriteMessage = "\r\n"
                + Time.getNowMDHMSTime()
                + "\r\n"
                + logType
                + "    "
                + tag
                + "\r\n"
                + msg;
        File file = new File(PATH, LOG_FILE_NAME);
        try {
            FileWriter fileWriter = new FileWriter(file, true);
            BufferedWriter bufWriter = new BufferedWriter(fileWriter);
            bufWriter.write(needWriteMessage);
            bufWriter.newLine();
            bufWriter.close();
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 删除日志文件
     */
    public static void delFile(){
        File file = new File(PATH, LOG_FILE_NAME);
        if (file.exists()){
            file.delete();
        }
    }

    /**
     * 判断文件夹是否存在，如果不存在则创建文件夹
     * @param path
     */
    public static void isExist(String path){
        File file = new File(path);
        if (!file.exists()){
            file.mkdirs();
        }
    }
}

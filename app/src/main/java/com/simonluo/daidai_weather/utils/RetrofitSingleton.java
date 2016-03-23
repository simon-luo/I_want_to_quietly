package com.simonluo.daidai_weather.utils;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.simonluo.daidai_weather.interf.ApiInterface;
import com.squareup.okhttp.OkHttpClient;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import retrofit.GsonConverterFactory;
import retrofit.Retrofit;

/**
 * Created by 333 on 2016/3/22.
 */
public class RetrofitSingleton {
    private static ApiInterface apiService = null;

    private static Retrofit retrofit = null;

    private static OkHttpClient okHttpClient = null;

    private static final String TAG = RetrofitSingleton.class.getSimpleName();

    public static Context context;

    public static void init(final Context mContext){
        Executor executor = Executors.newCachedThreadPool();
        Gson gson = new GsonBuilder().create();
//        retrofit = new Retrofit.Builder().addCallAdapterFactory(GsonConverterFactory.create(gson))
//                .baseUrl(ApiInterface.API)
//                .callbackExecutor(executor)
//                .addCallAdapterFactory(Rx)
    }
}

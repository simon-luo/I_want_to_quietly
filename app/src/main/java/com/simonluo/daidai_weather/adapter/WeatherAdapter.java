package com.simonluo.daidai_weather.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.simonluo.daidai_weather.entity.Weather;
import com.simonluo.daidai_weather.utils.Constant;

/**
 * Created by 333 on 2016/4/29.
 */
public class WeatherAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private static String TAG = WeatherAdapter.class.getSimpleName();
    private Context context;
    private final int TYPE_ONE = 0;
    private final int TYPE_TWO = 1;
    private final int TYPE_THREE = 2;
    private final int TYPE_FOUR = 3;
    private Weather mWeatherData;
    private Constant mConstant;

    public WeatherAdapter(Context context, Constant mConstant, Weather mWeatherData) {
        this.context = context;
        this.mConstant = mConstant;
        this.mWeatherData = mWeatherData;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == TYPE_ONE){
            return TYPE_ONE;
        }
        if (position == TYPE_TWO){
            return TYPE_TWO;
        }
        if (position == TYPE_THREE){
            return TYPE_THREE;
        }
        if (position == TYPE_FOUR){
            return TYPE_FOUR;
        }
        return super.getItemViewType(position);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_ONE){

        }
        if (viewType == TYPE_TWO){

        }
        if (viewType == TYPE_THREE){

        }
        if (viewType == TYPE_FOUR){

        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 4;
    }

    public static String dayForWeek(String time){
        String week = "";
        return week;
    }
}

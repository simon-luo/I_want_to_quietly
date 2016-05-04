package com.simonluo.daidai_weather.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.simonluo.daidai_weather.entity.Weather;
import com.simonluo.daidai_weather.utils.Constant;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

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

    public WeatherAdapter(Context context, Weather mWeatherData) {
        this.context = context;
        this.mWeatherData = mWeatherData;
        mConstant = Constant.getInstance();
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

    @SuppressLint("SimpleDateFormat")
    public static String dayForWeek(String time) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(format.parse(time));
        int dayForWeek = 0;
        String week = "";
        dayForWeek = calendar.get(Calendar.DAY_OF_WEEK);
        switch (dayForWeek){
            case 1:
                week = "星期日";
                break;
            case 2:
                week = "星期一";
                break;
            case 3:
                week = "星期二";
                break;
            case 4:
                week = "星期三";
                break;
            case 5:
                week = "星期四";
                break;
            case 6:
                week = "星期五";
                break;
            case 7:
                week = "星期六";
                break;
        }
        return week;
    }

    /**
     * 当前天气情况
     */
    class NowWeatherViewHolder extends RecyclerView.ViewHolder{

        public NowWeatherViewHolder(View itemView) {
            super(itemView);
        }
    }

    /**
     * 当日小时预告
     */
    class HoursWeatherViewHolder extends RecyclerView.ViewHolder{

        public HoursWeatherViewHolder(View itemView) {
            super(itemView);
        }
    }

    /**
     * 当日建议
     */
    class SuggestionViewHolder extends RecyclerView.ViewHolder{

        public SuggestionViewHolder(View itemView) {
            super(itemView);
        }
    }

    /**
     * 未来天气
     */
    class ForecastViewHolder extends RecyclerView.ViewHolder{

        public ForecastViewHolder(View itemView) {
            super(itemView);
        }
    }
}

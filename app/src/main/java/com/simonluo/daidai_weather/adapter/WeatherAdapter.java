package com.simonluo.daidai_weather.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.simonluo.daidai_weather.R;
import com.simonluo.daidai_weather.entity.Weather;
import com.simonluo.daidai_weather.utils.Constant;
import com.simonluo.daidai_weather.utils.WLog;

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
            return new NowWeatherViewHolder(LayoutInflater.from(context).inflate(R.layout.item_temperature, parent, false));
        }
        if (viewType == TYPE_TWO){
            return new HoursWeatherViewHolder(LayoutInflater.from(context).inflate(R.layout.item_hour_info, parent, false));
        }
        if (viewType == TYPE_THREE){
            return new SuggestionViewHolder(LayoutInflater.from(context).inflate(R.layout.item_suggestion, parent, false));
        }
        if (viewType == TYPE_FOUR){
            return new ForecastViewHolder(LayoutInflater.from(context).inflate(R.layout.item_forecast, parent, false));
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof NowWeatherViewHolder){
            try{
                ((NowWeatherViewHolder) holder).tempFlu.setText(mWeatherData.now.tmp + "℃");
                ((NowWeatherViewHolder) holder).tempMax.setText("↑ " + mWeatherData.daily_forecast.get(0).tmp.max + "°");
                ((NowWeatherViewHolder) holder).tempMin.setText("↓ " + mWeatherData.daily_forecast.get(0).tmp.min + "°");
                ((NowWeatherViewHolder) holder).tempPm.setText("PM25： " + mWeatherData.aqi.city.pm25);
                ((NowWeatherViewHolder) holder).tempQuality.setText("空气质量： " + mWeatherData.aqi.city.qlty);
            }catch (Exception e){
                WLog.e(TAG, e.toString());
            }
            Glide.with(context)
                    .load(mConstant.getInt(mWeatherData.now.cond.txt, R.mipmap.none))
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(((NowWeatherViewHolder) holder).weatherIcon);
        }

        if (holder instanceof HoursWeatherViewHolder){
            try {
                for (int i = 0; i < mWeatherData.hourly_forecast.size(); i++){
                    String mDate = mWeatherData.hourly_forecast.get(i).date;
                    ((HoursWeatherViewHolder) holder).mClock[i].setText(mDate.substring(mDate.length() - 5, mDate.length()));
                    ((HoursWeatherViewHolder) holder).mTemp[i].setText(mWeatherData.hourly_forecast.get(i).tmp + "°");
                    ((HoursWeatherViewHolder) holder).mHumidity[i].setText(
                            mWeatherData.hourly_forecast.get(i).hum + "%");
                    ((HoursWeatherViewHolder) holder).mWind[i].setText(
                            mWeatherData.hourly_forecast.get(i).wind.spd + "Km");
                }
            }catch (Exception e){
                WLog.e(TAG, e.toString());
            }
        }

        if (holder instanceof SuggestionViewHolder){
            try{
                ((SuggestionViewHolder) holder).clothBrief.setText("穿衣指数---" + mWeatherData.suggestion.drsg.brf);
                ((SuggestionViewHolder) holder).clothTxt.setText(mWeatherData.suggestion.drsg.txt);
                ((SuggestionViewHolder) holder).sportBrief.setText("运动指数---" + mWeatherData.suggestion.sport.brf);
                ((SuggestionViewHolder) holder).sportTxt.setText(mWeatherData.suggestion.sport.txt);
                ((SuggestionViewHolder) holder).travelBrief.setText("旅游指数---" + mWeatherData.suggestion.trav.brf);
                ((SuggestionViewHolder) holder).travelTxt.setText(mWeatherData.suggestion.trav.txt);
                ((SuggestionViewHolder) holder).fluBrief.setText("感冒指数---" + mWeatherData.suggestion.flu.brf);
                ((SuggestionViewHolder) holder).fluTxt.setText(mWeatherData.suggestion.flu.txt);
            }catch (Exception e){
                WLog.e(TAG, e.toString());
            }
        }

        if (holder instanceof ForecastViewHolder){
            try {
                ((ForecastViewHolder) holder).forecastDate[0].setText("今日");
                ((ForecastViewHolder) holder).forecastDate[1].setText("明日");
                for (int i = 0; i < mWeatherData.daily_forecast.size(); i++){
                    if (i > 1){
                        try {
                            ((ForecastViewHolder) holder).forecastDate[i].setText(
                                    dayForWeek(mWeatherData.daily_forecast.get(i).date));
                        }catch (Exception e){
                            WLog.e(TAG, e.toString());
                        }
                    }

                    Glide.with(context)
                            .load(mConstant.getInt(mWeatherData.daily_forecast.get(i).cond.txtD, R.mipmap.none))
                            .crossFade()
                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                            .into(((ForecastViewHolder) holder).forecastIcon[i]);

                    ((ForecastViewHolder) holder).forecastTemp[i].setText(
                            mWeatherData.daily_forecast.get(i).tmp.min + "° " +
                                    mWeatherData.daily_forecast.get(i).tmp.max + "°");
                    ((ForecastViewHolder) holder).forecastTxt[i].setText(
                            mWeatherData.daily_forecast.get(i).cond.txtD + "。 最高" +
                                    mWeatherData.daily_forecast.get(i).tmp.max + "℃。 " +
                                    mWeatherData.daily_forecast.get(i).wind.sc + " " +
                                    mWeatherData.daily_forecast.get(i).wind.dir + " " +
                                    mWeatherData.daily_forecast.get(i).wind.spd + " km/h。 " +
                                    "降水几率 " +
                                    "" + mWeatherData.daily_forecast.get(i).pop + "%。");
                }
            }catch (Exception e){
                WLog.e(TAG, e.toString());
            }
        }
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

        private CardView cardView;
        private ImageView weatherIcon;
        private TextView tempFlu;
        private TextView tempMax;
        private TextView tempMin;
        private TextView tempPm;
        private TextView tempQuality;
        public NowWeatherViewHolder(View itemView) {
            super(itemView);
            cardView = (CardView) itemView.findViewById(R.id.cardView);
            weatherIcon = (ImageView) itemView.findViewById(R.id.weather_icon);
            tempFlu = (TextView) itemView.findViewById(R.id.temp_flu);
            tempMax = (TextView) itemView.findViewById(R.id.temp_max);
            tempMin = (TextView) itemView.findViewById(R.id.temp_min);
            tempPm = (TextView) itemView.findViewById(R.id.temp_pm);
            tempQuality = (TextView) itemView.findViewById(R.id.temp_quality);
        }
    }

    /**
     * 当日小时预告
     */
    class HoursWeatherViewHolder extends RecyclerView.ViewHolder{

        private LinearLayout itemHourInfoLinearlayout;
        private TextView[] mClock = new TextView[mWeatherData.hourly_forecast.size()];
        private TextView[] mTemp = new TextView[mWeatherData.hourly_forecast.size()];
        private TextView[] mHumidity = new TextView[mWeatherData.hourly_forecast.size()];
        private TextView[] mWind = new TextView[mWeatherData.hourly_forecast.size()];
        public HoursWeatherViewHolder(View itemView) {
            super(itemView);
            itemHourInfoLinearlayout = (LinearLayout) itemView.findViewById(R.id.item_hour_info_linearlayout);
            for (int i = 0; i < mWeatherData.hourly_forecast.size(); i++){
                View view = View.inflate(context, R.layout.item_hour_info_line, null);
                mClock[i] = (TextView) view.findViewById(R.id.one_clock);
                mTemp[i] = (TextView) view.findViewById(R.id.one_temp);
                mHumidity[i] = (TextView) view.findViewById(R.id.one_humidity);
                mWind[i] = (TextView) view.findViewById(R.id.one_wind);
                itemHourInfoLinearlayout.addView(view);
            }
        }
    }

    /**
     * 当日建议
     */
    class SuggestionViewHolder extends RecyclerView.ViewHolder{

        private CardView cardView;
        private TextView clothBrief;
        private TextView clothTxt;
        private TextView sportBrief;
        private TextView sportTxt;
        private TextView travelBrief;
        private TextView travelTxt;
        private TextView fluBrief;
        private TextView fluTxt;
        public SuggestionViewHolder(View itemView) {
            super(itemView);
            cardView = (CardView) itemView.findViewById(R.id.cardView);
            clothBrief = (TextView) itemView.findViewById(R.id.cloth_brief);
            clothTxt = (TextView) itemView.findViewById(R.id.cloth_txt);
            sportBrief = (TextView) itemView.findViewById(R.id.sport_brief);
            sportTxt = (TextView) itemView.findViewById(R.id.sport_txt);
            travelBrief = (TextView) itemView.findViewById(R.id.travel_brief);
            travelTxt = (TextView) itemView.findViewById(R.id.travel_txt);
            fluBrief = (TextView) itemView.findViewById(R.id.flu_brief);
            fluTxt = (TextView) itemView.findViewById(R.id.flu_txt);
        }
    }

    /**
     * 未来天气
     */
    class ForecastViewHolder extends RecyclerView.ViewHolder{

        private LinearLayout forecastLinearlayout;
        private TextView[] forecastDate = new TextView[mWeatherData.daily_forecast.size()];
        private TextView[] forecastTemp = new TextView[mWeatherData.daily_forecast.size()];
        private TextView[] forecastTxt = new TextView[mWeatherData.daily_forecast.size()];
        private ImageView[] forecastIcon = new ImageView[mWeatherData.daily_forecast.size()];
        public ForecastViewHolder(View itemView) {
            super(itemView);
            forecastLinearlayout = (LinearLayout) itemView.findViewById(R.id.forecast_linear);
            for (int i = 0; i < mWeatherData.daily_forecast.size(); i++){
                View view = View.inflate(context, R.layout.item_forecast_line, null);
                forecastDate[i] = (TextView) view.findViewById(R.id.forecast_date);
                forecastTemp[i] = (TextView) view.findViewById(R.id.forecast_temp);
                forecastTxt[i] = (TextView) view.findViewById(R.id.forecast_txt);
                forecastIcon[i] = (ImageView) view.findViewById(R.id.forecast_icon);
                forecastLinearlayout.addView(view);
            }
        }
    }
}

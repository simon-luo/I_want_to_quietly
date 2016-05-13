package com.simonluo.daidai_weather.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.simonluo.daidai_weather.R;
import com.simonluo.daidai_weather.adapter.WeatherAdapter;
import com.simonluo.daidai_weather.api.WeatherApi;
import com.simonluo.daidai_weather.base.BaseActivity;
import com.simonluo.daidai_weather.entity.Weather;
import com.simonluo.daidai_weather.interf.HidingScrollListener;
import com.simonluo.daidai_weather.interf.NavigationDrawerCallbacks;
import com.simonluo.daidai_weather.utils.Constant;
import com.simonluo.daidai_weather.utils.RetrofitSingleton;
import com.simonluo.daidai_weather.utils.Utils;
import com.simonluo.daidai_weather.utils.WLog;
import com.simonluo.daidai_weather.view.NavigationDrawerFragment;

import java.util.Calendar;

import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by 333 on 2016/3/16.
 */
public class WeatherActivity extends BaseActivity implements NavigationDrawerCallbacks, SwipeRefreshLayout.OnRefreshListener, AMapLocationListener{

    private final String TAG = WeatherActivity.class.getSimpleName();
    private CollapsingToolbarLayout collapsingToolbarLayout;
    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private FloatingActionButton fab;
    private SwipeRefreshLayout swipeRefreshLayout;
    private NavigationDrawerFragment mNavigationDrawerFragment;
    private ImageView imageBanner;
    private RelativeLayout headerBackground;
    private RecyclerView mRecyclerView;
    private Weather mWeather = new Weather();
    private WeatherAdapter mAdapter;
    private Observer<Weather> observer;

    private long exitTime = 0; //记录第一次点击的时间

    public AMapLocationClient mapLocationClient = null;
    public AMapLocationClientOption mapLocationClientOption = null;

    private boolean isLocation = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.weather_activity);
        initView();
        initDrawer();
        initIcon();
//        new RefreshHandler().sendEmptyMessage(1);
        swipeRefreshLayout.setRefreshing(true);
        initDataObserver();
        if (Utils.isNetworkConnected(this)){
            location();
            if (isLocation){
                onRefresh();
            }
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    private void initView() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        imageBanner = (ImageView) findViewById(R.id.banner);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setOnRefreshListener(this);
        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);
        collapsingToolbarLayout.setTitle("");

        Calendar calendar = Calendar.getInstance();
        mConstant.putInt(Constant.HOUR, calendar.get(Calendar.HOUR_OF_DAY));
        if (mConstant.getInt(Constant.HOUR, 0) < 6 || mConstant.getInt(Constant.HOUR, 0) > 18){
            Glide.with(this)
                    .load(R.mipmap.night)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(imageBanner);
            collapsingToolbarLayout.setContentScrimColor(ContextCompat.getColor(this, R.color.colorSunset));
        }

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(WeatherActivity.this, "点我吧！", Toast.LENGTH_SHORT).show();
            }
        });
        CoordinatorLayout.LayoutParams lp = (CoordinatorLayout.LayoutParams) fab.getLayoutParams();
        final int fabBottomMargin = lp.bottomMargin;

        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(WeatherActivity.this));

        mRecyclerView.addOnScrollListener(new HidingScrollListener() {
            @Override
            protected void onShow() {
                fab.animate()
                        .translationY(fab.getHeight() + fabBottomMargin)
                        .setInterpolator(new AccelerateInterpolator(2))
                        .start();
            }

            @Override
            protected void onHide() {
                fab.animate()
                        .translationY(0)
                        .setInterpolator(new DecelerateInterpolator(2))
                        .start();
            }
        });
        mAdapter = new WeatherAdapter(WeatherActivity.this, mWeather);
        mRecyclerView.setAdapter(mAdapter);
    }

    private void initDrawer() {
        mNavigationDrawerFragment = (NavigationDrawerFragment) getFragmentManager()
                .findFragmentById(R.id.fragment_drawer);
        mNavigationDrawerFragment.setup(R.id.fragment_drawer, (DrawerLayout) findViewById(R.id.drawer_layout), toolbar);

    }

    private void initIcon() {
        if(mConstant.getInt(Constant.CHANGE_ICONS, 0) == 0){
            mConstant.putInt("未知", R.mipmap.none);
            mConstant.putInt("晴", R.mipmap.type_one_sunny);
            mConstant.putInt("阴", R.mipmap.type_one_cloudy);
            mConstant.putInt("多云", R.mipmap.type_one_cloudy);
            mConstant.putInt("少云", R.mipmap.type_one_cloudy);
            mConstant.putInt("晴间多云", R.mipmap.type_one_cloudytosunny);
            mConstant.putInt("小雨", R.mipmap.type_one_light_rain);
            mConstant.putInt("中雨", R.mipmap.type_one_middle_rain);
            mConstant.putInt("大雨", R.mipmap.type_one_heavy_rain);
            mConstant.putInt("阵雨", R.mipmap.type_one_thunderstorm);
            mConstant.putInt("雷阵雨", R.mipmap.type_one_thunderstorm);
            mConstant.putInt("霾", R.mipmap.type_one_fog);
            mConstant.putInt("雾", R.mipmap.type_one_fog);
        }
    }

    private void initDataObserver() {
        observer = new Observer<Weather>() {
            @Override
            public void onCompleted() {
                if (swipeRefreshLayout.isRefreshing()) {
                    swipeRefreshLayout.setRefreshing(false);
                }
            }
            @Override
            public void onError(Throwable e) {
                errorNetSnackbar(observer);
                if (swipeRefreshLayout.isRefreshing()) {
                    swipeRefreshLayout.setRefreshing(false);
                }
            }
            @Override
            public void onNext(Weather weather) {
                if (swipeRefreshLayout.isRefreshing()) {
                    swipeRefreshLayout.setRefreshing(false);
                }
                aCache.put("WeatherData", weather,
                        (mConstant.getInt(Constant.AUTO_UPDATE, 0) + 1) * Constant.ONE_HOUR);//默认一小时后缓存失效
                mWeather.status = weather.status;
                mWeather.aqi = weather.aqi;
                mWeather.basic = weather.basic;
                mWeather.suggestion = weather.suggestion;
                mWeather.now = weather.now;
                mWeather.daily_forecast = weather.daily_forecast;
                mWeather.hourly_forecast = weather.hourly_forecast;
                collapsingToolbarLayout.setTitle(mWeather.basic.city);
                mAdapter.notifyDataSetChanged();
                Snackbar.make(fab, "加载成功!", Snackbar.LENGTH_SHORT).show();
            }
        };
    }

    public void fetchDataByCache(final Observer<Weather> observer){
        Weather weather = null;
        try {
            weather = (Weather) aCache.getAsObject("WeatherData");
        }catch (Exception e){
            WLog.e(TAG, e.toString());
        }
        if (weather != null){
            Observable.just(weather).distinct().subscribe(observer);
        }else {
            fetchDataByNetWork(observer);
        }
    }

    private void errorNetSnackbar(final Observer<Weather> observer){
        Snackbar.make(fab, "网络不好!", Snackbar.LENGTH_INDEFINITE)
                .setAction("重试", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        fetchDataByNetWork(observer);
                    }
                }).show();
    }

    private void fetchDataByNetWork(Observer<Weather> observer){
        String cityName = mConstant.getString(Constant.CITY_NAME, "北京");
        if(cityName != null){
            cityName = cityName.replace("市", "")
                    .replace("省", "")
                    .replace("自治区", "")
                    .replace("特别行政区", "")
                    .replace("地区", "")
                    .replace("盟", "");
        }
        RetrofitSingleton.getApiService(this)
                .mWeatherApi(cityName, Constant.KEY)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .filter(new Func1<WeatherApi, Boolean>() {
                    @Override
                    public Boolean call(WeatherApi weatherApi) {
                        return weatherApi.mHeWeatherDataService30s.get(0).status.equals("ok");
                    }
                })
                .map(new Func1<WeatherApi, Weather>() {
                    @Override
                    public Weather call(WeatherApi weatherApi) {
                        return weatherApi.mHeWeatherDataService30s.get(0);
                    }
                })
                .subscribe(observer);
    }

    private void location() {
        //初始化定位
        mapLocationClient = new AMapLocationClient(getApplicationContext());
        //设置定位回调监听
        mapLocationClient.setLocationListener(this);
        mapLocationClientOption = new AMapLocationClientOption();
        //设置定位模式为高精度模式，Battery_Saving为低功耗模式，Device_Sensors是仅设备模式
        mapLocationClientOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Battery_Saving);
        //设置是否返回地址信息（默认返回地址信息）
        mapLocationClientOption.setNeedAddress(true);
        //设置是否只定位一次,默认为false
        mapLocationClientOption.setOnceLocation(false);
        //设置是否强制刷新WIFI，默认为强制刷新
        mapLocationClientOption.setWifiActiveScan(true);
        //设置是否允许模拟位置,默认为false，不允许模拟位置
        mapLocationClientOption.setMockEnable(false);
        //设置定位间隔 单位毫秒
        mapLocationClientOption.setInterval((mConstant.getInt(Constant.AUTO_UPDATE, 0) + 1) * Constant.ONE_HOUR * 1000);
        //给定位客户端对象设置定位参数
        mapLocationClient.setLocationOption(mapLocationClientOption);
        //启动定位
        mapLocationClient.startLocation();
    }

    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        if (aMapLocation != null){
            if (aMapLocation.getErrorCode() == 0){
                //定位成功回调信息，设置相关消息
                aMapLocation.getLocationType();//获取当前定位结果来源，如网络定位结果，详见定位类型表
                mConstant.putString(Constant.CITY_NAME, aMapLocation.getCity());
                isLocation = true;
                WLog.i(TAG, aMapLocation.getProvince() + aMapLocation.getCity());
            }else {
                Snackbar.make(fab ,"定位失败，加载默认城市", Snackbar.LENGTH_LONG).show();
            }
        }else {
            //显示错误信息ErrCode是错误码，errInfo是错误信息，详见错误码表。
            WLog.e("AmapError", "location Error, ErrCode:" + aMapLocation.getErrorCode() + ", errInfo:" +
                    aMapLocation.getErrorInfo());
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mapLocationClient != null){
            mapLocationClient.unRegisterLocationListener(this);
        }
    }

    class RefreshHandler extends Handler{
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 1:
                    break;
                case 2:
                    break;
                default:
                    break;
            }
        }
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        switch (position){
            case 0:
                break;
            case 1:
                break;
            case 2:
                startActivityForResult(new Intent(this, ChooseCityActivity.class), 1);
                break;
            case 3:
                break;
            case 4:
                break;
        }
    }

    @Override
    public void onRefresh() {
        swipeRefreshLayout.postDelayed(new Runnable() {
            @Override
            public void run() {
                fetchDataByNetWork(observer);
            }
        }, 1000);

//        fetchDataByNetWork(observer);
    }

    @Override
    public void onBackPressed() {
        if (mNavigationDrawerFragment.isDrawerOpen()){
            mNavigationDrawerFragment.closeDrawer();
        }else {
            if ((System.currentTimeMillis() - exitTime) > 2000){
                exitTime = System.currentTimeMillis();
            }else {
                finish();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == 2){
            swipeRefreshLayout.setRefreshing(true);
            mConstant.putString(Constant.CITY_NAME, data.getStringExtra(Constant.CITY_NAME));
            fetchDataByNetWork(observer);
        }
    }
}

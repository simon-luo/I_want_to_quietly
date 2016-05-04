package com.simonluo.daidai_weather.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.simonluo.daidai_weather.R;
import com.simonluo.daidai_weather.adapter.WeatherAdapter;
import com.simonluo.daidai_weather.base.BaseActivity;
import com.simonluo.daidai_weather.entity.Weather;
import com.simonluo.daidai_weather.interf.NavigationDrawerCallbacks;
import com.simonluo.daidai_weather.utils.Constant;
import com.simonluo.daidai_weather.utils.Utils;

import java.util.Calendar;

import rx.Observer;

/**
 * Created by 333 on 2016/3/16.
 */
public class WeatherActivity extends BaseActivity implements NavigationDrawerCallbacks, SwipeRefreshLayout.OnRefreshListener{

    private final String TAG = WeatherActivity.class.getSimpleName();
    private CollapsingToolbarLayout collapsingToolbarLayout;
    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private FloatingActionButton fab;
    private SwipeRefreshLayout swipeRefreshLayout;
    private ImageView imageBanner;
    private RelativeLayout headerBackground;
    private RecyclerView mRecyclerView;
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
        new RefreshHandler().sendEmptyMessage(1);
        fetchData();
        if (Utils.isNetworkConnected(this)){
            location();
            if (isLocation){
                onRefresh();
            }
        }
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

            }
        });
        CoordinatorLayout.LayoutParams lp = (CoordinatorLayout.LayoutParams) fab.getLayoutParams();
        final int fabBottomMargin = lp.bottomMargin;

        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(WeatherActivity.this));

    }

    private void initDrawer() {
    }

    private void initIcon() {
    }

    private void fetchData() {
    }

    private void location() {

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
            case 1:
                break;
            case 2:
                break;
            case 3:
                break;
            case 4:
                break;
            case 5:
                break;
        }
    }

    @Override
    public void onRefresh() {

    }
}

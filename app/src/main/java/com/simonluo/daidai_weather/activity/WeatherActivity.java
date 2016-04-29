package com.simonluo.daidai_weather.activity;

import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.simonluo.daidai_weather.base.BaseActivity;
import com.simonluo.daidai_weather.interf.NavigationDrawerCallbacks;

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

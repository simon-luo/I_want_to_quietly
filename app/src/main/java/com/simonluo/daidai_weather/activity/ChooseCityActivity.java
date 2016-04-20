package com.simonluo.daidai_weather.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;

import com.simonluo.daidai_weather.R;
import com.simonluo.daidai_weather.adapter.CityAdapter;
import com.simonluo.daidai_weather.base.BaseActivity;
import com.simonluo.daidai_weather.entity.CityEntity;
import com.simonluo.daidai_weather.entity.ProvinceEntity;
import com.simonluo.daidai_weather.utils.CityDB;
import com.simonluo.daidai_weather.utils.CityDBManager;
import com.simonluo.daidai_weather.utils.Constant;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 333 on 2016/4/14.
 */
public class ChooseCityActivity extends BaseActivity{
    private static String TAG = ChooseCityActivity.class.getSimpleName();

    private RecyclerView mRecyclerView;
    private ContentLoadingProgressBar mProgressBar;
    private CityDBManager mCityDBManager;
    private CityDB mCityDB;
    private ArrayList<String> dataList = new ArrayList<>();
    private ProvinceEntity selectProvince;
    private CityEntity selectCity;
    private List<ProvinceEntity> provinceList;
    private List<CityEntity> cityList;
    private CityAdapter mCityAdapter;

    public static final int LEVEL_PROVINCE = 1;
    public static final int LEVEL_CITY = 2;
    private int currentLevel = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.choose_city_activity);
        mCityDBManager = new CityDBManager(this);
        mCityDBManager.openDatabase();
        mCityDB = new CityDB(this);
        initView();
        mProgressBar.show();
        initRecyclerView();
        queryProvinces();
        if (mCityAdapter.getItemCount() != 0){
            mProgressBar.hide();
            mRecyclerView.setVisibility(View.VISIBLE);
        }
    }

    private void initView(){
        mProgressBar = (ContentLoadingProgressBar) findViewById(R.id.progressbar);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("选择城市");
        setSupportActionBar(toolbar);
    }

    private void initRecyclerView() {
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setHasFixedSize(true);
        mCityAdapter = new CityAdapter(this, dataList);
        mRecyclerView.setAdapter(mCityAdapter);
        mCityAdapter.setOnItemClickListener(new CityAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                if (currentLevel == LEVEL_PROVINCE){
                    selectProvince = provinceList.get(position);
                    mRecyclerView.scrollTo(0, 0);
                    queryCities();
                }else if (currentLevel == LEVEL_CITY){
                    selectCity = cityList.get(position);
                    Intent intent = new Intent();
                    String cityName = selectCity.getCityName();
                    intent.putExtra(Constant.CITY_NAME, cityName);
                    setResult(2, intent);
                    finish();
                }
            }
        });
    }

    private void queryProvinces() {
        provinceList = mCityDB.loadProvinces(mCityDBManager.getDatabase());
        if (provinceList.size() > 0){
            dataList.clear();
            for (ProvinceEntity provinceEntity : provinceList){
                dataList.add(provinceEntity.getProName());
            }
            mCityAdapter.notifyDataSetChanged();
            currentLevel = LEVEL_PROVINCE;
        }
    }

    private void queryCities(){
        cityList = mCityDB.loadCities(mCityDBManager.getDatabase(), selectProvince.getProSort());
        if (cityList.size() > 0){
            dataList.clear();
            for (CityEntity cityEntity : cityList){
                dataList.add(cityEntity.getCityName());
            }
            mCityAdapter.notifyDataSetChanged();
            mRecyclerView.smoothScrollToPosition(0);
            currentLevel = LEVEL_CITY;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK){
            if (currentLevel == LEVEL_PROVINCE){
                finish();
            }else {
                queryProvinces();
                mRecyclerView.smoothScrollToPosition(0);
            }
        }
        return false;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mCityDBManager.closeDatabase();
    }
}

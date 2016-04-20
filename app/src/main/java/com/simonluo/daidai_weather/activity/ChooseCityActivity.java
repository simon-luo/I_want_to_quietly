package com.simonluo.daidai_weather.activity;

import android.os.Bundle;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.simonluo.daidai_weather.R;
import com.simonluo.daidai_weather.adapter.CityAdapter;
import com.simonluo.daidai_weather.base.BaseActivity;
import com.simonluo.daidai_weather.entity.CityEntity;
import com.simonluo.daidai_weather.entity.ProvinceEntity;
import com.simonluo.daidai_weather.utils.CityDB;
import com.simonluo.daidai_weather.utils.CityDBManager;

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
                }else if (currentLevel == LEVEL_CITY){
                    selectCity = cityList.get(position);
                }
            }
        });
    }

    private void queryProvinces() {

    }

    private void queryCities(){

    }
}

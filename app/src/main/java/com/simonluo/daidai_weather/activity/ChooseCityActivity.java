package com.simonluo.daidai_weather.activity;

import android.support.v4.widget.ContentLoadingProgressBar;
import android.support.v7.widget.RecyclerView;

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

}

package com.simonluo.daidai_weather.base;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.simonluo.daidai_weather.utils.ACache;
import com.simonluo.daidai_weather.utils.Constant;

/**
 * Created by 333 on 2016/3/22.
 */
public class BaseActivity extends AppCompatActivity{
    public ACache aCache;
    public Constant mConstant = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}

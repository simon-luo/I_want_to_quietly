package com.simonluo.daidai_weather.utils;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.simonluo.daidai_weather.entity.CityEntity;
import com.simonluo.daidai_weather.entity.ProvinceEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 333 on 2016/4/15.
 */
public class CityDB {
    private Context context;

    public CityDB(Context context) {
        this.context = context;
    }

    public List<ProvinceEntity> loadProvinces(SQLiteDatabase db){
        List<ProvinceEntity> provinceList = new ArrayList<>();
        Cursor cursor = db.query("T_Province", null, null, null, null, null, null);
        if (cursor.moveToFirst()){
            do{
                ProvinceEntity provinceEntity = new ProvinceEntity();
                provinceEntity.setProSort(cursor.getInt(cursor.getColumnIndex("ProSort")));
                provinceEntity.setProName(cursor.getString(cursor.getColumnIndex("ProName")));
                provinceList.add(provinceEntity);
            }while (cursor.moveToNext());
        }
        cursor.close();
        return provinceList;
    }

    public List<CityEntity> loadCities(SQLiteDatabase db, int ProID){
        List<CityEntity> cityList = new ArrayList<>();
        Cursor cursor = db.query("T_City", null, "ProID = ?" ,new String[]{String.valueOf(ProID)}, null, null, null);
        if (cursor.moveToFirst()){
            do {
                CityEntity cityEntity = new CityEntity();
                cityEntity.setCityName(cursor.getString(cursor.getColumnIndex("CityName")));
                cityEntity.setProID(ProID);
                cityList.add(cityEntity);
            }while (cursor.moveToNext());
        }
        cursor.close();
        return cityList;
    }
}

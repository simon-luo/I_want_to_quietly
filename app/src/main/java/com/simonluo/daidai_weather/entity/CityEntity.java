package com.simonluo.daidai_weather.entity;

/**
 * Created by 333 on 2016/4/13.
 */
public class CityEntity {
    private String CityName;
    private int ProID;

    public String getCityName(){
        return CityName;
    }

    public void setCityName(String cityName){
        CityName = cityName;
    }

    public int getProID(){
        return ProID;
    }

    public void setProID(int proID){
        ProID = proID;
    }
}

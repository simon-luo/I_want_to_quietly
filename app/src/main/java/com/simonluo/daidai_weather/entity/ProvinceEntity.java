package com.simonluo.daidai_weather.entity;

/**
 * Created by 333 on 2016/4/13.
 */
public class ProvinceEntity {
    private String ProName;
    private int ProSort;

    public String getProName(){
        return ProName;
    }

    public void setProName(String proName){
        ProName = proName;
    }

    public int getProSort(){
        return ProSort;
    }

    public void setProSort(int proSort){
        ProSort = proSort;
    }
}

package com.simonluo.daidai_weather.entity;

import android.graphics.drawable.Drawable;

import com.simonluo.daidai_weather.utils.Style;

/**
 * Created by 333 on 2016/3/2.
 */
public class NavigationItem {
    private String mText;
    private Drawable mDrawable;
    private Style mStyle;

    public NavigationItem(String mText, Drawable mDrawable, Style mStyle) {
        this.mDrawable = mDrawable;
        this.mStyle = mStyle;
        this.mText = mText;
    }

    public String getText() {
        return mText;
    }

    public void setText(String mText) {
        this.mText = mText;
    }

    public Drawable getDrawable(){
        return mDrawable;
    }

    public void setDrawable(Drawable mDrawable){
        this.mDrawable = mDrawable;
    }

    public Style getStyle(){
        return mStyle;
    }

    public void setStyle(Style mStyle){
        this.mStyle = mStyle;
    }
}

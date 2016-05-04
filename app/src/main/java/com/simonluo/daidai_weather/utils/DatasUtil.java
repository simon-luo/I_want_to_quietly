package com.simonluo.daidai_weather.utils;

import android.content.Context;
import android.support.v4.content.ContextCompat;

import com.simonluo.daidai_weather.R;
import com.simonluo.daidai_weather.entity.NavigationItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 333 on 2016/3/9.
 */
public class DatasUtil {
    private static DatasUtil mDatas;
    private List<NavigationItem> mList;

    private DatasUtil(Context context){
        mList = new ArrayList<NavigationItem>();
        mList.add(new NavigationItem("首页", ContextCompat.getDrawable(context, R.mipmap.ic_toggle_star),Style.DEFAULT));
        mList.add(new NavigationItem("天气", ContextCompat.getDrawable(context, R.mipmap.ic_toggle_star),Style.DEFAULT));
        mList.add(new NavigationItem("城市", ContextCompat.getDrawable(context, R.mipmap.ic_toggle_star),Style.HASLINE));
        mList.add(new NavigationItem("切换主题", null,Style.NO_ICON));
        mList.add(new NavigationItem("设置", null,Style.NO_ICON));
    }

    public static DatasUtil getInstance(Context context){
        if(mDatas==null){
            synchronized (DatasUtil.class) {
                if(mDatas==null){
                    mDatas=new DatasUtil(context);
                }
            }
        }
        return mDatas;

    }


    public  List<NavigationItem> getMenu(){
        return new ArrayList<NavigationItem>(mList);
    }

}

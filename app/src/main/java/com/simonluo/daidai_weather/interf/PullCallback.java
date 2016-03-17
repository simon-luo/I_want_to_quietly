package com.simonluo.daidai_weather.interf;

/**
 * Created by 333 on 2016/3/2.
 */
public interface PullCallback {

    void onLoadMore();

    void onRefresh();

    boolean isLoading();

    boolean hasLoadedAllItems();
}

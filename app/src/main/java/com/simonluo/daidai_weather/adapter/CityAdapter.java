package com.simonluo.daidai_weather.adapter;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.simonluo.daidai_weather.R;

/**
 * Created by 333 on 2016/4/15.
 */
public class CityAdapter {


    class CityViewHolder extends RecyclerView.ViewHolder {
        private CardView cardView;
        private TextView cityName;
        public CityViewHolder(View itemView) {
            super(itemView);
            cardView = (CardView) itemView.findViewById(R.id.cardView);
            cityName = (TextView) itemView.findViewById(R.id.cityName);
        }
    }
}

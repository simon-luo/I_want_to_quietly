package com.simonluo.daidai_weather.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.simonluo.daidai_weather.R;

import java.util.ArrayList;

/**
 * Created by 333 on 2016/4/15.
 */
public class CityAdapter extends RecyclerView.Adapter<CityAdapter.CityViewHolder>{

    private Context mContext;
    private ArrayList<String> dataList;
    private OnRecyclerViewItemClickListener mOnItemClickListener = null;

    public CityAdapter(Context context, ArrayList<String> dataList) {
        this.mContext = context;
        this.dataList = dataList;
    }

    @Override
    public CityViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new CityViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_city, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(CityViewHolder cityViewHolder, final int i) {
        cityViewHolder.cityName.setText(dataList.get(i));
        cityViewHolder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnItemClickListener.onItemClick(v, i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public void setOnItemClickListener(OnRecyclerViewItemClickListener listener){
        this.mOnItemClickListener = listener;
    }

    public interface OnRecyclerViewItemClickListener{
        void onItemClick(View view, int position);
    }

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

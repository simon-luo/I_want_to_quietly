package com.simonluo.daidai_weather.adapter;

import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.simonluo.daidai_weather.R;
import com.simonluo.daidai_weather.entity.NavigationItem;
import com.simonluo.daidai_weather.interf.NavigationDrawerCallbacks;
import com.simonluo.daidai_weather.utils.Style;

import java.util.List;

/**
 * Created by 333 on 2016/3/9.
 */
public class NavigationDrawerAdapter extends RecyclerView.Adapter<NavigationDrawerAdapter.ViewHolder>{

    private List<NavigationItem> mData;
    private NavigationDrawerCallbacks mNavigationDrawerCallbacks;
    private int mSelectedPosition;
    private int mTouchedPosition;

    public NavigationDrawerAdapter(List<NavigationItem> mData) {
        this.mData = mData;
    }

    public NavigationDrawerCallbacks getNavigationDrawerCallbacks(){
        return mNavigationDrawerCallbacks;
    }

    public void setNavigationDrawerCallbacks(NavigationDrawerCallbacks mNavigationDrawerCallbacks){
        this.mNavigationDrawerCallbacks = mNavigationDrawerCallbacks;
    }

    @Override
    public NavigationDrawerAdapter.ViewHolder onCreateViewHolder(final ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.drawer_row, viewGroup, false);
        final ViewHolder viewHolder = new ViewHolder(view);
        viewHolder.itemView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        touchPosition(viewHolder.getPosition());
                        return false;
                    case MotionEvent.ACTION_CANCEL:
                        touchPosition(-1);
                        return false;
                    case MotionEvent.ACTION_MOVE:
                        return false;
                    case MotionEvent.ACTION_UP:
                        touchPosition(-1);
                        return false;
                }
                return true;
            }
        });
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mNavigationDrawerCallbacks != null){
                    mNavigationDrawerCallbacks.onNavigationDrawerItemSelected(viewHolder.getPosition());
                }
            }
        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(NavigationDrawerAdapter.ViewHolder viewHolder, int i) {
        NavigationItem item = mData.get(i);
        if (item.getStyle() == Style.DEFAULT){
            viewHolder.textView.setText(item.getText());
            viewHolder.textView.setCompoundDrawablesWithIntrinsicBounds(item.getDrawable(), null ,null, null);
        }
        if (item.getStyle() == Style.NO_ICON){
            viewHolder.textView.setText(item.getText());
        }
        if (item.getStyle() == Style.HASLINE){
            viewHolder.view.setVisibility(View.VISIBLE);
            viewHolder.textView.setText(item.getText());
            viewHolder.textView.setCompoundDrawablesWithIntrinsicBounds(item.getDrawable(), null ,null, null);
        }
        if (mSelectedPosition == i || mTouchedPosition == i){
            viewHolder.itemView.setBackgroundColor(ContextCompat.getColor(viewHolder.itemView.getContext(), R.color.selected_gray));
        }else {
            viewHolder.itemView.setBackgroundColor(Color.TRANSPARENT);
        }
    }

    private void touchPosition(int position){
        int lastPosition = mTouchedPosition;
        mTouchedPosition = position;
        if (lastPosition >= 0){
            notifyItemChanged(lastPosition);
        }
        if (position >= 0){
            notifyItemChanged(position);
        }
    }

    public void selectPosition(int position){
        int lastPosition = mSelectedPosition;
        mSelectedPosition = position;
        notifyItemChanged(lastPosition);
        notifyItemChanged(position);
    }

    @Override
    public int getItemCount() {
        return mData !=null ?mData.size() : 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        public TextView textView;
        public View view;
        public ViewHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.item_name);
            view = itemView.findViewById(R.id.view);
        }
    }
}

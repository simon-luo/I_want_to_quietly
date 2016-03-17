package com.simonluo.daidai_weather.activity;

import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.simonluo.daidai_weather.R;
import com.simonluo.daidai_weather.interf.NavigationDrawerCallbacks;
import com.simonluo.daidai_weather.interf.PullCallback;
import com.simonluo.daidai_weather.view.NavigationDrawerFragment;
import com.simonluo.daidai_weather.view.PullToLoadView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 333 on 2016/3/1.
 */
public class MainActivity extends AppCompatActivity implements NavigationDrawerCallbacks{

    private Toolbar mToolbar;
    private NavigationDrawerFragment mNavigationDrawerFragment;
    private PullToLoadView mPullToLoadView;
    private SimpleAdapter mAdapter;
    private boolean isLoading = false;
    private boolean isHasLoadedAll = false;
    private int nextPage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        initViews();
        initEvent();
    }

    private void initViews() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar_actionbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mNavigationDrawerFragment = (NavigationDrawerFragment) getFragmentManager().findFragmentById(R.id.fragment_drawer);
        mNavigationDrawerFragment.setup(R.id.fragment_drawer, (DrawerLayout) findViewById(R.id.drawer_layout),mToolbar);
        mPullToLoadView = (PullToLoadView) findViewById(R.id.pullToLoadView);
    }

    private void initEvent() {
        RecyclerView mRecyclerView = mPullToLoadView.getRecyclerView();
        LinearLayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false);
        mRecyclerView.setLayoutManager(manager);
        mAdapter = new SimpleAdapter();
        mRecyclerView.setAdapter(mAdapter);
        mPullToLoadView.isLoadMoreEnabled(true);
        mPullToLoadView.setPullCallback(new PullCallback() {
            @Override
            public void onLoadMore() {
//                loadData(nextPage);
            }

            @Override
            public void onRefresh() {
//                mAdapter.clear();
                isHasLoadedAll = false;
//                loadData(1);
            }

            @Override
            public boolean isLoading() {
                Log.e("main activity", "main isLoading:" + isLoading);
                return isLoading;
            }

            @Override
            public boolean hasLoadedAllItems() {
                return isHasLoadedAll;
            }
        });
        mPullToLoadView.initLoad();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        Toast.makeText(this, "Menu item selected -> " + position,
                Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onBackPressed() {
        if (mNavigationDrawerFragment.isDrawerOpen()){
            mNavigationDrawerFragment.closeDrawer();
        }else {
            super.onBackPressed();
        }
    }

    private static class SimpleAdapter extends RecyclerView.Adapter<CellHolder>{
        private List<String> mList;

        public SimpleAdapter() {
            mList = new ArrayList<>();
        }

        @Override
        public CellHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            return null;
        }

        @Override
        public void onBindViewHolder(CellHolder cellHolder, int i) {

        }

        @Override
        public int getItemCount() {
            return 0;
        }
    }

    private static class CellHolder extends RecyclerView.ViewHolder{

        public CellHolder(View itemView) {
            super(itemView);
        }
    }
}
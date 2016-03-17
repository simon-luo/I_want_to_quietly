package com.simonluo.daidai_weather.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Interpolator;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ProgressBar;

import com.nineoldandroids.view.ViewHelper;
import com.nineoldandroids.view.ViewPropertyAnimator;
import com.simonluo.daidai_weather.R;
import com.simonluo.daidai_weather.interf.PullCallback;
import com.simonluo.daidai_weather.utils.ScrollDirection;

/**
 * Created by 333 on 2016/3/2.
 */
public class PullToLoadView extends FrameLayout {
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private RecyclerView mRecyclerView;
    private ProgressBar mProgressBar;
    private PullCallback mPullCallback;
    private RecyclerViewPositionHelper mRecyclerViewHelper;
    protected ScrollDirection mCurScrollDirection;
    protected int mPrevFirstVisbleItem = 0;
    private int mLoadMoreOffset = 5;
    private boolean mIsLoadMoreEnabled = false;
    private ImageButton img_float_btn;
    private boolean mVisible;
    private final Interpolator mInterpolator = new AccelerateDecelerateInterpolator();

    public PullToLoadView(Context context) {
        this(context, null);
    }

    public PullToLoadView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PullToLoadView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mInflater.inflate(R.layout.loadview, this, true);
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        mProgressBar = (ProgressBar) findViewById(R.id.progressBar);
        img_float_btn = (ImageButton) findViewById(R.id.img_float_btn);
        mVisible = true;
        init();
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        mRecyclerViewHelper = RecyclerViewPositionHelper.createHelper(mRecyclerView);
    }

    private void init() {
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (null != mPullCallback) {
                    mPullCallback.onRefresh();
                }
            }
        });

        mRecyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                mCurScrollDirection = null;
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy > 0){
                    hide();
                }else {
                    show();
                }
                if (mCurScrollDirection == null){
                    mCurScrollDirection = ScrollDirection.SAME;
                    mPrevFirstVisbleItem = mRecyclerViewHelper.findFirstVisibleItemPosition();
                }else {
                    final int firstVisibleItem = mRecyclerViewHelper.findFirstVisibleItemPosition();
                    if (firstVisibleItem > mPrevFirstVisbleItem){
                        mCurScrollDirection = ScrollDirection.UP;
                    }else if (firstVisibleItem < mPrevFirstVisbleItem){
                        mCurScrollDirection = ScrollDirection.DOWN;
                    }else {
                        mCurScrollDirection = ScrollDirection.SAME;
                    }
                    mPrevFirstVisbleItem = firstVisibleItem;
                }
                if (mIsLoadMoreEnabled && (mCurScrollDirection == ScrollDirection.UP)){
                    if (!mPullCallback.isLoading() && !mPullCallback.hasLoadedAllItems()){
                        final int totalItemCount = mRecyclerViewHelper.getItemCount();
                        final int firstVisibleItem = mRecyclerViewHelper.findFirstVisibleItemPosition();
                        final int visibleItemCount = Math.abs(mRecyclerViewHelper.findLastVisibleItemPosition() - firstVisibleItem);
                        final int lastAdapterPosition = totalItemCount - 1;
                        final int lastVisiblePosition = (firstVisibleItem + visibleItemCount) - 1;
                        if (lastVisiblePosition >= (lastAdapterPosition - mLoadMoreOffset)){
                            if (null != mPullCallback){
                                mProgressBar.setVisibility(VISIBLE);
                                mPullCallback.onLoadMore();
                            }
                        }
                    }
                }
            }
        });
    }

    public void setComplete(){
        mProgressBar.setVisibility(GONE);
        mSwipeRefreshLayout.setRefreshing(false);
    }

    public void initLoad(){
        if (null != mPullCallback){
            mSwipeRefreshLayout.post(new Runnable() {
                @Override
                public void run() {
                    mSwipeRefreshLayout.setRefreshing(true);
                }
            });
            mPullCallback.onRefresh();
        }
    }

    public void setColorSchemeResources(int... colorResIds){
        mSwipeRefreshLayout.setColorSchemeResources(colorResIds);
    }

    public RecyclerView getRecyclerView(){
        return this.mRecyclerView;
    }

    public void setPullCallback(PullCallback mPullCallback){
        this.mPullCallback = mPullCallback;
    }

    public void setLoadMoreOffset(int mLoadMoreOffset){
        this.mLoadMoreOffset = mLoadMoreOffset;
    }

    public void isLoadMoreEnabled(boolean mIsLoadMoreEnabled){
        this.mIsLoadMoreEnabled = mIsLoadMoreEnabled;
    }

    public void show(){
        show(true);
    }

    public void hide(){
        hide(true);
    }

    public void show(boolean animate){
        toggle(true, animate, false);
    }

    public void hide(boolean animate){
        toggle(false, animate, false);
    }

    private void toggle(final boolean visible, final boolean animate, boolean force){
        if (mVisible != visible || force){
            mVisible = visible;
            int height = getHeight();
            if (height == 0 && !force){
                ViewTreeObserver vto = getViewTreeObserver();
                if (vto.isAlive()){
                    vto.addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
                        @Override
                        public boolean onPreDraw() {
                            ViewTreeObserver currentVto = getViewTreeObserver();
                            if (currentVto.isAlive()){
                                currentVto.removeOnPreDrawListener(this);
                            }
                            toggle(visible, animate, true);
                            return true;
                        }
                    });
                    return;
                }
            }
            int translationY = visible ? 0 : height + geMarginBottom();
            if (animate){
                ViewPropertyAnimator.animate(img_float_btn)
                        .setInterpolator(mInterpolator).setDuration(320)
                        .translationY(translationY);
            }else{
                ViewHelper.setTranslationY(img_float_btn, translationY);
            }
        }
    }

    private int geMarginBottom() {
        int marginBottom = 0;
        final ViewGroup.LayoutParams layoutParams = getLayoutParams();
        if (layoutParams instanceof ViewGroup.MarginLayoutParams){
            marginBottom = ((ViewGroup.MarginLayoutParams) layoutParams).bottomMargin;
        }
        return marginBottom;
    }

}

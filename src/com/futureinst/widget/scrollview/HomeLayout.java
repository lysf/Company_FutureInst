package com.futureinst.widget.scrollview;


import android.content.Context;
import android.graphics.Rect;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import com.futureinst.R;
import com.futureinst.global.Content;
import com.futureinst.widget.CustomViewPager;
import com.futureinst.widget.PagerSlidingTabStrip;
import com.futureinst.widget.list.PullListView;

/**
 * TODO: document your custom view class.
 */
public class HomeLayout extends ScrollView{
    private LinearLayout ll_top;
    private CustomViewPager mViewPager;
    private ViewGroup mInnerScrollView;
    private HomeLayout homeLayout;
    private PagerSlidingTabStrip slidingTab_float;
    private float xDistance, yDistance, xLast, yLast;
    private Context context;
    /**
     * 主要是用在用户手指离开MyScrollView，MyScrollView还在继续滑动，我们用来保存Y的距离，然后做比较
     */
    private int lastScrollY;
    private OnScrollListener onScrollListener;
    /**
     * 用于用户手指离开MyScrollView的时候获取MyScrollView滚动的Y距离，然后回调给onScroll方法中
     */
    private Handler handler = new Handler() {

        public void handleMessage(android.os.Message msg) {
            int scrollY = HomeLayout.this.getScrollY();

            //此时的距离和记录下的距离不相等，在隔5毫秒给handler发送消息
            if(lastScrollY != scrollY){
                lastScrollY = scrollY;
                handler.sendMessageDelayed(handler.obtainMessage(), 5);
            }
            if(onScrollListener != null){
                onScrollListener.onScroll(scrollY);
            }
        }

    };

    public HomeLayout(Context context) {
        super(context);
    }

    public HomeLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public HomeLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        setVerticalScrollBarEnabled(false);
        mViewPager = (CustomViewPager) findViewById(R.id.id_stickynavlayout_viewpager);
        slidingTab_float = (PagerSlidingTabStrip)findViewById(R.id.slidingTab_float);
        ll_top = (LinearLayout)findViewById(R.id.ll_top);
        homeLayout = (HomeLayout)findViewById(R.id.home_layout);
        homeLayout.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @SuppressWarnings("deprecation")
            @Override
            public void onGlobalLayout() {
                onScrollListener.onScroll(homeLayout.getScrollY());
            }
        });
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        onScrollListener.onScroll(t);
        super.onScrollChanged(l, t, oldl, oldt);
    }

    private void getCurrentScrollView() {

        int currentItem = mViewPager.getCurrentItem();
        PagerAdapter a = mViewPager.getAdapter();
        if (a instanceof FragmentPagerAdapter) {
            FragmentPagerAdapter fadapter = (FragmentPagerAdapter) a;
            Fragment item = (Fragment) fadapter.instantiateItem(mViewPager,
                    currentItem);
            mInnerScrollView = (ViewGroup) (item.getView()
                    .findViewById(R.id.id_stickynavlayout_innerscrollview));
        } else if (a instanceof FragmentStatePagerAdapter) {
            FragmentStatePagerAdapter fsAdapter = (FragmentStatePagerAdapter) a;
            Fragment item = (Fragment) fsAdapter.instantiateItem(mViewPager,
                    currentItem);
            mInnerScrollView = (ViewGroup) (item.getView()
                    .findViewById(R.id.id_stickynavlayout_innerscrollview));
        }

    }
    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if(onScrollListener != null){
            onScrollListener.onScroll(lastScrollY = this.getScrollY());
        }
        switch (ev.getAction()){
            case MotionEvent.ACTION_UP:
                handler.sendMessageDelayed(handler.obtainMessage(), 5);

                break;
        }
        return super.onTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        float topY = ll_top.getPivotY();
        float slidingY = slidingTab_float.getY();
        int[] location = new int[2];
        int[] location_top = new int[2];

        ll_top.getLocationOnScreen(location_top);
        slidingTab_float.getLocationOnScreen(location);
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                xLast = ev.getX();
                yLast = ev.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                final float curY = ev.getY();
                getCurrentScrollView();

                android.util.Log.i("", "=======topY=" + location_top[1] + ",sldingY=" + location[1]+",statusH="+ Content.statusHeight);



                if(location[1] <= Content.statusHeight
                        && curY  < yLast){
                    yLast = curY;
                    return false;
                }
                if(curY  > yLast) {

                    if (mInnerScrollView instanceof PullListView) {
                        PullListView lv = (PullListView) mInnerScrollView;
                        View c = lv.getChildAt(lv.getFirstVisiblePosition());
                        Log.i("","========lv.getFirstVisiblePosition()=="+lv.getFirstVisiblePosition());
                        if((c!=null && c.getTop() !=0) || lv.getFirstVisiblePosition() !=0){
                            Log.i("","===========top=="+c.getTop());
                            yLast = curY;
                            return false;
                        }

                    }
                    if(location_top[1] >= Content.statusHeight){
                        yLast = curY;
                        return false;
                    }
                    yLast = curY;
                    return true;
                }

                ll_top.getLocationInWindow(location_top);
                slidingTab_float.getLocationInWindow(location);
                yLast = curY;
                break;
        }
        return super.onInterceptTouchEvent(ev);
    }


    /**
     * 设置滚动接口
     * @param onScrollListener
     */
    public void setOnScrollListener(OnScrollListener onScrollListener) {
        this.onScrollListener = onScrollListener;
    }
    /**
     * 滚动的回调接口
     */
    public interface OnScrollListener{
        /**
         * 回调方法， 返回MyScrollView滑动的Y方向距离
         * @param scrollY
         *              、
         */
        void onScroll(int scrollY);
    }
    @Override
    protected int computeScrollDeltaToGetChildRectOnScreen(Rect rect) {
        // TODO Auto-generated method stub
        return 0;
    }
}

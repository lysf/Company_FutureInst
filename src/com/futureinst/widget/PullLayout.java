package com.futureinst.widget;

import com.futureinst.R;
import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.ObjectAnimator;
import com.nineoldandroids.animation.Animator.AnimatorListener;
import com.nineoldandroids.view.ViewHelper;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

public class PullLayout extends ScrollView{
	 private View rl_top;
	 private ObjectAnimator oa;
    private float lastY = -1;
    private float detalY = -1;
    private int range;
    private boolean isTouchOrRunning;
    private boolean isActionCancel;
    private ImageView iv_image;

//    private int tvHeight;
//    private View view_line;
//    private PullLayout bottom_scroll;
//    private int tvWidth;
//    private ImageView iv_operate;
//	private ImageView iv_share;
//	private ImageView iv_back;

//	private TextView tv_time;
//	private TextView tv_event_title;

	 private OnScrollListener onScrollListener;  
	/** 
     * 主要是用在用户手指离开MyScrollView，MyScrollView还在继续滑动，我们用来保存Y的距离，然后做比较 
     */  
    private int lastScrollY; 
    
    /** 
     * 用于用户手指离开MyScrollView的时候获取MyScrollView滚动的Y距离，然后回调给onScroll方法中 
     */  
    private Handler handler = new Handler() {  
  
        public void handleMessage(android.os.Message msg) {  
            int scrollY = PullLayout.this.getScrollY();  
              
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
	public PullLayout(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
	}

	public PullLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	public PullLayout(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}
	
	/** 
     * 设置滚动接口 
     * @param onScrollListener 
     */  
    public void setOnScrollListener(OnScrollListener onScrollListener) {  
        this.onScrollListener = onScrollListener;  
    }  
	@Override
	protected void onFinishInflate() {
		super.onFinishInflate();
		setVerticalScrollBarEnabled(false);

		rl_top = findViewById(R.id.rl_top);
//		bottom_scroll = (PullLayout) findViewById(R.id.bottom_scroll);
//		rl_deal = findViewById(R.id.rl_deal);
//		 tv_time = (TextView) findViewById(R.id.tv_time);
		iv_image = (ImageView) findViewById(R.id.iv_image);
//		iv_operate = (ImageView) findViewById(R.id.iv_operate);
//		iv_share = (ImageView) findViewById(R.id.iv_share);
//		iv_back = (ImageView) findViewById(R.id.iv_back);
//		tv_event_title = (TextView) findViewById(R.id.tv_event_title);
//		view_line = findViewById(R.id.view_line);
		rl_top.getViewTreeObserver().addOnGlobalLayoutListener(new OnGlobalLayoutListener() {
            @SuppressWarnings("deprecation")
            @Override
            public void onGlobalLayout() {
                rl_top.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                range = rl_top.getHeight();
//                scrollTo(0, range);
                rl_top.getLayoutParams().height = range;
            }
        });
//		bottom_scroll.getViewTreeObserver().addOnGlobalLayoutListener(new OnGlobalLayoutListener() {
//			@SuppressWarnings("deprecation")
//			@Override
//			public void onGlobalLayout() {
//				onScrollListener.onScroll(bottom_scroll.getScrollY());
//			}
//		});
//		open();
//		tv_event_title.getViewTreeObserver().addOnGlobalLayoutListener(new OnGlobalLayoutListener() {
//	            @SuppressWarnings("deprecation")
//	            @Override
//	            public void onGlobalLayout() {
//	            	tv_event_title.getViewTreeObserver().removeGlobalOnLayoutListener(this);
//	                tvHeight = tv_event_title.getHeight();
//	                tvWidth = tv_event_title.getWidth();
////	                ViewHelper.setTranslationY(ll_content, tvHeight);
//	            }
//	        });
	}
	
	@Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                isActionCancel = false;
                isTouchOrRunning = true;
                lastY = ev.getY();
                break;
        }
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
    	if(onScrollListener != null){  
            onScrollListener.onScroll(lastScrollY = this.getScrollY());  
        }
        if (oa != null && oa.isRunning()) {
            ev.setAction(MotionEvent.ACTION_UP);
            isActionCancel = true;
        }
        if (isActionCancel && ev.getAction() != MotionEvent.ACTION_DOWN) {
            return false;
        }
        if (ev.getActionIndex() != 0 && getScrollY() < range) {
            ev.setAction(MotionEvent.ACTION_UP);
            isActionCancel = true;
        }

        switch (ev.getAction()) {
            case MotionEvent.ACTION_MOVE:
                isTouchOrRunning = true;
                if (getScrollY() != 0) {
                    detalY = 0;
                    lastY = ev.getY();
                } else {
                    detalY = ev.getY() - lastY;
                    if (detalY > 0) {
                        setT((int) -detalY / 5);
                        return true;
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
            	 handler.sendMessageDelayed(handler.obtainMessage(), 5);  
                isTouchOrRunning = false;
                if (getScrollY() < range) {
                    if (detalY != 0) {
                        reset();
                    } else {
//                        toggle();
                    }
                    return true;
                }
                break;
        }
        return super.onTouchEvent(ev);
    }
    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        if(onScrollListener!=null){
            onScrollListener.onScroll(t);
        }
        if (scrollViewListener != null) {
            setScrollBottom(getScrollY() + getHeight() >= computeVerticalScrollRange());
            scrollViewListener.onScrollChanged(l, t, oldl, oldt);
        }
        if (t > range) {
            return;
        } else if (!isTouchOrRunning && t != range) {
            scrollTo(0, range);
        } else {
            animateScroll(t);
        }

    }

    public void setT(int t) {
        scrollTo(0, t);
        if (t < 0) {
            animatePull(t);
        }
    }

    private void animateScroll(int t) {
        float percent = (float) t / range;
//        ViewHelper.setTranslationY(rl_top, t);
        
//        ViewHelper.setTranslationY(iv_operate, -t);
//        ViewHelper.setTranslationY(iv_share, -t);
//        ViewHelper.setTranslationY(tv_event_title, -t);
//        ViewHelper.setTranslationY(iv_image, -t/3);
//        if(percent<=0.5){
//        	float alpha = 1-2*percent;
//        	iv_back.setAlpha(alpha);
//        	tv_time.setAlpha(alpha);
//        	iv_operate.setAlpha(alpha);
//        	iv_share.setAlpha(alpha);
//        	view_line.setAlpha(alpha);
//        	tv_event_title.setAlpha(alpha);
//        }
//        ViewHelper.setTranslationY(ll_content, tvHeight * percent);
//        ViewHelper.setTranslationY(tv_event_title, tvHeight*percent*1.5f);
       
//        tv_event_title.setTextColor(evaluate(percent, Color.WHITE, Color.BLACK));
    }

    private void animatePull(int t) {
    	float percent = (float) t / range;
        if(t<0){
            rl_top.getLayoutParams().height = range - t;
            rl_top.requestLayout();
            iv_image.requestLayout();
        }
//        iv_back.setAlpha(1-percent);
//        tv_time.setAlpha(1-percent);
//        iv_operate.setAlpha(1-percent);
//        iv_share.setAlpha(1-percent);
//        view_line.setAlpha(1-percent);
//        tv_event_title.setAlpha(1-percent);
    }

    private Integer evaluate(float fraction, Object startValue, Integer endValue) {
        int startInt = (Integer) startValue;
        int startA = (startInt >> 24) & 0xff;
        int startR = (startInt >> 16) & 0xff;
        int startG = (startInt >> 8) & 0xff;
        int startB = startInt & 0xff;
        int endInt = endValue;
        int endA = (endInt >> 24) & 0xff;
        int endR = (endInt >> 16) & 0xff;
        int endG = (endInt >> 8) & 0xff;
        int endB = endInt & 0xff;
        return (startA + (int) (fraction * (endA - startA))) << 24
                | (startR + (int) (fraction * (endR - startR))) << 16
                | (startG + (int) (fraction * (endG - startG))) << 8
                | (startB + (int) (fraction * (endB - startB)));
    }

//    public void toggle() {
//        if (isOpen()) {
//            close();
//        } else {
//            open();
//        }
//    }

    private Status status;

    public enum Status {
        Open, Close
    }

    public boolean isOpen() {
        return status == Status.Open;
    }

    private void reset() {
        if (oa != null && oa.isRunning()) {
            return;
        }
        oa = ObjectAnimator.ofInt(this, "t", (int) -detalY / 5, 0);
        oa.setDuration(150);
        oa.start();
    }

    public void close() {
        if (oa != null && oa.isRunning()) {
            return;
        }
        oa = ObjectAnimator.ofInt(this, "t", getScrollY(), range);
        oa.setInterpolator(new DecelerateInterpolator());
        oa.addListener(new AnimatorListener() {
            @Override
            public void onAnimationStart(Animator arg0) {
                isTouchOrRunning = true;
            }

            @Override
            public void onAnimationRepeat(Animator arg0) {
            }

            @Override
            public void onAnimationEnd(Animator arg0) {
                isTouchOrRunning = false;
                status = Status.Close;
            }

            @Override
            public void onAnimationCancel(Animator arg0) {

            }
        });
        oa.setDuration(250);
        oa.start();
    }

    public void open() {
        if (oa != null && oa.isRunning()) {
            return;
        }
        oa = ObjectAnimator.ofInt(this, "t", getScrollY(), (int) (-getScrollY() / 2.2f), 0);
        oa.setInterpolator(new DecelerateInterpolator());
        oa.addListener(new AnimatorListener() {
            @Override
            public void onAnimationStart(Animator arg0) {
                isTouchOrRunning = true;
            }

            @Override
            public void onAnimationRepeat(Animator arg0) {
            }

            @Override
            public void onAnimationEnd(Animator arg0) {
                isTouchOrRunning = false;
                status = Status.Open;
            }

            @Override
            public void onAnimationCancel(Animator arg0) {

            }
        });
        oa.setDuration(400);
        oa.start();
    }
//	public void setTitleHeight(int height){
//		tvHeight = height;
//	}
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



    private ScrollViewListener scrollViewListener = null;
    public void setScrollViewListener(ScrollViewListener scrollViewListener) {
        this.scrollViewListener = scrollViewListener;
    }

    public interface ScrollViewListener {
        void onScrollChanged( int x, int y, int oldx, int oldy);

    }
    private boolean scrollBottom;

    public boolean isScrollBottom() {
        return scrollBottom;
    }

    public void setScrollBottom(boolean scrollBottom) {
        this.scrollBottom = scrollBottom;
    }
}

package com.futureinst.widget;

import com.futureinst.R;
import com.futureinst.utils.Utils;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

public class CardView extends FrameLayout {
	private static final float TEXT_MAX_SIZE = 24f;  // 文字最带值
    
    private ViewGroup mCardView; // 上面的cardlayout
    private View mFirstChild;   // 该布局下的第一个子view 
//    private TextView mCardText; // 文字
    private ImageView iv_operate;
	private ImageView iv_back;
	private ImageView iv_image;
	private TextView tv_time,tv_event_title;	
    
    
    
    private int mCardHeight;   // cardlayout的高度
    private int mStartY;       // 开始拖拽的y值
    private int mNowMarginTop; // 保存当前的margintop
     
    private boolean isShown = true;   // 记录cardlayout是否显示
     
    public CardView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }
     
    public CardView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        // 获取cardLayout
        mCardView = (ViewGroup) LayoutInflater.from(context).inflate(
                R.layout.eventdetail_top, null);
//        mCardText = (TextView) mCardView.findViewById(R.id.card_text);
        tv_time = (TextView) mCardView.findViewById(R.id.tv_time);
		iv_image = (ImageView) mCardView.findViewById(R.id.iv_image);
		iv_operate = (ImageView) mCardView.findViewById(R.id.iv_operate);
		iv_back = (ImageView) mCardView.findViewById(R.id.iv_back);
		tv_event_title = (TextView) mCardView.findViewById(R.id.tv_event_title);
		
        // 设置默认属性
//        mCardText.setTextSize(0.0f);
//        mCardView.setAlpha(0.0f);
         
        // 测量一下cardlayout
        measureView(mCardView);
        // 测量后获取高度
        mCardHeight = mCardView.getMeasuredHeight();
        // 添加到当前布局中
        addView(mCardView, 0);
    }
     
    // 测量view
    private void measureView(View view) {
        ViewGroup.LayoutParams lp = view.getLayoutParams();
        if(null == lp) {
            lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, Utils.dip2px(getContext(), 180));
            view.setLayoutParams(lp);
        }
         
        int widthMeasureSpec = ViewGroup.getChildMeasureSpec(0, 0, lp.width);
        int height = lp.height;
        int heightMeasureSpec;
         
        if(height > 0) {
            heightMeasureSpec = MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY);
        }else {
            heightMeasureSpec = MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED);
        }
         
        view.measure(widthMeasureSpec, heightMeasureSpec);
    }
     
    // 当布局加载完毕后
    // 这时getChildCount()才能取出值
    // 在前面getChildCount()为0
    // 因此这里开始才能使用getChildAt()
    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        if(getChildCount() <= 0) {
            System.err.println("no child was found!");
            return;
        }
         
        // 获取第一个布局文件中定义的第一个子view
        // 为什么是1呢？ 因为我们上面手工添加了一个。
        mFirstChild = getChildAt(1);
    }
     
    // 滑动时，设置效果和属性
    private void setCurrent(int topMargin) {
        // 如果超过了cardlayout的高度
        // 则最大就是cardlayout的高度
        if(topMargin > mCardHeight) {
            topMargin = mCardHeight;
        }
         
        // 同样，小于0了，最小就是0
        if(topMargin <= 0) {
            topMargin = 0;
        }
         
        // 根据现在的topmargin设置cardlayout的alpha
        mCardView.setAlpha(topMargin/(float)mCardHeight);
        // 根据现在的topmargin设置文本的大小
//        mCardText.setTextSize(topMargin/(float)mCardHeight * TEXT_MAX_SIZE);
         
        // 获取子view的layoutparams
        FrameLayout.LayoutParams lp = (LayoutParams) mFirstChild.getLayoutParams();
        if(null == lp) {
            lp = new FrameLayout.LayoutParams(
                    FrameLayout.LayoutParams.MATCH_PARENT,
                    FrameLayout.LayoutParams.WRAP_CONTENT);
        }
         
        // 设置margintop
        lp.topMargin = topMargin;
        mFirstChild.setLayoutParams(lp);
        mNowMarginTop = topMargin;
        invalidate(); // 刷新界面
    }
     
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
        case MotionEvent.ACTION_DOWN:
            mStartY = (int) event.getY(); // 当按下时，记录按下的y值
            break;
        case MotionEvent.ACTION_MOVE:
            int nowY = (int) event.getY();  // 移动时，获取y
            // 如果当前不是显示状态
            if(!isShown) {
                setCurrent(nowY - mStartY); // 非显示状态->显示状态：使用这个逻辑
            }else {
                setCurrent(mCardHeight-(mStartY - nowY)); // 显示状态->非显示状态：使用这个逻辑
            }
            break;
        case MotionEvent.ACTION_UP:
            mStartY = 0;  // 抬起的时候，重新设置mStartY
             
            // 抬起了，cardlayout只有两种状态
            // 显示/不显示
            // 如果现在的margin值大于cardlayout高度的二分之一，则全显示
            // 否则不显示
            if(mNowMarginTop >= mCardHeight / 2) {
                setCurrent(mCardHeight);
                isShown = true;
            }else {
                setCurrent(0);
                isShown = false;
            }
            break;
        }
         
        return true;
    }
    public boolean isShow(){
    	return isShown;
    }
}

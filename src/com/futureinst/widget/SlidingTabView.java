package com.futureinst.widget;

/**
 * Created by hao on 2015/10/15.
 */

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.futureinst.R;
import com.futureinst.utils.Utils;

public class SlidingTabView extends LinearLayout {
    private Context mContext;
    private String iconFontText,bottomText;
    private int smallIconTextSize = 25;
    private int smallTextSize = 14;
    private int maxIconTextSize = 35;
    private int maxTextSize = 16;
    public SlidingTabView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mContext = context;
        setWidget(attrs);
    }

    public SlidingTabView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        setWidget(attrs);
    }

    public SlidingTabView(Context context) {
        super(context);
        mContext = context;
        init();
    }
    @Override
    public void setSelected(boolean selected) {
        super.setSelected(selected);
        iconView.setSelected(selected);
        textView.setSelected(selected);
       LayoutParams layoutParams = null;
       LayoutParams layoutParams_text = null;

        if(selected){
            iconView.setTextSize(maxIconTextSize);
            textView.setTextSize(maxTextSize);
            iconView.setTextColor(getResources().getColor(R.color.text_color_white));
            textView.setTextColor(getResources().getColor(R.color.sliding_table_1));
            int height = Utils.getViewMeasuredHeight(iconView);
            layoutParams = new LayoutParams(height,height);
            iconView.setLayoutParams(layoutParams);

        }else{
            iconView.setTextSize(smallIconTextSize);
            textView.setTextSize(smallTextSize);
            iconView.setTextColor(getResources().getColor(R.color.text_color_4));
            textView.setTextColor(getResources().getColor(R.color.text_color_4));
            int height = Utils.getViewMeasuredHeight(iconView);
            layoutParams = new LayoutParams(height,height);
            iconView.setLayoutParams(layoutParams);
        }
        layoutParams_text = new LayoutParams(Utils.getViewMeasuredWidth(textView),Utils.getViewMeasuredWidth(textView));
        textView.setLayoutParams(layoutParams_text);
    }
    private IconSlidingTabView iconView;
    private TextView textView;
    public void setIconTextBackground(){
        iconView.setBackground(getResources().getDrawable(R.drawable.sliding_bg));
    }

    public void setIconFontText(String iconFontText){
        iconView.setText(iconFontText);
    }
    public void setBottomText(String bottomText){
        textView.setText(bottomText);
    }


    private void setWidget(AttributeSet attrs){
        TypedArray a = mContext.obtainStyledAttributes(attrs, R.styleable.HomeTab);
        iconFontText = a.getString(R.styleable.HomeTab_iconFontText);
        bottomText = a.getString(R.styleable.HomeTab_bottomText);
        iconView = new IconSlidingTabView(mContext);
        iconView.setTextSize(smallIconTextSize);
        iconView.setText(iconFontText);
        iconView.setTextColor(getResources().getColor(R.color.text_color_4));
        iconView.setGravity(Gravity.CENTER);
        textView = new TextView(mContext);
        textView.setTextSize(smallTextSize);
        textView.setGravity(Gravity.CENTER);
        textView.setText(bottomText);
        textView.setTextColor(getResources().getColor(R.color.text_color_4));
        this.setOrientation(LinearLayout.VERTICAL);
        setGravity(Gravity.CENTER);
        setMinimumWidth(Utils.dip2px(getContext(),80));
        this.addView(iconView);
        this.addView(textView);
    }
    private void init(){
        iconView = new IconSlidingTabView(mContext);
        iconView.setTextSize(smallIconTextSize);
        iconView.setTextColor(getResources().getColor(R.color.text_color_4));
        iconView.setGravity(Gravity.CENTER);
        textView = new TextView(mContext);
        textView.setTextSize(smallTextSize);
        textView.setGravity(Gravity.CENTER);
        textView.setTextColor(getResources().getColor(R.color.text_color_4));
        this.setOrientation(LinearLayout.VERTICAL);
        setGravity(Gravity.CENTER);
        this.addView(iconView);
        this.addView(textView);
    }

}


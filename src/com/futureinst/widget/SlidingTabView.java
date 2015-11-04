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

public class SlidingTabView extends LinearLayout {
    private Context mContext;
    private String iconFontText,bottomText;
    private int smallIconTextSize = 25;
    private int smallTextSize = 14;
    private int maxIconTextSize = 30;
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
        if(selected){
            iconView.setTextSize(maxIconTextSize);
            textView.setTextSize(maxTextSize);
            iconView.setTextColor(getResources().getColor(R.color.sliding_table_1));
            textView.setTextColor(getResources().getColor(R.color.sliding_table_1));
        }else{
            iconView.setTextSize(smallIconTextSize);
            textView.setTextSize(smallTextSize);
            iconView.setTextColor(getResources().getColor(R.color.sliding_table_0));
            textView.setTextColor(getResources().getColor(R.color.sliding_table_0));
        }
    }
    private IconSlidingTabView iconView;
    private TextView textView;

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
        iconView.setTextColor(getResources().getColor(R.color.sliding_table_0));
        iconView.setGravity(Gravity.CENTER);
        textView = new TextView(mContext);
        textView.setTextSize(smallTextSize);
        textView.setGravity(Gravity.CENTER);
        textView.setText(bottomText);
        textView.setTextColor(getResources().getColor(R.color.sliding_table_0));
        this.setOrientation(LinearLayout.VERTICAL);
        setGravity(Gravity.CENTER);
        this.addView(iconView);
        this.addView(textView);
    }
    private void init(){
        iconView = new IconSlidingTabView(mContext);
        iconView.setTextSize(smallIconTextSize);
        iconView.setTextColor(getResources().getColor(R.color.sliding_table_0));
        iconView.setGravity(Gravity.CENTER);
        textView = new TextView(mContext);
        textView.setTextSize(smallTextSize);
        textView.setGravity(Gravity.CENTER);
        textView.setTextColor(getResources().getColor(R.color.sliding_table_0));
        this.setOrientation(LinearLayout.VERTICAL);
        setGravity(Gravity.CENTER);
        this.addView(iconView);
        this.addView(textView);
    }

}


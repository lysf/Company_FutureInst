package com.futureinst.widget;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by hao on 2016/1/19.
 */
public class IconMonthAndDayView extends TextView {
    private Context context;
    public IconMonthAndDayView(Context context) {
        super(context);
        this.context = context;
        initView();
    }

    public IconMonthAndDayView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        initView();
    }

    public IconMonthAndDayView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        initView();
    }
    private void initView()  {
        Typeface iconfont = Typeface.createFromAsset(context.getAssets(), "iconfont_month_day.ttf");
        setTypeface(iconfont);
    }
}

package com.futureinst.widget;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

public class IconView extends TextView {
	
	private Context context;
	public IconView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		this.context = context;
		initView();
		// TODO Auto-generated constructor stub
	}

	public IconView(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;
		initView();
		// TODO Auto-generated constructor stub
	}

	public IconView(Context context) {
		super(context);
		this.context = context;
		initView();
		// TODO Auto-generated constructor stub
	}
	private void initView()  {
        Typeface iconfont = Typeface.createFromAsset(context.getAssets(), "iconfont.ttf");  
        setTypeface(iconfont);  
    } 
}

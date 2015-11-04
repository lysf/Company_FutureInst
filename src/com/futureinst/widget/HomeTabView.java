package com.futureinst.widget;

import com.futureinst.R;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.TextView;

public class HomeTabView extends LinearLayout {
	private Context mContext;
	private String iconFontText,bottomText;
	public HomeTabView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		mContext = context;
		setWidget(attrs);
	}

	public HomeTabView(Context context, AttributeSet attrs) {
		super(context, attrs);
		mContext = context;
		setWidget(attrs);
	}

	public HomeTabView(Context context) {
		super(context);
		mContext = context;
	}
	@Override
	public void setSelected(boolean selected) {
		super.setSelected(selected);
		iconView.setSelected(selected);
		textView.setSelected(selected);
		if(selected){
			iconView.setTextColor(getResources().getColor(R.color.home_table_1));
			textView.setTextColor(getResources().getColor(R.color.home_table_1));
		}else{
			iconView.setTextColor(getResources().getColor(R.color.home_table_0));
			textView.setTextColor(getResources().getColor(R.color.home_table_0));
		}
	}
	private IconView iconView;
	private TextView textView;
	private int[] style = R.styleable.HomeTab;

	private void setWidget(AttributeSet attrs){
		TypedArray a = mContext.obtainStyledAttributes(attrs, style);
		iconFontText = a.getString(R.styleable.HomeTab_iconFontText);
		bottomText = a.getString(R.styleable.HomeTab_bottomText);
		iconView = new IconView(mContext);
		iconView.setTextSize(22);
		iconView.setText(iconFontText);
		iconView.setTextColor(getResources().getColor(R.color.home_table_0));
		iconView.setGravity(Gravity.CENTER);
		textView = new TextView(mContext);
		textView.setTextSize(14);
		textView.setGravity(Gravity.CENTER);
		textView.setText(bottomText);
		textView.setTextColor(getResources().getColor(R.color.home_table_0));
		this.setOrientation(LinearLayout.VERTICAL);
		setGravity(Gravity.CENTER);
		this.addView(iconView);
		this.addView(textView);
	}
}

package com.futureinst.widget;

import com.futureinst.home.SystemTimeUtile;
import com.futureinst.utils.LongTimeUtil;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

public class TimeView extends TextView implements Runnable {
	private Long time;
	protected TimeView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		postDelayed(this, 500);
	}

	protected TimeView(Context context, AttributeSet attrs) {
		super(context, attrs,0);
	}

	protected TimeView(Context context) {
		super(context,null);
	}
	public void setTime(Long time){
		this.time = time;
	}
	@Override
	public void run() {
		if(time == 0) {
			setText("待清算");
			return;
		}
		long remainTime = time - SystemTimeUtile.getInstance(0L).getSystemTime();
		setText(LongTimeUtil.longTimeUtil(remainTime));
		postInvalidateDelayed(500);
	}

}

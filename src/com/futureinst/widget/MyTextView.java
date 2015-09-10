package com.futureinst.widget;

import com.futureinst.R;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.util.AttributeSet;
import android.widget.TextView;

public class MyTextView extends TextView {
	private Paint paint;
	public MyTextView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
		// TODO Auto-generated constructor stub
	}

	public MyTextView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
		// TODO Auto-generated constructor stub
	}

	public MyTextView(Context context) {
		super(context);
		init();
		// TODO Auto-generated constructor stub
	}
	private void init(){
		paint = new Paint();
		paint.setAntiAlias(true);
		paint.setStyle(Paint.Style.FILL);
		paint.setTextAlign(Align.CENTER);
		paint.setTextSize(40);
		paint.setColor(getResources().getColor(R.color.text_color_white));
		this.setBackground(getResources().getDrawable(R.drawable.special));
	}
	@Override
	protected void onDraw(Canvas canvas) {
		//倾斜度45,上下左右居中  
		canvas.rotate(-45, getMeasuredWidth()/3, getMeasuredHeight()/3);
//		canvas.drawText("专题", getWidth()/8, getHeight()-40, paint);
		super.onDraw(canvas);
	}
}

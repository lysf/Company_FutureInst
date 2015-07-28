package com.futureinst.widget;

import com.futureinst.R;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Paint.FontMetrics;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

public class CircleView extends View {
	private int width = 0;
	private int height = 0;
	private int color;
	private float r;
	private float pointX = 0;
	private float pointY = 0;
	private Paint paint1,paint2,paintText;
	private String textTop = "50",textBottom = "+0";
	private float v = 0.5f;//高度比例
	private float d ;//圆心到所需图形的距离
	public CircleView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init(context);
	}

	public CircleView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}

	public CircleView(Context context) {
		super(context);
		init(context);
	}
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		drawArc(canvas);
		drawText(canvas);
		drawCircle(canvas);
	}
	public void setColor(int color){
		this.color = color;
	}
	public void setV(float v){
		if(v<0 || v>1) return;
		this.v = v;
		if(v<=0.5){
			d = r*(1-2*v);
		}else{
			d = 2*r*(1-v);
		}
		invalidate();
	}
	public void setTopText(String top){
		this.textTop = top;
	}
	public void setBottomText(String bottom){
		this.textBottom = bottom;
	}
	private void init(Context context){
//		width = getWidth();
//		height = getHeight();
		width = 200;
		height = 200;
		r = width/2;
		pointX = width/2;
		pointY = height/2;
		color = getResources().getColor(R.color.gain_blue);
		if(v<=0.5){
			d = r*(1-2*v);
		}else{
			d = r*(2*v-1);
		}
		
		paint1 = new Paint();
		paint1.setAntiAlias(true);
		paint1.setStyle(Paint.Style.FILL);
		paint1.setColor(getResources().getColor(R.color.text_color_white));
		paint2 = new Paint();
		paint2.setAntiAlias(true);
		paint2.setStyle(Paint.Style.FILL);
		
		paintText = new Paint();
		paintText.setAntiAlias(true);
		paintText.setColor(Color.BLACK);
	}
	private void drawArc(Canvas canvas){
		paint2.setColor(color);
		RectF oval = new RectF(0, 0, width, height);
		float startAngle = 0,sweepAngle = 0;
		if(v<=0.5){
			canvas.drawCircle(pointX, pointY, r, paint1);
			 startAngle = (float) (Math.asin(d/r)/Math.PI*180);
			sweepAngle = (float) (180 - 2*startAngle);
			canvas.drawArc(oval, startAngle, sweepAngle, false, paint2);
		}else{
			canvas.drawCircle(pointX, pointY, r, paint2);
			if(v == 1) return;
			float Q = (float) (Math.asin(2*v - 1)/Math.PI*180);
			startAngle = 180 + Q;
			sweepAngle = 180 -2*Q;
			canvas.drawArc(oval, startAngle, sweepAngle, false, paint1);
		}
		
	}
	private void drawText(Canvas canvas){
		paintText.setTextSize(60);
		paintText.setTextAlign(Align.CENTER);
		FontMetrics fontMetrics = paintText.getFontMetrics(); 
		 // 计算文字高度 
		float fontHeight = fontMetrics.bottom - fontMetrics.top; 
		 // 计算文字baseline 
//		 float textBaseY = height - (height - fontHeight) / 2 - fontMetrics.bottom; 
		canvas.drawText(textTop, width/2, fontHeight, paintText);
		paintText.setTextSize(40);
		canvas.drawText(textBottom, width/2, 150, paintText);
	}
	private void drawCircle(Canvas canvas){
		Paint paint = new Paint();
		paint.setAntiAlias(true);
		paint.setStyle(Paint.Style.STROKE);
		paint.setColor(getResources().getColor(R.color.text_color_9));
		canvas.drawCircle(pointX, pointY, r, paint);
	}
}

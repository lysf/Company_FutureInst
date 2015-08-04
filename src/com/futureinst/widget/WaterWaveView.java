package com.futureinst.widget;

import com.futureinst.R;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.Paint.Align;
import android.graphics.Paint.FontMetrics;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.View;
/**
 *波浪效果
 */
@SuppressLint({ "DrawAllocation", "HandlerLeak" })
public class WaterWaveView extends View
{
	private Handler mHandler;
	private long c = 0L;
	private boolean down;
	private boolean start;
	private final float f = 0.033F;
	private int mAlpha = 70;//透明度
	private int mColor = Color.BLUE;
	private float mAmplitude = 2.0F; // 振幅
	private final Paint mPaint = new Paint();
	private float mWateLevel = 0.5F;//水高(0~1)
	private float autoLevel = 0F;//渐变水高(autoLevel<=mWateLevel)
	private Path mPath;
	private String textTop = "50",textBottom = "+0";
	private float d ;//圆心到所需图形的距离
	private Long delayTime = 1L;
	public WaterWaveView(Context paramContext)
	{
		super(paramContext);
		init(paramContext);
	}
	public WaterWaveView(Context paramContext, AttributeSet paramAttributeSet)
	{
		super(paramContext, paramAttributeSet);
		init(paramContext);
	}
	/**
	 * 开始波动
	 */
	public void startWave()
	{
		if (!start)
		{
			this.c = 0L;
			start = true;
			this.mHandler.sendEmptyMessage(0);
		}
	}
	
	
	private void init(Context context)
	{
		mPaint.setColor(mColor);
		mPaint.setAlpha(mAlpha);
		mPath = new Path();
		mHandler = new Handler(){
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			if(msg.what == 0){
				invalidate();
				if(delayTime < 30L){
					delayTime += 1;
				}
				if(start){
					mHandler.sendEmptyMessageDelayed(0, delayTime);
					
				}
			}
		}
		};
	}
	@Override
	protected void onDraw(Canvas canvas)
	{
		canvas.save();
        mPaint.setAlpha(mAlpha);
		mPaint.setColor(mColor);
		mPaint.setAntiAlias(true);
		if(!start){
			drawStatic(canvas);
		}else{
			if(!down){
				drawUp(canvas);
			}else{
				drawDown(canvas);
			}
		}
		mPaint.setColor(getResources().getColor(R.color.text_color_9));
		canvas.drawCircle(getWidth()/2, getHeight() / 2, getWidth()/2, mPaint);
		drawText(canvas);
	}
	private void drawUp(Canvas canvas){
		// 得到控件的宽高
				int width = getWidth();
				int height = getHeight();
				int r = width/2;
				if(autoLevel < mWateLevel){
					autoLevel += 0.01; 
				}
				if(autoLevel<=0.5){
					d = r*(1-2*autoLevel);
				}else{
					d = r*(2*autoLevel-1);
				}
				RectF oval = new RectF(0, 0, width, height);
				float startAngle = 0,sweepAngle = 0;
				mPaint.setStyle(Paint.Style.FILL);
				if(autoLevel<=0.5){
					mPaint.setColor(Color.WHITE);
					canvas.drawCircle(width/2, height/2, r, mPaint);
					 startAngle = (float) (Math.asin(d/r)/Math.PI*180);
					sweepAngle = (float) (180 - 2*startAngle);
					mPaint.setColor(mColor);
					canvas.drawArc(oval, startAngle, sweepAngle, false, mPaint);
				}else{
					mPaint.setColor(mColor);
					canvas.drawCircle(width/2, height/2, r, mPaint);
					if(autoLevel == 1) return;
					float Q = (float) (Math.asin(2*autoLevel - 1)/Math.PI*180);
					startAngle = 180 + Q;
					sweepAngle = 180 -2*Q;
					mPaint.setColor(Color.WHITE);
					canvas.drawArc(oval, startAngle, sweepAngle, false, mPaint);
				}
				mPaint.setStyle(Paint.Style.STROKE);
			
				if ((width == 0) || (height == 0))
				{	
					return;
				}
				if (this.c >= 8388607L)
				{
					this.c = 0L;
				}
				//每次onDraw时c都会自增
				this.c = (1L + this.c);
				float f1 = height * (1.0F - autoLevel);
				int top = (int) (f1 + mAmplitude);
				if(autoLevel > 0.5){
					top = (int) (f1 - mAmplitude/2);
				}
				mPath.reset();
				
				int disX =  (int) Math.sqrt(r*r - d*d);
				int startX = (int) (width/2 - disX);
				//波浪效果
				while (startX < width/2 + disX)
				{
					int startY = (int) (f1 - mAmplitude* Math.sin(Math.PI* (2.0F * (startX + this.c * width * this.f))/ width));
					if(autoLevel > 0.5){
						mPaint.setColor(Color.WHITE);
						
					}
					canvas.drawLine(startX, startY, startX, top, mPaint);
					startX++;
				}
				canvas.restore();
	}
	private void drawDown(Canvas canvas){
		// 得到控件的宽高
		int width = getWidth();
		int height = getHeight();
		int r = width/2;
		if(autoLevel > mWateLevel){
			autoLevel -= 0.01; 
		}
		if(autoLevel<=0.5){
			d = r*(1-2*autoLevel);
		}else{
			d = r*(2*autoLevel-1);                 
		}
		RectF oval = new RectF(0, 0, width, height);
		float startAngle = 0,sweepAngle = 0;
		mPaint.setStyle(Paint.Style.FILL);
		if(autoLevel<=0.5){
			mPaint.setColor(Color.WHITE);
			canvas.drawCircle(width/2, height/2, r, mPaint);
			startAngle = (float) (Math.asin(d/r)/Math.PI*180);
			sweepAngle = (float) (180 - 2*startAngle);
			mPaint.setColor(mColor);
			canvas.drawArc(oval, startAngle, sweepAngle, false, mPaint);
		}else{
			mPaint.setColor(mColor);
			canvas.drawCircle(width/2, height/2, r, mPaint);
			if(autoLevel == 1) return;
			float Q = (float) (Math.asin(2*autoLevel - 1)/Math.PI*180);
			startAngle = 180 + Q;
			sweepAngle = 180 -2*Q;
			mPaint.setColor(Color.WHITE);
			canvas.drawArc(oval, startAngle, sweepAngle, false, mPaint);
		}
		mPaint.setStyle(Paint.Style.STROKE);
		
		if ((width == 0) || (height == 0))
		{	
			return;
		}
		if (this.c >= 8388607L)
		{
			this.c = 0L;
		}
		//每次onDraw时c都会自增
		this.c = (1L + this.c);
		float f1 = height * (1.0F - autoLevel);
		int top = (int) (f1 + mAmplitude);
		if(autoLevel > 0.5){
			top = (int) (f1 - mAmplitude/2);
		}
		mPath.reset();
		
		int disX =  (int) Math.sqrt(r*r - d*d);
		int startX = (int) (width/2 - disX);
		//波浪效果
		while (startX < width/2 + disX)
		{
			int startY = (int) (f1 - mAmplitude* Math.sin(Math.PI* (2.0F * (startX + this.c * width * this.f))/ width));
			if(autoLevel > 0.5){
				mPaint.setColor(Color.WHITE);
				
			}
			canvas.drawLine(startX, startY, startX, top, mPaint);
			startX++;
		}
		canvas.restore();
	}
	private void drawStatic(Canvas canvas){
		// 得到控件的宽高
		int width = getWidth();
		int height = getHeight();
		int r = width/2;
		if(mWateLevel<=0.5){
			d = r*(1-2*mWateLevel);
		}else{
			d = r*(2*mWateLevel-1);
		}
		RectF oval = new RectF(0, 0, width, height);
		float startAngle = 0,sweepAngle = 0;
		mPaint.setStyle(Paint.Style.FILL);
		if(mWateLevel<=0.5){
			mPaint.setColor(Color.WHITE);
			canvas.drawCircle(width/2, height/2, r, mPaint);
			startAngle = (float) (Math.asin(d/r)/Math.PI*180);
			sweepAngle = (float) (180 - 2*startAngle);
			mPaint.setColor(mColor);
			canvas.drawArc(oval, startAngle, sweepAngle, false, mPaint);
		}else{
			mPaint.setColor(mColor);
			canvas.drawCircle(width/2, height/2, r, mPaint);
			if(mWateLevel == 1) return;
			float Q = (float) (Math.asin(2*mWateLevel - 1)/Math.PI*180);
			startAngle = 180 + Q;
			sweepAngle = 180 -2*Q;
			mPaint.setColor(Color.WHITE);
			canvas.drawArc(oval, startAngle, sweepAngle, false, mPaint);
		}
		mPaint.setStyle(Paint.Style.STROKE);
		
		if ((width == 0) || (height == 0))
		{	
			
			return;
		}
		if (this.c >= 8388607L)
		{
			this.c = 0L;
		}
		//每次onDraw时c都会自增
		this.c = (1L + this.c);
		float f1 = height * (1.0F - mWateLevel);
		int top = (int) (f1 + mAmplitude);
		if(mWateLevel > 0.5){
			top = (int) (f1 - mAmplitude/2);
		}
		mPath.reset();
		
		canvas.restore();
	}
	private void drawText(Canvas canvas){
		mPaint.setColor(Color.BLACK);
		mPaint.setStyle(Paint.Style.FILL);
		int width = getWidth();
		int height = getHeight();
		mPaint.setTextSize(60);
		mPaint.setTextAlign(Align.CENTER);
		FontMetrics fontMetrics = mPaint.getFontMetrics(); 
		 // 计算文字高度 
		float fontHeight = fontMetrics.bottom - fontMetrics.top; 
		 // 计算文字baseline 
//		 float textBaseY = height - (height - fontHeight) / 2 - fontMetrics.bottom; 
		canvas.drawText(textTop, width/2, height/2, mPaint);
		mPaint.setTextSize(40);
		canvas.drawText(textBottom, width/2, height/2 + 60, mPaint);
	}
	
	/**
	 * 设置振幅
	 * @param amplitued
	 */
	public void setAmplitude(float amplitued)
	{
		mAmplitude = amplitued;
	}
	/**
	 * 设置透明度
	 * @param alpha
	 */
	public void setWaterAlpha(float alpha)
	{
		this.mAlpha = ((int) (255.0F * alpha));
		mPaint.setAlpha(this.mAlpha);
	}
	/**
	 * 设置颜色
	 * @param color
	 */
	public void setColor(int color)
	{
		this.mColor = color;
	}
	
	/**
	 * 设置水面高度
	 * @param paramFloat
	 */
	public void setWaterLevel(float paramFloat)
	{
		mWateLevel = paramFloat;
	}
	
	@Override
	protected void onAttachedToWindow()
	{
		super.onAttachedToWindow();
		// 关闭硬件加速，防止异常unsupported operation exception
		this.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
	}
	
	public boolean isDown() {
		return down;
	}
	public void setDown(boolean down) {
		this.down = down;
		if(down){
			this.autoLevel = 1.0f;
		}
	}
	public String getTextTop() {
		return textTop;
	}
	public void setTextTop(String textTop) {
		this.textTop = textTop;
	}
	public String getTextBottom() {
		return textBottom;
	}
	public void setTextBottom(String textBottom) {
		this.textBottom = textBottom;
	}
	public void stop(){
		start = false;
	}
}
package com.futureinst.home.forecast;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import static android.graphics.Paint.ANTI_ALIAS_FLAG;

/**
 * Created by hao on 2015/12/23.
 */
public class PagerIndictorView extends View {
    private boolean isSelected = false;
    private float mRadius = 5;
    private Paint mPaint =  new Paint(ANTI_ALIAS_FLAG);
    private int fillColor = Color.parseColor("#ffd311");
    private int strokeColor = Color.parseColor("#ffffff");
    private float swrokeWidth = 0;

    public PagerIndictorView(Context context) {
        super(context);
    }

    public PagerIndictorView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PagerIndictorView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void setSelected(boolean selected) {
        isSelected = selected;
        invalidate();
        super.setSelected(selected);

    }

    public void setRadius(float radius) {
        mRadius = radius;
        invalidate();
    }

    public float getRadius() {
        return mRadius;
    }


    public int getFillColor() {
        return fillColor;
    }

    public void setFillColor(int fillColor) {
        this.fillColor = fillColor;
        invalidate();
    }

    public int getStrokeColor() {
        return strokeColor;
    }

    public void setStrokeColor(int strokeColor) {
        this.strokeColor = strokeColor;
        invalidate();
    }

    public float getSwrokeWidth() {
        return swrokeWidth;
    }

    public void setSwrokeWidth(float swrokeWidth) {
        this.swrokeWidth = swrokeWidth;
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if(isSelected){
            mPaint.setStyle(Paint.Style.FILL);
            mPaint.setColor(fillColor);
            canvas.drawCircle(getWidth()/2,getHeight()/2,mRadius + 2,mPaint);
        }else{
            mPaint.setStyle(Paint.Style.FILL);
            mPaint.setColor(strokeColor);
            canvas.drawCircle(getWidth() / 2, getHeight() / 2, mRadius, mPaint);
        }

    }
}

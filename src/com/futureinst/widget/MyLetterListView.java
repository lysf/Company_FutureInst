package com.futureinst.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class MyLetterListView extends View {

    private  OnTouchingLetterChangedListener onTouchingLetterChangedListener;
    private  String[] mletters = { "#", "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S",
            "T", "U", "V", "W", "X", "Y", "Z" };
    private int choose = -1;
    private Paint paint = new Paint();
   private boolean showBkg = false;

    public MyLetterListView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public MyLetterListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyLetterListView(Context context) {
        super(context);
    }

    public void setLetters(String[] letter) {
        mletters = letter;
        requestLayout();
    }
    /**
     * @see android.view.View#onMeasure(int, int)
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthsize = MeasureSpec.getSize(widthMeasureSpec);
        int widthmodel = MeasureSpec.getMode(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        if (widthmodel != MeasureSpec.EXACTLY) {
            widthsize = height / mletters.length;
             paint.setTextSize((float)widthsize*0.9f);
             widthsize = (int) (widthsize*1.5);
        }

        setMeasuredDimension(widthsize, height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (showBkg) {
            canvas.drawColor(Color.parseColor("#ffffff"));
        }
        int height = getHeight();
        int width = getWidth();
        int singleHeight = height / mletters.length;
        for (int i = 0; i < mletters.length; i++) {
            paint.setAntiAlias(true);
            paint.setColor(Color.parseColor("#999999"));
            if (i == choose) {
                paint.setColor(Color.parseColor("#3399ff"));
            }
            float xPos = width / 2 - paint.measureText(mletters[i]) / 2;
            float yPos = singleHeight * i + singleHeight;
            canvas.drawText(mletters[i], xPos, yPos, paint);
        }

    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        final int action = event.getAction();
        final float y = event.getY();
        final int oldChoose = choose;
        final OnTouchingLetterChangedListener listener = onTouchingLetterChangedListener;
        final int c = (int) (y / getHeight() * mletters.length);

        switch (action) {
        case MotionEvent.ACTION_DOWN:
            showBkg = true;
            if (oldChoose != c && listener != null) {
                if (c >= 0 && c < mletters.length) {
                    listener.onTouchingLetterChanged(mletters[c]);
                    choose = c;
                    invalidate();
                }
            }

            break;
        case MotionEvent.ACTION_MOVE:
            if (oldChoose != c && listener != null) {
                if (c >= 0 && c < mletters.length) {
                    listener.onTouchingLetterChanged(mletters[c]);
                    choose = c;
                    invalidate();
                }
            }
            break;
        case MotionEvent.ACTION_UP:
            showBkg = false;
            choose = -1;
            invalidate();
            break;
        }
        return true;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event);
    }

    public void setOnTouchingLetterChangedListener(OnTouchingLetterChangedListener onTouchingLetterChangedListener) {
        this.onTouchingLetterChangedListener = onTouchingLetterChangedListener;
    }

    public interface OnTouchingLetterChangedListener {
        public void onTouchingLetterChanged(String s);
    }

}

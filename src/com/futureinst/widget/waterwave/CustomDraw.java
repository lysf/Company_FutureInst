package com.futureinst.widget.waterwave;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;

import com.futureinst.R;


/**
 * Created by hao on 2015/10/26.
 */
public class CustomDraw extends View {
    private float price = 0f, price_1 = 0.0f, update_price = 0.0f;
    private float sweepAngle = 0f, sweepAngle_1 = 0f;


    private long total_time = 400;//完成动画所需总时间
    private boolean sweepOK = false, priceOK = false, isStart = false, X1OK = false, X2Ok = false;
    private Paint mPaint;
    private int lineColor = Color.parseColor("#444444");
    private float r1, r2;
    private float dis = 40.0f;//两圆形间的间距
    float startX = 40.0f, startY = 40.0f;
    private long delay_time = 20;
    private float pointX, pointY;
    private int[] color_red = new int[]{Color.parseColor("#ffe8e8"), Color.parseColor("#ffcbcb"),
            Color.parseColor("#ff6565"), Color.parseColor("#fa2246")};
    private int[] color_blue = new int[]{Color.parseColor("#f5f8dc"), Color.parseColor("#e0f997"),
            Color.parseColor("#bad311"), Color.parseColor("#b3fc04")};
    private float x1, y1, x2, y2, x3, y3;
    private float X1, X2;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    invalidate();
                    break;
                case 1:
                    invalidate();
                    break;
            }
        }
    };

    public CustomDraw(Context context) {
        super(context);
        init(context);
    }

    public CustomDraw(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public CustomDraw(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.FILL);
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        r1 = metrics.widthPixels / 4;
        r2 = r1 - dis;
        pointY = pointX = r1 + startX;
        x1 = r1 + startX - 15;
        y1 = startY + r1 * 5 / 3;
        x2 = startX + 3 * r1 / 2 - 15;
        y2 = startY + 5 * r1 / 4;
        x3 = startX + (int)(9.5 * r1 / 3);
        y3 = startY + 5 * r1 / 4;
        X1 = x1;
        X2 = x2;
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        drawBase(canvas);
        if (!isStart) return;
        drawArc(canvas);

    }

    //画底层的图形
    private void drawBase(Canvas canvas) {
        if (update_price >= 0) {
            mPaint.setColor(color_red[0]);
        } else {
            mPaint.setColor(color_blue[0]);
        }
        canvas.drawCircle(pointX, pointY, r1, mPaint);
        if (update_price >= 0) {
            mPaint.setColor(color_red[1]);
        } else {
            mPaint.setColor(color_blue[1]);
        }
        canvas.drawCircle(pointX, pointY, r2, mPaint);
    }

    //画扇形
    private void drawArc(Canvas canvas) {
        if (update_price >= 0) {
            mPaint.setColor(color_red[2]);
        } else {
            mPaint.setColor(color_blue[2]);
        }
        sweepAngle_1 += sweepAngle * delay_time / total_time;//没隔delay_time所增加的角度
        if (sweepAngle_1 >= sweepAngle) {
            sweepAngle_1 = sweepAngle;
            sweepOK = true;
        }
        RectF oval1 = new RectF(startX, startY, 2 * r1 + startX, 2 * r1 + startY);
        canvas.drawArc(oval1, 90, sweepAngle_1, true, mPaint);
        if (update_price >= 0) {
            mPaint.setColor(color_red[3]);
        } else {
            mPaint.setColor(color_blue[3]);
        }
        RectF oval2 = new RectF(startX + dis, startY + dis, 2 * r1 - dis + startX, 2 * r1 - dis + startY);
        canvas.drawArc(oval2, 90, sweepAngle_1, true, mPaint);
        canvas.rotate(sweepAngle_1, pointX, pointY);
        if (update_price >= 0) {
            mPaint.setColor(color_red[2]);
        } else {
            mPaint.setColor(color_blue[2]);
        }
        canvas.drawCircle(r1 + startX, 2 * r1 + startY + 20, 8, mPaint);
        if (update_price >= 0) {
            mPaint.setColor(color_red[3]);
        } else {
            mPaint.setColor(color_blue[3]);
        }
        canvas.drawCircle(r1 + startX, 2 * r1 + startY + 20, 5, mPaint);
        canvas.drawLine(r1 + startX, 2 * r1 + startY + 20, pointX, pointY, mPaint);
        canvas.rotate(-sweepAngle_1, pointX, pointY);
        if (!sweepOK) {
            handler.sendEmptyMessageDelayed(0, delay_time);
        } else {
            drawLine(canvas);

        }
    }

    private void drawLine(Canvas canvas) {
        mPaint.setColor(lineColor);
        canvas.drawCircle(r1 + startX - 15, r1 * 5 / 3 + startY, 5, mPaint);
        X1 += (x2 - x1) * delay_time * 2 / total_time;
        if (X1 >= x2) {
            X1 = x2;
            X1OK = true;
        }
        canvas.drawLine(x1, y1, X1, (X1 - x1) * (y2 - y1) / (x2 - x1) + y1, mPaint);
        if (!X1OK) {
            handler.sendEmptyMessageDelayed(0, delay_time);
        }
        if (X1OK) {
            X2 += (x3 - x2) * delay_time * 2 / total_time;
            if (X2 >= x3) {
                X2 = x3;
                X2Ok = true;
            }
            canvas.drawLine(x2, y2, X2, y3, mPaint);
            if (!X2Ok) {
                handler.sendEmptyMessageDelayed(0, delay_time);
            } else {

                drawOther(canvas);
            }
        }
    }
        //画数字
    private void drawOther(Canvas canvas) {

        mPaint.setTextAlign(Paint.Align.RIGHT);
        mPaint.setTextSize(80);
        price_1 += price * delay_time / total_time;
        if (price_1 >= price) {
            price_1 = price;
            priceOK = true;
        }
        canvas.drawText(String.format("%.1f", price_1), x3, startY + 5 * r1 / 4 - 10, mPaint);
        mPaint.setTextSize(32);

        Paint.FontMetrics fontMetrics = mPaint.getFontMetrics();
        float top = fontMetrics.ascent;
        float bottom = fontMetrics.descent;
        float textWitdh = mPaint.measureText(String.format("%.1f", update_price));

        canvas.drawText(String.format("%.1f", Math.abs(update_price)), x3, startY + 5 * r1 / 4 + 30, mPaint);

        Bitmap bitmap = null;
        if (update_price >= 0) {
            bitmap = BitmapFactory.decodeStream(getResources().openRawResource(R.raw.up));
        } else {
            bitmap = BitmapFactory.decodeStream(getResources().openRawResource(R.raw.down));
        }

        drawImage(canvas, bitmap, (int) (x3 - textWitdh - (bottom - top) * 2 / 3 - 5), (int) (startY + 5 * r1 / 4 + 6), (int) ((bottom - top) * 2) / 3, (int) ((bottom - top) * 2) / 3);
        if (!priceOK) {
            handler.sendEmptyMessageDelayed(1, delay_time);
        }
    }

    public static void drawImage(Canvas canvas, Bitmap blt, int x, int y,
                                 int w, int h) {
        Rect dst = new Rect();// 屏幕 >>目标矩形
        dst.left = x;
        dst.top = y;
        dst.right = x + w;
        dst.bottom = y + h;
        canvas.drawBitmap(blt, null, dst, null);
        dst = null;
    }


    public void start() {
        isStart = true;//开始动画
        invalidate();
    }

    public void setPrice(float price) {
        this.price = price;
        this.sweepAngle = (float) (price * 3.6);//价格为0-100，所旋转角度从0-360
        invalidate();
    }

    public void setUpdate_price(float updatePrice) {
        this.update_price = updatePrice;
        invalidate();
    }

}

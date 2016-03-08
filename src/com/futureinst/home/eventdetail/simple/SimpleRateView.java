package com.futureinst.home.eventdetail.simple;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.View;

import com.futureinst.R;
import com.futureinst.utils.Utils;

/**
 * Created by yanhuihao on 16/1/29.
 */
public class SimpleRateView extends View {
    private Paint mPaint;
    private int height;
    private int width;
    private int buyColor;
    private int sellColor;
    private int textColor;
    private int defaultColor;
    private int startY = 20;
    public static int LookGOOD = 1;
    public static int LookBAD = 2;
    public static int COMPARING = 0;
    private int attitude = 0;

    private String tip = "预测准确者将按投入比例赢得对手阵营未币";

    private float angle = 30;//倾斜角度(垂直方向)
    private int distance;//偏移的距离
    private int margin;

    private Long animalTime = 500l;//动画持续时间
    private int delayTime = 20;
    private Long operateTime = 0l;//动画执行时间
    private int buyNum = 1,buyCoins = 2000,sellNum = 20,sellCoins = 500;

    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 1:
                    invalidate();
                    break;
            }
        }
    };

    public SimpleRateView(Context context) {
        super(context);
        initView(context);
    }

    public SimpleRateView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public SimpleRateView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }
    private void initView(Context context){
        mPaint = new Paint();
        buyColor = getResources().getColor(R.color.gain_red);
        sellColor = getResources().getColor(R.color.gain_blue);
        textColor = getResources().getColor(R.color.text_color_b);
        defaultColor = getResources().getColor(R.color.text_color_bf);
        margin = Utils.dip2px(context,20);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPaint.setColor(defaultColor);
        width = getWidth();
        height = Utils.dip2px(getContext(),40);
        distance = (int) (height*Math.tan(Math.PI*angle/180)/2);
        mPaint.setStyle(Paint.Style.STROKE);
//        canvas.drawRect(0, startY, width, height + startY, mPaint);
        float rate = 1;
        if(sellCoins == 0 ){
            if(buyCoins == 0)
            rate = 0.5f;
        }else{
            rate = buyCoins*1.0f/(sellCoins + buyCoins);
        }

        if(operateTime < animalTime){
            operateTime += delayTime;
            handler.sendEmptyMessageDelayed(1, 2*delayTime*operateTime/animalTime);
        }
        initDraw(canvas, rate * operateTime / animalTime, buyColor);
        canvas.rotate(180, width / 2, height / 2 + startY);
        initDraw(canvas, (1 - rate) * operateTime / animalTime, sellColor);
        canvas.rotate(-180, width / 2, height / 2 + startY) ;
        drawText(canvas, buyNum, buyCoins, sellNum, sellCoins);
        if(operateTime >= animalTime){
            drawWin(canvas,attitude);

        }
    }
    //动画
    private void initDraw(Canvas canvas,float rate,int color){
        Path path = new Path();
        mPaint.setColor(color);
        mPaint.setStyle(Paint.Style.FILL);
        path.moveTo(0, height+ startY);
        if(width*(1 - rate) < distance){
            int X = (int) Math.sqrt(2 * width * height * (1 - rate) *  Math.tan(Math.PI * angle/ 180));
            path.lineTo(width,height + startY);
            path.lineTo(width, (float) (X/Math.tan(Math.PI * angle / 180))+ startY);
            path.lineTo(width-X, startY);
            path.lineTo(0, startY);
        }
        else if(width*rate < distance){
            int X = (int) Math.sqrt(2 * width * height * rate * Math.tan(Math.PI * angle / 180));
            path.lineTo(X,height+ startY);
            path.lineTo(0, (float) (height - X/Math.tan(Math.PI * angle / 180))+ startY);
        }
        else{
            path.lineTo(width * rate + distance, height+ startY);
            path.lineTo(width * rate - distance,  startY);
            path.lineTo(0, startY);
        }
        path.close();
        canvas.drawPath(path, mPaint);
    }


    //画文字
    private void drawText(Canvas canvas,int buyNum,int buyCoins,int sellNum,int sellCoins){
        mPaint.setColor(getResources().getColor(R.color.text_color_9));
        mPaint.setTextAlign(Paint.Align.CENTER);
        mPaint.setTextSize(50);
        mPaint.setFakeBoldText(true);
        mPaint.setStrokeWidth(5);
        mPaint.setTypeface(Typeface.DEFAULT_BOLD);
        canvas.drawText("PK",width/2,height + startY + margin,mPaint);
        mPaint.setTextSize(26);
        mPaint.setColor(textColor);
        mPaint.setTextAlign(Paint.Align.LEFT);
        canvas.drawText(buyNum + "人投入" + buyCoins + "未币", 0, height + startY + margin - 5, mPaint);
        mPaint.setTextAlign(Paint.Align.RIGHT);
        canvas.drawText(sellNum + "人投入" + sellCoins + "未币", width, height + startY + margin - 5, mPaint);
        mPaint.setTextAlign(Paint.Align.CENTER);
        mPaint.setColor(getResources().getColor(R.color.text_color_4));
        mPaint.setTextSize(26);
        canvas.drawText(tip, width / 2, height + startY + margin * 2.5f, mPaint);
    }
    //画图（赢）
    private void drawWin(Canvas canvas,int attitude){//attitude: 1看好胜出，2不看好胜出，0还未有结果
        if(attitude == COMPARING) return;
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.win);
        Rect dst = new Rect();// 屏幕 >>目标矩形
        int x = 0 ,y = 0,w = height + 30,h = height + 30;
        if(attitude == LookBAD){
            x = width  - w;
        }
        dst.left = x;
        dst.top = y;
        dst.right = x + w;
        dst.bottom = y + h;

        canvas.drawBitmap(bitmap,null,dst,null);
        bitmap.recycle();
    }
    //设置动画时间
    public void setAnimalTime(Long animalTime){
        this.animalTime = animalTime;
    }

    /**
     * 初始化数据
     * @param buyNum
     * @param buyCoins
     * @param sellNum
     * @param sellCoins
     * @param attitude 0还未结算，1看好赢，2不看好赢
     */
    public void initData(int buyNum,float buyCoins,int sellNum,float sellCoins,int attitude){
        this.buyNum = buyNum;
        this.buyCoins = (int)buyCoins;
        this.sellNum = sellNum;
        this.sellCoins = (int)sellCoins;
        this.attitude = attitude;
        operateTime = 0l;
        invalidate();
    }
}

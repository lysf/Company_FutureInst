package com.futureinst.home.eventdetail.eventanim;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.drawable.AnimationDrawable;
import android.os.Handler;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.futureinst.R;
import com.futureinst.utils.DialogShow;

/**
 * Created by yanhuihao on 16/3/1.
 */
public class EventAnimDialog {
    //獲利時：预测神准，贏得N未幣！ 虧損時：看走眼了，亏损N未幣...  打平時：不赔不赚，你是来打酱油的？

    /**
     *
     * @param activity
     * @param win 获利数
     */
    public void showAnimDialog(Activity activity,float win){
        String message ="";
        final AnimationDrawable animationDrawable;
        View view = LayoutInflater.from(activity).inflate(R.layout.view_event_anim,null);
        final Dialog dialog = DialogShow.showDialog(activity, view, Gravity.CENTER);
        ImageView iv_anim = (ImageView) view.findViewById(R.id.iv_anim);
        TextView tv_message = (TextView) view.findViewById(R.id.tv_message);
        if(win == 0){
            message = "不赔不赚，你是来打酱油的？";
            iv_anim.setImageResource(R.drawable.anim_event_tie);
        }else if(win < 0){//亏损
            message = "看走眼了，亏损"+Math.abs(win)+"未币...";
            iv_anim.setImageResource(R.drawable.anim_event_lost);
        }else{
            message = "预测神准，赢得"+win+"未币";
            iv_anim.setImageResource(R.drawable.anim_event_win);
        }
        tv_message.setText(message);
        animationDrawable = (AnimationDrawable) iv_anim.getDrawable();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                animationDrawable.start();
            }
        }, 200);
        Button btn_submit = (Button) view.findViewById(R.id.btn_submit);
        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();

    }
}

package com.futureinst.home;

import android.app.Activity;
import android.app.Dialog;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.futureinst.R;
import com.futureinst.sharepreference.SharePreferenceUtil;
import com.futureinst.utils.DialogShow;

/**
 * Created by hao on 2015/11/4.
 */
public class WelcomeDialog {
    public static void showWelcom(Activity activity){
       final SharePreferenceUtil preferenceUtil = SharePreferenceUtil.getInstance(activity);
        View view = LayoutInflater.from(activity).inflate(R.layout.view_diaolog_welcom,null,false);
        Button btn_ok = (Button) view.findViewById(R.id.btn_ok);
        final Dialog dialog = DialogShow.showDialog(activity,view, Gravity.CENTER);
        dialog.show();
        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                preferenceUtil.setGuide1();
                dialog.dismiss();
            }
        });

    }
    public static void showTip(Activity activity,String title,String message){
        final SharePreferenceUtil preferenceUtil = SharePreferenceUtil.getInstance(activity);
        View view = LayoutInflater.from(activity).inflate(R.layout.view_diaolog_welcom, null, false);
        Button btn_ok = (Button) view.findViewById(R.id.btn_ok);
        TextView tv_message = (TextView) view.findViewById(R.id.tv_message);
        TextView tv_title = (TextView) view.findViewById(R.id.tv_title);
        tv_title.setVisibility(View.VISIBLE);
        tv_title.setText(title);
        tv_message.setText(message);
        final Dialog dialog = DialogShow.showDialog(activity,view, Gravity.CENTER);
        dialog.show();
        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                preferenceUtil.setGuide1();
                dialog.dismiss();
            }
        });

    }
}

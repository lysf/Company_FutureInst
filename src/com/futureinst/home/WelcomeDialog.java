package com.futureinst.home;

import android.app.Activity;
import android.app.Dialog;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

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
}

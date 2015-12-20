package com.futureinst.home.eventdetail.eventdetailabout;

import android.app.Activity;
import android.app.Dialog;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.futureinst.R;
import com.futureinst.utils.DialogShow;

/**
 * Created by hao on 2015/12/18.
 */
public class EventRuleDialog {

    //事件规则
    public static void showDialog(final Activity activity,final String rule) {
        View view = LayoutInflater.from(activity).inflate(R.layout.view_event_rule, null, false);
        Button btn_submit = (Button) view.findViewById(R.id.btn_submit);
        TextView tv_rule = (TextView) view.findViewById(R.id.tv_rule);
        tv_rule.setText(rule);
        final Dialog dialog = DialogShow.showDialog(activity, view, Gravity.CENTER);
        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });
        dialog.show();
    }
}

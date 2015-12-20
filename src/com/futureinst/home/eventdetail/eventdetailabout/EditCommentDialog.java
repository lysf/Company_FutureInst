package com.futureinst.home.eventdetail.eventdetailabout;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.futureinst.R;
import com.futureinst.comment.AddCommentActivity;
import com.futureinst.utils.DialogShow;

/**
 * Created by hao on 2015/12/18.
 */
public class EditCommentDialog {
    //评论提示
    public static void showEditCommentDialog(final Activity activity,final int type,final String event_id) {
        View view = LayoutInflater.from(activity).inflate(R.layout.view_event_order_config, null, false);
        Button btn_cancel = (Button) view.findViewById(R.id.btn_cancel);
        TextView tv_configMsg = (TextView) view.findViewById(R.id.tv_configMsg);
        String configMsg = "";
        switch (type) {//type 1-看好,3-不看好
            case 1:
                configMsg = "你已经看好咯，马上去支持两句！";
                break;
            case 3:
                configMsg = "你不看好哦，马上去吐个槽！";
                break;
        }
        tv_configMsg.setText(configMsg);
        Button btn_config = (Button) view.findViewById(R.id.btn_submit);
        final Dialog dialog = DialogShow.showDialog(activity, view, Gravity.CENTER);
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });
        btn_config.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, AddCommentActivity.class);
                intent.putExtra("eventId", event_id);
                intent.putExtra("attitude", type);
                activity.startActivity(intent);
                dialog.cancel();
            }
        });
        dialog.show();
    }
}

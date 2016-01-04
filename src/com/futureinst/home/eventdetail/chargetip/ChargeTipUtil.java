package com.futureinst.home.eventdetail.chargetip;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.futureinst.R;
import com.futureinst.charge.ChargeGoodsListActivity;
import com.futureinst.utils.DialogShow;

/**
 * Created by hao on 2015/12/28.
 */
public class ChargeTipUtil {
    public final static String CHARGE_TIP1 = "亲，未币不多了，充点呗？";
    public final static String CHARGE_TIP2 = "下单失败，未币不够了......";
    public static void showChargeTip(final Activity activity,String message){
        View view = LayoutInflater.from(activity).inflate(R.layout.view_charge_tip,null);
        ImageView iv_cancel = (ImageView) view.findViewById(R.id.iv_cancel);
        TextView tv_tip = (TextView) view.findViewById(R.id.tv_tip);
        Button btn_charge = (Button) view.findViewById(R.id.btn_charge);
        tv_tip.setText(message);
       final Dialog dialog = DialogShow.showDialog(activity,view, Gravity.CENTER);
        iv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });
        btn_charge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity,ChargeGoodsListActivity.class);
                activity.startActivity(intent);
                dialog.cancel();
            }
        });
        dialog.show();
    }

}

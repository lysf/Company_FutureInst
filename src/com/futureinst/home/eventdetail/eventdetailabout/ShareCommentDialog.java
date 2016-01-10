package com.futureinst.home.eventdetail.eventdetailabout;

import android.app.Activity;
import android.app.Dialog;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.futureinst.R;
import com.futureinst.model.homeeventmodel.QueryEventDAO;
import com.futureinst.net.HttpPath;
import com.futureinst.share.OneKeyShareUtil;
import com.futureinst.sharepreference.SharePreferenceUtil;
import com.futureinst.utils.DialogShow;

import cn.sharesdk.sina.weibo.SinaWeibo;
import cn.sharesdk.wechat.friends.Wechat;
import cn.sharesdk.wechat.moments.WechatMoments;

/**
 * Created by hao on 2015/12/18.
 */
public class ShareCommentDialog {

    //下单分享提示
    public static void showShareCommentDialog(final Activity activity,final int type,final QueryEventDAO event,final int share_award) {
        View view = LayoutInflater.from(activity).inflate(R.layout.view_event_order_config, null, false);
        Button btn_cancel = (Button) view.findViewById(R.id.btn_cancel);
        TextView tv_configMsg = (TextView) view.findViewById(R.id.tv_configMsg);
        String configMsg = "快找朋友一起来预测吧！";

        tv_configMsg.setText(configMsg);
        Button btn_config = (Button) view.findViewById(R.id.btn_submit);
        btn_cancel.setText("暂不分享");
        btn_config.setText("立即分享");
        final Dialog dialog = DialogShow.showDialog(activity, view, Gravity.CENTER);
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });
        btn_config.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {//分享下单
                if (event == null)
                    return;
                showShareDialog(activity,event, type,share_award);
                dialog.cancel();
            }
        });
        dialog.show();
    }

    //分享界面
    public static void showShareDialog(final Activity activity ,final QueryEventDAO event, int type,final int share_award) {

        String title = event.getLead() + " 你敢猜吗？";
        final SharePreferenceUtil preferenceUtil  = SharePreferenceUtil.getInstance(activity);
        if (type == 1) {//看好
            title = event.getLead() + " 我猜会发生！";
        } else if (type == 3) {//不看好
            title = event.getLead() + " 我猜不发生！";
        }
        final String shareTitle = title;
        final String content = "来自未来研究所";
        View view = LayoutInflater.from(activity).inflate(R.layout.view_share_gridview, null);
        final Dialog dialog = DialogShow.showDialog(activity, view, Gravity.BOTTOM);
        TextView tv_sina = (TextView) view.findViewById(R.id.tv_sina);
        TextView tv_wechat = (TextView) view.findViewById(R.id.tv_wechat);
        TextView tv_wechatmonets = (TextView) view.findViewById(R.id.tv_wechatmoments);
        TextView tv_cancel = (TextView) view.findViewById(R.id.tv_cancel);
        tv_sina.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OneKeyShareUtil.showShare(activity, event.getId() + "", share_award, shareTitle,
                        HttpPath.SHARE_URL + event.getId() + "?user_id=" + preferenceUtil.getID(),
                        shareTitle + HttpPath.SHARE_URL + event.getId() + "?user_id=" + preferenceUtil.getID(), null, event.getImgsrc(), true, SinaWeibo.NAME);
                dialog.dismiss();
            }
        });
        tv_wechat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OneKeyShareUtil.showShare(activity, event.getId() + "", share_award, shareTitle, HttpPath.SHARE_URL + event.getId() + "?user_id=" + preferenceUtil.getID(),
                        content, null, event.getImgsrc(), true, Wechat.NAME);
                dialog.dismiss();
            }
        });
        tv_wechatmonets.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OneKeyShareUtil.showShare(activity, event.getId()+"", share_award, shareTitle, HttpPath.SHARE_URL + event.getId() + "?user_id=" + preferenceUtil.getID(),
                        content, null, event.getImgsrc(), true, WechatMoments.NAME);
                dialog.dismiss();
            }
        });
        tv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    //即看好又不看好（选择一种分享）（1：看好，3：不看好）
    public static void showShareCheckDialog(final Activity activity,final QueryEventDAO event,final int share_award) {
        View view = LayoutInflater.from(activity).inflate(R.layout.view_event_order_config, null, false);
        Button btn_cancel = (Button) view.findViewById(R.id.btn_cancel);
        TextView tv_configMsg = (TextView) view.findViewById(R.id.tv_configMsg);
        Button btn_config = (Button) view.findViewById(R.id.btn_submit);
        String configMsg = "你同时看多又看空该\n事件，分享哪一个？";
        tv_configMsg.setText(configMsg);
        btn_cancel.setText("分享看空");
        btn_config.setText("分享看好");
        final Dialog dialog = DialogShow.showDialog(activity, view, Gravity.CENTER);
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {//不看好8
//                showShareCommentDialog(activity,3,event,share_award);
                showShareDialog(activity, event, 3, share_award);
                dialog.cancel();
            }
        });
        btn_config.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {//看好
//                showShareCommentDialog(activity,1,event,share_award);
                showShareDialog(activity,event, 1,share_award);
                dialog.cancel();
            }
        });
        dialog.show();
    }

}

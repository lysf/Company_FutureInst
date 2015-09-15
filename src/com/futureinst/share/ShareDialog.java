package com.futureinst.share;

import com.futureinst.R;
import com.futureinst.model.basemodel.ShareDAO;
import com.futureinst.utils.DialogShow;

import android.app.Activity;
import android.app.Dialog;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import cn.sharesdk.sina.weibo.SinaWeibo;
import cn.sharesdk.wechat.friends.Wechat;
import cn.sharesdk.wechat.moments.WechatMoments;

public class ShareDialog {
	public static void showShareDialog(final Activity activity,final ShareDAO item){
		View view = LayoutInflater.from(activity).inflate(R.layout.view_share_gridview, null);
		final Dialog dialog = DialogShow.showDialog(activity, view, Gravity.BOTTOM);
		TextView tv_sina = (TextView) view.findViewById(R.id.tv_sina);
		TextView tv_wechat = (TextView) view.findViewById(R.id.tv_wechat);
		TextView tv_wechatmonets = (TextView) view.findViewById(R.id.tv_wechatmoments);
		TextView tv_cancel = (TextView) view.findViewById(R.id.tv_cancel);
		tv_sina.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				OneKeyShareUtil.showShare(activity, "",500,item.getTitle(), item.getLink(), 
						item.getContent_weibo(), null,item.getImage(), true, SinaWeibo.NAME);
				dialog.dismiss();
			}
		});
		tv_wechat.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				OneKeyShareUtil.showShare(activity, "",500,item.getTitle(), item.getLink(), 
						item.getContent(), null, item.getImage(), true, Wechat.NAME);
				dialog.dismiss();
			}
		});
		tv_wechatmonets.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				OneKeyShareUtil.showShare(activity, "",500,item.getTitle(), item.getLink(), 
						item.getContent(), null, item.getImage(), true, WechatMoments.NAME);
				dialog.dismiss();
			}
		});
		tv_cancel.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				dialog.dismiss();
			}
		});
		dialog.show();
	}
}

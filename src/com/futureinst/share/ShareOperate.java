package com.futureinst.share;

import org.json.JSONException;

import com.futureinst.R;
import com.futureinst.model.homeeventmodel.FollowDAO;
import com.futureinst.model.homeeventmodel.FollowInfoDAO;
import com.futureinst.net.HttpPostParams;
import com.futureinst.net.HttpPushUtils;
import com.futureinst.net.PostCommentResponseListener;
import com.futureinst.net.PostMethod;
import com.futureinst.net.PostType;
import com.futureinst.sharepreference.SharePreferenceUtil;
import com.futureinst.utils.DialogShow;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class ShareOperate {
	public static void addMyAttention(final Context context,String event_id){
		HttpPushUtils.getInstace(context).postJson(HttpPostParams.getInstace().getPostParams(
				PostMethod.operate_follow.name(), PostType.follow.name(), 
				HttpPostParams.getInstace().operate_follow(SharePreferenceUtil.getInstance(context).getID()+"", 
						SharePreferenceUtil.getInstance(context).getUUid(), event_id, "share")), 
				FollowInfoDAO.class, 
				new PostCommentResponseListener() {
					@Override
					public void requestCompleted(Object response) throws JSONException {
						if(response == null) return;
						FollowInfoDAO followInfoDAO = (FollowInfoDAO) response;
						FollowDAO followDAO = followInfoDAO.getFollow();
						if(followDAO !=null && followDAO.getShareAward() > 0){//第一次分享
							showDialog(context, "耶~分享成功！你已获得未币"+followDAO.getShareAward()+"点!");
						}
					}
				});
	}
	
		private static void showDialog(Context context,final String message){
			View view = LayoutInflater.from(context).inflate(R.layout.share_tip, null,false);
			Button btn_submit = (Button) view.findViewById(R.id.btn_submit);
			TextView tv_message = (TextView) view.findViewById(R.id.tv_message); 
			tv_message.setText(message);
			final Dialog dialog = DialogShow.showDialog((Activity) context, view,Gravity.CENTER);
			btn_submit.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) { 
					dialog.cancel();
				}
			});
			dialog.show();
		}
}

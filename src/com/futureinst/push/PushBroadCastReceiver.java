package com.futureinst.push;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

import org.json.JSONException;

import com.futureinst.R;
import com.futureinst.db.PushMessageCacheUtil;
import com.futureinst.home.HomeActivity;
import com.futureinst.model.push.PushMessageDAO;
import com.futureinst.model.usermodel.UserInfo;
import com.futureinst.net.GsonUtils;
import com.futureinst.net.HttpPostParams;
import com.futureinst.net.HttpPushUtils;
import com.futureinst.net.PostCommentResponseListener;
import com.futureinst.net.PostMethod;
import com.futureinst.net.PostType;
import com.futureinst.sharepreference.SharePreferenceUtil;
import com.futureinst.utils.Utils;
import com.igexin.sdk.PushConsts;
import com.igexin.sdk.PushManager;

public class PushBroadCastReceiver extends BroadcastReceiver {
	/**
	 * 应用未启动, 个推 service已经被唤醒,保存在该时间段内离线消息(此时 GetuiSdkDemoActivity.tLogView ==
	 * null)
	 */
	
	@Override
	public void onReceive(Context context, Intent intent) {
		Bundle bundle = intent.getExtras();
		Log.d("GetuiSdkDemo", "onReceive() action=" + bundle.getInt("action"));
		switch (bundle.getInt(PushConsts.CMD_ACTION)) {
		case PushConsts.GET_MSG_DATA:
			// 获取透传数据
			// String appid = bundle.getString("appid");
			byte[] payload = bundle.getByteArray("payload");

			String taskid = bundle.getString("taskid");
			String messageid = bundle.getString("messageid");

			// smartPush第三方回执调用接口，actionid范围为90000-90999，可根据业务场景执行
			boolean result = PushManager.getInstance().sendFeedbackMessage(
					context, taskid, messageid, 90001);
			System.out.println("第三方回执接口调用" + (result ? "成功" : "失败"));

			if (payload != null) {
				PushMessageCacheUtil messageCacheUtil = PushMessageCacheUtil.getInstance(context);
				PushMessageDAO pushMessageDAO = null;
				String data = new String(payload);
				Log.d("GetuiSdkDemo", "receiver payload : " + data);
				if(data.contains("{") && data.contains("}")){
						pushMessageDAO = GsonUtils.json2Bean(data, PushMessageDAO.class);
						pushMessageDAO.setId(taskid);
						pushMessageDAO.setRead(false);
						pushMessageDAO.setTime(System.currentTimeMillis());
				}else{
					pushMessageDAO = new PushMessageDAO(taskid, data, false,System.currentTimeMillis());
				}
				if(TextUtils.isEmpty(pushMessageDAO.getNo_notice())
//						|| pushMessageDAO.getNo_notice().equals("1")
						|| !Utils.isBackground(context.getApplicationContext())){
                    //不发通知提示
                }else{
                    setNotify(context,pushMessageDAO);
                }

				messageCacheUtil.addPushMessage(pushMessageDAO);
				Intent intent2 = new Intent("newPushMessage");
				context.sendBroadcast(intent2);
			}
			break;

		case PushConsts.GET_CLIENTID:
			// 获取ClientID(CID)
			// 第三方应用需要将CID上传到第三方服务器，并且将当前用户帐号和CID进行关联，以便日后通过用户帐号查找CID进行消息推送
			String cid = bundle.getString("clientid");
			HomeActivity.isUpdate = true;
			update_user_cid(cid, context);
			
			break;

		case PushConsts.THIRDPART_FEEDBACK:
			/*
			 * String appid = bundle.getString("appid"); String taskid =
			 * bundle.getString("taskid"); String actionid =
			 * bundle.getString("actionid"); String result =
			 * bundle.getString("result"); long timestamp =
			 * bundle.getLong("timestamp");
			 * 
			 * Log.d("GetuiSdkDemo", "appid = " + appid); Log.d("GetuiSdkDemo",
			 * "taskid = " + taskid); Log.d("GetuiSdkDemo", "actionid = " +
			 * actionid); Log.d("GetuiSdkDemo", "result = " + result);
			 * Log.d("GetuiSdkDemo", "timestamp = " + timestamp);
			 */
			break;
            case PushConsts.GET_SDKSERVICEPID:

                break;
            default:
			break;
		}
	}

	@SuppressWarnings("deprecation")
	private void setNotify(Context context,PushMessageDAO pushMessageDAO){
		NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
		Intent notifyIntent = new Intent(context, HomeActivity.class);
        notifyIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		notifyIntent.putExtra("push", true);
		notifyIntent.putExtra("pushMessage", pushMessageDAO);
		PendingIntent appIntent = PendingIntent.getActivity(context, 0,
				notifyIntent, PendingIntent.FLAG_UPDATE_CURRENT);
		Notification myNoti = new Notification.Builder(context)
                .setSmallIcon(R.drawable.push_logo)
                .setAutoCancel(true)
                .setContentTitle(pushMessageDAO.getTitle())
                .setContentText("未来研究所")
                .setContentIntent(appIntent)
                .setDefaults(Notification.DEFAULT_ALL)
                .setWhen(System.currentTimeMillis())
                .build();
		notificationManager.notify(0, myNoti);
	}
	//上传clientid
		private void update_user_cid(final String cid,Context context){
			SharePreferenceUtil preferenceUtil = SharePreferenceUtil.getInstance(context);
			HttpPushUtils.getInstace(context).postJson(HttpPostParams.getInstace().getPostParams(PostMethod.update_user.name(), PostType.user.name(), 
					HttpPostParams.getInstace().update_user_cid(preferenceUtil.getUUid(), preferenceUtil.getID()+"",cid)), 
					UserInfo.class,
					new PostCommentResponseListener() {
						@Override
						public void requestCompleted(Object response) throws JSONException {
							if(response == null) return;
						}
					});
		}
}

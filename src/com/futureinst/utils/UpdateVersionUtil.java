package com.futureinst.utils;

import org.json.JSONException;

import com.futureinst.R;
import com.futureinst.global.Content;
import com.futureinst.home.HomeActivity;
import com.futureinst.model.version.VersionDAO;
import com.futureinst.net.HttpPostParams;
import com.futureinst.net.HttpResponseUtils;
import com.futureinst.net.PostCommentResponseListener;
import com.futureinst.net.PostMethod;
import com.futureinst.net.PostType;
import com.futureinst.service.UpdateDialogShow;
import com.futureinst.service.UpdateService;
import com.futureinst.sharepreference.SharePreferenceUtil;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class UpdateVersionUtil {
	// 获取版本信息
	public static void get_android_version(final Boolean isToast,final Activity activity) {
		HttpResponseUtils httpResponseUtils = HttpResponseUtils.getInstace(activity);
		HttpPostParams httpPostParams = HttpPostParams.getInstace();
		if(SharePreferenceUtil.getInstance(activity).getIsUpdateVersion() && isToast){
			Toast.makeText(activity, "正在下载最新版本...", Toast.LENGTH_SHORT).show();
			return;
		}
		httpResponseUtils
				.postJson(
						httpPostParams.getPostParams(PostMethod.get_android_version.name(), PostType.common.name(),
								httpPostParams.get_android_version()),
						VersionDAO.class, new PostCommentResponseListener() {
							@Override
							public void requestCompleted(Object response) throws JSONException {
								if (response == null)
									return;
								VersionDAO versionDAO = (VersionDAO) response;
								if (versionDAO.getAoto_refresh_event_price() == 1) {
									Content.is_aoto_refresh_event_price = true;
									Content.aoto_refresh_event_price_interval = versionDAO
											.getAoto_refresh_event_price_interval();
								}
								Content.main_list_fresh_interval = versionDAO.getMain_list_fresh_interval();
								int versionCode = Utils.getVersionCode(activity);
								if (versionCode < versionDAO.getAndroid_version()) {
									update(activity,versionDAO.getDownload_url());
								}else{
									if(isToast)
									Toast.makeText(activity, "当前已是最新版本！", Toast.LENGTH_SHORT).show();
								}
							}
						});
	}

	private static void update(final Activity activity,final String url) {
		View view = LayoutInflater.from(activity).inflate(R.layout.version_update, null, false);
		// 得到屏幕的宽和高
		DisplayMetrics metrics = new DisplayMetrics();
		activity.getWindowManager().getDefaultDisplay().getMetrics(metrics);
		int width = metrics.widthPixels;
		int height = metrics.heightPixels;
		final Dialog dialog = UpdateDialogShow.showDialog(activity, view, width, height);
		// TextView textView = (TextView) view.findViewById(R.id.version);
		TextView textView = (TextView) view.findViewById(R.id.update_detail);
		Button button = (Button) view.findViewById(R.id.next);
		Button button2 = (Button) view.findViewById(R.id.update);
		button.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				dialog.dismiss();
			}
		});
		button2.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				dialog.dismiss();
				// 下载新版本
				Intent intent = new Intent(activity, UpdateService.class);
				intent.putExtra("url", url);
				activity.startService(intent);
			}
		});

	}
}

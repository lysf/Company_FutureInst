package com.futureinst.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import com.futureinst.R;
import com.futureinst.model.global.Content;
import com.futureinst.sharepreference.SharePreferenceUtil;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;


/**
 * 下载更新服务
 * 
 * @author Tom.Cai
 * 
 */
public class UpdateService extends Service {
	private String url = null;
	// 通知栏
	private NotificationManager updateNotificationManager = null;
	private Notification updateNotification = null;
	private String appName = null;
	private String fileName = null;
	private String updateDir = null;
	private SharePreferenceUtil preferenceUtil;
	// 通知栏跳转Intent

	@SuppressWarnings("deprecation")
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		preferenceUtil = SharePreferenceUtil.getInstance(getApplicationContext());
		url = intent.getStringExtra("url");
		appName = getApplication().getResources().getText(R.string.app_name)
				.toString();
		if (url != null) {
			fileName = url.substring(url.lastIndexOf("/") + 1);
			updateDir = Content.DIR;
			File file = new File(updateDir, fileName);
			if (file.exists()) {
				file.delete();
			}

			Intent nullIntent = new Intent();
			PendingIntent pendingIntent = PendingIntent.getActivity(
					getApplicationContext(), 0, nullIntent, 0);
			// 创建文件
			updateNotificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
			updateNotification = new Notification();
			updateNotification.icon = R.drawable.logo;
			updateNotification.tickerText = "正在更新" + appName;
			updateNotification.setLatestEventInfo(getApplication(), "正在下载"
					+ appName, "0%", null);
//			updateNotification.defaults = Notification.DEFAULT_SOUND;
			updateNotification.flags = Notification.FLAG_AUTO_CANCEL;
			updateNotification.contentIntent = pendingIntent;
			updateNotificationManager.notify(101, updateNotification);
			// 开启线程现在
			new Thread(new updateRunnable()).start();
		}
		return super.onStartCommand(intent, 0, 0);
	}

	private class updateRunnable implements Runnable {
		Message message = updateHandler.obtainMessage();

		public void run() {
			message.what = 0;
			try {
				long downloadSize = downloadUpdateFile(url);
				Log.i("cai", downloadSize / 1024 + "");
				if (downloadSize == 100) {
					// 下载成功
					updateHandler.sendMessage(message);
				}
			} catch (Exception ex) {
				ex.printStackTrace();
				message.what = 1;
				// 下载失败
				updateHandler.sendMessage(message);
			}
		}
	}

	private Handler updateHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			
			switch (msg.what) {
			case 0:
				installApk(updateDir + File.separator + fileName);
				// 停止服务
				preferenceUtil.setUpdate(false);
				stopSelf();
				break;
			case 1:
				Intent nullIntent = new Intent();
				PendingIntent pendingIntent = PendingIntent.getActivity(
						UpdateService.this, 10, nullIntent, 0);
				// 下载失败
				updateNotification.setLatestEventInfo(UpdateService.this,
						appName, "网络连接不正常，下载失败！", pendingIntent);
				updateNotification.flags = Notification.FLAG_AUTO_CANCEL;
				updateNotificationManager.notify(101, updateNotification);
				preferenceUtil.setUpdate(false);
				stopSelf();
				break;
			default:
				stopSelf();
			}
		}
	};

	// 下载
	@SuppressWarnings("deprecation")
	@SuppressLint("WorldReadableFiles")
	public long downloadUpdateFile(String downloadUrl) throws Exception {
		int downloadCount = 0;
		int currentSize = 0;
		long totalSize = 0;
		int updateTotalSize = 0;

		HttpURLConnection httpConnection = null;
		InputStream is = null;
		FileOutputStream fos = null;

		try {
			URL url = new URL(downloadUrl);
			httpConnection = (HttpURLConnection) url.openConnection();
			// if(currentSize > 0) {
			// httpConnection.setRequestProperty("RANGE", "bytes=" + currentSize
			// + "-");
			// }
			httpConnection.setConnectTimeout(10000);
			httpConnection.setReadTimeout(20000);
			updateTotalSize = httpConnection.getContentLength();
			if (httpConnection.getResponseCode() == 404) {
				throw new Exception("fail!");
			}
			is = httpConnection.getInputStream();
			fos = new FileOutputStream(updateDir + File.separator + fileName);
			// fos = openFileOutput(updateDir+File.separator+fileName,
			// MODE_WORLD_READABLE);

			byte buffer[] = new byte[1024];
			int readsize = 0;
			while ((readsize = is.read(buffer)) != -1) {
				fos.write(buffer, 0, readsize);
				totalSize += readsize;
			if(downloadCount== 0 ||
					(int) (totalSize * 100 / updateTotalSize)-1 > downloadCount
					|| totalSize == updateTotalSize){
				preferenceUtil.setUpdate(true);
				downloadCount  = (int) (totalSize * 100 / updateTotalSize);
				Intent nullIntent = new Intent();
				PendingIntent pendingIntent = PendingIntent.getActivity(
						getApplicationContext(), 0, nullIntent, 0);
				updateNotification.contentIntent = pendingIntent;
				updateNotification.setLatestEventInfo(UpdateService.this,
						appName + "正在下载",  downloadCount + "%", null);
				updateNotificationManager.notify(101, updateNotification);
			}
			}
		} finally {
			if (httpConnection != null) {
				httpConnection.disconnect();
			}
			if (is != null) {
				is.close();
			}
			if (fos != null) {
				fos.close();
			}
		}
		return downloadCount;
	}

	private void installApk(String filename) {
		File file = new File(filename);
		Intent intent = new Intent();
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		intent.setAction(Intent.ACTION_VIEW);
		String type = "application/vnd.android.package-archive";
		intent.setDataAndType(Uri.fromFile(file), type);
		startActivity(intent);
	}

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}
}

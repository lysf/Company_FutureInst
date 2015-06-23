package com.futureinst.home.eventdetail;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.TextView;

public class MyThread implements Runnable {
	private TextView tv_time;
	private Long time;
	private int what;
	private Handler handler;

	public MyThread(TextView tv_time, Long time, int what, Handler handler) {
		this.tv_time = tv_time;
		this.time = time;
		this.what = what;
		this.handler = handler;
	}

	@Override
	public void run() {
		while (time > 0) {
			time -= 1000;
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Message message = new Message();
			message.what = what;
			message.obj = tv_time;
			Bundle bundle = new Bundle();
			bundle.putLong("time", time);
			message.setData(bundle);
			handler.sendMessage(message);

		}
	}
}

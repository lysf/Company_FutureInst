package com.futureinst.model.attention;

import java.io.Serializable;

import android.widget.TextView;

@SuppressWarnings("serial")
public class CountDownBean implements Serializable {
	private AttentionDAO attention;
	private TextView tv_downTime;
	public AttentionDAO getAttention() {
		return attention;
	}
	public void setAttention(AttentionDAO attention) {
		this.attention = attention;
	}
	public TextView getTv_downTime() {
		return tv_downTime;
	}
	public void setTv_downTime(TextView tv_downTime) {
		this.tv_downTime = tv_downTime;
	}
	
}

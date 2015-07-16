package com.futureinst.model.version;

import java.io.Serializable;

@SuppressWarnings("serial")
public class VersionDAO implements Serializable {
	private float android_version;
	private String method;
	private String type;
	private Long curr_time;
	private String download_url;
	public float getAndroid_version() {
		return android_version;
	}
	public void setAndroid_version(float android_version) {
		this.android_version = android_version;
	}
	public String getMethod() {
		return method;
	}
	public void setMethod(String method) {
		this.method = method;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Long getCurr_time() {
		return curr_time;
	}
	public void setCurr_time(Long curr_time) {
		this.curr_time = curr_time;
	}
	public String getDownload_url() {
		return download_url;
	}
	public void setDownload_url(String download_url) {
		this.download_url = download_url;
	}
	@Override
	public String toString() {
		return "VersionDAO [android_version=" + android_version + ", method=" + method + ", type=" + type
				+ ", curr_time=" + curr_time + ", download_url=" + download_url + "]";
	}
	
}

package com.futureinst.model.version;

import java.io.Serializable;

@SuppressWarnings("serial")
public class VersionDAO implements Serializable {
	private int aoto_refresh_event_price;
	private int aoto_refresh_event_price_interval;
	private int disable_app_sign_in;//disable_app_sign_in 为1表示禁止app内注册；
	private int main_list_fresh_interval;
	private float android_version;
	private String method;
	private String type;
	private Long curr_time;
	private String download_url;
	private String load_ad_image;
	private String load_ad_url;
	
	public String getLoad_ad_image() {
		return load_ad_image;
	}
	public void setLoad_ad_image(String load_ad_image) {
		this.load_ad_image = load_ad_image;
	}
	public String getLoad_ad_url() {
		return load_ad_url;
	}
	public void setLoad_ad_url(String load_ad_url) {
		this.load_ad_url = load_ad_url;
	}
	public int getDisable_app_sign_in() {
		return disable_app_sign_in;
	}
	public void setDisable_app_sign_in(int disable_app_sign_in) {
		this.disable_app_sign_in = disable_app_sign_in;
	}
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
	
	public int getAoto_refresh_event_price() {
		return aoto_refresh_event_price;
	}
	public void setAoto_refresh_event_price(int aoto_refresh_event_price) {
		this.aoto_refresh_event_price = aoto_refresh_event_price;
	}
	public int getAoto_refresh_event_price_interval() {
		return aoto_refresh_event_price_interval;
	}
	public void setAoto_refresh_event_price_interval(int aoto_refresh_event_price_interval) {
		this.aoto_refresh_event_price_interval = aoto_refresh_event_price_interval;
	}
	public int getMain_list_fresh_interval() {
		return main_list_fresh_interval;
	}
	public void setMain_list_fresh_interval(int main_list_fresh_interval) {
		this.main_list_fresh_interval = main_list_fresh_interval;
	}
	@Override
	public String toString() {
		return "VersionDAO [aoto_refresh_event_price=" + aoto_refresh_event_price
				+ ", aoto_refresh_event_price_interval=" + aoto_refresh_event_price_interval
				+ ", main_list_fresh_interval=" + main_list_fresh_interval + ", android_version=" + android_version
				+ ", method=" + method + ", type=" + type + ", curr_time=" + curr_time + ", download_url="
				+ download_url + "]";
	}
	
}

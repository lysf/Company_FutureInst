package com.futureinst.utils.xutils;

import org.json.JSONException;

import com.futureinst.net.GsonUtils;
import com.futureinst.net.PostCommentResponseListener;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;

import android.content.Context;
import android.widget.Toast;

public class XUtilsHttpUtils {
	private HttpUtils httpUtils;
	private Context context;
	private static XUtilsHttpUtils utilsHttpUtils;
	private XUtilsHttpUtils(Context context){
		this.context = context;
		httpUtils = new HttpUtils();
	}
	public static XUtilsHttpUtils getInstance(Context context){
		if(utilsHttpUtils == null)
			utilsHttpUtils = new XUtilsHttpUtils(context);
		return utilsHttpUtils;
	}
	
	//普通的get/post方法
	public <T>void getData(HttpMethod httpMethod,String url,RequestParams params,final Class<T> clz,final PostCommentResponseListener responseListener){
		httpUtils.send(httpMethod, url, params,new RequestCallBack<String>() {
			@Override
			public void onFailure(HttpException arg0, String arg1) {
				Toast.makeText(context, arg1, Toast.LENGTH_SHORT).show();
			}
			@Override
			public void onSuccess(ResponseInfo<String> responseInfo) {
				try {
					responseListener.requestCompleted(GsonUtils.json2Bean(responseInfo.result, clz));
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		});
	}
}

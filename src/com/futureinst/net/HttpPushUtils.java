package com.futureinst.net;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.futureinst.baseui.BaseApplication;
import com.futureinst.model.basemodel.BaseModel;
import com.futureinst.sharepreference.SharePreferenceUtil;
import com.futureinst.utils.Utils;

public class HttpPushUtils {
	private static HttpPushUtils httpUtils;
	private Context activity;
	private RequestQueue mQueue;
	private SharePreferenceUtil preferenceUtil;
	private HttpPushUtils(Context activity) {
		mQueue = BaseApplication.getInstance().getRequestQueue();
		this.activity = activity;
		preferenceUtil = SharePreferenceUtil.getInstance(activity);
	}

	public static HttpPushUtils getInstace(Context activity) {
		if (httpUtils == null) {
			httpUtils = new HttpPushUtils(activity);
		}
		return httpUtils;
	}
	
	// volley使用post上传数据
	public synchronized <T> void postJson(final Map<String, String> params, final Class<T> clz,
			final PostCommentResponseListener commentResponseListener) {
		StringRequest postRequest = new StringRequest(Request.Method.POST, HttpPath.URL,
				new Response.Listener<String>() {
					@Override
					public void onResponse(String response) {
						if (!TextUtils.isEmpty(response)) {
							Log.i(clz.getName(), "----------"+clz.getName()+"-->>" + response);
							BaseModel baseModel = GsonUtils.json2Bean(response,
									BaseModel.class);
							int status = baseModel.getStatus();
							String method = baseModel.getMethod();
							final String message = baseModel.getErrinfo();
							if(status!=0){
								try {
									commentResponseListener
											.requestCompleted(null);
								} catch (JSONException e1) {
									e1.printStackTrace();
								}
								return;
							}
							try {
								commentResponseListener
										.requestCompleted(GsonUtils.json2Bean(
												response, clz));
							} catch (JSONException e) {
								e.printStackTrace();
							}
						}
					}
				}, new Response.ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError error) {
						Log.i("-----VolleyError---", "-----client error--->>"
								+ error.toString());
						try {
							commentResponseListener.requestCompleted(GsonUtils.json2Bean(
									null, clz));
						} catch (JSONException e) {
							e.printStackTrace();
						}
						if(!Utils.checkNetkworkState(activity)){
//							MyToast.showToast(activity, activity.getResources().getString(R.string.connection_interrupt), 0);
							return;
						}
					}
				}) {
			@Override
			protected Map<String, String> getParams() {
				return params;
			}
			@Override
					public Map<String, String> getHeaders() throws AuthFailureError {
				Map<String,String> header = new HashMap<String, String>();
				header.put("uuid", preferenceUtil.getUUid());
				header.put("user_id", preferenceUtil.getID()+"");
				header.put("deviceID", Utils.getUniquePsuedoID());
				return header;
					}

		};
		postRequest.setRetryPolicy(new DefaultRetryPolicy(
						6*1000, DefaultRetryPolicy.DEFAULT_TIMEOUT_MS,
						DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
		mQueue.add(postRequest);
		
	}
	
}

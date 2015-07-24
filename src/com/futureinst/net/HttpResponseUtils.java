package com.futureinst.net;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONException;

import android.app.Activity;
import android.text.TextUtils;
import android.util.Log;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.futureinst.R;
import com.futureinst.baseui.BaseApplication;
import com.futureinst.db.DataCacheUtil;
import com.futureinst.fileupload.MultiPartStack;
import com.futureinst.fileupload.MultiPartStringRequest;
import com.futureinst.model.basemodel.BaseModel;
import com.futureinst.utils.MyToast;
import com.futureinst.utils.Utils;

public class HttpResponseUtils {
	private static HttpResponseUtils httpUtils;
	private Activity activity;
	private RequestQueue mQueue;
	private RequestQueue mSingleQueue;
	private DataCacheUtil cacheUtil;
	private HttpResponseUtils(Activity activity) {
		mQueue = BaseApplication.getInstance().getRequestQueue();
		this.activity = activity;
		mSingleQueue = Volley.newRequestQueue(activity, new MultiPartStack());
		cacheUtil = DataCacheUtil.getInstance(activity);
	}

	public static HttpResponseUtils getInstace(Activity activity) {
		if (httpUtils == null) {
			httpUtils = new HttpResponseUtils(activity);
		}
		return httpUtils;
	}

	// volley使用post上传数据
	public synchronized <T> void postJson(final Map<String, String> params, final Class<T> clz,
			final PostCommentResponseListener commentResponseListener) {
		final String path = HttpPath.URL + params.toString();
		final String result = cacheUtil.getCache(path);
		try {
			commentResponseListener.requestCompleted(GsonUtils.json2Bean(
					result, clz));
		} catch (JSONException e2) {
			e2.printStackTrace();
		}
		StringRequest postRequest = new StringRequest(Request.Method.POST, HttpPath.URL,
				new Response.Listener<String>() {
					@Override
					public void onResponse(String response) {
						if (!TextUtils.isEmpty(response)) {
							Log.i(clz.getName(), "----------"+clz.getName()+"-->>" + response);
							BaseModel baseModel = GsonUtils.json2Bean(response,
									BaseModel.class);
							int status = baseModel.getStatus();
							final String message = baseModel.getErrinfo();
							if(status!=0){
								try {
									commentResponseListener
											.requestCompleted(null);
								} catch (JSONException e1) {
									e1.printStackTrace();
								}
								MyToast.showToast(activity, message, 0);
								return;
							}
							cacheUtil.addOrReplaCecache(path, response);
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
									result, clz));
						} catch (JSONException e) {
							e.printStackTrace();
						}
						if(!Utils.checkNetkworkState(activity)){
							MyToast.showToast(activity, activity.getResources().getString(R.string.connection_interrupt), 0);
							
							return;
						}
						MyToast.showToast(activity, activity.getResources().getString(R.string.client_no_response), 0);
					}
				}) {
			@Override
			protected Map<String, String> getParams() {
				return params;
			}

		};
		postRequest.setRetryPolicy(new DefaultRetryPolicy(
						6*1000, DefaultRetryPolicy.DEFAULT_TIMEOUT_MS,
						DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
		mQueue.add(postRequest);
		
	}
	public synchronized <T> void postJson_1(final Map<String, String> params, final Class<T> clz,
			final PostCommentResponseListener commentResponseListener) {
		if(!Utils.checkNetkworkState(activity)){
			MyToast.showToast(activity, activity.getResources().getString(R.string.connection_interrupt), 0);
			return;
		}
		StringRequest postRequest = new StringRequest(Request.Method.POST, HttpPath.URL,
				new Response.Listener<String>() {
			@Override
			public void onResponse(String response) {
				if (!TextUtils.isEmpty(response)) {
					Log.i(clz.getName(), "----------"+clz.getName()+"-->>" + response);
					BaseModel baseModel = GsonUtils.json2Bean(response,
							BaseModel.class);
					int status = baseModel.getStatus();
					final String message = baseModel.getErrinfo();
					if(status!=0){
						try {
							commentResponseListener
							.requestCompleted(null);
						} catch (JSONException e1) {
							e1.printStackTrace();
						}
						if(status == -3) return;
						MyToast.showToast(activity, message, 0);
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
					commentResponseListener.requestCompleted(null);
				} catch (JSONException e) {
					e.printStackTrace();
				}
				if(!Utils.checkNetkworkState(activity)){
					MyToast.showToast(activity, activity.getResources().getString(R.string.connection_interrupt), 0);
					return;
				}
				MyToast.showToast(activity, activity.getResources().getString(R.string.client_no_response), 0);
			}
		}) {
			@Override
			protected Map<String, String> getParams() {
				return params;
			}
			
		};
		postRequest.setRetryPolicy(new DefaultRetryPolicy(
				6*1000, DefaultRetryPolicy.DEFAULT_TIMEOUT_MS,
				DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
		mQueue.add(postRequest);
		
	}

	public <T> void postCookieJson(String url, final String cookie,
			final Map<String, String> params, final Class<T> clz,
			final PostCommentResponseListener commentResponseListener) {
		CookieRequest postRequest = new CookieRequest(Request.Method.POST, url,
				new Response.Listener<String>() {
					@Override
					public void onResponse(String response) {
						if (!TextUtils.isEmpty(response)) {
							Log.i("json", "------------" + response);
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
						Log.i("-----VolleyError---", "-----服务器连接失败！--->>"
								+ error.toString());
//						Toast.makeText(activity, "服务器连接失败！", Toast.LENGTH_SHORT)
//								.show();
					}
				}) {
			@Override
			protected Map<String, String> getParams() {
				return params;
			}

			@Override
			protected Response<String> parseNetworkResponse(
					NetworkResponse response) {
				// TODO Auto-generated method stub
				try {
					Map<String, String> responseHeaders = response.headers;
					String rawCookies = responseHeaders.get("Set-Cookie");
//					SharePreferenceUtil preferenceUtil = SharePreferenceUtil
//							.getInstance(activity);
//					preferenceUtil.setCookie(rawCookies);
					String dataString = new String(response.data, "UTF-8");
					return Response.success(dataString,
							HttpHeaderParser.parseCacheHeaders(response));
				} catch (UnsupportedEncodingException e) {
					return Response.error(new ParseError(e));
				}
			}

			@Override
			public RetryPolicy getRetryPolicy() {
				DefaultRetryPolicy retryPolicy = new DefaultRetryPolicy(8000,
						DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
						DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
				return retryPolicy;
			}
		};
		if (!TextUtils.isEmpty(cookie)) {
			postRequest.setCookie(cookie);
		}
		mQueue.add(postRequest);
	}

	public <T> void UploadFileRequest(final String url, final List<String> files,
			final Map<String, String> params, final Class<T> clz,
			final PostCommentResponseListener commentResponseListener) {
		if (null == url || null == commentResponseListener) {
			return;
		}
		MultiPartStringRequest multiPartRequest = new MultiPartStringRequest(
				Request.Method.POST, url, new Listener<String>() {
					@Override
					public void onResponse(String response) {
						if (!TextUtils.isEmpty(response)) {
							Log.i("json", "----------response-->>" + response);
							BaseModel baseModel = GsonUtils.json2Bean(response,
									BaseModel.class);
							int status = baseModel.getStatus();
							final String message = baseModel.getErrinfo();
							if(status!=0){
								MyToast.showToast(activity, message, 0);
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
				}, new ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError error) {
						// TODO Auto-generated method stub
						Log.i("-----VolleyError---",
								"-----文件上传失败--->>" + error.toString());
						try {
							commentResponseListener.requestCompleted(null);
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}) {

			@Override
			public Map<String, File> getFileUploads() {
				return new HashMap<String, File>();
			}

			@Override
			public Map<String, String> getStringUploads() {
				return params;
			}

			@Override
			public List<String> getFileNameUploads() {
				return files;
			}
		};
		mSingleQueue.add(multiPartRequest);
	}
}

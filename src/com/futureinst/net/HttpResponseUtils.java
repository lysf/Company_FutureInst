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
import android.widget.Toast;

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
import com.futureinst.fileupload.MultiPartStack;
import com.futureinst.fileupload.MultiPartStringRequest;
import com.futureinst.model.basemodel.BaseModel;
import com.futureinst.utils.MyToast;
import com.futureinst.utils.ToastUtils;
import com.futureinst.utils.Utils;

import de.keyboardsurfer.android.widget.crouton.Configuration;
import de.keyboardsurfer.android.widget.crouton.Style;
public class HttpResponseUtils {
	private static HttpResponseUtils httpUtils;
	private Activity activity;
	private RequestQueue mQueue;
	private RequestQueue mSingleQueue;

	private HttpResponseUtils(Activity activity) {
		mQueue = BaseApplication.getInstance().getRequestQueue();
		this.activity = activity;
		mSingleQueue = Volley.newRequestQueue(activity, new MultiPartStack());
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
		if(!Utils.checkNetkworkState(activity)){
			ToastUtils.showToast(activity, activity.getResources().getString(R.string.connection_interrupt), 
					Configuration.DURATION_SHORT, Style.ALERT);
			return;
		}
		StringRequest postRequest = new StringRequest(Request.Method.POST, HttpPath.URL,
				new Response.Listener<String>() {
					@Override
					public void onResponse(String response) {
						if (!TextUtils.isEmpty(response)) {
							Log.i("RESPONSE", "----------response-->>" + response);
							BaseModel baseModel = GsonUtils.json2Bean(response,
									BaseModel.class);
							int status = baseModel.getStatus();
							final String message = baseModel.getErrinfo();
							if(status!=0){
//								ToastUtils.showToast(activity, message, 
//										Configuration.DURATION_SHORT, Style.ALERT);
								MyToast.showToast(activity, message, 0);
//								Toast.makeText(activity, message,0).show();
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
							commentResponseListener.requestCompleted(null);
						} catch (JSONException e) {
							e.printStackTrace();
						}
						ToastUtils.showToast(activity, activity.getResources().getString(R.string.client_no_response), 
								Configuration.DURATION_SHORT, Style.ALERT);
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
//							int status = baseModel.getStatus();
							// String message = baseModel.getMsg();
//							switch (status) {
//							case -1:// 异地登录或登录超时
//								Toast.makeText(activity, "异地登录或登录超时",
//										Toast.LENGTH_SHORT).show();
//
//								 Intent intent = new Intent(activity,
//								 LoginActivity.class);
//								 intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//								 activity.startActivity(intent);
//								 ActivityManagerUtil.finishActivity();
//								try {
//									commentResponseListener
//											.requestCompleted(null);
//								} catch (JSONException e1) {
//									// TODO Auto-generated catch block
//									e1.printStackTrace();
//								}
//								return;
//							case 1:
//							case 2:// 1：请求失败
//							case 3:// 500： 服务器内部错误
//								Toast.makeText(activity, baseModel.getMsg(),
//										Toast.LENGTH_LONG).show();
//								try {
//									commentResponseListener
//											.requestCompleted(null);
//								} catch (JSONException e1) {
//									// TODO Auto-generated catch block
//									e1.printStackTrace();
//								}
//								return;
//							}
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

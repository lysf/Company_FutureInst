package com.futureinst.net;

import java.util.HashMap;
import java.util.Map;

import com.android.volley.AuthFailureError;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.StringRequest;

public class CookieRequest extends StringRequest{
	private Map<String,String> mHeaders = new HashMap<String, String>(1);
	public CookieRequest(int method, String url, Listener<String> listener,
			ErrorListener errorListener) {
		super(method, url, listener, errorListener);
		// TODO Auto-generated constructor stub
	}

	public CookieRequest(String url, Listener<String> listener,
			ErrorListener errorListener) {
		super(url, listener, errorListener);
		// TODO Auto-generated constructor stub
	}
    public void setCookie(String cookie){
        mHeaders.put("Cookie", cookie);
    }
    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
    	// TODO Auto-generated method stub
    	   return mHeaders;
    }
   
}

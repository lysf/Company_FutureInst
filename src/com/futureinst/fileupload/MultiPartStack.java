package com.futureinst.fileupload;


import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Request.Method;
import com.android.volley.toolbox.HttpClientStack.HttpPatch;

import android.util.Log;

import com.android.volley.toolbox.HurlStack;

@SuppressWarnings("deprecation")
public class MultiPartStack extends HurlStack {
	@SuppressWarnings("unused")
	private static final String TAG = MultiPartStack.class.getSimpleName();
    private final static String HEADER_CONTENT_TYPE = "Content-Type";
	
	@Override
	public HttpResponse performRequest(Request<?> request,
			Map<String, String> additionalHeaders) throws IOException, AuthFailureError {
		
		if(!(request instanceof MultiPartRequest)) {
			return super.performRequest(request, additionalHeaders);
		}
		else {
			return performMultiPartRequest(request, additionalHeaders);
		}
	}
	
    private  void addHeaders(HttpUriRequest httpRequest, Map<String, String> headers) {
        for (String key : headers.keySet()) {
            httpRequest.setHeader(key, headers.get(key));
        }
    }
	
	public HttpResponse performMultiPartRequest(Request<?> request,
			Map<String, String> additionalHeaders)  throws IOException, AuthFailureError {
        HttpUriRequest httpRequest = createMultiPartRequest(request, additionalHeaders);
        addHeaders(httpRequest, additionalHeaders);
        addHeaders(httpRequest, request.getHeaders());
        HttpParams httpParams = httpRequest.getParams();
        int timeoutMs = request.getTimeoutMs();

        if(timeoutMs != -1) {
        	HttpConnectionParams.setSoTimeout(httpParams, timeoutMs);
        }
        
        /* Make a thread safe connection manager for the client */
        HttpClient httpClient = new DefaultHttpClient(httpParams);

        return httpClient.execute(httpRequest);
	}
	
	

     HttpUriRequest createMultiPartRequest(Request<?> request,
            Map<String, String> additionalHeaders) throws AuthFailureError {
        switch (request.getMethod()) {
            case Method.DEPRECATED_GET_OR_POST: {
                // This is the deprecated way that needs to be handled for backwards compatibility.
                // If the request's post body is null, then the assumption is that the request is
                // GET.  Otherwise, it is assumed that the request is a POST.
                byte[] postBody = request.getBody();
                if (postBody != null) {
                    HttpPost postRequest = new HttpPost(request.getUrl());
                    if(request.getBodyContentType() != null)
                    	postRequest.addHeader(HEADER_CONTENT_TYPE, request.getBodyContentType());
                    HttpEntity entity;
                    entity = new ByteArrayEntity(postBody);
                    postRequest.setEntity(entity);
                    return postRequest;
                } else {
                    return new HttpGet(request.getUrl());
                }
            }
            case Method.GET:
                return new HttpGet(request.getUrl());
            case Method.DELETE:
                return new HttpDelete(request.getUrl());
            case Method.POST: {
                HttpPost postRequest = new HttpPost(request.getUrl());
                if(request.getBodyContentType() != null) {
                	postRequest.addHeader(HEADER_CONTENT_TYPE, request.getBodyContentType());
                }
                setMultiPartBody(postRequest,request);
                return postRequest;
            }
            case Method.PUT: {
                HttpPut putRequest = new HttpPut(request.getUrl());
                if(request.getBodyContentType() != null)
                	putRequest.addHeader(HEADER_CONTENT_TYPE, request.getBodyContentType());
                setMultiPartBody(putRequest,request);
                return putRequest;
            }
            // Added in source code of Volley libray.
            case Method.PATCH: {
            	HttpPatch patchRequest = new HttpPatch(request.getUrl());
            	if(request.getBodyContentType() != null)
            		patchRequest.addHeader(HEADER_CONTENT_TYPE, request.getBodyContentType());
                return patchRequest;
            }
            default:
                throw new IllegalStateException("Unknown request method.");
        }
    }
	
	/**
	 * If Request is MultiPartRequest type, then set MultipartEntity in the
	 * httpRequest object.
	 * 
	 * @param httpRequest
	 * @param request
	 * @throws AuthFailureError
	 */
	private  void setMultiPartBody(HttpEntityEnclosingRequestBase httpRequest,
			Request<?> request) throws AuthFailureError {

		// Return if Request is not MultiPartRequest
		if (!(request instanceof MultiPartRequest)) {
			return;
		}

		// MultipartEntity multipartEntity = new
		// MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE);

		MultipartEntityBuilder builder = MultipartEntityBuilder.create();

		/* example for setting a HttpMultipartMode */
		builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
		
		ContentType contentType = ContentType.create(HTTP.PLAIN_TEXT_TYPE, HTTP.UTF_8);
		
		// Iterate the stringUploads
		Map<String, String> stringUpload = ((MultiPartRequest) request).getStringUploads();
		if(null != stringUpload) {
			Iterator<Entry<String, String>> iter = stringUpload.entrySet().iterator();
			// 添加参数
			while (iter.hasNext()) {
				Map.Entry<String, String> entry = iter.next();
				final String key = entry.getKey();
				final String val = entry.getValue();
				if (null != val) {
					builder.addPart(key, new StringBody((String) entry.getValue(), contentType));
				}
			}
		}
		// Iterate the fileUploads
		Map<String, File> map= ((MultiPartRequest) request).getFileUploads();
		Log.i(TAG, "----------file--->>"+map.get("file").getAbsolutePath());
		builder.addPart("mainImg", new FileBody(map.get("file")));
		
//		if(null != fileNames && fileNames.size() > 0) {
//			// 添加文件
//			for (String file : fileNames) {
//				builder.addPart("mainImg", new FileBody(new File(file)));
//			}
//		}
		
		
		httpRequest.setEntity(builder.build());
	}

}

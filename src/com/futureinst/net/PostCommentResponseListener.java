package com.futureinst.net;

import org.json.JSONException;


public interface PostCommentResponseListener {
//	public void requestStarted();

	void requestCompleted(Object response) throws JSONException;

//	public void requestEndedWithError(String error);
}

package com.futureinst.share;

import com.futureinst.model.basemodel.ShareDAO;
import com.futureinst.net.GsonUtils;
import com.futureinst.utils.ActivityManagerUtil;

import android.app.Activity;
import android.os.Handler;
import android.util.Log;
import android.webkit.JavascriptInterface;

public class JavaScriptObject {
    Activity mContxt;
    public JavaScriptObject(Activity mContxt) {
        this.mContxt = mContxt;
    }
    public void testObjcCallback() {
    	ActivityManagerUtil.finishOthersActivity();
    	mContxt.finish();
    }
    @JavascriptInterface
    public void shareObjcCallback(String share) {
    	//解析share，分享
    	Log.i("", "=========share====>>"+share);
    	final ShareDAO shareDAO = GsonUtils.json2Bean(share, ShareDAO.class);
    	new  Handler().post(new Runnable() {
			
			@Override
			public void run() {
				ShareDialog.showShareDialog(mContxt, shareDAO);
			}
		});
    	
    }
}

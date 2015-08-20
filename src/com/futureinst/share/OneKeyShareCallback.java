/*
 * 官网地站:http://www.mob.com
 * 技术支持QQ: 4006852216
 * 官方微信:ShareSDK   （如果发布新版本的话，我们将会第一时间通过微信将版本更新内容推送给您。如果使用过程中有任何问题，也可以通过微信与我们取得联系，我们将会在24小时内给予回复）
 *
 * Copyright (c) 2013年 mob.com. All rights reserved.
 */

package com.futureinst.share;

import java.util.HashMap;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.onekeyshare.OnekeyShare;

/**
 * OneKeyShareCallback是快捷分享功能的一个“外部回调”示例。通过
 *{@link OnekeyShare#setCallback(PlatformActionListener)}将
 *本类的示例传递进快捷分享，分享操作结束后，快捷分享会将分享结果
 *回调到本类中来做自定义处理。
 */
public class OneKeyShareCallback implements PlatformActionListener {
	private Context context;
	public OneKeyShareCallback(Context context){
		this.context = context;
	}
	public void onComplete(Platform plat, int action, HashMap<String, Object> res) {
		Log.d(getClass().getSimpleName(), res.toString());
		// 在这里添加分享成功的处理代码
		Toast.makeText(context, "分享成功", Toast.LENGTH_SHORT).show();
	}

	public void onError(Platform plat, int action, Throwable t) {
		t.printStackTrace();
		// 在这里添加分享失败的处理代码
		// 操作失败的处理代码
  	  String expName = t.getClass().getSimpleName();
  	  //判断有没有安装客户端
			if ("WechatClientNotExistException".equals(expName)
					|| "WechatTimelineNotSupportedException".equals(expName)
					|| "WechatFavoriteNotSupportedException".equals(expName))
			{
				Toast.makeText(context, "抱歉您未安装微信客端，无法进行分享！", Toast.LENGTH_SHORT).show();
			}

	}

	public void onCancel(Platform plat, int action) {
		// 在这里添加取消分享的处理代码
	}

}

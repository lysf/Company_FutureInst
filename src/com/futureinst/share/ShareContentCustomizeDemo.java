package com.futureinst.share;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.Platform.ShareParams;
import cn.sharesdk.onekeyshare.ShareContentCustomizeCallback;
import cn.sharesdk.tencent.qq.QQ;
import cn.sharesdk.tencent.qzone.QZone;
import cn.sharesdk.wechat.friends.Wechat;

public class ShareContentCustomizeDemo implements ShareContentCustomizeCallback {

	private String titleUrl;
	private String title;
	private String imageurl;
	private String content;
	public ShareContentCustomizeDemo(String shareTitle, String shareTitleUrl, String shareImageUrl,String content) {
		this.titleUrl = shareTitleUrl;
		this.title = shareTitle;
		this.imageurl = shareImageUrl;
		this.content = content;
	}

	@Override
	public void onShare(Platform platform, ShareParams paramsToShare) {
		 if(Wechat.NAME.equals(platform.getName())){
			paramsToShare.setTitle(title);
			paramsToShare.setImageUrl(imageurl);
			paramsToShare.setText(content);
//			paramsToShare.setSite("凤凰师轩");
//			paramsToShare.setSiteUrl("http://www.jl100star.com");
			paramsToShare.setShareType(Platform.SHARE_WEBPAGE);
			paramsToShare.setUrl(titleUrl);
		}else if(QZone.NAME.equals(platform.getName())){
			paramsToShare.setTitle(title);
			paramsToShare.setTitleUrl(titleUrl);
			paramsToShare.setImageUrl(imageurl);
			paramsToShare.setText(content);
//			paramsToShare.setSite("凤凰师轩");
//			paramsToShare.setSiteUrl("http://www.jl100star.com");
		}else if(QQ.NAME.equals(platform.getName())){
			paramsToShare.setTitle(title);
			paramsToShare.setTitleUrl(titleUrl);
			paramsToShare.setImageUrl(imageurl);
			paramsToShare.setText(content);
//			paramsToShare.setSite("凤凰师轩");
//			paramsToShare.setSiteUrl("http://www.jl100star.com");
		}
	}

}
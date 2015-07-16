package com.futureinst.share;

import com.futureinst.R;

import android.content.Context;
import android.text.TextUtils;
import cn.sharesdk.onekeyshare.OnekeyShare;

public class OneKeyShareUtil {
	/**
	 * 
	 * @param context
	 * @param shareTitle
	 * @param shareTitleUrl
	 * @param shareContents
	 * @param shareLocalImageUrl
	 * @param shareImageUrl
	 * @param silent
	 * @param platform
	 */
	public static void showShare(Context context, String shareTitle,
			String shareTitleUrl, String shareContents,
			String shareLocalImageUrl, String shareImageUrl, boolean silent,
			String platform) {
		// 实例化一个OnekeyShare对象
		OnekeyShare oks = new OnekeyShare();
		// 设置Notification的显示图标和显示文字
		oks.setNotification(R.drawable.logo, "未来研究所");
		// 设置短信地址或者是邮箱地址，如果没有可以不设置
		// oks.setAddress("12345678901");
		// 分享内容的标题
		oks.setTitle(shareTitle);
		// 标题对应的网址，如果没有可以不设置
		if (!TextUtils.isEmpty(shareTitleUrl)) {
			oks.setTitleUrl(shareTitleUrl);
		}
		// 设置分享的文本内容
		// oks.setText("分享内容测试！！！！");
		oks.setText(shareContents);
		// 设置分享照片的本地路径，如果没有可以不设置
		if (!TextUtils.isEmpty(shareLocalImageUrl)) {
			oks.setImagePath(shareLocalImageUrl);
		}
		// 设置分享照片的url地址，如果没有可以不设置
		if (!TextUtils.isEmpty(shareImageUrl)) {
			oks.setImageUrl(shareImageUrl);
		}
		// 微信和易信的分享的网络连接，如果没有可以不设置
		oks.setUrl("http://cmcc.wapgw.cn"); 
		// 人人平台特有的评论字段，如果没有可以不设置
		// oks.setComment("comment");
		// 程序的名称或者是站点名称 
		oks.setSite("未来研究所");
		// 程序的名称或者是站点名称的链接地址
		oks.setSiteUrl("http://www.schooledu.com.cn");
		// 设置纬度
		// oks.setLatitude(23.122619f);
		// 设置精度
		// oks.setLongitude(113.372338f);
		// 设置是否是直接分享
		oks.setSilent(true);
//		 设置自定义的外部回调
		 oks.setCallback(new OneKeyShareCallback());
		 oks.setShareContentCustomizeCallback(new ShareContentCustomizeDemo(shareTitle, shareTitleUrl, shareImageUrl, shareContents));
		// 显示
		// oks.disableSSOWhenAuthorize();
		oks.show(context);
	}

}

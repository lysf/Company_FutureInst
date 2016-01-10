package com.futureinst.utils;

import android.app.Activity;
import android.content.Intent;
import android.text.TextUtils;

import com.futureinst.login.LoginActivity;
import com.futureinst.sharepreference.SharePreferenceUtil;

/**
 * Created by hao on 2015/12/18.
 */
public class LoginUtil {
    //判断是否已登录
    public static boolean judgeIsLogin(Activity activity) {
        SharePreferenceUtil preferenceUtil = SharePreferenceUtil.getInstance(activity);
        if (TextUtils.isEmpty(preferenceUtil.getUUid())) {
            Intent intent = new Intent(activity, LoginActivity.class);
            intent.putExtra("login", true);
            activity.startActivity(intent);
            return false;
        }
        return true;
    }
}

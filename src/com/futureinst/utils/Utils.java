package com.futureinst.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

public class Utils {
	/**
	 * 
	 * @Title: getNetworkInfo
	 * @return: NetworkInfo
	 */
	public static NetworkInfo getNetworkInfo(Context context) {
		NetworkInfo networkInfo = null;
		try {
			ConnectivityManager connectivityManager = (ConnectivityManager) context
					.getSystemService(Context.CONNECTIVITY_SERVICE);
			networkInfo = connectivityManager.getActiveNetworkInfo();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return networkInfo;
	}

	/**
	 * 
	 * @Title: checkNetkworkState
	 * @return: boolean
	 */
	public static boolean checkNetkworkState(Context context) {
		NetworkInfo networkInfo = getNetworkInfo(context);
		return (networkInfo != null && networkInfo.isAvailable());
	}

	/**
	 * 
	 * @Title: isWifiEnable
	 * @return: boolean
	 */
	public static boolean isWifiEnable(Context context) {
		NetworkInfo networkInfo = getNetworkInfo(context);
		if (checkNetkworkState(context))
			return networkInfo.getType() == ConnectivityManager.TYPE_WIFI;
		return false;
	}

	/**
	 * 
	 * @Title: isMobileEnable
	 * @return: boolean
	 */
	public static boolean isMobileEnable(Context context) {
		NetworkInfo networkInfo = getNetworkInfo(context);
		if (checkNetkworkState(context))
			return networkInfo.getType() == ConnectivityManager.TYPE_MOBILE;
		return false;
	}

	/**
	 * @Title: hideSoftInputView
	 * @return: void
	 */
	public static void hideSoftInputView(Activity activity) {
		InputMethodManager inputMethodManager = (InputMethodManager) activity
				.getSystemService(Context.INPUT_METHOD_SERVICE);
		if (activity.getWindow().getAttributes().softInputMode != WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN) {
			if (activity.getCurrentFocus() != null)
				inputMethodManager.hideSoftInputFromWindow(activity
						.getCurrentFocus().getWindowToken(),
						InputMethodManager.HIDE_NOT_ALWAYS);
		}

	}

	/**
	 * @Title: showSoftInputView
	 * @return: void
	 */
	public static void showSoftInputView(Activity activity, View view) {
		if (activity.getWindow().getAttributes().softInputMode == WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN) {
			if (activity.getCurrentFocus() != null)
				((InputMethodManager) activity
						.getSystemService(Context.INPUT_METHOD_SERVICE))
						.showSoftInput(view, 0);
		}
	}

	/**
	 * @Title: isEmail
	 * @return: boolean
	 */
	public static boolean isEmail(String mobiles) {
		boolean flag = false;
		try {
			Pattern pattern = Pattern
					.compile("^\\s*\\w+(?:\\.{0,1}[\\w-]+)*@[a-zA-Z0-9]+(?:[-.][a-zA-Z0-9]+)*\\.[a-zA-Z]+\\s*$");
			Matcher matcher = pattern.matcher(mobiles);
			flag = matcher.matches();
		} catch (Exception e) {
			flag = false;
		}
		return flag;
	}
	//^[\u4e00-\u9fa5]{1,7}$|^[\dA-Za-z_]{1,14}$
	public static boolean checkName(String name) {
		boolean flag = false;
		try {
			Pattern pattern = Pattern.compile("^[\\u4e00-\\u9fa5]{1,7}$|^[\\dA-Za-z_]{0,14}$");
			Matcher matcher = pattern.matcher(name);
			flag = matcher.matches();
		} catch (Exception e) {
			e.printStackTrace();
			flag = false;
		}
		return flag;
	}
	//匹配双字节字符 [^x00-xff] 
	public static boolean mathChinese(String name) {
		boolean flag = false;
		try {
			Pattern pattern = Pattern.compile("[^x00-xff]");
			Matcher matcher = pattern.matcher(name);
			flag = matcher.matches();
		} catch (Exception e) {
			e.printStackTrace();
			flag = false;
		}
		return flag;
	}
	/**
	 * @Title: checkPassword
	 * @return: boolean
	 */
	public static boolean checkPassword(String password) {
		boolean flag = false;
		try {
			Pattern pattern = Pattern.compile("^[0-9a-zA-Z]{6,12}$");
			Matcher matcher = pattern.matcher(password);
			flag = matcher.matches();
		} catch (Exception e) {
			flag = false;
		}
		return flag;
	}

	/**
	 * 
	 * @Title: getVersionName
	 * @return: String
	 */
	public static String getVersionName(Context context) {
		PackageManager packageManager = context.getPackageManager();
		String version = "1.0";
		try {
			PackageInfo packageInfo = packageManager.getPackageInfo(
					context.getPackageName(), 0);
			version = packageInfo.versionName;
		} catch (NameNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return version;
	}

	/**
	 * 
	 * @Title: getVersionCode
	 * @return: int
	 */
	public static int getVersionCode(Context context) {
		int versionCode = 1;
		PackageManager packageManager = context.getPackageManager();
		try {
			PackageInfo packageInfo = packageManager.getPackageInfo(
					context.getPackageName(), 0);
			versionCode = packageInfo.versionCode;
		} catch (NameNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return versionCode;
	}

	/**
	 * 
	 * @Title: checkMobilePhoneNo
	 * @return: boolean
	 */
	public static boolean checkMobilePhoneNo(String mobilePhone) {
		boolean flag = false;
		try {
			Pattern p = Pattern
					.compile("^((13[0-9])|(15[^4,\\D])|(18[0-9]))\\d{8}$");
			Matcher m = p.matcher(mobilePhone);
			flag = m.matches();
		} catch (Exception e) {
			flag = false;
		}
		return flag;
	}

	/**
	 * 
	 * @Title: checkIsChinse
	 * @return: boolean
	 */
	public static boolean checkIsChinse(String name) {
		boolean flag = false;
		try {
			Pattern pattern = Pattern
					.compile("^[\\u4E00-\\u9FA5\\uF900-\\uFA2D]+$");
			Matcher matcher = pattern.matcher(name);
			flag = matcher.matches();
		} catch (Exception e) {
			flag = false;
		}
		return flag;
	}

	// 获取屏幕的宽度
	@SuppressWarnings("deprecation")
	public static int getScreenWidth(Context context) {
		WindowManager manager = (WindowManager) context
				.getSystemService(Context.WINDOW_SERVICE);
		Display display = manager.getDefaultDisplay();
		return display.getWidth();
	}

	// 获取屏幕的高度
	@SuppressWarnings("deprecation")
	public static int getScreenHeight(Context context) {
		WindowManager manager = (WindowManager) context
				.getSystemService(Context.WINDOW_SERVICE);
		Display display = manager.getDefaultDisplay();
		return display.getHeight();
	}
	public static int dip2px(Context context, int dp) {
        final float fDensity = context.getResources().getDisplayMetrics().density;
        return (int) (dp * fDensity + 0.5f);
    }
	public static int getViewMeasuredHeight(View view){
		int w = View.MeasureSpec.makeMeasureSpec(0,
                View.MeasureSpec.UNSPECIFIED);
        int h = View.MeasureSpec.makeMeasureSpec(0,
                View.MeasureSpec.UNSPECIFIED);
        view.measure(w, h);
        return view.getMeasuredHeight();
//        int width = view.getMeasuredWidth();
        
	}
}

package com.futureinst.utils;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

import java.io.InputStream;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utils {
    /**
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
     * @Title: checkNetkworkState
     * @return: boolean
     */
    public static boolean checkNetkworkState(Context context) {
        NetworkInfo networkInfo = getNetworkInfo(context);
        return (networkInfo != null && networkInfo.isAvailable());
    }

    /**
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
     * @throws
     * @Title: checkIsNumber
     * @Description: TODO
     * @author: huihaoyan
     * @param: @param s
     * @param: @return
     * @return: boolean
     */
    public static boolean checkIsNumber(String s) {
        boolean flag;
        try {
            Pattern pattern = Pattern.compile("^[0-9]*$");
            Matcher m = pattern.matcher(s);
            flag = m.matches();
        } catch (Exception e) {
            flag = false;
        }
        return flag;
    }

    /**
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

    public static int getViewMeasuredHeight(View view) {
        int w = View.MeasureSpec.makeMeasureSpec(0,
                View.MeasureSpec.UNSPECIFIED);
        int h = View.MeasureSpec.makeMeasureSpec(0,
                View.MeasureSpec.UNSPECIFIED);
        view.measure(w, h);
        return view.getMeasuredHeight();
//        int width = view.getMeasuredWidth();

    }

    /**
     * 以最省内存的方式读取本地资源的图片
     *
     * @param context
     * @param resId
     * @return
     */
    public static Bitmap readBitMap(Context context, int resId) {

        BitmapFactory.Options opt = new BitmapFactory.Options();

        opt.inPreferredConfig = Bitmap.Config.RGB_565;

        opt.inPurgeable = true;

        opt.inInputShareable = true;

        // 获取资源图片

        InputStream is = context.getResources().openRawResource(resId);

        return BitmapFactory.decodeStream(is, null, opt);

    }

    /**
     * 获取设备号
     *
     * @throws
     * @Title: getDeviceID
     * @Description: TODO
     * @author: huihaoyan
     * @param: @param context
     * @param: @return
     * @return: String
     */
    public static String getDeviceID(Context context) {
        TelephonyManager manager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        return manager.getDeviceId();
    }

    /**
     * 判断程序是在前台还是后台
     *
     * @param context
     * @return
     */
    public static boolean isBackground(Context context) {
        ActivityManager activityManager = (ActivityManager) context
                .getSystemService(Context.ACTIVITY_SERVICE);
        List<RunningAppProcessInfo> appProcesses = activityManager
                .getRunningAppProcesses();
        for (RunningAppProcessInfo appProcess : appProcesses) {
            if (appProcess.processName.equals(context.getPackageName())) {
                if (appProcess.importance == RunningAppProcessInfo.IMPORTANCE_BACKGROUND) {
                    return true;
                } else {
                    return false;
                }
            }
        }
        return false;
    }

    /**
     * @param activity
     * @return > 0 success; <= 0 fail
     */
    public static int getStatusHeight(Activity activity) {
        int statusHeight = 0;
        Rect localRect = new Rect();
        activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(localRect);
        statusHeight = localRect.top;
        if (0 == statusHeight) {
            Class<?> localClass;
            try {
                localClass = Class.forName("com.android.internal.R$dimen");
                Object localObject = localClass.newInstance();
                int i5 = Integer.parseInt(localClass.getField("status_bar_height").get(localObject).toString());
                statusHeight = activity.getResources().getDimensionPixelSize(i5);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (NumberFormatException e) {
                e.printStackTrace();
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (SecurityException e) {
                e.printStackTrace();
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            }
        }
        return statusHeight;
    }
}

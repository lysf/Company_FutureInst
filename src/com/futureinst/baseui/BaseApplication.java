package com.futureinst.baseui;

import java.io.File;

import android.app.Application;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Environment;
import android.os.Vibrator;
import android.util.Log;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.futureinst.net.HttpPath;
import com.futureinst.sharepreference.SharePreferenceUtil;
import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiskCache;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.utils.StorageUtils;


public class BaseApplication extends Application {
    private static BaseApplication application;
    private BaseFragmentActivity mTopActivity;
    private NotificationManager mNotificationManager;
    public static int EXIT_TIMEOUT = 2500;
    private Vibrator vibrator;
    private int mnTabIndex;
    private RequestQueue queue;

    @Override
    public void onCreate() {
        super.onCreate();
        application = this;
        queue = Volley.newRequestQueue(this);
        initImageLoader(getApplicationContext());
        initBaseFile();
        initPath();
    }

    private void initPath() {
//        SharePreferenceUtil preferenceUtil = SharePreferenceUtil.getInstance(this);
//        if (preferenceUtil.getServerOnline()) {//线下
//            HttpPath.URL = "http://www.futureinst.com/api";
//            HttpPath.SHARE_URL = "http://www.futureinst.com/mweb/event/";
//            HttpPath.SHARE_URL_ARTICLE = "http://www.futureinst.com/mweb/article/";
//            HttpPath.Cookie = "; Domain=futureinst.com; Path=/";
//            HttpPath.SHOP = "http://www.futureinst.com/mweb/gift/list";
//            HttpPath.PICPATH = "http://www.futureinst.com/api/uploadImage";
//            HttpPath.CHARGEURL = "http://www.futureinst.com/api";
//        } else {
//            HttpPath.URL = "http://182.92.183.219/api";
//            HttpPath.SHARE_URL = "http://182.92.183.219/mweb/event/";
//            HttpPath.SHARE_URL_ARTICLE = "http://182.92.183.219/mweb/article/";
//            HttpPath.Cookie = "; Domain=182.92.183.219; Path=/";
//            HttpPath.SHOP = "http://182.92.183.219/mweb/gift/list";
//            HttpPath.PICPATH = "http://182.92.183.219/api/uploadImage";
//            HttpPath.CHARGEURL = "http://182.92.183.219/api";
//        }
    }

    /**
     * 初始化ImageLoader
     */
    public static void initImageLoader(Context context) {
        File cacheDir = StorageUtils.getOwnCacheDirectory(context,
                "futureinst/Cache");// 获取到缓存的目录地址
        Log.d("cacheDir", cacheDir.getPath());
        // 创建配置ImageLoader(所有的选项都是可选的,只使用那些你真的想定制)，这个可以设定在APPLACATION里面，设置为全局的配置参数
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context)
                // 线程池内加载的数量
                .threadPoolSize(3)
                .threadPriority(Thread.NORM_PRIORITY - 2)
                .memoryCache(new WeakMemoryCache())
                .denyCacheImageMultipleSizesInMemory()
                .diskCacheFileNameGenerator(new Md5FileNameGenerator())
                .diskCacheExtraOptions(400, 600, null)
                        // 将保存的时候的URI名称用MD5 加密
                .tasksProcessingOrder(QueueProcessingType.LIFO)
                .diskCache(new UnlimitedDiskCache(cacheDir))// 自定义缓存路径
                        // .defaultDisplayImageOptions(DisplayImageOptions.createSimple())
                .writeDebugLogs() // Remove for release app
                .build();

        // Initialize ImageLoader with configuration.
        ImageLoader.getInstance().init(config);// 全局初始化此配置
    }

    private void initBaseFile() {
        File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/FutureInst");
        if (!file.exists())
            file.mkdirs();
    }

    //返回volley队列
    public RequestQueue getRequestQueue() {
        return queue;
    }

    public NotificationManager getNotificationManager() {
        if (mNotificationManager == null)
            mNotificationManager = (NotificationManager) getSystemService(android.content.Context.NOTIFICATION_SERVICE);
        return mNotificationManager;
    }

    // 单例模式中获取唯一的ExitApplication 实例
    public static BaseApplication getInstance() {
        return application;
    }

    public void setTop(BaseFragmentActivity activity) {
        mTopActivity = activity;
    }

    public BaseFragmentActivity getTop() {
        return mTopActivity;
    }


    public void vibrate() {
        vibrator.vibrate(200);
    }

    /**
     * @return the mnTabIndex
     */
    public int getTabIndex() {
        return mnTabIndex;
    }

    /**
     * @param mnTabIndex the mnTabIndex to set
     */
    public void setTabIndex(int mnTabIndex) {
        this.mnTabIndex = mnTabIndex;
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }
}

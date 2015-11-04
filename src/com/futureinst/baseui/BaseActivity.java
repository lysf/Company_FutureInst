package com.futureinst.baseui;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import com.futureinst.R;
import com.futureinst.net.HttpPostParams;
import com.futureinst.net.HttpResponseUtils;
import com.futureinst.sharepreference.SharePreferenceUtil;
import com.futureinst.utils.MyProgressDialog;
import com.futureinst.widget.TitleButton;
import com.umeng.analytics.MobclickAgent;


@SuppressLint("HandlerLeak")
public abstract class BaseActivity extends BaseFragmentActivity {
	protected Handler handler = new Handler(){
		@Override
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 0:
				Toast.makeText(getApplicationContext(), getResources().getString(R.string.data_over), Toast.LENGTH_SHORT).show();
				break;
			}
		}
    };
	protected int page = 1;
	protected String last_id = "0";
    protected final static String OVERIDE = "overide";
    protected String TAG = getClass().getSimpleName();
    private TextView mtxtTitle;
    private TitleButton mbtnBack;
    private TitleButton mbtnRight;
    private ProgressBar mProgressBar;
    private ImageView mRightImage;
    private ImageView mLeftImage;
    protected MyProgressDialog progressDialog;
    protected HttpPostParams httpPostParams;
    protected SharePreferenceUtil preferenceUtil;
    protected HttpResponseUtils httpResponseUtils;
    protected NotificationManager mNotificationManager;
    protected boolean isActive = true;
    private OnClickListener mClickListener = new OnClickListener() {

        @Override
        public void onClick(View v) {
            if (v == mbtnBack) {
                if (!onLeftClick(v))
                    onBackPressed();
            } else if (v == mbtnRight) {
                onRightClick(v);
            }else if( v == mRightImage){
            	onRightImageViewClick(v);
            }else if( v == mLeftImage){
            	if(!onLeftImageClick(v)){
            		onBackPressed();
            	}else{
            		onLeftImageViewClick(v);
            	}
            }
        }
    };
	@SuppressWarnings({ "rawtypes", "deprecation" })
	public void setNotiType(int iconId, String contentTitle,
			String contentText, Class activity, String from) {
		Intent notifyIntent = new Intent(this, activity);
		notifyIntent.putExtra("to", from);
		PendingIntent appIntent = PendingIntent.getActivity(this, 0,
				notifyIntent, 0);
		Notification myNoti = new Notification();
		myNoti.flags = Notification.FLAG_AUTO_CANCEL;
		myNoti.icon = iconId;
		myNoti.tickerText = contentTitle;
		myNoti.defaults = Notification.DEFAULT_SOUND;
		myNoti.setLatestEventInfo(BaseActivity.this, contentTitle, contentText, appIntent);
		mNotificationManager.notify(0, myNoti);
	}

    /**
     * TitleBar
     */
    private ViewGroup mviewTool;
    /**
     * 用于放置activity的content
     */
    private ViewGroup mGroupContent = null;
	private LinearLayout baseView;

    /**
     * 请在{@link #localOnCreate(Bundle)}做相关操作，以后不要重写此方法
     */
    @Deprecated
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 创建状态栏的管理实例  
        SystemBarTintManager tintManager = new SystemBarTintManager(this);  
        // 激活状态栏设置  
         tintManager.setStatusBarTintEnabled(true);  
          // 激活导航栏设置  
         tintManager.setNavigationBarTintEnabled(true); 
      // 设置一个颜色给系统栏  
//         tintManager.setTintColor(getResources().getColor(R.color.status_bar));  
//         tintManager.setNavigationBarTintColor(getResources().getColor(R.color.text_color_3));
         tintManager.setStatusBarTintColor(getResources().getColor(R.color.status_bar));
//         tintManager.setStatusBarTintResource(R.color.text_color_3);
         // 设置一个样式背景给导航栏  
//         tintManager.setNavigationBarTintResource(R.drawable.my_tint);  
         // 设置一个状态栏资源  
//         tintManager.setStatusBarTintDrawable(MyDrawable);  
        mNotificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        if (savedInstanceState != null && savedInstanceState.getBoolean(OVERIDE)) {
            return;
        }
        setCustomerView(R.layout.layout_activity_base);
        localOnCreate(savedInstanceState);
    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onPageStart(this.getClass().getName());
        MobclickAgent.onResume(this);
    }

    /**
     * @see android.app.Activity#onPause()
     */
    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd(this.getClass().getName());
        MobclickAgent.onPause(this);
    }
    @Override
    protected void onStop() {
    	// TODO Auto-generated method stub
    	super.onStop();
    	
    }

    protected String getPageName() {
        return getClass().getSimpleName();
    }

    @Override
    public void setTitleProgressVisibility(boolean show) {
        mProgressBar.setVisibility(show ? View.VISIBLE : View.GONE);
    }

    /**
     * Judge whether the screen is larger than 5 inch or not.
     * 
     * @return whether the screen is larger than 5 inch or not.
     */
    @SuppressWarnings("deprecation")
	protected boolean isLarge5() {
        WindowManager wm = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        DisplayMetrics displayMetrics = new DisplayMetrics();
        display.getMetrics(displayMetrics);

        int w = display.getWidth();
        int h = display.getHeight();

        double pixels = Math.sqrt(w * w + h * h);
        double inch = pixels / displayMetrics.densityDpi;

        return inch > 5.0;
    }

    /**
     * 可通过此方法设置自定义view，为了右划菜单的实现
     * 
     * @param res
     */
    protected void setCustomerView(int res) {
        super.setContentView(res);
        preferenceUtil = SharePreferenceUtil.getInstance(this);
        progressDialog = MyProgressDialog.getInstance(this);
        httpPostParams = HttpPostParams.getInstace();
        httpResponseUtils = HttpResponseUtils.getInstace(this);
        baseView = (LinearLayout) findViewById(R.id.ll_activity_container);
        mviewTool = (ViewGroup) findViewById(R.id.title_bar);
        mbtnBack = (TitleButton) findViewById(R.id.btn_left_base);
        mbtnBack.setPadding(15, 15, 15, 15);
        mbtnRight = (TitleButton) findViewById(R.id.btn_right_base);
        mtxtTitle = (TextView) findViewById(R.id.txt_title_base);
        mProgressBar = (ProgressBar) findViewById(R.id.progressBar1);

        mGroupContent = (ViewGroup) findViewById(R.id.activity_content);
        mbtnBack.setOnClickListener(mClickListener);
        mbtnRight.setOnClickListener(mClickListener);
        
        mRightImage = (ImageView)findViewById(R.id.iv_right);
        mRightImage.setOnClickListener(mClickListener);
        mLeftImage = (ImageView)findViewById(R.id.iv_left);
        mLeftImage.setOnClickListener(mClickListener);
    }
    protected int getRightTitleId(){
    	return R.id.btn_right_base;
    }
    

    /**
     * 重写此方法而不再重写{@link Activity#onCreate(Bundle)};
     * 
     * @param savedInstanceState
     */
    protected abstract void localOnCreate(Bundle savedInstanceState);

    @Override
    public void setContentView(int layoutResID) {
        View view = LayoutInflater.from(this).inflate(layoutResID, mGroupContent, false);
        setContentView(view);
    }

    @Override
    public void setContentView(View view) {
        // final float fDensity = getResources().getDisplayMetrics().density;
        // int top = (int) (10 * fDensity + 0.5f);
        // view.setPadding(view.getPaddingLeft(), view.getPaddingTop()+top,
        // view.getPaddingRight(), view.getPaddingBottom());
        mGroupContent.addView(view);
    }

    @Deprecated
    @Override
    public void setContentView(View view, LayoutParams params) {
    }

    /**
     * 如果不为空将显示TitleBar，否则会隐藏
     */
    @Override
    public void setTitle(CharSequence title) {
        mviewTool.setVisibility(TextUtils.isEmpty(title) ? View.GONE : View.VISIBLE);
        mtxtTitle.setText(title);
    }
    
    @Override
    public void setTitle(int titleId) {
        setTitle(getString(titleId));
    }
    
    
    public ViewGroup getTitlebarView() {
        return mviewTool;
    }
//    public void setTitleBackGround(int color){
//    	mviewTool.setBackgroundColor(color);
//    }
    /**
     * 设置后左边的Button会显示content，可以为空不调用次方法左边的button不会显示
     * 
     * @param content
     */
    public void setLeft(String content) {
        mbtnBack.setVisibility(View.VISIBLE);
        mbtnBack.setText(content);
    }

    /**
     * 设置后右边的Button会显示content，可以为空不调用次方法右边的button不会显示
     * 
     * @param content
     */
    public void setRight(String content) {
        mbtnRight.setVisibility(View.VISIBLE);
        mbtnRight.setText(content);
        if (!TextUtils.isEmpty(content)) {
//            mbtnRight.setBackgroundResource(R.drawable.btn_normal_bg);
            mbtnRight.getLayoutParams().height = LayoutParams.WRAP_CONTENT;
//            ((MarginLayoutParams)mbtnRight.getLayoutParams()).rightMargin = getResources().getDimensionPixelOffset(R.dimen.normal_margin);
            mbtnRight.requestLayout();
        }
    }
    public void setRight(int id) {
        setRight(getString(id));
    }

    /**
     * @return TitleBar中间的TextView，获得后可以设置点击事件等相关操作
     */
    protected TextView getTitleView() {
        return mtxtTitle;
    }

    /**
     * @return 左侧Button，可进行修改样式等，点击时会回调{@link #onLeftClick(View)}
     */
    public TitleButton getLeftButton() {
        return mbtnBack;
    }

    /**
     * @return 右侧Button，可进行修改样式等;点击时会回调{@link #onRightClick(View)}
     */
    public TitleButton getRightButton() {
    	mbtnRight.setVisibility(View.VISIBLE);
        return mbtnRight;
    }
    /**
     * 返回根 view
    * @方法描述: TODO
    * @创建人：lzhixiang
    * @创建时间：2015-3-3 下午12:16:03
    * @返回类型：LinearLayout
     */
    public LinearLayout getBaseView(){
    	return baseView;
    }

    /**
     * 左侧Button点击时回调,如果返回true不会执行onback
     * 
     * @param view
     */
    protected boolean onLeftClick(View view) {
        return false;
    }
    protected boolean onLeftImageClick(View view) {
    	return false;
    }
    
    public ImageView getRightImageView(){
    	mRightImage.setVisibility(View.VISIBLE);
    	return mRightImage;
    }
    public ImageView getLeftImageView(){
    	return mLeftImage;
    }
    /**
     * 右侧Button点击时回调
     * 
     * @param view
     */
    protected void onRightClick(View view) {

    }
    protected void onRightImageViewClick(View view) {
    	
    }
    protected void onLeftImageViewClick(View view) {
    	
    }
    /*
     * (non-Javadoc)
     * 
     * @see android.app.Activity#onDestroy()
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    /**
     * @see android.app.Activity#onKeyDown(int, android.view.KeyEvent)
     */
//    @Override
//    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        if (isRoot() && event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
//            long second = System.currentTimeMillis();
//            if (second - mClickTime < BaseApplication.EXIT_TIMEOUT) {
//            	 ImageUtils.getInstance(this).clear();
//                finish();
//                return true;
//            } else {
//                mClickTime = second;
//                Toast.makeText(this, R.string.click_for_quit, Toast.LENGTH_SHORT).show();
//                return true;
//            }
//        }
//
//        return super.onKeyDown(keyCode, event);
//    }

    protected boolean isRoot() {
        return isTaskRoot() || (getParent() != null && getParent().isTaskRoot());
    }
    
    protected void closeInput() {
    	InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		if (inputMethodManager != null && getCurrentFocus() != null) {
			inputMethodManager.hideSoftInputFromWindow(getCurrentFocus()
					.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
		}
    }
    public void showToast(String message){
    	Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }
    /** 隐藏软键盘
	  * hideSoftInputView
	  * @Title: hideSoftInputView
	  * @Description: TODO
	  * @param  
	  * @return void
	  * @throws
	  */
	public void hideSoftInputView() {
		InputMethodManager manager = ((InputMethodManager) this.getSystemService(Activity.INPUT_METHOD_SERVICE));
		if (getWindow().getAttributes().softInputMode != WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN) {
			if (getCurrentFocus() != null)
				manager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
		}
	}
	// 显示软键盘
		public void showSoftInputView(final View view) {
            InputMethodManager inputManager =(InputMethodManager) view.getContext().getSystemService(INPUT_METHOD_SERVICE);
            inputManager.showSoftInput(view, 0);
//			if (getWindow().getAttributes().softInputMode == WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN) {
//				if (getCurrentFocus() != null){
//                 new Handler().postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//
//                    }
//                },600);
//
//
////                }
//			}
		}
		
	@Override
	public void onBackPressed() {
		super.onBackPressed();
		finish();
	}
	  
	    @Override  
	    protected void onActivityResult(int requestCode, int resultCode, Intent data) {  
	        FragmentManager fm = getSupportFragmentManager();  
	        int index = requestCode >> 16;  
	        if (index != 0) {  
	            index--;  
	            if (fm.getFragments() == null || index < 0  
	                    || index >= fm.getFragments().size()) {  
	                Log.w(TAG, "Activity result fragment index out of range: 0x"  
	                        + Integer.toHexString(requestCode));  
	                return;  
	            }  
	            Fragment frag = fm.getFragments().get(index);  
	            if (frag == null) {  
	                Log.w(TAG, "Activity result no fragment exists for index: 0x"  
	                        + Integer.toHexString(requestCode));  
	            } else {  
	                handleResult(frag, requestCode, resultCode, data);  
	            }  
	            return;  
	        }  
	  
	    }  
	  
	    /** 
	     * 递归调用，对所有子Fragement生效 
	     *  
	     * @param frag 
	     * @param requestCode 
	     * @param resultCode 
	     * @param data 
	     */  
	    private void handleResult(Fragment frag, int requestCode, int resultCode,  
	            Intent data) {  
	        frag.onActivityResult(requestCode & 0xffff, resultCode, data);  
	        List<Fragment> frags = frag.getChildFragmentManager().getFragments();  
	        if (frags != null) {  
	            for (Fragment f : frags) {  
	                if (f != null)  
	                    handleResult(f, requestCode, resultCode, data);  
	            }  
	        }  
	}  
	   @Override
	public void finish() {
//		   MobclickAgent.onKillProcess( this );
		super.finish();
	}
	   private boolean isAppOnForeground(Context context) {   
		      ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);   
		      List<RunningAppProcessInfo> appProcesses = activityManager.getRunningAppProcesses();   
		      if (appProcesses == null) {   
		        return false;   
		      }   
		      final String packageName = context.getPackageName();   
		      for (RunningAppProcessInfo appProcess : appProcesses) {   
		        if (appProcess.importance == RunningAppProcessInfo.IMPORTANCE_FOREGROUND && appProcess.processName.equals(packageName)) {   
		          return true;   
		        }   
		      }   
		      return false;   
		  }  
}

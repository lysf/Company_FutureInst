package com.futureinst.home;



import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.futureinst.R;
import com.futureinst.baseui.BaseActivity;
import com.futureinst.db.PushMessageCacheUtil;
import com.futureinst.global.Content;
import com.futureinst.home.forecast.ForecastFragment;
import com.futureinst.home.find.FondFragment;
import com.futureinst.home.pushmessage.PushMessageActivity;
import com.futureinst.home.userinfo.UserInfoFragment;
import com.futureinst.login.LoginActivity;
import com.futureinst.model.basemodel.BaseModel;
import com.futureinst.model.dailytask.DailyTaskInfoDAO;
import com.futureinst.model.push.PushMessageDAO;
import com.futureinst.model.usermodel.UserInformationInfo;
import com.futureinst.model.version.VersionDAO;
import com.futureinst.net.PostCommentResponseListener;
import com.futureinst.net.PostMethod;
import com.futureinst.net.PostType;
import com.futureinst.service.UpdateDialogShow;
import com.futureinst.service.UpdateService;
import com.futureinst.utils.ActivityManagerUtil;
import com.futureinst.utils.BadgeUtil;
import com.futureinst.utils.TaskTipUtil;
import com.futureinst.utils.TimeUtil;
import com.futureinst.utils.Utils;
import com.igexin.sdk.PushManager;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;


@SuppressLint("ClickableViewAccessibility")
public class HomeActivity extends BaseActivity {
	private boolean isCloseTab = true;
//	private PushMessageUtils pushMessageUtils;
	private PushMessageCacheUtil messageCacheUtil;
	public static boolean isUpdate = false;
	private boolean isHide = false;  
	private float lastX = 0;  
	private float lastY = 0;  
//	private LinearLayout ll_home_tab;
	private BroadcastReceiver receiver;
	private String cid;
//	private TextView tv_messageCount;
    private ImageView iv_message_tip;
	private View[] views;
	private List<Fragment> fragments;
	private Animation animation;
	private FragmentActivityTabAdapter activityAdapter;
	
	//广告
	private RelativeLayout rl_ad;
	private ImageView iv_ad,iv_cancel;
	
	
	private Handler handler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case -11:
				UserInformationInfo userInformationInfo = (UserInformationInfo) msg.obj;
				if(userInformationInfo.getUser_record() != null){
					preferenceUtil.setAssure(userInformationInfo.getUser_record().getAssure());
					preferenceUtil.setAsset(userInformationInfo.getUser_record().getAsset());
//					setRanking(userInformationInfo.getUser_record());
				}
				break;
                case -1:
                    getMessageCount();
                    break;
			}
		}
    };
	@Override
	protected void localOnCreate(Bundle savedInstanceState) {
		setContentView(R.layout.activity_home2);
		Content.statusHeight = Utils.getStatusHeight(this);
        ActivityManagerUtil.addActivity(this);
		initVeiw();
		initFragment();
		if(!preferenceUtil.getInstallTag()){
			add_download();
		}
		get_android_version();

        if(getIntent().getBooleanExtra("push",false)
                && (!TextUtils.isEmpty(preferenceUtil.getUUid()))){
//            activityAdapter.setFragmentShow(3);
            final Intent intent = new Intent(this, PushMessageActivity.class);
            intent.putExtra("push",true);
            PushMessageDAO pushMessageDAO = (PushMessageDAO) getIntent().getSerializableExtra("pushMessage");
            intent.putExtra("pushMessage", pushMessageDAO);
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    startActivity(intent);
                }
            },300);
        }

		
	}
	 //显示新手引导
	 private void showGuide(){
		 if(preferenceUtil.getGuide1())
			 return;
		 WelcomeDialog.showWelcom(this);
	 }
	 
	 OnClickListener clickListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.iv_ad://广告页
				Intent intent = new Intent(Intent.ACTION_VIEW,Uri.parse(versionInfo.getLoad_ad_url()));
				startActivity(intent);
				rl_ad.setVisibility(View.GONE);
				break;
			case R.id.iv_cancel://隐藏
				rl_ad.setVisibility(View.GONE);
				showGuide();
				break;
			}
		}
	};
	private void initVeiw() {
		//广告
		rl_ad = (RelativeLayout) findViewById(R.id.rl_ad);
		iv_ad = (ImageView) findViewById(R.id.iv_ad);
		iv_cancel = (ImageView) findViewById(R.id.iv_cancel);
		iv_ad.setOnClickListener(clickListener);
		iv_cancel.setOnClickListener(clickListener);
		
		
		views = new View[4];
		fragments = new ArrayList<Fragment>(); 
//		ll_home_tab = (LinearLayout) findViewById(R.id.ll_home_table);
//		tv_messageCount = (TextView) findViewById(R.id.tv_message_count);
        iv_message_tip = (ImageView)findViewById(R.id.iv_message_tip);
		
		views[0]  = findViewById(R.id.tab1);
		views[1]  = findViewById(R.id.tab2);
		views[2]  = findViewById(R.id.tab3);
		views[3]  = findViewById(R.id.tab4);
		
		receiver = new BroadcastReceiver() {
			@Override
			public void onReceive(Context context, Intent intent) {
				if(intent.getAction().equals("newPushMessage")){
					getMessageCount();
				}
				else if(intent.getAction().equals("rank")){
					activityAdapter.setFragmentShow(2);
				}
			}
		};
		IntentFilter filter = new IntentFilter();
		filter.addAction("newPushMessage");
		filter.addAction("rank");
		registerReceiver(receiver, filter);
		
	}
	
	
	private void initFragment(){
		fragments.add(new ForecastFragment());
		fragments.add(new FondFragment());
		fragments.add(new WelfareFragment());
		fragments.add(new UserInfoFragment());
		activityAdapter = new FragmentActivityTabAdapter(this, fragments, R.id.container, views);
	}
	//0-3
	public void setTab(int position){
		activityAdapter.setFragmentShow(position);
	}
	public int getCurrentTab(){
		return activityAdapter.getCurrentTab();
	}
	private boolean isLogin(){
		if (TextUtils.isEmpty(preferenceUtil.getUUid())) {
			Intent intent = new Intent(this, LoginActivity.class);
			startActivity(intent);
			return false;
		} 
		return true;
	}
	// 判断是否登录
	private void judgeIsLogin() {
		if (!TextUtils.isEmpty(preferenceUtil.getUUid())) {
            Log.i(TAG,"=========id="+preferenceUtil.getID()+"==uuid="+preferenceUtil.getUUid());
            getMessageCount();
			if(!isUpdate){
				//初始化推送
				query_user_record();
                query_user_daily_task();
                    PushManager.getInstance().initialize(this);
                isUpdate = true;

			}
		}
	}

	// 获取个人信息
	private void query_user_record() {
		httpResponseUtils.postJson(
				httpPostParams.getPostParams(
						PostMethod.query_user_record.name(),
						PostType.user_info.name(),
						httpPostParams.query_user_record(preferenceUtil.getID()
                                + "", preferenceUtil.getUUid())),
				UserInformationInfo.class, new PostCommentResponseListener() {
					@Override
					public void requestCompleted(Object response)
							throws JSONException {
						if (response == null)
							return;
						UserInformationInfo userInformationInfo = (UserInformationInfo) response;
						
						Message message =Message.obtain();
						message.what = -11;
						message.obj = userInformationInfo;
						handler.sendMessage(message);
					}
				});
	}
    //查询今日任务
	private void query_user_daily_task() {
        if(TextUtils.isEmpty(preferenceUtil.getUUid())){
            return;
        }
		httpResponseUtils.postJson(
				httpPostParams.getPostParams(
						PostMethod.query_user_daily_task.name(),
						PostType.daily_task.name(),
						httpPostParams.query_user_daily_task(preferenceUtil.getID()
                                + "", preferenceUtil.getUUid())),
                DailyTaskInfoDAO.class, new PostCommentResponseListener() {
					@Override
					public void requestCompleted(Object response)
							throws JSONException {
						if (response == null)
							return;
                        DailyTaskInfoDAO dailyTaskInfo = (DailyTaskInfoDAO) response;
                        if(TaskTipUtil.isShowTip(dailyTaskInfo)){
                            iv_message_tip.setVisibility(View.VISIBLE);
                        }else{
                            iv_message_tip.setVisibility(View.INVISIBLE);
                        }
                        handler.sendEmptyMessage(-1);
					}
				});
	}
	//获取未读消息数量
		private void getMessageCount(){
			if(TextUtils.isEmpty(preferenceUtil.getUUid())){
				return;
			}
			messageCacheUtil = PushMessageCacheUtil.getInstance(this);
			int count =  messageCacheUtil.getUnReadMessage();
			if(count>0){
				BadgeUtil.setBadgeCount(getApplicationContext(), count);
//				tv_messageCount.setText(count+"");
                iv_message_tip.setVisibility(View.VISIBLE);
			}
//            else{
//				BadgeUtil.resetBadgeCount(getApplicationContext());
//                iv_message_tip.setVisibility(View.INVISIBLE);
//			}
		}
		
	//显示广告
		private VersionDAO versionInfo;
	private void showAD(VersionDAO versionDAO){
		String current = TimeUtil.longToString(System.currentTimeMillis(), TimeUtil.FORMAT_DATE);
		if(current.equals(preferenceUtil.getADTime())
				|| TextUtils.isEmpty(versionDAO.getLoad_ad_image())
				|| versionDAO.getLoad_ad_image().equals("null")){
			showGuide();
			return;
		}
		versionInfo = versionDAO;
		preferenceUtil.setADTime(current);
		ImageLoader.getInstance().displayImage(versionDAO.getLoad_ad_image(), iv_ad,
                new ImageLoadingListener() {
                    @Override
                    public void onLoadingStarted(String imageUri, View view) {
                    }

                    @Override
                    public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
//						rl_ad.setVisibility(View.GONE);
                    }

                    @Override
                    public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                        rl_ad.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onLoadingCancelled(String imageUri, View view) {
                        rl_ad.setVisibility(View.GONE);

                    }
                });
	}
	//获取版本信息
	private void get_android_version(){
		Content.isPull = true;
//		progressDialog.progressDialog();
		httpResponseUtils.postJson(
				httpPostParams.getPostParams(PostMethod.get_android_version.name(), PostType.common.name(), 
						httpPostParams.get_android_version()), 
				VersionDAO.class,
				new PostCommentResponseListener() {
					@Override
					public void requestCompleted(Object response) throws JSONException {
						progressDialog.cancleProgress();
						Content.isPull = false;
						if(response == null) return;
						VersionDAO versionDAO = (VersionDAO) response;
                        Content.min_p2p_give_exchange = versionDAO.getMin_p2p_give_exchange();
                        showAD(versionDAO);
                        Content.disable_app_sign_in = versionDAO.getDisable_app_sign_in() != 1;
						if(versionDAO.getAoto_refresh_event_price() == 1){
							Content.is_aoto_refresh_event_price = true;
							Content.aoto_refresh_event_price_interval = versionDAO.getAoto_refresh_event_price_interval();
						}
						Content.main_list_fresh_interval = versionDAO.getMain_list_fresh_interval();
						int versionCode = Utils.getVersionCode(HomeActivity.this);
						if(versionCode < versionDAO.getAndroid_version()){
							update(versionDAO.getDownload_url());
						}
					}
				});
	}
	//添加渠道下载标记
	private void add_download(){
		httpResponseUtils.postJson(
				httpPostParams.getPostParams(PostMethod.add_download.name(), PostType.common.name(), 
						httpPostParams.add_download()), 
				BaseModel.class,
				new PostCommentResponseListener() {
					@Override
					public void requestCompleted(Object response) throws JSONException {
						if(response == null) return;
						preferenceUtil.setInstallTag();
					}
				});
	}
	private void update(final String url){
		View view = LayoutInflater.from(HomeActivity.this).inflate(R.layout.version_update, null, false);
		// 得到屏幕的宽和高
		DisplayMetrics metrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(metrics);
		int width = metrics.widthPixels;
		int height = metrics.heightPixels;
		final Dialog dialog = UpdateDialogShow.showDialog(HomeActivity.this, view, width, height);
		// TextView textView = (TextView) view.findViewById(R.id.version);
		TextView textView = (TextView) view.findViewById(R.id.update_detail);
		Button button = (Button) view.findViewById(R.id.next);
		Button button2 = (Button) view.findViewById(R.id.update);
		button.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				dialog.dismiss();
			}
		});
		button2.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				dialog.dismiss();
				// 下载新版本
//                showToast("新版本正在后台下载...");
						Intent intent = new Intent(HomeActivity.this, UpdateService.class);
						intent.putExtra("url", url);
						startService(intent);
			}
		});
	
	}
	
	
//	@Override
//	public boolean dispatchTouchEvent(MotionEvent event) {
//		float x = event.getX();
//		float y = event.getY();
//		switch (event.getAction()) {
//		case MotionEvent.ACTION_DOWN:
//			lastY = y;
//			lastX = x;
//			break;
//		case MotionEvent.ACTION_MOVE:
//			if(!isCloseTab) break;
//			float dy = Math.abs(y - lastY);
//			float dx = Math.abs(x - lastX);
//			Log.i(TAG, "---------dx="+dx+",dy="+dy);
//			boolean down = y > lastY ? true : false;
//			lastY = y;
//			lastX = x;
//			break;
//		}
//		return super.dispatchTouchEvent(event);
//	}
	@Override
	protected void onResume() {
		super.onResume();
		judgeIsLogin();
		activityAdapter.getCurrentFragment().onResume();
		if(activityAdapter.getCurrentTab() != 0){
			isCloseTab = false;
		}
		query_user_daily_task();



	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
        if(isUpdate){
            PushManager.getInstance().stopService(this.getApplicationContext());
            isUpdate = false;
        }
		SystemTimeUtile.getInstance(0L).setFlag(false);
		if (receiver != null)
			unregisterReceiver(receiver);
        ActivityManagerUtil.finishActivity();
	}


    @Override
    public void finish() {
        super.finish();
    }
    class FragmentActivityTabAdapter implements OnClickListener {
		private List<Fragment> fragments; // 一个tab页面对应一个Fragment
		private View[] btns; // 用于切换tab
		private FragmentActivity activity; // Fragment
		private int fragmentContentId; // Activity中所要被替换的区域的id
		private int currentTab; // 当前Tab页面索引
		public FragmentActivityTabAdapter(FragmentActivity activity,
				List<Fragment> fragments, int fragmentContentId, View[] btns) {
			this.fragments = fragments;
			this.btns = btns;
			this.activity = activity;
			this.fragmentContentId = fragmentContentId;

			// 默认显示第一页
			FragmentTransaction ft = activity.getSupportFragmentManager()
					.beginTransaction();
			ft.add(fragmentContentId, fragments.get(0));
			ft.commitAllowingStateLoss();
			btns[0].setSelected(true);
			for (int i = 0; i < btns.length; i++) {
				btns[i].setOnClickListener(this);
			}
		}

		/**
		 * 切换tab
		 * 
		 * @param idx
		 */
		private void showTab(int idx) {
			for (int i = 0; i < fragments.size(); i++) {
				Fragment fragment = fragments.get(i);
				FragmentTransaction ft = obtainFragmentTransaction(idx);

				if (idx == i) {
					ft.show(fragment);
				} else {
					ft.hide(fragment);
				}
				ft.commitAllowingStateLoss();
			}
			currentTab = idx; // 更新目标tab为当前tab
		}
		private FragmentTransaction obtainFragmentTransaction(int index) {
			FragmentTransaction ft = activity.getSupportFragmentManager()
					.beginTransaction();
			return ft;
		}

		public int getCurrentTab() {
			return currentTab;
		}

		public Fragment getCurrentFragment() {
			return fragments.get(currentTab);
		}
		public void setFragmentShow(int i){
			if(i!=0 && !isLogin()){
				return;
			}

			btns[currentTab].setSelected(false);
			//把当前tab设为选中状态
			btns[i].setSelected(true);
			currentTab = i;
			Fragment fragment = fragments.get(i);
			FragmentTransaction ft = obtainFragmentTransaction(i);

			if (fragment.isAdded()) {
				fragment.onResume(); // 启动目标tab的onResume()
			} else {
				ft.add(fragmentContentId, fragment);
			}
			showTab(i); // 显示目标tab
			ft.commitAllowingStateLoss();
		
		}
		@Override
		public void onClick(View v) {
			for (int i = 0; i < btns.length; i++) {
				if (btns[i] == v ) {
					if(i!=0 && !isLogin()){
						return;
					}
                    isCloseTab = i == 0;
                    if(i == 3){//个人
                        if(!preferenceUtil.getDailyTaskTip()){
                            //今日任务功能提示
                            WelcomeDialog.showTip(HomeActivity.this,
                                    getResources().getString(R.string.daily_tip_title),getResources().getString(R.string.daily_tip_message));
                            preferenceUtil.setDailyTaskTip();
                        }
                    }
					btns[currentTab].setSelected(false);
					//把当前tab设为选中状态
					btns[i].setSelected(true);
					currentTab = i;
					Fragment fragment = fragments.get(i);
					FragmentTransaction ft = obtainFragmentTransaction(i);

					if (fragment.isAdded()) {
						fragment.onResume(); // 启动目标tab的onResume()
					} else {
						ft.add(fragmentContentId, fragment);
					}
					showTab(i); // 显示目标tab
					ft.commitAllowingStateLoss();
					
				}
			}
		}

	}

}

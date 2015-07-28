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
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.futureinst.R;
import com.futureinst.baseui.BaseActivity;
import com.futureinst.home.forecast.ForecastFragment;
import com.futureinst.home.hold.HoldingFragment;
import com.futureinst.home.ranking.RankAndRecordFragment;
import com.futureinst.home.userinfo.UserInfoFragment2;
import com.futureinst.login.LoginActivity;
import com.futureinst.model.usermodel.UserInfo;
import com.futureinst.model.usermodel.UserInformationDAO;
import com.futureinst.model.usermodel.UserInformationInfo;
import com.futureinst.model.version.VersionDAO;
import com.futureinst.net.PostCommentResponseListener;
import com.futureinst.net.PostMethod;
import com.futureinst.net.PostType;
import com.futureinst.push.PushMessageUtils;
import com.futureinst.service.UpdateDialogShow;
import com.futureinst.service.UpdateService;
import com.futureinst.utils.Utils;
import com.igexin.sdk.PushManager;


@SuppressLint("ClickableViewAccessibility")
public class HomeActivity extends BaseActivity {
	private boolean isHide = false;  
	private float lastX = 0;  
	private float lastY = 0;  
	private LinearLayout ll_home_tab;
	private BroadcastReceiver receiver;
	private String cid;
	private TextView tv_ranking,tv_messageCount;
	private ImageView iv_ranking;
	private View[] views;
	private List<Fragment> fragments;
	private Animation animation;
	@Override
	protected void localOnCreate(Bundle savedInstanceState) {
		setContentView(R.layout.activity_home2);
		
		initVeiw();
		initFragment();
		get_android_version();
	}
	private void initVeiw() {
		
		views = new View[4];
		fragments = new ArrayList<Fragment>(); 
		ll_home_tab = (LinearLayout) findViewById(R.id.ll_home_table);
		tv_ranking = (TextView) findViewById(R.id.tv_ranking);
		iv_ranking = (ImageView) findViewById(R.id.iv_ranking);
		tv_messageCount = (TextView) findViewById(R.id.tv_message_count);
		
		views[0] = (TextView) findViewById(R.id.tab_forecast);
		views[1] = (TextView) findViewById(R.id.tab_revoke);
		views[2] = (LinearLayout) findViewById(R.id.tab_ranking);
		views[3] = (RelativeLayout) findViewById(R.id.tab_personal);
		
		receiver = new BroadcastReceiver() {
			@Override
			public void onReceive(Context context, Intent intent) {
				if(intent.getAction().equals("newPushMessage")){
					getMessageCount();
				}
			}
		};
		IntentFilter filter = new IntentFilter();
		filter.addAction("newPushMessage");
		registerReceiver(receiver, filter);
		if(TextUtils.isEmpty(preferenceUtil.getCLIENTID())){
			cid = PushManager.getInstance().getClientid(this);
			preferenceUtil.setCLIENTID(cid);
		}else{
			cid = preferenceUtil.getCLIENTID();
		}
	}
	private void initFragment(){
		fragments.add(new ForecastFragment());
		fragments.add(new HoldingFragment());
		fragments.add(new RankAndRecordFragment());
		fragments.add(new UserInfoFragment2());
		new FragmentActivityTabAdapter(this, fragments, R.id.container, views);
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
			if(!PushManager.getInstance().isPushTurnedOn(this.getApplicationContext())){
				//初始化推送
				PushManager.getInstance().initialize(this.getApplicationContext());
			}
			
			getMessageCount();
			query_user_record();
			update_user_cid(cid);
		} 
	}
	
	
	//设置用户的排名
	private void setRanking(UserInformationDAO userInformationDAO){
		tv_ranking.setText(userInformationDAO.getRank()+"");
		tv_ranking.setAlpha(0.3f);
		
		if(userInformationDAO.getRank() > userInformationDAO.getLastRank()){
			iv_ranking.setImageDrawable(getResources().getDrawable(R.drawable.tab_ranking_up));
		}else if(userInformationDAO.getRank() < userInformationDAO.getLastRank()){
			iv_ranking.setImageDrawable(getResources().getDrawable(R.drawable.tab_ranking_down));
		}else{
			iv_ranking.setImageDrawable(getResources().getDrawable(R.drawable.tab_ranking_balance));
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
						setRanking(userInformationInfo.getUser_record());
					}
				});
	}
	//上传clientid
	private void update_user_cid(final String cid){
		httpResponseUtils.postJson(httpPostParams.getPostParams(PostMethod.update_user.name(), PostType.user.name(), 
				httpPostParams.update_user_cid(preferenceUtil.getUUid(), preferenceUtil.getID()+"",cid)), 
				UserInfo.class,
				new PostCommentResponseListener() {
					@Override
					public void requestCompleted(Object response) throws JSONException {
						if(response == null) return;
					}
				});
	}
	//获取未读消息数量
		private void getMessageCount(){
			int count =  PushMessageUtils.getInstance(this).getUnReadMessageCount();
			if(count>0){
				tv_messageCount.setText(count+"");
				tv_messageCount.setVisibility(View.VISIBLE);
			}else{
				tv_messageCount.setText("0");
				tv_messageCount.setVisibility(View.INVISIBLE);
			}
		}
	//获取版本信息
	private void get_android_version(){
		httpResponseUtils.postJson(
				httpPostParams.getPostParams(PostMethod.get_android_version.name(), PostType.common.name(), 
						httpPostParams.get_android_version()), 
				VersionDAO.class,
				new PostCommentResponseListener() {
					@Override
					public void requestCompleted(Object response) throws JSONException {
						if(response == null) return;
						VersionDAO versionDAO = (VersionDAO) response;
						int versionCode = Utils.getVersionCode(HomeActivity.this);
						if(versionCode < versionDAO.getAndroid_version()){
							update(versionDAO.getDownload_url());
						}
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
				Log.i(TAG, "path : ");
				dialog.dismiss();
				// 下载新版本
						Intent intent = new Intent(HomeActivity.this, UpdateService.class);
						intent.putExtra("url", url);
						startService(intent);
			}
		});
	
	}
	
	
	@Override
	public boolean dispatchTouchEvent(MotionEvent event) {
		float x = event.getX();
		float y = event.getY();
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			lastY = y;  
			lastX = x;  
			break;
		case MotionEvent.ACTION_MOVE:
			float dy = Math.abs(y - lastY);
			float dx = Math.abs(x - lastX);
			Log.i(TAG, "---------dx="+dx+",dy="+dy);
			boolean down = y > lastY ? true : false;
			lastY = y;
			lastX = x;
			if(dx< 8 && dy > 8&& !isHide && !down){
				animation = AnimationUtils.loadAnimation(this, R.anim.push_out);
				ll_home_tab.startAnimation(animation);
				new Handler().postDelayed(new Runnable(){
		            public void run() {
		            	ll_home_tab.setVisibility(View.GONE);
		            } 
				}, 500);
			}else if(dx < 8 && dy > 8 && isHide && down){
				animation = AnimationUtils.loadAnimation(this, R.anim.push_in);
//				ll_home_tab.setVisibility(View.VISIBLE);
				ll_home_tab.startAnimation(animation);
				new Handler().postDelayed(new Runnable(){
		            public void run() {
//		            	ll_home_tab.setVisibility(View.GONE);
		            	ll_home_tab.setVisibility(View.VISIBLE);
		            } 
				}, 500);
			}else{
				break;
			}
			isHide = !isHide;  
			break;
		}
		return super.dispatchTouchEvent(event);
	}
	@Override
	protected void onResume() {
		super.onResume();
		judgeIsLogin();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		if(PushManager.getInstance().isPushTurnedOn(this.getApplicationContext())){
			PushManager.getInstance().stopService(this.getApplicationContext());
		}
		SystemTimeUtile.getInstance(0L).setFlag(false);
		if (receiver != null)
			unregisterReceiver(receiver);
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
			ft.commit();
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
				ft.commit();
			}
			currentTab = idx; // 更新目标tab为当前tab
		}
		private FragmentTransaction obtainFragmentTransaction(int index) {
			FragmentTransaction ft = activity.getSupportFragmentManager()
					.beginTransaction();
			// 设置切换动画
			// if(index > currentTab){
			// ft.setCustomAnimations(R.anim.slide_left_in, R.anim.slide_left_out);
			// }else{
			// ft.setCustomAnimations(R.anim.slide_right_in,
			// R.anim.slide_right_out);
			// }
			return ft;
		}

		public int getCurrentTab() {
			return currentTab;
		}

		public Fragment getCurrentFragment() {
			return fragments.get(currentTab);
		}
		@Override
		public void onClick(View v) {
			for (int i = 0; i < btns.length; i++) {
				if (btns[i] == v ) {
					if(i!=0 && !isLogin()){
						return;
					}
					if(i == 2){
						iv_ranking.setSelected(true);
						tv_ranking.setSelected(true);
					}else{
						iv_ranking.setSelected(false);
						tv_ranking.setSelected(false);
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
					ft.commit();
					
				}
			}
		}

	}
}

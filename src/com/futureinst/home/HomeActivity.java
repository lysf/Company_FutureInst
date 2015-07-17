package com.futureinst.home;



import org.json.JSONException;

import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.futureinst.R;
import com.futureinst.baseui.BaseActivity;
import com.futureinst.home.hold.HoldingFragment;
import com.futureinst.home.ranking.RankAndRecordFragment;
import com.futureinst.home.search.SearchFragment;
import com.futureinst.home.userinfo.UserInfoFragment;
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


public class HomeActivity extends BaseActivity {
//	private FragmentTransaction fragmentTransaction;
	private UserInformationDAO userInformationDAO;
	private BroadcastReceiver receiver;
	private int primaryTitle = 0;
	private int secondTitle = 0;
	private View view_bottom_1, view_bottom_2,view_bottom_3;
	private ImageView iv_home_type, iv_home_search, iv_home_cc,
			iv_ranking_status, iv_login;
	private LinearLayout ll_login, ll_unlogin;
	private TextView tv_ranking, tv_unlogin;
	private ImageView iv_home_type_2, iv_home_search_2, iv_home_cc_2,
			iv_ranking_status_2, iv_login_2;
	private ImageView iv_home_type_3, iv_home_search_3, iv_home_cc_3,
	iv_ranking_status_3,iv_login_3;
	private LinearLayout ll_login_2, ll_unlogin_2;
	private TextView tv_ranking_2, tv_unlogin_2;
	private LinearLayout ll_login_3, ll_unlogin_3;
	private TextView tv_ranking_3, tv_unlogin_3;
	private int currentPosition = 0;
	private String cid;
	private LinearLayout ll_ranking_1,ll_ranking_2,ll_ranking_3;
	private TextView tv_message_count_1,tv_message_count_2,tv_message_count_3;
	private FragmentTransaction fragmentTransaction;
	private HomeTypeFragment homeTypeFragment;
	private SearchFragment searchFragment ;
	private HoldingFragment holdingFragment;
	private UserInfoFragment userInfoFragment;
	@Override
	protected void localOnCreate(Bundle savedInstanceState) {
		//初始化推送
		PushManager.getInstance().initialize(this.getApplicationContext());
		initVeiw();
		initFragment();
		receiver = new BroadcastReceiver() {
			@Override
			public void onReceive(Context context, Intent intent) {
				if (intent.getAction().equals("titleType")) {
					primaryTitle = intent.getIntExtra("primaryTitle", 0);
					secondTitle = intent.getIntExtra("secondTitle", 0);
//					initFragment();
					homeTypeFragment.setPrimaryAndSecondTitle(primaryTitle, secondTitle);
					showBottomView(primaryTitle);
				}else if(intent.getAction().equals("newPushMessage")){
					getMessageCount();
				}
			}
		};
		IntentFilter filter = new IntentFilter();
		filter.addAction("titleType");
		filter.addAction("newPushMessage");
		registerReceiver(receiver, filter);
		get_android_version();
		
	}

	@Override
	protected void onResume() {
		super.onResume();
		judgeIsLogin();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		PushManager.getInstance().stopService(this.getApplicationContext());
		SystemTimeUtile.getInstance(0L).setFlag(false);
		if (receiver != null)
			unregisterReceiver(receiver);
	}

	private void initVeiw() {
		setContentView(R.layout.activity_home_layout);
		view_bottom_1 = findViewById(R.id.view_bottom_1);
		view_bottom_2 = findViewById(R.id.view_bottom_2);
		view_bottom_3 = findViewById(R.id.view_bottom_3);
		
		tv_message_count_1 = (TextView) findViewById(R.id.tv_message_count_1);
		tv_message_count_2 = (TextView) findViewById(R.id.tv_message_count_2);
		tv_message_count_3 = (TextView) findViewById(R.id.tv_message_count_3);
		
		iv_home_type = (ImageView) findViewById(R.id.iv_home_type);
		iv_home_type.setOnClickListener(clickListener);
		iv_home_search = (ImageView) findViewById(R.id.iv_home_search);
		iv_home_search.setOnClickListener(clickListener);
		iv_home_cc = (ImageView) findViewById(R.id.iv_home_cc);
		iv_home_cc.setOnClickListener(clickListener);
		iv_ranking_status = (ImageView) findViewById(R.id.iv_ranking_status);
		iv_login = (ImageView) findViewById(R.id.iv_login);
		iv_login.setOnClickListener(clickListener);
		ll_login = (LinearLayout) findViewById(R.id.ll_login);
		ll_unlogin = (LinearLayout) findViewById(R.id.ll_unLogin);
		tv_ranking = (TextView) findViewById(R.id.tv_ranking);
		ll_ranking_1 = (LinearLayout) findViewById(R.id.ll_ranking_1);
		ll_ranking_1.setOnClickListener(clickListener);
		tv_unlogin = (TextView) findViewById(R.id.tv_unLogin);
		tv_unlogin.setOnClickListener(clickListener);

		iv_home_type_2 = (ImageView) findViewById(R.id.iv_home_type_2);
		iv_home_type_2.setOnClickListener(clickListener);
		iv_home_search_2 = (ImageView) findViewById(R.id.iv_home_search_2);
		iv_home_search_2.setOnClickListener(clickListener);
		iv_home_cc_2 = (ImageView) findViewById(R.id.iv_home_cc_2);
		iv_home_cc_2.setOnClickListener(clickListener);
		iv_ranking_status_2 = (ImageView) findViewById(R.id.iv_ranking_status_2);
		iv_login_2 = (ImageView) findViewById(R.id.iv_login_2);
		iv_login_2.setOnClickListener(clickListener);
		ll_login_2 = (LinearLayout) findViewById(R.id.ll_login_2);
		ll_unlogin_2 = (LinearLayout) findViewById(R.id.ll_unLogin_2);
		tv_ranking_2 = (TextView) findViewById(R.id.tv_ranking_2);
		ll_ranking_2 = (LinearLayout) findViewById(R.id.ll_ranking_2);
		ll_ranking_2.setOnClickListener(clickListener);
		tv_unlogin_2 = (TextView) findViewById(R.id.tv_unLogin_2);
		tv_unlogin_2.setOnClickListener(clickListener);
		
		iv_home_type_3 = (ImageView) findViewById(R.id.iv_home_type_3);
		iv_home_type_3.setOnClickListener(clickListener);
		iv_home_search_3 = (ImageView) findViewById(R.id.iv_home_search_3);
		iv_home_search_3.setOnClickListener(clickListener);
		iv_home_cc_3 = (ImageView) findViewById(R.id.iv_home_cc_3);
		iv_home_cc_3.setOnClickListener(clickListener);
		iv_ranking_status_3 = (ImageView) findViewById(R.id.iv_ranking_status_3);
		tv_ranking_3 = (TextView) findViewById(R.id.tv_ranking_3);
		iv_login_3 = (ImageView) findViewById(R.id.iv_login_3);
		iv_login_3.setOnClickListener(clickListener);
		iv_login_3 = (ImageView) findViewById(R.id.iv_login_3);
		iv_login_3.setOnClickListener(clickListener);
		ll_login_3 = (LinearLayout) findViewById(R.id.ll_login_3);
		ll_unlogin_3 = (LinearLayout) findViewById(R.id.ll_unLogin_3);
		ll_ranking_3 = (LinearLayout) findViewById(R.id.ll_ranking_3);
		ll_ranking_3.setOnClickListener(clickListener);
		tv_unlogin_3 = (TextView) findViewById(R.id.tv_unLogin_3);
		tv_unlogin_3.setOnClickListener(clickListener);
		
		homeTypeFragment = new HomeTypeFragment();
		showBottomView(primaryTitle);
//		initFragment();
		
		iv_home_type.setSelected(true);
	}
	
	// 判断是否登录
	private void judgeIsLogin() {
		if (TextUtils.isEmpty(preferenceUtil.getUUid())) {
			ll_unlogin.setVisibility(View.VISIBLE);
			ll_login.setVisibility(View.INVISIBLE);
			ll_unlogin_2.setVisibility(View.VISIBLE);
			ll_unlogin_3.setVisibility(View.VISIBLE);
			ll_login_2.setVisibility(View.INVISIBLE);
			ll_login_3.setVisibility(View.INVISIBLE);
		} else {
			if(TextUtils.isEmpty(preferenceUtil.getCLIENTID())){
				cid = PushManager.getInstance().getClientid(this);
				preferenceUtil.setCLIENTID(cid);
			}else{
				cid = preferenceUtil.getCLIENTID();
			}
			ll_unlogin.setVisibility(View.INVISIBLE);
			ll_login.setVisibility(View.VISIBLE);
			ll_unlogin_2.setVisibility(View.INVISIBLE);
			ll_unlogin_3.setVisibility(View.INVISIBLE);
			ll_login_2.setVisibility(View.VISIBLE);
			ll_login_3.setVisibility(View.VISIBLE);
			getMessageCount();
			query_user_record();
			update_user_cid(cid);
		}
	}

	// 显示底部view
	public void showBottomView(int primaryTitle) {
		if (primaryTitle == 4 || primaryTitle == 6 || primaryTitle == 7
				|| primaryTitle == 8) {
			view_bottom_1.setVisibility(View.INVISIBLE);
			view_bottom_2.setVisibility(View.VISIBLE);
			view_bottom_3.setVisibility(View.INVISIBLE);
			tv_ranking.setTextColor(getResources().getColor(R.color.text_color_white));
		} else if(primaryTitle == 0){
			view_bottom_1.setVisibility(View.INVISIBLE);
			view_bottom_2.setVisibility(View.INVISIBLE);
			view_bottom_3.setVisibility(View.VISIBLE);
		}else {
			view_bottom_1.setVisibility(View.VISIBLE);
			view_bottom_2.setVisibility(View.INVISIBLE);
			view_bottom_3.setVisibility(View.INVISIBLE);
		}
	}

	private void initFragment() {
		fragmentTransaction = getSupportFragmentManager().beginTransaction();
		homeTypeFragment = new HomeTypeFragment();
		Bundle bundle = new Bundle();
		bundle.putInt("primaryTitle", primaryTitle);
		bundle.putInt("secondTitle", secondTitle);
		homeTypeFragment.setArguments(bundle);
		fragmentTransaction.replace(R.id.container, homeTypeFragment);
		fragmentTransaction.commitAllowingStateLoss();
	}

	OnClickListener clickListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			fragmentTransaction = getSupportFragmentManager()
					.beginTransaction();
			view_bottom_1.setVisibility(View.VISIBLE);
			view_bottom_2.setVisibility(View.INVISIBLE);
			view_bottom_3.setVisibility(View.INVISIBLE);
			iv_home_type.setSelected(false);
			iv_home_search.setSelected(false);
			iv_home_cc.setSelected(false);
			iv_login.setSelected(false);
//			setRankingSelect(userInformationDAO, false);
			iv_ranking_status.setSelected(false);
			iv_ranking_status_2.setSelected(false);
			switch (v.getId()) {
			case R.id.iv_home_type:// 主题分类
			case R.id.iv_home_type_2:
			case R.id.iv_home_type_3:
				if (currentPosition == 0)
					return;
				iv_home_type.setSelected(true);
				view_bottom_1.setVisibility(View.INVISIBLE);
				view_bottom_2.setVisibility(View.INVISIBLE);
				view_bottom_3.setVisibility(View.VISIBLE);
				homeTypeFragment = new HomeTypeFragment();
				Bundle bundle = new Bundle();
				bundle.putInt("primaryTitle", 0);
				bundle.putInt("secondTitle", 0);
				homeTypeFragment.setArguments(bundle);
				fragmentTransaction.replace(R.id.container, homeTypeFragment);
				fragmentTransaction.commitAllowingStateLoss();
				tv_ranking.setAlpha(0.3f);
				tv_ranking_2.setAlpha(0.3f);
				tv_ranking_3.setAlpha(0.3f);
				currentPosition = 0;
				break;
			case R.id.iv_home_search:// 搜索
			case R.id.iv_home_search_2:
			case R.id.iv_home_search_3:
				if (currentPosition == 1)
					return;
				iv_home_search.setSelected(true);
				searchFragment = new SearchFragment();
				fragmentTransaction.replace(R.id.container, searchFragment);
				fragmentTransaction.commitAllowingStateLoss();
				currentPosition = 1;
				break;
			case R.id.iv_home_cc:// 持仓
			case R.id.iv_home_cc_2:
			case R.id.iv_home_cc_3:
				if(TextUtils.isEmpty(preferenceUtil.getUUid())){
					startActivity(new Intent(HomeActivity.this, LoginActivity.class));
					return;
				}
				if (currentPosition == 2)
					return;
				iv_home_cc.setSelected(true);
				tv_ranking.setAlpha(0.3f);
				tv_ranking_2.setAlpha(0.3f);
				tv_ranking_3.setAlpha(0.3f);
				holdingFragment = new HoldingFragment();
				fragmentTransaction.replace(R.id.container,holdingFragment);
				fragmentTransaction.commitAllowingStateLoss();
				currentPosition = 2;
				break;
			case R.id.iv_login:// 用户
			case R.id.iv_login_2:
			case R.id.iv_login_3:
				
				if (currentPosition == 3)
					return;
				iv_login.setSelected(true);
				 userInfoFragment = new UserInfoFragment();
				fragmentTransaction.replace(R.id.container,new UserInfoFragment2());
				fragmentTransaction.commitAllowingStateLoss();
				tv_ranking.setAlpha(0.3f);
				tv_ranking_2.setAlpha(0.3f);
				tv_ranking_3.setAlpha(0.3f);
				currentPosition = 3;
				break;
			case R.id.ll_ranking_1:// 排名
			case R.id.ll_ranking_2:
			case R.id.ll_ranking_3:
				if (currentPosition == 4)
					return;
//				setRankingSelect(userInformationDAO, true);
				iv_ranking_status.setSelected(true);
				iv_ranking_status_2.setSelected(true);
				fragmentTransaction.replace(R.id.container,
						new RankAndRecordFragment());
				fragmentTransaction.commitAllowingStateLoss();
				tv_ranking.setAlpha(1f);
				tv_ranking_2.setAlpha(1f);
				tv_ranking_3.setAlpha(1f);
				currentPosition = 4;
				break;
			case R.id.tv_unLogin:// 登录
			case R.id.tv_unLogin_2:
			case R.id.tv_unLogin_3:
				Intent intent = new Intent(HomeActivity.this,
						LoginActivity.class);
				startActivity(intent);
				break;
			}
		}
	};
	//设置用户的排名
	private void setRanking(UserInformationDAO userInformationDAO){
		tv_ranking.setText(userInformationDAO.getRank()+"");
		tv_ranking_2.setText(userInformationDAO.getRank()+"");
		tv_ranking_3.setText(userInformationDAO.getRank()+"");
		tv_ranking.setAlpha(0.3f);
		tv_ranking_2.setAlpha(0.3f);
		tv_ranking_3.setAlpha(0.3f);
		if(userInformationDAO.getRank() > userInformationDAO.getLastRank()){
			iv_ranking_status.setImageDrawable(getResources().getDrawable(R.drawable.ranking_up));
			iv_ranking_status_2.setImageDrawable(getResources().getDrawable(R.drawable.ranking_up));
			iv_ranking_status_3.setImageDrawable(getResources().getDrawable(R.drawable.ranking_up));
		}else if(userInformationDAO.getRank() < userInformationDAO.getLastRank()){
			iv_ranking_status.setImageDrawable(getResources().getDrawable(R.drawable.ranking_down));
			iv_ranking_status_2.setImageDrawable(getResources().getDrawable(R.drawable.ranking_down));
			iv_ranking_status_3.setImageDrawable(getResources().getDrawable(R.drawable.ranking_down));
		}else{
			iv_ranking_status.setImageDrawable(getResources().getDrawable(R.drawable.ranking_balance));
			iv_ranking_status_2.setImageDrawable(getResources().getDrawable(R.drawable.ranking_balance));
			iv_ranking_status_3.setImageDrawable(getResources().getDrawable(R.drawable.ranking_balance_3));
		}
	}
//	private void setRankingSelect(UserInformationDAO userInformationDAO,boolean isSelect){
//		if(userInformationDAO == null){
//			return ;
//		}
//		if(userInformationDAO.getRank() > userInformationDAO.getLastRank()){
//			iv_ranking_status.setImageDrawable(getResources().getDrawable(R.drawable.ranking_up));
//			iv_ranking_status_2.setImageDrawable(getResources().getDrawable(R.drawable.ranking_up));
//			iv_ranking_status_3.setImageDrawable(getResources().getDrawable(R.drawable.ranking_up));
//		}else if(userInformationDAO.getRank() < userInformationDAO.getLastRank()){
//			iv_ranking_status.setImageDrawable(getResources().getDrawable(R.drawable.ranking_down));
//			iv_ranking_status_2.setImageDrawable(getResources().getDrawable(R.drawable.ranking_down));
//			iv_ranking_status_3.setImageDrawable(getResources().getDrawable(R.drawable.ranking_down));
//		}else{
//			if(isSelect){
//				iv_ranking_status.setImageDrawable(getResources().getDrawable(R.drawable.ranking_balance_un));
//				iv_ranking_status_2.setImageDrawable(getResources().getDrawable(R.drawable.ranking_balance_un));
//				iv_ranking_status_3.setImageDrawable(getResources().getDrawable(R.drawable.ranking_balance_un));
//			}else{
//				iv_ranking_status.setImageDrawable(getResources().getDrawable(R.drawable.rank_balance_1));
//				iv_ranking_status_2.setImageDrawable(getResources().getDrawable(R.drawable.rank_balance_1));
//				iv_ranking_status_3.setImageDrawable(getResources().getDrawable(R.drawable.rank_balance_1));
//			}
//		}
//	}
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
						userInformationDAO = userInformationInfo.getUser_record();
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
				tv_message_count_1.setText(count+"");
				tv_message_count_1.setVisibility(View.VISIBLE);
				tv_message_count_2.setText(count+"");
				tv_message_count_2.setVisibility(View.VISIBLE);
				tv_message_count_3.setText(count+"");
				tv_message_count_3.setVisibility(View.VISIBLE);
			}else{
				tv_message_count_1.setText("0");
				tv_message_count_1.setVisibility(View.INVISIBLE);
				tv_message_count_2.setText("0");
				tv_message_count_2.setVisibility(View.INVISIBLE);
				tv_message_count_3.setText("0");
				tv_message_count_3.setVisibility(View.INVISIBLE);
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
//				if (new File(Content.downloadPath).exists()) {
//					try {
						// sPreferences.edit().putLong("refernece",
						// 10L).commit();
						Intent intent = new Intent(HomeActivity.this, UpdateService.class);
						intent.putExtra("url", url);
						startService(intent);
						// downloadAPK(Contents.UPDATE_URL);
//					} catch (Exception e) {
//						e.printStackTrace();
//						Log.i(TAG, "下载异常：" + e.getMessage());
//					}

//				}
				// }
			}
		});
	
	}
}

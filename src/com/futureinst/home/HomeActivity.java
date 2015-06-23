package com.futureinst.home;

import org.json.JSONException;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.futureinst.R;
import com.futureinst.baseui.BaseActivity;
import com.futureinst.home.hold.HoldingFragment;
import com.futureinst.home.ranking.RankingFragment;
import com.futureinst.home.search.SearchFragment;
import com.futureinst.home.userinfo.UserInfoFragment;
import com.futureinst.login.LoginActivity;
import com.futureinst.model.usermodel.UserInfo;
import com.futureinst.model.usermodel.UserInformationDAO;
import com.futureinst.model.usermodel.UserInformationInfo;
import com.futureinst.net.PostCommentResponseListener;
import com.futureinst.net.PostMethod;
import com.futureinst.net.PostType;
import com.futureinst.push.PushMessageUtils;
import com.igexin.sdk.PushManager;


public class HomeActivity extends BaseActivity {
	private FragmentTransaction fragmentTransaction;
	private BroadcastReceiver receiver;
	private int primaryTitle = 0;
	private int secondTitle = 0;
	private View view_bottom_1, view_bottom_2;
	private ImageView iv_home_type, iv_home_search, iv_home_cc,
			iv_ranking_status, iv_login;
	private LinearLayout ll_login, ll_unlogin;
	private TextView tv_ranking, tv_unlogin;
	private ImageView iv_home_type_2, iv_home_search_2, iv_home_cc_2,
			iv_ranking_status_2, iv_login_2;
	private LinearLayout ll_login_2, ll_unlogin_2;
	private TextView tv_ranking_2, tv_unlogin_2;
	private int currentPosition = 0;
	private String cid;
	private LinearLayout ll_ranking_1,ll_ranking_2;
	private TextView tv_message_count_1,tv_message_count_2;
	
	@Override
	protected void localOnCreate(Bundle savedInstanceState) {
		//初始化推送
		PushManager.getInstance().initialize(this.getApplicationContext());
		initVeiw();
		receiver = new BroadcastReceiver() {
			@Override
			public void onReceive(Context context, Intent intent) {
				if (intent.getAction().equals("titleType")) {
					primaryTitle = intent.getIntExtra("primaryTitle", 0);
					secondTitle = intent.getIntExtra("secondTitle", 0);
					initFragment();
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
		if(TextUtils.isEmpty(preferenceUtil.getCLIENTID())){
			cid = PushManager.getInstance().getClientid(this);
			preferenceUtil.setCLIENTID(cid);
		}else{
			cid = preferenceUtil.getCLIENTID();
		}
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
		if (receiver != null)
			unregisterReceiver(receiver);
	}

	private void initVeiw() {
		setContentView(R.layout.activity_home_layout);
		view_bottom_1 = findViewById(R.id.view_bottom_1);
		view_bottom_2 = findViewById(R.id.view_bottom_2);
		
		tv_message_count_1 = (TextView) findViewById(R.id.tv_message_count_1);
		tv_message_count_2 = (TextView) findViewById(R.id.tv_message_count_2);
		
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
		
		showBottomView(primaryTitle);
		initFragment();
		
		iv_home_type.setSelected(true);
	}

	// 判断是否登录
	private void judgeIsLogin() {
		if (TextUtils.isEmpty(preferenceUtil.getUUid())) {
			ll_unlogin.setVisibility(View.VISIBLE);
			ll_login.setVisibility(View.INVISIBLE);
			ll_unlogin_2.setVisibility(View.VISIBLE);
			ll_login_2.setVisibility(View.INVISIBLE);
		} else {
			ll_unlogin.setVisibility(View.INVISIBLE);
			ll_login.setVisibility(View.VISIBLE);
			ll_unlogin_2.setVisibility(View.INVISIBLE);
			ll_login_2.setVisibility(View.VISIBLE);
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
			tv_ranking.setTextColor(getResources().getColor(R.color.text_color_white));
		} else {
			view_bottom_1.setVisibility(View.VISIBLE);
			view_bottom_2.setVisibility(View.INVISIBLE);
		}
	}

	private void initFragment() {
		fragmentTransaction = getSupportFragmentManager().beginTransaction();
		HomeTypeFragment homeTypeFragment = new HomeTypeFragment();
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
			iv_home_type.setSelected(false);
			iv_home_search.setSelected(false);
			iv_home_cc.setSelected(false);
			iv_login.setSelected(false);
			
			switch (v.getId()) {
			case R.id.iv_home_type:// 主题分类
			case R.id.iv_home_type_2:
				iv_home_type.setSelected(true);
				if (currentPosition == 0)
					return;
				HomeTypeFragment homeTypeFragment = new HomeTypeFragment();
				Bundle bundle = new Bundle();
				bundle.putInt("primaryTitle", 0);
				bundle.putInt("secondTitle", 0);
				homeTypeFragment.setArguments(bundle);
				fragmentTransaction.replace(R.id.container, homeTypeFragment);
				fragmentTransaction.commitAllowingStateLoss();
				tv_ranking.setAlpha(0.3f);
				tv_ranking_2.setAlpha(0.3f);
//				tv_ranking_2.setTextColor(getResources().getColor(R.color.text_color_e));
				currentPosition = 0;
				break;
			case R.id.iv_home_search:// 搜索
			case R.id.iv_home_search_2:
				iv_home_search.setSelected(true);
				if (currentPosition == 1)
					return;
				SearchFragment searchFragment = new SearchFragment();
				fragmentTransaction.replace(R.id.container, searchFragment);
				fragmentTransaction.commitAllowingStateLoss();
				currentPosition = 1;
				break;
			case R.id.iv_home_cc:// 持仓
			case R.id.iv_home_cc_2:
				if(TextUtils.isEmpty(preferenceUtil.getUUid())){
//					ToastUtils.showToast(HomeActivity.this, "您还未登录，请登录后查看！", 2000, Style.ALERT);
					startActivity(new Intent(HomeActivity.this, LoginActivity.class));
					return;
				}
				iv_home_cc.setSelected(true);
				
//				tv_ranking.setTextColor(getResources().getColor(R.color.text_color_e));
//				tv_ranking_2.setTextColor(getResources().getColor(R.color.text_color_e));
				if (currentPosition == 2)
					return;
				tv_ranking.setAlpha(0.3f);
				tv_ranking_2.setAlpha(0.3f);
				fragmentTransaction.replace(R.id.container,
						new HoldingFragment());
				fragmentTransaction.commitAllowingStateLoss();
				currentPosition = 2;
				break;
			case R.id.iv_login:// 用户
			case R.id.iv_login_2:
				iv_login.setSelected(true);
				if (currentPosition == 3)
					return;
				UserInfoFragment userInfoFragment = new UserInfoFragment();
//				userInfoFragment.setUserVisibleHint(true);
				fragmentTransaction.replace(R.id.container,userInfoFragment);
				fragmentTransaction.commitAllowingStateLoss();
//				tv_ranking.setTextColor(getResources().getColor(R.color.text_color_e));
//				tv_ranking_2.setTextColor(getResources().getColor(R.color.text_color_e));
				tv_ranking.setAlpha(0.3f);
				tv_ranking_2.setAlpha(0.3f);
				currentPosition = 3;
				break;
			case R.id.ll_ranking_1:// 排名
			case R.id.ll_ranking_2:
				if (currentPosition == 4)
					return;
				fragmentTransaction.replace(R.id.container,
						new RankingFragment());
				fragmentTransaction.commitAllowingStateLoss();
//				tv_ranking.setTextColor(getResources().getColor(R.color.text_color_white));
//				tv_ranking_2.setTextColor(getResources().getColor(R.color.text_color_white));
				tv_ranking.setAlpha(1f);
				tv_ranking_2.setAlpha(1f);
				currentPosition = 4;
				break;
			case R.id.tv_unLogin:// 登录
			case R.id.tv_unLogin_2:
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
		tv_ranking.setAlpha(0.3f);
		tv_ranking_2.setAlpha(0.3f);
		if(userInformationDAO.getRank() > userInformationDAO.getLastRank()){
			iv_ranking_status.setImageDrawable(getResources().getDrawable(R.drawable.ranking_up));
			iv_ranking_status_2.setImageDrawable(getResources().getDrawable(R.drawable.ranking_up));
		}else if(userInformationDAO.getRank() < userInformationDAO.getLastRank()){
			iv_ranking_status.setImageDrawable(getResources().getDrawable(R.drawable.ranking_down));
			iv_ranking_status_2.setImageDrawable(getResources().getDrawable(R.drawable.ranking_down));
		}else{
			iv_ranking_status.setImageDrawable(getResources().getDrawable(R.drawable.ranking_balance_un));
			iv_ranking_status_2.setImageDrawable(getResources().getDrawable(R.drawable.ranking_balance_un));
		}
		
//		iv_ranking_status.setImageDrawable(drawable)
//		iv_ranking_status_2.setImageDrawable()
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
				tv_message_count_1.setText(count+"");
				tv_message_count_1.setVisibility(View.VISIBLE);
				tv_message_count_2.setText(count+"");
				tv_message_count_2.setVisibility(View.VISIBLE);
			}else{
				tv_message_count_1.setText("0");
				tv_message_count_1.setVisibility(View.INVISIBLE);
				tv_message_count_2.setText("0");
				tv_message_count_2.setVisibility(View.INVISIBLE);
			}
		}
}

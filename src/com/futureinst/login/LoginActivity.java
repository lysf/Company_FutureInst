package com.futureinst.login;

import java.util.HashMap;

import org.json.JSONException;

import com.futureinst.R;
import com.futureinst.baseui.BaseActivity;
import com.futureinst.global.Content;
import com.futureinst.home.HomeActivity;
import com.futureinst.model.usermodel.UserInfo;
import com.futureinst.model.usermodel.UserInformationInfo;
import com.futureinst.net.HttpResponseUtils;
import com.futureinst.net.PostCommentResponseListener;
import com.futureinst.net.PostMethod;
import com.futureinst.net.PostType;
import com.futureinst.utils.MyToast;
import com.igexin.sdk.PushManager;
import com.mob.tools.utils.UIHandler;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.os.Handler.Callback;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.sina.weibo.SinaWeibo;
import cn.sharesdk.wechat.friends.Wechat;

public class LoginActivity extends BaseActivity implements Callback, PlatformActionListener {
	private static final int MSG_USERID_FOUND = 1;
	private static final int MSG_AUTH_CANCEL = 3;
	private static final int MSG_AUTH_ERROR = 4;
	private static final int MSG_AUTH_COMPLETE = 5;
	private static final int MSG_AUTH_LOGIN = 6;
	private EditText et_phone,et_password;
	private boolean loginTag;
	private TextView tv_wechat,tv_sina;
	private String openId;
	private String thirdName;//第三方昵称
	private String gender;//性别
	private String headImage = "";
	private String type;
	@Override
	protected void localOnCreate(Bundle savedInstanceState) {
		ShareSDK.initSDK(this);
		initView();
	}
	@Override
	protected void onRightClick(View view) {
		super.onRightClick(view);
		//注册
		if(Content.disable_app_sign_in){
		startActivity(new Intent(LoginActivity.this, RegistActiivty_1.class));
		}else{
			MyToast.showToast(LoginActivity.this, getResources().getString(R.string.app_regist_tip), 0);
		}
	}
	private void initView() {
		loginTag = getIntent().getBooleanExtra("login", false);
		setContentView(R.layout.activity_login);
		setTitle(getResources().getString(R.string.login_login));
		setLeft(getResources().getString(R.string.login_cancel));
		setRight(R.string.login_regist);
//		setTitleBackGround(getResources().getColor(R.color.login_title_layout_back));
		et_phone = (EditText) findViewById(R.id.et_phoneNumber);
		et_password = (EditText) findViewById(R.id.et_password);
		findViewById(R.id.btn_login).setOnClickListener(clickListener);
		findViewById(R.id.tv_forgetPwd).setOnClickListener(clickListener);
		
		tv_wechat = (TextView) findViewById(R.id.tv_wechat);
		tv_sina = (TextView) findViewById(R.id.tv_sina);
		tv_wechat.setOnClickListener(clickListener);
		tv_sina.setOnClickListener(clickListener);
	}
	
	OnClickListener clickListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.btn_login://登录
				String phoneNummber = et_phone.getText().toString().trim();
				String pwd = et_password.getText().toString().trim();
				doLogin(phoneNummber,pwd);
				break;
			case R.id.tv_forgetPwd://忘记密码
				startActivity(new Intent(LoginActivity.this, ForgetPasswordActivity.class));
				break;
			case R.id.tv_wechat://微信
//				authorize(new Wechat(LoginActivity.this));
				break;
			case R.id.tv_sina://新浪
//				authorize(new SinaWeibo(LoginActivity.this));
				break;
			}
		}

	};
	//登录
	private void doLogin(String phoneNummber, String pwd) {
		if(!checkLoginData(phoneNummber, pwd)){
			return;
		}
		Content.isPull = true;
		progressDialog.progressDialog();
		HttpResponseUtils.getInstace(this).postJson(httpPostParams.getPostParams(
				PostMethod.sign_in_by_phone.name(), 
				PostType.user.name(), httpPostParams.loginJson(phoneNummber, pwd)), 
				UserInfo.class, new PostCommentResponseListener() {
					@Override
					public void requestCompleted(Object response) throws JSONException {
						//登录成功
						progressDialog.cancleProgress();
						Content.isPull = false;
						if(response == null) return;
						UserInfo userInfo = (UserInfo) response;
						SaveUserInfo.saveUserInfo(getApplicationContext(), userInfo.getUser());
						if(loginTag){
							PushManager.getInstance().initialize(getApplicationContext());
//							String cid = PushManager.getInstance().getClientid(LoginActivity.this);
//							Log.i("", "===============>>"+cid+"-------"
//							+PushManager.getInstance().isPushTurnedOn(LoginActivity.this));
//							preferenceUtil.setCLIENTID(cid);
//							update_user_cid(cid);
						}
						query_user_record();
					}
				});
	}
	// 获取个人信息
		private void query_user_record() {
			progressDialog.progressDialog();
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
							progressDialog.cancleProgress();
							if (response == null)
								return;
							UserInformationInfo userInformationInfo = (UserInformationInfo) response;
							preferenceUtil.setAssure(userInformationInfo.getUser_record().getAssure());
							preferenceUtil.setAsset(userInformationInfo.getUser_record().getAsset());
							if(judgeIsFirstLogin())
								return;
							
							if(!loginTag){
								Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
								intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
								startActivity(intent);
							}
							finish();
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
	//检测登录数据
	private boolean checkLoginData(String phoneNummber, String pwd){
		if(TextUtils.isEmpty(phoneNummber)){
			MyToast.showToast(this, getResources().getString(R.string.empty_phone), 0);
			return false;
		}
		if(TextUtils.isEmpty(pwd)){
			MyToast.showToast(this, getResources().getString(R.string.empty_phone), 0);
			return false;
		}
//		if(!Utils.checkMobilePhoneNo(phoneNummber)){
//			MyToast.showToast(this, getResources().getString(R.string.empty_phone), 0);
//			return false;
//		}
		return true;
	}
	//判断用户是否是第一次登陆
	private boolean judgeIsFirstLogin(){
		if(TextUtils.isEmpty(preferenceUtil.getName())){
			Intent intent = new Intent(this,RegistActivity_2.class);
			intent.putExtra("loginTag", loginTag);
			startActivity(intent);
			finish();
			return true;
		}
		return false;
	}
	// 授权
		private void authorize(Platform plat) {
			if (plat == null) {
				return;
			}
			if (plat.isValid()) {
				openId = plat.getDb().getUserId();
				Log.i("openid", "---------------openId------>>"+openId);
				if (!TextUtils.isEmpty(openId)) {
					if (plat.getName().equals(SinaWeibo.NAME)) {// 新浪微博
						type = "weibo"+openId;
					} else if (plat.getName().equals(Wechat.NAME)) {// 微信
						type = "weixin"+openId;
					}
					thirdName = plat.getDb().getUserName();
					gender = (String) plat.getDb().get("gender");
					if(gender.equals("m")){
						gender = "1";
					}else {
						gender = "2";
					}
					UIHandler.sendEmptyMessage(MSG_USERID_FOUND, this);
				}
				plat.removeAccount();
				return;
			}
			plat.setPlatformActionListener(this);
//			plat.SSOSetting(true);
//			plat.showUser(null);
			
			plat.authorize();
		}
		@Override
		public boolean handleMessage(Message msg) {
			switch (msg.what) {
			case MSG_USERID_FOUND: {
				query_user_with_uuid(type);
				Toast.makeText(this, R.string.userid_found, Toast.LENGTH_SHORT).show();
			}
				break;
			case MSG_AUTH_CANCEL: {
				Toast.makeText(this, R.string.auth_cancel, Toast.LENGTH_SHORT).show();
			}
				break;
			case MSG_AUTH_ERROR: {
				Toast.makeText(this, R.string.auth_error, Toast.LENGTH_SHORT).show();
			}
				break;
			case MSG_AUTH_COMPLETE: {
			//第三方登录或注册
				Toast.makeText(this, R.string.auth_complete, Toast.LENGTH_SHORT).show();
				query_user_with_uuid(type);
			}
				break;
			case MSG_AUTH_LOGIN:
				thirdLogin(type, thirdName, gender,headImage);
				break;
			}
			return false;
		}

		@Override
		public void onCancel(Platform arg0, int action) {
			if (action == Platform.ACTION_USER_INFOR) {
				arg0.removeAccount();
				UIHandler.sendEmptyMessage(MSG_AUTH_CANCEL, this);
			}
		}


		@Override
		public void onComplete(Platform arg0, int action, HashMap<String, Object> arg2) {
			Log.i("third", "---------------->" + arg0.getDb().getUserGender());
				openId = arg0.getDb().getUserId();
				if (arg0.getName().equals(SinaWeibo.NAME)) {// 新浪微博
					type = "weibo"+openId;
				} else if (arg0.getName().equals(Wechat.NAME)) {// 微信
					type = "weixin"+openId;
				}
				thirdName = arg0.getDb().getUserName();
				gender = arg0.getDb().getUserGender();
				headImage = arg0.getDb().getUserIcon();
				if(gender.equals("m")){
					gender = "1";
				}else{
					gender = "2";
				}
				
				UIHandler.sendEmptyMessage(MSG_AUTH_COMPLETE, this);
		}

		@Override
		public void onError(Platform arg0, int action, Throwable t) {
			arg0.removeAccount();
			if (action == Platform.ACTION_USER_INFOR) {
				UIHandler.sendEmptyMessage(MSG_AUTH_ERROR, this);
			}
			t.printStackTrace();
		}
	//第三方注册
	private void thirdLogin(String uuid,String name,String gender,String head_image){	
		progressDialog.progressDialog();
		Content.isPull = true;
		httpResponseUtils.postJson(
				httpPostParams.getPostParams(PostMethod.add_user_with_uuid.name(), PostType.user.name(), 
						httpPostParams.add_user_with_uuid(uuid, name, gender, head_image)), 
				UserInfo.class,
				new PostCommentResponseListener() {
					@Override
					public void requestCompleted(Object response) throws JSONException {
						//登录成功
						progressDialog.cancleProgress();
						Content.isPull = false;
						if(response == null) return;
						UserInfo userInfo = (UserInfo) response;
						SaveUserInfo.saveUserInfo(getApplicationContext(), userInfo.getUser());
						if(!loginTag){
							Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
							intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
							startActivity(intent);
						}
						finish();
					}
				});
	}
	//判断是否已经注册
	private void query_user_with_uuid(String uuid){	
		progressDialog.progressDialog();
		Content.isPull = true;
		httpResponseUtils.postJson(
				httpPostParams.getPostParams(PostMethod.query_user_with_uuid.name(), PostType.user.name(), 
						httpPostParams.query_user_with_uuid(uuid)), 
				UserInfo.class,
				new PostCommentResponseListener() {
					@Override
					public void requestCompleted(Object response) throws JSONException {
						//登录成功
						progressDialog.cancleProgress();
						Content.isPull = false;
						if(response == null){ 
							UIHandler.sendEmptyMessage(MSG_AUTH_LOGIN, LoginActivity.this );
							return;
						}
						UserInfo userInfo = (UserInfo) response;
						SaveUserInfo.saveUserInfo(getApplicationContext(), userInfo.getUser());
						if(!loginTag){
							Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
							intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
							startActivity(intent);
						}
						finish();
					}
				});
	}
		
	@Override
	protected void onDestroy() {
		ShareSDK.stopSDK(this);
		super.onDestroy();
	}
}

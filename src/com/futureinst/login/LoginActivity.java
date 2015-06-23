package com.futureinst.login;

import org.json.JSONException;

import com.futureinst.R;
import com.futureinst.baseui.BaseActivity;
import com.futureinst.home.HomeActivity;
import com.futureinst.model.usermodel.UserInfo;
import com.futureinst.net.HttpResponseUtils;
import com.futureinst.net.PostCommentResponseListener;
import com.futureinst.net.PostMethod;
import com.futureinst.net.PostType;
import com.futureinst.utils.ToastUtils;
import com.futureinst.utils.Utils;

import de.keyboardsurfer.android.widget.crouton.Configuration;
import de.keyboardsurfer.android.widget.crouton.Style;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;

public class LoginActivity extends BaseActivity {
	private EditText et_phone,et_password;
	private boolean loginTag;
	@Override
	protected void localOnCreate(Bundle savedInstanceState) {
		initView();
	}
	@Override
	protected void onRightClick(View view) {
		super.onRightClick(view);
		//注册
		startActivity(new Intent(LoginActivity.this, RegistActiivty_1.class));
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
			}
		}

	};
	//登录
	private void doLogin(String phoneNummber, String pwd) {
		if(!checkLoginData(phoneNummber, pwd)){
			return;
		}
		progressDialog.progressDialog();
		HttpResponseUtils.getInstace(this).postJson(httpPostParams.getPostParams(
				PostMethod.sign_in_by_phone.name(), 
				PostType.user.name(), httpPostParams.loginJson(phoneNummber, pwd)), 
				UserInfo.class, new PostCommentResponseListener() {
					@Override
					public void requestCompleted(Object response) throws JSONException {
						//登录成功
						progressDialog.cancleProgress();
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
	//检测登录数据
	private boolean checkLoginData(String phoneNummber, String pwd){
		if(TextUtils.isEmpty(phoneNummber)){
			ToastUtils.showToast(this, getResources().getString(R.string.empty_phone), Configuration.DURATION_SHORT, Style.ALERT);
			return false;
		}
		if(TextUtils.isEmpty(pwd)){
			ToastUtils.showToast(this, getResources().getString(R.string.empty_pwd), Configuration.DURATION_SHORT, Style.ALERT);
			return false;
		}
		if(!Utils.checkMobilePhoneNo(phoneNummber)){
			ToastUtils.showToast(this, getResources().getString(R.string.wrong_phone), Configuration.DURATION_SHORT, Style.ALERT);
			return false;
		}
		return true;
	}
}

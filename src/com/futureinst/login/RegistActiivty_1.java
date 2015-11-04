package com.futureinst.login;

import org.json.JSONException;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.futureinst.R;
import com.futureinst.baseui.BaseActivity;
import com.futureinst.global.Content;
import com.futureinst.model.basemodel.BaseModel;
import com.futureinst.model.usermodel.UserInfo;
import com.futureinst.net.PostCommentResponseListener;
import com.futureinst.net.PostMethod;
import com.futureinst.net.PostType;
import com.futureinst.utils.MyToast;
import com.futureinst.utils.Utils;

public class RegistActiivty_1 extends BaseActivity {
	private EditText et_phoneNumber,et_authCode,et_password,et_submit_password;
	private Button btn_authCode;
	@Override
	protected void localOnCreate(Bundle savedInstanceState) {
		setContentView(R.layout.activity_regist_1);
		initView();
	}
	@Override
	protected void onRightClick(View view) {
		super.onRightClick(view);
		String phoneNumber = et_phoneNumber.getText().toString().trim();
		String authCode = et_authCode.getText().toString().trim();
		String password = et_password.getText().toString().trim();
		String submit_pwd = et_submit_password.getText().toString().trim();
//		startActivity(new Intent(RegistActiivty_1.this, RegistActivity_2.class));
		regist(phoneNumber, authCode, password,submit_pwd);
		
	}
	private void initView() {
		// TODO Auto-generated method stub
		setTitle(getResources().getString(R.string.login_regist));
//		setTitleBackGround(getResources().getColor(R.color.login_title_layout_back));
		setRight(R.string.next);
		getLeftImageView().setImageDrawable(getResources().getDrawable(R.drawable.back));
		et_phoneNumber = (EditText) findViewById(R.id.et_phoneNumber);
		et_authCode = (EditText) findViewById(R.id.et_authCode);
		et_password = (EditText) findViewById(R.id.et_password);
		et_submit_password = (EditText) findViewById(R.id.et_submit_password);
		btn_authCode = (Button) findViewById(R.id.btn_auth);
		btn_authCode.setOnClickListener(clickListener);
	}
	OnClickListener clickListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.btn_auth://验证码
				if(isOperating)
					return;
				String mobile = et_phoneNumber.getText().toString().trim();
				getAuthCode(mobile);
				break;
			}
		}
	};
	//获取验证码
	protected void getAuthCode(String mobile) {
		if(!checkData(mobile, "123456", "123456","123456")){
			return;
		}
		progressDialog.progressDialog();
		Content.isPull = true;
		httpResponseUtils.postJson(httpPostParams.getPostParams(
				PostMethod.send_smscode.name(), PostType.user.name(), httpPostParams.getAuthCode(mobile)), 
				BaseModel.class, 
				new PostCommentResponseListener() {
					@Override
					public void requestCompleted(Object response) throws JSONException {
						//获取验证码
						progressDialog.cancleProgress();
						Content.isPull = false;
						if(response == null) return;
						setDelay();
						
					}
				});
		
	}
	//注册
	private void regist(String mobile, String authCode, String pwd,String submit_pwd){
		if(!checkData(mobile, authCode, pwd,submit_pwd)){
			return;
		}
		Content.isPull = true;
		progressDialog.progressDialog();
		httpResponseUtils.postJson(httpPostParams.getPostParams(
				PostMethod.add_user_by_phone.name(), PostType.user.name(), httpPostParams.regist(mobile, authCode, pwd)), 
				UserInfo.class, 
				new PostCommentResponseListener() {
					@Override
					public void requestCompleted(Object response) throws JSONException {
						progressDialog.cancleProgress();
						Content.isPull = false;
						if(response == null) return;
						UserInfo userInfo = (UserInfo) response;
						SaveUserInfo.saveUserInfo(getApplicationContext(), userInfo.getUser());
						startActivity(new Intent(RegistActiivty_1.this, RegistActivity_2.class));
						finish();
					}
				});
		
	
	}
	//检测输入数据
		private boolean checkData(String mobile, String authCode, String pwd,String submit_pwd){
			if(TextUtils.isEmpty(mobile)){
				MyToast.getInstance().showToast(this, getResources().getString(R.string.empty_phone), 0);
				return false;
			}
			if(!Utils.checkMobilePhoneNo(mobile)){
				MyToast.getInstance().showToast(this, getResources().getString(R.string.wrong_phone), 0);
				return false;
			}
			if(TextUtils.isEmpty(pwd)){
				MyToast.getInstance().showToast(this, getResources().getString(R.string.empty_pwd), 0);
				return false;
			}
			if(TextUtils.isEmpty(authCode)){
				MyToast.getInstance().showToast(this, getResources().getString(R.string.empty_authCode), 0);
				return false;
			}
			if(TextUtils.isEmpty(submit_pwd)){
				MyToast.getInstance().showToast(this, "请输入确认密码", 0);
				return false;
			}
			if(!pwd.equals(submit_pwd)){
				MyToast.getInstance().showToast(this, "两次密码不一致", 0);
				return false;
			}
			return true;
		}
	private Thread thread;
	private  void setDelay() {
		isOperating = true;
		thread = new Thread(new Runnable() {
			@Override
			public void run() {
				for (int i = 119; i >= 0; i--) {
//					if(!isOperating){
//						i = 0;
//					}
					Message message = Message.obtain();
					message.what = 11;
					message.arg1 = i;
					handler.sendMessage(message);
					if(i==0){
						isOperating = false;
					}
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}

			}
		});
		thread.start();
	}
	boolean isOperating;
	@SuppressLint("HandlerLeak")
	Handler handler = new Handler(){
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 11:
				if(msg.arg1 == 0){
					btn_authCode.setText("获取验证码");
					isOperating = false;
					break;
				}
				String text = msg.arg1+"秒后重新发送";
				btn_authCode.setText(text);
				break;
			default:
				break;
			}
		};
	};
}

package com.futureinst.login;

import org.json.JSONException;

import android.annotation.SuppressLint;
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
import com.futureinst.model.basemodel.BaseModel;
import com.futureinst.model.global.Content;
import com.futureinst.net.PostCommentResponseListener;
import com.futureinst.net.PostMethod;
import com.futureinst.net.PostType;
import com.futureinst.utils.MyToast;
import com.futureinst.utils.Utils;

public class ForgetPasswordActivity extends BaseActivity {
	private EditText et_mobile, et_authCode, et_newPassword;
	private Button btn_authCode;

	@Override
	protected void localOnCreate(Bundle savedInstanceState) {
		setContentView(R.layout.actiivty_forgetpassword);
		initView();
	}

	private void initView() {
		setTitle(getResources().getString(R.string.reset_pwd));
		setLeft(getResources().getString(R.string.cancel));
//		setTitleBackGround(getResources().getColor(R.color.login_title_layout_back));
		et_mobile = (EditText) findViewById(R.id.et_phoneNumber);
		et_authCode = (EditText) findViewById(R.id.et_authCode);
		et_newPassword = (EditText) findViewById(R.id.et_new_password);
		btn_authCode = (Button) findViewById(R.id.btn_auth);
		btn_authCode.setOnClickListener(clickListener);
		findViewById(R.id.btn_submit).setOnClickListener(clickListener);
		
	}

	OnClickListener clickListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.btn_auth:// 验证码
				if (isOperating)
					return;
				String phoneNumber = et_mobile.getText().toString().trim();
				getAuthCode(phoneNumber);
				break;
			case R.id.btn_submit:// 提交
				String mobile = et_mobile.getText().toString().trim();
				String authCode = et_authCode.getText().toString().trim();
				String new_pwd = et_newPassword.getText().toString().trim();
				resetPassword(mobile,authCode,new_pwd);
				break;
			}
		}
	};

	// 获取验证码
	protected void getAuthCode(String mobile) {
		if(!checkData(mobile, "123456", "123456")){
			return;
		}
		Content.isPull = true;
		progressDialog.progressDialog();
		httpResponseUtils.postJson(httpPostParams.getPostParams(
				PostMethod.send_smscode.name(), PostType.user.name(),
				httpPostParams.getAuthCode(mobile)), BaseModel.class,
				new PostCommentResponseListener() {
					@Override
					public void requestCompleted(Object response)
							throws JSONException {
						//验证码发送成功
						Content.isPull = false;
						progressDialog.cancleProgress();
						setDelay();
					}
				});
	}
	//重置密码
	protected void resetPassword(String mobile, String authCode, String new_pwd) {
		if(!checkData(mobile, authCode, new_pwd)){
			return;
		}
		progressDialog.progressDialog();
		Content.isPull = true;
		httpResponseUtils.postJson(httpPostParams.getPostParams(
				PostMethod.update_pwd.name(), PostType.user.name(),
				httpPostParams.resetPassword(mobile, new_pwd, authCode)), BaseModel.class,
				new PostCommentResponseListener() {
					@Override
					public void requestCompleted(Object response)
							throws JSONException {
						//密码重置
						Content.isPull = false;
						progressDialog.cancleProgress();
						if(response == null) return;
							
						
//						ToastUtils.showToast(ForgetPasswordActivity.this, "密码修改成功！", 3000, Style.CONFIRM);
						showToast("密码修改成功！");
//						toastShow("密码修改成功！", Style.CONFIRM);
						finish();
					}
				});
	}
	//检测输入数据
	private boolean checkData(String mobile, String authCode, String new_pwd){
		if(TextUtils.isEmpty(mobile)){
			MyToast.showToast(this, getResources().getString(R.string.empty_phone), 0);
			return false;
		}
		if(!Utils.checkMobilePhoneNo(mobile)){
			MyToast.showToast(this, getResources().getString(R.string.wrong_phone), 0);
			return false;
		}
		if(TextUtils.isEmpty(new_pwd)){
			MyToast.showToast(this, getResources().getString(R.string.empty_new_pwd), 0);
			return false;
		}
		if(TextUtils.isEmpty(authCode)){
			MyToast.showToast(this, getResources().getString(R.string.empty_authCode), 0);
			return false;
		}
		
		return true;
	}
	//延时
	private void setDelay() {
		isOperating = true;
		new Thread(new Runnable() {
			@Override
			public void run() {
				for (int i = 119; i >= 0; i--) {
					Message message = Message.obtain();
					message.what = 11;
					message.arg1 = i;
					handler.sendMessage(message);
					if (i == 0) {
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
		}).start();
	}

	boolean isOperating;
	@SuppressLint("HandlerLeak")
	Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 11:
				if (msg.arg1 == 0) {
					btn_authCode.setText("获取验证码");
					isOperating = false;
					break;
				}
				String text = msg.arg1 + "秒后重新发送";
				btn_authCode.setText(text);
				break;
			default:
				break;
			}
		};
	};
}

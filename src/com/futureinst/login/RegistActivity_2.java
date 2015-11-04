package com.futureinst.login;

import java.io.UnsupportedEncodingException;

import org.json.JSONException;

import android.content.Intent;
import android.content.res.Resources.NotFoundException;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;

import com.futureinst.R;
import com.futureinst.baseui.BaseActivity;
import com.futureinst.global.Content;
import com.futureinst.home.HomeActivity;
import com.futureinst.model.usermodel.UserInfo;
import com.futureinst.net.PostCommentResponseListener;
import com.futureinst.net.PostMethod;
import com.futureinst.net.PostType;
import com.futureinst.utils.ChooseSexUtil;
import com.futureinst.utils.MyToast;
import com.futureinst.utils.TimePickUtil;

public class RegistActivity_2 extends BaseActivity {
	private EditText et_userName;
	private TextView tv_sex,tv_birthday;
	private boolean loginTag;
	@Override
	protected void localOnCreate(Bundle savedInstanceState) {
		setContentView(R.layout.activity_regist_2);
		initView();
	}
	@Override
	protected void onRightClick(View view) {//下一步
		super.onRightClick(view);
		String userName = et_userName.getText().toString().trim();
		String gender = (String) tv_sex.getTag();
		String birthday = tv_birthday.getText().toString().trim();
//		startActivity(new Intent(RegistActivity_2.this, RegistActivity_3.class));
		update_user(userName, gender, birthday);
		
	}
	private void initView() {
		loginTag = getIntent().getBooleanExtra("loginTag", false);
		setTitle(getResources().getString(R.string.login_regist));
//		getLeftImageView().setImageDrawable(getResources().getDrawable(R.drawable.back));
//		setTitleBackGround(getResources().getColor(R.color.login_title_layout_back));
		setRight(R.string.next);
		et_userName = (EditText) findViewById(R.id.et_userName);
		tv_sex = (TextView) findViewById(R.id.tv_sex);
		tv_birthday = (TextView) findViewById(R.id.tv_birthday);
		tv_sex.setOnClickListener(clickListener);
		tv_birthday.setOnClickListener(clickListener);
		
		et_userName.addTextChangedListener(new TextWatcher() {
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				try {
					if(s.toString().getBytes("GB2312").length > 14){
						MyToast.getInstance().showToast(RegistActivity_2.this, getResources().getString(R.string.regist_userName_tip), 0);
						et_userName.setText("");
						return;
					}
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				} catch (NotFoundException e) {
					e.printStackTrace();
				}
			}
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {}
			@Override
			public void afterTextChanged(Editable s) {}
		});
	}
	OnClickListener clickListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.tv_sex://性别
				ChooseSexUtil.chooseSex(RegistActivity_2.this, tv_sex);
				break;
			case R.id.tv_birthday://生日
				TimePickUtil.timePick(RegistActivity_2.this,tv_birthday);
				break;
			}
		}
	};
	private void update_user(String userName,String gender,String birthday){
		if(!judgeData(userName, gender, birthday)){
			return;
		}
		Content.isPull = true;
		progressDialog.progressDialog();
		httpResponseUtils.postJson(httpPostParams.getPostParams(PostMethod.update_user.name(), PostType.user.name(), 
				httpPostParams.update_user(preferenceUtil.getUUid(), preferenceUtil.getID()+"", userName, gender, birthday, "", "")), 
				UserInfo.class,
				new PostCommentResponseListener() {
					@Override
					public void requestCompleted(Object response) throws JSONException {
						progressDialog.cancleProgress();
						Content.isPull = false;
						if(response == null) return;
						Intent intent = new Intent(RegistActivity_2.this, RegistActivity_3.class);
						intent.putExtra("loginTag", loginTag);
						startActivity(intent);
						
//						if(!loginTag){
//							Intent intent = new Intent(RegistActivity_2.this, HomeActivity.class);
//							intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//							startActivity(intent);
//						}
						finish();
						
					}
				});
	}
	private boolean judgeData(String userName,String gender,String birthday){
		boolean flag = true;
		if(TextUtils.isEmpty(userName)){
			MyToast.getInstance().showToast(this, "请输入昵称！", 0);
			flag = false;
		}
		if(TextUtils.isEmpty(gender)){
			MyToast.getInstance().showToast(this, "请选择性别！", 0);
			flag = false;
		}
		if(TextUtils.isEmpty(birthday)){
			MyToast.getInstance().showToast(this, "请选择生日！", 0);
			flag = false;
		}
		return flag;
	}
}

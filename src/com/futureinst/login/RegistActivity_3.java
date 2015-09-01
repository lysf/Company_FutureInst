package com.futureinst.login;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;

import com.futureinst.R;
import com.futureinst.baseui.BaseActivity;
import com.futureinst.global.Content;
import com.futureinst.home.HomeActivity;
import com.futureinst.model.basemodel.RegistsKeywordDAO;
import com.futureinst.model.usermodel.UserInfo;
import com.futureinst.net.PostCommentResponseListener;
import com.futureinst.net.PostMethod;
import com.futureinst.net.PostType;
import com.futureinst.utils.ActivityManagerUtil;
import com.futureinst.utils.DialogShow;
import com.futureinst.utils.MyToast;
import com.igexin.sdk.PushManager;

public class RegistActivity_3 extends BaseActivity {
	private GridView gridView;
	private KeywordGridViewAdapter adapter;
	private String keywords[];
	private List<RegistsKeywordDAO > list;
	private boolean loginTag;
	@Override
	protected void localOnCreate(Bundle savedInstanceState) {
		initView();
		initData();
	}
	@Override
	protected void onRightClick(View view) {//完成
		super.onRightClick(view);
		update_user(adapter.getResult());
	}
	private void initView() {
		loginTag = getIntent().getBooleanExtra("loginTag", false);
		setTitle(R.string.login_regist);
		getLeftImageView().setImageDrawable(getResources().getDrawable(R.drawable.back));
//		setTitleBackGround(getResources().getColor(R.color.login_title_layout_back));
		setRight(R.string.complete);
		setContentView(R.layout.activity_regist_3);
		gridView = (GridView) findViewById(R.id.gv_keyword);
		adapter = new KeywordGridViewAdapter(this);
		gridView.setAdapter(adapter);
		keywords = getResources().getStringArray(R.array.regist_keyword);
		list = new ArrayList<RegistsKeywordDAO>();
	}
	private void initData(){
		RegistsKeywordDAO registsKeyword1 = new RegistsKeywordDAO(keywords[0], R.color.regist_keyword_1, R.color.text_color_white, false);
		list.add(registsKeyword1);
		RegistsKeywordDAO registsKeyword2 = new RegistsKeywordDAO(keywords[1], R.color.regist_keyword_2, R.color.text_color_white, false);
		list.add(registsKeyword2);
		RegistsKeywordDAO registsKeyword3 = new RegistsKeywordDAO(keywords[2], R.color.regist_keyword_3, R.color.text_color_white, false);
		list.add(registsKeyword3);
		RegistsKeywordDAO registsKeyword4 = new RegistsKeywordDAO(keywords[3], R.color.regist_keyword_4, R.color.text_color_3, false);
		list.add(registsKeyword4);
		RegistsKeywordDAO registsKeyword5 = new RegistsKeywordDAO(keywords[4], R.color.regist_keyword_5, R.color.text_color_white, false);
		list.add(registsKeyword5);
		RegistsKeywordDAO registsKeyword6 = new RegistsKeywordDAO(keywords[5], R.color.regist_keyword_6, R.color.text_color_3, false);
		list.add(registsKeyword6);
		RegistsKeywordDAO registsKeyword7 = new RegistsKeywordDAO(keywords[6], R.color.regist_keyword_7, R.color.text_color_3, false);
		list.add(registsKeyword7);
		RegistsKeywordDAO registsKeyword8 = new RegistsKeywordDAO(keywords[7], R.color.regist_keyword_8, R.color.text_color_3, false);
		list.add(registsKeyword8);
		RegistsKeywordDAO registsKeyword9 = new RegistsKeywordDAO(keywords[8], R.color.regist_keyword_9, R.color.text_color_3, false);
		list.add(registsKeyword9);
		adapter.setList(list);
	}
	private void update_user(String interest){
		if(TextUtils.isEmpty(interest)){
			MyToast.showToast(this, "您至少选择一项！", 0);
			return;
		}
		Content.isPull = true;
		progressDialog.progressDialog();
		httpResponseUtils.postJson(httpPostParams.getPostParams(PostMethod.update_user.name(), PostType.user.name(), 
				httpPostParams.update_user(preferenceUtil.getUUid(), preferenceUtil.getID()+"",interest)), 
				UserInfo.class,
				new PostCommentResponseListener() {
					@Override
					public void requestCompleted(Object response) throws JSONException {
						Content.isPull = false;
						progressDialog.cancleProgress();
						if(response == null) return;
//						MyToast.showToast(RegistActivity_3.this, getResources().getString(R.string.regist_sucess_tip), 1);
						showDialog(RegistActivity_3.this, getResources().getString(R.string.regist_sucess_tip));
						
					}
				});
	}
	private void showDialog(Context context,final String message){
		View view = LayoutInflater.from(context).inflate(R.layout.share_tip, null,false);
		Button btn_submit = (Button) view.findViewById(R.id.btn_submit);
		TextView tv_message = (TextView) view.findViewById(R.id.tv_message); 
		tv_message.setText(message);
		final Dialog dialog = DialogShow.showDialog((Activity) context, view,Gravity.CENTER);
		dialog.setCanceledOnTouchOutside(false);
		btn_submit.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) { 
				if(!loginTag){
					Intent intent = new Intent(RegistActivity_3.this, HomeActivity.class);
					intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					startActivity(intent);
					}
				ActivityManagerUtil.finishActivity();
				finish();
				dialog.cancel();
			}
		});
		dialog.show();
	}
}

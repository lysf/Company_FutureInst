package com.futureinst.login;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.GridView;

import com.futureinst.R;
import com.futureinst.baseui.BaseActivity;
import com.futureinst.home.HomeActivity;
import com.futureinst.model.basemodel.RegistsKeywordDAO;
import com.futureinst.model.usermodel.UserInfo;
import com.futureinst.net.PostCommentResponseListener;
import com.futureinst.net.PostMethod;
import com.futureinst.net.PostType;
import com.futureinst.utils.MyToast;

public class RegistActivity_3 extends BaseActivity {
	private GridView gridView;
	private KeywordGridViewAdapter adapter;
	private String keywords[];
	private List<RegistsKeywordDAO > list;
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
		progressDialog.progressDialog();
		httpResponseUtils.postJson(httpPostParams.getPostParams(PostMethod.update_user.name(), PostType.user.name(), 
				httpPostParams.update_user(preferenceUtil.getUUid(), preferenceUtil.getID()+"",interest)), 
				UserInfo.class,
				new PostCommentResponseListener() {
					@Override
					public void requestCompleted(Object response) throws JSONException {
						progressDialog.cancleProgress();
						if(response == null) return;
						MyToast.showToast(RegistActivity_3.this, getResources().getString(R.string.regist_sucess_tip), 1);
						Intent intent = new Intent(RegistActivity_3.this,HomeActivity.class);
						intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
						startActivity(intent);
					}
				});
	}
}

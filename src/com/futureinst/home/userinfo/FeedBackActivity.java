package com.futureinst.home.userinfo;

import org.json.JSONException;

import com.futureinst.R;
import com.futureinst.baseui.BaseActivity;
import com.futureinst.model.basemodel.BaseModel;
import com.futureinst.model.order.UnDealOrderInfo;
import com.futureinst.net.HttpPostParams;
import com.futureinst.net.HttpResponseUtils;
import com.futureinst.net.PostCommentResponseListener;
import com.futureinst.net.PostMethod;
import com.futureinst.net.PostType;
import com.futureinst.utils.MyToast;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

public class FeedBackActivity extends BaseActivity {
	private EditText et_feedback;
	@Override
	protected void localOnCreate(Bundle savedInstanceState) {
		setTitle("意见反馈");
		setRight("发送");
		getLeftImageView().setImageDrawable(getResources().getDrawable(R.drawable.back));
		setContentView(R.layout.activity_feedback);
		initView();
	}
	@Override
	protected void onLeftImageViewClick(View view) {
		super.onLeftImageViewClick(view);
		finish();
	}
	@Override
	protected void onRightClick(View view) {
		super.onRightClick(view);
		//发送意见
		String content = et_feedback.getText().toString().trim();
		if(TextUtils.isEmpty(content)){
			MyToast.showToast(FeedBackActivity.this, "请填写您的意见和建议", 0);
			return;
		}
		add_feedback(content);
		
	}
	private void initView() {
		et_feedback = (EditText) findViewById(R.id.et_feedback);
		
		
	}
	private void add_feedback(String content){
		progressDialog.progressDialog();
		httpResponseUtils.postJson(
				httpPostParams.getPostParams(PostMethod.add_feedback.name(), PostType.common.name(),
						httpPostParams.add_feedback(preferenceUtil.getUUid(), preferenceUtil.getID()+"",content)),
				BaseModel.class, 
				new PostCommentResponseListener() {
					@Override
					public void requestCompleted(Object response) throws JSONException {
						progressDialog.cancleProgress();
						if(response == null) return;
						finish();
						MyToast.showToast(FeedBackActivity.this, "您的意见反馈成功", 1);
					}
				});
	
	}
}

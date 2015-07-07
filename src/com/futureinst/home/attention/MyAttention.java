package com.futureinst.home.attention;

import org.json.JSONException;

import com.futureinst.R;
import com.futureinst.baseui.BaseActivity;
import com.futureinst.model.attention.AttentionInfoDAO;
import com.futureinst.net.PostCommentResponseListener;
import com.futureinst.net.PostMethod;
import com.futureinst.net.PostType;

import android.os.Bundle;

public class MyAttention extends BaseActivity {

	@Override
	protected void localOnCreate(Bundle savedInstanceState) {
		setTitle("我的关注");
		getLeftImageView().setImageDrawable(getResources().getDrawable(R.drawable.back));
//		setContentView(R.layout.activity_attention);
	}
	
	//获取我的关注
	private void getMyAttention(){
		httpResponseUtils.postJson(
				httpPostParams.getPostParams(PostMethod.query_follow.name(), PostType.follow.name(),
						httpPostParams.query_follow(preferenceUtil.getID()+"", preferenceUtil.getUUid())), 
				AttentionInfoDAO.class, 
				new PostCommentResponseListener() {
			@Override
			public void requestCompleted(Object response) throws JSONException {
				if(response == null) return;
				AttentionInfoDAO attentionInfoDAO = (AttentionInfoDAO) response;
				
			}
		});
	}
}

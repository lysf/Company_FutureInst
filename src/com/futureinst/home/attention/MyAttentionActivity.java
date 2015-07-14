package com.futureinst.home.attention;

import org.json.JSONException;

import com.futureinst.R;
import com.futureinst.baseui.BaseActivity;
import com.futureinst.home.eventdetail.EventDetailActivity;
import com.futureinst.model.attention.AttentionDAO;
import com.futureinst.model.attention.AttentionInfoDAO;
import com.futureinst.model.homeeventmodel.QueryEventDAO;
import com.futureinst.net.PostCommentResponseListener;
import com.futureinst.net.PostMethod;
import com.futureinst.net.PostType;
import com.futureinst.widget.scrollview.OverListView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class MyAttentionActivity extends BaseActivity {
	private ListView lv_attention;
	private MyAttentionAdapter adapter;
	@Override
	protected void localOnCreate(Bundle savedInstanceState) {
		setTitle("我的关注");
		getLeftImageView().setImageDrawable(getResources().getDrawable(R.drawable.back));
		setContentView(R.layout.activity_attention);
		initView();
		getMyAttention();
	}
	
	private void initView() {
		lv_attention = (ListView) findViewById(R.id.lv_attention);
		adapter = new MyAttentionAdapter(this);
		lv_attention.setAdapter(adapter);
		lv_attention.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				QueryEventDAO item = ((AttentionDAO) adapter.getItem(position)).getEvent();
				Intent intent = new Intent(MyAttentionActivity.this, EventDetailActivity.class);
				intent.putExtra("event", item);
				startActivity(intent);
			}
		});
	}

	//获取我的关注
	private void getMyAttention(){
		progressDialog.progressDialog();
		httpResponseUtils.postJson(
				httpPostParams.getPostParams(PostMethod.query_follow.name(), PostType.follow.name(),
						httpPostParams.query_follow(preferenceUtil.getID()+"", preferenceUtil.getUUid())), 
				AttentionInfoDAO.class, 
				new PostCommentResponseListener() {
			@Override
			public void requestCompleted(Object response) throws JSONException {
				progressDialog.cancleProgress();
				if(response == null) return;
				AttentionInfoDAO attentionInfoDAO = (AttentionInfoDAO) response;
				adapter.setList(attentionInfoDAO.getFollows());
				adapter.start();
			}
		});
	}
}

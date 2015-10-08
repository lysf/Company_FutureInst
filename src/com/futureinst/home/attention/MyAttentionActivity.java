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

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class MyAttentionActivity extends BaseActivity {
	private ListView lv_attention;
	private MyAttentionAdapter adapter;
	private int page=1;
	private String last_id = "0";
	@Override
	protected void localOnCreate(Bundle savedInstanceState) {
		setTitle("我的关注");
		getLeftImageView().setImageDrawable(getResources().getDrawable(R.drawable.back));
		setContentView(R.layout.activity_attention);
		initView();
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
				intent.putExtra("eventId", item.getId()+"");
				startActivity(intent);
			}
		});
	}
	@Override
	protected void onResume() {
		super.onResume();
		getMyAttention(page,last_id);
	}
	//获取我的关注
	private void getMyAttention(int page,String last_id){
		progressDialog.progressDialog();
		httpResponseUtils.postJson(
				httpPostParams.getPostParams(PostMethod.query_follow.name(), PostType.follow.name(),
						httpPostParams.query_follow(preferenceUtil.getID()+"", preferenceUtil.getUUid(),page,last_id)), 
				AttentionInfoDAO.class, 
				new PostCommentResponseListener() {
			@Override
			public void requestCompleted(Object response) throws JSONException {
				progressDialog.cancleProgress();
				if(response == null) return;
				AttentionInfoDAO attentionInfoDAO = (AttentionInfoDAO) response;
				adapter.setList(attentionInfoDAO.getFollows());
				notifyDate();
			}
		});
	}
	Handler handler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 111:
				adapter.notifyDataSetChanged();
				break;
			}
		};
	};
	boolean flag = false;
	public void notifyDate(){
		flag = true;
		new Thread(new Runnable() {
			public void run() {
				while(flag){
					handler.sendEmptyMessage(111);
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}).start();
	}
	@Override
	protected void onDestroy() {
		super.onDestroy();
		flag = false;
	}
}

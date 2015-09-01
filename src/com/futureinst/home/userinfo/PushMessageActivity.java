package com.futureinst.home.userinfo;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

import com.futureinst.R;
import com.futureinst.baseui.BaseActivity;
import com.futureinst.comment.CommentActivity;
import com.futureinst.home.eventdetail.EventDetailActivity;
import com.futureinst.model.push.PushMessageDAO;
import com.futureinst.model.push.PushMessageInfo;
import com.futureinst.push.PushMessageUtils;
import com.futureinst.push.PushWebActivity;
import com.futureinst.widget.list.PullListView;

public class PushMessageActivity extends BaseActivity {
	private PullListView pullListView;
	private PushMessageAdapter adapter;
	private PushMessageInfo pushMessageInfo;
	private PushMessageUtils pushMessageUtils;
	private boolean push;
	private PushMessageDAO pushMessageDAO;
	@Override
	protected void localOnCreate(Bundle savedInstanceState) {
		setContentView(R.layout.activity_push_message);
		setTitle(R.string.pushMessage);
		getLeftImageView().setImageDrawable(getResources().getDrawable(R.drawable.back));
		initView();
	}
	@Override
	protected void onLeftImageViewClick(View view) {
		super.onLeftImageViewClick(view);
		pushMessageUtils.setAllRead();
		finish();
	}
	private void initView() {
		push = getIntent().getBooleanExtra("push", false);
		pushMessageDAO = (PushMessageDAO) getIntent().getSerializableExtra("pushMessage");
		if(push){
			itemClick(pushMessageDAO);
		}
		pushMessageUtils = new PushMessageUtils(this);
		try {
			pushMessageInfo = pushMessageUtils.readObject();
			Log.i(TAG, "-----------pushMessageInfo-->>"+pushMessageInfo);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		pullListView = (PullListView) findViewById(R.id.pull_listView);
		pullListView.setRefresh(false);
		pullListView.setLoadMore(false);
		adapter = new PushMessageAdapter(this);
		pullListView.setAdapter(adapter);
		if(pushMessageInfo!=null)
			adapter.setList(pushMessageInfo.getList());
		pullListView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				PushMessageDAO item = (PushMessageDAO) adapter.getItem(position-1);
				itemClick(item);
			}
		});
		
	}
	//点击事件
	public void itemClick(PushMessageDAO item){
		if(item.getEvent() != null ||(item.getType() == null && item.getEvent_id()!=null)
				|| (item.getType()!=null && item.getType().equals("event"))){//事件详情
			Intent intent = new Intent(PushMessageActivity.this, EventDetailActivity.class);
			intent.putExtra("eventId",item.getEvent_id()+"");
			startActivity(intent);
		}
		if(item.getType()!=null){
			if(item.getType().equals("comment")){//评论
				Intent intent = new Intent(PushMessageActivity.this, CommentActivity.class);
				intent.putExtra("eventId",item.getEvent_id()+"");
				startActivity(intent);
			}
			if(item.getType().equals("rank")){//排名
				Intent intent = new Intent("rank");
				sendBroadcast(intent);
				finish();
			}
			if(item.getType().equals("url")){//打开指定网页
				Intent intent = new Intent(PushMessageActivity.this, PushWebActivity.class);
				intent.putExtra("url", item.getHref());
				startActivity(intent);
			}
		}
	}
	@Override
	public void onBackPressed() {
		super.onBackPressed();
		pushMessageUtils.setAllRead();
	}
	@Override
	protected void onDestroy() {
		super.onDestroy();
		pushMessageUtils.setAllRead();
	}
}

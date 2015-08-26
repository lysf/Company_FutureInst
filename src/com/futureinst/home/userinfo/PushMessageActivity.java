package com.futureinst.home.userinfo;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

import com.futureinst.R;
import com.futureinst.baseui.BaseActivity;
import com.futureinst.home.HomeActivity;
import com.futureinst.home.eventdetail.EventDetailActivity;
import com.futureinst.model.push.PushMessageDAO;
import com.futureinst.model.push.PushMessageInfo;
import com.futureinst.push.PushMessageUtils;
import com.futureinst.widget.list.PullListView;

public class PushMessageActivity extends BaseActivity {
	private PullListView pullListView;
	private PushMessageAdapter adapter;
	private PushMessageInfo pushMessageInfo;
	private PushMessageUtils pushMessageUtils;
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
		pushMessageUtils = PushMessageUtils.getInstance(this);
		try {
			pushMessageInfo = pushMessageUtils.readObject();
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
				if(item.getEvent() != null){ 
					Intent intent = new Intent(PushMessageActivity.this, EventDetailActivity.class);
					intent.putExtra("eventId",item.getEvent().getId()+"");
					startActivity(intent);
				}
			}
		});
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

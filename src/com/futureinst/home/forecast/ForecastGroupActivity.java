package com.futureinst.home.forecast;

import org.json.JSONException;

import com.futureinst.R;
import com.futureinst.baseui.BaseActivity;
import com.futureinst.home.SystemTimeUtile;
import com.futureinst.home.eventdetail.EventDetailActivity;
import com.futureinst.model.homeeventmodel.QueryEventDAO;
import com.futureinst.model.homeeventmodel.QueryEventInfoDAO;
import com.futureinst.net.HttpPostParams;
import com.futureinst.net.HttpResponseUtils;
import com.futureinst.net.PostCommentResponseListener;
import com.futureinst.net.PostMethod;
import com.futureinst.net.PostType;
import com.futureinst.widget.list.PullListView;
import com.futureinst.widget.list.PullListView.OnRefreshListener;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

public class ForecastGroupActivity extends BaseActivity implements OnRefreshListener{
	private PullListView pullListView;
	private ForecastItemAdapter adapter;
	private String group_id;
	private String title;
	@Override
	protected void localOnCreate(Bundle savedInstanceState) {
		setContentView(R.layout.pull_listview_2);
		getLeftImageView().setImageDrawable(getResources().getDrawable(R.drawable.back));
//		setTitle(R.string.company_name);
		initView();
		getData(group_id);
	}
	private void initView() {
		// TODO Auto-generated method stub
		group_id = getIntent().getStringExtra("group_id");
		title = getIntent().getStringExtra("title");
		if(title.length() > 15){
			title = title.substring(0, 15) + "...";
		}
		setTitle(title);
		pullListView = (PullListView) findViewById(R.id.pull_listView);
		pullListView.setonRefreshListener(this);
		adapter = new ForecastItemAdapter(this);
		pullListView.setAdapter(adapter);
		pullListView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int index, long id) {
				if(index < 1) return;
				QueryEventDAO item = (QueryEventDAO) adapter.getItem(index - 1);
					//预测
					Intent intent = new Intent(ForecastGroupActivity.this, EventDetailActivity.class);
					intent.putExtra("eventId", item.getId()+"");
					startActivity(intent);
				
			}
		});
	}
	@Override
	public void onRefresh(boolean isTop) {
		if(isTop){
			getData(group_id);
		}
		
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
	private void notifyDate(){
		if(flag) return;
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
	
	//获取事件数据
		 private void getData(String group_id){
			 HttpResponseUtils.getInstace(this).postJson(
					 HttpPostParams.getInstace().getPostParams(PostMethod.query_event_all.name(), PostType.event.name(), HttpPostParams.getInstace().query_event(group_id)), 
					 QueryEventInfoDAO.class, 
					 new PostCommentResponseListener() {
						@Override
						public void requestCompleted(Object response) throws JSONException {
							pullListView.onRefreshComplete();
							if(response == null) return;
							QueryEventInfoDAO queryEventInfoDAO = (QueryEventInfoDAO) response;
							SystemTimeUtile.getInstance(queryEventInfoDAO.getCurr_time()).setSystemTime(queryEventInfoDAO.getCurr_time());
							adapter.setList(queryEventInfoDAO.getEvents());
							notifyDate();
						}
					});
		 }
	@Override
	public void onPause() {
		super.onPause();
	
		flag = false;
		
	}
	@Override
	public void onDestroy() {
		super.onDestroy();
		flag = false;
	}
}

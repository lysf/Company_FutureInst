package com.futureinst.home.userinfo;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;

import com.futureinst.R;
import com.futureinst.baseui.BaseActivity;
import com.futureinst.comment.CommentActivity;
import com.futureinst.db.PushMessageCacheUtil;
import com.futureinst.global.Content;
import com.futureinst.home.eventdetail.EventDetailActivity;
import com.futureinst.home.forecast.ForecastGroupActivity;
import com.futureinst.model.homeeventmodel.QueryEventDAO;
import com.futureinst.model.homeeventmodel.SingleEventInfoDAO;
import com.futureinst.model.push.PushMessageDAO;
import com.futureinst.net.PostCommentResponseListener;
import com.futureinst.net.PostMethod;
import com.futureinst.net.PostType;
import com.futureinst.net.SingleEventScope;
import com.futureinst.personalinfo.other.PersonalShowActivity;
import com.futureinst.push.PushWebActivity;
import com.futureinst.widget.list.PullListView;

public class PushMessageActivity extends BaseActivity {
	private PullListView pullListView;
	private PushMessageAdapter adapter;
//	private PushMessageInfo pushMessageInfo;
//	private PushMessageUtils pushMessageUtils;
	private List<PushMessageDAO> list;
	private PushMessageCacheUtil messageCacheUtil;
	private boolean push;
	private PushMessageDAO pushMessageDAO;
	@Override
	protected void localOnCreate(Bundle savedInstanceState) {
		setContentView(R.layout.activity_push_message);
		setTitle(R.string.pushMessage);
		getLeftImageView().setImageDrawable(getResources().getDrawable(R.drawable.back));
		initView();
		messageCacheUtil.updateMesaage(null);
	}
	@Override
	protected void onLeftImageViewClick(View view) {
		super.onLeftImageViewClick(view);
		finish();
	}
	private void initView() {
		push = getIntent().getBooleanExtra("push", false);
		pushMessageDAO = (PushMessageDAO) getIntent().getSerializableExtra("pushMessage");
//		pushMessageUtils = new PushMessageUtils(this);
		messageCacheUtil = PushMessageCacheUtil.getInstance(this);
		list = new ArrayList<PushMessageDAO>();
		list = messageCacheUtil.getPushMessage();
//		try {
//			pushMessageInfo = pushMessageUtils.readObject();
//			Log.i(TAG, "-----------pushMessageInfo-->>"+pushMessageInfo);
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		pullListView = (PullListView) findViewById(R.id.pull_listView);
		pullListView.setRefresh(false);
		pullListView.setLoadMore(false);
		adapter = new PushMessageAdapter(this);
		pullListView.setAdapter(adapter);
//		if(pushMessageInfo!=null)
			adapter.setList(list);
		pullListView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				PushMessageDAO item = (PushMessageDAO) adapter.getItem(position-1);
				list.get(position-1).setRead(true);
				itemClick(item);
			}
		});
		if(push){
			itemClick(pushMessageDAO);
		}
	}
	//点击事件
	public void itemClick(PushMessageDAO item){
		if(item.getType() == null){//专题或广告
			if(item.getEvent_id()!=null){
				query_single_event(item.getEvent_id());
				adapter.notifyDataSetChanged();
			}
		}
		else{
			if(item.getType().equals("event") || item.getType().equals("deal")){//事件详情
				Intent intent = new Intent(PushMessageActivity.this, EventDetailActivity.class);
				intent.putExtra("eventId",item.getEvent_id()+"");
				startActivity(intent);
				adapter.notifyDataSetChanged();
			}else if(item.getType().equals("comment")){//评论
				Intent intent = new Intent(PushMessageActivity.this, CommentActivity.class);
				intent.putExtra("eventId",item.getEvent_id()+"");
				startActivity(intent);
				adapter.notifyDataSetChanged();
			}
			if(item.getType().equals("rank")){//排名
				Intent intent = new Intent("rank");
				sendBroadcast(intent);
				finish();
				adapter.notifyDataSetChanged();
			}
			if(item.getType().equals("url")){//打开指定网页
				Intent intent = new Intent(PushMessageActivity.this, PushWebActivity.class);
				intent.putExtra("url", item.getHref());
				intent.putExtra("title", "");
				startActivity(intent);
				adapter.notifyDataSetChanged();
			}
			if(item.getType().equals("follow_me")){//关注通知
				Intent intent = new Intent(PushMessageActivity.this,PersonalShowActivity.class);
				intent.putExtra("id", item.getPeer_id());
				startActivity(intent);
			}
		}
	}
	
	private void query_single_event(String event_id){
		progressDialog.progressDialog();
		Content.isPull = true;
		httpResponseUtils.postJson(httpPostParams.getPostParams(
				PostMethod.query_single_event.name(), PostType.event.name(), 
				httpPostParams.query_single_event(event_id, SingleEventScope.base.name())), 
				SingleEventInfoDAO.class, 
				new PostCommentResponseListener() {
					@Override
					public void requestCompleted(Object response) throws JSONException {
						progressDialog.cancleProgress();
						Content.isPull = false;
						if(response == null) return;
						SingleEventInfoDAO queryEventInfoDAO = (SingleEventInfoDAO) response;
						QueryEventDAO event = queryEventInfoDAO.getEvent();
						operate(event);
					}
				});
	
	}
	
	private void operate(QueryEventDAO item){
		if(item.getType() == 1){//专题
			Intent intent = new Intent(this, ForecastGroupActivity.class);
			intent.putExtra("group_id", item.getId()+"");
			intent.putExtra("title", item.getTitle());
			startActivity(intent);
		}else if(item.getType() == 2){//广告
			Intent intent = new Intent(this, PushWebActivity.class);
			Log.i("", "=======================广告=>>");
			intent.putExtra("url", item.getLead());
			intent.putExtra("title", item.getTitle());
			startActivity(intent);
		}else{
			//预测
			Intent intent = new Intent(this, EventDetailActivity.class);
			intent.putExtra("eventId", item.getId()+"");
			startActivity(intent);
		}
	}
	@Override
	protected void onDestroy() {
		super.onDestroy();
	}
}

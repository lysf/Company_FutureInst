package com.futureinst.personalinfo.other;



import org.json.JSONException;

import com.futureinst.R;
import com.futureinst.baseui.BaseActivity;
import com.futureinst.model.other.OtherForecastInfo;
import com.futureinst.net.PostCommentResponseListener;
import com.futureinst.net.PostMethod;
import com.futureinst.net.PostType;
import com.futureinst.widget.list.PullListView;
import com.futureinst.widget.list.PullListView.OnRefreshListener;

import android.os.Bundle;

public class PersonalTagRecordActivity extends BaseActivity implements OnRefreshListener{
	private String title;
	private String id;
	private int type;
	private PullListView pullListView;
	private PersonalForecastAdapter adapter;
	private boolean isMe;
	@Override
	protected void localOnCreate(Bundle savedInstanceState) {
		setContentView(R.layout.view_pulllist_personal);
		getLeftImageView().setImageDrawable(getResources().getDrawable(R.drawable.back));
		initView();
		progressDialog.progressDialog();
		if(isMe){
			query_event_clear(page, last_id, type);
		}else{
			peer_info_query_event_clear(id, type,page,last_id);
		}
	}
	private void initView() {
		isMe = getIntent().getBooleanExtra("isMe", false);
		title = getIntent().getStringExtra("title");
		id = getIntent().getStringExtra("id");
		type = getIntent().getIntExtra("type", 0);
		setTitle(title);
		pullListView = (PullListView) findViewById(R.id.pull_listView);
		pullListView.setonRefreshListener(this);
		adapter = new PersonalForecastAdapter(this);
		adapter.setGain(true);
		adapter.setTagRecord();
		pullListView.setAdapter(adapter);
		
	}
	//查询他人战绩的具体事件
	private void peer_info_query_event_clear(String peer_id,int tag,final int page,String last_id){
		httpResponseUtils.postJson(httpPostParams.getPostParams(PostMethod.peer_info_query_event_clear.name(), PostType.peer_info.name(),
				httpPostParams.peer_info_query_event_clear(preferenceUtil.getUUid(), preferenceUtil.getID()+"", peer_id,tag,page,last_id,"clear")), 
				OtherForecastInfo.class,
				new PostCommentResponseListener() {
					@Override
					public void requestCompleted(Object response) throws JSONException {
						progressDialog.cancleProgress();
						pullListView.onRefreshComplete();
						if(response == null)
							return;
						OtherForecastInfo otherForecastInfo = (OtherForecastInfo) response;
						
						if(page == 1){
							adapter.refresh(otherForecastInfo.getEventclears());
						}else{
							adapter.setList(otherForecastInfo.getEventclears());
						}
						if(adapter.getCount()>9){
							pullListView.setLoadMore(true);
						}else{
							pullListView.setLoadMore(false);
						}
						if(page!=1 && (otherForecastInfo.getEventclears()==null || otherForecastInfo.getEventclears().size() ==0)){
							handler.sendEmptyMessage(0);
							pullListView.setLoadMore(false);
						}
					}
				});
	}
	//查询自己战绩的具体事件
	private void query_event_clear(final int page,String last_id,int tag){
		httpResponseUtils.postJson(httpPostParams.getPostParams(PostMethod.query_event_clear.name(), PostType.event_clear.name(),
				httpPostParams.query_event_clear(preferenceUtil.getID()+"", preferenceUtil.getUUid(),page,last_id,tag,"clear")), 
				OtherForecastInfo.class,
				new PostCommentResponseListener() {
			@Override
			public void requestCompleted(Object response) throws JSONException {
				progressDialog.cancleProgress();
				pullListView.onRefreshComplete();
				if(response == null)
					return;
				OtherForecastInfo otherForecastInfo = (OtherForecastInfo) response;
				
				if(page == 1){
					adapter.refresh(otherForecastInfo.getEventclears());
				}else{
					adapter.setList(otherForecastInfo.getEventclears());
				}
				if(adapter.getCount()>9){
					pullListView.setLoadMore(true);
				}else{
					pullListView.setLoadMore(false);
				}
				if(page!=1 && (otherForecastInfo.getEventclears()==null || otherForecastInfo.getEventclears().size() ==0)){
					handler.sendEmptyMessage(0);
					pullListView.setLoadMore(false);
				}
			}
		});
	}
	@Override
	public void onRefresh(boolean isTop) {
		if(isTop){
			page = 1;
			last_id = "0";
		}else{
				page++;
				if(adapter.getList() !=null && adapter.getCount() > 0){
					last_id = adapter.getList().get(adapter.getCount()-1).getUeid();
				}
		}
		if(isMe){
			query_event_clear(page, last_id, type);
		}else{
			peer_info_query_event_clear(id, type,page,last_id);
		}
	}

}

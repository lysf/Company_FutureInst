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

public class PersonalForecastActivity extends BaseActivity implements OnRefreshListener{
	private PullListView pullListView;
	private PersonalForecastAdapter adapter;
	private String id;
	@Override
	protected void localOnCreate(Bundle savedInstanceState) {
		setContentView(R.layout.view_pulllist_personal);
		setTitle(R.string.show_forecast);
		getLeftImageView().setImageDrawable(getResources().getDrawable(R.drawable.back));
		initView();
		peer_info_query_event_trade(id,page,last_id);
	}
	private void initView() {
		id = getIntent().getStringExtra("id");
		pullListView = (PullListView) findViewById(R.id.pull_listView);
		pullListView.setonRefreshListener(this);
		adapter = new PersonalForecastAdapter(this);
		pullListView.setAdapter(adapter);
		
	}
	//预测中事件
		private void peer_info_query_event_trade(String peer_id,final int page,String last_id){
			httpResponseUtils.postJson(httpPostParams.getPostParams(PostMethod.peer_info_query_event_trade.name(), PostType.peer_info.name(),
					httpPostParams.peer_info_query_event_trade (preferenceUtil.getUUid(), preferenceUtil.getID()+"",peer_id,page,last_id)),
					OtherForecastInfo.class,
					new PostCommentResponseListener() {
				@Override
				public void requestCompleted(Object response) throws JSONException {
					pullListView.onRefreshComplete();
					if(response == null ) return;
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
		
		peer_info_query_event_trade(id,page,last_id);
	}

}

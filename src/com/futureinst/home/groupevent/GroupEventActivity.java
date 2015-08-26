package com.futureinst.home.groupevent;

import org.json.JSONException;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;

import com.futureinst.R;
import com.futureinst.baseui.BaseActivity;
import com.futureinst.home.eventdetail.EventDetailActivity;
import com.futureinst.home.search.SearchAdapter;
import com.futureinst.model.homeeventmodel.QueryEventDAO;
import com.futureinst.model.homeeventmodel.QueryEventInfoDAO;
import com.futureinst.net.HttpPostParams;
import com.futureinst.net.HttpResponseUtils;
import com.futureinst.net.PostCommentResponseListener;
import com.futureinst.net.PostMethod;
import com.futureinst.net.PostType;

public class GroupEventActivity extends BaseActivity {
	private GridView gv_groupEvent;
	private SearchAdapter adapter;
	private String id;
	@Override
	protected void localOnCreate(Bundle savedInstanceState) {
		setContentView(R.layout.activity_group_event);
		initView();
		getData(id);
	}

	private void initView() {
		id = getIntent().getStringExtra("groupId");
		String title = getIntent().getStringExtra("groupEventTitle");
		getLeftImageView().setImageDrawable(getResources().getDrawable(R.drawable.back));
		setTitle(title);
		gv_groupEvent = (GridView) findViewById(R.id.gv_group_event);
		adapter = new SearchAdapter(this);
		gv_groupEvent.setAdapter(adapter);
		gv_groupEvent.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position,
					long arg3) {
				QueryEventDAO item = (QueryEventDAO) adapter.getItem(position);
				Intent intent = new Intent(GroupEventActivity.this, EventDetailActivity.class);
				intent.putExtra("eventId", item.getId()+"");
				startActivity(intent);
				
			}
		});
	}
	//获取事件数据
		 private void getData(String id){
			 HttpResponseUtils.getInstace(this).postJson(
					 HttpPostParams.getInstace().getPostParams(PostMethod.query_event.name(), PostType.event.name(), HttpPostParams.getInstace().query_event_by_group_id(id)), 
					 QueryEventInfoDAO.class, 
					 new PostCommentResponseListener() {
						@Override
						public void requestCompleted(Object response) throws JSONException {
							if(response == null) return;
							QueryEventInfoDAO queryEventInfoDAO = (QueryEventInfoDAO) response;
							adapter.setList(queryEventInfoDAO.getEvents());
						}
					});
		 }
}

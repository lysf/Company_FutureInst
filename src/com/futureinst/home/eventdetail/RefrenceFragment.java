package com.futureinst.home.eventdetail;


import org.json.JSONException;

import com.futureinst.R;
import com.futureinst.baseui.BaseFragment;
import com.futureinst.model.homeeventmodel.EventRelatedInfo;
import com.futureinst.model.homeeventmodel.ReferenceDAO;
import com.futureinst.net.HttpPostParams;
import com.futureinst.net.HttpResponseUtils;
import com.futureinst.net.PostCommentResponseListener;
import com.futureinst.net.PostMethod;
import com.futureinst.net.PostType;
import com.futureinst.net.SingleEventScope;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class RefrenceFragment extends BaseFragment {
	private ListView lv_refrence;
	private RefrenceAdapter adapter;
	private HttpResponseUtils httpResponseUtils;
	private HttpPostParams httpPostParams;
	private String event_id;
	private boolean isVisiable;
	@Override
	protected void localOnCreate(Bundle savedInstanceState) {
		setContentView(R.layout.fragment_refrence);
		initView();
		getEvetnRealted();
		isVisiable = true;
	}
	@Override
	public void setUserVisibleHint(boolean isVisibleToUser) {
		super.setUserVisibleHint(isVisibleToUser);
		if(isVisibleToUser && isVisiable){
			getEvetnRealted();	
		}
	}
	
	private void initView() {
		event_id = getArguments().getString("eventId");
		httpResponseUtils = HttpResponseUtils.getInstace(getActivity());
		httpPostParams = HttpPostParams.getInstace();
		lv_refrence = (ListView) findViewById(R.id.lv_refrence);
		adapter = new RefrenceAdapter(getContext());
		lv_refrence.setAdapter(adapter);
		lv_refrence.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				ReferenceDAO item = (ReferenceDAO) adapter.getItem(position);
				Intent intent = new Intent(getActivity(), RefrenceNewsActivity.class);
				intent.putExtra("news", item);
				startActivity(intent);
			}
		});
	}
	//获取事件相关信息
		private void getEvetnRealted(){
			httpResponseUtils.postJson(httpPostParams.getPostParams(
					PostMethod.query_single_event.name(), PostType.event.name(), 
					httpPostParams.query_single_event(event_id, SingleEventScope.related.name())), 
					EventRelatedInfo.class, 
					new PostCommentResponseListener() {
						@Override
						public void requestCompleted(Object response) throws JSONException {
							if(response == null) return;
							EventRelatedInfo eventRelatedInfo = (EventRelatedInfo) response;
							
							adapter.setList(eventRelatedInfo.getRelated().getRefer().getRefer());
							
						}
					});
		}
}

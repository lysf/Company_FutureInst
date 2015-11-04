package com.futureinst.home.eventdetail;


import org.json.JSONException;

import com.futureinst.R;
import com.futureinst.baseui.BaseFragment;
import com.futureinst.model.homeeventmodel.EventRelatedInfo;
import com.futureinst.model.homeeventmodel.ReferenceDAO;
import com.futureinst.model.homeeventmodel.ReferenceDAOInfo;
import com.futureinst.net.HttpPostParams;
import com.futureinst.net.HttpResponseUtils;
import com.futureinst.net.PostCommentResponseListener;
import com.futureinst.net.PostMethod;
import com.futureinst.net.PostType;
import com.futureinst.net.SingleEventScope;
import com.futureinst.widget.list.MyListView;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

import java.util.List;

public class RefrenceFragment extends BaseFragment {
	private MyListView lv_refrence;
	private RefrenceAdapter adapter;
	private HttpResponseUtils httpResponseUtils;
	private HttpPostParams httpPostParams;
	private String event_id;
//	private ReferenceDAOInfo referenceDAOInfo;
	private BroadcastReceiver receiver;
	public RefrenceFragment(){

	}
	@Override
	protected void localOnCreate(Bundle savedInstanceState) {
		setContentView(R.layout.fragment_refrence);
		initView();
	}

	@Override
	public void onAttach(Context context) {
		super.onAttach(context);

	}

	private void initView() {
		event_id = getArguments().getString("eventId");
//		referenceDAOInfo = (ReferenceDAOInfo) getArguments().getSerializable("date");
		httpResponseUtils = HttpResponseUtils.getInstace(getActivity());
		httpPostParams = HttpPostParams.getInstace();
		lv_refrence = (MyListView) findViewById(R.id.lv_refrence);
		adapter = new RefrenceAdapter(getContext());
//		adapter.setList(referenceDAOInfo.getRefer());
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


		receiver = new BroadcastReceiver() {
			@Override
			public void onReceive(Context context, Intent intent) {
				if(intent.getAction().equals("refer")){
					adapter.setList(((ReferenceDAOInfo)intent.getSerializableExtra("data")).getRefer());
				}
			}
		};
		IntentFilter filter = new IntentFilter("refer");
		getActivity().registerReceiver(receiver,filter);
	}
	public void update(List<ReferenceDAO> list){
		adapter.setList(list);
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		if(receiver != null){
			getActivity().unregisterReceiver(receiver);
			receiver = null;
		}
	}
}


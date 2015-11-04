package com.futureinst.home.eventdetail;


import android.content.Context;
import android.os.Bundle;

import com.futureinst.R;
import com.futureinst.baseui.BaseFragment;
import com.futureinst.model.homeeventmodel.LazyBagDAO;
import com.futureinst.model.homeeventmodel.LazyBagInfoDao;
import com.futureinst.widget.list.MyListView;

import java.util.List;

public class LazyBagFragment extends BaseFragment {
	private MyListView overListView;
	private LazyBagAdapter adapter;
	private String eventId;
	private LazyBagInfoDao lazyBagInfo;
	public  LazyBagFragment(){
	}
	@Override
	protected void localOnCreate(Bundle savedInstanceState) {
		setContentView(R.layout.view_over_listview);
		initView();
	}

	@Override
	public void onAttach(Context context) {
		super.onAttach(context);

	}

	private void initView() {
		eventId = getArguments().getString("eventId");
		lazyBagInfo = (LazyBagInfoDao) getArguments().getSerializable("date");
		overListView =  (MyListView) findViewById(R.id.myList);
		adapter = new LazyBagAdapter(getContext());
		adapter.setList(lazyBagInfo.getBags());
		overListView.setAdapter(adapter);
	
	}
	public void update(List<LazyBagDAO> list){
		if(list !=null){
			adapter.setList(list);
		}
	}


}

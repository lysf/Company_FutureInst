package com.futureinst.home.eventdetail;

import org.json.JSONException;

import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import com.futureinst.R;
import com.futureinst.baseui.BaseFragment;
import com.futureinst.model.homeeventmodel.LazyBagInfo;
import com.futureinst.net.HttpPostParams;
import com.futureinst.net.HttpResponseUtils;
import com.futureinst.net.PostCommentResponseListener;
import com.futureinst.net.PostMethod;
import com.futureinst.net.PostType;
import com.futureinst.net.SingleEventScope;
import com.futureinst.utils.ListViewHeightUtil;
import com.futureinst.widget.list.MyListView;

public class LazyBagFragment extends BaseFragment {
	private MyListView overListView;
	private LazyBagAdapter adapter;
	private String eventId;
	private HttpResponseUtils httpResponseUtils;
	private HttpPostParams httpPostParams;
	private boolean isStart;
	@Override
	protected void localOnCreate(Bundle savedInstanceState) {
		setContentView(R.layout.view_over_listview);
//		setTitle(getResources().getString(R.string.lazy_bag));
//		getLeftImageView().setImageDrawable(getResources().getDrawable(R.drawable.back));
		initView();
		getLazyBagData(eventId);
	}
	private void initView() {
		httpResponseUtils = HttpResponseUtils.getInstace(getActivity());
		httpPostParams = HttpPostParams.getInstace();
		eventId = getArguments().getString("eventId");
		overListView =  (MyListView) findViewById(R.id.myList);

		adapter = new LazyBagAdapter(getContext());
		overListView.setAdapter(adapter);
	
		isStart = true;
	}
	@Override
	public void setUserVisibleHint(boolean isVisibleToUser) {
		super.setUserVisibleHint(isVisibleToUser);
		if(isVisibleToUser && isStart){
			getLazyBagData(eventId);
		}
	}
	//获取懒人包数据
		private void getLazyBagData(String event_id){
//			progressDialog.progressDialog();
			httpResponseUtils.postJson(httpPostParams.getPostParams(
					PostMethod.query_single_event.name(), PostType.event.name(), 
					httpPostParams.query_single_event(event_id, SingleEventScope.lazybag.name())), 
					LazyBagInfo.class, 
					new PostCommentResponseListener() {
						@Override
						public void requestCompleted(Object response) throws JSONException {
//							progressDialog.cancleProgress();
							if(response == null) return;
							LazyBagInfo lazyBagInfo = (LazyBagInfo) response;
							adapter.setList(lazyBagInfo.getLazybag().getBags());
//							ListViewHeightUtil.setListViewHeightBasedOnChildren(overListView);
						}
					});
		}
}

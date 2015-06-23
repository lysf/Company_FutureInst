package com.futureinst.home.hold;

import org.json.JSONException;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import com.futureinst.R;
import com.futureinst.baseui.BaseFragment;
import com.futureinst.model.order.DealOrderInfo;
import com.futureinst.net.HttpPostParams;
import com.futureinst.net.HttpResponseUtils;
import com.futureinst.net.PostCommentResponseListener;
import com.futureinst.net.PostMethod;
import com.futureinst.net.PostType;
import com.futureinst.sharepreference.SharePreferenceUtil;
import com.futureinst.utils.MyProgressDialog;
import com.futureinst.widget.list.PullListView;
import com.futureinst.widget.list.PullListView.OnRefreshListener;

public class HoldDealFragment extends BaseFragment implements OnRefreshListener{
	private PullListView pullListView;
	private HoldDealAdapter adapter;
	private MyProgressDialog progressDialog;
	private SharePreferenceUtil preferenceUtil;
	@Override
	protected void localOnCreate(Bundle savedInstanceState) {
		setContentView(R.layout.pull_listview);
		initView();
		initListHeader();
		progressDialog.progressDialog();
		getData();
	}

	private void initView() {
		preferenceUtil = SharePreferenceUtil.getInstance(getContext());
		progressDialog = MyProgressDialog.getInstance(getContext());
		pullListView = (PullListView) findViewById(R.id.pull_listView);
		adapter = new HoldDealAdapter(getContext());
	}
	private void initListHeader(){
		View head = LayoutInflater.from(getContext()).inflate(R.layout.hold_order_top, null, false);
		pullListView.addHeaderView(head);
		pullListView.setLoadMore(false);
		pullListView.setonRefreshListener(this);
		pullListView.setAdapter(adapter);
	}
	private void getData(){
		HttpResponseUtils.getInstace(getActivity()).postJson(
				HttpPostParams.getInstace().getPostParams(PostMethod.query_event_clear.name(), PostType.event_clear .name(),
						HttpPostParams.getInstace().query_event_clear(preferenceUtil.getID()+"",preferenceUtil.getUUid())),
				DealOrderInfo.class, 
				new PostCommentResponseListener() {
					@Override
					public void requestCompleted(Object response) throws JSONException {
						pullListView.onRefreshComplete();
						progressDialog.cancleProgress();
						if(response == null) return;
						DealOrderInfo dealOrderInfo = (DealOrderInfo) response;
						adapter.setList(dealOrderInfo.getEventclears());
					}
				});
	}
	@Override
	public void onRefresh(boolean isTop) {
		if(isTop){
			getData();
		}
	}
}

package com.futureinst.home.hold;

import org.json.JSONException;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import com.futureinst.R;
import com.futureinst.baseui.BaseFragment;
import com.futureinst.model.order.UnDealOrderInfo;
import com.futureinst.net.HttpPostParams;
import com.futureinst.net.HttpResponseUtils;
import com.futureinst.net.PostCommentResponseListener;
import com.futureinst.net.PostMethod;
import com.futureinst.net.PostType;
import com.futureinst.sharepreference.SharePreferenceUtil;
import com.futureinst.widget.list.PullListView;
import com.futureinst.widget.list.PullListView.OnRefreshListener;

public class HoldUnDealFragment extends BaseFragment implements OnRefreshListener{

	private PullListView pullListView;
	private HoldUnDealAdapter adapter;
	private SharePreferenceUtil preferenceUtil;
	@Override
	protected void localOnCreate(Bundle savedInstanceState) {
		setContentView(R.layout.pull_listview);
		initView();
		initListHeader();
		getData();
	}

	private void initView() {
		preferenceUtil = SharePreferenceUtil.getInstance(getContext());
		pullListView = (PullListView) findViewById(R.id.pull_listView);
		adapter = new HoldUnDealAdapter(getContext());
	}
	private void initListHeader(){
		View head = LayoutInflater.from(getContext()).inflate(R.layout.unhold_order_top, null, false);
		pullListView.addHeaderView(head);
		pullListView.setAdapter(adapter);
		pullListView.setRefresh(true);
		pullListView.setLoadMore(false);
		pullListView.setonRefreshListener(this);
	}
	private void getData(){
		HttpResponseUtils.getInstace(getActivity()).postJson(
				HttpPostParams.getInstace().getPostParams(PostMethod.query_order.name(), PostType.order.name(),
						HttpPostParams.getInstace().query_order(preferenceUtil.getID()+"", preferenceUtil.getUUid())),
				UnDealOrderInfo.class, 
				new PostCommentResponseListener() {
					@Override
					public void requestCompleted(Object response) throws JSONException {
						pullListView.onRefreshComplete();
						if(response == null) return;
						UnDealOrderInfo orderInfo = (UnDealOrderInfo) response;
						adapter.setList(orderInfo.getOrders());
					}
				});
	}
	@Override
	public void onRefresh(boolean isTop) {
		if(isTop){
			//刷新
			getData();
		}
	}

}

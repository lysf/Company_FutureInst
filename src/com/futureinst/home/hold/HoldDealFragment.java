package com.futureinst.home.hold;

import org.json.JSONException;

import com.futureinst.R;
import com.futureinst.baseui.BaseFragment;
import com.futureinst.home.eventdetail.EventDetailActivity;
import com.futureinst.model.homeeventmodel.QueryEventDAO;
import com.futureinst.model.order.DealOrderDAO;
import com.futureinst.model.order.DealOrderInfo;
import com.futureinst.net.HttpPostParams;
import com.futureinst.net.HttpResponseUtils;
import com.futureinst.net.PostCommentResponseListener;
import com.futureinst.net.PostMethod;
import com.futureinst.net.PostType;
import com.futureinst.sharepreference.SharePreferenceUtil;
import com.futureinst.utils.DialogShow;
import com.futureinst.utils.MyProgressDialog;
import com.futureinst.widget.list.PullListView;
import com.futureinst.widget.list.PullListView.OnRefreshListener;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.TextView;

public class HoldDealFragment extends BaseFragment implements OnRefreshListener{
	private PullListView pullListView;
	private HoldDealAdapter adapter;
	private MyProgressDialog progressDialog;
	private SharePreferenceUtil preferenceUtil;
	private boolean isStart;
	private TextView empty;
	@Override
	protected void localOnCreate(Bundle savedInstanceState) {
		setContentView(R.layout.fragment_hold_deal);
		initView();
		initListHeader();
		progressDialog.progressDialog();
		getData();
		isStart = true;
	}
	@Override
	public void setUserVisibleHint(boolean isVisibleToUser) {
		if(isVisibleToUser && isStart){
			getData();
		}
		super.setUserVisibleHint(isVisibleToUser);
	}
	private void initView() {
		preferenceUtil = SharePreferenceUtil.getInstance(getContext());
		progressDialog = MyProgressDialog.getInstance(getContext());
		pullListView = (PullListView) findViewById(R.id.hold_pull_listView);
		empty = (TextView) findViewById(R.id.empty);
		adapter = new HoldDealAdapter(getContext());
		pullListView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				if(position == 0) return;
				QueryEventDAO item = ((DealOrderDAO)adapter.getItem(position-1)).getEvent();
				Intent intent = new Intent(getActivity(), EventDetailActivity.class);
				intent.putExtra("event", item);
				startActivity(intent);
				
			}
		});
	}
	private void initListHeader(){
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
						pullListView.setEmptyView(empty);
					}
				});
	}
	@Override
	public void onRefresh(boolean isTop) {
		if(isTop){
			getData();
		}
	}
	//删除已清算预测
	private void delete(){
		View view = LayoutInflater.from(getContext()).inflate(R.layout.view_hold_delete_tip, null, false);
		final Dialog dialog = DialogShow.showDialog(getActivity(), view, Gravity.CENTER);
		TextView tv_tips = (TextView) view.findViewById(R.id.tv_tips);
		tv_tips.setText(getResources().getString(R.string.hold_delete));
		Button btn_cancel = (Button) view.findViewById(R.id.btn_cancel);
		Button btn_submit = (Button) view.findViewById(R.id.btn_submit);
		btn_cancel.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				dialog.dismiss();
			}
		});
		btn_submit.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				//删除
				
			}
		});
		dialog.show();
	}
}

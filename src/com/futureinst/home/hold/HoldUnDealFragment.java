package com.futureinst.home.hold;

import org.json.JSONException;

import com.futureinst.R;
import com.futureinst.baseui.BaseFragment;
import com.futureinst.home.eventdetail.EventDetailActivity;
import com.futureinst.model.basemodel.BaseModel;
import com.futureinst.model.homeeventmodel.QueryEventDAO;
import com.futureinst.model.order.UnDealOrderDAO;
import com.futureinst.model.order.UnDealOrderInfo;
import com.futureinst.net.HttpPostParams;
import com.futureinst.net.HttpResponseUtils;
import com.futureinst.net.PostCommentResponseListener;
import com.futureinst.net.PostMethod;
import com.futureinst.net.PostType;
import com.futureinst.sharepreference.SharePreferenceUtil;
import com.futureinst.utils.DialogShow;
import com.futureinst.utils.MyProgressDialog;
import com.futureinst.utils.MyToast;
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
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Button;
import android.widget.TextView;

public class HoldUnDealFragment extends BaseFragment implements OnRefreshListener{
	private MyProgressDialog progressDialog;
	private PullListView pullListView;
	private HoldUnDealAdapter adapter;
	private SharePreferenceUtil preferenceUtil;
	private boolean isStart;
	private TextView empty;
	@Override
	protected void localOnCreate(Bundle savedInstanceState) {
		setContentView(R.layout.fragment_hold_deal);
		initView();
		initListHeader();
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
		progressDialog = MyProgressDialog.getInstance(getContext());
		preferenceUtil = SharePreferenceUtil.getInstance(getContext());
		pullListView = (PullListView) findViewById(R.id.hold_pull_listView);
		empty = (TextView) findViewById(R.id.empty);
		adapter = new HoldUnDealAdapter(getContext());
		pullListView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				if(position == 0) return;
				QueryEventDAO item = ((UnDealOrderDAO)adapter.getItem(position-1)).getEvent();
				Intent intent = new Intent(getActivity(), EventDetailActivity.class);
				intent.putExtra("eventId", item.getId()+"");
				startActivity(intent);
				
			}
		});
	}
	private void initListHeader(){
		pullListView.setAdapter(adapter);
		pullListView.setRefresh(true);
		pullListView.setLoadMore(false);
		pullListView.setonRefreshListener(this);
		pullListView.setOnItemLongClickListener(new OnItemLongClickListener() {
			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
				revoke_order(((UnDealOrderDAO)adapter.getItem(position-1)));
				return true;
			}
		});
	}
	//获取数据
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
						pullListView.setEmptyView(empty);
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
	//删除已失效预测
		private void delete(){
			View view = LayoutInflater.from(getContext()).inflate(R.layout.view_hold_delete_tip, null, false);
			final Dialog dialog = DialogShow.showDialog(getActivity(), view, Gravity.CENTER);
			TextView tv_tips = (TextView) view.findViewById(R.id.tv_tips);
			tv_tips.setText(getResources().getString(R.string.unhold_delete));
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
	//撤销订单提示
	private void revoke_order(final UnDealOrderDAO item){
			View view = LayoutInflater.from(getContext()).inflate(R.layout.view_hold_delete_tip, null, false);
			final Dialog dialog = DialogShow.showDialog(getActivity(), view, Gravity.CENTER);
			TextView tv_tips = (TextView) view.findViewById(R.id.tv_tips);
			tv_tips.setText(getResources().getString(R.string.unhold_revoke));
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
					update_order(item);
					dialog.dismiss();
				}
			});
			dialog.show();
		}
	//撤銷订单
	private void update_order(final UnDealOrderDAO item){
		progressDialog.progressDialog();
		HttpResponseUtils.getInstace(getActivity()).postJson(
				HttpPostParams.getInstace().getPostParams(PostMethod.update_order.name(), PostType.order.name(),
						HttpPostParams.getInstace().update_order(preferenceUtil.getID()+"", preferenceUtil.getUUid(), item.getId()+"", "undo")), 
				BaseModel.class,
				new PostCommentResponseListener() {
					@Override
					public void requestCompleted(Object response) throws JSONException {
						progressDialog.cancleProgress();
						if(response == null) return;
						//订单撤销成功
						BaseModel baseModel = (BaseModel) response;
						MyToast.getInstance().showToast(getActivity(), baseModel.getInfo(), 1);
						adapter.removeIten(item);
					}
				});
	}
}

package com.futureinst.home.eventdetail;

import org.json.JSONException;

import com.futureinst.R;
import com.futureinst.baseui.BaseFragment;
import com.futureinst.home.eventdetail.RevokeAdapter.onRightItemClickListener;
import com.futureinst.model.basemodel.BaseModel;
import com.futureinst.model.order.UnDealOrderDAO;
import com.futureinst.model.order.UnDealOrderInfo;
import com.futureinst.net.HttpPostParams;
import com.futureinst.net.HttpResponseUtils;
import com.futureinst.net.PostCommentResponseListener;
import com.futureinst.net.PostMethod;
import com.futureinst.net.PostType;
import com.futureinst.sharepreference.SharePreferenceUtil;
import com.futureinst.utils.DialogShow;
import com.futureinst.utils.ListViewHeightUtil;
import com.futureinst.utils.MyProgressDialog;
import com.futureinst.utils.MyToast;
import com.futureinst.widget.list.SwipeListView;

import android.app.Dialog;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class RevokeFragment extends BaseFragment {
	private LinearLayout ll_revoke;
	private MyProgressDialog progressDialog;
	private SharePreferenceUtil preferenceUtil;
	private SwipeListView lv_revoke;
	private RevokeAdapter adapter;
	private String event_id;
	private boolean isvisiable;
	@Override
	protected void localOnCreate(Bundle savedInstanceState) {
		setContentView(R.layout.fragment_revoke);
		initView();
		query_order();
		isvisiable = true;
	}
	@Override
	public void setUserVisibleHint(boolean isVisibleToUser) {
		super.setUserVisibleHint(isVisibleToUser);
		if(isVisibleToUser && isvisiable){
			query_order();
		}
	}
	private void initView() {
		event_id = getArguments().getString("eventId");
		progressDialog = MyProgressDialog.getInstance(getContext());
		preferenceUtil = SharePreferenceUtil.getInstance(getContext());
		lv_revoke = (SwipeListView) findViewById(R.id.lv_revoke);
		ll_revoke = (LinearLayout) findViewById(R.id.ll_revoke);
		adapter = new RevokeAdapter(getContext(), lv_revoke.getRightViewWidth());
		lv_revoke.setAdapter(adapter);
		adapter.setOnRightItemClickListener(new onRightItemClickListener() {
			@Override
			public void onRightItemClick(View v, int position) {
				revoke_order((UnDealOrderDAO)adapter.getItem(position));
				lv_revoke.hiddenRight();
			}
		});
		ll_revoke.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				lv_revoke.hiddenRight();
			}
		});
		ListViewHeightUtil.setListViewHeightBasedOnChildren(lv_revoke);
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
							MyToast.showToast(getActivity(), baseModel.getInfo(), 1);
							adapter.removeItem(item);
							ListViewHeightUtil.setListViewHeightBasedOnChildren(lv_revoke);
						}
					});
		}
		//获取订单
		private void query_order(){
//			progressDialog.progressDialog();
			HttpResponseUtils.getInstace(getActivity()).postJson(
					HttpPostParams.getInstace().getPostParams(PostMethod.query_order.name(), PostType.order.name(),
							HttpPostParams.getInstace().query_order(preferenceUtil.getID()+"", preferenceUtil.getUUid(),event_id)), 
					UnDealOrderInfo.class,
					new PostCommentResponseListener() {
						@Override
						public void requestCompleted(Object response) throws JSONException {
							progressDialog.cancleProgress();
							if(response == null) return;
							UnDealOrderInfo unDealOrderInfo = (UnDealOrderInfo) response;
							adapter.setList(unDealOrderInfo.getOrders());
							ListViewHeightUtil.setListViewHeightBasedOnChildren(lv_revoke);
						}
					});
		}
}

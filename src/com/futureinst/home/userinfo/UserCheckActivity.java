package com.futureinst.home.userinfo;

import org.json.JSONException;

import com.futureinst.R;
import com.futureinst.baseui.BaseActivity;
import com.futureinst.home.eventdetail.EventDetailActivity;
import com.futureinst.model.usermodel.UserCheckDAO;
import com.futureinst.model.usermodel.UserCheckInfo;
import com.futureinst.net.PostCommentResponseListener;
import com.futureinst.net.PostMethod;
import com.futureinst.net.PostType;
import com.futureinst.widget.list.PullListView;
import com.futureinst.widget.list.PullListView.OnRefreshListener;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

public class UserCheckActivity extends BaseActivity implements OnRefreshListener{
	private PullListView pullListView;
	private UserCheckAdapter adapter;

	@Override
	protected void localOnCreate(Bundle savedInstanceState) {
		setContentView(R.layout.activity_user_check);
		setTitle(R.string.bill);
		getLeftImageView().setImageDrawable(
				getResources().getDrawable(R.drawable.back));
//		setTitleBackGround(getResources().getColor(R.color.login_title_layout_back));
		initView();
		progressDialog.progressDialog();
		getData();
	}

	@Override
	protected void onLeftImageViewClick(View view) {
		super.onLeftImageViewClick(view);
		finish();
	}

	private void initView() {
		pullListView = (PullListView) findViewById(R.id.pull_listView);
		View heaed = LayoutInflater.from(this).inflate(R.layout.view_check_top, null);
//		pullListView.addHeaderView(heaed);
		pullListView.setRefresh(true);
		pullListView.setLoadMore(false);
		adapter = new UserCheckAdapter(this);
		pullListView.setAdapter(adapter);
		pullListView.setonRefreshListener(this);
		pullListView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				if(position < 1) return;
				UserCheckDAO item = (UserCheckDAO) adapter.getItem(position-1);
				Intent intent = new Intent(UserCheckActivity.this, EventDetailActivity.class);
				intent.putExtra("eventId", item.getEventId()+"");
				startActivity(intent);
			}
		});
	}

	// 获取对账单
	private void getData() {
		httpResponseUtils.postJson(httpPostParams.getPostParams(
				PostMethod.query_user_check.name(), PostType.user_info.name(),
				httpPostParams.query_user_check(preferenceUtil.getID()+"",preferenceUtil.getUUid())), UserCheckInfo.class,
				new PostCommentResponseListener() {
					@Override
					public void requestCompleted(Object response)
							throws JSONException {
						progressDialog.cancleProgress();
						pullListView.onRefreshComplete();
						if (response == null)
							return;
						UserCheckInfo userCheckInfo = (UserCheckInfo) response;
						adapter.setList(userCheckInfo.getChecks());
					}
				});
	}

	@Override
	public void onRefresh(boolean isTop) {
		// TODO Auto-generated method stub
		if(isTop){
			getData();
		}
	}
}

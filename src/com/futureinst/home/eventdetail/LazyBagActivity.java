package com.futureinst.home.eventdetail;

import org.json.JSONException;

import android.os.Bundle;
import android.view.View;

import com.futureinst.R;
import com.futureinst.baseui.BaseActivity;
import com.futureinst.model.homeeventmodel.LazyBagInfo;
import com.futureinst.model.homeeventmodel.QueryEventDAO;
import com.futureinst.net.PostCommentResponseListener;
import com.futureinst.net.PostMethod;
import com.futureinst.net.PostType;
import com.futureinst.net.SingleEventScope;
import com.futureinst.widget.list.PullListView;

public class LazyBagActivity extends BaseActivity {
	private PullListView pullListView;
	private LazyBagAdapter adapter;
	private QueryEventDAO event;
	@Override
	protected void localOnCreate(Bundle savedInstanceState) {
		setContentView(R.layout.pull_listview);
		setTitle(getResources().getString(R.string.lazy_bag));
		getLeftImageView().setImageDrawable(getResources().getDrawable(R.drawable.back));
//		setTitleBackGround(getResources().getColor(R.color.login_title_layout_back));
		initView();
		getLazyBagData(event.getId()+"");
//		getLazyBagData("1003");
	}
	@Override
	protected void onLeftImageViewClick(View view) {
		super.onLeftImageViewClick(view);
		finish();
	}
	private void initView() {
		event = (QueryEventDAO) getIntent().getSerializableExtra("event");
		pullListView = (PullListView) findViewById(R.id.pull_listView);
		pullListView.setDividerHeight(0);
		adapter = new LazyBagAdapter(this);
		pullListView.setAdapter(adapter);
		pullListView.setRefresh(false);
		pullListView.setLoadMore(false);
		
	}
	//获取懒人包数据
		private void getLazyBagData(String event_id){
			progressDialog.progressDialog();
			httpResponseUtils.postJson(httpPostParams.getPostParams(
					PostMethod.query_single_event.name(), PostType.event.name(), 
					httpPostParams.query_single_event(event_id, SingleEventScope.lazybag.name())), 
					LazyBagInfo.class, 
					new PostCommentResponseListener() {
						@Override
						public void requestCompleted(Object response) throws JSONException {
							progressDialog.cancleProgress();
							if(response == null) return;
							LazyBagInfo lazyBagInfo = (LazyBagInfo) response;
							adapter.setList(lazyBagInfo.getLazybag().getBags());
						}
					});
		}
}

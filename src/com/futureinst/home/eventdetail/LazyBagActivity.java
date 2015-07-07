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
import com.futureinst.widget.scrollview.OverListView;

public class LazyBagActivity extends BaseActivity {
	private OverListView overListView;
	private LazyBagAdapter adapter;
	private QueryEventDAO event;
	@Override
	protected void localOnCreate(Bundle savedInstanceState) {
		setContentView(R.layout.view_over_listview);
		setTitle(getResources().getString(R.string.lazy_bag));
		getLeftImageView().setImageDrawable(getResources().getDrawable(R.drawable.back));
		initView();
		getLazyBagData(event.getId()+"");
	}
	@Override
	protected void onLeftImageViewClick(View view) {
		super.onLeftImageViewClick(view);
		finish();
	}
	private void initView() {
		event = (QueryEventDAO) getIntent().getSerializableExtra("event");
		overListView =  (OverListView) findViewById(R.id.overListView);
		overListView.setDividerHeight(0);

		adapter = new LazyBagAdapter(this);
		overListView.setAdapter(adapter);
		
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

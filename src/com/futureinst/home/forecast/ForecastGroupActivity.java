package com.futureinst.home.forecast;

import org.json.JSONException;

import com.futureinst.R;
import com.futureinst.baseui.BaseActivity;
import com.futureinst.home.SystemTimeUtile;
import com.futureinst.home.eventdetail.EventDetailActivity;
import com.futureinst.model.homeeventmodel.QueryEventDAO;
import com.futureinst.model.homeeventmodel.QueryEventInfoDAO;
import com.futureinst.net.HttpPostParams;
import com.futureinst.net.HttpResponseUtils;
import com.futureinst.net.PostCommentResponseListener;
import com.futureinst.net.PostMethod;
import com.futureinst.net.PostType;
import com.futureinst.push.PushWebActivity;
import com.futureinst.widget.list.PullListView;
import com.futureinst.widget.list.PullListView.OnRefreshListener;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class ForecastGroupActivity extends BaseActivity implements OnRefreshListener{
	private PullListView pullListView;
	private ForecastItemAdapter adapter;
	private String group_id;
	private String title;
	private int page = 1;
	private String last_id = "0";
	@Override
	protected void localOnCreate(Bundle savedInstanceState) {
		getLeftImageView().setImageDrawable(getResources().getDrawable(R.drawable.back));
		setContentView(R.layout.pull_listview_2);
		setTitle(R.string.company_name);
		initView();
		getData(page,last_id,group_id);
	}
	private void initView() {
		// TODO Auto-generated method stub
		group_id = getIntent().getStringExtra("group_id");
		title = getIntent().getStringExtra("title");
		if(title.length() > 15){
			title = title.substring(0, 15) + "...";
		}
		if(!TextUtils.isEmpty(title)){
			setTitle(title);
		}
//        getRightImageView().setImageDrawable(getResources().getDrawable(R.drawable.sort));
		pullListView = (PullListView) findViewById(R.id.id_stickynavlayout_innerscrollview);
		adapter = new ForecastItemAdapter(this);
		pullListView.setAdapter(adapter);
		pullListView.setonRefreshListener(this);
		pullListView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int index, long id) {
				if(index < 1) return;
				QueryEventDAO item = (QueryEventDAO) adapter.getItem(index - 1);
				if(item.getType() == 1){//专题
					Intent intent = new Intent(ForecastGroupActivity.this, ForecastGroupActivity.class);
					intent.putExtra("group_id", item.getId()+"");
					intent.putExtra("title", item.getTitle());
					startActivity(intent);
				}else if(item.getType() == 2){//广告
					Intent intent = new Intent(ForecastGroupActivity.this, PushWebActivity.class);
					intent.putExtra("url", item.getLead());
					intent.putExtra("title", item.getTitle());
					startActivity(intent);
				}else{
					//预测
					Intent intent = new Intent(ForecastGroupActivity.this, EventDetailActivity.class);
					intent.putExtra("eventId", item.getId()+"");
					startActivity(intent);
				}
			}
		});
	}

    @Override
    protected void onRightImageViewClick(View view) {
        super.onRightImageViewClick(view);

    }

    @Override
	public void onRefresh(boolean isTop) {
		if(isTop){
			page = 1;
			last_id = "0";
		}else{
			page++;
			if(adapter.getList() !=null && adapter.getList().size()>0){
					last_id = adapter.getList().get(adapter.getCount()-1).getId()+"";
			}
		}
		getData(page,last_id,group_id);
	}
	Handler handler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 111:
				adapter.notifyDataSetChanged();
				break;
			case 0:
				Toast.makeText(getApplicationContext(), getResources().getString(R.string.data_over), Toast.LENGTH_SHORT).show();
				break;
			}
		}
    };
	boolean flag = false;
	private void notifyDate(){
		if(flag) return;
		flag = true;
		new Thread(new Runnable() {
			public void run() {
				while(flag){
					handler.sendEmptyMessage(111);
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}).start();
	}
	
	//获取事件数据
		 private void getData(final int page,String last_id,String group_id){
			 HttpResponseUtils.getInstace(this).postJson(
					 HttpPostParams.getInstace().getPostParams(PostMethod.query_event_all.name(), PostType.event.name(),
                             HttpPostParams.getInstace().query_event(page,last_id,group_id)),
					 QueryEventInfoDAO.class, 
					 new PostCommentResponseListener() {
							@Override
							public void requestCompleted(Object response) throws JSONException {
								pullListView.onRefreshComplete();
								if(response == null) return;
								QueryEventInfoDAO queryEventInfoDAO = (QueryEventInfoDAO) response;
								SystemTimeUtile.getInstance(queryEventInfoDAO.getCurr_time()).setSystemTime(queryEventInfoDAO.getCurr_time());
								
								if(page ==1){
									adapter.refresh(queryEventInfoDAO.getEvents(),queryEventInfoDAO.getCommentMap());
								}else{
									adapter.setList(queryEventInfoDAO.getEvents(),queryEventInfoDAO.getCommentMap());
								}
								
								if(adapter.getCount() > 9){
									pullListView.setLoadMore(true);
								}else{
									pullListView.setLoadMore(false);
								}
								if(page!=1 && (queryEventInfoDAO.getEvents() == null || queryEventInfoDAO.getEvents().size() ==0)){
									handler.sendEmptyMessage(0);
									pullListView.setLoadMore(false);
								}
								notifyDate();
							}
					});
		 }
	@Override
	public void onPause() {
		super.onPause();
		flag = false;
	}
	@Override
	public void onDestroy() {
		super.onDestroy();
		flag = false;
	}
}

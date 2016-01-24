package com.futureinst.home.pushmessage;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TableRow;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;

import com.futureinst.R;
import com.futureinst.baseui.BaseActivity;
import com.futureinst.comment.CommentActivity;
import com.futureinst.db.PushMessageCacheUtil;
import com.futureinst.global.Content;
import com.futureinst.home.eventdetail.EventDetailActivity;
import com.futureinst.home.find.NewsUtil;
import com.futureinst.home.forecast.ForecastGroupActivity;
import com.futureinst.model.homeeventmodel.QueryEventDAO;
import com.futureinst.model.homeeventmodel.SingleEventInfoDAO;
import com.futureinst.model.push.PushMessageDAO;
import com.futureinst.net.PostCommentResponseListener;
import com.futureinst.net.PostMethod;
import com.futureinst.net.PostType;
import com.futureinst.net.SingleEventScope;
import com.futureinst.personalinfo.other.PersonalShowActivity;
import com.futureinst.push.PushWebActivity;

public class PushMessageActivity extends BaseActivity {
	private ListView lv_pushmessage;
	private PushMessageAdapter adapter;
	private List<PushMessageDAO> list;
	private PushMessageCacheUtil messageCacheUtil;
	private boolean push;
	private PushMessageDAO pushMessageDAO;
    private TableRow[] tableRows;
	@Override
	protected void localOnCreate(Bundle savedInstanceState) {
		setContentView(R.layout.activity_push_message);
		setTitle(R.string.pushMessage);
		getLeftImageView().setImageDrawable(getResources().getDrawable(R.drawable.back));
		initView();
		messageCacheUtil.updateMesaage(null);
	}
	@Override
	protected void onLeftImageViewClick(View view) {
		super.onLeftImageViewClick(view);
		finish();
	}
	private void initView() {
        View view_top_message = LayoutInflater.from(this).inflate(R.layout.view_top_pushmessage, null);
		push = getIntent().getBooleanExtra("push", false);
		pushMessageDAO = (PushMessageDAO) getIntent().getSerializableExtra("pushMessage");
		messageCacheUtil = PushMessageCacheUtil.getInstance(this);
		list = new ArrayList<>();
		list = messageCacheUtil.getPushMessage();
//			Log.i(TAG, "-----------pushMessageInfo-->>"+list);
		lv_pushmessage = (ListView) findViewById(R.id.lv_pushmessage);
        lv_pushmessage.addHeaderView(view_top_message);
        adapter = new PushMessageAdapter(this);
        lv_pushmessage.setAdapter(adapter);
        adapter.setList(list);
		lv_pushmessage.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(position > 0){
                    PushMessageDAO item = (PushMessageDAO) adapter.getItem(position-1);
                    list.get(position-1).setRead(true);
                    itemClick(item);
                }
			}
		});
		if(push){
			itemClick(pushMessageDAO);
		}

        tableRows = new TableRow[3];
        tableRows[0] = (TableRow) findViewById(R.id.tableRow_acount);
        tableRows[1] = (TableRow) findViewById(R.id.tableRow_fans);
        tableRows[2] = (TableRow) findViewById(R.id.tableRow_comment);
        tableRows[0].setOnClickListener(onClickListener);
        tableRows[1].setOnClickListener(onClickListener);
        tableRows[2].setOnClickListener(onClickListener);
	}
    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(PushMessageActivity.this,PushMessageClassifyActivity.class);
            switch (v.getId()){
                case R.id.tableRow_acount://账户
                    intent.putExtra("type",0);
                    break;
                case R.id.tableRow_fans://粉丝
                    intent.putExtra("type",1);
                    break;
                case R.id.tableRow_comment://赞与评论
                    intent.putExtra("type",2);
                    break;
            }
            startActivity(intent);
        }
    };

	//点击事件
	public void itemClick(PushMessageDAO item){
		if(item.getType() == null){//专题或广告
			if(item.getEvent_id()!=null){
				query_single_event(item.getEvent_id());
			}
		}
		else{
			if(item.getType().equals("event")
                    || item.getType().equals("deal") ){//事件详情
				Intent intent = new Intent(PushMessageActivity.this, EventDetailActivity.class);
				intent.putExtra("eventId", item.getEvent_id() + "");
				startActivity(intent);
			}else if(item.getType().equals("comment")){//评论
				Intent intent = new Intent(PushMessageActivity.this, CommentActivity.class);
				intent.putExtra("eventId", item.getEvent_id() + "");
				startActivity(intent);
			}
			else if(item.getType().equals("rank")){//排名
				Intent intent = new Intent("rank");
				sendBroadcast(intent);
				finish();
			}
			else if(item.getType().equals("url")){//打开指定网页
                NewsUtil newsUtil = new NewsUtil();
                if(!newsUtil.clickListener(this,item.getHref())){
                    Intent intent = new Intent(PushMessageActivity.this, PushWebActivity.class);
                    intent.putExtra("url", item.getHref());
                    intent.putExtra("title", "");
                    startActivity(intent);
                }
			}
			else if(item.getType().equals("follow_me")){//关注通知
				Intent intent = new Intent(PushMessageActivity.this,PersonalShowActivity.class);
				intent.putExtra("id", item.getPeer_id());
				startActivity(intent);
			}else {
                if(item.getEvent_id()!=null){//事件详情页
                    Intent intent = new Intent(PushMessageActivity.this, EventDetailActivity.class);
                    intent.putExtra("eventId", item.getEvent_id() + "");
                    startActivity(intent);
                }
            }
		}
        adapter.notifyDataSetChanged();
	}
	
	private void query_single_event(String event_id){
		progressDialog.progressDialog();
		Content.isPull = true;
		httpResponseUtils.postJson(httpPostParams.getPostParams(
				PostMethod.query_single_event.name(), PostType.event.name(), 
				httpPostParams.query_single_event(event_id, SingleEventScope.base.name())), 
				SingleEventInfoDAO.class, 
				new PostCommentResponseListener() {
					@Override
					public void requestCompleted(Object response) throws JSONException {
						progressDialog.cancleProgress();
						Content.isPull = false;
						if(response == null) return;
						SingleEventInfoDAO queryEventInfoDAO = (SingleEventInfoDAO) response;
						QueryEventDAO event = queryEventInfoDAO.getEvent();
						operate(event);
					}
				});
	
	}
	
	private void operate(QueryEventDAO item){
		if(item.getType() == 1){//专题
			Intent intent = new Intent(this, ForecastGroupActivity.class);
			intent.putExtra("group_id", item.getId()+"");
			intent.putExtra("title", item.getTitle());
			startActivity(intent);
		}else if(item.getType() == 2){//广告
            NewsUtil newsUtil = new NewsUtil();
            if(!newsUtil.clickListener(this,item.getLead())){
                Intent intent = new Intent(this, PushWebActivity.class);
                intent.putExtra("url", item.getLead());
                intent.putExtra("title", item.getTitle());
                startActivity(intent);
            }
		}else{
			//预测
			Intent intent = new Intent(this, EventDetailActivity.class);
			intent.putExtra("eventId", item.getId()+"");
			startActivity(intent);
		}
	}
	@Override
	protected void onDestroy() {
		super.onDestroy();
	}
}

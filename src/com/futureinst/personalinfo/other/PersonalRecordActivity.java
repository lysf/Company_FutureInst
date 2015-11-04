package com.futureinst.personalinfo.other;

import java.util.List;

import org.json.JSONException;

import com.futureinst.R;
import com.futureinst.baseui.BaseActivity;
import com.futureinst.home.find.RecordAdapter;
import com.futureinst.model.record.RecordDAO;
import com.futureinst.model.record.RecordInofDAO;
import com.futureinst.model.record.UserRecordDAO;
import com.futureinst.model.record.UserRecordInfoDAO;
import com.futureinst.net.PostCommentResponseListener;
import com.futureinst.net.PostMethod;
import com.futureinst.net.PostType;
import com.futureinst.utils.MyProgressDialog;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

public class PersonalRecordActivity extends BaseActivity {
	private String id;
	private MyProgressDialog progressDialog;
	private TextView tv_gainTotal,tv_avgGain,tv_gainNumber,tv_odds;
	private TextView tv_type_record;
	private ListView lv_record;
	private RecordAdapter adapter;
	private String[] types;
	private boolean isMe;
	private UserRecordDAO recordDAO;
	private LinearLayout ll_total;
	@Override
	protected void localOnCreate(Bundle savedInstanceState) {
		setContentView(R.layout.activity_personal_record);
		setTitle("Ta的战绩");
		getLeftImageView().setImageDrawable(getResources().getDrawable(R.drawable.back));
		initView();
		if(isMe){
			setTitle("我的战绩");
			query_user_record();
			query_user_tag_record();
		}else{
//			tv_type_record.setVisibility(View.GONE);
//			ll_total.setVisibility(View.GONE);
			peer_info_query_user_tag_record(id);
			initTotalRecordView(recordDAO);
		}
	}
	
	private void initView() {
		isMe = getIntent().getBooleanExtra("isMe", false);
		types = getResources().getStringArray(R.array.record_tag);
		id = getIntent().getStringExtra("id");
		if(!isMe){
			recordDAO = (UserRecordDAO) getIntent().getSerializableExtra("record");
		}
		progressDialog = MyProgressDialog.getInstance(this);
		
		tv_gainTotal = (TextView) findViewById(R.id.tv_gainTotal);
		tv_avgGain = (TextView) findViewById(R.id.tv_avgGain);
		tv_gainNumber = (TextView) findViewById(R.id.tv_gainNumber);
		tv_odds = (TextView) findViewById(R.id.tv_odds);
		tv_type_record = (TextView) findViewById(R.id.tv_type_record);
		ll_total = (LinearLayout) findViewById(R.id.ll_total);
		lv_record = (ListView) findViewById(R.id.lv_record);
		adapter = new RecordAdapter(this);
		lv_record.setAdapter(adapter);
		ll_total.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(PersonalRecordActivity.this, PersonalTagRecordActivity.class);
				intent.putExtra("title", "总战绩");
				intent.putExtra("type", 0);
				intent.putExtra("id", id);
				intent.putExtra("isMe", isMe);
				startActivity(intent);
			}
		});
		lv_record.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long ids) {
				RecordDAO item = (RecordDAO) adapter.getItem(position);
				Intent intent = new Intent(PersonalRecordActivity.this, PersonalTagRecordActivity.class);
				intent.putExtra("title", types[item.getTag() - 1]+" — 战绩");
				intent.putExtra("type", item.getTag());
				intent.putExtra("id", id);
				intent.putExtra("isMe", isMe);
				startActivity(intent);
			}
		});
	}
	
	private void initTotalRecordView(UserRecordDAO userRecord){
		tv_gainTotal.setText(String.format("%.2f", userRecord.getAllGain()));
		tv_avgGain.setText(String.format("%.2f", userRecord.getAvgGain()));
		tv_gainNumber.setText(userRecord.getGainEvent()+"");
		if(userRecord.getAllEvent() == 0){
			tv_odds.setText("0");
		}else{
			tv_odds.setText((int)(userRecord.getGainEvent()*1.0f/userRecord.getAllEvent()*100)+"%");
		}
	}
	private void initListView(List<RecordDAO> list){
		if(list == null || list.size() == 0){
			tv_type_record.setVisibility(View.GONE);
			return;
		}
		adapter.setList(list);
	}
	//查询个人总战绩
		private void query_user_record(){
			progressDialog.progressDialog();
			httpResponseUtils.postJson(httpPostParams.getPostParams(PostMethod.query_user_record.name(), PostType.user_info.name(),
					httpPostParams.query_user_record(preferenceUtil.getID()+"", preferenceUtil.getUUid())), 
					UserRecordInfoDAO.class, 
					new PostCommentResponseListener() {
						@Override
						public void requestCompleted(Object response) throws JSONException {
							progressDialog.cancleProgress();
							if(response == null) return;
							UserRecordInfoDAO userRecordInfoDAO = (UserRecordInfoDAO) response;
							initTotalRecordView(userRecordInfoDAO.getUser_record());
						}
					});
		}
		//查询个人分类战绩
		private void query_user_tag_record(){
			progressDialog.progressDialog();
			httpResponseUtils.postJson(httpPostParams.getPostParams(PostMethod.query_user_tag_record.name(), PostType.user_info.name(),
					httpPostParams.query_user_tag_record(preferenceUtil.getID()+"", preferenceUtil.getUUid())), 
					RecordInofDAO.class, 
					new PostCommentResponseListener() {
				@Override
				public void requestCompleted(Object response) throws JSONException {
					progressDialog.cancleProgress();
					if(response == null) return;
					RecordInofDAO recordInofDAO = (RecordInofDAO) response;
					initListView(recordInofDAO.getTag_records());
					
				}
			});
		}
		//查询制定用户的分类战绩 
		private void peer_info_query_user_tag_record (String peer_id){
			progressDialog.progressDialog();
			httpResponseUtils.postJson(httpPostParams.getPostParams(PostMethod.peer_info_query_user_tag_record.name(), PostType.peer_info.name(),
					httpPostParams.peer_info_query_user_tag_record(preferenceUtil.getUUid(),preferenceUtil.getID()+"",peer_id)), 
					RecordInofDAO.class, 
					new PostCommentResponseListener() {
				@Override
				public void requestCompleted(Object response) throws JSONException {
					progressDialog.cancleProgress();
					if(response == null) return;
					RecordInofDAO recordInofDAO = (RecordInofDAO) response;
					initListView(recordInofDAO.getTag_records());
				}
			});
		}
}

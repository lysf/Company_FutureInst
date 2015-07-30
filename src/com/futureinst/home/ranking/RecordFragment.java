package com.futureinst.home.ranking;

import java.util.List;

import org.json.JSONException;

import com.futureinst.R;
import com.futureinst.baseui.BaseFragment;
import com.futureinst.model.record.RecordDAO;
import com.futureinst.model.record.RecordInofDAO;
import com.futureinst.model.record.UserRecordDAO;
import com.futureinst.model.record.UserRecordInfoDAO;
import com.futureinst.net.HttpPostParams;
import com.futureinst.net.HttpResponseUtils;
import com.futureinst.net.PostCommentResponseListener;
import com.futureinst.net.PostMethod;
import com.futureinst.net.PostType;
import com.futureinst.sharepreference.SharePreferenceUtil;
import com.futureinst.utils.MyProgressDialog;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

public class RecordFragment extends BaseFragment {
	private SharePreferenceUtil preferences;
	private HttpResponseUtils httpResponseUtils;
	private HttpPostParams httpPostParams;
	private MyProgressDialog progressDialog;
	private TextView tv_gainTotal,tv_avgGain,tv_gainNumber,tv_odds;
	private TextView tv_type_record;
	private ListView lv_record;
	private RecordAdapter adapter;
	private boolean isStart;
	@Override
	protected void localOnCreate(Bundle savedInstanceState) {
		setContentView(R.layout.fragment_record);
		initView();
		query_user_record();
		query_user_tag_record();
	}

	private void initView() {
		preferences = SharePreferenceUtil.getInstance(getContext());
		httpResponseUtils = HttpResponseUtils.getInstace(getActivity());
		httpPostParams = HttpPostParams.getInstace();
		progressDialog = MyProgressDialog.getInstance(getContext());
		
		tv_gainTotal = (TextView) findViewById(R.id.tv_gainTotal);
		tv_avgGain = (TextView) findViewById(R.id.tv_avgGain);
		tv_gainNumber = (TextView) findViewById(R.id.tv_gainNumber);
		tv_odds = (TextView) findViewById(R.id.tv_odds);
		tv_type_record = (TextView) findViewById(R.id.tv_type_record);
		lv_record = (ListView) findViewById(R.id.lv_record);
		adapter = new RecordAdapter(getContext());
		lv_record.setAdapter(adapter);
		isStart = true;
	}
	@Override
	public void setUserVisibleHint(boolean isVisibleToUser) {
		if(isVisibleToUser && isStart){
			query_user_record();
			query_user_tag_record();
		}
		super.setUserVisibleHint(isVisibleToUser);
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
				httpPostParams.query_user_record(preferences.getID()+"", preferences.getUUid())), 
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
				httpPostParams.query_user_tag_record(preferences.getID()+"", preferences.getUUid())), 
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

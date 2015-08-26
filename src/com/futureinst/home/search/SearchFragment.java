package com.futureinst.home.search;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.json.JSONException;

import com.futureinst.R;
import com.futureinst.baseui.BaseFragment;
import com.futureinst.home.eventdetail.EventDetailActivity;
import com.futureinst.model.homeeventmodel.QueryEventDAO;
import com.futureinst.model.homeeventmodel.QueryEventInfoDAO;
import com.futureinst.net.HttpPostParams;
import com.futureinst.net.HttpResponseUtils;
import com.futureinst.net.PostCommentResponseListener;
import com.futureinst.net.PostMethod;
import com.futureinst.net.PostType;
import com.futureinst.utils.MyProgressDialog;

public class SearchFragment extends BaseFragment {
	private MyProgressDialog progressDialog;
	private EditText et_search;
	private Button btn_cancel;
	private GridView gd_search;
	private SearchAdapter adapter;
	private LinearLayout ll_search;
	private TextView emptyView;
	@Override
	protected void localOnCreate(Bundle savedInstanceState) {
		setContentView(R.layout.fragment_home_search);
		initView();
	}

	private void initView() {
		progressDialog = MyProgressDialog.getInstance(getContext());
		et_search = (EditText) findViewById(R.id.et_search);
		btn_cancel = (Button) findViewById(R.id.btn_cancel_search);
		gd_search = (GridView) findViewById(R.id.gd_search);
		ll_search = (LinearLayout) findViewById(R.id.ll_search);
		btn_cancel.setOnClickListener(clickListener);
		ll_search.setOnClickListener(clickListener);
		adapter = new SearchAdapter(getActivity());
		gd_search.setAdapter(adapter);
		
		et_search.setOnKeyListener(new OnKeyListener() {
			@Override
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				String keyword = et_search.getText().toString().trim();
				if(keyCode == KeyEvent.KEYCODE_ENTER && !TextUtils.isEmpty(keyword)){
					InputMethodManager imm = (InputMethodManager)v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);  
					if(imm.isActive()){  
					imm.hideSoftInputFromWindow(v.getApplicationWindowToken(), 0 );  
					}
					getData(keyword);
					return true;
				}
				return false;
			}
		});
		emptyView = (TextView) findViewById(R.id.empt_search);
		
		gd_search.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				QueryEventDAO item = (QueryEventDAO) adapter.getItem(position);
				Intent intent = new Intent(getActivity(), EventDetailActivity.class);
				intent.putExtra("eventId", item.getId()+"");
				startActivity(intent);
			}
		});
	}

	OnClickListener clickListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.btn_cancel_search:
				et_search.setText("");
				adapter.clearList();
				break;
			case R.id.ll_search:
				closeInput();
				break;
			}
		}
	};

	// 获取数据
	private void getData(String keyword) {
		progressDialog.progressDialog();
		HttpResponseUtils.getInstace(getActivity()).postJson(
				HttpPostParams.getInstace().getPostParams(PostMethod.search_event.name(), PostType.event.name(),
						HttpPostParams.getInstace().search_event(keyword)),
				QueryEventInfoDAO.class, new PostCommentResponseListener() {
					@Override
					public void requestCompleted(Object response) throws JSONException {
						progressDialog.cancleProgress();
						if (response == null) return;
						QueryEventInfoDAO queryEventInfoDAO = (QueryEventInfoDAO) response;
//						if(queryEventInfoDAO.getEvents().size() > 0 ){
							adapter.setList(queryEventInfoDAO.getEvents());
							gd_search.setEmptyView(emptyView);
//							gd_search.setAdapter(adapter);
					}
				});
	}
}

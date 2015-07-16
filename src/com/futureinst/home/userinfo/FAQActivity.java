package com.futureinst.home.userinfo;

import org.json.JSONException;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.futureinst.R;
import com.futureinst.baseui.BaseActivity;
import com.futureinst.model.usermodel.FaqDAO;
import com.futureinst.model.usermodel.FaqInfoDAO;
import com.futureinst.net.PostCommentResponseListener;
import com.futureinst.net.PostMethod;
import com.futureinst.net.PostType;

public class FAQActivity extends BaseActivity {
	private ListView overListView;
	private FAQAdapter adapter;
	@Override
	protected void localOnCreate(Bundle savedInstanceState) {
		setContentView(R.layout.view_over_listview);
		setTitle(R.string.FAQ);
		getLeftImageView().setImageDrawable(getResources().getDrawable(R.drawable.back));
		initView();
		getFAQ();
	}
	@Override
	protected void onLeftImageViewClick(View view) {
		super.onLeftImageViewClick(view);
		finish();
	}
	private void initView() {
		overListView = (ListView) findViewById(R.id.overListView);
		overListView.setDividerHeight(1);
		adapter = new FAQAdapter(this);
		overListView.setAdapter(adapter);
		overListView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position,
					long id) {
				FaqDAO item = (FaqDAO) adapter.getItem(position);
				Intent intent = new Intent(FAQActivity.this, FAQDetailActivity.class);
				intent.putExtra("faq", item);
				startActivity(intent);
			}
		});
	}
	//获取常见问题
	private void getFAQ(){
		progressDialog.progressDialog();
		httpResponseUtils.postJson(httpPostParams.getPostParams(PostMethod.get_faq.name(), PostType.common.name(), httpPostParams.get_faq()), 
				FaqInfoDAO.class,
				new PostCommentResponseListener() {
					@Override
					public void requestCompleted(Object response) throws JSONException {
						progressDialog.cancleProgress();
						if(response == null) return;
						FaqInfoDAO faqInfoDAO = (FaqInfoDAO) response;
						adapter.setList(faqInfoDAO.getFaqs());
					}
				});
	}
}

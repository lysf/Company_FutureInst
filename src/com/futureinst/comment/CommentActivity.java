package com.futureinst.comment;

import org.json.JSONException;

import com.futureinst.R;
import com.futureinst.baseui.BaseActivity;
import com.futureinst.model.comment.CommentInfoDAO;
import com.futureinst.model.homeeventmodel.QueryEventDAO;
import com.futureinst.net.PostCommentResponseListener;
import com.futureinst.net.PostMethod;
import com.futureinst.net.PostType;
import com.futureinst.widget.list.PullListView;
import com.futureinst.widget.list.PullListView.OnRefreshListener;

import android.os.Bundle;
import android.view.View;

public class CommentActivity extends BaseActivity implements OnRefreshListener{
	private PullListView lv_comment;
	private QueryEventDAO event;
	@Override
	protected void localOnCreate(Bundle savedInstanceState) {
		setTitle("评论");
		getLeftImageView().setImageDrawable(getResources().getDrawable(R.drawable.back));
		setContentView(R.layout.activity_comment);
		initView();
		getComment(event.getId()+"", 1);
	}
	@Override
	protected void onLeftImageViewClick(View view) { 
		super.onLeftImageViewClick(view);
		finish();
	}
	private void initView() {
		event = (QueryEventDAO) getIntent().getSerializableExtra("event");
		lv_comment = (PullListView) findViewById(R.id.listView_comment);
		lv_comment.setRefresh(true);
		lv_comment.setLoadMore(false);
		
	}
	@Override
	public void onRefresh(boolean isTop) {
		if(isTop){
			
		}
		
	}
	/**
	 * 获取评论
	 * @Title: getComment   
	 * @Description: TODO  
	 * @author: huihaoyan  
	 * @param: @param event_id
	 * @param: @param attitude 1 表示支持， 2 表示反对  
	 * @return: void      
	 * @throws
	 */
	private void getComment(String event_id,int attitude){
		httpResponseUtils.postJson(httpPostParams.getPostParams(PostMethod.query_comment.name(), PostType.comment.name(), 
				httpPostParams.query_comment(event_id, attitude)),
				CommentInfoDAO.class,
				new PostCommentResponseListener() {
					@Override
					public void requestCompleted(Object response) throws JSONException {
						lv_comment.onRefreshComplete();
						progressDialog.cancleProgress();
						if(response == null) return;
						CommentInfoDAO commentInfoDAO = (CommentInfoDAO) response;
						
					}
				});
	}
}

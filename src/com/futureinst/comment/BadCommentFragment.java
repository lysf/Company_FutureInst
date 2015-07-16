package com.futureinst.comment;

import org.json.JSONException;

import com.futureinst.R;
import com.futureinst.baseui.BaseFragment;
import com.futureinst.model.comment.CommentInfoDAO;
import com.futureinst.net.HttpPostParams;
import com.futureinst.net.HttpResponseUtils;
import com.futureinst.net.PostCommentResponseListener;
import com.futureinst.net.PostMethod;
import com.futureinst.net.PostType;
import com.futureinst.utils.MyProgressDialog;
import com.futureinst.widget.list.PullListView;
import com.futureinst.widget.list.PullListView.OnRefreshListener;

import android.os.Bundle;
import android.view.View;

public class BadCommentFragment extends BaseFragment implements OnRefreshListener{
	private HttpResponseUtils httpResponseUtils;
	private HttpPostParams httpPostParams;
	private MyProgressDialog progressDialog;
	private PullListView lv_comment;
	private String event_id;
	private CommentDetailAdapter adapter;
	@Override
	protected void localOnCreate(Bundle savedInstanceState) {
		setContentView(R.layout.fragment_detail_comment);
		initView();
		progressDialog.progressDialog();
		getComment(event_id);
	}
	private void initView() {
		event_id = getArguments().getString("eventId");
		httpResponseUtils = HttpResponseUtils.getInstace(getActivity());
		httpPostParams = HttpPostParams.getInstace();
		progressDialog = MyProgressDialog.getInstance(getContext());
		lv_comment = (PullListView) findViewById(R.id.listView_comment);
		lv_comment.setRefresh(true);
		lv_comment.setLoadMore(false);
		adapter = new CommentDetailAdapter(getContext(), false);
		lv_comment.setAdapter(adapter);
		View emptyView = findViewById(R.id.view_empty);
		lv_comment.setEmptyView(emptyView);
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
	private void getComment(String event_id){
		httpResponseUtils.postJson(httpPostParams.getPostParams(PostMethod.query_comment.name(), PostType.comment.name(), 
				httpPostParams.query_comment(event_id, 2)),
				CommentInfoDAO.class,
				new PostCommentResponseListener() {
					@Override
					public void requestCompleted(Object response) throws JSONException {
						lv_comment.onRefreshComplete();
						progressDialog.cancleProgress();
						if(response == null) return;
						CommentInfoDAO commentInfoDAO = (CommentInfoDAO) response;
						adapter.setList(commentInfoDAO.getComments());
					}
				});
	}
	@Override
	public void onRefresh(boolean isTop) {
		if(isTop){
			getComment(event_id);
		}
	}
}

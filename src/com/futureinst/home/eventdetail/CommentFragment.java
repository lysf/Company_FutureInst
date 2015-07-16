package com.futureinst.home.eventdetail;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;

import com.futureinst.R;
import com.futureinst.baseui.BaseFragment;
import com.futureinst.comment.AddCommentActivity;
import com.futureinst.comment.CommentActivity;
import com.futureinst.model.comment.CommentDAO;
import com.futureinst.model.homeeventmodel.EventRelatedInfo;
import com.futureinst.net.HttpPostParams;
import com.futureinst.net.HttpResponseUtils;
import com.futureinst.net.PostCommentResponseListener;
import com.futureinst.net.PostMethod;
import com.futureinst.net.PostType;
import com.futureinst.net.SingleEventScope;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;

public class CommentFragment extends BaseFragment {
	private HttpResponseUtils httpResponseUtils;
	private HttpPostParams httpPostParams;
	private LinearLayout ll_comment;
	private String event_id;
	private TextView tv_addComment;
	private TextView tv_moreComment;
	private LinearLayout[] ll_goodComments;
	private LinearLayout[] ll_badComments;
	private List<CommentDAO> list;
	private boolean isVisiable;
	@Override
	protected void localOnCreate(Bundle savedInstanceState) {
		setContentView(R.layout.fragment_comment);
		initView();
		getEvetnRealted();
		isVisiable = true;
	}
	@Override
	public void setUserVisibleHint(boolean isVisibleToUser) {
		super.setUserVisibleHint(isVisibleToUser);
		if(isVisibleToUser && isVisiable){
			getEvetnRealted();   
		}
	}
	private void initView() {
		event_id = getArguments().getString("eventId");

		httpResponseUtils = HttpResponseUtils.getInstace(getActivity());
		httpPostParams = HttpPostParams.getInstace();

		ll_comment = (LinearLayout) findViewById(R.id.ll_comment);
		ll_comment.setOnClickListener(clickListener);
		tv_addComment = (TextView) findViewById(R.id.tv_addComment);
		tv_addComment.setOnClickListener(clickListener);
		tv_moreComment = (TextView)findViewById(R.id.tv_moreComment);
		tv_moreComment.setOnClickListener(clickListener);
		
		ll_goodComments = new LinearLayout[2];
		ll_badComments = new LinearLayout[2];
		ll_goodComments[0] = (LinearLayout) findViewById(R.id.ll_comment_1);
		ll_goodComments[1] = (LinearLayout) findViewById(R.id.ll_comment_3);
		ll_badComments[0] = (LinearLayout) findViewById(R.id.ll_comment_2);
		ll_badComments[1] = (LinearLayout) findViewById(R.id.ll_comment_4);
	}

	OnClickListener clickListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.ll_comment:
				closeInput();
				break;
			case R.id.tv_addComment:
				Intent intent = new Intent(getActivity(), AddCommentActivity.class);
				intent.putExtra("eventId", event_id);
				startActivity(intent);
				break;
			case R.id.tv_moreComment://更多评论
				Intent intent2 = new Intent(getActivity(), CommentActivity.class);
				intent2.putExtra("eventId", event_id);
				startActivity(intent2);
				break;
			}
		}
	};

	// 获取事件相关信息
	private void getEvetnRealted() {
		httpResponseUtils.postJson(
				httpPostParams.getPostParams(PostMethod.query_single_event.name(), PostType.event.name(),
						httpPostParams.query_single_event(event_id, SingleEventScope.related.name())),
				EventRelatedInfo.class, new PostCommentResponseListener() {
					@Override
					public void requestCompleted(Object response) throws JSONException {
						if (response == null)
							return;
						EventRelatedInfo eventRelatedInfo = (EventRelatedInfo) response;
						if(eventRelatedInfo.getRelated().getComment().getSize() == 0) return;
						list = eventRelatedInfo.getRelated().getComment().getComments();
						formatCommentList(list);
					}
				});
	}
	private void formatCommentList(List<CommentDAO> list){
		List<CommentDAO> goodList = new ArrayList<CommentDAO>();
		List<CommentDAO> badList = new ArrayList<CommentDAO>();
		for(CommentDAO comment : list){
			if(comment.getAttitude() == 1){//看好
				goodList.add(comment);
			}else if(comment.getAttitude() == 2){//不看好
				badList.add(comment);
			}
		}
		for(int i = 0;i<goodList.size();i++){
			if(i>=2) return;
			ll_goodComments[i].removeAllViews();
			View view = LayoutInflater.from(getContext()).inflate(R.layout.view_comment, null);
			TextView tv_name = (TextView) view.findViewById(R.id.tv_name);
			TextView tv_comment = (TextView) view.findViewById(R.id.tv_comment);
			tv_name.setText("@"+goodList.get(i).getUser().getName());
			tv_comment.setText(goodList.get(i).getContent());
			ll_goodComments[i].addView(view);
		}
		for(int i = 0;i<badList.size();i++){
			if(i>=2) return;
			ll_badComments[i].removeAllViews();
			View view = LayoutInflater.from(getContext()).inflate(R.layout.view_comment, null);
			TextView tv_name = (TextView) view.findViewById(R.id.tv_name);
			TextView tv_comment = (TextView) view.findViewById(R.id.tv_comment);
			tv_name.setText("@"+badList.get(i).getUser().getName());
			tv_comment.setText(badList.get(i).getContent());
			ll_badComments[i].addView(view);
		}
	}
	public void upDate() {
		getEvetnRealted();
	}
}

package com.futureinst.personalinfo.other;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;

import com.futureinst.R;
import com.futureinst.baseui.BaseActivity;
import com.futureinst.model.record.UserRecordDAO;
import com.futureinst.model.usermodel.FollowInfoDAO;
import com.futureinst.net.PostCommentResponseListener;
import com.futureinst.net.PostMethod;
import com.futureinst.net.PostType;
import com.futureinst.widget.list.PullListView;
import com.futureinst.widget.list.PullListView.OnRefreshListener;

import android.os.Bundle;
import android.widget.TextView;
//我关注
public class PersonalAttentionActivity extends BaseActivity implements OnRefreshListener{
	private PersonalAttentionAdapter adapter;
	private PullListView pullListView;
	private boolean isMe;
	private TextView view_empty;
	private String id;
	
	@Override
	protected void localOnCreate(Bundle savedInstanceState) {
		setTitle(R.string.show_attention);
		getLeftImageView().setImageDrawable(getResources().getDrawable(R.drawable.back));
		setContentView(R.layout.view_pull_list_atten);
		initView();
		onRefresh(true);
	}
	private void initView() {
		isMe = getIntent().getBooleanExtra("isMe", false);
		id = getIntent().getStringExtra("id");
		pullListView = (PullListView) findViewById(R.id.pull_listView);
		pullListView.setonRefreshListener(this);
		adapter = new PersonalAttentionAdapter(this);
		pullListView.setAdapter(adapter);
		view_empty = (TextView) findViewById(R.id.view_empty);
		pullListView.setEmptyView(view_empty);
		
	}
	//查询我关注的人
	private void query_me_follow(final int page,String last_id){
		httpResponseUtils.postJson(httpPostParams.getPostParams(PostMethod.query_me_follow.name(), PostType.peer.name(),
				httpPostParams.query_me_follow(preferenceUtil.getUUid(), preferenceUtil.getID()+"",page,last_id)),
				FollowInfoDAO.class,
				new PostCommentResponseListener() {
					@Override
					public void requestCompleted(Object response) throws JSONException {
						pullListView.onRefreshComplete();
						if(response == null ) return;
						FollowInfoDAO followInfo = (FollowInfoDAO) response;
						List<UserRecordDAO> list = new ArrayList<UserRecordDAO>();
						list.addAll(followInfo.getFollows());
						list.addAll(followInfo.getFriends());
						
						if(page == 1){
							adapter.refresh(list);
						}else{
							adapter.setList(list);
						}
						if(adapter.getCount()>9){
							pullListView.setLoadMore(true);
						}else{
							pullListView.setLoadMore(false);
						}
						if(page!=1 && (list == null || list.size() ==0)){
							handler.sendEmptyMessage(0);
							pullListView.setLoadMore(false);
						}
					}
				});
	}
	//查询他关注的人
	private void peer_info_query_me_follow(String peer_id,final int page,String last_id){
		httpResponseUtils.postJson(httpPostParams.getPostParams(PostMethod.peer_info_query_me_follow.name(), PostType.peer_info.name(),
				httpPostParams.peer_info_query_me_follow(preferenceUtil.getUUid(), preferenceUtil.getID()+"",peer_id,page,last_id)),
				FollowInfoDAO.class,
				new PostCommentResponseListener() {
			@Override
			public void requestCompleted(Object response) throws JSONException {
				pullListView.onRefreshComplete();
				if(response == null ) return;
				FollowInfoDAO followInfo = (FollowInfoDAO) response;
				List<UserRecordDAO> list = new ArrayList<UserRecordDAO>();
				list.addAll(followInfo.getFollows());
				list.addAll(followInfo.getFriends());
				if(page!=1 && (list == null || list.size() ==0)){
					handler.sendEmptyMessage(0);
				}
				if(page == 1){
					adapter.refresh(list);
				}else{
					adapter.setList(list);
				}
				if(adapter.getCount()>9){
					pullListView.setLoadMore(true);
				}else{
					pullListView.setLoadMore(false);
				}
			}
		});
	}
	@Override
	public void onRefresh(boolean isTop) {
		// TODO Auto-generated method stub
		if(isTop){
			page = 1;
			last_id = "0";
		}else{
			page++;
			if(adapter.getList() !=null && adapter.getCount() > 0){
				last_id = adapter.getList().get(adapter.getCount()-1).getUserId()+"";
			}
		}
		if(isMe){
			setTitle("我关注的人");
			query_me_follow(page,last_id);
		}else{
			peer_info_query_me_follow(id,page,last_id);
		}
	}
	
	
}

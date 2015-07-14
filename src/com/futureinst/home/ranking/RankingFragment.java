package com.futureinst.home.ranking;

import org.json.JSONException;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.futureinst.R;
import com.futureinst.baseui.BaseFragment;
import com.futureinst.model.usermodel.RankInfo;
import com.futureinst.model.usermodel.UserInformationDAO;
import com.futureinst.model.usermodel.UserInformationInfo;
import com.futureinst.net.HttpPostParams;
import com.futureinst.net.HttpResponseUtils;
import com.futureinst.net.PostCommentResponseListener;
import com.futureinst.net.PostMethod;
import com.futureinst.net.PostType;
import com.futureinst.sharepreference.SharePreferenceUtil;
import com.futureinst.widget.list.PullListView;

public class RankingFragment extends BaseFragment {
	private SharePreferenceUtil preferenceUtil;
	private HttpResponseUtils httpResponseUtils;
	private HttpPostParams httpPostParams;
	private PullListView pullListView;
	private TextView tv_userName,tv_prophet,tv_ranking;
	private ImageView iv_ranking;
	private RankingAdapter adapter;
	private Button[] buttons;
	@Override
	protected void localOnCreate(Bundle savedInstanceState) {
		setContentView(R.layout.fragment_home_ranking);
		initView();
		get_rank();
		query_user_record();
	}
	private void initView() {
		preferenceUtil =SharePreferenceUtil .getInstance(getContext());
		httpResponseUtils = HttpResponseUtils.getInstace(getActivity());
		httpPostParams = HttpPostParams.getInstace();
		pullListView = (PullListView) findViewById(R.id.pull_listView);
		adapter = new RankingAdapter(getContext());
		pullListView.setAdapter(adapter);
		tv_userName = (TextView) findViewById(R.id.tv_userName);
		tv_prophet = (TextView) findViewById(R.id.tv_prophet);
		tv_ranking = (TextView) findViewById(R.id.tv_ranking);
		iv_ranking = (ImageView) findViewById(R.id.iv_ranking);
		
	}
	private void initMyRanking(UserInformationDAO userInformationDAO){
		tv_userName.setText(userInformationDAO.getUser().getName());
		tv_prophet.setText(userInformationDAO.getForeIndex()+"");
		tv_ranking.setText(userInformationDAO.getRank()+"");
		if(userInformationDAO.getRank() > userInformationDAO.getLastRank()){
			iv_ranking.setImageDrawable(getResources().getDrawable(R.drawable.ranking_up));
		}else if(userInformationDAO.getRank() < userInformationDAO.getLastRank()){
			iv_ranking.setImageDrawable(getResources().getDrawable(R.drawable.ranking_down));
		}else{
			iv_ranking.setImageDrawable(getResources().getDrawable(R.drawable.ranking_balance_un));
		}
	}
	//获取排名
		private void get_rank(){
			httpResponseUtils.postJson(httpPostParams.getPostParams(
					PostMethod.get_rank.name(), PostType.common.name(), 
					httpPostParams.get_rank()), 
					RankInfo.class, 
					new PostCommentResponseListener() {
				@Override
				public void requestCompleted(Object response) throws JSONException {
					if(response == null) return;
					RankInfo rankInfo = (RankInfo) response;
					adapter.setList(rankInfo.getRanks());
				}
			});
		}
		//获取个人信息
		private void query_user_record(){
			httpResponseUtils.postJson(httpPostParams.getPostParams(
					PostMethod.query_user_record.name(), PostType.user_info.name(), 
					httpPostParams.query_user_record(preferenceUtil.getID()+"",preferenceUtil.getUUid())), 
					UserInformationInfo.class, 
					new PostCommentResponseListener() {
				@Override
				public void requestCompleted(Object response) throws JSONException {
					if(response == null) return;
					UserInformationInfo userInformationInfo = (UserInformationInfo) response;
					initMyRanking(userInformationInfo.getUser_record());
				}
			});
		}
}

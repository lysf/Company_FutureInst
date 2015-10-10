package com.futureinst.home.ranking;

import org.json.JSONException;

import android.content.Intent;
import android.os.Bundle;
import android.provider.SyncStateContract.Constants;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.futureinst.R;
import com.futureinst.baseui.BaseFragment;
import com.futureinst.home.HomeActivity;
import com.futureinst.model.record.UserRecordDAO;
import com.futureinst.model.usermodel.RankDAO;
import com.futureinst.model.usermodel.RankInfo;
import com.futureinst.model.usermodel.UserInformationDAO;
import com.futureinst.model.usermodel.UserInformationInfo;
import com.futureinst.net.HttpPostParams;
import com.futureinst.net.HttpResponseUtils;
import com.futureinst.net.PostCommentResponseListener;
import com.futureinst.net.PostMethod;
import com.futureinst.net.PostType;
import com.futureinst.personalinfo.other.PersonalShowActivity;
import com.futureinst.roundimageutils.RoundedImageView;
import com.futureinst.sharepreference.SharePreferenceUtil;
import com.futureinst.utils.ImageLoadOptions;
import com.futureinst.widget.list.PullListView;
import com.futureinst.widget.list.PullListView.OnRefreshListener;
import com.nostra13.universalimageloader.core.ImageLoader;

public class RankingFragment extends BaseFragment implements OnRefreshListener{
	private SharePreferenceUtil preferenceUtil;
	private HttpResponseUtils httpResponseUtils;
	private HttpPostParams httpPostParams;
	private PullListView pullListView;
	private TextView tv_userName,tv_prophet,tv_ranking;
	private ImageView iv_ranking;
	private RankingAdapter adapter;
	private RoundedImageView iv_headImg;
	private Button[] buttons;
	private boolean isStart;
	@Override
	protected void localOnCreate(Bundle savedInstanceState) {
		setContentView(R.layout.fragment_home_ranking);
		setTitle("排行榜");
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
		iv_headImg = (RoundedImageView) findViewById(R.id.iv_headImg);
		isStart = true;
		pullListView.setonRefreshListener(this);
		pullListView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				if(position < 1) return;
				RankDAO item = (RankDAO) adapter.getItem(position-1);
				if(item.getUser_id() == preferenceUtil.getID()){//本人
					((HomeActivity)getContext()).setTab(3);
				}else{
					Intent intent = new Intent(getActivity(), PersonalShowActivity.class);
					intent.putExtra("id", item.getUser_id()+"");
					startActivity(intent);
				}
				
			}
		});
	}
	@Override
	public void onResume() {
		if(((HomeActivity)getActivity()).getCurrentTab() == 1){
			get_rank();
			query_user_record();
		}
		super.onResume();
	}
	private void initMyRanking(UserRecordDAO userInformationDAO){
		tv_userName.setText(userInformationDAO.getUser().getName());
		tv_prophet.setText(userInformationDAO.getForeIndex()+"");
		if(iv_headImg.getTag() == null || !iv_headImg.getTag().equals(userInformationDAO.getUser().getHeadImage())){
			ImageLoader.getInstance().displayImage(userInformationDAO.getUser().getHeadImage(), iv_headImg, ImageLoadOptions.getOptions(R.drawable.logo));
			iv_headImg.setTag(userInformationDAO.getUser().getHeadImage());
		}
		tv_ranking.setText(userInformationDAO.getRank()+"  ");
		if(userInformationDAO.getRank() == 0){
			tv_ranking.setText("-  ");
		}
		if(userInformationDAO.getRank() < userInformationDAO.getLastRank()){
			iv_ranking.setImageDrawable(getResources().getDrawable(R.drawable.ranking_up_2));
		}else if(userInformationDAO.getRank() > userInformationDAO.getLastRank()){
			iv_ranking.setImageDrawable(getResources().getDrawable(R.drawable.ranking_down_2));
		}else{
			iv_ranking.setImageDrawable(getResources().getDrawable(R.drawable.ranking_balance_2));
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
					pullListView.onRefreshComplete();
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
		@Override
		public void onRefresh(boolean isTop) {
			// TODO Auto-generated method stub
			if(isTop){
//				query_user_record();
				get_rank();
			}
		}
}

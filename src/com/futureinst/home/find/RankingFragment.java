package com.futureinst.home.find;

import org.json.JSONException;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.futureinst.R;
import com.futureinst.baseui.BaseFragment;
import com.futureinst.home.HomeActivity;
import com.futureinst.model.record.UserRecordDAO;
import com.futureinst.model.usermodel.RankDAO;
import com.futureinst.model.usermodel.RankInfo;
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
import com.futureinst.widget.IconSlidingTabView;
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
	private LinearLayout ll_type,ll_ranking_type;
	private ListView lv_type;
	private IconSlidingTabView  icon_type;
	private RankingTypeAdapter rankingTypeAdapter;
	private TextView tv_type;
	private int index = 0;
	private BroadcastReceiver receiver;

	@Override
	protected void localOnCreate(Bundle savedInstanceState) {
		setContentView(R.layout.fragment_home_ranking);
		initView();
		get_rank();
		query_user_record();
	}
	private void initView() {
		receiver = new BroadcastReceiver() {
			@Override
			public void onReceive(Context context, Intent intent) {
				if(intent.getAction().equals("relation")){
					onRefresh(true);
				}
			}
		};
		IntentFilter filter = new IntentFilter();
		filter.addAction("relation");
		getActivity().registerReceiver(receiver,filter);

		preferenceUtil =SharePreferenceUtil .getInstance(getContext());
		httpResponseUtils = HttpResponseUtils.getInstace(getActivity());
		httpPostParams = HttpPostParams.getInstace();

		ll_ranking_type = (LinearLayout)findViewById(R.id.ll_ranking_type);
		icon_type = (IconSlidingTabView) findViewById(R.id.icon_type);
		tv_type = (TextView)findViewById(R.id.tv_type);
		ll_type = (LinearLayout)findViewById(R.id.ll_type);
		lv_type = (ListView)findViewById(R.id.lv_type);
		ll_ranking_type.setOnClickListener(onClickListener);
		ll_type.setOnClickListener(onClickListener);
		rankingTypeAdapter = new RankingTypeAdapter(getContext());
		lv_type.setAdapter(rankingTypeAdapter);
		lv_type.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
				String item = (String)rankingTypeAdapter.getItem(i);
				String[] type = item.split("-");
				tv_type.setText(type[0]);
				icon_type.setText(type[1]);
				ll_type.setVisibility(View.GONE);
				rankingTypeAdapter.setIndex(i);
				if(i == 0){
					index = i;
					get_rank();
				}else{
					index = i+1;
					get_tag_rank(i+1);
				}
				pullListView.setSelection(1);
			}
		});

		pullListView = (PullListView) findViewById(R.id.pull_listView);
		adapter = new RankingAdapter(getContext());
		pullListView.setAdapter(adapter);
		tv_userName = (TextView) findViewById(R.id.tv_userName);
		tv_prophet = (TextView) findViewById(R.id.tv_prophet);
		tv_ranking = (TextView) findViewById(R.id.tv_ranking);
		iv_ranking = (ImageView) findViewById(R.id.iv_ranking);
		iv_headImg = (RoundedImageView) findViewById(R.id.iv_headImg);
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
	View.OnClickListener onClickListener = new View.OnClickListener() {
		@Override
		public void onClick(View view) {
			switch (view.getId()){
				case R.id.ll_ranking_type:
					if(ll_type.getVisibility() == View.VISIBLE){
						ll_type.setVisibility(View.GONE);
					}else{
						ll_type.setVisibility(View.VISIBLE);
					}
					break;
				case R.id.ll_type:
					ll_type.setVisibility(View.GONE);
					break;
			}
		}
	};
	@Override
	public void onResume() {
		if(((HomeActivity)getActivity()).getCurrentTab() == 1){
//			get_rank();
//			query_user_record();
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
			iv_ranking.setImageDrawable(getResources().getDrawable(R.drawable.iv_up));
		}else if(userInformationDAO.getRank() > userInformationDAO.getLastRank()){
			iv_ranking.setImageDrawable(getResources().getDrawable(R.drawable.iv_down));
		}else{
			iv_ranking.setImageDrawable(getResources().getDrawable(R.drawable.ranking_balance_2));
		}
	}
	//获取排名
		private void get_rank(){
			httpResponseUtils.postJson(httpPostParams.getPostParams(
							PostMethod.get_rank.name(), PostType.common.name(),
							httpPostParams.get_rank(preferenceUtil.getID()+"",preferenceUtil.getUUid())),
					RankInfo.class,
					new PostCommentResponseListener() {
						@Override
						public void requestCompleted(Object response) throws JSONException {
							pullListView.onRefreshComplete();
							if (response == null) return;
							RankInfo rankInfo = (RankInfo) response;
							adapter.setList(rankInfo.getRanks(),rankInfo.getFollows(),rankInfo.getFriends());
//							pullListView.setSelection(1);
						}
					});
		}
	//获取排名
		private void get_tag_rank(int tag){
			httpResponseUtils.postJson(httpPostParams.getPostParams(
					PostMethod.get_tag_rank.name(), PostType.common.name(),
					httpPostParams.get_tag_rank(preferenceUtil.getID()+"",preferenceUtil.getUUid(),tag)),
					RankInfo.class,
					new PostCommentResponseListener() {
				@Override
				public void requestCompleted(Object response) throws JSONException {
					pullListView.onRefreshComplete();
					if(response == null) return;
					RankInfo rankInfo = (RankInfo) response;
					adapter.setList(rankInfo.getRanks(),rankInfo.getFollows(),rankInfo.getFriends());
//					pullListView.setSelection(1);
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
				if(index == 0){
					get_rank();
				}else{
					get_tag_rank(index);
				}
			}
		}
}

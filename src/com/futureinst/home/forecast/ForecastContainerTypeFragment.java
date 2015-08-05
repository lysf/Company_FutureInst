package com.futureinst.home.forecast;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;

import com.futureinst.R;
import com.futureinst.baseui.BaseFragment;
import com.futureinst.home.SystemTimeUtile;
import com.futureinst.home.eventdetail.EventDetailActivity;
import com.futureinst.login.LoginActivity;
import com.futureinst.model.attention.AttentionDAO;
import com.futureinst.model.attention.AttentionInfoDAO;
import com.futureinst.model.homeeventmodel.FilingInfoDAO;
import com.futureinst.model.homeeventmodel.QueryEventDAO;
import com.futureinst.model.homeeventmodel.QueryEventInfoDAO;
import com.futureinst.net.HttpPostParams;
import com.futureinst.net.HttpResponseUtils;
import com.futureinst.net.PostCommentResponseListener;
import com.futureinst.net.PostMethod;
import com.futureinst.net.PostType;
import com.futureinst.newbieguide.GuideClickInterface;
import com.futureinst.newbieguide.NewbieGuide;
import com.futureinst.sharepreference.SharePreferenceUtil;
import com.futureinst.widget.list.PullListView;
import com.futureinst.widget.list.PullListView.OnRefreshListener;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.text.Html;
import android.text.Html.ImageGetter;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;

public class ForecastContainerTypeFragment extends BaseFragment implements OnRefreshListener{
	private static final String ARG_POSITION = "position";
	private int position;
	private PullListView pullListView;
	private ForecastItemAdapter adapter;
	private String[] orders;
	private LinearLayout ll_unlogin,ll_empty;
	private TextView tv_empty;
	private boolean isStart;
	private Button btn_login;
	private SharePreferenceUtil preferenceUtil;
	private String empty = "快去点击事件进入事件详情页打开‘<img src=\""+R.drawable.attention_tip+"\" />’";
	public static ForecastContainerTypeFragment newInstance(int position) {
		ForecastContainerTypeFragment f = new ForecastContainerTypeFragment();
		Bundle b = new Bundle();
		b.putInt(ARG_POSITION, position);
		f.setArguments(b);
		return f;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		preferenceUtil = SharePreferenceUtil.getInstance(getContext());
		position = getArguments().getInt(ARG_POSITION);
		orders = getActivity().getResources().getStringArray(R.array.home_seond_title_order);
	}

	@Override
	protected void localOnCreate(Bundle savedInstanceState) {
		setContentView(R.layout.pull_listview_2);
		initView();
		if(position == 1){
			if(!TextUtils.isEmpty(SharePreferenceUtil.getInstance(getContext()).getUUid())){
				getMyAttention();
			}else{
				ll_unlogin.setVisibility(View.VISIBLE);
			}
		}else if(position == 0){
			getData(position+1+"", orders[0]);
		}else if(position == -1){
			getData();
		}else{
			getData(position+"", orders[0]);
		}
		isStart = true;
	}
	private void initView(){
		ll_unlogin = (LinearLayout) findViewById(R.id.ll_unLogin);
		ll_empty = (LinearLayout) findViewById(R.id.ll_empty);
		tv_empty = (TextView) findViewById(R.id.tv_empty);
		btn_login = (Button) findViewById(R.id.btn_login);
		ImageGetter imageGetter = new ImageGetter() { 
	         @Override 
	         public Drawable getDrawable(String source) { 
	             int id = Integer.parseInt(source); 
	             Drawable drawable = getResources().getDrawable(id); 
	             drawable.setBounds(0, 0, drawable.getIntrinsicWidth(),drawable.getIntrinsicHeight()); 
	             return drawable; 
	         } 
	     }; 
	     tv_empty.append(Html.fromHtml(empty, imageGetter, null));
		pullListView = (PullListView) findViewById(R.id.pull_listView);
		pullListView.setonRefreshListener(this);
		adapter = new ForecastItemAdapter(getContext());
		pullListView.setAdapter(adapter);
		pullListView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int index, long id) {
				if(index < 1) return;
				QueryEventDAO item = (QueryEventDAO) adapter.getItem(index - 1);
				//预测
				Intent intent = new Intent(getActivity(), EventDetailActivity.class);
				intent.putExtra("event", item);
				if(position == -1)
					intent.putExtra("boolean", true);
				startActivity(intent);
			}
		});
		btn_login.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getActivity(), LoginActivity.class);
				startActivity(intent);
			}
		});
	}
	@Override
	public void setUserVisibleHint(boolean isVisibleToUser) {
		super.setUserVisibleHint(isVisibleToUser);
		if(isVisibleToUser && isStart){
			onRefresh(true);
			pullListView.onRefreshComplete();
		}
	}
	//获取我的关注
		private void getMyAttention(){
			HttpResponseUtils.getInstace(getActivity()).postJson(
					HttpPostParams.getInstace().getPostParams(PostMethod.query_follow.name(), PostType.follow.name(),
							HttpPostParams.getInstace().query_follow(SharePreferenceUtil.getInstance(getContext()).getID()+"", SharePreferenceUtil.getInstance(getContext()).getUUid())), 
					AttentionInfoDAO.class, 
					new PostCommentResponseListener() {
				@Override
				public void requestCompleted(Object response) throws JSONException {
					pullListView.onRefreshComplete();
					if(response == null) return;
					AttentionInfoDAO attentionInfoDAO = (AttentionInfoDAO) response;
					List<QueryEventDAO> list = new ArrayList<QueryEventDAO>();
					for(AttentionDAO dao : attentionInfoDAO.getFollows()){
						list.add(dao.getEvent());
					}
					adapter.setList(list);
					if(list.size() == 0){
						ll_empty.setVisibility(View.VISIBLE);
						pullListView.setEmptyView(ll_empty);
					}else{
						ll_empty.setVisibility(View.GONE);
					}
					notifyDate();
				}
			});
		}
	 //获取事件数据
	 private void getData(String tag,String order){
		 HttpResponseUtils.getInstace(getActivity()).postJson(
				 HttpPostParams.getInstace().getPostParams(PostMethod.query_event.name(), PostType.event.name(), HttpPostParams.getInstace().query_event(tag, order)), 
				 QueryEventInfoDAO.class, 
				 new PostCommentResponseListener() {
					@Override
					public void requestCompleted(Object response) throws JSONException {
						pullListView.onRefreshComplete();
						if(response == null) return;
						QueryEventInfoDAO queryEventInfoDAO = (QueryEventInfoDAO) response;
						SystemTimeUtile.getInstance(queryEventInfoDAO.getCurr_time()).setSystemTime(queryEventInfoDAO.getCurr_time());
						adapter.setList(queryEventInfoDAO.getEvents());
						showGuide();
						notifyDate();
					}
				});
	 }
	 //显示新手引导
	 private void showGuide(){
		 if(preferenceUtil.getGuide1())
			 return;
		 new NewbieGuide(getActivity(), R.drawable.guide_1, new GuideClickInterface() {
			@Override
			public void guideClick() {
				preferenceUtil.setGuide1();
			}
		});
		 
	 }
	 //查询归档事件数据
	 private void getData(){
		 HttpResponseUtils.getInstace(getActivity()).postJson(
				 HttpPostParams.getInstace().getPostParams(PostMethod.query_event.name(), PostType.event.name(), HttpPostParams.getInstace().query_event()), 
				 FilingInfoDAO.class, 
				 new PostCommentResponseListener() {
					 @Override
					 public void requestCompleted(Object response) throws JSONException {
						 pullListView.onRefreshComplete();
						 if(response == null) return;
						 FilingInfoDAO filingInfoDAO = (FilingInfoDAO) response;
						 SystemTimeUtile.getInstance(filingInfoDAO.getCurr_time()).setSystemTime(filingInfoDAO.getCurr_time());
						 adapter.setList(filingInfoDAO.getEvents());
						 notifyDate();
					 }
				 });
	 }

	@Override
	public void onRefresh(boolean isTop) {
		if(isTop){
			if(position == 1){
				if(!TextUtils.isEmpty(SharePreferenceUtil.getInstance(getContext()).getUUid())){
					getMyAttention();
				}else{
					ll_unlogin.setVisibility(View.VISIBLE);
				}
			}else if(position == 0){
				getData(position+1+"", orders[0]);
			}else if(position == -1){
				getData();
			}else{
				getData(position+"", orders[0]);
			}
		}
		
	}
	Handler handler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 111:
				adapter.notifyDataSetChanged();
				break;
			}
		};
	};
	boolean flag = false;
	private void notifyDate(){
		flag = true;
		new Thread(new Runnable() {
			public void run() {
				while(flag){
					handler.sendEmptyMessage(111);
					try {
						Thread.sleep(10000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}).start();
	}
	@Override
	public void onPause() {
		super.onPause();
		
	}
	@Override
	public void onDestroy() {
		super.onDestroy();
		flag = false;
	}
}

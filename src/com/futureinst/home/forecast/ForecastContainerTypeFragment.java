package com.futureinst.home.forecast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONException;

import com.futureinst.R;
import com.futureinst.activitytransition.ActivityTransitionLauncher;
import com.futureinst.baseui.BaseFragment;
import com.futureinst.global.Content;
import com.futureinst.home.HomeActivity;
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
import com.futureinst.push.PushWebActivity;
import com.futureinst.sharepreference.SharePreferenceUtil;
import com.futureinst.utils.CustomStatUtil;
import com.futureinst.widget.dragtop.AttachUtil;
import com.futureinst.widget.list.PullListView;
import com.futureinst.widget.list.PullListView.OnRefreshListener;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.text.Html;
import android.text.Html.ImageGetter;
import android.util.Log;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;

import de.greenrobot.event.EventBus;

public class ForecastContainerTypeFragment extends BaseFragment implements OnRefreshListener{
	private static final String ARG_POSITION = "position";
	private int page = 1;
	private String last_id = "0";
	private int position;
	private long stayTime;
	private PullListView pullListView;
	private ForecastItemAdapter adapter;
	private String[] orders;
	private LinearLayout ll_unlogin,ll_empty;
	private TextView tv_empty;
	private boolean isStart;
	private Button btn_login;
	private String empty = "快去点击事件进入事件详情页打开“‘<img src=\""+R.drawable.detail_operate+"\" />’”";
	public static ForecastContainerTypeFragment newInstance(int position) {
		ForecastContainerTypeFragment f = new ForecastContainerTypeFragment();
		Bundle b = new Bundle();
		b.putInt(ARG_POSITION, position);
		f.setArguments(b);
		return f;
	}
    private BroadcastReceiver receiver ;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		position = getArguments().getInt(ARG_POSITION);
		orders = getActivity().getResources().getStringArray(R.array.home_seond_title_order);
	}

    @Override
    public void onStart() {
        super.onStart();
        receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if(intent.getAction().equals("order")){
                    Content.order = intent.getIntExtra("order",0);
                    onRefresh(true);
                }
            }
        };
        IntentFilter filter = new IntentFilter();
        filter.addAction("order");
        getContext().registerReceiver(receiver, filter);
    }



    @Override
	protected void localOnCreate(Bundle savedInstanceState) {
		setContentView(R.layout.pull_listview_2);
		initView();
		if(position == 1){
			if(!TextUtils.isEmpty(SharePreferenceUtil.getInstance(getContext()).getUUid())){
				getMyAttention(page,last_id);
			}else{
				ll_unlogin.setVisibility(View.VISIBLE);
			}
		}else if(position == 0){
			getData(position+1+"", orders[Content.order],page,last_id);
		}else if(position == -1){
			getData(page,last_id);
		}else{
			getData(position+"", orders[Content.order],page,last_id);
		}
		stayTime = System.currentTimeMillis();
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
		pullListView = (PullListView) findViewById(R.id.id_stickynavlayout_innerscrollview);
		pullListView.setonRefreshListener(this);
		adapter = new ForecastItemAdapter(getContext());
		pullListView.setAdapter(adapter);
		pullListView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int index, long id) {
                if (index < 1) return;
                QueryEventDAO item = (QueryEventDAO) adapter.getItem(index - 1);

                if (item.getType() == 1) {//专题
                    Intent intent = new Intent(getActivity(), ForecastGroupActivity.class);
                    intent.putExtra("group_id", item.getId() + "");
                    intent.putExtra("title", item.getTitle());
                    startActivity(intent);
                } else if (item.getType() == 2) {//广告
                    if (TextUtils.isEmpty(item.getLead())) return;
                    Intent intent = new Intent(getActivity(), PushWebActivity.class);
                    intent.putExtra("url", item.getLead());
                    intent.putExtra("title", item.getTitle());
                    startActivity(intent);
                } else {
                    //预测
                    Intent intent = new Intent(getActivity(), EventDetailActivity.class);
                    intent.putExtra("eventId", item.getId() + "");
                    if (position == -1)
                        intent.putExtra("boolean", true);
                    startActivity(intent);
                }
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
		if(!isStart) return;
		if(isVisibleToUser){
			pullListView.hideHeader();
			long currentTime = System.currentTimeMillis();
			Log.i("time", "------------距离上次刷新-->>"+(currentTime - pullListView.getLastUpdatedTime())/1000+"秒"+"--"+pullListView.getLastUpdatedTime());
			if(currentTime - pullListView.getLastUpdatedTime() > Content.main_list_fresh_interval*1000){
				onRefresh(true);
			}
			stayTime = System.currentTimeMillis();
		}else{
			if(stayTime == 0) return;
			String id = "channel"+position;
			if(position == -1){
				id = "channel_1";
			}
			stayTime = System.currentTimeMillis() - stayTime;
			Log.i(ARG_POSITION, "--------stop refresh-"+position+"----"+stayTime/1000+"s");
			HashMap<String, String> map = new HashMap<String, String>();
			map.put("delayTime", (stayTime/1000)+"s");
			CustomStatUtil.onEvent(getContext(), id, map, (int)(stayTime/1000));
			stayTime = 0;
		}
		super.setUserVisibleHint(isVisibleToUser);
	}
	@Override
	public void onResume() {
		super.onResume();
		pullListView.hideHeader();
		if(position == 1 && ll_unlogin.getVisibility() == View.VISIBLE){
			if(!TextUtils.isEmpty(SharePreferenceUtil.getInstance(getContext()).getUUid())
					){
				ll_unlogin.setVisibility(View.GONE);
				page = 1;
				last_id = "0";
				getMyAttention(page,last_id);
			}else{
				pullListView.onRefreshComplete();
				ll_unlogin.setVisibility(View.VISIBLE);
			}
		}
		if(getActivity() instanceof HomeActivity){
		if(((HomeActivity)getActivity()).getCurrentTab() == 0){
			setUserVisibleHint(true);
		}
		}


	}

	//获取我的关注
		private void getMyAttention(final int page,String lastId){
			HttpResponseUtils.getInstace(getActivity()).postJson(
					HttpPostParams.getInstace().getPostParams(PostMethod.query_follow.name(), PostType.follow.name(),
							HttpPostParams.getInstace().query_follow(SharePreferenceUtil.getInstance(getContext()).getID()+"", SharePreferenceUtil.getInstance(getContext()).getUUid(),page,lastId)),
					AttentionInfoDAO.class,
					new PostCommentResponseListener() {
				@Override
				public void requestCompleted(Object response) throws JSONException {
					pullListView.onRefreshComplete();
					if(response == null) return;
					AttentionInfoDAO attentionInfoDAO = (AttentionInfoDAO) response;
					List<QueryEventDAO> list = new ArrayList<QueryEventDAO>();

					if(attentionInfoDAO.getFollows()!=null && attentionInfoDAO.getFollows().size()>0){
						last_id = attentionInfoDAO.getFollows().get(attentionInfoDAO.getFollows().size()-1).getFeid();
					}
					for(AttentionDAO dao : attentionInfoDAO.getFollows()){
						list.add(dao.getEvent());
					}
					if(page ==1){
						adapter.refresh(list,attentionInfoDAO.getCommentMap());
					}else{
						adapter.setList(list,attentionInfoDAO.getCommentMap());
					}
					if(page == 1 && list.size() == 0){
						ll_empty.setVisibility(View.VISIBLE);
						pullListView.setEmptyView(ll_empty);
					}else{
						ll_empty.setVisibility(View.GONE);
					}

					if(adapter.getCount() > 9){
						pullListView.setLoadMore(true);
					}else{
						pullListView.setLoadMore(false);
					}
					if(page!=1 && (attentionInfoDAO.getFollows() == null || attentionInfoDAO.getFollows().size() == 0)){
						handler.sendEmptyMessage(10);
						pullListView.setLoadMore(false);
					}
					notifyDate();
				}
			});
		}
	 //获取事件数据
	 private void getData(String tag,String order,final int page,String last_id){
		 HttpResponseUtils.getInstace(getActivity()).postJson(
				 HttpPostParams.getInstace().getPostParams(PostMethod.query_event_all.name(), PostType.event.name(), HttpPostParams.getInstace().query_event(tag, order,page,last_id)),
				 QueryEventInfoDAO.class,
				 new PostCommentResponseListener() {
					@Override
					public void requestCompleted(Object response) throws JSONException {
						pullListView.onRefreshComplete();
						if(response == null) return;
						QueryEventInfoDAO queryEventInfoDAO = (QueryEventInfoDAO) response;
						SystemTimeUtile.getInstance(queryEventInfoDAO.getCurr_time()).setSystemTime(queryEventInfoDAO.getCurr_time());

						if(page ==1){
							adapter.refresh(queryEventInfoDAO.getEvents(),queryEventInfoDAO.getCommentMap());
						}else{
							adapter.setList(queryEventInfoDAO.getEvents(),queryEventInfoDAO.getCommentMap());
						}

						if(adapter.getCount() > 9){
							pullListView.setLoadMore(true);
						}else{
							pullListView.setLoadMore(false);
						}
						if(page!=1 && (queryEventInfoDAO.getEvents() == null || queryEventInfoDAO.getEvents().size() ==0)){
							handler.sendEmptyMessage(10);
							pullListView.setLoadMore(false);
						}
						notifyDate();
					}
				});
	 }

	 //查询归档事件数据
	 private void getData(final int page,String last_id){
		 HttpResponseUtils.getInstace(getActivity()).postJson(
				 HttpPostParams.getInstace().getPostParams(PostMethod.query_event_all.name(), PostType.event.name(), HttpPostParams.getInstace().query_event(page,last_id)),
				 FilingInfoDAO.class,
				 new PostCommentResponseListener() {
					 @Override
					 public void requestCompleted(Object response) throws JSONException {
						 pullListView.onRefreshComplete();
						 if(response == null) return;
						 FilingInfoDAO filingInfoDAO = (FilingInfoDAO) response;
						 SystemTimeUtile.getInstance(filingInfoDAO.getCurr_time()).setSystemTime(filingInfoDAO.getCurr_time());

						 if(page ==1){
								adapter.refresh(filingInfoDAO.getEvents(),filingInfoDAO.getCommentMap());
							}else{
								adapter.setList(filingInfoDAO.getEvents(),filingInfoDAO.getCommentMap());
							}

						 if(adapter.getCount() > 9){
								pullListView.setLoadMore(true);
							}else{
								pullListView.setLoadMore(false);
							}
						 if(page!=1 && (filingInfoDAO.getEvents() == null || filingInfoDAO.getEvents().size() ==0)){
								handler.sendEmptyMessage(10);
								pullListView.setLoadMore(false);
						}
						 notifyDate();
					 }
				 });
	 }

	@Override
	public void onRefresh(boolean isTop) {
		if(isTop){
			page = 1;
			last_id = "0";
		}else{
			page++;
			if(adapter.getList() !=null && adapter.getList().size()>0){
				if(position !=1){
					last_id = adapter.getList().get(adapter.getCount()-1).getId()+"";
				}
			}
		}
		if(position == 1){
			if(!TextUtils.isEmpty(SharePreferenceUtil.getInstance(getContext()).getUUid())){
				ll_unlogin.setVisibility(View.GONE);
				getMyAttention(page,last_id);
			}else{
				pullListView.onRefreshComplete();
				ll_unlogin.setVisibility(View.VISIBLE);
			}
		}else if(position == 0){
			getData(position+1+"", orders[Content.order],page,last_id);
		}else if(position == -1){
			getData(page,last_id);
		}else{
			getData(position+"", orders[Content.order],page,last_id);
		}
	}
	Handler handler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 10:
				Toast.makeText(getContext(), getResources().getString(R.string.data_over), Toast.LENGTH_SHORT).show();
				break;
			case 111:
				adapter.notifyDataSetChanged();
				break;
			}
		}
    };
	boolean flag = false;
	private void notifyDate(){
		if(flag) return;
		
		Log.i(ARG_POSITION, "--------start refresh");
		flag = true;
		new Thread(new Runnable() {
			public void run() {
				while(flag){
					handler.sendEmptyMessage(111);
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}).start();
	}
	private Runnable delayLoad = new Runnable() {
		@Override
		public void run() {
			onRefresh(true);
		}
	};
	public void setIsHaveTop(boolean haveTop){
		if(haveTop){
			pullListView.setRefresh(true);
		}else{
			pullListView.setRefresh(false);
		}
	}
	@Override
	public void onPause() {
		super.onPause();
	
		flag = false;
		handler.removeCallbacks(delayLoad);
		
	}

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if(receiver != null){
            getContext().unregisterReceiver(receiver);
        }
    }

    @Override
	public void onDestroy() {
		super.onDestroy();
		flag = false;
		handler.removeCallbacks(delayLoad);

	}


}

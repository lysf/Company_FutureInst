package com.futureinst.home.eventdetail;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.LinearLayout.LayoutParams;

import com.futureinst.R;
import com.futureinst.baseui.BaseActivity;
import com.futureinst.home.SystemTimeUtile;
import com.futureinst.interfaces.OnRgsExtraCheckedChangedListener;
import com.futureinst.login.LoginActivity;
import com.futureinst.model.basemodel.BaseModel;
import com.futureinst.model.homeeventmodel.EventBuyDAO;
import com.futureinst.model.homeeventmodel.EventPriceDAOInfo;
import com.futureinst.model.homeeventmodel.EventPriceInfo;
import com.futureinst.model.homeeventmodel.EventSellDAO;
import com.futureinst.model.homeeventmodel.QueryEventDAO;
import com.futureinst.model.order.SingleEventClearDAO;
import com.futureinst.model.order.SingleEventInfoDAO;
import com.futureinst.net.HttpPath;
import com.futureinst.net.PostCommentResponseListener;
import com.futureinst.net.PostMethod;
import com.futureinst.net.PostType;
import com.futureinst.net.SingleEventScope;
import com.futureinst.share.OneKeyShareUtil;
import com.futureinst.utils.DialogShow;
import com.futureinst.utils.FragmentActivityTabAdapter;
import com.futureinst.utils.ImageLoadOptions;
import com.futureinst.utils.LongTimeUtil;
import com.futureinst.utils.MyProgressDialog;
import com.futureinst.utils.MyToast;
import com.futureinst.widget.CircleView;
import com.futureinst.widget.WaterWaveView;
import com.nostra13.universalimageloader.core.ImageLoader;


@SuppressLint({ "HandlerLeak", "DefaultLocale" })
public class EventDetailActivity extends BaseActivity {
	private boolean isAttention;
	private MyProgressDialog progressDialog;
	private EventPriceDAOInfo priceDAOInfo;
	private String event_id;
	private QueryEventDAO event;
	//头部
	private WaterWaveView wav;
	private ImageView iv_operate;
	private ImageView iv_back;
	private TextView tv_description,tv_event_title;
	private Button btn_lood_good,btn_lood_bad;
	private TextView[] tv_buys,tv_sells;
	private View view_line;
	private ImageView iv_image;
	private TextView tv_time;
	
	//单个事件账单 
	private View view_single_event;
	private TextView tv_buy_1,tv_buy_2;
	private TextView tv_sell_1,tv_sell_2;
	private TextView tv_eventdetail_gain_good,tv_eventdetail_gain_bad;
	
	private LinearLayout ll_scroll;
	//底部
	private Button[] bottom_btns;
	private View[] bottom_views;
	private ViewPager viewPager;
	private CommentFragment commentFragment;
	private RefrenceFragment refrenceFragment;
	private LazyBagFragment lazyBagFragment;
	
	private boolean timeIsStart;
	private Handler handler = new Handler(){
		@Override
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 1:
				Long time = event.getTradeTime() - SystemTimeUtile.getInstance(0L).getSystemTime();
				if(time > 800){
					tv_time.setText("剩余"+LongTimeUtil.longTimeUtil(time));
					view_line.setBackgroundColor(getResources().getColor(R.color.forecast_deal));
					tv_time.setBackgroundColor(getResources().getColor(R.color.forecast_deal));
					
				}else{
					timeIsStart = false;
					view_line.setBackgroundColor(getResources().getColor(R.color.tab_text_selected));
					tv_time.setBackgroundColor(getResources().getColor(R.color.tab_text_selected));
				}
				break;
			}
		};
	};
	@Override
	protected void localOnCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		setContentView(R.layout.activity_event_detail_about);
		initView();
		initData(event);
		judgeIsClear();
	}
	@Override
	protected void onResume() {
		super.onResume();
		getPrice();
		if(!TextUtils.isEmpty(preferenceUtil.getUUid()) && event.getStatusStr()!=null && !event.getStatusStr().equals("清算中")){
			query_single_event_clear();
			lazyBagFragment.setUserVisibleHint(true);
		}else{
			view_single_event.setVisibility(View.GONE);
		}
	}
	private void initView() {
		event = (QueryEventDAO) getIntent().getSerializableExtra("event");
		
		progressDialog = MyProgressDialog.getInstance(this);
		event_id = event.getId()+"";
		tv_buys = new TextView[3];
		tv_sells = new TextView[3];
		
		wav = (WaterWaveView) findViewById(R.id.wav);
		view_line = findViewById(R.id.view_line);
		tv_time = (TextView) findViewById(R.id.tv_time);
		iv_image = (ImageView) findViewById(R.id.iv_image);
		
		view_single_event = findViewById(R.id.view_singlev_event);
		tv_buy_1 = (TextView) findViewById(R.id.tv_event_buy_1);
		tv_buy_2 = (TextView) findViewById(R.id.tv_event_buy_2);
		tv_sell_1 = (TextView) findViewById(R.id.tv_event_sell_1);
		tv_sell_2 = (TextView) findViewById(R.id.tv_event_sell_2);
		iv_operate = (ImageView) findViewById(R.id.iv_operate);
		iv_operate.setOnClickListener(clickListener);
		tv_eventdetail_gain_good = (TextView) findViewById(R.id.tv_eventdetail_gain_good);
		tv_eventdetail_gain_bad = (TextView) findViewById(R.id.tv_eventdetail_gain_bad);
		
		ll_scroll = (LinearLayout) findViewById(R.id.ll_scroll);
		iv_back = (ImageView) findViewById(R.id.iv_back);
		tv_event_title = (TextView) findViewById(R.id.tv_event_title);
		tv_description = (TextView) findViewById(R.id.tv_description);
		iv_back.setOnClickListener(clickListener);
		ll_scroll.setOnClickListener(clickListener);
		initPriceView();
		initBottomView();
	}
	private void initPriceView(){ 
		tv_buys[0] = (TextView) findViewById(R.id.tv_buy_1);
		tv_buys[1] = (TextView) findViewById(R.id.tv_buy_2);
		tv_buys[2] = (TextView) findViewById(R.id.tv_buy_3);
		tv_sells[0] = (TextView) findViewById(R.id.tv_sell_1);
		tv_sells[1] = (TextView) findViewById(R.id.tv_sell_2);
		tv_sells[2] = (TextView) findViewById(R.id.tv_sell_3);
		btn_lood_good = (Button) findViewById(R.id.btn_lood_good);
		btn_lood_bad = (Button) findViewById(R.id.btn_lood_bad);
		btn_lood_good.setOnClickListener(clickListener);
		btn_lood_bad.setOnClickListener(clickListener);
	}
	//单个事件的账单
	private void initSingleEvent(SingleEventInfoDAO singleEventInfo){
		SingleEventClearDAO item = singleEventInfo.getUser().getEvent_clear();
		if(item.getAllBuyNum() == 0 && item.getAllSellNum() == 0){
			view_single_event.setVisibility(View.GONE);
			return;
		}
		view_single_event.setVisibility(View.VISIBLE);
		String gain_good = String.format("%.2f",  singleEventInfo.getUser().getIf_yes());
		String gain_bad = String.format("%.2f",  singleEventInfo.getUser().getIf_no());
		tv_eventdetail_gain_good.setText(gain_good);
		tv_eventdetail_gain_bad.setText(gain_bad);
		if(singleEventInfo.getUser().getIf_yes()>=0){
			tv_eventdetail_gain_good.setText("+"+gain_good);
		}
		if(singleEventInfo.getUser().getIf_no()>=0){
			tv_eventdetail_gain_bad.setText("+"+gain_bad);
		}
		tv_buy_1.setText(getResources().getString(R.string.unhold_1)+"\t"+item.getAllBuyNum()+"\t份");
		tv_buy_2.setText(getResources().getString(R.string.event_detail_deal)+"\t"+item.getBuyNum()+"\t份\t已成交\t\t均价\t"+String.format("%.1f", item.getBuyPrice()));
		tv_sell_1.setText(getResources().getString(R.string.unhold_2)+"\t"+item.getAllSellNum()+"\t份");
		tv_sell_2.setText(getResources().getString(R.string.event_detail_deal)+"\t"+item.getSellNum()+"\t份\t已成交\t\t均价\t"+String.format("%.1f", item.getSellPrice()));
	}
	private void initBottomView(){
		bottom_btns = new Button[3];
		bottom_views = new View[3];
		viewPager =  (ViewPager) findViewById(R.id.event_detail_container);
		bottom_btns[0] = (Button) findViewById(R.id.btn_revoke);
		bottom_btns[1] = (Button) findViewById(R.id.btn_refrence);
		bottom_btns[2] = (Button) findViewById(R.id.btn_comment);
		bottom_views[0] = findViewById(R.id.view1);
		bottom_views[1] = findViewById(R.id.view2);
		bottom_views[2] = findViewById(R.id.view3);
		final List<Fragment> fragments = new ArrayList<Fragment>();
		Bundle bundle = new Bundle();
		bundle.putCharSequence("eventId", event.getId()+"");
		commentFragment = new CommentFragment();
		commentFragment.setArguments(bundle);
		refrenceFragment = new RefrenceFragment();
		refrenceFragment.setArguments(bundle);
		lazyBagFragment = new LazyBagFragment();
		lazyBagFragment.setArguments(bundle);
		fragments.add(lazyBagFragment);
		fragments.add(refrenceFragment);
		fragments.add(commentFragment);
		FragmentActivityTabAdapter activityTabAdapter = new FragmentActivityTabAdapter(this, fragments, R.id.container, bottom_btns,bottom_views);
		activityTabAdapter.setOnRgsExtraCheckedChangedListener(new OnRgsExtraCheckedChangedListener() {
			@Override
			public void OnRgsExtraCheckedChanged(View[] btns, int checkedId, int index) {
				fragments.get(index).setUserVisibleHint(true);
			}
		});
	}
	
	
	//初始化数据
	private void initData(QueryEventDAO event){
		tv_event_title.setText(event.getTitle());
		tv_description.setText(event.getDescription());
		ImageLoader.getInstance().displayImage(event.getImgsrc(), iv_image, ImageLoadOptions.getOptions(R.drawable.image_top_default));
//		CircleView circleView = new CircleView(this);
//		LayoutParams layoutParams = new LayoutParams(200, 200);
//		circleView.setLayoutParams(layoutParams);
		wav.setTextTop(String.format("%.2f", event.getCurrPrice()));
		if(event.getPriceChange() >= 0){
			wav.setDown(false);
			wav.setColor(getResources().getColor(R.color.gain_red));
			wav.setTextBottom("+"+String.format("%.2f", event.getPriceChange()));
		}else{
			wav.setDown(true);
			wav.setColor(getResources().getColor(R.color.gain_blue));
			wav.setTextBottom("-"+String.format("%.2f", Math.abs(event.getPriceChange())));
		}
		wav.startWave();
		wav.setWaterLevel(event.getCurrPrice()/100);
		tv_time.setText(event.getStatusStr());
		showTimeStatus();
	}
	OnClickListener clickListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.iv_back:
				finish();
				break;
			case R.id.iv_operate://关注、分享
				showOperateDialog();
				break;
			case R.id.tv_lanren://懒人包
				Intent intentLazyBag = new Intent(EventDetailActivity.this, LazyBagFragment.class);
				intentLazyBag.putExtra("event", event);
				startActivity(intentLazyBag);
				break;
			
			case R.id.btn_lood_good://
				if(!judgeIsLogin()) return;  
				Intent intent1 = new Intent(EventDetailActivity.this, EventBuyActivity.class);
				intent1.putExtra("buyOrSell", true);
				intent1.putExtra("event", event);
				intent1.putExtra("price", priceDAOInfo);
				startActivity(intent1);
				break;
			case R.id.btn_lood_bad://
				if(!judgeIsLogin()) return;
				Intent intent2 = new Intent(EventDetailActivity.this, EventBuyActivity.class);
				intent2.putExtra("buyOrSell", false);
				intent2.putExtra("event", event);
				intent2.putExtra("price", priceDAOInfo);
				startActivity(intent2);
				break;
			case R.id.ll_scroll:
				hideSoftInputView();
				break;
			}
		}
	};

	//初始化价格数据
	private void initPrice(EventPriceDAOInfo info){
		//价格图
//		int[] pathData = new int[info.getPrices().size()];
//		for(int i = 0;i<info.getPrices().size();i++){
//			EventPriceDAO eventPriceDAO = info.getPrices().get(i);
//			pathData[i] = (int) eventPriceDAO.getPrice();
//		}
//		ll_path.removeAllViews();
//		View view = LayoutInflater.from(this).inflate(R.layout.view_pathview, null, false);
//		PathView pathView = (PathView) view.findViewById(R.id.pathView);
//		pathView.setXCount(100, 5);
//		pathView.setType(PathView.OTHER);
//		pathView.setDate(pathData);
//		ll_path.addView(view);
		//价格三等对比
		List<EventBuyDAO> buys = info.getBuys();
		List<EventSellDAO> sells = info.getSells();
		for(int i = 0;i<buys.size();i++){
			tv_buys[i].setText(buys.get(i).getNum()+"  份  "+String.format("%.2f", buys.get(i).getPrice()));
			if(buys.get(i).getNum() > 9999)
				tv_buys[i].setText("9999+  份  "+String.format("%.2f", buys.get(i).getPrice()));
		}
		for(int j = 0;j<sells.size();j++){
			tv_sells[j].setText(String.format("%.2f", sells.get(j).getPrice())+"  "+sells.get(j).getNum()+"  份  ");
			if(sells.get(j).getNum() > 9999)
				tv_sells[j].setText(String.format("%.2f", sells.get(j).getPrice())+"  9999+  份  ");
		}
		
	}
	//获取事件的价格走势
	private void getPrice(){
//		progressDialog.progressDialog();
		httpResponseUtils.postJson(httpPostParams.getPostParams(
				PostMethod.query_single_event.name(), PostType.event.name(), 
				httpPostParams.query_single_event(event_id, SingleEventScope.price.name())), 
				EventPriceInfo.class, 
				new PostCommentResponseListener() {
					@Override
					public void requestCompleted(Object response) throws JSONException {
						progressDialog.cancleProgress();
						if(response == null) return;
						EventPriceInfo eventPriceInfo = (EventPriceInfo) response;
						initPrice(eventPriceInfo.getPrice());
						priceDAOInfo = eventPriceInfo.getPrice();
						SystemTimeUtile.getInstance(eventPriceInfo.getCurr_time()).setSystemTime(eventPriceInfo.getCurr_time());
//						setCountDown(eventPriceInfo.getCurr_time());
					}
				});
	}
	
	
	//事件规则
	private void showDialog(final String rule){
		View view = LayoutInflater.from(this).inflate(R.layout.view_event_rule, null,false);
		ImageView iv_cancel = (ImageView) view.findViewById(R.id.iv_event_rule_cancel);
		TextView tv_rule = (TextView) view.findViewById(R.id.tv_rule); 
		tv_rule.setText(rule);
		final Dialog dialog = DialogShow.showDialog(this, view,Gravity.CENTER);
		iv_cancel.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				dialog.cancel();
			}
		});
		dialog.show();
	}
	
	//判断是否已登录
	private boolean judgeIsLogin(){
		if(TextUtils.isEmpty(preferenceUtil.getUUid())){
			Intent intent = new Intent(EventDetailActivity.this, LoginActivity.class);
			intent.putExtra("login", true);
			startActivity(intent);
			return false;
		}
		return true;
	}
	//倒计时
	private void showTimeStatus(){
		if(event.getStatusStr().equals("交易中")){
			Long time = event.getTradeTime() - SystemTimeUtile.getInstance(0L).getSystemTime();
			tv_time.setText("剩余"+LongTimeUtil.longTimeUtil(time));
			view_line.setBackgroundColor(getResources().getColor(R.color.forecast_deal));
			tv_time.setBackgroundColor(getResources().getColor(R.color.forecast_deal));
			timeIsStart = true;
			timeRunThread.start();
		}else{
			view_line.setBackgroundColor(getResources().getColor(R.color.tab_text_selected));
			tv_time.setBackgroundColor(getResources().getColor(R.color.tab_text_selected));
		}
	}
	Thread timeRunThread = new Thread(new Runnable() {
		public void run() {
			while(timeIsStart){
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				Message message = Message.obtain();
				message.what = 1;
			handler.sendMessage(message);
			}
		}
	});
	//事件清算中不可下单
	private void judgeIsClear(){
		if(!event.getStatusStr().equals("交易中")){
			btn_lood_good.setClickable(false);
			btn_lood_bad.setClickable(false);
			btn_lood_good.setBackground(getResources().getDrawable(R.drawable.btn_gray));
			btn_lood_bad.setBackground(getResources().getDrawable(R.drawable.btn_gray));
		}
	}
	//添加我的关注  follow表示关注,unfollow表示取消关注
	private void addMyAttention(final String type){
		progressDialog.progressDialog();
		httpResponseUtils.postJson(httpPostParams.getPostParams(
				PostMethod.operate_follow.name(), PostType.follow.name(), 
				httpPostParams.operate_follow(preferenceUtil.getID()+"", preferenceUtil.getUUid(), event_id, type)), 
				BaseModel.class, 
				new PostCommentResponseListener() {
					@Override
					public void requestCompleted(Object response) throws JSONException {
						progressDialog.cancleProgress();
						if(response == null) return;
						if(type.equals("follow")){
							MyToast.showToast(EventDetailActivity.this, "事件关注成功", 1);
							isAttention = true;
						}else{
							MyToast.showToast(EventDetailActivity.this, "事件取消关注成功", 1);
							isAttention = false;
						}
					}
				});
	}
	//查询单个事件
	private void query_single_event_clear(){
//		progressDialog.progressDialog();
		httpResponseUtils.postJson_1(httpPostParams.getPostParams(
				PostMethod.query_single_event.name(), PostType.event.name(), 
				httpPostParams.query_single_event(preferenceUtil.getID()+"", preferenceUtil.getUUid(), event_id,"user")), 
				SingleEventInfoDAO.class, 
				new PostCommentResponseListener() {
			@Override
			public void requestCompleted(Object response) throws JSONException {
				progressDialog.cancleProgress();
				if(response == null) {
					view_single_event.setVisibility(View.GONE);
					return;
				}
				SingleEventInfoDAO singleEventInfoDAO = (SingleEventInfoDAO) response;
				if(singleEventInfoDAO.getUser().getFollow() == 1){
					isAttention = true;
				}
				
				if(singleEventInfoDAO.getUser().getEvent_clear() == null){
					view_single_event.setVisibility(View.GONE);
					return;
				}
				initSingleEvent(singleEventInfoDAO);
			}
		});
	}
	//分享
	private void share(){
		OneKeyShareUtil.showShare(this, event.getTitle(), HttpPath.SHARE_URL+event_id, event.getDescription(), null, event.getImgsrc(), true, null);
	}
	private void showOperateDialog(){
		View view = LayoutInflater.from(this).inflate(R.layout.view_event_operate, null);
		String attentionText = null;
		if(isAttention){
			attentionText = getResources().getString(R.string.event_detail_unfocus);
		}else{
			attentionText = getResources().getString(R.string.event_detail_focus);
		}
		final Dialog dialog = DialogShow.showDialog(this, view, Gravity.BOTTOM);
		Button btn_tips = (Button) view.findViewById(R.id.btn_tips);
		Button btn_attention = (Button) view.findViewById(R.id.btn_attention);
		btn_attention.setText(attentionText);
		Button btn_share = (Button) view.findViewById(R.id.btn_share);
		Button btn_cancel = (Button) view.findViewById(R.id.btn_cancel);
		btn_tips.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				showDialog(event.getRule());
				dialog.dismiss();
			}
		});
		btn_attention.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if(judgeIsLogin()){
					String attention =null;
					if(isAttention){
						attention = "unfollow";
					}else{
						attention = "follow";
					}
					addMyAttention(attention);
					dialog.dismiss();
				}
			}
		});
		btn_share.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				share();
				dialog.dismiss();
			}
		});
		btn_cancel.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				dialog.dismiss();
			}
		});
		dialog.show();
	}
	
	@Override 
	protected void onDestroy() {
		super.onDestroy();
		timeIsStart = false;
		wav.stop();
		System.gc();
	}
}

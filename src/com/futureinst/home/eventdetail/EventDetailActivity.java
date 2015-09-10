package com.futureinst.home.eventdetail;

import java.text.DecimalFormat;
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
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import cn.sharesdk.sina.weibo.SinaWeibo;
import cn.sharesdk.wechat.friends.Wechat;
import cn.sharesdk.wechat.moments.WechatMoments;

import com.futureinst.R;
import com.futureinst.baseui.BaseActivity;
import com.futureinst.global.Content;
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
import com.futureinst.newbieguide.EventdetailGuide;
import com.futureinst.newbieguide.GuideClickInterface;
import com.futureinst.newbieguide.NewbieGuide;
import com.futureinst.newbieguide.NewbieGuide2;
import com.futureinst.share.OneKeyShareUtil;
import com.futureinst.utils.DialogShow;
import com.futureinst.utils.FragmentActivityTabAdapter;
import com.futureinst.utils.ImageLoadOptions;
import com.futureinst.utils.LongTimeUtil;
import com.futureinst.utils.MyProgressDialog;
import com.futureinst.utils.MyToast;
import com.futureinst.widget.PullLayout;
import com.futureinst.widget.PullLayout.OnScrollListener;
import com.futureinst.widget.waterwave.MySersor;
import com.futureinst.widget.waterwave.WaterWaveView;
import com.nostra13.universalimageloader.core.ImageLoader;


@SuppressLint({ "HandlerLeak", "DefaultLocale" })
public class EventDetailActivity extends BaseActivity implements OnScrollListener{
	private PullLayout pullLayout;
	private RelativeLayout rl_deal,rl_deal_float;
	
	private MySersor mySensor;
	private SingleEventClearDAO singleEventClearDAO;
	private boolean isAttention;
	private MyProgressDialog progressDialog;
	private EventPriceDAOInfo priceDAOInfo;
	private String event_id;
	private QueryEventDAO event;
	private boolean came;
	private int share_award = 50;
	//头部
	private Button btn_invivate;
	private Button btn_lood_good,btn_lood_bad;
	
//	private ImageView iv_operate;
	private ImageView iv_back;
	private ImageView iv_image;
	private TextView tv_time,tv_event_title;
	
	FragmentActivityTabAdapter activityTabAdapter;
	private WaterWaveView wav;
	private TextView tv_description;
	private TextView[] tv_buys_1,tv_buys_2,tv_buys_3,tv_sells_1,tv_sells_2,tv_sells_3;
	private View view_line;
	
	//单个事件账单 
	private View view_single_event;
	private TextView tv_buy_2;
	private TextView tv_sell_2;
	private LinearLayout ll_event_buy,ll_event_sell;
	private TextView tv_eventdetail_gain_good,tv_eventdetail_gain_bad;
	
	private LinearLayout ll_scroll;
	//底部
	private Button[] bottom_btns;
	private View[] bottom_views;
	private CommentFragment commentFragment;
	private RefrenceFragment refrenceFragment;
	private LazyBagFragment lazyBagFragment;
	
	private boolean timeIsStart,isDestroy;
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
			case 2:
				getPrice();
				break;
			}
		};
	};
	//显示新手引导
		 private void showGuide(){
			 if(preferenceUtil.getGuide2())
				 return;
			new NewbieGuide2(this, isHavaPrice);
			preferenceUtil.setGuide2();
		 }
		 
	@Override
	protected void onRightImageViewClick(View view) {
		super.onRightImageViewClick(view);
		if(event == null) return;
		showOperateDialog(event);
	}	 
	@Override
	protected void localOnCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		setContentView(R.layout.activity_event_detail_about);
		initView();
		progressDialog.progressDialog();
		getPrice();
	}
	@Override
	protected void onResume() {
		super.onResume();
		handler.postDelayed(new Runnable() {
			@Override
			public void run() {
				getPrice();
			}
		}, 2000);
		
		if(!isPriceRefresh){
			isPriceRefresh = true;
			refreshPrice();
		}
		showGuide();
//		mySensor.onResume();
	}
	private void initView() {
		getLeftImageView().setImageDrawable(getResources().getDrawable(R.drawable.back));
		getRightImageView().setImageDrawable(getResources().getDrawable(R.drawable.detail_operate));
		setTitle(R.string.event_detail);
		
		mySensor = new MySersor(this);
		came = getIntent().getBooleanExtra("boolean", false);
		progressDialog = MyProgressDialog.getInstance(this);
		event_id = getIntent().getStringExtra("eventId");
		
		pullLayout = (PullLayout) findViewById(R.id.bottom_scroll);
		rl_deal = (RelativeLayout) findViewById(R.id.rl_deal);
		rl_deal_float = (RelativeLayout) findViewById(R.id.rl_deal_float);
		pullLayout.setOnScrollListener(this);
		
		
		tv_buys_1 = new TextView[3];
		tv_buys_2 = new TextView[3];
		tv_buys_3 = new TextView[3];
		tv_sells_1 = new TextView[3];
		tv_sells_2 = new TextView[3];
		tv_sells_3 = new TextView[3];
		
		wav = (WaterWaveView) findViewById(R.id.wav);
		view_line = findViewById(R.id.view_line);
		tv_time = (TextView) findViewById(R.id.tv_time);
		iv_image = (ImageView) findViewById(R.id.iv_image);
		
		view_single_event = findViewById(R.id.view_singlev_event);
		tv_buy_2 = (TextView) findViewById(R.id.tv_event_buy_2);
		tv_sell_2 = (TextView) findViewById(R.id.tv_event_sell_2);
		ll_event_buy = (LinearLayout) findViewById(R.id.ll_event_buy);
		ll_event_sell = (LinearLayout) findViewById(R.id.ll_event_sell);
		btn_invivate = (Button) findViewById(R.id.btn_invivate_float);
		btn_invivate.setOnClickListener(clickListener);
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
		tv_buys_1[0] = (TextView) findViewById(R.id.tv_buy_1_1);
		tv_buys_1[1] = (TextView) findViewById(R.id.tv_buy_2_1);
		tv_buys_1[2] = (TextView) findViewById(R.id.tv_buy_3_1);
		
		tv_buys_2[0] = (TextView) findViewById(R.id.tv_buy_1_2);
		tv_buys_2[1] = (TextView) findViewById(R.id.tv_buy_2_2);
		tv_buys_2[2] = (TextView) findViewById(R.id.tv_buy_3_2);
		
		tv_buys_3[0] = (TextView) findViewById(R.id.tv_buy_1_3);
		tv_buys_3[1] = (TextView) findViewById(R.id.tv_buy_2_3);
		tv_buys_3[2] = (TextView) findViewById(R.id.tv_buy_3_3);
		
		tv_sells_1[0] = (TextView) findViewById(R.id.tv_sell_1_1);
		tv_sells_1[1] = (TextView) findViewById(R.id.tv_sell_2_1);
		tv_sells_1[2] = (TextView) findViewById(R.id.tv_sell_3_1);
		
		tv_sells_2[0] = (TextView) findViewById(R.id.tv_sell_1_2);
		tv_sells_2[1] = (TextView) findViewById(R.id.tv_sell_2_2);
		tv_sells_2[2] = (TextView) findViewById(R.id.tv_sell_3_2);
		
		tv_sells_3[0] = (TextView) findViewById(R.id.tv_sell_1_3);
		tv_sells_3[1] = (TextView) findViewById(R.id.tv_sell_2_3);
		tv_sells_3[2] = (TextView) findViewById(R.id.tv_sell_3_3);
		btn_lood_good = (Button) findViewById(R.id.btn_lood_good_float);
		btn_lood_bad = (Button) findViewById(R.id.btn_lood_bad_float);
		btn_lood_good.setOnClickListener(clickListener);
		btn_lood_bad.setOnClickListener(clickListener);
	}
	//单个事件的账单
	private void initSingleEvent(SingleEventInfoDAO singleEventInfo){
		SingleEventClearDAO item = singleEventInfo.getUser().getEvent_clear();
		singleEventClearDAO = item;
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
		if(item.getBuyNum() > 0){
			tv_buy_2.setText(getResources().getString(R.string.unhold_1_1)+"\t"+String.format("%3d", item.getBuyNum())+"\t份\t\t\t均价\t"+String.format("%.2f", item.getBuyPrice()));
			ll_event_buy.setVisibility(View.VISIBLE);
		}else{
			ll_event_buy.setVisibility(View.GONE);
		}
		if(item.getSellNum() > 0){
			tv_sell_2.setText(getResources().getString(R.string.unhold_2)+"\t"+String.format("%3d", item.getSellNum())+"\t份\t\t\t均价\t"+String.format("%.2f", item.getSellPrice()));
			ll_event_sell.setVisibility(View.VISIBLE);
		}else{
			ll_event_sell.setVisibility(View.GONE);
		}
	}
	private void initBottomView(){
		bottom_btns = new Button[3]; 
		bottom_views = new View[3];
//		viewPager =  (ViewPager) findViewById(R.id.event_detail_container);
		bottom_btns[0] = (Button) findViewById(R.id.btn_comment);
		bottom_btns[1] = (Button) findViewById(R.id.btn_revoke);
		bottom_btns[2] = (Button) findViewById(R.id.btn_refrence);
		bottom_views[0] = findViewById(R.id.view1);
		bottom_views[1] = findViewById(R.id.view2);
		bottom_views[2] = findViewById(R.id.view3);
		final List<Fragment> fragments = new ArrayList<Fragment>();
		Bundle bundle = new Bundle();
		bundle.putCharSequence("eventId", event_id);
		commentFragment = new CommentFragment();
		commentFragment.setArguments(bundle);
		refrenceFragment = new RefrenceFragment();
		refrenceFragment.setArguments(bundle);
		lazyBagFragment = new LazyBagFragment();
		lazyBagFragment.setArguments(bundle);
		fragments.add(commentFragment);
		fragments.add(lazyBagFragment);
		fragments.add(refrenceFragment);
		activityTabAdapter = new FragmentActivityTabAdapter(this, fragments, R.id.container, bottom_btns,bottom_views);
		activityTabAdapter.setOnRgsExtraCheckedChangedListener(new OnRgsExtraCheckedChangedListener() {
			@Override
			public void OnRgsExtraCheckedChanged(View[] btns, int checkedId, int index) {
				if(index == 2){
//					fragments.get(index).setUserVisibleHint(true);
				}
			}
		});
	}
	
	
	//初始化数据
	private void initData(QueryEventDAO event){
		tv_event_title.setText(event.getTitle());
		tv_description.setText(event.getDescription());
		if(came || event.getStatusStr().equals("已清算")){
			tv_description.setText(event.getAccord());
			tv_description.setTextColor(getResources().getColor(R.color.forecast_bottom_line_select));
		}
		if(iv_image.getTag()==null || !iv_image.getTag().equals(event.getImgsrc())){
			iv_image.setTag(event.getImgsrc());
			ImageLoader.getInstance().displayImage(event.getImgsrc(), iv_image, ImageLoadOptions.getOptions(R.drawable.image_top_default));
		}
		wav.setTextTop(String.format("%.2f", event.getCurrPrice()));
		wav.setWaterLevel(event.getCurrPrice()/100);
		if(!came && !event.getStatusStr().equals("已清算")){
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
		}
		
		mySensor.setMySensorLisenter(wav);
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
			
			case R.id.btn_invivate_float://分享
				if(event == null) return;
				showShareDialog(event);
				break;
			case R.id.tv_lanren://懒人包
				if(event == null) return;
				Intent intentLazyBag = new Intent(EventDetailActivity.this, LazyBagFragment.class);
				intentLazyBag.putExtra("event", event);
				startActivity(intentLazyBag);
				break;
			
			case R.id.btn_lood_good_float://
				if(!judgeIsLogin() || event == null) return; 
				Intent intent1 = new Intent(EventDetailActivity.this, EventBuyActivity.class);
				intent1.putExtra("buyOrSell", true);
				intent1.putExtra("assure", singleEventClearDAO);
				intent1.putExtra("event", event);
				intent1.putExtra("price", priceDAOInfo);
				startActivity(intent1);
				break;
			case R.id.btn_lood_bad_float://
				if(!judgeIsLogin() || event == null) return;
				Intent intent2 = new Intent(EventDetailActivity.this, EventBuyActivity.class);
				intent2.putExtra("buyOrSell", false);
				intent2.putExtra("assure", singleEventClearDAO);
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
		//价格三等对比
		List<EventBuyDAO> buys = info.getBuys();
		List<EventSellDAO> sells = info.getSells();
		DecimalFormat df= new DecimalFormat("##0.00");
		priceViewReset();
		for(int i = 0;i<buys.size();i++){
			tv_buys_1[i].setText(String.format("%3d", buys.get(i).getNum())+"  份");
			tv_buys_2[i].setVisibility(View.INVISIBLE);
			tv_buys_3[i].setText(df.format(buys.get(i).getPrice()));
		}
		for(int j = 0;j<sells.size();j++){
			tv_sells_1[j].setText(df.format(sells.get(j).getPrice()));
			tv_sells_2[j].setVisibility(View.INVISIBLE);
			tv_sells_3[j].setText(String.format("%3d", sells.get(j).getNum())+"  份");
		}
	}
	private void priceViewReset(){
		for(int i = 0;i<tv_buys_1.length;i++){
			tv_buys_1[i].setText("");
			tv_buys_3[i].setText("");
			tv_buys_2[i].setVisibility(View.VISIBLE);
			tv_sells_1[i].setText("");
			tv_sells_3[i].setText("");
			tv_sells_2[i].setVisibility(View.VISIBLE);
		}
	}
	//获取事件的价格走势
	private boolean priceClear;
	private void getPrice(){
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
						event = eventPriceInfo.getEvent();
						initData(event);
						judgeIsClear(event);
						initPrice(eventPriceInfo.getPrice());
						priceDAOInfo = eventPriceInfo.getPrice();
						SystemTimeUtile.getInstance(eventPriceInfo.getCurr_time()).setSystemTime(eventPriceInfo.getCurr_time());
						if(!TextUtils.isEmpty(preferenceUtil.getUUid()) 
								&& event.getStatusStr()!=null && 
								!event.getStatusStr().equals("清算中")
								&& !priceClear){
							query_single_event_clear();
						}
						pullLayout.setTitleHeight(tv_event_title.getHeight());
//						setCountDown(eventPriceInfo.getCurr_time());
					}
				});
	}
	
	
	//事件规则
	private void showDialog(final String rule){
		View view = LayoutInflater.from(this).inflate(R.layout.view_event_rule, null,false);
		Button btn_submit = (Button) view.findViewById(R.id.btn_submit);
		TextView tv_rule = (TextView) view.findViewById(R.id.tv_rule); 
		tv_rule.setText(rule);
		final Dialog dialog = DialogShow.showDialog(this, view,Gravity.CENTER);
		btn_submit.setOnClickListener(new OnClickListener() {
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
			
			if(!timeIsStart && !isDestroy){
				timeIsStart = true;
				timeRunThread.start();
			}
				
		}else if(event.getStatusStr().equals("已清算")){
			view_line.setBackgroundColor(getResources().getColor(R.color.text_color_bf));
			tv_time.setBackgroundColor(getResources().getColor(R.color.text_color_bf));
		}
		else{
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
	//刷新价格
	private boolean isPriceRefresh = false;
	private void refreshPrice(){
		while (isPriceRefresh && Content.is_aoto_refresh_event_price) {
			long delayTime = (long) (Math.random()*10 + Content.aoto_refresh_event_price_interval)*1000;
			try {
				Thread.sleep(delayTime);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			Message message = Message.obtain();
			message.what = 2;
		handler.sendMessage(message);
		}
	
	}
	//事件清算中不可下单
	private void judgeIsClear(QueryEventDAO event){
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
	
	private boolean isHavaPrice ;
	//查询用户对于该事件的操作结果，关注和清算单
	private void query_single_event_clear(){
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
				priceClear = true;
				SingleEventInfoDAO singleEventInfoDAO = (SingleEventInfoDAO) response;
				if(singleEventInfoDAO.getUser().getFollow() == 1){
					isAttention = true;
				}
				share_award = singleEventInfoDAO.getUser().getShare_award();
				if(singleEventInfoDAO.getUser().getEvent_clear() == null){
					view_single_event.setVisibility(View.GONE);
					return;
				}
				isHavaPrice = true;
				if(preferenceUtil.getGuide2() && isHavaPrice
						&& !preferenceUtil.getGuide7()){
					new  NewbieGuide(EventDetailActivity.this, R.drawable.guide_7, new GuideClickInterface() {
						@Override
						public void guideClick() {
							preferenceUtil.setGuide7();
						}
					});
				}
				initSingleEvent(singleEventInfoDAO);
			}
		});
	}
	private void showShareDialog(final QueryEventDAO event){
		final String shareTitle = getResources().getString(R.string.shareTips).replace("X", event.getCurrPrice()+"").replace("Y", event.getLead());
		final String content = "来自未来研究所";
		View view = LayoutInflater.from(this).inflate(R.layout.view_share_gridview, null);
		final Dialog dialog = DialogShow.showDialog(this, view, Gravity.BOTTOM);
		TextView tv_sina = (TextView) view.findViewById(R.id.tv_sina);
		TextView tv_wechat = (TextView) view.findViewById(R.id.tv_wechat);
		TextView tv_wechatmonets = (TextView) view.findViewById(R.id.tv_wechatmoments);
		TextView tv_cancel = (TextView) view.findViewById(R.id.tv_cancel);
		tv_sina.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				OneKeyShareUtil.showShare(EventDetailActivity.this, event_id,share_award,shareTitle, HttpPath.SHARE_URL+event_id+"?user_id="+preferenceUtil.getID(), 
						shareTitle, null, event.getImgsrc(), true, SinaWeibo.NAME);
				dialog.dismiss();
			}
		});
		tv_wechat.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				OneKeyShareUtil.showShare(EventDetailActivity.this, event_id,share_award,shareTitle, HttpPath.SHARE_URL+event_id+"?user_id="+preferenceUtil.getID(), 
						content, null, event.getImgsrc(), true, Wechat.NAME);
				dialog.dismiss();
			}
		});
		tv_wechatmonets.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				OneKeyShareUtil.showShare(EventDetailActivity.this, event_id,share_award,shareTitle, HttpPath.SHARE_URL+event_id+"?user_id="+preferenceUtil.getID(), 
						content, null, event.getImgsrc(), true, WechatMoments.NAME);
				dialog.dismiss();
			}
		});
		tv_cancel.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				dialog.dismiss();
			}
		});
		dialog.show();
	}
	//其他操作
	private void showOperateDialog(final QueryEventDAO event){
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
		Button btn_guide = (Button) view.findViewById(R.id.btn_guide);
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
		btn_guide.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				new EventdetailGuide(EventDetailActivity.this);
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
	protected void onPause() {
		super.onPause();
//		mySensor.onPasue();
		isPriceRefresh = false;
	}
	@Override 
	protected void onDestroy() {
		super.onDestroy();
		timeIsStart = false;
		isPriceRefresh = false;
		isDestroy = true;
		wav.stop();
		System.gc();
	}
	
	OnPageChangeListener changeListener = new OnPageChangeListener() {
		@Override
		public void onPageSelected(int position) {
			bottom_views[0].setSelected(false);
			bottom_views[1].setSelected(false);
			bottom_views[2].setSelected(false);
			bottom_views[position].setSelected(true);
		}
		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {}
		
		@Override
		public void onPageScrollStateChanged(int arg0) {}
	};
	public class MyFragmentAdapter extends FragmentPagerAdapter{
		private List<Fragment> fragments;
		public MyFragmentAdapter(FragmentManager fm) {
			super(fm);
			// TODO Auto-generated constructor stub
		}
		public MyFragmentAdapter(FragmentManager fm,List<Fragment> oneListFragments){
			super(fm);
			this.fragments=oneListFragments;
		}
		@Override
		public Fragment getItem(int arg0) {
			return fragments.get(arg0);
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return fragments.size();
		}
		
	}
	/** 
     * 滚动的回调方法，当滚动的Y距离大于或者等于 购买布局距离父类布局顶部的位置，就显示购买的悬浮框 
     * 当滚动的Y的距离小于 购买布局距离父类布局顶部的位置加上购买布局的高度就移除购买的悬浮框 
     *  
     */  
    @Override  
    public void onScroll(int scrollY) {  
//        if(scrollY >= buyLayoutTop){  
//            if(suspendView == null){  
//                showSuspend();  
//            }  
//        }else if(scrollY <= buyLayoutTop + buyLayoutHeight){  
//            if(suspendView != null){  
//                removeSuspend();  
//            }  
//        } 
    	int mFloatLayout2ParentTop = Math.max(scrollY, rl_deal.getTop());  
        rl_deal_float.layout(0, mFloatLayout2ParentTop, rl_deal_float.getWidth(), mFloatLayout2ParentTop + rl_deal_float.getHeight());
    }  
  
  
}

package com.futureinst.home.eventdetail;

import java.util.List;

import org.json.JSONException;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.futureinst.R;
import com.futureinst.baseui.BaseActivity;
import com.futureinst.home.SystemTimeUtile;
import com.futureinst.login.LoginActivity;
import com.futureinst.model.basemodel.BaseModel;
import com.futureinst.model.homeeventmodel.EventBuyDAO;
import com.futureinst.model.homeeventmodel.EventPriceDAO;
import com.futureinst.model.homeeventmodel.EventPriceDAOInfo;
import com.futureinst.model.homeeventmodel.EventPriceInfo;
import com.futureinst.model.homeeventmodel.EventRelatedInfo;
import com.futureinst.model.homeeventmodel.EventSellDAO;
import com.futureinst.model.homeeventmodel.QueryEventDAO;
import com.futureinst.model.homeeventmodel.ReferenceDAO;
import com.futureinst.net.PostCommentResponseListener;
import com.futureinst.net.PostMethod;
import com.futureinst.net.PostType;
import com.futureinst.net.SingleEventScope;
import com.futureinst.utils.DialogShow;
import com.futureinst.utils.ImageLoadOptions;
import com.futureinst.utils.LongTimeUtil;
import com.futureinst.utils.MyProgressDialog;
import com.futureinst.utils.MyToast;
import com.futureinst.utils.Utils;
import com.futureinst.widget.path.PathView;
import com.nostra13.universalimageloader.core.ImageLoader;


@SuppressLint({ "HandlerLeak", "DefaultLocale" })
public class EventDetailActivity extends BaseActivity {
	private ImageLoader imageLoader;
	private MyProgressDialog progressDialog;
	private String event_id;
	private QueryEventDAO event;
	private ImageView iv_image,iv_tips_1,iv_back;
	private TextView tv_title,tv_description,tv_current_price,tv_priceChange,tv_involve;
	private Button btn_easy,btn_advance;
	private TextView tv_lanren;
	private TextView tv_status;//倒计时或状态
	private TextView tv_myHold;//我的持仓
	private int MODE = 0;
	private View view_easy,view_advance,layout_price;
	private LinearLayout ll_path,ll_refrence_news;
	private TextView[] tv_buys,tv_sells;
	//简易模式
	private Button btn_easy_buy,btn_easy_sell;
	//高级模式
	private boolean IsAdvanceBuy = true;
	private boolean isLimitPrice;
	private LinearLayout view_steep_1;
	private RelativeLayout view_steep_2,view_steep_3;
	//steep_1
	private Button btn_advance_buy;
	private Button btn_advance_sell;
	//steep_2
	private EditText et_price_buy;
	private Button btn_next;
	private SeekBar seekBar_price;
	//steep_3
	private Button btn_steep3_submit;
	private TextView tv_numbers;//数量
	private TextView tv_totalPrice;//需要的未币总数
	private TextView tv_first,tv_second;
	private SeekBar seekBar_num;
	
	private LinearLayout ll_scroll;
	private Handler handler = new Handler(){
		@Override
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 1:
				Long time = msg.getData().getLong("time");
				TextView tv_time = (TextView)msg.obj;
				tv_time.setText(LongTimeUtil.longTimeUtil(time));
				break;
			}
		};
	};
	@Override
	protected void localOnCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		setContentView(R.layout.activity_event_detail);
		initView();
		initData(event);
		judgeIsClear();
		getPrice();
		getEvetnRealted();
	}
	private void initView() {
		imageLoader = ImageLoader.getInstance();
		event = (QueryEventDAO) getIntent().getSerializableExtra("event");
		progressDialog = MyProgressDialog.getInstance(this);
		event_id = event.getId()+"";
		view_easy = findViewById(R.id.view_easy);
		view_advance = findViewById(R.id.view_advance);
		tv_buys = new TextView[3];
		tv_sells = new TextView[3];
		ll_path = (LinearLayout) findViewById(R.id.ll_path);
		layout_price = findViewById(R.id.layout_price);
		tv_status = (TextView) findViewById(R.id.tv_status);
		ll_refrence_news = (LinearLayout) findViewById(R.id.ll_refrence_news);
		
		tv_myHold = (TextView) findViewById(R.id.tv_myHode);
		ll_scroll = (LinearLayout) findViewById(R.id.ll_scroll);
		iv_image = (ImageView) findViewById(R.id.iv_image);
		iv_back = (ImageView) findViewById(R.id.iv_back);
		iv_tips_1 = (ImageView) findViewById(R.id.iv_tips_1);
		tv_title = (TextView) findViewById(R.id.tv_title);
		tv_description = (TextView) findViewById(R.id.tv_description);
		tv_current_price = (TextView) findViewById(R.id.tv_current_price);
		tv_priceChange = (TextView) findViewById(R.id.tv_priceChange);
		tv_involve = (TextView) findViewById(R.id.tv_involve);
		btn_easy = (Button) findViewById(R.id.btn_easy);
		btn_advance = (Button) findViewById(R.id.btn_advance);
		tv_lanren = (TextView) findViewById(R.id.tv_lanren);
		btn_easy.setOnClickListener(clickListener);
		btn_advance.setOnClickListener(clickListener);
		tv_lanren.setOnClickListener(clickListener);
		iv_back.setOnClickListener(clickListener);
		iv_tips_1.setOnClickListener(clickListener);
		ll_scroll.setOnClickListener(clickListener);
		tv_myHold.setOnClickListener(clickListener);
		initPriceView();
		initEasyView();
		initAdanceView();
	}
	private void initPriceView(){
		tv_buys[0] = (TextView) findViewById(R.id.tv_buy_1);
		tv_buys[1] = (TextView) findViewById(R.id.tv_buy_2);
		tv_buys[2] = (TextView) findViewById(R.id.tv_buy_3);
		tv_sells[0] = (TextView) findViewById(R.id.tv_sell_1);
		tv_sells[1] = (TextView) findViewById(R.id.tv_sell_2);
		tv_sells[2] = (TextView) findViewById(R.id.tv_sell_3);
	}
	//简单模式
	private void initEasyView(){
		btn_easy_buy = (Button) findViewById(R.id.btn_buy);
		btn_easy_sell = (Button) findViewById(R.id.btn_sell);
		btn_easy_buy.setOnClickListener(clickListener);
		btn_easy_sell.setOnClickListener(clickListener);
	}
	//高级模式
	private void initAdanceView(){
		view_steep_1 = (LinearLayout) findViewById(R.id.view_steep_1);
		view_steep_2 = (RelativeLayout) findViewById(R.id.view_steep_2);
		view_steep_3 = (RelativeLayout) findViewById(R.id.view_steep_3);
		//steep1
		btn_advance_buy = (Button) findViewById(R.id.btn_advance_buy);
		btn_advance_sell = (Button) findViewById(R.id.btn_advance_sell);
		//steep2
		et_price_buy = (EditText) findViewById(R.id.et_price_buy);
		btn_next = (Button) findViewById(R.id.btn_next);
		seekBar_price = (SeekBar) findViewById(R.id.seekbar);
		et_price_buy.setText(String.format("%.1f", event.getCurrPrice()));
		seekBar_price.setProgress((int)(event.getCurrPrice()*10));
		seekBar_price.setOnSeekBarChangeListener(barChangeListener);
		et_price_buy.addTextChangedListener(new TextWatcher() {
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				if(!TextUtils.isEmpty(s)){
					if(!Utils.checkIsNumber(s.subSequence(0, 1).toString())){
						et_price_buy.setText("");
						return;
					}
					float price = Float.valueOf(s.toString());
					if(price > 99.9){
						showToast("价格超限");
						et_price_buy.setText("");
						return;
					}
				}
			}
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {}
			
			@Override
			public void afterTextChanged(Editable s) {}
		});
		//steep3
		btn_steep3_submit = (Button) findViewById(R.id.btn_steep3_submit);
		tv_numbers = (TextView) findViewById(R.id.tv_steep3_numbers);
		tv_totalPrice = (TextView) findViewById(R.id.tv_totalPrice);
		tv_first = (TextView) findViewById(R.id.tv_first);
		tv_second = (TextView) findViewById(R.id.tv_second);
		seekBar_num = (SeekBar) findViewById(R.id.seekbar_steep3);
		seekBar_num.setProgress(1);
		seekBar_num.setOnSeekBarChangeListener(barChangeListener);
		addAdvanceClickListener();
	}
	private void addAdvanceClickListener(){
		btn_advance_buy.setOnClickListener(advanceClickListener);
		btn_advance_sell.setOnClickListener(advanceClickListener);
		btn_next.setOnClickListener(advanceClickListener);
		btn_steep3_submit.setOnClickListener(advanceClickListener);
	}
	//高级模式买卖事件监听
	OnClickListener advanceClickListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.btn_advance_buy://买
				btn_advance_buy.setAlpha(1f);
				btn_advance_sell.setAlpha(0.3f);
				setAdvanceView(true);
				view_steep_1.setVisibility(View.INVISIBLE);
				view_steep_2.setVisibility(View.VISIBLE);
				view_steep_3.setVisibility(View.INVISIBLE);
				IsAdvanceBuy = true;
				break;
			case R.id.btn_advance_sell://卖
				btn_advance_buy.setAlpha(0.3f);
				btn_advance_sell.setAlpha(1f);
				setAdvanceView(false);
				view_steep_1.setVisibility(View.INVISIBLE);
				view_steep_2.setVisibility(View.VISIBLE);
				view_steep_3.setVisibility(View.INVISIBLE);
				IsAdvanceBuy = false;
				break;
			case R.id.btn_next://第二步到第三部
				tv_totalPrice.setText(et_price_buy.getText().toString());
				view_steep_1.setVisibility(View.INVISIBLE);
				view_steep_2.setVisibility(View.INVISIBLE);
				view_steep_3.setVisibility(View.VISIBLE);
				break;
			case R.id.btn_steep3_submit://交易确认
				showBuyConfig(getOrderType(IsAdvanceBuy, true),et_price_buy.getText().toString(), Integer.valueOf(tv_numbers.getText().toString()));
				break;
			}
		}
	};
	//根据“看好”或“不看好”设置背景色
	private void setAdvanceView(boolean attitude){
		Drawable drawableBuy = getResources().getDrawable(R.drawable.btn_buy_back);
		Drawable drawableSell = getResources().getDrawable(R.drawable.btn_sell_back);
		int colorBuy = getResources().getColor(R.color.gain_red);
		int colorSell = getResources().getColor(R.color.gain_blue);
		if(attitude){//看好
			et_price_buy.setBackground(drawableBuy);
			btn_next.setBackground(drawableBuy);
			btn_steep3_submit.setBackground(drawableBuy);
			tv_numbers.setBackground(drawableBuy);
			tv_totalPrice.setBackground(drawableBuy);
			tv_first.setTextColor(colorBuy);
			tv_second.setTextColor(colorBuy);
			tv_first.setText("我要看好");
		}else{//不看好
			et_price_buy.setBackground(drawableSell);
			btn_next.setBackground(drawableSell);
			btn_steep3_submit.setBackground(drawableSell);
			tv_numbers.setBackground(drawableSell);
			tv_totalPrice.setBackground(drawableSell);
			tv_first.setTextColor(colorSell);
			tv_second.setTextColor(colorSell);
			tv_first.setText("我要不看好");
		}
	}
	//seekbar滑动监听
	OnSeekBarChangeListener barChangeListener = new OnSeekBarChangeListener() {
		@Override
		public void onStopTrackingTouch(SeekBar seekBar) {}
		@Override
		public void onStartTrackingTouch(SeekBar seekBar) {}
		@Override
		public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
			switch (seekBar.getId()) {
			case R.id.seekbar://价格
				String price =String.format("%.1f", progress/10f) ;
				if(progress == 0){
					price = "0.1";
				}
				if(progress == 1000){
					price = "99.9";
				}
				et_price_buy.setText(price);
				break;
			case R.id.seekbar_steep3://数量
				int number = 0;
				number = progress;
				if(progress == 0){
					number = 1;
				}
				tv_totalPrice.setText(String.format("%.1f", number*Float.valueOf(et_price_buy.getText().toString())));
				tv_numbers.setText(number+"");
				break;
			}
			
		}
	};
	//初始化数据
	private void initData(QueryEventDAO event){
		tv_title.setText(event.getTitle());
		imageLoader.displayImage(event.getImgsrc(), iv_image, ImageLoadOptions.getOptions(R.drawable.view_shap));
		tv_description.setText(event.getDescription());
		tv_current_price.setText(String.format("%.1f", event.getCurrPrice()));
		if(event.getPriceChange() <0){
			tv_priceChange.setText("-"+String.format("%.2f", Math.abs(event.getPriceChange())));
			tv_priceChange.setBackgroundColor(getResources().getColor(R.color.gain_blue));
		}else{
			tv_priceChange.setText("+"+String.format("%.2f", Math.abs(event.getPriceChange())));
			tv_priceChange.setBackgroundColor(getResources().getColor(R.color.gain_red));
		}
		tv_involve.setText(event.getInvolve()+"");
		tv_status.setText(event.getStatusStr());
		
		if(event.getInvolve()>99999)
			tv_involve.setText(99999+"+");
		btn_easy.setSelected(true);
		btn_advance.setSelected(false);
	}
	OnClickListener clickListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.iv_back:
				finish();
				break;
			case R.id.btn_easy:
				if(MODE == 0) return;
				getPrice();
				btn_easy.setSelected(true);
				btn_advance.setSelected(false);
				view_easy.setVisibility(View.VISIBLE);
				view_advance.setVisibility(View.GONE);
				ll_path.setVisibility(View.VISIBLE);
				layout_price.setVisibility(View.GONE);
				MODE = 0;
				break;
			case R.id.btn_advance:
				if(MODE == 1) return;
				getPrice();
				btn_easy.setSelected(false);
				btn_advance.setSelected(true);
				view_easy.setVisibility(View.GONE);
				view_advance.setVisibility(View.VISIBLE);
				ll_path.setVisibility(View.GONE);
				layout_price.setVisibility(View.VISIBLE);
				MODE = 1;
				break;
			case R.id.tv_lanren://懒人包
				Intent intentLazyBag = new Intent(EventDetailActivity.this, LazyBagActivity.class);
				intentLazyBag.putExtra("event", event);
				startActivity(intentLazyBag);
				break;
			case R.id.iv_tips_1:
				showDialog(event.getRule());
				break;
			case R.id.btn_buy://简易模式买入
				if(!judgeIsLogin()) return;
				isLimitPrice = false;//市价买入
				showBuyConfig(2, String.format("%.1f", event.getCurrPrice()), 10);
				
//				Intent intent1 = new Intent(EventDetailActivity.this, EventBuyActivity.class);
//				intent1.putExtra("buyOrSell", true);
//				intent1.putExtra("event", event);
//				startActivity(intent1);
				break;
			case R.id.btn_sell://简易模式卖空
				if(!judgeIsLogin()) return;
				isLimitPrice = false;//市价卖空
				showBuyConfig(4, String.format("%.1f", event.getCurrPrice()), 10);
				
//				Intent intent2 = new Intent(EventDetailActivity.this, EventBuyActivity.class);
//				intent2.putExtra("buyOrSell", false);
//				intent2.putExtra("event", event);
//				startActivity(intent2);
				break;
			case R.id.ll_scroll:
				hideSoftInputView();
				break;
			case R.id.tv_myHode://我的持仓
				if(!judgeIsLogin()) return;
				startActivity(new Intent(EventDetailActivity.this, MyHoldActivity.class));
				break;
			}
		}
	};

	//初始化价格数据
	private void initPrice(EventPriceDAOInfo info){
		//价格图
		int[] pathData = new int[info.getPrices().size()];
		for(int i = 0;i<info.getPrices().size();i++){
			EventPriceDAO eventPriceDAO = info.getPrices().get(i);
			pathData[i] = (int) eventPriceDAO.getPrice();
		}
		ll_path.removeAllViews();
		View view = LayoutInflater.from(this).inflate(R.layout.view_pathview, null, false);
		PathView pathView = (PathView) view.findViewById(R.id.pathView);
		pathView.setXCount(100, 5);
		pathView.setType(PathView.OTHER);
		pathView.setDate(pathData);
		ll_path.addView(view);
		//价格三等对比
		List<EventBuyDAO> buys = info.getBuys();
		List<EventSellDAO> sells = info.getSells();
		for(int i = 0;i<buys.size();i++){
			tv_buys[i].setText(buys.get(i).getNum()+"  件  "+String.format("%.1f", buys.get(i).getPrice()));
			if(buys.get(i).getNum() > 9999)
				tv_buys[i].setText("9999+  件  "+String.format("%.1f", buys.get(i).getPrice()));
		}
		for(int j = 0;j<sells.size();j++){
			tv_sells[j].setText(String.format("%.1f", sells.get(j).getPrice())+"  "+sells.get(j).getNum()+"  件  ");
			if(sells.get(j).getNum() > 9999)
				tv_sells[j].setText(String.format("%.1f", sells.get(j).getPrice())+"  9999+  件  ");
		}
		
	}
	//参考消息
	private void initReferenceNews(List<ReferenceDAO> list){
		if(list == null) return;
		for(int i = 0;i<list.size();i++){
			final ReferenceDAO referenceDAO = list.get(i);
			TextView tv_news = new TextView(this);
			LayoutParams layoutParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
			tv_news.setBackground(getResources().getDrawable(R.drawable.linearlayout_select));
			tv_news.setLayoutParams(layoutParams);
			tv_news.setText(i+1+"."+referenceDAO.getTitle());
			tv_news.setTextColor(getResources().getColor(R.color.text_color_9));
			tv_news.setTextSize(13f);
			tv_news.setGravity(Gravity.CENTER_VERTICAL);
			tv_news.setPadding(0, 20, 0, 0);
			tv_news.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					Intent intent = new Intent(EventDetailActivity.this, RefrenceNewsActivity.class);
					intent.putExtra("news", referenceDAO);
					startActivity(intent);
				}
			});
			ll_refrence_news.addView(tv_news, i);
		}
		ll_refrence_news.invalidate();
	}
	
	//判断高级模式下订单类型
	private int getOrderType(boolean IsAdvanceBuy,boolean isLimitPrice){
		int type = 1;
		if(IsAdvanceBuy){//买入
			if(isLimitPrice){//限价买入
				type = 1;
			}else{//市价买入
				type = 2;
			}
		}else{//卖空
			if(isLimitPrice){//限价卖空
				type = 3;
			}else{//市价卖空
				type = 4;
			}
		}
		return type;
	}
	//获取事件的价格走势
	private void getPrice(){
		httpResponseUtils.postJson(httpPostParams.getPostParams(
				PostMethod.query_single_event.name(), PostType.event.name(), 
				httpPostParams.query_single_event(event_id, SingleEventScope.price.name())), 
				EventPriceInfo.class, 
				new PostCommentResponseListener() {
					@Override
					public void requestCompleted(Object response) throws JSONException {
						if(response == null) return;
						EventPriceInfo eventPriceInfo = (EventPriceInfo) response;
						initPrice(eventPriceInfo.getPrice());
						SystemTimeUtile.getInstance(eventPriceInfo.getCurr_time()).setSystemTime(eventPriceInfo.getCurr_time());
						setCountDown(eventPriceInfo.getCurr_time());
					}
				});
	}
	//获取事件相关信息
	private void getEvetnRealted(){
		httpResponseUtils.postJson(httpPostParams.getPostParams(
				PostMethod.query_single_event.name(), PostType.event.name(), 
				httpPostParams.query_single_event(event_id, SingleEventScope.related.name())), 
				EventRelatedInfo.class, 
				new PostCommentResponseListener() {
					@Override
					public void requestCompleted(Object response) throws JSONException {
						if(response == null) return;
						EventRelatedInfo eventRelatedInfo = (EventRelatedInfo) response;
						initReferenceNews(eventRelatedInfo.getRelated().getRefer().getRefer());
					}
				});
	}
	//添加订单
	private void addOrder(int type,String price,int num){
		progressDialog.progressDialog();
		httpResponseUtils.postJson(httpPostParams.getPostParams(
				PostMethod.add_order .name(), PostType.order .name(), 
				httpPostParams.add_order(preferenceUtil.getID()+"", preferenceUtil.getUUid(), type+"", price, num+"", event_id)), 
				BaseModel.class, 
				new PostCommentResponseListener() {
			@Override
			public void requestCompleted(Object response) throws JSONException {
				progressDialog.cancleProgress();
				if(response == null) return;
				//交易成功
				MyToast.showToast(EventDetailActivity.this, "正在为您撮合订单，请到我的持仓查看订单状态。",1);
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
	//事件购买确认提示
	private void showBuyConfig(final int type,final String price,final int num){
		View view = LayoutInflater.from(this).inflate(R.layout.view_event_order_config, null, false);
		TextView tv_cancel = (TextView) view.findViewById(R.id.tv_cancel);
		TextView tv_configMsg = (TextView) view.findViewById(R.id.tv_configMsg);
		String configMsg = "";
		switch (type) {//type 1-限价买进 2-市价买进 3-限价卖空 4-市价卖空
		case 1:
			configMsg = "确定以限价" + price + "未币买进" + num + "件";
			break;
		case 2:
			configMsg = "确定以市价" + "买进" + num + "件";
			break;
		case 3:
			configMsg = "确定以限价" + price + "未币卖空" + num + "件";
			break;
		case 4:
			configMsg = "确定以市价" + "卖空" + num + "件";
			break;
		}
		tv_configMsg.setText(configMsg);
		Button btn_config = (Button) view.findViewById(R.id.btn_submit);
		final Dialog dialog = DialogShow.showDialog(this, view,Gravity.BOTTOM);
		tv_cancel.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
			dialog.cancel();
			}
		});
		btn_config.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				addOrder(type, price, num);
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
	MyThread thread;
	//倒计时
	private void setCountDown(Long currentTime){
		if(event.getStatusStr().equals("交易中")){
			tv_status.setText(LongTimeUtil.longTimeUtil(event.getTradeTime() - currentTime));
			thread = new MyThread(tv_status, event.getTradeTime() - currentTime, 1, handler);
			thread.start();
		}
	}
	//事件清算中不可下单
	private void judgeIsClear(){
		if(event.getStatusStr().equals("清算中")){
			btn_advance.setSelected(true);
			btn_advance.setClickable(false);
			btn_easy.setClickable(false);
			btn_easy_buy.setClickable(false);
			btn_easy_sell.setClickable(false);
			btn_advance.setAlpha(0.4f);
			btn_easy.setAlpha(0.4f);
			btn_easy_buy.setBackground(getResources().getDrawable(R.drawable.btn_gray));
			btn_easy_sell.setBackground(getResources().getDrawable(R.drawable.btn_gray));
		}
	}
	@Override 
	protected void onDestroy() {
		super.onDestroy();
		if(thread!=null){
			thread.stopThread();
			thread = null;
		}
		if(imageLoader !=null){
			imageLoader.clearMemoryCache();
			imageLoader.cancelDisplayTask(iv_image);
		}
		imageLoader = null;
		Bitmap bitmap = iv_image.getDrawingCache();
		if(bitmap!=null && !bitmap.isRecycled()){
			bitmap.recycle();
			bitmap = null;
		}
		iv_image = null;
		System.gc();
	}
}

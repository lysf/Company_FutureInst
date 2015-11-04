package com.futureinst.home.eventdetail;

import java.text.DecimalFormat;
import java.util.List;

import org.json.JSONException;

import com.futureinst.R;
import com.futureinst.baseui.BaseActivity;
import com.futureinst.global.Content;
import com.futureinst.model.basemodel.BaseModel;
import com.futureinst.model.homeeventmodel.EventBuyDAO;
import com.futureinst.model.homeeventmodel.EventPriceDAOInfo;
import com.futureinst.model.homeeventmodel.EventSellDAO;
import com.futureinst.model.homeeventmodel.QueryEventDAO;
import com.futureinst.model.order.SingleEventClearDAO;
import com.futureinst.net.PostCommentResponseListener;
import com.futureinst.net.PostMethod;
import com.futureinst.net.PostType;
import com.futureinst.newbieguide.GuideClickInterface;
import com.futureinst.newbieguide.NewbieGuide;
import com.futureinst.newbieguide.NewbieGuide2;
import com.futureinst.utils.DialogShow;
import com.futureinst.utils.MyToast;
import com.futureinst.utils.Utils;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class EventBuyActivity extends BaseActivity {
	private SingleEventClearDAO singleEventClearDAO;
	private LinearLayout ll_detail_buy,ll_detail_sell;
	private float asset;
	private QueryEventDAO event;
	private EventPriceDAOInfo priceDAOInfo;
	private EditText et_price,et_num;
	private Button submit;
	private boolean isBuy;
	private TextView[] tv_buys_1,tv_buys_2,tv_buys_3,tv_sells_1,tv_sells_2,tv_sells_3;
	private LinearLayout ll_event_buy;
	private TextView tv_total_1,tv_total_2,tv_total_3,tv_total_4;
	private String order_tips_1,order_tips_2,order_tips_3,order_tips_4;
	private ImageView iv_price_sub,iv_price_add;
	private ImageView iv_number_sub,iv_number_add;
	private String price,num;
	private TextView tv_tip;
	private TableRow tableRow4;
	private int number = 10;
	@Override
	protected void localOnCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		setTitle("预测交易");
		getLeftImageView().setImageDrawable(getResources().getDrawable(R.drawable.back));
		setContentView(R.layout.event_buy);
		initView();
		initPriceView();
		initPrice(priceDAOInfo);
		getTotal();
		showGuide();
	}
	private void getTotal(){
		if(!judgeIsUse()){
			tv_total_1.setVisibility(View.GONE);
			tv_total_2.setVisibility(View.GONE);
			tv_total_3.setVisibility(View.GONE);
			tv_total_4.setVisibility(View.GONE);
			return;
		}
		float price = Float.valueOf(et_price.getText().toString());
		int num = Integer.valueOf(et_num.getText().toString());
	//你选择的是看好（看涨）/不看好（看跌），越低价成交赚越多/越高价成交赚越多，但不利于成交
		
		if(isBuy){
			order_tips_1 = "提示：\n1.成交价越低自然赚得越多，不过若定价太低当心会没人和你交易哦\n"
					+ "2.若事件发生（价格涨至100.00），则你获利：";
			order_tips_2 = "  （100 - "+String.format("%.2f", price)+
					"）*"+num+" = "+String.format("%.2f", (100-price)*num);
			order_tips_3 = "3.若事件不发生（价格跌至0.00），则你亏损 :";
			order_tips_4 = " 	"+String.format("%.2f", price)+" * "+num+" = "+String.format("%.2f", price*num);
		}else{
			order_tips_1 = "提示：\n1.成交价越高自然赚得越多，不过若定价太高当心会没人和你交易哦\n"
					+ "2.若事件不发生（价格跌至0.00），则你获利：";
			order_tips_2 = " 	" + String.format("%.2f", price)+" * "+num+" = "+String.format("%.2f", price*num);
			order_tips_3 = "3.若事件发生（价格涨至100.00），则你亏损：";
			order_tips_4 = "  （100-"+String.format("%.2f", price)+"） * "+num+" = "+String.format("%.2f", (100-price)*num);
		}
		tv_total_1.setText(order_tips_1);
		tv_total_2.setText(order_tips_2);
		tv_total_3.setText(order_tips_3);
		tv_total_4.setText(order_tips_4);
		tv_total_1.setVisibility(View.VISIBLE);
		tv_total_2.setVisibility(View.VISIBLE);
		tv_total_3.setVisibility(View.VISIBLE);
		tv_total_4.setVisibility(View.VISIBLE);
	}
	private void initView() {
		asset = preferenceUtil.getAsset();
		singleEventClearDAO = (SingleEventClearDAO) getIntent().getSerializableExtra("assure");
		if(singleEventClearDAO == null){
			singleEventClearDAO = new SingleEventClearDAO(0,0,0,0);
		}
		event = (QueryEventDAO) getIntent().getSerializableExtra("event");
		priceDAOInfo = (EventPriceDAOInfo) getIntent().getSerializableExtra("price");
		number = getIntent().getIntExtra("number", 10);
		if(number > 100) number = 100;
		
		isBuy = getIntent().getBooleanExtra("buyOrSell", false);
		ll_event_buy = (LinearLayout) findViewById(R.id.ll_event_buy);
		tv_total_1 = (TextView) findViewById(R.id.tv_total_1);
		tv_total_2 = (TextView) findViewById(R.id.tv_total_2);
		tv_total_3 = (TextView) findViewById(R.id.tv_total_3);
		tv_total_4 = (TextView) findViewById(R.id.tv_total_4);
		ll_detail_buy = (LinearLayout) findViewById(R.id.ll_detail_buy);
		ll_detail_sell = (LinearLayout) findViewById(R.id.ll_detail_sell);
		if(isBuy){
			ll_detail_buy.setBackground(null);
			ll_detail_sell.setOnClickListener(clickListener);
		}else{
			ll_detail_sell.setBackground(null);
			ll_detail_buy.setOnClickListener(clickListener);
		}
		tv_buys_1 = new TextView[3];
		tv_buys_2 = new TextView[3];
		tv_buys_3 = new TextView[3];
		tv_sells_1 = new TextView[3];
		tv_sells_2 = new TextView[3];
		tv_sells_3 = new TextView[3];
		et_price = (EditText) findViewById(R.id.et_price);
		et_num = (EditText) findViewById(R.id.et_num);
		submit = (Button) findViewById(R.id.btn_type);
		iv_price_sub = (ImageView) findViewById(R.id.price_sub);
		iv_price_add = (ImageView) findViewById(R.id.price_add);
		iv_number_sub = (ImageView) findViewById(R.id.number_sub);
		iv_number_add = (ImageView) findViewById(R.id.number_add);
		tv_tip = (TextView) findViewById(R.id.tv_tip);
		
		tableRow4 = (TableRow) findViewById(R.id.tableRow4);
		tableRow4.setVisibility(View.GONE);
		et_price.setText(String.format("%.2f", event.getCurrPrice()));
		et_num.setText(number+"");
		if(isBuy){
			submit.setText("确认看多");
			submit.setBackground(getResources().getDrawable(R.drawable.btn_detail_buy_back));
			tv_tip.setText(getResources().getString(R.string.buy_tip_buy));
		}else{
			submit.setText("确认看空");
			submit.setBackground(getResources().getDrawable(R.drawable.btn_detail_sell_back));
			tv_tip.setText(getResources().getString(R.string.buy_tip_sell));
		}
		tv_total_2.setTextColor(getResources().getColor(R.color.gain_red));
		tv_total_4.setTextColor(getResources().getColor(R.color.gain_blue));
		et_price.addTextChangedListener(new TextWatcher() {
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				if(!TextUtils.isEmpty(s)){
					if(!Utils.checkIsNumber(s.subSequence(0, 1).toString())){
						et_price.setText(String.format("%.1f", event.getCurrPrice()));
						return;
					}
					float price = Float.valueOf(s.toString());
					if(price > 99.9){
						MyToast.getInstance().showToast(EventBuyActivity.this, "价格超限", 0);
						et_price.setText(String.format("%.1f", event.getCurrPrice()));
						return;
					}
				}
					getTotal();
			}
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {}
			@Override
			public void afterTextChanged(Editable s) {}
		});
		et_num.addTextChangedListener(new TextWatcher() {
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				if(!TextUtils.isEmpty(s)){
					if(!Utils.checkIsNumber(s.subSequence(0, 1).toString()) || s.subSequence(0, 1).toString().equals("0")){
						MyToast.getInstance().showToast(EventBuyActivity.this, "件数不能为0", 0);
						et_num.setText("1");
						return;
					}
					int number = Integer.valueOf(s.toString());
					if(number > 100){
						MyToast.getInstance().getInstance().showToast(EventBuyActivity.this, "件数超限", 0);
						et_num.setText("10");
						return;
					}
				}
					getTotal();
			}
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {}
			@Override
			public void afterTextChanged(Editable s) {}
		});
		
		submit.setOnClickListener(clickListener);
		ll_event_buy.setOnClickListener(clickListener);
		iv_price_sub.setOnClickListener(clickListener);
		iv_price_add.setOnClickListener(clickListener);
		iv_number_sub.setOnClickListener(clickListener);
		iv_number_add.setOnClickListener(clickListener);
	}
	
	OnClickListener clickListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.btn_type:
				if(judgeData()){
					String currPrice = et_price.getText().toString();
					int currNum = Integer.valueOf(et_num.getText().toString());
					float assure = CalculateAssureUtil.calculateNeedAssure(isBuy, currNum, Float.valueOf(currPrice), singleEventClearDAO.getBuyNum(), singleEventClearDAO.getBuyPrice(),
							singleEventClearDAO.getSellNum(), singleEventClearDAO.getSellPrice());
					Log.i(TAG, "--------------------assure="+assure+"-----asset="+asset);
					if(assure > asset){
						MyToast.getInstance().showToast(EventBuyActivity.this, "保证金不足，无法下单", 0);
						return;
					}
					if(isBuy){
						showBuyConfig(1,currPrice , currNum);
					}else{
						showBuyConfig(3, currPrice, currNum);
					}
				}
				break;
			case R.id.ll_event_buy:
				hideSoftInputView();
				break;
			case R.id.price_sub://价格 -
				price = et_price.getText().toString();
				if(TextUtils.isEmpty(price)) return;
				float priceSub = Float.valueOf(price);
				if(priceSub - 0.1 > 0){
					priceSub -= 0.1; 
					et_price.setText(String.format("%.1f", priceSub));
				}
				if(judgeIsUse()){
					getTotal();
				}
				break;
			case R.id.price_add://价格 +
				price = et_price.getText().toString();
				if(TextUtils.isEmpty(price)) price = "0";
				float priceAdd = Float.valueOf(price);
				if(priceAdd + 0.1 < 100){
					priceAdd += 0.1; 
					et_price.setText(String.format("%.1f", priceAdd));
				}
				if(judgeIsUse()){
					getTotal();
				}
				break;
			case R.id.number_sub://数量 -
				num = et_num.getText().toString();
				if(TextUtils.isEmpty(num)) return;
				int numberSub = Integer.valueOf(num);
				if(numberSub - 1 > 0){
					numberSub -= 1; 
					et_num.setText(numberSub+"");
				}
				if(judgeIsUse()){
					getTotal();
				}
				break;
			case R.id.number_add://数量+
				num = et_num.getText().toString();
				if(TextUtils.isEmpty(num)) num = "0";
				int numberAdd = Integer.valueOf(num);
				if(numberAdd + 1 < 101){
					numberAdd += 1; 
					et_num.setText(numberAdd+"");
				}
				if(judgeIsUse()){
					getTotal();
				}
				break;
			case R.id.ll_detail_buy://三档看好
				if(event == null || 
				priceDAOInfo.getBuys() == null || priceDAOInfo.getBuys().size() == 0) return; 
				int number_buy = priceDAOInfo.getBuys().get(0).getNum();
				if(number_buy>100) number_buy = 100;
				et_num.setText(number_buy+"");
				et_price.setText(String.format("%.1f", priceDAOInfo.getBuys().get(0).getPrice()));
				break;
			case R.id.ll_detail_sell://三档不看好
				if(event == null || 
						priceDAOInfo.getSells() == null || priceDAOInfo.getSells().size() == 0) return;
				int number_sell = priceDAOInfo.getSells().get(0).getNum();
						if(number_sell>100) number_sell = 100;
				et_num.setText(number_sell+"");
				et_price.setText(String.format("%.1f", priceDAOInfo.getSells().get(0).getPrice()));
				break;
			}
		}
	};
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
	}
	//初始化价格数据
		private void initPrice(EventPriceDAOInfo info){
			//价格三等对比
			List<EventBuyDAO> buys = info.getBuys();
			List<EventSellDAO> sells = info.getSells();
			DecimalFormat df= new DecimalFormat("##0.00");
			for(int i = 0;i<buys.size();i++){
				tv_buys_1[i].setText(String.format("%3d", buys.get(i).getNum())+"  份");
				tv_buys_2[i].setVisibility(View.INVISIBLE);
				tv_buys_3[i].setText(df.format(buys.get(i).getPrice()));
//				if(buys.get(i).getNum() > 9999)
//					tv_buys[i].setText("9999+  份  "+String.format("%.2f", buys.get(i).getPrice()));
			}
			for(int j = 0;j<sells.size();j++){
				tv_sells_1[j].setText(df.format(sells.get(j).getPrice()));
				tv_sells_2[j].setVisibility(View.INVISIBLE);
				tv_sells_3[j].setText(String.format("%3d", sells.get(j).getNum())+"  份");
//				if(sells.get(j).getNum() > 9999)
//					tv_sells[j].setText(String.format("%.2f", sells.get(j).getPrice())+"  9999+  份  ");
			}
			
		}
		//添加订单
		private void addOrder(final int type,String price,int num){
			progressDialog.progressDialog();
			Content.isPull = true;
			httpResponseUtils.postJson(httpPostParams.getPostParams(
					PostMethod.add_order .name(), PostType.order .name(), 
					httpPostParams.add_order(preferenceUtil.getID()+"", preferenceUtil.getUUid(), type+"", price, num+"", event.getId()+"","pro")),
					BaseModel.class, 
					new PostCommentResponseListener() {
				@Override
				public void requestCompleted(Object response) throws JSONException {
					progressDialog.cancleProgress();
					Content.isPull = false;
					if(response == null) return;
					//交易成功
					Intent intent = new Intent("priceClear");
					intent.putExtra("attitude",type);
					sendBroadcast(intent);
					finish();
				}
			});
		}
		//事件购买确认提示
		private void showBuyConfig(final int type,final String price,final int num){
			View view = LayoutInflater.from(this).inflate(R.layout.view_event_order_config, null, false);
			Button btn_cancel = (Button) view.findViewById(R.id.btn_cancel);
			TextView tv_configMsg = (TextView) view.findViewById(R.id.tv_configMsg);
			String configMsg = "";
			switch (type) {//type 1-限价买进 2-市价买进 3-限价卖空 4-市价卖空
			case 1:
//				configMsg = "确定以" + price + "未币的价格看好" + num + "份事件（"+event.getTitle()+"）吗？";
				configMsg = "确定以价格" + price + "，看好" + num + "份";
				break;
//			case 2:
//				configMsg = "确定以市价" + "买进" + num + "份";
//				break;
			case 3:
				configMsg = "确定以价格" + price + "，不看好" + num + "份";
				break;
//			case 4:
//				configMsg = "确定以市价" + "卖空" + num + "份";
//				break;
			}
			tv_configMsg.setText(configMsg);
			Button btn_config = (Button) view.findViewById(R.id.btn_submit);
			final Dialog dialog = DialogShow.showDialog(this, view,Gravity.CENTER);
			btn_cancel.setOnClickListener(new OnClickListener() {
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
		private boolean judgeData(){
			if(TextUtils.isEmpty(et_price.getText().toString()) || et_price.getText().toString().equals("0")){
				MyToast.getInstance().showToast(this, "请输入价格", 0);
				return false;
			}
			if(TextUtils.isEmpty(et_num.getText().toString())){
				MyToast.getInstance().showToast(this, "请输入件数", 0);
				return false;
			}
			return true;
		}
		private boolean judgeIsUse(){
			if(TextUtils.isEmpty(et_price.getText().toString())){
				return false;
			}
			if(TextUtils.isEmpty(et_num.getText().toString())){
				return false;
			}
			return true;
		
		}
		
		//显示新手引导
		 private void showGuide(){
			 if(!preferenceUtil.getGuide4()){
				 new NewbieGuide2(this, true,3);
				 preferenceUtil.setGuide4();
			 }
			 
		 }
}

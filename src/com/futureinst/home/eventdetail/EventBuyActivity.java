package com.futureinst.home.eventdetail;

import java.util.List;

import org.json.JSONException;

import com.futureinst.R;
import com.futureinst.baseui.BaseActivity;
import com.futureinst.model.basemodel.BaseModel;
import com.futureinst.model.homeeventmodel.EventBuyDAO;
import com.futureinst.model.homeeventmodel.EventPriceDAOInfo;
import com.futureinst.model.homeeventmodel.EventSellDAO;
import com.futureinst.model.homeeventmodel.QueryEventDAO;
import com.futureinst.net.PostCommentResponseListener;
import com.futureinst.net.PostMethod;
import com.futureinst.net.PostType;
import com.futureinst.utils.DialogShow;
import com.futureinst.utils.MyToast;
import com.futureinst.utils.Utils;

import android.app.Dialog;
import android.os.Bundle;
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
import android.widget.TextView;

public class EventBuyActivity extends BaseActivity {
	private QueryEventDAO event;
	private EventPriceDAOInfo priceDAOInfo;
	private EditText et_price,et_num;
	private Button submit;
	private boolean isBuy;
	private TextView[] tv_buys,tv_sells;
	private LinearLayout ll_event_buy;
	private TextView tv_total;
	private String order_tips;
	private ImageView iv_price_sub,iv_price_add;
	private ImageView iv_number_sub,iv_number_add;
	private String price,num;
	private TextView tv_tip;
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
	}
	private void getTotal(){
		if(!judgeIsUse()){
			tv_total.setVisibility(View.GONE);
			return;
		}
		float price = Float.valueOf(et_price.getText().toString());
		int num = Integer.valueOf(et_num.getText().toString());
	
		if(isBuy){
			order_tips = "你选择的是看好，也即看涨。\n如果事件发生，价格涨至100，则你获利（100 - "+String.format("%.1f", price)+
					"）*"+num+" = "+String.format("%.1f", (100-price)*num)+
					"；\n若事件不发生，价格跌至0，则你亏损 "+String.format("%.1f", price)+" * "+num+" = "+String.format("%.1f", price*num)+"。";
		}else{
			order_tips = "你选择的是不看好，也即看跌。\n如果事件不发生，价格跌至0，则你获利 "+String.format("%.1f", price)+" * "+num+" = "+String.format("%.1f", price*num)
		+"；\n若事件发生，价格涨至100，则你亏损 （100-"+String.format("%.1f", price)+"） * "+num+" = "+String.format("%.1f", (100-price)*num)+"。";
		}
				
		tv_total.setText(order_tips);
		tv_total.invalidate();
		tv_total.setVisibility(View.VISIBLE);
	}
	private void initView() {
		event = (QueryEventDAO) getIntent().getSerializableExtra("event");
		priceDAOInfo = (EventPriceDAOInfo) getIntent().getSerializableExtra("price");
		isBuy = getIntent().getBooleanExtra("buyOrSell", false);
		ll_event_buy = (LinearLayout) findViewById(R.id.ll_event_buy);
		tv_total = (TextView) findViewById(R.id.tv_total);
		tv_buys = new TextView[3];
		tv_sells = new TextView[3];
		et_price = (EditText) findViewById(R.id.et_price);
		et_num = (EditText) findViewById(R.id.et_num);
		submit = (Button) findViewById(R.id.btn_type);
		iv_price_sub = (ImageView) findViewById(R.id.price_sub);
		iv_price_add = (ImageView) findViewById(R.id.price_add);
		iv_number_sub = (ImageView) findViewById(R.id.number_sub);
		iv_number_add = (ImageView) findViewById(R.id.number_add);
		tv_tip = (TextView) findViewById(R.id.tv_tip);
		
//		et_price.setText(String.format("%.1f", event.getCurrPrice()));
		et_num.setText("10");
		if(isBuy){
			submit.setText("看好");
			submit.setBackground(getResources().getDrawable(R.drawable.btn_buy_back));
			tv_tip.setText(getResources().getString(R.string.buy_tip_buy));
			tv_total.setTextColor(getResources().getColor(R.color.gain_red));
//			order_tips = getResources().getString(R.string.order_look_good);
		}else{
			submit.setText("不看好");
			submit.setBackground(getResources().getDrawable(R.drawable.btn_sell_back));
			tv_tip.setText(getResources().getString(R.string.buy_tip_sell));
			tv_total.setTextColor(getResources().getColor(R.color.gain_blue));
//			order_tips = getResources().getString(R.string.order_look_bad);
		}
		et_price.addTextChangedListener(new TextWatcher() {
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				if(!TextUtils.isEmpty(s)){
					if(!Utils.checkIsNumber(s.subSequence(0, 1).toString())){
//						et_price.setText(String.format("%.1f", event.getCurrPrice()));
						return;
					}
					float price = Float.valueOf(s.toString());
					if(price > 99.9){
						MyToast.showToast(EventBuyActivity.this, "价格超限", 0);
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
//						et_num.setText("1");
						return;
					}
					int number = Integer.valueOf(s.toString());
					if(number > 100){
						MyToast.showToast(EventBuyActivity.this, "件数超限", 0);
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
					if(isBuy){
						showBuyConfig(1, et_price.getText().toString(), Integer.valueOf(et_num.getText().toString()));
					}else{
						showBuyConfig(3, et_price.getText().toString(), Integer.valueOf(et_num.getText().toString()));
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
			}
		}
	};
	private void initPriceView(){
		tv_buys[0] = (TextView) findViewById(R.id.tv_buy_1);
		tv_buys[1] = (TextView) findViewById(R.id.tv_buy_2);
		tv_buys[2] = (TextView) findViewById(R.id.tv_buy_3);
		tv_sells[0] = (TextView) findViewById(R.id.tv_sell_1);
		tv_sells[1] = (TextView) findViewById(R.id.tv_sell_2);
		tv_sells[2] = (TextView) findViewById(R.id.tv_sell_3);
	}
	//初始化价格数据
		private void initPrice(EventPriceDAOInfo info){
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
		//添加订单
		private void addOrder(int type,String price,int num){
			progressDialog.progressDialog();
			httpResponseUtils.postJson(httpPostParams.getPostParams(
					PostMethod.add_order .name(), PostType.order .name(), 
					httpPostParams.add_order(preferenceUtil.getID()+"", preferenceUtil.getUUid(), type+"", price, num+"", event.getId()+"")), 
					BaseModel.class, 
					new PostCommentResponseListener() {
				@Override
				public void requestCompleted(Object response) throws JSONException {
					progressDialog.cancleProgress();
					if(response == null) return;
					//交易成功
//					MyToast.showToast(EventBuyActivity.this, "正在为您撮合订单，请到我的持仓查看订单状态。",1);
					finish();
				}
			});
		}
		//事件购买确认提示
		private void showBuyConfig(final int type,final String price,final int num){
			View view = LayoutInflater.from(this).inflate(R.layout.view_event_order_config, null, false);
			TextView tv_cancel = (TextView) view.findViewById(R.id.tv_cancel);
			TextView tv_configMsg = (TextView) view.findViewById(R.id.tv_configMsg);
			String configMsg = "";
			switch (type) {//type 1-限价买进 2-市价买进 3-限价卖空 4-市价卖空
			case 1:
				configMsg = "确定以价格" + price + "，看好" + num + "件";
				break;
			case 2:
				configMsg = "确定以市价" + "买进" + num + "件";
				break;
			case 3:
				configMsg = "确定以价格" + price + "，不看好" + num + "件";
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
		private boolean judgeData(){
			if(TextUtils.isEmpty(et_price.getText().toString())){
				MyToast.showToast(this, "请输入价格", 0);
				return false;
			}
			if(TextUtils.isEmpty(et_num.getText().toString())){
				MyToast.showToast(this, "请输入件数", 0);
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
}

package com.futureinst.home.hold;

import java.util.ArrayList;
import java.util.List;

import com.futureinst.R;
import com.futureinst.model.order.DealOrderDAO;
import com.futureinst.utils.TimeUtil;
import com.futureinst.utils.ViewHolder;

import android.content.Context;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.text.style.TextAppearanceSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

public class HoldDealAdapter extends BaseAdapter {
	private Context context;
	private int gainBlueColor;
	private int gainRedColor;
	private List<DealOrderDAO> list;
	public HoldDealAdapter(Context context){
		this.context = context;
		gainBlueColor = context.getResources().getColor(R.color.gain_blue);
		gainRedColor = context.getResources().getColor(R.color.gain_red);
		list = new ArrayList<DealOrderDAO>();
	}
	public void refresh(List<DealOrderDAO> list){
		this.list = list;
		notifyDataSetChanged();
	}
	public void setList(List<DealOrderDAO> list){
		this.list.addAll(list);
		notifyDataSetChanged();
	}
	public List<DealOrderDAO> getList() {
		return this.list;
	}
	@Override
	public int getCount() {
		return list!=null?list.size():0;
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return list!=null?list.get(arg0):null;
	}

	@Override
	public long getItemId(int arg0) {
		return arg0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup arg2) {
		if(convertView == null)
			convertView = LayoutInflater.from(context).inflate(R.layout.item_hold, null, false);
			
			DealOrderDAO item = list.get(position);

		LinearLayout ll_simple = ViewHolder.get(convertView,R.id.ll_simple);
		LinearLayout ll_pref = ViewHolder.get(convertView,R.id.ll_pref);
		ll_pref.setVisibility(View.VISIBLE);

		View div = ViewHolder.get(convertView,R.id.div);
		div.setVisibility(View.VISIBLE);
		if(position == 0){
			div.setVisibility(View.GONE);
		}

		TextView tv_simple_hold_buy = ViewHolder.get(convertView,R.id.tv_simple_hold_buy);
		TextView tv_simple_hold_sell = ViewHolder.get(convertView,R.id.tv_simple_hold_sell);
		if(item.getPk0Volume() > 0){
			ll_simple.setVisibility(View.VISIBLE);
			tv_simple_hold_buy.setVisibility(View.VISIBLE);
			String buyPK = "你投入"+(int)item.getPk0Volume()+"未币看好";

			SpannableStringBuilder stringBuilder = new SpannableStringBuilder(buyPK);
			ForegroundColorSpan span = new ForegroundColorSpan(gainRedColor);
			stringBuilder.setSpan(span,buyPK.length()-2,buyPK.length(),Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
			tv_simple_hold_buy.setText(stringBuilder);
			if(item.getPk1Volume() >0){
				String sellPK = "又投入"+(int)item.getPk1Volume()+"未币不看好";
				SpannableStringBuilder stringBuilderSell = new SpannableStringBuilder(sellPK);
				ForegroundColorSpan spanSell = new ForegroundColorSpan(gainBlueColor);
				stringBuilderSell.setSpan(spanSell,sellPK.length()-3,sellPK.length(),Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
				tv_simple_hold_sell.setText(stringBuilderSell);
				tv_simple_hold_sell.setVisibility(View.VISIBLE);

			}else{
				tv_simple_hold_sell.setVisibility(View.GONE);
			}
		}else{
			tv_simple_hold_buy.setVisibility(View.GONE);
			if(item.getPk1Volume() > 0){
				String sellPK = "你投入"+(int)item.getPk1Volume()+"未币不看好";
				SpannableStringBuilder stringBuilderSell = new SpannableStringBuilder(sellPK);
				ForegroundColorSpan spanSell = new ForegroundColorSpan(gainBlueColor);
				stringBuilderSell.setSpan(spanSell,sellPK.length()-3,sellPK.length(),Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

				tv_simple_hold_sell.setText(stringBuilderSell);
				tv_simple_hold_sell.setVisibility(View.VISIBLE);
			}else{
				ll_simple.setVisibility(View.GONE);
			}
		}




			TextView tv_status = ViewHolder.get(convertView, R.id.tv_attitude);
			String sttaus = "此预测将于"+TimeUtil.longToString(item.getEvent().getClearTime(), TimeUtil.FORMAT_DATE_TIME_SECOND)+"清算盈亏";
			tv_status.setText(sttaus);
			tv_status.setVisibility(View.VISIBLE);
			TextView tv_deal = ViewHolder.get(convertView, R.id.tv_deal);
			TextView tv_currentPrice = ViewHolder.get(convertView, R.id.tv_price);
			TextView tv_gain = ViewHolder.get(convertView, R.id.tv_gain);
			tv_currentPrice.setText(String.format("%.2f", item.getEvent().getCurrPrice()));
			TextView tv_title = ViewHolder.get(convertView, R.id.tv_title);
			tv_title.setText(item.getEvent().getTitle());
			TextView tv_buy = ViewHolder.get(convertView, R.id.tv_hold_buy);
			String buy = "你看多"+String.format("%3d", item.getBuyNum())+"份，均价"+String.format("%.2f", Math.abs(item.getBuyPrice()))+"\t";

			tv_buy.setText(getSpannbleString(buy,"看多",gainRedColor));
			tv_buy.setVisibility(View.VISIBLE);
			TextView tv_sell = ViewHolder.get(convertView, R.id.tv_hold_sell);
			String sell = "又看空"+String.format("%3d", item.getSellNum())+"份，均价"+String.format("%.2f", Math.abs(item.getSellPrice()))+"\t";
			tv_sell.setText(getSpannbleString(sell,"看空",gainBlueColor));
			tv_sell.setVisibility(View.VISIBLE);
			if(item.getBuyNum() == 0){
				if(item.getSellNum() > 0){
					sell = "你看空"+String.format("%3d", item.getSellNum())+"份，均价"+String.format("%.2f", Math.abs(item.getSellPrice()))+"\t";
					tv_sell.setText(getSpannbleString(sell,"看空",gainBlueColor));
					tv_sell.setVisibility(View.VISIBLE);
					tv_buy.setVisibility(View.GONE);
				}else{
					ll_pref.setVisibility(View.GONE);
				}

			}else{
				if(item.getSellPrice()!=0){
					tv_sell.setVisibility(View.VISIBLE);
				}else{
					tv_sell.setVisibility(View.GONE);
				}
			}
			
			if(item.getEvent().getStatusStr() !=null && item.getEvent().getStatusStr().equals("已清算")){
				tv_status.setVisibility(View.GONE);
				tv_deal.setVisibility(View.VISIBLE);
				String gain ;
				if(item.getGain() < 0){
					gain = " - "+String.format("%.2f", Math.abs(item.getGain()));
					tv_gain.setTextColor(gainBlueColor);
				}else{
					gain = " + "+String.format("%.2f", Math.abs(item.getGain()));
					tv_gain.setTextColor(gainRedColor);
				}
				tv_gain.setText(gain);
			}else{
				tv_deal.setVisibility(View.GONE);
				tv_gain.setText("最新成交价");
				tv_gain.setTextColor(context.getResources().getColor(R.color.text_color_4));
			}
		return convertView;
	}

	private SpannableStringBuilder getSpannbleString(String text,String result,int color){
		SpannableStringBuilder stringBuilder = new SpannableStringBuilder(text);
		stringBuilder.setSpan(new ForegroundColorSpan(color),text.indexOf(result),text.indexOf(result)+result.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
		return stringBuilder;
	}
}

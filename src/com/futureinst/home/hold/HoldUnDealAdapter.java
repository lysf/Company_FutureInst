package com.futureinst.home.hold;

import java.util.ArrayList;
import java.util.List;

import com.futureinst.R;
import com.futureinst.model.order.UnDealOrderDAO;
import com.futureinst.utils.TimeUtil;
import com.futureinst.utils.ViewHolder;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class HoldUnDealAdapter extends BaseAdapter {
	private List<UnDealOrderDAO> list;
	private Context context;
	private String[] types;
	public HoldUnDealAdapter(Context context){
		this.context = context;
		list = new ArrayList<UnDealOrderDAO>();
		types = new String[]{"限价\n买进" ,"市价\n买进","限价\n卖空 ","市价\n卖空"};
	}
	public void setList(List<UnDealOrderDAO> list){
		this.list = list;
		notifyDataSetChanged();
	}
	public void removeIten(UnDealOrderDAO unDealOrderDAO){
		list.remove(unDealOrderDAO);
		notifyDataSetChanged();
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list!=null ? list.size() : 0;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return list!=null ? list.get(position) : null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if(convertView == null)
			convertView = LayoutInflater.from(context).inflate(R.layout.item_unhold, null);
		UnDealOrderDAO item = list.get(position);
		TextView tv_time = ViewHolder.get(convertView, R.id.tv_time);
//		tv_time.setText(context.getResources().getString(R.string.point)+TimeUtil.longToString(item.getCtime(), TimeUtil.FORMAT_DATE_TIME_SECOND));
		tv_time.setText("下单时间"+TimeUtil.longToString(item.getCtime(), TimeUtil.FORMAT_DATE_TIME_SECOND));
		TextView tv_price = ViewHolder.get(convertView, R.id.tv_price);
		tv_price.setText(String.format("%.2f", item.getEvent().getCurrPrice()));
		TextView tv_title = ViewHolder.get(convertView, R.id.tv_title);
		tv_title.setText(item.getEvent().getTitle());
		TextView tv_hold_buy = ViewHolder.get(convertView, R.id.tv_hold_buy);
		String hold_buy = "";
		if(item.getType() == 1 || item.getType() == 2){//买
			tv_hold_buy.setTextColor(context.getResources().getColor(R.color.gain_red));
			hold_buy = context.getResources().getString(R.string.unhold_1)+"\t"+String.format("%3d", item.getNum())+"份\t\t出价"+String.format("%.2f", item.getPrice())+"\t";
		}else{
			tv_hold_buy.setTextColor(context.getResources().getColor(R.color.text_blue));
			hold_buy = context.getResources().getString(R.string.unhold_2)+"\t"+String.format("%3d", item.getNum())+"份\t\t出价"+String.format("%.2f", item.getPrice())+"\t";
		}
		tv_hold_buy.setText(hold_buy);
		TextView tv_residue = ViewHolder.get(convertView, R.id.tv_residue);
		tv_residue.setText(context.getResources().getString(R.string.unhold_3)+"\t"+(String.format("%3d", item.getNum()-item.getDealNum()))+"份\t\t未成交");
		if(item.getNum()-item.getDealNum() ==0)
			tv_residue.setVisibility(View.GONE);
		TextView tv_tip = ViewHolder.get(convertView, R.id.tv_tip);
		tv_tip.setText("此预测于"+TimeUtil.longToString(item.getDeadTime(), TimeUtil.FORMAT_DATE_TIME_SECOND)+"自动取消");
		
		TextView tv_unfit = ViewHolder.get(convertView, R.id.tv_unfit);//保证金不足
		ImageView iv_cancel = ViewHolder.get(convertView, R.id.iv_cancel);
		
		return convertView;
	}

}

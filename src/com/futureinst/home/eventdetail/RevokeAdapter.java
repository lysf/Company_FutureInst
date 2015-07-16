package com.futureinst.home.eventdetail;

import java.util.ArrayList;
import java.util.List;

import com.futureinst.R;
import com.futureinst.model.order.UnDealOrderDAO;
import com.futureinst.utils.ViewHolder;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

public class RevokeAdapter extends BaseAdapter {
	private Context context;
	 private int mRightWidth = 0;
	 private List<UnDealOrderDAO> list;
	public RevokeAdapter(Context context,int mRightWidth) {
		this.context = context;
		this.mRightWidth = mRightWidth;
		list = new ArrayList<UnDealOrderDAO>();
	}
	public void setList(List<UnDealOrderDAO> list) {
		this.list = list;
		notifyDataSetChanged();
	}
	public void removeItem(UnDealOrderDAO item){
		this.list.remove(item);
		notifyDataSetChanged();
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list == null ? 0 : list.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return list == null ? null : list.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		if(convertView == null)
			convertView = LayoutInflater.from(context).inflate(R.layout.item_revoke, null);
		UnDealOrderDAO item = list.get(position);
		TextView tv_left = ViewHolder.get(convertView, R.id.tv_left);
		TextView tv_right = ViewHolder.get(convertView, R.id.tv_right);
		String left = null;
		String right = null;
		if(item.getType() == 1 || item.getType() == 2){//买
			left = context.getResources().getString(R.string.unhold_1)+"\t"+item.getNum()+"份\t以"+String.format("%.1f", item.getPrice());
		}else{
			left = context.getResources().getString(R.string.unhold_2)+"\t"+item.getNum()+"份\t以"+String.format("%.1f", item.getPrice());
		}
		right = "剩余\t\t"+(item.getNum()-item.getDealNum())+"份\t\t未成交";
		tv_left.setText(left);
		tv_right.setText(right);
		
		Button item_right = ViewHolder.get(convertView, R.id.btn_revoke);
		LinearLayout.LayoutParams lp = new LayoutParams(mRightWidth, LayoutParams.MATCH_PARENT);
		item_right.setLayoutParams(lp);
		item_right.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if(mListener != null){
					mListener.onRightItemClick(v, position);
				}
			}
		});
		
		return convertView;
	}
	 /**
     * 单击事件监听器
     */
    private onRightItemClickListener mListener = null;
    
    public void setOnRightItemClickListener(onRightItemClickListener listener){
    	mListener = listener;
    }

    public interface onRightItemClickListener {
        void onRightItemClick(View v, int position);
    }
}

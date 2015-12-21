package com.futureinst.home.forecast;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.futureinst.R;
import com.futureinst.utils.ViewHolder;

/**
 * Created by hao on 2015/12/16.
 */
public class OrderAdapter extends BaseAdapter {
    private Context context;
    private String[] orders;
    private int order = 0;
    public OrderAdapter(Context context,int order){
        this.context = context;
        this.order = order;
        orders = context.getResources().getStringArray(R.array.home_seond_title_order_string);
    }
    @Override
    public int getCount() {
        return orders == null ? 0 : orders.length;
    }

    @Override
    public Object getItem(int position) {
        return orders == null ? null :orders[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.item_home_pop_order,null);
        }
        TextView tv_order = ViewHolder.get(convertView,R.id.tv_order);
        tv_order.setText(orders[position]);
        if(order == position){
            tv_order.setSelected(true);
        }else{
            tv_order.setSelected(false);
        }
        return convertView;
    }
    public void setOrder(int order){
        this.order = order;
        notifyDataSetChanged();
    }

}

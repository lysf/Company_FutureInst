package com.futureinst.charge;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.futureinst.R;
import com.futureinst.model.charge.GoodsDAO;
import com.futureinst.utils.ViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hao on 2015/12/11.
 */
public class GoodsAdapter extends BaseAdapter {
    private Context context;
    private List<GoodsDAO> list;
    public GoodsAdapter(Context context){
        this.context = context;
        list = new ArrayList<>();
    }
    public void setList(List<GoodsDAO> list){
        this.list = list;
        notifyDataSetChanged();
    }
    @Override
    public int getCount() {
        return list == null ? 0 : list.size();
    }
    @Override
    public Object getItem(int position) {
        return list == null ? null : list.get(position);
    }
    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        GoodsDAO item = list.get(position);
        if(convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.item_charge_goods,null);
        }
        TextView tv_goods_name = ViewHolder.get(convertView,R.id.tv_goods_name);
        TextView tv_goods_price = ViewHolder.get(convertView,R.id.tv_goods_price);

        tv_goods_name.setText(item.getTradeCurrency()+"");
        tv_goods_price.setText(item.getCnyprice()+"元");
        return convertView;
    }
}

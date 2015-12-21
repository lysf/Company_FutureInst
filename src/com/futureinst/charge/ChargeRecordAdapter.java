package com.futureinst.charge;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.futureinst.R;
import com.futureinst.model.charge.PayOrderDAO;
import com.futureinst.utils.TimeUtil;
import com.futureinst.utils.ViewHolder;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by hao on 2015/12/14.
 */
public class ChargeRecordAdapter extends BaseAdapter {
    private Context context;
    private List<PayOrderDAO> list;
    public ChargeRecordAdapter(Context context){
        this.context = context;
        list = new ArrayList<>();
    }
    public void setList(List<PayOrderDAO> list){
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
        if(convertView == null)
            convertView = LayoutInflater.from(context).inflate(R.layout.item_charge_record,null);
        PayOrderDAO item = list.get(position);
        TextView tv_type = ViewHolder.get(convertView,R.id.tv_type);
        TextView tv_price = ViewHolder.get(convertView,R.id.tv_price);
        TextView tv_time = ViewHolder.get(convertView,R.id.tv_time);
        tv_type.setText("充值"+item.getGoods().getTradeCurrency()+"未币");
        tv_time.setText(TimeUtil.longToString(item.getCtime(),TimeUtil.FORMAT_DATE2_TIME));
        String formatted = NumberFormat.getCurrencyInstance(Locale.CHINA).format((Double.parseDouble(item.getGoods().getCnyprice())));
        tv_price.setText(formatted);
        return convertView;
    }
}

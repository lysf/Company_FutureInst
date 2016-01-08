package com.futureinst.home.userinfo.checkorder;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.futureinst.R;
import com.futureinst.utils.ViewHolder;

/**
 * Created by hao on 2016/1/7.
 */
public class CheckScreenAdapter extends BaseAdapter{
    private Context context;
    private String[] data_trade;//交易
    private String[] data_consume;//消费
    private String[] data_consume_key;
    private int flag = 0;
    private int index = 0;
    public CheckScreenAdapter(Context context,int flag){
        this.context = context;
        this.flag = flag;
        data_trade = new String[]{"全部","交易","任务","充值","转账","打赏","兑换","奖励"};
        data_consume = new String[]{"全部","交易","转账","打赏","兑换"};

        data_consume_key = new String[]{"0","1","4","5","6"};
    }
    public void setFlag(int flag){
        this.flag = flag;
        notifyDataSetChanged();
    }
    @Override
    public int getCount() {
        return flag == 0 ? data_trade.length : data_consume.length;
    }

    @Override
    public Object getItem(int position) {
        return flag == 0 ? data_trade[position] : data_consume[position];
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
        TextView tv_order = ViewHolder.get(convertView, R.id.tv_order);
        tv_order.setText((String)getItem(position));
        if(index == position){
            tv_order.setSelected(true);
        }else{
            tv_order.setSelected(false);
        }
        return convertView;
    }

    public void setIndex(int index){
        this.index = index;
        notifyDataSetChanged();
    }
    public String getKey(int position){
        return flag == 0 ? String.valueOf(position) : data_consume_key[position];
    }
}

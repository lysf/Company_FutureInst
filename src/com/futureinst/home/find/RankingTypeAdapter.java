package com.futureinst.home.find;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.futureinst.R;
import com.futureinst.utils.ViewHolder;
import com.futureinst.widget.IconSlidingRankingTabView;

/**
 * Created by hao on 2015/10/22.
 */
public class RankingTypeAdapter extends BaseAdapter {
    private Context context;
    private int index = 0;
    private String[] types;
    public RankingTypeAdapter(Context context){
        this.context = context;
        types = context.getResources().getStringArray(R.array.ranking_icon_title);
    }
    public void setIndex(int index){
        this.index = index;
        notifyDataSetChanged();
    }
    @Override
    public int getCount() {
        return types.length;
    }

    @Override
    public Object getItem(int i) {
        return types[i];
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if(view == null)
            view = LayoutInflater.from(context).inflate(R.layout.item_ranking_type,null,false);
        String[] item = types[i].split("-");
        IconSlidingRankingTabView iconView = ViewHolder.get(view, R.id.icon);
        TextView tv_type = ViewHolder.get(view,R.id.tv_type);
        iconView.setText(item[1]);
        tv_type.setText(item[0]);
        if(i == index){
            iconView.setTextColor(Color.parseColor("#52C3D1"));
            tv_type.setTextColor(Color.parseColor("#52C3D1"));
        }else{
            iconView.setTextColor(Color.parseColor("#000000"));
            tv_type.setTextColor(Color.parseColor("#4A4A4A"));
        }

        return view;
    }
}

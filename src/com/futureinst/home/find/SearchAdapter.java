package com.futureinst.home.find;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.futureinst.R;
import com.futureinst.model.record.UserRecordDAO;
import com.futureinst.roundimageutils.RoundedImageView;
import com.futureinst.utils.ImageLoadOptions;
import com.futureinst.utils.ViewHolder;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hao on 2015/12/16.
 */
public class SearchAdapter extends BaseAdapter {
    private Context context;
    private List<UserRecordDAO> list;
    public SearchAdapter(Context context){
        this.context = context;
        list = new ArrayList<>();
    }
    public void setList(List<UserRecordDAO> list){
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
        if(convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.item_user_search,null);
        }
        UserRecordDAO item = list.get(position);
        RoundedImageView iv_headImg = ViewHolder.get(convertView,R.id.iv_headImg);
        TextView tv_userName = ViewHolder.get(convertView,R.id.tv_userName);
        if(iv_headImg.getTag() == null || !iv_headImg.getTag().equals(item.getUser().getHeadImage())){
            iv_headImg.setTag(item.getUser().getHeadImage());
            ImageLoader.getInstance().displayImage(item.getUser().getHeadImage(),iv_headImg, ImageLoadOptions.getOptions(R.drawable.logo));
        }
        tv_userName.setText(item.getUser().getName());
        return convertView;
    }
}

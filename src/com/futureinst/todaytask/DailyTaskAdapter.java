package com.futureinst.todaytask;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.futureinst.R;
import com.futureinst.model.dailytask.TaskDAO;
import com.futureinst.utils.ViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hao on 2016/1/6.
 */
public class DailyTaskAdapter extends BaseAdapter {
    private Context context;
    private List<TaskDAO> list;
    public DailyTaskAdapter(Context context){
        this.context = context;
        list = new ArrayList<>();
    }
    public void setList(List<TaskDAO> list){
        this.list = list;
        notifyDataSetChanged();
    }
    public List<TaskDAO> getList(){
        return this.list;
    }
    //设置任务奖励已领取
    public void setAwarded(int position){
        list.get(position).setFlag(2);
        notifyDataSetChanged();
    }

    public void removeView(int position){
        list.remove(position);
        notifyDataSetChanged();
    }
    public void insertView(int position,TaskDAO object){
        list.add(position,object);
        notifyDataSetChanged();
    }
    @Override
    public int getCount() {
        return list==null ? 0 :list.size();
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
            convertView = LayoutInflater.from(context).inflate(R.layout.item_daily_task,null);

        ImageView iv_check_box = ViewHolder.get(convertView,R.id.iv_check_box);
        TextView tv_title = ViewHolder.get(convertView,R.id.tv_title);
        TextView tv_normal_icon = ViewHolder.get(convertView,R.id.tv_normal_icon);
        TextView tv_normal_progress = ViewHolder.get(convertView,R.id.tv_normal_progress);
        TextView tv_normal_status = ViewHolder.get(convertView,R.id.tv_normal_status);
        TaskDAO item = list.get(position);

        tv_title.setText(item.getTitle());
        tv_normal_icon.setText("+"+item.getAward()+"未币");
        switch (item.getFlag()){
            case 0://进行中
                tv_normal_progress.setVisibility(View.VISIBLE);
                tv_normal_status.setVisibility(View.GONE);
                iv_check_box.setImageResource(R.drawable.today_task_check_unclickable);
                tv_normal_icon.setSelected(false);
                tv_title.setSelected(false);
                tv_normal_progress.setText(item.getProgress());
                tv_normal_progress.setSelected(true);
                break;
            case 1://已完成，还未领取
                tv_normal_progress.setVisibility(View.GONE);
                tv_normal_status.setVisibility(View.VISIBLE);
                iv_check_box.setImageResource(R.drawable.today_task_check_clickable);
                tv_normal_icon.setSelected(false);
                tv_title.setSelected(false);
                tv_normal_status.setText("点击领取");
                tv_normal_status.setSelected(false);
                break;
            case 2://已领取
                tv_normal_progress.setVisibility(View.GONE);
                tv_normal_status.setVisibility(View.VISIBLE);
                iv_check_box.setImageResource(R.drawable.today_task_check_complete);
                tv_normal_icon.setSelected(true);
                tv_title.setSelected(true);
                tv_normal_status.setText("已领取");
                tv_normal_status.setSelected(true);
                break;
        }

        return convertView;
    }
}

package com.futureinst.login;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.futureinst.R;


/**
 * Created by hao on 2015/11/12.
 */
public class PhoneAdapter extends BaseAdapter {
    private String[] list;
    private Context context;
    private OnItemClickListener onItemClickListener;
    public PhoneAdapter(Context context){
        this.context = context;
    }
    public void setList(String[] list){
        this.list = list;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return list.length;
    }

    @Override
    public Object getItem(int position) {
        return list == null?null:list[position];
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if(convertView == null){
            convertView = new TextView(context);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            convertView.setLayoutParams(layoutParams);
            convertView.setPadding(20, 10, 20, 10);
            convertView.setBackgroundResource(R.drawable.item_select);
            ((TextView)convertView).setTextColor(context.getResources().getColor(R.color.text_color_4));
        }
        ((TextView)convertView).setText(list[position]);
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(onItemClickListener != null){
                    onItemClickListener.onItemClickListener(list[position]);
                }
            }
        });
        return convertView;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        if(onItemClickListener != null){
            this.onItemClickListener = onItemClickListener;
        }
    }

    public interface OnItemClickListener{
      void  onItemClickListener(String phone);
    }

}

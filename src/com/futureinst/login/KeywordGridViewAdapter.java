package com.futureinst.login;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.text.TextUtils;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.futureinst.R;
import com.futureinst.model.basemodel.RegistsKeywordDAO;
import com.futureinst.utils.ViewHolder;

public class KeywordGridViewAdapter extends BaseAdapter {
	private List<RegistsKeywordDAO> list;
	private Context context;
	private SparseBooleanArray isCheck;
	private String resullt = "";
	public KeywordGridViewAdapter(Context context){
		list = new ArrayList<RegistsKeywordDAO>();
		isCheck = new SparseBooleanArray();
		this.context = context;
	}
	public void setList(List<RegistsKeywordDAO> list){
		this.list = list;
		for(int i = 0;i<list.size();i++){
			isCheck.put(i, false);
		}
		notifyDataSetChanged();
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list !=null ? list.size() : 0;
	}

	@Override
	public Object getItem(int arg0) {
		
		return list!=null?list.get(arg0):null;
	}

	@Override
	public long getItemId(int arg0) {
		return 0;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup arg2) {
		// TODO Auto-generated method stub
		if(convertView==null)
		convertView = LayoutInflater.from(context).inflate(R.layout.item_keyword, null, false);
		RegistsKeywordDAO item = list.get(position);
		TextView tv_keyword = ViewHolder.get(convertView, R.id.tv_keyword);
		final ImageView iv_check = ViewHolder.get(convertView, R.id.iv_check);
		tv_keyword.setText(item.getKeyword());
		tv_keyword.setBackground(context.getResources().getDrawable(item.getBackgroundColor()));
		tv_keyword.setTextColor(context.getResources().getColor(item.getTextColor()));
		tv_keyword.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
					iv_check.setSelected(!(isCheck.get(position)));
					isCheck.put(position, !(isCheck.get(position)));
			}
		});
		return convertView;
	}
	public String getResult(){
		for(int i = 0;i< list.size();i++){
			if(isCheck.get(i)){
				if(TextUtils.isEmpty(resullt)){
					resullt = list.get(i).getKeyword();
				}else{
					resullt +="-"+list.get(i).getKeyword();
				}
			}
		}
		return resullt;
	}
}

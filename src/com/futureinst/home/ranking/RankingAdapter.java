package com.futureinst.home.ranking;

import java.util.ArrayList;
import java.util.List;

import com.futureinst.R;
import com.futureinst.model.usermodel.RankDAO;
import com.futureinst.roundimageutils.RoundedImageView;
import com.futureinst.utils.ImageLoadOptions;
import com.futureinst.utils.ViewHolder;
import com.nostra13.universalimageloader.core.ImageLoader;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class RankingAdapter extends BaseAdapter {
	private List<RankDAO> list;
	private Context context;
	//排名跌涨0-涨，1-跌，2-持平
	public RankingAdapter(Context context){
		this.context = context;
		list = new ArrayList<RankDAO>();
	}
	public void setList(List<RankDAO> list){
		this.list = list;
		notifyDataSetChanged();
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list!=null ? list.size():0;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return list!=null ? list.get(position) : null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if(convertView == null)
			convertView = LayoutInflater.from(context).inflate(R.layout.item_ranking,parent, false);
		    RankDAO item = list.get(position);
			TextView tv_userName = ViewHolder.get(convertView, R.id.tv_userName);
			TextView tv_prophet = ViewHolder.get(convertView, R.id.tv_prophet);//先知指数
			TextView tv_ranking = ViewHolder.get(convertView, R.id.tv_ranking);
			ImageView iv_ranking = ViewHolder.get(convertView, R.id.iv_ranking);
			RoundedImageView iv_headImg = ViewHolder.get(convertView, R.id.iv_headImg);
			
			if(iv_headImg.getTag() == null || !iv_headImg.getTag().equals(item.getUser_head_image())){
				ImageLoader.getInstance().displayImage(item.getUser_head_image(), iv_headImg, ImageLoadOptions.getOptions(R.drawable.logo));
				iv_headImg.setTag(item.getUser_head_image());
			}
			if(TextUtils.isEmpty(item.getName())){
				tv_userName.setText("佚名");
			}else{
				tv_userName.setText(item.getName());
			}
			tv_prophet.setText(item.getForeIndex()+"");
			tv_ranking.setText(item.getCurrRank()+"  ");
			if(item.getCurrRank() < item.getLastRank()){
				iv_ranking.setImageDrawable(context.getResources().getDrawable(R.drawable.ranking_up_2));
			}else if(item.getCurrRank() > item.getLastRank()){
				iv_ranking.setImageDrawable(context.getResources().getDrawable(R.drawable.ranking_down_2));
			}else{
				iv_ranking.setImageDrawable(context.getResources().getDrawable(R.drawable.ranking_balance_2));
			}
		return convertView;
	}

}

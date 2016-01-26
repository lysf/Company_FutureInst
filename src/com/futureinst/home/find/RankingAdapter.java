package com.futureinst.home.find;

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
	private List<String> follows;
	private List<String> friends;
	private Context context;
	//排名跌涨0-涨，1-跌，2-持平
	public RankingAdapter(Context context){
		this.context = context;
		list = new ArrayList<RankDAO>();
		follows = new ArrayList<String>();
		friends = new ArrayList<String>();
	}
	public void setList(List<RankDAO> list,List<String> follows,List<String> friends){
		this.list = list;
		if(follows != null){
			this.follows = follows;
		}
		if(friends != null){
			this.friends = friends;
		}
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
			convertView = LayoutInflater.from(context).inflate(R.layout.item_ranking,null, false);
		    RankDAO item = list.get(position);
			TextView tv_userName = ViewHolder.get(convertView, R.id.tv_userName);
			TextView tv_prophet = ViewHolder.get(convertView, R.id.tv_prophet);//先知指数
			TextView tv_ranking = ViewHolder.get(convertView, R.id.tv_ranking);
			ImageView iv_ranking = ViewHolder.get(convertView, R.id.iv_ranking);
			ImageView iv_relation = ViewHolder.get(convertView, R.id.iv_relation);//关系
			RoundedImageView iv_headImg = ViewHolder.get(convertView, R.id.iv_headImg);


			if(follows !=null && follows.contains(item.getUserId()+"")){
				iv_relation.setImageDrawable(context.getResources().getDrawable(R.drawable.relat_2));
			}else if(friends != null && friends.contains(item.getUserId()+"")){
				iv_relation.setImageDrawable(context.getResources().getDrawable(R.drawable.relat_3));
			}else{
				iv_relation.setImageDrawable(context.getResources().getDrawable(R.drawable.relat_1));
			}

			if(iv_headImg.getTag() == null || !iv_headImg.getTag().equals(item.getUser().getHeadImage())){
				ImageLoader.getInstance().displayImage(item.getUser().getHeadImage(), iv_headImg, ImageLoadOptions.getOptions(R.drawable.logo));
				iv_headImg.setTag(item.getUser().getHeadImage());
			}
			if(TextUtils.isEmpty(item.getUser().getName())){
				tv_userName.setText("佚名");
			}else{
				tv_userName.setText(item.getUser().getName());
			}
			tv_prophet.setText(String.format("%.3f",item.getForeIndexNew()));
			tv_ranking.setText(item.getRank()+"  ");
			if(item.getRank() < item.getLastRank()){
				iv_ranking.setImageDrawable(context.getResources().getDrawable(R.drawable.iv_up));
			}else if(item.getRank() > item.getLastRank()){
				iv_ranking.setImageDrawable(context.getResources().getDrawable(R.drawable.iv_down));
			}else{
				iv_ranking.setImageDrawable(context.getResources().getDrawable(R.drawable.ranking_balance_2));
			}
		return convertView;
	}

}

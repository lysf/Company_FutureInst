package com.futureinst.comment;

import com.futureinst.R;
import com.futureinst.model.comment.CommentDAO;
import com.futureinst.roundimageutils.RoundedImageView;
import com.futureinst.utils.ImageLoadOptions;
import com.futureinst.utils.TimeUtil;
import com.futureinst.utils.ViewHolder;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.os.Handler;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CommentDetailAdapter extends BaseAdapter {
	private Context context;
	private List<CommentDAO> list;
	private boolean attitude;
	private Animation animation;
	public CommentDetailAdapter(Context context,boolean attitude) {
		this.context = context;
		list = new ArrayList<CommentDAO>();
		this.attitude = attitude;
		animation = AnimationUtils.loadAnimation(context, R.anim.comment_prise);
	}
	public List<CommentDAO> getList() {
		return list;
	}
	public void setList(List<CommentDAO> list) {
		this.list = list;
		notifyDataSetChanged();
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list==null?0:list.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return list == null ? null : list.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if(convertView == null) 
			convertView = LayoutInflater.from(context).inflate(R.layout.view_comment, null);
		CommentDAO item = list.get(position);
		
		View view1 = ViewHolder.get(convertView, R.id.view_1);
		View view2 = ViewHolder.get(convertView, R.id.view_2);
		RoundedImageView imageView = ViewHolder.get(convertView, R.id.headImage);
		TextView tv_name = ViewHolder.get(convertView, R.id.tv_name);
		TextView tv_comment = ViewHolder.get(convertView, R.id.tv_comment);
		TextView tv_time = ViewHolder.get(convertView, R.id.tv_time);
		TextView tv_prise = ViewHolder.get(convertView, R.id.tv_prise);
		final TextView tv_prise_add = ViewHolder.get(convertView, R.id.tv_prise_add);
		ImageView iv_prise = ViewHolder.get(convertView, R.id.iv_prise);
		String comment = item.getContent();
		int color = 0;
		if(item.getAttitude() == 1){
//			comment = "[看好]"+comment;
			color = context.getResources().getColor(R.color.gain_red);
			view1.setBackgroundColor(color);
			view2.setBackgroundColor(color);
		}else{
//			comment = "[不看好]"+comment;
			color = context.getResources().getColor(R.color.text_blue);
			view1.setBackgroundColor(color);
			view2.setBackgroundColor(color);
		}
		tv_name.setText(item.getUser().getName());
		ImageLoader.getInstance().displayImage(item.getUser().getHeadImage(), imageView, ImageLoadOptions.getOptions(R.drawable.image_top_default));
		tv_time.setText(TimeUtil.getDescriptionTimeFromTimestamp(item.getCtime()));
//		SpannableStringBuilder stringBuilder = new SpannableStringBuilder(comment);
//		stringBuilder.setSpan(new ForegroundColorSpan(color), 0, comment.indexOf("]")+1, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
		tv_comment.setText(comment);
		iv_prise.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				tv_prise_add.setVisibility(View.VISIBLE);
				tv_prise_add.startAnimation(animation);
				new Handler().postDelayed(new  Runnable() {
					public void run() {
						tv_prise_add.setVisibility(View.GONE);
					}
				}, 1000);
			}
		});
		return convertView;
	}

}

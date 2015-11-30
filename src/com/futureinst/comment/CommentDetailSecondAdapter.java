package com.futureinst.comment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.futureinst.R;
import com.futureinst.model.comment.CommentDAO;
import com.futureinst.net.CommentOperate;
import com.futureinst.personalinfo.other.PersonalShowActivity;
import com.futureinst.roundimageutils.RoundedImageView;
import com.futureinst.sharepreference.SharePreferenceUtil;
import com.futureinst.utils.ImageLoadOptions;
import com.futureinst.utils.TimeUtil;
import com.futureinst.utils.Utils;
import com.futureinst.utils.ViewHolder;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CommentDetailSecondAdapter extends BaseAdapter {
	private Context context;
	private List<CommentDAO> list;
	private List<String> loves;
	private Map<String,CommentDAO> map;
	private SharePreferenceUtil preferenceUtil;

	private DeleteCommentListener deleteCommentListener;
	private PraiseOperateListener operateListener;
	private ApplyCommentListener applyCommentListener;
	public CommentDetailSecondAdapter(Context context) {
		this.context = context;
		list = new ArrayList<CommentDAO>();
		loves = new ArrayList<String>();
		map = new HashMap<>();
		preferenceUtil = SharePreferenceUtil.getInstance(context);
	}
	public List<CommentDAO> getList() {
		return list;
	}
	public void setList(List<CommentDAO> list,List<String> loves,Map<String,CommentDAO>replyToCommentMap) {
		this.list = list;
		if(loves!=null){
			this.loves = loves;
		}
		if(replyToCommentMap != null){
			this.map = replyToCommentMap;
		}
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
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if(convertView == null) 
			convertView = LayoutInflater.from(context).inflate(R.layout.item_comment_top, null);
		final CommentDAO item = list.get(position);
		

		RoundedImageView imageView = ViewHolder.get(convertView, R.id.headImage);
		LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(Utils.dip2px(context,25),Utils.dip2px(context,25));
		layoutParams.rightMargin = Utils.dip2px(context,3);
		imageView.setLayoutParams(layoutParams);
		TextView tv_name = ViewHolder.get(convertView, R.id.tv_name);
		TextView tv_attitude = ViewHolder.get(convertView, R.id.tv_attitude);
		LinearLayout ll_agree = ViewHolder.get(convertView,R.id.ll_agree);
		TextView tv_comment_num = ViewHolder.get(convertView, R.id.tv_comment_num);
		ImageView iv_agree = ViewHolder.get(convertView,R.id.iv_agree);
		TextView tv_time = ViewHolder.get(convertView, R.id.tv_time);
		TextView tv_comment = ViewHolder.get(convertView, R.id.tv_comment);


		tv_attitude.setVisibility(View.GONE);
		tv_comment_num.setText(item.getLikeNum()+"");
		String s = null;
		if(loves == null || !loves.contains((item.getId()+"").trim())){
			iv_agree.setSelected(false);
			s = CommentOperate.like.name();
		}else{
			iv_agree.setSelected(true);
			s = CommentOperate.unlike.name();
		}
		final String operate = s;

		tv_comment.setText(item.getContent());
		if(map != null && map.containsKey(item.getReplyTo()+"")){
			String string = item.getUser().getName() + " 回复 " + map.get(item.getReplyTo() + "").getUser().getName();
			SpannableStringBuilder stringBuilder = new SpannableStringBuilder(string);
			stringBuilder.setSpan(new ForegroundColorSpan(Color.parseColor("#69AFC0")),string.indexOf(" 回复 ")+" 回复 ".length(),string.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
			tv_name.setText(stringBuilder);

		}else{
			tv_name.setText(item.getUser().getName());
		}
		if(imageView.getTag() == null || !imageView.getTag().equals(item.getUser().getHeadImage())){
			ImageLoader.getInstance().displayImage(item.getUser().getHeadImage(), imageView, ImageLoadOptions.getOptions(R.drawable.logo));
			imageView.setTag(item.getUser().getHeadImage());
		}
		tv_time.setText(TimeUtil.getDescriptionTimeFromTimestamp(item.getCtime()));
//		tv_time.setText(item.getCtimeStr());

		imageView.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
                if ((preferenceUtil.getID()+"").equals(item.getUser().getId()+"")) {
					return;
				}
				Intent intent = new Intent(context, PersonalShowActivity.class);
				intent.putExtra("id", item.getUser().getId() + "");
				context.startActivity(intent);
			}
		});
		tv_name.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
                if ((preferenceUtil.getID()+"").equals(item.getUser().getId()+"")){
					return;
				}
				Intent intent = new Intent(context, PersonalShowActivity.class);
				intent.putExtra("id", item.getUser().getId() + "");
				context.startActivity(intent);
			}
		});
		ll_agree.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if(operateListener !=null){
					operateListener.onClickListener(item.getId()
					+"",operate);
				}
			}
		});
		tv_comment.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
                if ((preferenceUtil.getID()+"").equals(item.getUser().getId()+"")){
					//删除提示
					if(deleteCommentListener != null){
						deleteCommentListener.onClickListener(item);
					}
					return;
				}
				if(applyCommentListener!=null){
					applyCommentListener.onClickListener(item);
				}
			}
		});
		return convertView;
	}
	public void setOperateListener(PraiseOperateListener operateListener){
		if(operateListener !=null){
			this.operateListener = operateListener;
		}
	}
	public void setApplyCommentListener(ApplyCommentListener applyCommentListener){
		if(applyCommentListener!=null){
			this.applyCommentListener = applyCommentListener;
		}
	}
	public void setDeleteCommentListener(DeleteCommentListener deleteCommentListener){
		if(deleteCommentListener!=null){
			this.deleteCommentListener = deleteCommentListener;
		}
	}
	public interface PraiseOperateListener{
		 void onClickListener(String com_id, String operate);//（点赞/取消点赞）
	}
	public interface ApplyCommentListener{
		void onClickListener(CommentDAO comment) ;//回复评论
	}
	public interface DeleteCommentListener{
		void onClickListener(CommentDAO comment);//删除评论
	}
}

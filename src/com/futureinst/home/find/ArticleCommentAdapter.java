package com.futureinst.home.find;

import android.content.Context;
import android.content.Intent;
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
import android.widget.Toast;

import com.futureinst.R;
import com.futureinst.comment.CommentDetailSecondActivity;
import com.futureinst.model.comment.CommentDAO;
import com.futureinst.model.usermodel.UserDAO;
import com.futureinst.net.CommentOperate;
import com.futureinst.personalinfo.other.PersonalShowActivity;
import com.futureinst.roundimageutils.RoundedImageView;
import com.futureinst.sharepreference.SharePreferenceUtil;
import com.futureinst.utils.ImageLoadOptions;
import com.futureinst.utils.TimeUtil;
import com.futureinst.utils.ViewHolder;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ArticleCommentAdapter extends BaseAdapter {
	private Context context;
	private List<CommentDAO> list;
	private List<String> loves;
	private Map<String,CommentDAO> map;
	private SharePreferenceUtil preferenceUtil;

	private Animation animation;
	private DeleteCommentListener deleteCommentListener;
	private PraiseOperateListener operateListener;
	private ApplyCommentListener applyCommentListener;
	public ArticleCommentAdapter(Context context) {
		this.context = context;
		list = new ArrayList<CommentDAO>();
		loves = new ArrayList<String>();
		preferenceUtil = SharePreferenceUtil.getInstance(context);
		map = new HashMap<String,CommentDAO>();
		animation = AnimationUtils.loadAnimation(context, R.anim.comment_prise);
	}
	public List<CommentDAO> getList() {
		return list;
	}
	public void setList(List<CommentDAO> list,Map<String,CommentDAO> map,List<String> loves) {
		this.list = list;
		if(map!=null){
			this.map = map;
		}
		if(loves != null){
			this.loves = loves;
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
			convertView = LayoutInflater.from(context).inflate(R.layout.item_comment, null);
		final CommentDAO item = list.get(position);


		RoundedImageView imageView = ViewHolder.get(convertView, R.id.headImage);
		TextView tv_name = ViewHolder.get(convertView, R.id.tv_name);
		TextView tv_attitude = ViewHolder.get(convertView, R.id.tv_attitude);
		LinearLayout ll_agree = ViewHolder.get(convertView,R.id.ll_agree);
		TextView tv_comment_num = ViewHolder.get(convertView, R.id.tv_comment_num);
		ImageView iv_agree = ViewHolder.get(convertView,R.id.iv_agree);
		TextView tv_time = ViewHolder.get(convertView, R.id.tv_time);
		TextView tv_comment = ViewHolder.get(convertView, R.id.tv_comment);

		RoundedImageView headImage_1 = ViewHolder.get(convertView,R.id.headImage_1);
		TextView tv_name_1 = ViewHolder.get(convertView,R.id.tv_name_1);
		TextView tv_time_1 = ViewHolder.get(convertView,R.id.tv_time_1);
		TextView tv_total_apply = ViewHolder.get(convertView,R.id.tv_total_apply);
		LinearLayout ll_apply = ViewHolder.get(convertView, R.id.ll_apply);

		tv_attitude.setVisibility(View.GONE);
		tv_comment_num.setText(item.getLikeNum()+"");
		String s = null;
		if(loves == null || !loves.contains(item.getId()+"")){
			iv_agree.setSelected(false);
			s = CommentOperate.like.name();
		}else{
			iv_agree.setSelected(true);
			s = CommentOperate.unlike.name();
		}
		final String operate = s;


		if(map == null || !map.containsKey(item.getLastChildId()+"")){
			ll_apply.setVisibility(View.GONE);
		}else{
			ll_apply.setVisibility(View.VISIBLE);
			CommentDAO dao = map.get(item.getLastChildId()+"");
			if(headImage_1.getTag() == null || !headImage_1.getTag().equals(dao.getUser().getHeadImage())){
				ImageLoader.getInstance().displayImage(dao.getUser().getHeadImage(),headImage_1,ImageLoadOptions.getOptions(R.drawable.logo));
				headImage_1.setTag(dao.getUser().getHeadImage());
			}
			tv_name_1.setText(dao.getUser().getName()+" 已回复");
			tv_time_1.setText(TimeUtil.getDescriptionTimeFromTimestamp(dao.getCtime()));
			tv_total_apply.setText("共"+item.getChildNum()+"条回复");
		}

		tv_comment.setText(item.getContent());
		tv_name.setText(item.getUser().getName());
		if(imageView.getTag() == null || !imageView.getTag().equals(item.getUser().getHeadImage())){
			ImageLoader.getInstance().displayImage(item.getUser().getHeadImage(), imageView, ImageLoadOptions.getOptions(R.drawable.logo));
			imageView.setTag(item.getUser().getHeadImage());
		}
		tv_time.setText(TimeUtil.getDescriptionTimeFromTimestamp(item.getCtime()));

		imageView.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (preferenceUtil.getID() == item.getUser().getId()) {
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
				if(preferenceUtil.getID() == item.getUser().getId()){
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
				if(item.getUser().getId() == preferenceUtil.getID()){
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
		ll_apply.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				//进入评论详情（子评论）
				Intent intent = new Intent(context,CommentDetailSecondActivity.class);
				intent.putExtra("value",item);
				intent.putExtra("from",false);
				if(operate.equals(CommentOperate.like.name())){
					intent.putExtra("praise",false);
				}else{
					intent.putExtra("praise",true);
				}
				context.startActivity(intent);

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
		void onClickListener(String com_id,String operate);//（点赞/取消点赞）
	}
	public interface ApplyCommentListener{
		void onClickListener(CommentDAO comment) ;//回复评论
	}
	public interface DeleteCommentListener{
		void onClickListener(CommentDAO comment);//删除评论
	}
}

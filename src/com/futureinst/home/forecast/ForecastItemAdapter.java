package com.futureinst.home.forecast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.futureinst.R;
import com.futureinst.home.SystemTimeUtile;
import com.futureinst.model.comment.CommentDAO;
import com.futureinst.model.homeeventmodel.QueryEventDAO;
import com.futureinst.roundimageutils.RoundedImageView;
import com.futureinst.utils.ImageLoadOptions;
import com.futureinst.utils.LongTimeUtil;
import com.futureinst.utils.Utils;
import com.futureinst.utils.ViewHolder;
import com.nostra13.universalimageloader.core.ImageLoader;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class ForecastItemAdapter extends BaseAdapter {
	private List<QueryEventDAO> list;
	private Map<String,List<CommentDAO>> commentMap;
	private Context context;
	public ForecastItemAdapter(Context context){
		this.context = context;
		list = new ArrayList<QueryEventDAO>();
		commentMap = new HashMap<String,List<CommentDAO>>();
	}
	public void refresh(List<QueryEventDAO> list,Map<String,List<CommentDAO>> commentMap) {
		this.list = list;
		if(commentMap !=null){
			this.commentMap = commentMap;
		}
		notifyDataSetChanged();
	}
	public void setList(List<QueryEventDAO> list,Map<String,List<CommentDAO>> commentMap) {
		this.list.addAll(list);
		if(commentMap!=null){
			this.commentMap.putAll(commentMap);
		}
		notifyDataSetChanged();
	}
	public List<QueryEventDAO> getList() {
		return list;
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list!=null ? list.size() : 0;
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
			convertView = LayoutInflater.from(context).inflate(R.layout.item_forecast_type, null);
		QueryEventDAO item = list.get(position);
		//评论
		RoundedImageView headImage_1 = ViewHolder.get(convertView,R.id.headImage_1);
		RoundedImageView headImage_2 = ViewHolder.get(convertView,R.id.headImage_2);
		TextView tv_name_1 = ViewHolder.get(convertView,R.id.tv_name_1);
		TextView tv_name_2 = ViewHolder.get(convertView,R.id.tv_name_2);
		TextView tv_status_1 = ViewHolder.get(convertView,R.id.tv_status_1);
		TextView tv_status_2 = ViewHolder.get(convertView,R.id.tv_status_2);
		TextView tv_comment_1 = ViewHolder.get(convertView,R.id.tv_comment_1);
		TextView tv_comment_2 = ViewHolder.get(convertView,R.id.tv_comment_2);
		TextView tv_time_1 = ViewHolder.get(convertView,R.id.tv_time_1);
		TextView tv_time_2 = ViewHolder.get(convertView,R.id.tv_time_2);
		TextView tv_prise_1 = ViewHolder.get(convertView,R.id.tv_prise_1);
		TextView tv_prise_2 = ViewHolder.get(convertView,R.id.tv_prise_2);
		View view_transp = ViewHolder.get(convertView,R.id.view_transp);



		ImageView iv_image = ViewHolder.get(convertView, R.id.iv_image);
		TextView tv_title = ViewHolder.get(convertView, R.id.tv_title);
		LinearLayout ll_time = ViewHolder.get(convertView,R.id.ll_time);
		TextView tv_type = ViewHolder.get(convertView,R.id.tv_type);
        TextView tv_time = ViewHolder.get(convertView,R.id.tv_time);
        TextView tv_status = ViewHolder.get(convertView,R.id.tv_attitude);
        TextView tv_price = ViewHolder.get(convertView,R.id.tv_price);
        ImageView iv_attitude = ViewHolder.get(convertView,R.id.iv_attitude);
        View view_middle = ViewHolder.get(convertView,R.id.view_middle);
        View view_bottom = ViewHolder.get(convertView,R.id.view_bottom);
		TextView tv_commentNum = ViewHolder.get(convertView,R.id.tv_commentNum);//评论数
		TextView tv_orderNum = ViewHolder.get(convertView,R.id.tv_orderNum);//下单数

		tv_commentNum.setText(item.getAllComNum()+"");
		tv_orderNum.setText(item.getInvolve() + "");
		float iv_h = (Utils.getScreenWidth(context)-Utils.dip2px(context,20))*266/702;
		android.widget.RelativeLayout.LayoutParams iv_layoutParams = new android.widget.RelativeLayout.LayoutParams(
				LayoutParams.MATCH_PARENT, (int)iv_h);
		iv_image.setLayoutParams(iv_layoutParams);
		view_transp.setLayoutParams(iv_layoutParams);
		RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
		layoutParams.setMargins(0,(int)iv_h*3/4,Utils.dip2px(context,8),0);
		layoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
		tv_status.setLayoutParams(layoutParams);

		view_transp.setVisibility(View.INVISIBLE);
        ll_time.setVisibility(View.INVISIBLE);
		if(iv_image.getTag()==null || !iv_image.getTag().equals(item.getImgsrc())){
			ImageLoader.getInstance().displayImage(item.getImgsrc(), iv_image, ImageLoadOptions.getOptions(R.drawable.image_top_default));
			iv_image.setTag(item.getImgsrc());
		}
		tv_title.setText(item.getLead());
        if(item.getType() == 1 || item.getType() == 2){//专题或广告
			if(item.getType() == 1){
                tv_type.setText("专题");
			}else{
                tv_type.setText("广告");
			}
            tv_type.setTextColor(context.getResources().getColor(R.color.text_color_white));
            tv_type.setBackground(context.getResources().getDrawable(R.drawable.shap_trasp_white_bg));
			tv_title.setVisibility(View.GONE);
            tv_status.setVisibility(View.GONE);
            ll_time.setVisibility(View.INVISIBLE);
            view_middle.setVisibility(View.GONE);
            view_bottom.setVisibility(View.GONE);
			view_transp.setVisibility(View.VISIBLE);
		}else{
			List<CommentDAO> comments = getComment(item.getId());
            view_middle.setVisibility(View.VISIBLE);
			if(comments!=null){
				//有评论
				view_bottom.setVisibility(View.VISIBLE);
				if(headImage_1.getTag() == null || !headImage_1.getTag().equals(comments.get(0).getUser().getHeadImage())){
					ImageLoader.getInstance().displayImage(comments.get(0).getUser().getHeadImage(),headImage_1,ImageLoadOptions.getOptions(R.drawable.logo));
					headImage_1.setTag(comments.get(0).getUser().getHeadImage());
				}
				if(headImage_2.getTag() == null || !headImage_2.getTag().equals(comments.get(1).getUser().getHeadImage())){
					ImageLoader.getInstance().displayImage(comments.get(1).getUser().getHeadImage(),headImage_2,ImageLoadOptions.getOptions(R.drawable.logo));
					headImage_2.setTag(comments.get(1).getUser().getHeadImage());
				}
				tv_name_1.setText(comments.get(0).getUser().getName());
				tv_name_2.setText(comments.get(1).getUser().getName());
				if(comments.get(0).getAttitude() == 1){//赞同
					tv_status_1.setText("看好");
					tv_status_1.setTextColor(context.getResources().getColor(R.color.gain_red));
				}else{
					tv_status_1.setText("不看好");
					tv_status_1.setTextColor(context.getResources().getColor(R.color.gain_blue));
				}
				if(comments.get(1).getAttitude() == 1){//赞同
					tv_status_2.setText("看好");
					tv_status_2.setTextColor(context.getResources().getColor(R.color.gain_red));
				}else{
					tv_status_2.setText("不看好");
					tv_status_2.setTextColor(context.getResources().getColor(R.color.gain_blue));
				}
				tv_comment_1.setText(comments.get(0).getContent());
				tv_comment_2.setText(comments.get(1).getContent());

//				int w = View.MeasureSpec.makeMeasureSpec(0,
//						View.MeasureSpec.UNSPECIFIED);
//				int h = View.MeasureSpec.makeMeasureSpec(0,
//						View.MeasureSpec.UNSPECIFIED);
//				tv_comment_1.measure(w, h);
//				tv_comment_2.measure(w, h);
//				int height1 = tv_comment_1.getMeasuredHeight();
//				int height2 = tv_comment_2.getMeasuredHeight();
//				int height = height1>height2?height1:height2;
//				RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,height);
//				tv_comment_1.setLayoutParams(params);
//				tv_comment_2.setLayoutParams(params);

				tv_time_1.setText(comments.get(0).getCtimeStr());
				tv_time_2.setText(comments.get(1).getCtimeStr());
				tv_prise_1.setText(comments.get(0).getLikeNum()+"");
				tv_prise_2.setText(comments.get(1).getLikeNum()+"");
			}else{
				view_bottom.setVisibility(View.GONE);
			}
			tv_title.setVisibility(View.VISIBLE);
            tv_status.setVisibility(View.VISIBLE);
            tv_type.setText(item.getTagstr());
            tv_price.setText(String.format("%.1f", item.getCurrPrice())+"%");
            if(item.getPriceChange() >= 0){
                iv_attitude.setImageDrawable(context.getResources().getDrawable(R.drawable.iv_up));
            }else{
                iv_attitude.setImageDrawable(context.getResources().getDrawable(R.drawable.iv_down));
            }
            tv_type.setTextColor(context.getResources().getColor(R.color.text_color_4));
			tv_type.setBackground(context.getResources().getDrawable(R.drawable.shap_trasp_black_bg));
            //倒计时
			if(item.getStatusStr()!=null){
				if(item.getStatusStr().equals("交易中")){
                    ll_time.setVisibility(View.VISIBLE);
					Long time = item.getTradeTime() - SystemTimeUtile.getInstance(0L).getSystemTime();
					tv_time.setText(LongTimeUtil.longTimeUtil(time));
					tv_status.setText("去预测");
                    tv_status.setBackground(context.getResources().getDrawable(R.drawable.huang_3_icon));
				}else if(item.getStatusStr().equals("待清算")){
                    tv_status.setText(item.getStatusStr());
                    tv_status.setBackground(context.getResources().getDrawable(R.drawable.lan_3_icon));
				}else{
                    String str = item.getStatusStr();
                    tv_status.setText(str);
                    if(str.length() == 3){
                        tv_status.setBackground(context.getResources().getDrawable(R.drawable.hui_3_icon));
                    }else if(str.length() == 4){
                        tv_status.setBackground(context.getResources().getDrawable(R.drawable.hui_4_icon));
                    }else if(str.length() == 5){
                        tv_status.setBackground(context.getResources().getDrawable(R.drawable.hui_5_icon));
                    }else {
                        tv_status.setBackground(context.getResources().getDrawable(R.drawable.hui_6_icon));
                    }
				}
			}

        }

		return convertView;
	}
	private List<CommentDAO> getComment(Long id){
		List<CommentDAO> list = new ArrayList<CommentDAO>();
		if(commentMap.containsKey(id+"")){
			list = commentMap.get(id+"");
		}
		if(list.size() < 2){
			list = null;
		}
		return list;
	}

}

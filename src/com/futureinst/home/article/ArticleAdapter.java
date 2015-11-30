package com.futureinst.home.article;

import android.content.Context;
import android.graphics.Color;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.futureinst.R;
import com.futureinst.model.comment.ArticleDAO;
import com.futureinst.utils.ImageLoadOptions;
import com.futureinst.utils.TimeUtil;
import com.futureinst.utils.ViewHolder;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hao on 2015/10/30.
 */
public class ArticleAdapter extends BaseAdapter {
    private boolean isMe;
    private Context context;
    private List<ArticleDAO> list;
    public ArticleAdapter(Context context,boolean isMe){
        this.context = context;
        list = new ArrayList<ArticleDAO>();
        this.isMe = isMe;
    }
    public void refresh(List<ArticleDAO> list){
        this.list.addAll(list);
        notifyDataSetChanged();
    }
    public void setList(List<ArticleDAO> list){
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
        if(convertView == null)

            convertView = LayoutInflater.from(context).inflate(R.layout.item_article,parent,false);
        ArticleDAO item = list.get(position);
        ImageView iv_event = ViewHolder.get(convertView,R.id.iv_event);
        TextView tv_event_type = ViewHolder.get(convertView,R.id.tv_event_type);
        TextView tv_event_title = ViewHolder.get(convertView,R.id.tv_event_title);
        TextView tv_point_title = ViewHolder.get(convertView,R.id.tv_point_title);
        TextView tv_time = ViewHolder.get(convertView,R.id.tv_time);
        TextView tv_read = ViewHolder.get(convertView,R.id.tv_read);
        TextView tv_award = ViewHolder.get(convertView,R.id.tv_award);
        TextView tv_praise = ViewHolder.get(convertView,R.id.tv_praise);
        TextView tv_comment_num = ViewHolder.get(convertView,R.id.tv_comment_num);
        TextView tv_point_content = ViewHolder.get(convertView,R.id.tv_point_content);

        if(iv_event.getTag() == null || !iv_event.getTag().equals(item.getEvent().getImgsrc())){
            ImageLoader.getInstance().displayImage(item.getEvent().getImgsrc(),iv_event, ImageLoadOptions.getOptions(R.drawable.image_top_default));
            iv_event.setTag(item.getEvent().getImgsrc());
        }
        tv_praise.setText("  "+item.getLoveNum());
        tv_comment_num.setText("  "+item.getCommentNum());
        tv_event_type.setText(item.getEvent().getTagstr());
        tv_event_title.setText(item.getEvent().getLead());
        tv_point_title.setText(item.getTitle());
        tv_time.setText(TimeUtil.getDescriptionTimeFromTimestamp(item.getMtime()));
        tv_read.setText("已阅读"+item.getReadNum()+"次");
        tv_point_content.setText(item.getAbstr());
        String award = item.getAward()+"";
        String awardtext = "  本文共受赏"+award+"未币";
        SpannableStringBuilder stringBuilder = new SpannableStringBuilder(awardtext);
        stringBuilder.setSpan(new ForegroundColorSpan(context.getResources().getColor(R.color.text_color_4)), awardtext.indexOf(award), awardtext.indexOf(award) + award.length(), Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
        tv_award.setText(stringBuilder);
        if(isMe){
            tv_award.setVisibility(View.VISIBLE);
        }else{
            tv_award.setVisibility(View.GONE);
        }
        return convertView;
    }
}

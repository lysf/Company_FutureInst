package com.futureinst.home.article;

import android.content.Context;
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
    private Context context;
    private List<ArticleDAO> list;
    public ArticleAdapter(Context context){
        this.context = context;
        list = new ArrayList<ArticleDAO>();
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
        TextView tv_praise = ViewHolder.get(convertView,R.id.tv_praise);
        TextView tv_comment_num = ViewHolder.get(convertView,R.id.tv_comment_num);
        TextView tv_point_content = ViewHolder.get(convertView,R.id.tv_point_content);

        if(iv_event.getTag() == null || !iv_event.getTag().equals(item.getEvent().getImgsrc())){
            ImageLoader.getInstance().displayImage(item.getEvent().getImgsrc(),iv_event, ImageLoadOptions.getOptions(R.drawable.image_top_default));
            iv_event.setTag(item.getEvent().getImgsrc());
        }
        tv_praise.setText(item.getLoveNum()+"");
        tv_comment_num.setText(item.getCommentNum()+"");
        tv_event_type.setText(item.getEvent().getTagstr());
        tv_event_title.setText(item.getEvent().getLead());
        tv_point_title.setText(item.getTitle());
        tv_time.setText(TimeUtil.getDescriptionTimeFromTimestamp(item.getMtime()));
        tv_read.setText(item.getReadNum()+"人已阅读");
        tv_point_content.setText(item.getAbstr());

        return convertView;
    }
}

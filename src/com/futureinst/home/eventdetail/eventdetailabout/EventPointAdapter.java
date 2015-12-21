package com.futureinst.home.eventdetail.eventdetailabout;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.futureinst.R;
import com.futureinst.home.find.ArticleDetailActivity;
import com.futureinst.model.comment.ArticleDAO;
import com.futureinst.personalinfo.other.PersonalShowActivity;
import com.futureinst.roundimageutils.RoundedImageView;
import com.futureinst.sharepreference.SharePreferenceUtil;
import com.futureinst.utils.ImageLoadOptions;
import com.futureinst.utils.TimeUtil;
import com.futureinst.utils.ViewHolder;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hao on 2015/12/18.
 */
public class EventPointAdapter extends BaseAdapter {
    private Context context;
    private List<ArticleDAO> list;
    private SharePreferenceUtil preferenceUtil;
    public EventPointAdapter(Context context){
        this.context = context;
        preferenceUtil = SharePreferenceUtil.getInstance(context);
        list = new ArrayList<>();
    }
    public void  setList(List<ArticleDAO> list){
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
        if(convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.view_comment_main,null);
        }
         LinearLayout ll_article_content = ViewHolder.get(convertView,R.id.ll_article_content);
         TextView tv_name = ViewHolder.get(convertView,R.id.tv_name);
        TextView tv_article_time = ViewHolder.get(convertView,R.id.tv_article_time);
        TextView tv_prise_num = ViewHolder.get(convertView,R.id.tv_prise_num);
        TextView tv_article_title = ViewHolder.get(convertView,R.id.tv_article_title);
        TextView tv_article_content = ViewHolder.get(convertView,R.id.tv_article_content);
         TextView tv_article_readNum = ViewHolder.get(convertView,R.id.tv_article_readNum);
        TextView tv_article_comment_num = ViewHolder.get(convertView,R.id.tv_article_comment_num);
        RoundedImageView headImage = ViewHolder.get(convertView,R.id.headImage);
        final ArticleDAO article = list.get(position);

        if (headImage.getTag() == null || !headImage.getTag().equals(article.getUser().getHeadImage())) {
            ImageLoader.getInstance().displayImage(article.getUser().getHeadImage(), headImage, ImageLoadOptions.getOptions(R.drawable.logo));
            headImage.setTag(article.getUser().getHeadImage());
        }
        tv_name.setText(article.getUser().getName());
        tv_article_time.setText(TimeUtil.getDescriptionTimeFromTimestamp(article.getMtime()));
        tv_article_title.setText(article.getTitle());
        tv_article_content.setText(article.getAbstr());
        tv_article_readNum.setText(article.getReadNum() + "人已阅读");
        ll_article_content.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {//进入观点详情页
                Intent intent = new Intent(context, ArticleDetailActivity.class);
                intent.putExtra("article_id", article.getId() + "");
                intent.putExtra("from", true);
                context.startActivity(intent);
            }
        });
        headImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ((preferenceUtil.getID() + "").equals(article.getUser().getId() + "")) {//是自己
                    return;
                }
                Intent intent = new Intent(context, PersonalShowActivity.class);
                intent.putExtra("id", article.getUser().getId() + "");
                context.startActivity(intent);
            }
        });

        return convertView;
    }
}

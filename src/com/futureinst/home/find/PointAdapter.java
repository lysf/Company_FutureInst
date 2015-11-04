package com.futureinst.home.find;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.futureinst.R;
import com.futureinst.model.comment.ArticleDAO;
import com.futureinst.roundimageutils.RoundedImageView;
import com.futureinst.utils.ImageLoadOptions;
import com.futureinst.utils.TimeUtil;
import com.futureinst.utils.ViewHolder;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hao on 2015/10/28.
 */
public class PointAdapter extends BaseAdapter {
    private Context context;
    private List<ArticleDAO> list;
    private PraiseOperateListener operateListener;
    public PointAdapter(Context context){
        this.context = context;
        list = new ArrayList<ArticleDAO>();
    }
    public void setList(List<ArticleDAO> list){
        this.list = list;
        notifyDataSetChanged();
    }
    public void refresh(List<ArticleDAO> list){
        this.list.addAll(list);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return list==null?0:list.size();
    }

    @Override
    public Object getItem(int position) {
        return list == null?null:list.get(position);
    }


    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null)
            convertView = LayoutInflater.from(context).inflate(R.layout.item_point,parent,false);
        ArticleDAO item = list.get(position);
        RoundedImageView headImage_select = ViewHolder.get(convertView,R.id.headImage_select);
        TextView tv_type = ViewHolder.get(convertView,R.id.tv_type);
        TextView tv_title = ViewHolder.get(convertView,R.id.tv_title);
        TextView tv_name = ViewHolder.get(convertView,R.id.tv_name);
        TextView tv_time = ViewHolder.get(convertView,R.id.tv_time);
        TextView tv_read = ViewHolder.get(convertView,R.id.tv_read);
        TextView tv_praise = ViewHolder.get(convertView,R.id.tv_praise);
        TextView tv_comment_num = ViewHolder.get(convertView,R.id.tv_comment_num);

        if(headImage_select.getTag() == null || !headImage_select.getTag().equals(item.getUser().getHeadImage())){
            ImageLoader.getInstance().displayImage(item.getUser().getHeadImage(),headImage_select, ImageLoadOptions.getOptions(R.drawable.image_top_default));
            headImage_select.setTag(item.getUser().getHeadImage());
        }
        tv_type.setText(item.getEvent().getTagstr());
        tv_name.setText(item.getUser().getName());
        tv_read.setText(item.getReadNum()+"人已阅读");
        tv_praise.setText(" "+item.getLoveNum());
        tv_comment_num.setText(" "+item.getCommentNum());
        tv_title.setText(item.getTitle());
        tv_time.setText(TimeUtil.getDescriptionTimeFromTimestamp(item.getMtime()));
        return convertView;
    }
    public void setOperateListener(PraiseOperateListener operateListener){
        if(operateListener !=null){
            this.operateListener = operateListener;
        }
    }
    public interface PraiseOperateListener{
        void onClickListener(String com_id,String operate);//（点赞/取消点赞）
    }
}

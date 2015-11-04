package com.futureinst.home.find;

import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.TextAppearanceSpan;
import android.text.style.URLSpan;
import android.text.style.UnderlineSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.futureinst.R;
import com.futureinst.home.HomeActivity;
import com.futureinst.model.news.NewsDAO;
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
 * Created by hao on 2015/10/28.
 */
public class IncidentAdapter extends BaseAdapter {
    private Context context;
    private SharePreferenceUtil preferenceUtil;
    private List<NewsDAO> list;
    public IncidentAdapter(Context context){
        this.context = context;
        preferenceUtil = SharePreferenceUtil.getInstance(context);
        list = new ArrayList<>();

    }
    public void setList(List<NewsDAO> list){
        this.list = list;
        notifyDataSetChanged();
    }
    @Override
    public int getCount() {
        return list == null ?0 : list.size();
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
            convertView = LayoutInflater.from(context).inflate(R.layout.item_indicent,parent,false);

        final NewsDAO item = list.get(position);
        RoundedImageView headImage = ViewHolder.get(convertView,R.id.headImage);
        TextView tv_indicent = ViewHolder.get(convertView,R.id.tv_indicent);

        if(headImage.getTag() == null || !headImage.getTag().equals(item.getUser().getHeadImage())){
            ImageLoader.getInstance().displayImage(item.getUser().getHeadImage(),headImage, ImageLoadOptions.getOptions(R.drawable.image_top_default));
            headImage.setTag(item.getUser().getHeadImage());
        }
        tv_indicent.setMovementMethod(LinkMovementMethod.getInstance());
        String time = TimeUtil.getDescriptionTimeFromTimestamp(item.getCtime());
        String texts = item.getMessage() + "\t\t\t"+time;

        tv_indicent.setText(Html.fromHtml(texts));
        CharSequence text = tv_indicent.getText();
        if(text instanceof Spannable){
            int end = text.length();
            Spannable sp = (Spannable)tv_indicent.getText();
            URLSpan[] urls=sp.getSpans(0, end, URLSpan.class);
            SpannableStringBuilder style=new SpannableStringBuilder(text);
            style.clearSpans();//should clear old spans

            for(URLSpan url : urls){
                MyURLSpan myURLSpan = new MyURLSpan(context,url.getURL());
                style.setSpan(myURLSpan,sp.getSpanStart(url),sp.getSpanEnd(url),Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
            TextAppearanceSpan textAppearanceSpan = new TextAppearanceSpan(null,0,23, ColorStateList.valueOf(0xff9a9b9b),null);
            style.setSpan(textAppearanceSpan, text.length() - time.length() - 1, text.length(), Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
            tv_indicent.setText(style);
        }
        headImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(preferenceUtil.getID() == item.getUser().getId()){//是否是当前用户
                    return;
                }
                Intent intent = new Intent(context, PersonalShowActivity.class);
                intent.putExtra("id",item.getUser().getId()+"");
                context.startActivity(intent);
            }
        });
        return convertView;
    }



    private static class MyURLSpan extends ClickableSpan {
        private Context context;
        private String mUrl;
        private NewsUtil newsUtil;
        private SharePreferenceUtil preferenceUtil;
        MyURLSpan(Context context,String url) {
            this.context = context;
            mUrl =url;
            newsUtil = NewsUtil.getInstance();
            preferenceUtil = SharePreferenceUtil.getInstance(context);
        }

        @Override
        public void updateDrawState(TextPaint ds) {
            super.updateDrawState(ds);
            ds.setUnderlineText(false);
        }

        @Override
        public void onClick(View widget) {
            ////1:用户；2：事件；3：事件评论；4：事件评论详情；5：观点；6：观点评论；7：观点评论详情
           newsUtil.analysisNews(mUrl);
            switch (newsUtil.getType()){
                case 1:
                    if(newsUtil.getUser_id().equals(preferenceUtil.getID()+"")){
                        ((HomeActivity)context).setTab(3);
                        return;
                    }else{
                        Intent intent = new Intent(context, PersonalShowActivity.class);
                        intent.putExtra("id",newsUtil.getUser_id());
                        context.startActivity(intent);
                    }
                    break;
                case 2:

                    break;
                case 3:

                    break;
                case 4:

                    break;
                case 5:

                    break;
                case 6:

                    break;
                case 7:

                    break;


            }
        }
    }
}

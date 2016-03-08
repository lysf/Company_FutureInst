package com.futureinst.widget.commentlabview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.futureinst.R;
import com.futureinst.model.comment.CommentDAO;
import com.futureinst.utils.ImageLoadOptions;
import com.futureinst.utils.Utils;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by yanhuihao on 16/2/26.
 */
public class CustomCommentLabView extends RelativeLayout {
    private int tagNum;//标签个数
    private int tagHeight;//每一组标签的高度范围
    private  int width,height;
    private Map<List<CommentDAO>,RelativeLayout> viewList = new HashMap<>();
    public CustomCommentLabView(Context context) {
        super(context);
        init();
    }

    public CustomCommentLabView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CustomCommentLabView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }
    private void init(){
        width = Utils.getScreenWidth(getContext())-Utils.dip2px(getContext(),20);
        height = width*548/702;
    }
    public void setDatas(List<CommentDAO> datas){
        removeAllViews();
        if(viewList.containsKey(datas)){
            addView(viewList.get(datas));
        }else{
            initView(datas);
        }
    }

    private void initView(List<CommentDAO> datas){
        if(datas == null || datas.size() == 0){
            return;
        }else{
            tagNum = datas.size() > 6 ? 6 : datas.size() ;
            tagHeight = (height- Utils.dip2px(getContext(),25))/tagNum;
        }
        LayoutParams params = new LayoutParams(width, height);
        RelativeLayout relativeLayout = new RelativeLayout(getContext());
        relativeLayout.setLayoutParams(params);
        for(int i = 0;i < tagNum;i++){
            if(i > 5) break;
            CommentDAO item = datas.get(i);
            TabView tabView = new TabView(getContext());
            LayoutParams layoutParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            int position = (int) (tagHeight*(i + Math.random()));

            while(item.getAttitude() == 2 && position > height - Utils.dip2px(getContext(),95) && position < height - Utils.dip2px(getContext(),30)){
                position = (int) (tagHeight*(i + Math.random()));
            }

            if(position +  Utils.dip2px(getContext(), 25) >= tagHeight * (i + 1) ){
                position -= (position + Utils.dip2px(getContext(), 25) - tagHeight * (i + 1));
            }
            layoutParams.topMargin = Utils.dip2px(getContext(),25) + position;
            String comment = item.getContent();
            if(item.getAttitude() == 1){//看好
                if(comment.length() > 15){
                    comment = comment.substring(0,15)+"...";
                }
                layoutParams.leftMargin = (int) (Math.random()*width/8) + 10;
            }else{
                if(comment.length() > 6){
                    comment = comment.substring(0,6)+"...";
                }
                if(i == 4){
                    layoutParams.leftMargin = width/2;
                }else{
                    layoutParams.leftMargin = (int) (Math.random()*width/8 + width/2);
                }
            }
            tabView.getTabTextView().setText(comment);
            ImageLoader.getInstance().displayImage(item.getUser().getHeadImage(),tabView.getRoundedImageView(), ImageLoadOptions.getOptions(R.drawable.logo));
            relativeLayout.addView(tabView, i, layoutParams);
        }
        viewList.put(datas, relativeLayout);
        addView(relativeLayout);
    }
}

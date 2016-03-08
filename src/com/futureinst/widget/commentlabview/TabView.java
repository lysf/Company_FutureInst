package com.futureinst.widget.commentlabview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.futureinst.R;
import com.futureinst.roundimageutils.RoundedImageView;
import com.futureinst.utils.Utils;

/**
 * Created by yanhuihao on 16/2/26.
 */
public class TabView extends LinearLayout{
    private TextView tabTextView;
    private RoundedImageView roundedImageView;
    public TabView(Context context) {
        super(context);
        initView();
    }

    public TabView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public TabView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }
    private void initView(){
        setOrientation(HORIZONTAL);
        setGravity(Gravity.CENTER);
        tabTextView = new TextView(getContext());
        tabTextView.setTextSize(11);
        tabTextView.setSingleLine(true);
        tabTextView.setBackground(getResources().getDrawable(R.drawable.table_cut));
        tabTextView.setTextColor(getResources().getColor(R.color.text_color_f3));
        LayoutParams params1 = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        tabTextView.setLayoutParams(params1);

        roundedImageView = new RoundedImageView(getContext());
        roundedImageView.setCornerRadius(8f);
        roundedImageView.setMutateBackground(true);
        roundedImageView.setOval(true);
        roundedImageView.setScaleType(ImageView.ScaleType.CENTER);
        LayoutParams params2 = new LayoutParams(Utils.dip2px(getContext(),16),Utils.dip2px(getContext(),16));
        params2.rightMargin = Utils.dip2px(getContext(),2);
        roundedImageView.setLayoutParams(params2);
        addView(roundedImageView);
        addView(tabTextView);
    }
    public TextView getTabTextView(){
        return this.tabTextView;
    }
    public RoundedImageView getRoundedImageView(){
        return this.roundedImageView;
    }
}

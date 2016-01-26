package com.futureinst.widget.list;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.futureinst.R;

public class MyLoadingListView extends ListView {
    private TextView tv_clickLoad;
    private TextView tv_loadComplete;
    private LinearLayout ll_loading;
	public MyLoadingListView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
        initView(context);
	}

	public MyLoadingListView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
        initView(context);
	}

	public MyLoadingListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
        initView(context);
	}
	private void initView(Context context){
        View view = LayoutInflater.from(context).inflate(R.layout.view_mylistview_bottom,null);
        tv_clickLoad = (TextView) view.findViewById(R.id.tv_clickLoad);
        ll_loading = (LinearLayout) view.findViewById(R.id.ll_loading);
        tv_loadComplete = (TextView) view.findViewById(R.id.tv_loadComplete);
        this.addFooterView(view);
    }

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int expandSpaec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
		super.onMeasure(widthMeasureSpec, expandSpaec);
	}
    public void loading(){
        tv_clickLoad.setVisibility(View.GONE);
        ll_loading.setVisibility(View.VISIBLE);
        tv_loadComplete.setVisibility(View.GONE);
    }
    public void loadStart(){
        tv_clickLoad.setVisibility(View.VISIBLE);
        ll_loading.setVisibility(View.GONE);
        tv_loadComplete.setVisibility(View.GONE);
    }
    public void loadComplete(){
        tv_clickLoad.setVisibility(View.GONE);
        ll_loading.setVisibility(View.GONE);
        tv_loadComplete.setVisibility(View.VISIBLE);
    }

}

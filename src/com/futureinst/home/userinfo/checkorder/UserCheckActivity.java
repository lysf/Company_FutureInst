package com.futureinst.home.userinfo.checkorder;


import com.futureinst.R;
import com.futureinst.baseui.BaseActivity;
import com.futureinst.home.forecast.OrderAdapter;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.util.SparseIntArray;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserCheckActivity extends BaseActivity {
    private Button[] buttons;
    private View[] views;
    private List<Fragment> fragments;
    private ViewPager container;
    private boolean transfer;
    private PopupWindow popupWindow;
    private SparseIntArray sparseIntArray;
    private boolean charge;
    private String tradeScreen = "0";
    private String consumeScreen = "0";

    @Override
    protected void localOnCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_user_check);
        setTitle("对账单");
        setRight("筛选");
        getLeftImageView().setImageDrawable(getResources().getDrawable(R.drawable.back));
        initView();

    }

    @Override
    protected void onRightClick(View view) {
        super.onRightClick(view);
        Log.i(TAG,"-----------currentItem---------->>"+container.getCurrentItem());
        if (popupWindow != null && popupWindow.isShowing()) {
            popupWindow.dismiss();
            popupWindow = null;
        } else {
            getPopupWindow(container.getCurrentItem());
            popupWindow.showAsDropDown(view);
        }

    }

    private void initView() {
        transfer = getIntent().getBooleanExtra("transfer",false);
        charge = getIntent().getBooleanExtra("charge",false);
        if(transfer){
            consumeScreen = "4";
        }
        if(charge){
            tradeScreen = "3";
        }

        sparseIntArray = new SparseIntArray();
        sparseIntArray.put(0,0);
        sparseIntArray.put(1,0);
        buttons = new Button[2];
        views = new View[2];
        fragments = new ArrayList<>();
        buttons[0] = (Button) findViewById(R.id.btn_trade);
        buttons[1] = (Button) findViewById(R.id.btn_consume);
        views[0] = findViewById(R.id.view1);
        views[1] = findViewById(R.id.view2);
        fragments.add(new UserCheckTradeFragment());
        fragments.add(new UserCheckConsumeFragment());

        buttons[0].setOnClickListener(clickListener);
        buttons[1].setOnClickListener(clickListener);
        container = (ViewPager) findViewById(R.id.container);
        MyFragmentAdapter adapter = new MyFragmentAdapter(
                getSupportFragmentManager(), fragments);
        container.setAdapter(adapter);
        container.addOnPageChangeListener(adapter);

        if(transfer){
            buttons[1].setSelected(true);
            views[1].setSelected(true);
            container.setCurrentItem(1);
            sparseIntArray.put(1, 2);
        }else{
            buttons[0].setSelected(true);
            views[0].setSelected(true);
            if(charge){
                sparseIntArray.put(0, 3);
            }
        }
    }
    public String getTradeScreen(){
        return tradeScreen;
    }
    public String getConsumeScreen(){
        return consumeScreen;
    }

    View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            views[0].setSelected(false);
            views[1].setSelected(false);
            buttons[0].setSelected(false);
            buttons[1].setSelected(false);
            switch (v.getId()) {
                case R.id.btn_trade://可交易
                    views[0].setSelected(true);
                    buttons[0].setSelected(true);
                    container.setCurrentItem(0);
                    break;
                case R.id.btn_consume://可消费
                    views[1].setSelected(true);
                    buttons[1].setSelected(true);
                    container.setCurrentItem(1);
                    break;
            }
        }
    };


    class MyFragmentAdapter extends FragmentPagerAdapter implements ViewPager.OnPageChangeListener {
        private List<Fragment> fragments;

        public MyFragmentAdapter(FragmentManager fm) {
            super(fm);
        }

        public MyFragmentAdapter(FragmentManager fm, List<Fragment> oneListFragments) {
            super(fm);
            this.fragments = oneListFragments;
        }

        @Override
        public Fragment getItem(int arg0) {
            return fragments.get(arg0);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }

        @Override
        public void onPageScrollStateChanged(int position) {
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {
        }

        @Override
        public void onPageSelected(int position) {
            views[0].setSelected(false);
            views[1].setSelected(false);
            buttons[0].setSelected(false);
            buttons[1].setSelected(false);
            views[position].setSelected(true);
            buttons[position].setSelected(true);
            container.setCurrentItem(position);
        }

    }


    private void initPopuptWindow(final int key){
        View pop_view = LayoutInflater.from(this).inflate(R.layout.view_home_order_popwindow, null, false);
        popupWindow = new PopupWindow(pop_view, RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT,true);
        pop_view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (popupWindow != null && popupWindow.isShowing()) {
                    popupWindow.dismiss();
                    popupWindow = null;
                }
                return true;
            }
        });
        ListView lv_order = (ListView) pop_view.findViewById(R.id.lv_order);
        final CheckScreenAdapter adapter = new CheckScreenAdapter(this,key);
        adapter.setIndex(sparseIntArray.get(key));
        adapter.setFlag(key);
        lv_order.setAdapter(adapter);
        lv_order.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                sparseIntArray.put(key, position);
                adapter.setIndex(position);

                Intent intent = new Intent("checkScreen");
                intent.putExtra("screen", adapter.getKey(position));
                intent.putExtra("flag",key);
                sendBroadcast(intent);
                popupWindow.dismiss();
                popupWindow = null;
            }
        });

    }
    /***
     * 获取PopupWindow实例
     */
    private void getPopupWindow(int index) {
        if (null != popupWindow) {
            popupWindow.dismiss();
            return;
        } else {
            initPopuptWindow(index);
        }
    }
}

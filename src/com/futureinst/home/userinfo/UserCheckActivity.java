package com.futureinst.home.userinfo;


import com.futureinst.R;
import com.futureinst.baseui.BaseActivity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

public class UserCheckActivity extends BaseActivity {
    private Button[] buttons;
    private View[] views;
    private List<Fragment> fragments;
    private ViewPager container;

    @Override
    protected void localOnCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_user_check);
        setTitle("对账单");
        getLeftImageView().setImageDrawable(getResources().getDrawable(R.drawable.back));
        initView();

    }

    private void initView() {


        buttons = new Button[2];
        views = new View[2];
        fragments = new ArrayList<>();
        buttons[0] = (Button) findViewById(R.id.btn_trade);
        buttons[1] = (Button) findViewById(R.id.btn_consume);
        views[0] = findViewById(R.id.view1);
        views[1] = findViewById(R.id.view2);
        fragments.add(new UserCheckTradeFragment());
        fragments.add(new UserCheckConsumeFragment());
        buttons[0].setSelected(true);
        views[0].setSelected(true);
        buttons[0].setOnClickListener(clickListener);
        buttons[1].setOnClickListener(clickListener);
        container = (ViewPager) findViewById(R.id.container);
        MyFragmentAdapter adapter = new MyFragmentAdapter(
                getSupportFragmentManager(), fragments);
        container.setAdapter(adapter);
        container.setOnPageChangeListener(adapter);
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
        }

    }

}

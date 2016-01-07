package com.futureinst.home;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Button;

import com.futureinst.R;
import com.futureinst.baseui.BaseFragment;
import com.futureinst.charge.ChargeGoodsListFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hao on 2016/1/7.
 */
public class WelfareFragment extends BaseFragment {
    private Button[] buttons;
    private List<Fragment> fragments;
    private ViewPager container;
    @Override
    protected void localOnCreate(Bundle savedInstanceState) {
        setContentView(R.layout.fragment_welfare);
        initView();
    }

    private void initView() {
        buttons = new Button[2];
        fragments = new ArrayList<>();
        fragments.add(new ShoopFragment());
        fragments.add(new ChargeGoodsListFragment());
        container = (ViewPager) findViewById(R.id.container);
        buttons[0] = (Button) findViewById(R.id.btn_shoop);
        buttons[1] = (Button) findViewById(R.id.btn_charge);
        buttons[0].setOnClickListener(clickListener);
        buttons[1].setOnClickListener(clickListener);
        MyFragmentAdapter adapter = new MyFragmentAdapter(
                getChildFragmentManager(), fragments);
        container.setAdapter(adapter);
        container.setOnPageChangeListener(adapter);
        buttons[0].setSelected(true);

    }

    View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            buttons[0].setSelected(false);
            buttons[1].setSelected(false);
            switch (v.getId()) {
                case R.id.btn_shoop:
                    buttons[0].setSelected(true);
                    container.setCurrentItem(0);
                    break;
                case R.id.btn_charge:
                    buttons[1].setSelected(true);
                    container.setCurrentItem(1);
                    break;
            }
        }
    };

    public class MyFragmentAdapter extends FragmentPagerAdapter implements ViewPager.OnPageChangeListener {
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
            buttons[0].setSelected(false);
            buttons[1].setSelected(false);
            buttons[position].setSelected(true);
        }

    }
}

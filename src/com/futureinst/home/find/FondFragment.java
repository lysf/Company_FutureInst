package com.futureinst.home.find;

import java.util.ArrayList;
import java.util.List;

import com.futureinst.R;
import com.futureinst.baseui.BaseFragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class FondFragment extends BaseFragment {
    private Button[] buttons;
    private View[] views;
    private List<Fragment> fragments;
    private ViewPager container;

    @Override
    protected void localOnCreate(Bundle savedInstanceState) {
        setContentView(R.layout.fragment_fond);
        initView();
    }

    private void initView() {
        buttons = new Button[3];
        views = new View[3];
        buttons[0] = (Button) findViewById(R.id.btn_attitude);
        buttons[1] = (Button) findViewById(R.id.btn_trends);
        buttons[2] = (Button) findViewById(R.id.btn_ranking);
        views[0] = findViewById(R.id.view1);
        views[1] = findViewById(R.id.view2);
        views[2] = findViewById(R.id.view3);
        buttons[0].setSelected(true);
        views[0].setSelected(true);
        buttons[0].setOnClickListener(clickListener);
        buttons[1].setOnClickListener(clickListener);
        buttons[2].setOnClickListener(clickListener);
        container = (ViewPager) findViewById(R.id.container);
        fragments = new ArrayList<Fragment>();
        fragments.add(new FondPointFragment());
        fragments.add(new FondIndicentFragment());
        fragments.add(new RankingFragment());
        MyFragmentAdapter adapter = new MyFragmentAdapter(
                getChildFragmentManager(), fragments);
        container.setAdapter(adapter);
        container.setOnPageChangeListener(adapter);
    }

    OnClickListener clickListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            views[0].setSelected(false);
            views[1].setSelected(false);
            views[2].setSelected(false);
            buttons[0].setSelected(false);
            buttons[1].setSelected(false);
            buttons[2].setSelected(false);
            switch (v.getId()) {
                case R.id.btn_attitude://观点
                    views[0].setSelected(true);
                    buttons[0].setSelected(true);
                    container.setCurrentItem(0);
                    break;
                case R.id.btn_trends:
                    views[1].setSelected(true);
                    buttons[1].setSelected(true);
                    container.setCurrentItem(1);
                    break;
                case R.id.btn_ranking://排名
                    views[2].setSelected(true);
                    buttons[2].setSelected(true);
                    container.setCurrentItem(2);
                    break;
            }
        }
    };

    public class MyFragmentAdapter extends FragmentPagerAdapter implements OnPageChangeListener {
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
            views[2].setSelected(false);
            buttons[0].setSelected(false);
            buttons[1].setSelected(false);
            buttons[2].setSelected(false);
            views[position].setSelected(true);
            buttons[position].setSelected(true);
        }

    }
}

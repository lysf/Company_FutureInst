package com.futureinst.home.ranking;

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

public class RankAndRecordFragment extends BaseFragment {
	private Button[] buttons;
	private View[] views;
	private List<Fragment> fragments;
	private ViewPager container;
	@Override
	protected void localOnCreate(Bundle savedInstanceState) {
		setContentView(R.layout.fragment_rank_reccord);
		initView();
	}
	private void initView() {
		buttons = new Button[2];
		views = new View[2];
		buttons[0] = (Button) findViewById(R.id.btn_ranking);
		buttons[1] = (Button) findViewById(R.id.btn_record);
		views[0] = findViewById(R.id.view1);
		views[1] = findViewById(R.id.view2);
		buttons[0].setSelected(true);
		views[0].setSelected(true);
		buttons[0].setOnClickListener(clickListener);
		buttons[1].setOnClickListener(clickListener);
		container = (ViewPager) findViewById(R.id.container);
		fragments = new ArrayList<Fragment>();
		fragments.add(new RankingFragment());
		fragments.add(new RecordFragment());
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
			switch (v.getId()) {
			case R.id.btn_ranking://排名
				views[0].setSelected(true);
				container.setCurrentItem(0);
				break;
			case R.id.btn_record://战绩
				views[1].setSelected(true);
				container.setCurrentItem(1);
				break;
			}
		}
	};
	public class MyFragmentAdapter extends FragmentPagerAdapter implements OnPageChangeListener{
		private List<Fragment> fragments;
		public MyFragmentAdapter(FragmentManager fm) {
			super(fm);
		}
		public MyFragmentAdapter(FragmentManager fm,List<Fragment> oneListFragments){
			super(fm);
			this.fragments=oneListFragments;
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
			views[position].setSelected(true);
		}
		
	}
}

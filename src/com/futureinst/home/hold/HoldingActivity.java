package com.futureinst.home.hold;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.futureinst.R;
import com.futureinst.baseui.BaseActivity;
import com.futureinst.baseui.BaseFragment;

public class HoldingActivity extends BaseActivity {
	private Button btns[];
	private View[] views;
	private List<Fragment> fragments;
	private ViewPager container;
	private MyFragmentAdapter adapter;
	@Override
	protected void localOnCreate(Bundle savedInstanceState) {
		setTitle("预测中事件");
		getLeftImageView().setImageDrawable(getResources().getDrawable(R.drawable.back));
		setContentView(R.layout.fragment_home_holding);
		initView();
	}
	@Override
	public void onResume() {
		super.onResume();
		adapter.getItem(container.getCurrentItem()).setUserVisibleHint(true);
		
	}
	private void initView() {
		btns = new Button[2];
		views = new View[2];
		fragments = new ArrayList<>();
		fragments.add(new HoldDealFragment());
		fragments.add(new HoldUnDealFragment());
		btns[0] = (Button) findViewById(R.id.btn_deal);
		btns[1] = (Button) findViewById(R.id.btn_undeal);
		views[0] = findViewById(R.id.view0);
		views[1] = findViewById(R.id.view1);
		container = (ViewPager) findViewById(R.id.container);
		adapter = new MyFragmentAdapter(
				getSupportFragmentManager(), fragments);
		container.setAdapter(adapter);
		container.setOnPageChangeListener(changeListener);
		views[0].setSelected(true);
		btns[0].setOnClickListener(clickListener);
		btns[1].setOnClickListener(clickListener);
		
//		new FragmentChildTabAdapter(this, fragments, R.id.container, btns);
	}
	OnClickListener clickListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			views[0].setSelected(false);
			views[1].setSelected(false);
			switch (v.getId()) {
			case R.id.btn_deal:
				views[0].setSelected(true);
				container.setCurrentItem(0);
				break;
			case R.id.btn_undeal:
				views[1].setSelected(true);
				container.setCurrentItem(1);
				break;
			}
		}
	};
	public void setCurrentPosition(int position){
		container.setCurrentItem(position);
		views[0].setSelected(false);
		views[1].setSelected(false);
		views[position].setSelected(true);
	}
	OnPageChangeListener changeListener = new OnPageChangeListener() {
		@Override
		public void onPageSelected(int position) {
			views[0].setSelected(false);
			views[1].setSelected(false);
			views[position].setSelected(true);
		}
		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {}
		
		@Override
		public void onPageScrollStateChanged(int arg0) {}
	};
	public class MyFragmentAdapter extends FragmentPagerAdapter{
		private List<Fragment> fragments;
		public MyFragmentAdapter(FragmentManager fm) {
			super(fm);
			// TODO Auto-generated constructor stub
		}
		public MyFragmentAdapter(FragmentManager fm,List<Fragment> oneListFragments){
			super(fm);
			this.fragments=oneListFragments;
		}
		@Override
		public Fragment getItem(int arg0) {
			// TODO Auto-generated method stub
			return fragments.get(arg0);
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return fragments.size();
		}
		
	}
	
}

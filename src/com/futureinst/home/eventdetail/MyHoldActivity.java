package com.futureinst.home.eventdetail;

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
import com.futureinst.home.hold.HoldDealFragment;
import com.futureinst.home.hold.HoldUnDealFragment;

public class MyHoldActivity extends BaseActivity {
	private Button btns[];
	private List<Fragment> fragments;
	private ViewPager container;
	@Override
	protected void localOnCreate(Bundle savedInstanceState) {
		setTitle("我的持仓");
		getLeftImageView().setImageDrawable(getResources().getDrawable(R.drawable.back));
		setContentView(R.layout.activity_my_hode);
		initView();
	}
	@Override
	protected void onLeftImageViewClick(View view) {
		super.onLeftImageViewClick(view);
		finish();
	}
	private void initView() {
		btns = new Button[2];
		fragments = new ArrayList<Fragment>();
		fragments.add(new HoldDealFragment());
		fragments.add(new HoldUnDealFragment());
		btns[0] = (Button) findViewById(R.id.btn_deal);
		btns[1] = (Button) findViewById(R.id.btn_undeal);
		container = (ViewPager) findViewById(R.id.container);
		MyFragmentAdapter adapter = new MyFragmentAdapter(
				getSupportFragmentManager(), fragments);
		container.setAdapter(adapter);
		container.setOnPageChangeListener(changeListener);
		btns[0].setSelected(true);
		btns[0].setOnClickListener(clickListener);
		btns[1].setOnClickListener(clickListener);
		
//		new FragmentChildTabAdapter(this, fragments, R.id.container, btns);
	}
	OnClickListener clickListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			btns[0].setSelected(false);
			btns[1].setSelected(false);
			switch (v.getId()) {
			case R.id.btn_deal:
				btns[0].setSelected(true);
				container.setCurrentItem(0);
				break;
			case R.id.btn_undeal:
				btns[1].setSelected(true);
				container.setCurrentItem(1);
				break;
			}
		}
	};
	
	OnPageChangeListener changeListener = new OnPageChangeListener() {
		@Override
		public void onPageSelected(int position) {
			btns[0].setSelected(false);
			btns[1].setSelected(false);
			btns[position].setSelected(true);
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

package com.futureinst.index;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;

import com.futureinst.R;
import com.futureinst.baseui.BaseActivity;
import com.futureinst.home.HomeActivity;
import com.futureinst.sharepreference.SharePreferenceUtil;


public class IndexActivity extends BaseActivity{
	private ViewPager viewPager;
	private List<View> pagers;
	private IndexViewPagerAdapter adapter;
	private SharePreferenceUtil sharedPreferences;
	private Button btn_skip;
	@Override
	protected void localOnCreate(Bundle savedInstanceState) {

		sharedPreferences = SharePreferenceUtil.getInstance(this);
		if(!sharedPreferences.isFirstLogin()){
			Intent intent = new Intent(IndexActivity.this, HomeActivity.class);
			startActivity(intent);
			finish();
			return;
		}
		setContentView(R.layout.index);
		initView();
		for (int i = 0; i < pagers.size(); i++) {
			ImageView child = new ImageView(this);
			LayoutParams layoutParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
			layoutParams.setMargins(20, 0, 20, 0);
			child.setLayoutParams(layoutParams);
			child.setImageResource(R.drawable.dot_1);
			dot.addView(child);
		}
		initDots();
		sharedPreferences.setUnIsFirstLogin();
	}
	private void initView() {
		// TODO Auto-generated method stub
		btn_skip = (Button) findViewById(R.id.btn_skip);
		viewPager = (ViewPager)this.findViewById(R.id.viewpager);
		dot = (LinearLayout) findViewById(R.id.linear_dot_array);
		pagers = new ArrayList<View>();
		setPagers();
		adapter = new IndexViewPagerAdapter(IndexActivity.this, pagers);
		viewPager.setAdapter(adapter);
		viewPager.setOnPageChangeListener(adapter);
		btn_skip.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				startActivity(new Intent(IndexActivity.this, HomeActivity.class));
				finish();
			}
		});
	}
	//设置图片
	private void setPagers(){
		View view1 = LayoutInflater.from(this).inflate(R.layout.index_1, null);
		View view2 = LayoutInflater.from(this).inflate(R.layout.index_2, null);
		View view3 = LayoutInflater.from(this).inflate(R.layout.index_3, null);
		Button btn_enter = (Button) view3.findViewById(R.id.btn_enter);
		btn_enter.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				startActivity(new Intent(IndexActivity.this, HomeActivity.class));
				finish();
			}
		});
		pagers.add(view1);
		pagers.add(view2);
		pagers.add(view3);
	}
	@Override
	protected void onResume() {
		super.onResume();
	}
	@Override
	protected void onPause() {
		super.onPause();
	}
	
	private LinearLayout dot;
	private ImageView[] dots;// 底部小点图片
	private int currentIndex;// 记录当前选中位置
	
	private void initDots() {
		// TODO Auto-generated method stub
		dots = new ImageView[pagers.size()];

		// 循环取得小点图片
		for (int i = 0; i < pagers.size(); i++) {
			dots[i] = (ImageView) dot.getChildAt(i);
		}
		
		currentIndex = 0;
		dots[currentIndex].setImageResource(R.drawable.dot);// 设置选中状态

	}

	private void setCurrentDot(int position) {
		if (position < 0 || position > pagers.size() - 1
				|| currentIndex == position) {
			return;
		}

		dots[currentIndex].setImageResource(R.drawable.dot_1);// 设置未选中状态
		dots[position].setImageResource(R.drawable.dot);// 设置选中状态
		currentIndex = position;
	}
	
	private class IndexViewPagerAdapter extends PagerAdapter implements OnPageChangeListener{
		private List<View> list;
		public IndexViewPagerAdapter(Context context,List<View> list){
			this.list = list;
		}
		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			View view = list.get(position);
			((ViewPager) container).addView(view);
			return view;
		}
		@Override
		public int getCount() {
			return list.size();
		}

		@Override
		public boolean isViewFromObject(View v, Object o) {
			return v == o;
		}
		@Override
		public CharSequence getPageTitle(int position) {
			// TODO Auto-generated method stub
			return super.getPageTitle(position);
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			// TODO Auto-generated method stub
			// super.destroyItem(container, position, object);
			((ViewPager) container).removeView(list.get(position));
		}
		
		@Override
		public void onPageScrollStateChanged(int arg0) {
			// TODO Auto-generated method stub
			
		}
		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {
			// TODO Auto-generated method stub
			
		}
		@Override
		public void onPageSelected(int arg0) {
			// TODO Auto-generated method stub
			viewPager.setCurrentItem(arg0, true);
			setCurrentDot(arg0);
		}
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
	}
	
}

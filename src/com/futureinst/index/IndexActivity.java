package com.futureinst.index;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
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
import com.futureinst.global.Content;
import com.futureinst.home.HomeActivity;
import com.futureinst.sharepreference.SharePreferenceUtil;
import com.futureinst.utils.Utils;
import com.umeng.analytics.AnalyticsConfig;
import com.umeng.analytics.MobclickAgent;


public class IndexActivity extends BaseActivity{
	private ViewPager viewPager;
	private IndexViewPagerAdapter adapter;
	private SharePreferenceUtil sharedPreferences;
	private Button btn_skip;
    private int count = 5;
	@Override
	protected void localOnCreate(Bundle savedInstanceState) {
		MobclickAgent.openActivityDurationTrack(false); 
		AnalyticsConfig.enableEncrypt(true);
		MobclickAgent.updateOnlineConfig(this);
        Content.DEVICE_ID = Utils.getUniquePsuedoID();
        sharedPreferences = SharePreferenceUtil.getInstance(this);
		if(!sharedPreferences.isFirstLogin()){
			Intent intent = new Intent(IndexActivity.this, PrimaryActivity.class);
			startActivity(intent);
			finish();
			return;
		}
		setContentView(R.layout.index);
		initView();

//		initDots();
		sharedPreferences.setUnIsFirstLogin();
	}
	private void initView() {
		// TODO Auto-generated method stub
		btn_skip = (Button) findViewById(R.id.btn_skip);
		viewPager = (ViewPager)this.findViewById(R.id.viewpager);
//		dot = (LinearLayout) findViewById(R.id.linear_dot_array);
//		dot.setVisibility(View.INVISIBLE);

		adapter = new IndexViewPagerAdapter(getSupportFragmentManager());
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

	@Override
	protected void onResume() {
		super.onResume();
	}
	@Override
	protected void onPause() {
		super.onPause();
	}
	
//	private LinearLayout dot;
//	private ImageView[] dots;// 底部小点图片
//	private int currentIndex;// 记录当前选中位置
	
//	private void initDots() {
//		// TODO Auto-generated method stub
//		dots = new ImageView[count];
//		// 循环取得小点图片
//		for (int i = 0; i < count; i++) {
//			dots[i] = new ImageView(this);
//            dots[i].setPadding(Utils.dip2px(this,5)
//                    ,Utils.dip2px(this,5),Utils.dip2px(this,5),Utils.dip2px(this,5));
//
//		}
//
//		currentIndex = 0;
//		dots[currentIndex].setImageResource(R.drawable.dot);// 设置选中状态
//
//	}

//	private void setCurrentDot(int position) {
//		if (position < 0 || position > count
//				|| currentIndex == position) {
//			return;
//		}
//
//		dots[currentIndex].setImageResource(R.drawable.dot_1);// 设置未选中状态
//		dots[position].setImageResource(R.drawable.dot);// 设置选中状态
//		currentIndex = position;
//	}
	
	private class IndexViewPagerAdapter extends FragmentPagerAdapter implements OnPageChangeListener{

        public IndexViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return IndexFragment.getInstance(position);
        }

		@Override
		public int getCount() {
			return count;
		}

		@Override
		public CharSequence getPageTitle(int position) {
			// TODO Auto-generated method stub
			return super.getPageTitle(position);
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			// TODO Auto-generated method stub
			 super.destroyItem(container, position, object);
//			container.removeView(list.get(position));
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
//			setCurrentDot(arg0);
		}
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
	}
	
}

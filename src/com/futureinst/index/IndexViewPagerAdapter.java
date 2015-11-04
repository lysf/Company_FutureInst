package com.futureinst.index;

import java.util.List;

import android.app.Activity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.ViewGroup;



public class IndexViewPagerAdapter extends PagerAdapter implements OnPageChangeListener{
	private List<View> list;
	private Activity context;
	public IndexViewPagerAdapter(Activity context,List<View> list){
		this.context = context;
		this.list = list;
	}
	@Override
	public Object instantiateItem(ViewGroup container, int position) {
		View view = list.get(position);
		container.addView(view);
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
		container.removeView(list.get(position));
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
		
	}

}

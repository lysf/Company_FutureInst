package com.futureinst.home;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

public class VerticalPagerAdapter extends FragmentPagerAdapter{

	private int secondTitle;
	public VerticalPagerAdapter(FragmentManager fm) {
		super(fm);
	}
	@Override
	public Fragment getItem(int position) {
		// TODO Auto-generated method stub
		return HomeTypeContainerFragment.getInstance(position, secondTitle);
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return 9;
	}
	@Override
	public int getItemPosition(Object object) {
		// TODO Auto-generated method stub
		return super.getItemPosition(object);
	}
//	 @Override  
//     public boolean isViewFromObject(View arg0, Object arg1) {  
//         return arg0 == (arg1);  
//     } 
	@Override
	public void destroyItem(View container, int position, Object object) {
		// TODO Auto-generated method stub
		((ViewPager) container).removeView((View)object);  
	}
	public void setSecondTitle(int secondTitle){
		this.secondTitle = secondTitle;
	}
	
}

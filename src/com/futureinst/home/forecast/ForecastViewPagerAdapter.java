package com.futureinst.home.forecast;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

public class ForecastViewPagerAdapter extends FragmentPagerAdapter {
	private String[] titles;
	private int[] tabTag;
	public ForecastViewPagerAdapter(FragmentManager fm,String[] titles) {
		super(fm);
		this.titles = titles;
		tabTag = new int[]{0,1,4,2,3,7,6,5,8};
	}
	public void setTitles(String[] titles){
		this.titles = titles;
		notifyDataSetChanged();
	}
	@Override
	public CharSequence getPageTitle(int position) {
		return titles[position];
	}
	@Override
	public Fragment getItem(int arg0) {
		
		return ForecastContainerTypeFragment.newInstance(tabTag[arg0]);
	}
	@Override
	public int getCount() {
		return titles.length;
	}
	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		super.destroyItem(container, position, object);
	}
}

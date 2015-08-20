package com.futureinst.home.forecast;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

public class ForecastViewPagerAdapter extends FragmentPagerAdapter {
	private String[] titles;
	public ForecastViewPagerAdapter(FragmentManager fm,String[] titles) {
		super(fm);
		this.titles = titles;
	}
	@Override
	public CharSequence getPageTitle(int position) {
		return titles[position];
	}
	@Override
	public Fragment getItem(int arg0) {
		
		return ForecastContainerTypeFragment.newInstance(arg0);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return titles.length;
	}
	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
//		super.destroyItem(container, position, object);
	}
}

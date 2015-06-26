package com.futureinst.home;

import java.util.List;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.ViewGroup;

public class VerticalPagerAdapter extends FragmentStatePagerAdapter{

	private int secondTitle;
	public VerticalPagerAdapter(FragmentManager fm) {
		super(fm);
	}
	public VerticalPagerAdapter(FragmentManager fm,List<Fragment> fragments){
		super(fm);
	}
	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		
		super.destroyItem(container, position, object);
	}
	@Override
	public Fragment getItem(int position) {
		
		return new HomeTypeContainerFragment(position, secondTitle);
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
	public void setSecondTitle(int secondTitle){
		this.secondTitle = secondTitle;
	}
	
}

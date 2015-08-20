package com.futureinst.home.forecast;

import com.futureinst.R;
import com.futureinst.baseui.BaseFragment;
import com.futureinst.widget.CustomViewPager;
import com.futureinst.widget.PagerSlidingTabStrip;

import android.os.Bundle;
import android.support.v4.view.ViewPager.OnPageChangeListener;

public class ForecastFragment extends BaseFragment implements OnPageChangeListener{
	private CustomViewPager viewPager;
	private String[] titles;
	private PagerSlidingTabStrip slidingTab;
	private ForecastViewPagerAdapter adapter;
	
	@Override
	protected void localOnCreate(Bundle savedInstanceState) {
		setTitle(R.string.app_name);
		getTitleView().setTextSize(22);
		setContentView(R.layout.fragment_forecast);
		initView();
	}
	private void initView() {
		slidingTab = (PagerSlidingTabStrip) findViewById(R.id.slidingTab);
		viewPager = (CustomViewPager) findViewById(R.id.pager);
		titles = getResources().getStringArray(R.array.home_title);
		adapter = new ForecastViewPagerAdapter(getChildFragmentManager(), titles);
		viewPager.setAdapter(adapter);
//		viewPager.setOffscreenPageLimit(2);
		slidingTab.setViewPager(viewPager);
		slidingTab.setIndicatorColor(getResources().getColor(R.color.tab_text_selected));
	}
	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		adapter.getItem(viewPager.getCurrentItem()).setUserVisibleHint(true);
	}
	@Override
	public void onPageScrollStateChanged(int arg0) {
	}
	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
		((ForecastContainerTypeFragment)adapter.getItem(arg0)).setIsHaveTop(false);
	}
	@Override
	public void onPageSelected(int arg0) {
		((ForecastContainerTypeFragment)adapter.getItem(arg0)).setUserVisibleHint(true);
		((ForecastContainerTypeFragment)adapter.getItem(arg0)).setIsHaveTop(true);
	}
	
}

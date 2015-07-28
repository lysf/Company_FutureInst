package com.futureinst.home.forecast;

import com.futureinst.R;
import com.futureinst.baseui.BaseFragment;
import com.futureinst.widget.PagerSlidingTabStrip;

import android.os.Bundle;
import android.support.v4.view.ViewPager;

public class ForecastFragment extends BaseFragment {
	private ViewPager viewPager;
	private String[] titles;
	private PagerSlidingTabStrip slidingTab;
	private ForecastViewPagerAdapter adapter;
	
	@Override
	protected void localOnCreate(Bundle savedInstanceState) {
//		setTitle(R.string.tab_forecast);
		setContentView(R.layout.fragment_forecast);
		initView();
	}
	private void initView() {
		slidingTab = (PagerSlidingTabStrip) findViewById(R.id.slidingTab);
		viewPager = (ViewPager) findViewById(R.id.pager);
		titles = getResources().getStringArray(R.array.home_title);
		adapter = new ForecastViewPagerAdapter(getChildFragmentManager(), titles);
		viewPager.setAdapter(adapter);
		slidingTab.setViewPager(viewPager);
		slidingTab.setIndicatorColor(getResources().getColor(R.color.tab_text_selected));
	}
	
}

package com.futureinst.home;



import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import com.futureinst.R;
import com.futureinst.baseui.BaseFragment;
import com.futureinst.widget.verticalviewpager.VerticalViewPager;
import com.futureinst.widget.verticalviewpager.VerticalViewPager.OnPageChangeListener;

public class HomeTypeFragment extends BaseFragment {
	private VerticalViewPager verticalViewPager;
	private int primaryTitle = 0;
	private int secondTitle = 0;
	private Context context;

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		context = activity;
		Bundle bundle = this.getArguments();
		primaryTitle = bundle.getInt("primaryTitle", 0);
		secondTitle = bundle.getInt("secondTitle", 0);

	}

	@Override
	protected void localOnCreate(Bundle savedInstanceState) {
		initView();
	}

	private void initView() {
		setContentView(R.layout.activity_home);
		verticalViewPager = (VerticalViewPager) findViewById(R.id.pager);
		VerticalPagerAdapter adapter = new VerticalPagerAdapter(getChildFragmentManager());
		adapter.setSecondTitle(secondTitle);
		verticalViewPager.setAdapter(adapter);
		verticalViewPager.setOnPageChangeListener(changeListener);
		verticalViewPager.setCurrentItem(primaryTitle);
	}


	OnPageChangeListener changeListener = new OnPageChangeListener() {

		@Override
		public void onPageSelected(int position) {
			if (context instanceof HomeActivity)
				((HomeActivity) context).showBottomView(position);
		}

		@Override
		public void onPageScrolled(int position, float positionOffset,
				int positionOffsetPixels) {
		}

		@Override
		public void onPageScrollStateChanged(int state) {
		}
	};
}

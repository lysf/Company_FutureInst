package com.futureinst.home.userinfo;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

import com.futureinst.R;
import com.futureinst.baseui.BaseActivity;
import com.futureinst.home.forecast.ForecastContainerTypeFragment;
import com.futureinst.home.hold.HoldingActivity.MyFragmentAdapter;

public class AboutUsActivity extends BaseActivity {
	private List<Fragment> fragments;
	private Button[] btns;
	private View[] views;
	private ViewPager container;
	private int position = 0;
	@Override
	protected void localOnCreate(Bundle savedInstanceState) {
		setContentView(R.layout.activity_about_us);
		setTitle(getResources().getString(R.string.about_us));
		getLeftImageView().setImageDrawable(getResources().getDrawable(R.drawable.back));
//		setTitleBackGround(getResources().getColor(R.color.login_title_layout_back));
		initView();
	}
	@Override
	protected void onLeftImageViewClick(View view) {
		super.onLeftImageViewClick(view);
		finish();
	}
	private void initView(){
        position = getIntent().getIntExtra("position",0);
		btns = new Button[4];
		views = new View[4];
		btns[0] = (Button) findViewById(R.id.btn_future);
		btns[1] = (Button) findViewById(R.id.btn_media_report);
		btns[2] = (Button) findViewById(R.id.btn_contact_us);
		btns[3] = (Button) findViewById(R.id.btn_guidang);
		views[0] = findViewById(R.id.view1);
		views[1] = findViewById(R.id.view2);
		views[2] = findViewById(R.id.view3);
		views[3] = findViewById(R.id.view4);
		container = (ViewPager) findViewById(R.id.container);
		views[0].setSelected(true);
		btns[0].setOnClickListener(clickListener);
		btns[1].setOnClickListener(clickListener);
		btns[2].setOnClickListener(clickListener);
		btns[3].setOnClickListener(clickListener);
		fragments = new ArrayList<Fragment>();
		for(int i=0;i<2;i++){
			WebViewFragment fragment = new WebViewFragment();
			Bundle bundle = new Bundle();
			bundle.putInt("index", i);
			fragment.setArguments(bundle);
			fragments.add(fragment);
		}
        ContactFragment contactFragment = new ContactFragment();
        fragments.add(contactFragment);
		ForecastContainerTypeFragment forecastContainerTypeFragment = ForecastContainerTypeFragment.newInstance(-1);
		fragments.add(forecastContainerTypeFragment);
		MyFragmentAdapter adapter = new MyFragmentAdapter(
			getSupportFragmentManager(), fragments);
		container.setAdapter(adapter);
		container.setOnPageChangeListener(changeListener);
        container.setCurrentItem(position);
        btns[position].setSelected(true);
        views[position].setSelected(true);
	}
	
	OnPageChangeListener changeListener = new OnPageChangeListener() {
		@Override
		public void onPageSelected(int position) {
			views[0].setSelected(false);
			views[1].setSelected(false);
			views[2].setSelected(false);
			views[3].setSelected(false);
            btns[0].setSelected(false);
            btns[1].setSelected(false);
            btns[2].setSelected(false);
            btns[3].setSelected(false);
            btns[position].setSelected(true);
            views[position].setSelected(true);
        }
		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {}
		
		@Override
		public void onPageScrollStateChanged(int arg0) {}
	};
	public class MyFragmentAdapter extends FragmentPagerAdapter{
		private List<Fragment> fragments;
		public MyFragmentAdapter(FragmentManager fm) {
			super(fm);
			// TODO Auto-generated constructor stub
		}
		public MyFragmentAdapter(FragmentManager fm,List<Fragment> oneListFragments){
			super(fm);
			this.fragments=oneListFragments;
		}
		@Override
		public Fragment getItem(int arg0) {
			// TODO Auto-generated method stub
			return fragments.get(arg0);
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return fragments.size();
		}
		
	}
	OnClickListener clickListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			views[0].setSelected(false);
			views[1].setSelected(false);
			views[2].setSelected(false);
			views[3].setSelected(false);
            btns[0].setSelected(false);
            btns[1].setSelected(false);
            btns[2].setSelected(false);
            btns[3].setSelected(false);

			switch (v.getId()) {
			case R.id.btn_future://未来研究所
				if(position == 0) return;
				views[0].setSelected(true);
                btns[0].setSelected(true);
				container.setCurrentItem(0);
				position = 0;
				break;
			case R.id.btn_media_report://媒体报道
				if(position == 1) return;
				views[1].setSelected(true);
                btns[1].setSelected(true);
				container.setCurrentItem(1);
				position = 1;
				break;
			case R.id.btn_contact_us://联系我们
				if(position == 2) return;
				views[2].setSelected(true);
                btns[2].setSelected(true);
				container.setCurrentItem(2);
				position = 2;
				break;
			case R.id.btn_guidang://归档事件
				if(position == 3) return;
				views[3].setSelected(true);
                btns[3].setSelected(true);
				container.setCurrentItem(3);
				position = 3;
				break;
			}
		}
	};
}

package com.futureinst.comment;


import java.util.ArrayList;
import java.util.List;

import com.futureinst.R;
import com.futureinst.baseui.BaseActivity;
import com.futureinst.home.forecast.BottomViewpagerAdapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class CommentActivity extends BaseActivity{
	private String event_id;
	private Button[] btns;
	private List<Fragment> fragments;
	private View[] lines;
	private ViewPager pager;
	@Override
	protected void localOnCreate(Bundle savedInstanceState) {
		setTitle("评论");
		getLeftImageView().setImageDrawable(getResources().getDrawable(R.drawable.back));
		setContentView(R.layout.activity_comment);
		initView();
	}
	@Override
	protected void onLeftImageViewClick(View view) { 
		super.onLeftImageViewClick(view);
		finish();
	}
	private void initView() {
		event_id = getIntent().getStringExtra("eventId");
		btns = new Button[2];
		lines = new View[2];
		fragments = new ArrayList<Fragment>();
		GoodCommentFragment goodCommentFragment = new GoodCommentFragment();
		BadCommentFragment badCommentFragment = new BadCommentFragment();
		Bundle bundle = new Bundle();
		bundle.putString("eventId", event_id);
		goodCommentFragment.setArguments(bundle);
		badCommentFragment.setArguments(bundle);
		fragments.add(goodCommentFragment);
		fragments.add(badCommentFragment);
		btns[0]  = (Button) findViewById(R.id.btn_good_comment);
		btns[1]  = (Button) findViewById(R.id.btn_bad_comment);
		btns[0].setOnClickListener(clickListener);
		btns[1].setOnClickListener(clickListener);
		lines[0] = findViewById(R.id.view1);
		lines[1] = findViewById(R.id.view2);
		pager = (ViewPager) findViewById(R.id.container);
//		new FragmentActivityTabAdapter(this, fragments, R.id.container, btns,lines);
		lines[0].setSelected(true);
		pager.setAdapter(new BottomViewpagerAdapter(getSupportFragmentManager(), fragments));
		pager.setOnPageChangeListener(new OnPageChangeListener() {
			
			@Override
			public void onPageSelected(int arg0) {
				for(View view : lines) 
					view.setSelected(false);
				lines[arg0].setSelected(true);
			}
			
			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
			}
			@Override
			public void onPageScrollStateChanged(int arg0) {
			}
		});
	}
	OnClickListener clickListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.btn_good_comment:
				pager.setCurrentItem(0);
				break;
			case R.id.btn_bad_comment:
				pager.setCurrentItem(1);
				break;
			}
			
		}
	};
	
}

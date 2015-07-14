package com.futureinst.comment;


import java.util.ArrayList;
import java.util.List;

import com.futureinst.R;
import com.futureinst.baseui.BaseActivity;
import com.futureinst.utils.FragmentActivityTabAdapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.Button;

public class CommentActivity extends BaseActivity{
	private String event_id;
	private Button[] btns;
	private List<Fragment> fragments;
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
		new FragmentActivityTabAdapter(this, fragments, R.id.container, btns);
	}
	
	
}

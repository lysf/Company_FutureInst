package com.futureinst.personalinfo.other;

import com.futureinst.R;
import com.futureinst.baseui.BaseActivity;

import android.os.Bundle;

public class PersonalCommentActivity extends BaseActivity {

	@Override
	protected void localOnCreate(Bundle savedInstanceState) {
		setTitle(R.string.show_comment);
		getLeftImageView().setImageDrawable(getResources().getDrawable(R.drawable.back));

	}

}

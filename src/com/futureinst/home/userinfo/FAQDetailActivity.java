package com.futureinst.home.userinfo;

import android.os.Bundle;
import android.widget.TextView;

import com.futureinst.R;
import com.futureinst.baseui.BaseActivity;
import com.futureinst.model.usermodel.FaqDAO;

public class FAQDetailActivity extends BaseActivity {
	private FaqDAO faqDAO;
	private TextView tv_content;
	@Override
	protected void localOnCreate(Bundle savedInstanceState) {
		setContentView(R.layout.activity_faq_detail);
		
		tv_content = (TextView) findViewById(R.id.tv_detail);
		faqDAO = (FaqDAO) getIntent().getSerializableExtra("faq");
		setTitle(faqDAO.getTitle().replace("?", ""));
		getLeftImageView().setImageDrawable(getResources().getDrawable(R.drawable.back));
		tv_content.setText(faqDAO.getContent());
	}

}

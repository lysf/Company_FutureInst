package com.futureinst.home.eventdetail;

import com.futureinst.R;
import com.futureinst.baseui.BaseActivity;
import com.futureinst.model.homeeventmodel.QueryEventDAO;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class EventBuyActivity extends BaseActivity {
	private QueryEventDAO event;
	private EditText et_price,et_num;
	private Button submit;
	private boolean isBuy;
	@Override
	protected void localOnCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		getLeftImageView().setImageDrawable(getResources().getDrawable(R.drawable.back));
		setContentView(R.layout.event_buy);
		event = (QueryEventDAO) getIntent().getSerializableExtra("event");
		isBuy = getIntent().getBooleanExtra("buyOrSell", false);
		
		setTitle(event.getTitle().subSequence(0, 8));
		
		et_price = (EditText) findViewById(R.id.et_price);
		et_num = (EditText) findViewById(R.id.et_num);
		submit = (Button) findViewById(R.id.btn_type);
		et_price.setText(String.format("%.1f", event.getCurrPrice()));
		et_num.setText("10");
		if(isBuy){
			submit.setBackground(getResources().getDrawable(R.drawable.btn_buy_back));
		}else{
			submit.setBackground(getResources().getDrawable(R.drawable.btn_sell_back));
		}
		submit.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
			}
		});
	}

}

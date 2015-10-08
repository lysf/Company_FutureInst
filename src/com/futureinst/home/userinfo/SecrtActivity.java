package com.futureinst.home.userinfo;

import com.futureinst.R;
import com.futureinst.baseui.BaseActivity;
import com.futureinst.model.usermodel.PermitDAO;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TableRow;
import android.widget.TextView;

public class SecrtActivity extends BaseActivity {
	private TableRow[] raws;
	private BroadcastReceiver receiver;
	private TextView[] tv_secrets;
	private String[] values = new String[]{"所有人","关注我的人","不公开"};
	private PermitDAO permits;
	@Override
	protected void localOnCreate(Bundle savedInstanceState) {
		setTitle(R.string.secret);
		getLeftImageView().setImageDrawable(getResources().getDrawable(R.drawable.back));
		setContentView(R.layout.activity_secrt);
		initView();
		initPermit(permits);
	}
	
	private void initView() {
		permits = (PermitDAO) getIntent().getSerializableExtra("permit");
		receiver = new BroadcastReceiver() {
			@Override
			public void onReceive(Context context, Intent intent) {
				int value = intent.getIntExtra("value", 0);
				int type = intent.getIntExtra("type", 0);
				setPermit(type, value);
				tv_secrets[type].setText(values[value]);
			}
		};
		raws = new TableRow[4];
		raws[0] = (TableRow) findViewById(R.id.tableRow0);
		raws[1] = (TableRow) findViewById(R.id.tableRow1);
		raws[2] = (TableRow) findViewById(R.id.tableRow2);
		raws[3] = (TableRow) findViewById(R.id.tableRow3);
		raws[0].setOnClickListener(clickListener);
		raws[1].setOnClickListener(clickListener);
		raws[2].setOnClickListener(clickListener);
		raws[3].setOnClickListener(clickListener);
		tv_secrets = new TextView[4];
		tv_secrets[0] = (TextView) findViewById(R.id.tv_secret_1); 
		tv_secrets[1] = (TextView) findViewById(R.id.tv_secret_2); 
		tv_secrets[2] = (TextView) findViewById(R.id.tv_secret_3); 
		tv_secrets[3] = (TextView) findViewById(R.id.tv_secret_4); 
		IntentFilter filter = new IntentFilter();
		filter.addAction("set");
		registerReceiver(receiver, filter);
	}
	private void initPermit(PermitDAO dao){
		tv_secrets[0].setText(getPermit(dao.getOrder()));
		tv_secrets[1].setText(getPermit(dao.getGain()));
		tv_secrets[2].setText(getPermit(dao.getMe_follow()));
		tv_secrets[3].setText(getPermit(dao.getFollow_me()));
	}
	private String getPermit(String permit){
		if(permit.equals("all")){
			return values[0];
		}else if(permit.equals("follow")){
			return values[1];
		}else{
			return values[2];
		}
	}
	private void setPermit(int type ,int value){
		switch (value) {
		case 0://all
			setPermit_2(type, "all");
			break;
		case 1://follow
			setPermit_2(type, "follow");
			break;
		case 2://none
			setPermit_2(type, "none");
			break;
		}
	}
	private void setPermit_2(int type ,String value){
		switch (type) {
		case 0://预测中事件
			permits.setOrder(value);
			break;
		case 1://战绩
			permits.setGain(value);
			break;
		case 2://我关注的人
			permits.setMe_follow(value);
			break;
		case 3://关注我的人
			permits.setFollow_me(value);
			break;
		}
	}
	private int getPermitValue(String permit){
		if(permit.equals("all")){
			return 0;
		}else if(permit.equals("follow")){
			return 1;
		}else{
			return 2;
		}
	}
	OnClickListener clickListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.tableRow0://预测中事件
				Intent intent1 = new Intent(SecrtActivity.this, SetActivity.class);
				intent1.putExtra("type", 0);
				intent1.putExtra("value", getPermitValue(permits.getOrder()));
				startActivity(intent1);
				break;
			case R.id.tableRow1://战绩
				Intent intent2 = new Intent(SecrtActivity.this, SetActivity.class);
				intent2.putExtra("type", 1);
				intent2.putExtra("value", getPermitValue(permits.getGain()));
				startActivity(intent2);
				break;
			case R.id.tableRow2://我关注的人
				Intent intent3 = new Intent(SecrtActivity.this, SetActivity.class);
				intent3.putExtra("type", 2);
				intent3.putExtra("value", getPermitValue(permits.getMe_follow()));
				startActivity(intent3);
				break;
			case R.id.tableRow3://关注我的人
				Intent intent4 = new Intent(SecrtActivity.this, SetActivity.class);
				intent4.putExtra("type", 3);
				intent4.putExtra("value", getPermitValue(permits.getFollow_me()));
				startActivity(intent4);
				break;
			}
			
		}
	};
	@Override
	protected void onDestroy() {
		super.onDestroy();
		unregisterReceiver(receiver);
	}
}

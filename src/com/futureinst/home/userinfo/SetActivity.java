package com.futureinst.home.userinfo;

import org.json.JSONException;

import com.futureinst.R;
import com.futureinst.baseui.BaseActivity;
import com.futureinst.global.Content;
import com.futureinst.model.basemodel.BaseModel;
import com.futureinst.net.PostCommentResponseListener;
import com.futureinst.net.PostMethod;
import com.futureinst.net.PostType;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TableRow;

public class SetActivity extends BaseActivity {
	private TableRow[] tableRows;
	private ImageView[] imageViews;
	private int type,value;
	//permit_key:order(预测中事件),gain(战绩),follow_me（关注我的人）,me_follow（我关注的人）
	private String[] permit_keys = new String[]{"order","gain","follow_me","me_follow"};
	//permit_value: all（所有人可看），follow(关注我的人可看)，none（没有人可以看）
	private String[] permit_values = new String[]{"all","follow","none"};
	@Override
	protected void localOnCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		setTitle("隐私设置");
		getLeftImageView().setImageDrawable(getResources().getDrawable(R.drawable.back));
		setContentView(R.layout.actiivty_set);
		initView();
	}
	@Override
	protected void onLeftImageViewClick(View view) {
		super.onLeftImageViewClick(view);
		onBackPressed();
	}
	@Override
	public void onBackPressed() {
		super.onBackPressed();
		update_permit();
		
	}
	private void initView() {
		type = getIntent().getIntExtra("type", 0);
		value = getIntent().getIntExtra("value", 0);
		tableRows = new TableRow[3];
		imageViews = new ImageView[3];
		tableRows[0] = (TableRow) findViewById(R.id.tableRow1);
		tableRows[1] = (TableRow) findViewById(R.id.tableRow2);
		tableRows[2] = (TableRow) findViewById(R.id.tableRow3);
		imageViews[0] = (ImageView) findViewById(R.id.iv_set_1);
		imageViews[1] = (ImageView) findViewById(R.id.iv_set_2);
		imageViews[2] = (ImageView) findViewById(R.id.iv_set_3);
		imageViews[value].setSelected(true);
		tableRows[0].setOnClickListener(clickListener);
		tableRows[1].setOnClickListener(clickListener);
		tableRows[2].setOnClickListener(clickListener);
	}
	OnClickListener clickListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			imageViews[value].setSelected(false);
			switch (v.getId()) {
			case R.id.tableRow1://所有人
				imageViews[0].setSelected(true);
				value = 0;
				break;
			case R.id.tableRow2://互相关注的人
				imageViews[1].setSelected(true);
				value = 1;
				break;
			case R.id.tableRow3://不公开
				imageViews[2].setSelected(true);
				value = 2;
				break;
			}
		}
	};
	//隐私设置
	private void update_permit(){
		Content.isPull = true;
		httpResponseUtils.postJson(httpPostParams.getPostParams(PostMethod.update_permit.name(), PostType.user.name(), 
				httpPostParams.update_permit(preferenceUtil.getUUid(), preferenceUtil.getID()+"", permit_keys[type], permit_values[value])),
				BaseModel.class, new PostCommentResponseListener() {
					@Override
					public void requestCompleted(Object response) throws JSONException {
						Content.isPull = false;
						if(response == null)
							return;
						Intent intent = new Intent("set");
						intent.putExtra("type", type);
						intent.putExtra("value", value);
						sendBroadcast(intent);
						
					}
				});
	}
}

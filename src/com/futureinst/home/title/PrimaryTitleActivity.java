package com.futureinst.home.title;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.futureinst.R;
import com.futureinst.baseui.BaseActivity;

public class PrimaryTitleActivity extends BaseActivity {
	private ListView listView;
	private int target;
	private int primaryTitle;
	private LinearLayout ll_primary_title;
	private ImageView iv_cancel;
	private int[] picIds;

	@Override
	protected void localOnCreate(Bundle savedInstanceState) {
		setContentView(R.layout.activity_primary_title);
		initView();
	}

	private void initView() {
		picIds = new int[] { R.drawable.a_hp_1_bg_yuan,
				R.drawable.a_hp_2_bg_yuan, R.drawable.a_hp_3_bg_yuan,
				R.drawable.a_hp_4_bg_yuan, R.drawable.a_hp_5_bg_yuan,
				R.drawable.a_hp_6_bg_yuan, R.drawable.a_hp_7_bg_yuan,
				R.drawable.a_hp_8_bg_yuan, R.drawable.a_hp_9_bg_yuan };
		primaryTitle = getIntent().getIntExtra("primaryTitle", 0);
		target = getIntent().getIntExtra("target", 0);
		listView = (ListView) findViewById(R.id.lv_primary_title);
		ll_primary_title = (LinearLayout) findViewById(R.id.ll_primary_title);
		PrimaryTitleListAdapter adapter = new PrimaryTitleListAdapter(this,
				primaryTitle, target);
		listView.setAdapter(adapter);
		setBackGround(primaryTitle);
		iv_cancel = (ImageView) findViewById(R.id.iv_cancel);
		iv_cancel.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
	}

	// 设置背景
//	Bitmap bitmap;

	private void setBackGround(int primaryTitle) {
//		BitmapFactory.Options opts = new BitmapFactory.Options();
//		opts.inSampleSize = 1; // 这个的值压缩的倍数（2的整数倍），数值越小，压缩率越小，图片越清晰
//		// 返回原图解码之后的bitmap对象
//		bitmap = BitmapFactory.decodeResource(getResources(),
//				picIds[primaryTitle], opts);
//		bitmap = BitmapFactory.decodeStream(getResources().openRawResource(picIds[primaryTitle]), null, opts);
//		ll_primary_title.setBackground(new BitmapDrawable(bitmap));
		ll_primary_title.setBackgroundResource(picIds[primaryTitle]);
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		ll_primary_title.setBackground(null);
		ll_primary_title = null;
//		if (bitmap != null && !bitmap.isRecycled()) {
//			bitmap.recycle();
//			bitmap = null;
//		}
		System.gc();
	}

}

package com.futureinst.newbieguide;

import com.futureinst.R;

import android.app.Activity;
import android.app.Dialog;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;

public class EventdetailGuide {
	private  ImageView[] imageViews = new ImageView[14];
	private Button btn_cancel;
	private  Dialog dialog;
	private int[] guids;
	private int index;
	private Activity activity;
	public EventdetailGuide(Activity activity){
		guids = new int[]{R.drawable.detail_guide_1,R.drawable.detail_guide_2,R.drawable.detail_guide_3,R.drawable.detail_guide_4,R.drawable.detail_guide_5,
				R.drawable.detail_guide_6,R.drawable.detail_guide_7,R.drawable.detail_guide_8,R.drawable.detail_guide_9,
				R.drawable.detail_guide_10,R.drawable.detail_guide_11,R.drawable.detail_guide_12,R.drawable.detail_guide_13,R.drawable.detail_guide_14,
				R.drawable.detail_guide_15,R.drawable.detail_guide_16};
		index = 0;
		this.activity = activity;
		showDialog(activity).show();
	}
	OnClickListener clickListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.btn_cancel:
				dialog.dismiss();
				break;
			case R.id.guide1:
//				imageViews[0].setVisibility(View.GONE);
				if(index == 15){
					dialog.dismiss();
					return;
				}
				index++;
				imageViews[0].setImageDrawable(activity.getResources().getDrawable(guids[index]));
				break;
//			case R.id.guide2:
//				imageViews[1].setVisibility(View.GONE);
//				break;
//			case R.id.guide3:
//				imageViews[2].setVisibility(View.GONE);
//				break;
//			case R.id.guide4:
//				imageViews[3].setVisibility(View.GONE);
//				break;
//			case R.id.guide5:
//				imageViews[4].setVisibility(View.GONE);
//				break;
//			case R.id.guide6:
//				imageViews[5].setVisibility(View.GONE);
//				break;
//			case R.id.guide7:
//				imageViews[6].setVisibility(View.GONE);
//				break;
//			case R.id.guide8:
//				imageViews[7].setVisibility(View.GONE);
//				break;
//			case R.id.guide9:
//				imageViews[8].setVisibility(View.GONE);
//				break;
//			case R.id.guide10:
//				imageViews[9].setVisibility(View.GONE);
//				break;
//			case R.id.guide11:
//				imageViews[10].setVisibility(View.GONE);
//				break;
//			case R.id.guide12:
//				imageViews[11].setVisibility(View.GONE);
//				break;
//			case R.id.guide13:
//				imageViews[12].setVisibility(View.GONE);
//				break;
//			case R.id.guide14:
////				imageViews[13].setVisibility(View.GONE);
//				dialog.dismiss();
//				break;
			
			}
		}
	};
	private Dialog showDialog(Activity context){
		dialog = new Dialog(context,R.style.choose_dialog);
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		View view = LayoutInflater.from(context).inflate(R.layout.eventdetail_guide, null);
		btn_cancel = (Button) view.findViewById(R.id.btn_cancel);
		btn_cancel.setOnClickListener(clickListener);
		imageViews[0] = (ImageView) view.findViewById(R.id.guide1);
		imageViews[0].setOnClickListener(clickListener);
//		imageViews[1] = (ImageView) view.findViewById(R.id.guide2);
//		imageViews[1].setOnClickListener(clickListener);
//		imageViews[2] = (ImageView) view.findViewById(R.id.guide3);
//		imageViews[2].setOnClickListener(clickListener);
//		imageViews[3] = (ImageView) view.findViewById(R.id.guide4);
//		imageViews[3].setOnClickListener(clickListener);
//		imageViews[4] = (ImageView) view.findViewById(R.id.guide5);
//		imageViews[4].setOnClickListener(clickListener);
//		imageViews[5] = (ImageView) view.findViewById(R.id.guide6);
//		imageViews[5].setOnClickListener(clickListener);
//		imageViews[6] = (ImageView) view.findViewById(R.id.guide7);
//		imageViews[6].setOnClickListener(clickListener);
//		imageViews[7] = (ImageView) view.findViewById(R.id.guide8);
//		imageViews[7].setOnClickListener(clickListener);
//		imageViews[8] = (ImageView) view.findViewById(R.id.guide9);
//		imageViews[8].setOnClickListener(clickListener);
//		imageViews[9] = (ImageView) view.findViewById(R.id.guide10);
//		imageViews[9].setOnClickListener(clickListener);
//		imageViews[10] = (ImageView) view.findViewById(R.id.guide11);
//		imageViews[10].setOnClickListener(clickListener);
//		imageViews[11] = (ImageView) view.findViewById(R.id.guide12);
//		imageViews[11].setOnClickListener(clickListener);
//		imageViews[12] = (ImageView) view.findViewById(R.id.guide13);
//		imageViews[12].setOnClickListener(clickListener);
//		imageViews[13] = (ImageView) view.findViewById(R.id.guide14);
//		imageViews[13].setOnClickListener(clickListener);
		
		dialog.setContentView(view);
		DisplayMetrics metrics = new DisplayMetrics();
		context.getWindowManager().getDefaultDisplay().getMetrics(metrics);
		int screenWidth = metrics.widthPixels;
		int screenHeight = metrics.heightPixels;
		Window window = dialog.getWindow();
		//设置显示动画  
		window.setWindowAnimations(R.anim.dialog_open);  
		WindowManager.LayoutParams wl = window.getAttributes();  
		wl.width = screenWidth;
		wl.height = screenHeight;
		
		//设置显示位置  
		dialog.onWindowAttributesChanged(wl);
		//设置点击外围解散  
		dialog.setCanceledOnTouchOutside(false); 
		return dialog;
	}

}

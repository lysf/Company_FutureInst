package com.futureinst.newbieguide;

import com.futureinst.R;
import com.futureinst.sharepreference.SharePreferenceUtil;

import android.app.Activity;
import android.app.Dialog;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

public class NewbieGuide2 {
	private int[] eventDetail_guid_1 = new int[]{R.drawable.guide_2,R.drawable.guide_3,R.drawable.guide_4,R.drawable.guide_5};
	private int[] eventDetail_guid_2 = new int[]{R.drawable.guide_6};
	private int[] eventTrade_guid_2 = new int[]{R.drawable.guide_7,R.drawable.guide_8};
	private int[] imagesId;
	private int index = 0;
    private ImageView guide;

    public void setImagesId(int[] imagesId){
		this.imagesId = imagesId;
	}

	public NewbieGuide2(Activity activity,boolean isHavaPrice,int type){
		if(type == 1){
			this.imagesId = eventDetail_guid_1;
		}else if(type == 2){
			this.imagesId = eventDetail_guid_2;
		}else if(type == 3){
			this.imagesId = eventTrade_guid_2;
		}
		index = 0;
		showDialog(activity,isHavaPrice).show();
	}
	private Dialog showDialog(final Activity context,final boolean isHavaPrice){
		final SharePreferenceUtil preferenceUtil = SharePreferenceUtil.getInstance(context);
		final Dialog dialog = new Dialog(context,R.style.choose_dialog);
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		View view = LayoutInflater.from(context).inflate(R.layout.choose_dialog_2, null);
        guide = (ImageView) view.findViewById(R.id.guide);
		guide.setImageDrawable(context.getResources().getDrawable(imagesId[0]));
				guide.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						if(index == imagesId.length-1){
							dialog.dismiss();
                            guide = null;
							return;
						}
						index++;
						guide.setImageDrawable(context.getResources().getDrawable(imagesId[index]));
					}
				});
				


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

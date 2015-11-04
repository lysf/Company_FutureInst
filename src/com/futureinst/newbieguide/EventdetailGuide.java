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
    private ImageView[] imageViews = new ImageView[5];
    private Button btn_cancel;
    private Dialog dialog;
    private int[] guids;
    private int index;
    private Activity activity;

    public EventdetailGuide(Activity activity) {
        guids = new int[]{R.drawable.detail_guide_1, R.drawable.detail_guide_2,
                R.drawable.detail_guide_3, R.drawable.detail_guide_4, R.drawable.detail_guide_5
        };
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
                    if (index == 4) {
                        dialog.dismiss();
                        return;
                    }
                    index++;
                    imageViews[0].setImageDrawable(activity.getResources().getDrawable(guids[index]));
                    break;

            }
        }
    };

    private Dialog showDialog(Activity context) {
        dialog = new Dialog(context, R.style.choose_dialog);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        View view = LayoutInflater.from(context).inflate(R.layout.eventdetail_guide, null);
        btn_cancel = (Button) view.findViewById(R.id.btn_cancel);
        btn_cancel.setOnClickListener(clickListener);
        imageViews[0] = (ImageView) view.findViewById(R.id.guide1);
        imageViews[0].setOnClickListener(clickListener);

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

package com.futureinst.home.find;

import android.app.Activity;
import android.app.Dialog;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.futureinst.R;
import com.futureinst.roundimageutils.RoundedImageView;
import com.futureinst.utils.DialogShow;
import com.futureinst.utils.ImageLoadOptions;
import com.nostra13.universalimageloader.core.ImageLoader;


/**
 * Created by hao on 2015/10/28.
 */
public class AwradDialogUtil {
    private ImageView[] btn_awrads;
    public static  AwradDialogUtil awradDialogUtil;
    private int award = 0;//10、50、100
    public static  AwradDialogUtil getInstance(){
        if(awradDialogUtil == null)
            awradDialogUtil = new AwradDialogUtil();
        return awradDialogUtil;
    }
    private AwradDialogUtil(){
        btn_awrads= new ImageView[3];
    }
    public void showDialog(final Activity activity, String imageUrl,final AwardClickListener awardClickListener){
        View view = LayoutInflater.from(activity).inflate(R.layout.view_award_dialog,null,false);
        Button btn_award = (Button) view.findViewById(R.id.btn_award);
        Button btn_cancle = (Button) view.findViewById(R.id.btn_cancel);
        RoundedImageView headImage = (RoundedImageView) view.findViewById(R.id.headImage);
        ImageLoader.getInstance().displayImage(imageUrl,headImage, ImageLoadOptions.getOptions(R.drawable.logo));
        btn_awrads[0] = (ImageView) view.findViewById(R.id.btn_award_1);
        btn_awrads[1] = (ImageView) view.findViewById(R.id.btn_award_2);
        btn_awrads[2] = (ImageView) view.findViewById(R.id.btn_award_3);
        final Dialog dialog = DialogShow.showDialog(activity,view, Gravity.BOTTOM);
        dialog.show();
        btn_cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        btn_award.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getAward() == 0) {
                    Toast.makeText(activity, "请选择要打赏的未币", Toast.LENGTH_SHORT).show();
                    return;
                }
                awardClickListener.onClickListner(getAward());
                dialog.dismiss();
                setAward(0);
                btn_awrads[0].setSelected(false);
                btn_awrads[1].setSelected(false);
                btn_awrads[2].setSelected(false);
            }
        });
        btn_awrads[0].setOnClickListener(clickListener);
        btn_awrads[1].setOnClickListener(clickListener);
        btn_awrads[2].setOnClickListener(clickListener);

    }
    View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            btn_awrads[0].setSelected(false);
            btn_awrads[1].setSelected(false);
            btn_awrads[2].setSelected(false);
            switch (v.getId()){
                case R.id.btn_award_1://10
                    btn_awrads[0].setSelected(true);
                    setAward(10);
                    break;
                case R.id.btn_award_2://50
                    btn_awrads[1].setSelected(true);
                    setAward(50);
                    break;
                case R.id.btn_award_3://100
                    btn_awrads[2].setSelected(true);
                    setAward(100);
                    break;
            }
        }
    };
    public void setAward(int award){
        this.award = award;
    }
    public int getAward(){
        return this.award;
    }
    interface AwardClickListener{
        void onClickListner(int icons);
    }
}

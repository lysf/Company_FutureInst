package com.futureinst.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.futureinst.R;


/**
 * TODO: document your custom view class.
 */
public class CustomView_Image_Text extends LinearLayout {


    public CustomView_Image_Text(Context context) {
        super(context);
        init(null);
    }

    public CustomView_Image_Text(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public CustomView_Image_Text(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs);
    }
    private ImageView imageView;
    private TextView textView;

    @Override
    public void setSelected(boolean selected) {
        super.setSelected(selected);
        imageView.setSelected(selected);
        textView.setSelected(selected);
        if(selected){
            textView.setTextColor(getResources().getColor(R.color.text_color_white));
        }else{
            textView.setTextColor(getResources().getColor(R.color.text_color_4));
        }
    }

    @Override
    public void setPressed(boolean pressed) {
        super.setPressed(pressed);
//        imageView.setPressed(pressed);
//        if(pressed){
//            textView.setTextColor(getResources().getColor(R.color.text_color_white));
//        }else{
//            textView.setTextColor(getResources().getColor(R.color.text_color_4));
//        }
    }

    private void init(AttributeSet attrs) {
        // Load attributes
        final TypedArray a = getContext().obtainStyledAttributes(
                attrs, R.styleable.CustomViewImgText);
        int resourceId = a.getResourceId(R.styleable.CustomViewImgText_imageSrc, 0);
        String text = a.getString(R.styleable.CustomViewImgText_text);
        a.recycle();
        imageView = new ImageView(getContext());
        textView = new TextView(getContext());
        textView.setTextColor(getResources().getColor(R.color.text_color_4));
        LayoutParams layoutParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.leftMargin = 20;
        textView.setLayoutParams(layoutParams);
        textView.setGravity(Gravity.CENTER);
        textView.setTextSize(17);
        imageView.setImageResource(resourceId);
        textView.setText(text);
        setOrientation(HORIZONTAL);
        setGravity(Gravity.CENTER);
        this.addView(imageView);
        this.addView(textView);

    }
    public void setText(String text){
        textView.setText(text);
    }


}

/**
 *ClassName : TitleLayout</br>
 * 
 * <p>2013© e-future.com.cn 版权所有 翻版必究</p>
 * <p>未经允许不得使用</p>
 *
 */
package com.futureinst.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;

/**
 * ClassName : TitleLayout
 * <br>功能描述：
 * <br>History
 * <br>Create User: Chen limiao
 * <br>Create Date: 2013-12-26 下午1:52:01
 * <br>Update User:
 * <br>Update Date:
 */
public class TitleLayout extends LinearLayout {

    /**
     * @param context
     */
    public TitleLayout(Context context) {
        super(context);
    }

    /**
     * @param context
     * @param attrs
     */
    public TitleLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
/**
 * @see android.widget.LinearLayout#onMeasure(int, int)
 */
@Override
protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
    super.onMeasure(widthMeasureSpec, heightMeasureSpec);
//    int count = getChildCount();
//    int totalLength = 0;
//    for (int i = 0; i < count; i++) {
//        View child = getChildAt(i);
//        if (child.getVisibility() != View.GONE) {
//            MarginLayoutParams params = (MarginLayoutParams) child.getLayoutParams();
//            measureChildWithMargins(child, widthMeasureSpec, totalLength, heightMeasureSpec, 0);
//            totalLength += child.getMeasuredWidth() + params.leftMargin + params.rightMargin;
//        }
//    }
}
    /**
     * @see android.widget.LinearLayout#onLayout(boolean, int, int, int, int)
     */
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        int childCount = getChildCount();
        View child = null;
        int i = 0;
        for (; i < childCount; i++) {
            child = getChildAt(i);
            if (child.getVisibility() != VISIBLE) {
                child = null;
                continue;
            }
            final LayoutParams lp =
                    (LayoutParams) child.getLayoutParams();
            int gravity = lp.gravity;
            if (gravity < 0) {
                child = null;
                continue;
            }
            if( (gravity & Gravity.HORIZONTAL_GRAVITY_MASK) == Gravity.CENTER_HORIZONTAL) {
                break;
            }else {
                child = null;
            }
        }
        if (child != null) {//有需要居中的
            int left= 0;
            int right = getWidth();
            int j = i-1;
            //找出左边可见的view
            while (j>=0) {
                View leftView = getChildAt(j);
                j--;
                if (leftView.getVisibility() == VISIBLE) {
                    left = leftView.getRight();
                    break;
                }
            }
            j = i+1;
            while (j<childCount) {
                View leftView = getChildAt(j);
                j++;
                if (leftView.getVisibility() == VISIBLE) {
                    right = leftView.getLeft();
                    break;
                }
            }
            final LayoutParams lp =
                    (LayoutParams) child.getLayoutParams();
            left += lp.leftMargin;
            right -= lp.rightMargin;
            int endge = Math.max(left, getWidth()-right);
            int childwidth = getWidth() - 2*endge;
            child.measure(MeasureSpec.makeMeasureSpec(childwidth, MeasureSpec.EXACTLY), MeasureSpec.makeMeasureSpec(child.getHeight(), MeasureSpec.EXACTLY));
            child.layout(endge, child.getTop(), getWidth()-endge, child.getBottom());
        }
    }
}

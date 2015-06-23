/**
 *ClassName : TitleButton</br>
 * 
 * <p>2013© e-future.com.cn 版权所有 翻版必究</p>
 * <p>未经允许不得使用</p>
 *
 */
package com.futureinst.widget;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.Button;

/**
 * ClassName : TitleButton <br>
 * 功能描述： <br>
 * History <br>
 * Create User: An Zewei <br>
 * Create Date: 2014年7月29日 下午2:44:14 <br>
 * Update User: <br>
 * Update Date:
 */
public class TitleButton extends Button {

    /**
     * @param context
     */
    public TitleButton(Context context) {
        super(context);
    }

    /**
     * @param context
     * @param attrs
     */
    public TitleButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * @param context
     * @param attrs
     * @param defStyle
     */
    public TitleButton(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void setImageResource(int res) {
        setImageDrawable(getContext().getResources().getDrawable(res));
    }

    public void setImageDrawable(Drawable drawable) {
        setCompoundDrawablesWithIntrinsicBounds(null, null, drawable, null);
    }
}

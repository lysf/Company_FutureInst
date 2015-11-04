package com.futureinst.widget.scrollview;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ScrollView;

/**
 * Created by hao on 2015/10/21.
 */
public class MyScrollView extends ScrollView {
    private ScrollViewListener scrollViewListener = null;
    public MyScrollView(Context context) {
        super(context);
    }

    public MyScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    public void setScrollViewListener(ScrollViewListener scrollViewListener) {
        this.scrollViewListener = scrollViewListener;
    }

    @Override
    protected void onScrollChanged(int x, int y, int oldx, int oldy) {
        super.onScrollChanged(x, y, oldx, oldy);
        if (scrollViewListener != null) {
//            if(getScrollY() + getHeight() >=  computeVerticalScrollRange()){
            setScrollBottom(getScrollY() + getHeight() >=  computeVerticalScrollRange());
//            }
            scrollViewListener.onScrollChanged( x, y, oldx, oldy);
        }
    }
    public interface ScrollViewListener {
        void onScrollChanged( int x, int y, int oldx, int oldy);

    }
    private boolean scrollBottom;

    public boolean isScrollBottom() {
        return scrollBottom;
    }

    public void setScrollBottom(boolean scrollBottom) {
        this.scrollBottom = scrollBottom;
    }
}

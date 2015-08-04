package com.futureinst.baseui;

import java.util.List;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.KeyEvent;
import android.view.Window;
import android.widget.Toast;

import com.futureinst.R;



public abstract class BaseFragmentActivity extends FragmentActivity {
    protected String TAG = getClass().getSimpleName();
    protected FragmentManager mFragmentManager;

    private long mClickTime = 0l;

    /**
     * @return the mFragmentManager
     */
    public FragmentManager getmFragmentManager() {
        return mFragmentManager;
    }

    /**
     * 初始化fragment
     */
    private void initFragmentManager() {
        mFragmentManager = getSupportFragmentManager();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        mBlFlag = LoginUtils.getThemeFlag(BaseFragmentActivity.this);
//        if (!mBlFlag) {
//			setTheme(R.style.DingDingThemeDefault);
//		} else {
//			setTheme(R.style.DingDingThemeNight);
//		}
        

        requestWindowFeature(Window.FEATURE_NO_TITLE);
//        ActivityManagerUtil.addActivity(this);
//        mTintManager = new SystemBarTintManager(this);
//        mTintManager.setStatusBarAlpha(0.25f);
//        mTintManager.setStatusBarTintEnabled(true);
//        CustomerApp.getInstance().setTop(this);
        super.setContentView(R.layout.fragment_template);
        initFragmentManager();
    }

    public void setTitleProgressVisibility(boolean show) {
        try {
            List<Fragment> fragments = mFragmentManager.getFragments();
            if (fragments != null) {
                ((BaseFragment) fragments.get(fragments.size() - 1)).setTitleProgressVisibility(show);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    /**
     * @see android.app.Activity#onPause()
     */
    @Override
    protected void onPause() {
        super.onPause();
    }

    /**
     * @see android.app.Activity#onKeyUp(int, android.view.KeyEvent)
     */
    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if (isRoot() && event.getKeyCode() == KeyEvent.KEYCODE_BACK && mFragmentManager.getBackStackEntryCount() <= 0) {
            long second = System.currentTimeMillis();
            if (second - mClickTime < BaseApplication.EXIT_TIMEOUT) {
                finish();
                return true;
            } else {
                mClickTime = second;
                Toast.makeText(this, "再按一次返回键退出", Toast.LENGTH_SHORT).show();
                return true;
            }
        }
        return super.onKeyUp(keyCode, event);
    }

    protected boolean isRoot() {
        return isTaskRoot() || (getParent() != null && getParent().isTaskRoot());
    }
    
    public boolean shouldShowBack() {
        return !isRoot() || mFragmentManager.getBackStackEntryCount() >0;
    }
    
    
    @Override
    protected void onDestroy() {
    	// TODO Auto-generated method stub
    	super.onDestroy();
//    	 ActivityManagerUtil.finishOtherZctivity(this);
    }
}

/**
 *ClassName : FragmentTransfor</br>
 * 
 * <p>2013© e-future.com.cn 版权所有 翻版必究</p>
 * <p>未经允许不得使用</p>
 *
 */
package com.futureinst.baseui;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.futureinst.R;




/**
 * ClassName : FragmentTransfor
 * <br>功能描述：
 * <br>History
 * <br>Update User:
 * <br>Update Date:
 */
public class FragmentTransform {

    public static void replaceFragments(FragmentManager fragmentManager, Fragment fragment,String tag, boolean addToBackStack) {
        if (fragment.isAdded()) {
            return;
        }
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        if (fragmentManager.getFragments() != null){
            transaction.setCustomAnimations(R.anim.activity_open_enter, 0);
        }
        transaction.add(R.id.fragment_conainer, fragment, tag);
        transaction.commitAllowingStateLoss();
    }
    public static void showFragments(FragmentManager fragmentManager, Fragment fragment,String tag, boolean addToBackStack) {
        if (fragment.isAdded()) {
            return;
        }
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        if (fragmentManager.getFragments() != null){
        	if(addToBackStack) {
        		transaction.addToBackStack(tag);
        	}
            transaction.setCustomAnimations(R.anim.activity_open_enter, 0);
        }
        transaction.add(R.id.fragment_conainer, fragment, tag);
        transaction.commitAllowingStateLoss();
    }
}

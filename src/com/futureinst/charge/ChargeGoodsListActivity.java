package com.futureinst.charge;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;

import com.futureinst.R;
import com.futureinst.baseui.BaseActivity;

public class ChargeGoodsListActivity extends BaseActivity {
    private ChargeGoodsListFragment chargeListFragment;
    private final String FRAGMENTTAG = "chargeListFragment";
    @Override
    protected void localOnCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_charge_list);
        getLeftImageView().setImageDrawable(getResources().getDrawable(R.drawable.back));
        setTitle("充值中心");
        initView();
    }

    private void initView() {
        chargeListFragment = new ChargeGoodsListFragment();

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.container,chargeListFragment,FRAGMENTTAG).commitAllowingStateLoss();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Fragment f = getSupportFragmentManager().findFragmentByTag(FRAGMENTTAG);
        f.onActivityResult(requestCode, resultCode, data);
    }
}

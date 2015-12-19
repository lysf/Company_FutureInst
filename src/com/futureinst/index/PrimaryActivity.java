package com.futureinst.index;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import com.futureinst.R;
import com.futureinst.baseui.BaseActivity;
import com.futureinst.home.HomeActivity;
import com.futureinst.utils.Utils;

public class PrimaryActivity extends BaseActivity{
    private ImageView image;
    @Override
    protected void localOnCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_primary);
        image = (ImageView)findViewById(R.id.image);
        image.setImageBitmap(Utils.readBitMap(this, R.raw.index_1));
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(PrimaryActivity.this, HomeActivity.class);
                startActivity(intent);
                finish();
            }
        },2000);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        image = null;
        System.gc();
    }
}

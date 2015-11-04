package com.futureinst.index;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.futureinst.R;
import com.futureinst.baseui.BaseFragment;
import com.futureinst.home.HomeActivity;

/**
 * Created by hao on 2015/11/7.
 */
public class IndexFragment extends BaseFragment {
    private ImageView imageView;
    private static final String INDEX = "INDEX";
    private int index = 0;
    private int[] images = new int[]{R.drawable.index_1,R.drawable.index_2,R.drawable.index_3,
    R.drawable.index_4,R.drawable.index_5};
    public static IndexFragment getInstance(int index){
        IndexFragment indexFragment = new IndexFragment();
        Bundle bundle =  new Bundle();
        bundle.putInt(INDEX,index);
        indexFragment.setArguments(bundle);
        return indexFragment;
    }
    @Override
    protected void localOnCreate(Bundle savedInstanceState) {
        setContentView(R.layout.index_1);
        initView();
    }

    private void initView() {
        index = getArguments().getInt(INDEX);
        imageView = (ImageView)findViewById(R.id.imageView);
        imageView.setImageDrawable(getResources().getDrawable(images[index%5]));
        Button btn_enter = (Button) findViewById(R.id.btn_enter);

        btn_enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(index == images.length-1){
                    startActivity(new Intent(getContext(), HomeActivity.class));
                    getActivity().finish();
                }
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        imageView = null;
        System.gc();
    }
}

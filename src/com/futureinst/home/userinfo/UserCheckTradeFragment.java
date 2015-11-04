package com.futureinst.home.userinfo;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import com.futureinst.R;
import com.futureinst.baseui.BaseFragment;
import com.futureinst.home.eventdetail.EventDetailActivity;
import com.futureinst.model.usermodel.UserCheckDAO;
import com.futureinst.model.usermodel.UserCheckInfo;
import com.futureinst.net.HttpPostParams;
import com.futureinst.net.HttpResponseUtils;
import com.futureinst.net.PostCommentResponseListener;
import com.futureinst.net.PostMethod;
import com.futureinst.net.PostType;
import com.futureinst.sharepreference.SharePreferenceUtil;
import com.futureinst.utils.MyProgressDialog;
import com.futureinst.widget.list.PullListView;

import org.json.JSONException;

/**
 * Created by hao on 2015/11/2.
 */
public class UserCheckTradeFragment extends BaseFragment implements PullListView.OnRefreshListener{
    private PullListView pullListView;
    private UserCheckAdapter adapter;
    private MyProgressDialog progressDialog;
    private SharePreferenceUtil preferenceUtil;
    private HttpPostParams httpPostParams;
    private HttpResponseUtils httpResponseUtils;
    private int page = 1;
    private String last_id = "0";
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case 0:
                    Toast.makeText(getContext(), getResources().getString(R.string.data_over), Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };
    @Override
    protected void localOnCreate(Bundle savedInstanceState) {
        setContentView(R.layout.fragment_user_check);
        initView();
        progressDialog.progressDialog();
        getData(page, last_id);
    }
    private void initView() {
        progressDialog = MyProgressDialog.getInstance(getContext());
        preferenceUtil = SharePreferenceUtil.getInstance(getContext());
        httpPostParams = HttpPostParams.getInstace();
        httpResponseUtils = HttpResponseUtils.getInstace(getActivity());

        pullListView = (PullListView) findViewById(R.id.pull_listView);
        pullListView.setRefresh(true);
        adapter = new UserCheckAdapter(getContext());
        pullListView.setAdapter(adapter);
        pullListView.setonRefreshListener(this);
        pullListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(position < 1) return;
                UserCheckDAO item = (UserCheckDAO) adapter.getItem(position-1);
                Intent intent = new Intent(getContext(), EventDetailActivity.class);
                intent.putExtra("eventId", item.getEventId()+"");
                startActivity(intent);
            }
        });
    }

    // 获取对账单
    private void getData(final int page,String last_id) {
        httpResponseUtils.postJson(httpPostParams.getPostParams(
                        PostMethod.query_user_check.name(), PostType.user_info.name(),
                        httpPostParams.query_user_check(preferenceUtil.getID()+"",preferenceUtil.getUUid(),page,last_id,"trade")), UserCheckInfo.class,
                new PostCommentResponseListener() {
                    @Override
                    public void requestCompleted(Object response)
                            throws JSONException {
                        progressDialog.cancleProgress();
                        pullListView.onRefreshComplete();
                        if (response == null)
                            return;
                        UserCheckInfo userCheckInfo = (UserCheckInfo) response;

                        if(page == 1){
                            adapter.refresh(userCheckInfo.getChecks());
                        }else{
                            adapter.setList(userCheckInfo.getChecks());
                        }
                        if(adapter.getCount() > 9){
                            pullListView.setLoadMore(true);
                        }else{
                            pullListView.setLoadMore(false);
                        }
                        if(page!=1 && (userCheckInfo.getChecks() == null || userCheckInfo.getChecks().size() ==0)){
                            handler.sendEmptyMessage(0);
                            pullListView.setLoadMore(false);
                        }
                    }
                });
    }

    @Override
    public void onRefresh(boolean isTop) {
        // TODO Auto-generated method stub
        if(isTop){
            page = 1;
            last_id = "0";
        }else{
            page ++;
            if(adapter.getList()!=null && adapter.getList().size()>0){
                last_id = adapter.getList().get(adapter.getCount()-1).getId()+"";
            }

        }
        getData(page,last_id);
    }
}

package com.futureinst.home.userinfo.checkorder;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import com.futureinst.R;
import com.futureinst.baseui.BaseFragment;
import com.futureinst.home.eventdetail.EventDetailActivity;
import com.futureinst.home.find.ArticleDetailActivity;
import com.futureinst.home.userinfo.checkorder.UserCheckAdapter;
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
    private String channel = "0";
    private String last_id = "0";
    private BroadcastReceiver receiver;
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
        channel = ((UserCheckActivity)getActivity()).getTradeScreen();
        initView();
        progressDialog.progressDialog();
        getData(page, last_id,channel);
        receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if(intent.getAction().equals("checkScreen")){
                    if(intent.getIntExtra("flag",0) == 0){
                        channel = intent.getStringExtra("screen");
                        progressDialog.progressDialog();
                        getData(1,"0",channel);
                    }

                }
            }
        };
        IntentFilter filter = new IntentFilter("checkScreen");
        getActivity().registerReceiver(receiver, filter);
    }

    @Override
    public void onStart() {
        super.onStart();

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
                if (position < 1) return;
                UserCheckDAO item = (UserCheckDAO) adapter.getItem(position - 1);
                if (item.getEventId() != 0) {//事件
                    Intent intent = new Intent(getContext(), EventDetailActivity.class);
                    intent.putExtra("eventId", item.getEventId() + "");
                    startActivity(intent);
                }
                if (item.getType() == 1) {//文章
                    Intent intentPoint = new Intent(getContext(), ArticleDetailActivity.class);
//                    intentPoint.putExtra("article_id",item.get)
//                    startActivity(intentPoint);
                }
            }
        });
    }

    // 获取对账单
    private void getData(final int page,String last_id,String channel) {
        httpResponseUtils.postJson(httpPostParams.getPostParams(
                        PostMethod.query_user_check.name(), PostType.user_info.name(),
                        httpPostParams.query_user_check(preferenceUtil.getID() + "", preferenceUtil.getUUid(), page, last_id, "trade", channel)), UserCheckInfo.class,
                new PostCommentResponseListener() {
                    @Override
                    public void requestCompleted(Object response)
                            throws JSONException {
                        progressDialog.cancleProgress();
                        pullListView.onRefreshComplete();
                        if (response == null)
                            return;
                        UserCheckInfo userCheckInfo = (UserCheckInfo) response;

                        if (page == 1) {
                            adapter.refresh(userCheckInfo.getChecks());
                        } else {
                            adapter.setList(userCheckInfo.getChecks());
                        }
                        if (adapter.getCount() > 9) {
                            pullListView.setLoadMore(true);
                        } else {
                            pullListView.setLoadMore(false);
                        }
                        if (page != 1 && (userCheckInfo.getChecks() == null || userCheckInfo.getChecks().size() == 0)) {
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
        getData(page, last_id, channel);
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        if(receiver != null){
            getActivity().unregisterReceiver(receiver);
        }
    }
}

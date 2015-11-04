package com.futureinst.home.find;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.widget.ImageView;

import com.futureinst.R;
import com.futureinst.baseui.BaseFragment;
import com.futureinst.model.news.NewsInfoDAO;
import com.futureinst.model.usermodel.RankInfo;
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
 * 发现-动态
 */

public class FondIndicentFragment extends BaseFragment implements PullListView.OnRefreshListener{
    private SharePreferenceUtil preferenceUtil;
    private HttpResponseUtils httpResponseUtils;
    private HttpPostParams httpPostParams;
    private MyProgressDialog progressDialog;
    private PullListView pullListView;
    private IncidentAdapter adapter;
    private ImageView iv_empty;
    @Override
    protected void localOnCreate(Bundle savedInstanceState) {
        setContentView(R.layout.fragment_fond_indicent);
        initView();
        query_user_news();
    }

    private void initView() {

        preferenceUtil =SharePreferenceUtil .getInstance(getContext());
        httpResponseUtils = HttpResponseUtils.getInstace(getActivity());
        httpPostParams = HttpPostParams.getInstace();
        progressDialog = MyProgressDialog.getInstance(getContext());
        pullListView = (PullListView) findViewById(R.id.pull_listView);
        iv_empty = (ImageView)findViewById(R.id.iv_empty);
        pullListView.setEmptyView(iv_empty);
        adapter = new IncidentAdapter(getContext());
        pullListView.setAdapter(adapter);
        pullListView.setonRefreshListener(this);
    }

    //获取排名
    private void query_user_news(){
        progressDialog.cancleProgress();
        httpResponseUtils.postJson(httpPostParams.getPostParams(
                        PostMethod.query_user_news.name(), PostType.news.name(),
                        httpPostParams.query_user_news(preferenceUtil.getID() + "", preferenceUtil.getUUid())),
                NewsInfoDAO.class,
                new PostCommentResponseListener() {
                    @Override
                    public void requestCompleted(Object response) throws JSONException {
                        pullListView.onRefreshComplete();
                        if (response == null) return;
                        NewsInfoDAO newsInfoDAO = (NewsInfoDAO) response;
                        adapter.setList(newsInfoDAO.getNewss());
                    }
                });
    }
    @Override
    public void onRefresh(boolean isTop) {
        if(isTop){
            query_user_news();
        }
    }
}

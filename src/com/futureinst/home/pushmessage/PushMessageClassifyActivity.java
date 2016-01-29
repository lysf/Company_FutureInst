package com.futureinst.home.pushmessage;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.futureinst.R;
import com.futureinst.baseui.BaseActivity;
import com.futureinst.comment.CommentActivity;
import com.futureinst.db.PushMessageCacheUtil;
import com.futureinst.global.Content;
import com.futureinst.home.eventdetail.EventDetailActivity;
import com.futureinst.home.find.NewsUtil;
import com.futureinst.home.forecast.ForecastGroupActivity;
import com.futureinst.model.homeeventmodel.QueryEventDAO;
import com.futureinst.model.homeeventmodel.SingleEventInfoDAO;
import com.futureinst.model.push.PushMessageDAO;
import com.futureinst.net.PostCommentResponseListener;
import com.futureinst.net.PostMethod;
import com.futureinst.net.PostType;
import com.futureinst.net.SingleEventScope;
import com.futureinst.personalinfo.other.PersonalShowActivity;
import com.futureinst.push.PushWebActivity;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hao on 2016/1/22.
 */
public class PushMessageClassifyActivity extends BaseActivity {
    private int type;
    private ListView lv_push_message_classify;
    private PushMessageClassifyAdapter adapter;
    private List<PushMessageDAO> list;
    private PushMessageCacheUtil messageCacheUtil;

    @Override
    protected void localOnCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_push_message_classify);
        initView();
        getLeftImageView().setImageResource(R.drawable.back);
    }

    private void initView() {
        type = getIntent().getIntExtra("type",0);
        lv_push_message_classify = (ListView) findViewById(R.id.lv_push_message_classify);
        list = new ArrayList<>();
        adapter = new PushMessageClassifyAdapter(this);
        lv_push_message_classify.setAdapter(adapter);
        messageCacheUtil = PushMessageCacheUtil.getInstance(this);
        switch (type){
            case 0:
                setTitle("账户通知");
                list = messageCacheUtil.getPushMessage(Category.account.name());
                messageCacheUtil.updateMesaage(Category.account.name());
                break;
            case 1:
                setTitle("粉丝通知");
                list = messageCacheUtil.getPushMessage(Category.fans.name());
                messageCacheUtil.updateMesaage(Category.fans.name());
                break;
            case 2:
                setTitle("赞与评论通知");
                list = messageCacheUtil.getPushMessage(Category.interact.name());
                messageCacheUtil.updateMesaage(Category.interact.name());
                break;
        }
        Log.i(TAG, "-----------pushMessageInfo-->>" + list);
        adapter.setList(list);
        lv_push_message_classify.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                PushMessageDAO item = (PushMessageDAO) adapter.getItem(position);
                list.get(position).setRead(true);
                itemClick(item);
            }
        });
        View view_empty = findViewById(R.id.view_empty);
        lv_push_message_classify.setEmptyView(view_empty);
        
    }

    //点击事件
    public void itemClick(PushMessageDAO item){
        if(item.getTarget_url() != null && !item.getTarget_url().equals("")){
            NewsUtil newsUtil = new NewsUtil();
            if(!newsUtil.clickListener(this,item.getTarget_url())){
                Intent intent = new Intent(PushMessageClassifyActivity.this, PushWebActivity.class);
                intent.putExtra("url", item.getTarget_url());
                intent.putExtra("title", "");
                startActivity(intent);
            }
        }
        else if(item.getType() == null){//专题或广告
            if(item.getEvent_id()!=null){
                query_single_event(item.getEvent_id());
            }
        }
        else{
            if(item.getType().equals("event")
                    || item.getType().equals("deal") ){//事件详情
                Intent intent = new Intent(PushMessageClassifyActivity.this, EventDetailActivity.class);
                intent.putExtra("eventId", item.getEvent_id() + "");
                startActivity(intent);
            }else if(item.getType().equals("comment")){//评论
                Intent intent = new Intent(PushMessageClassifyActivity.this, CommentActivity.class);
                intent.putExtra("eventId", item.getEvent_id() + "");
                startActivity(intent);
            }
            else if(item.getType().equals("rank")){//排名
                Intent intent = new Intent("rank");
                sendBroadcast(intent);
                finish();
            }
            else if(item.getType().equals("url")){//打开指定网页
                NewsUtil newsUtil = new NewsUtil();
                if(!newsUtil.clickListener(this,item.getHref())){
                    Intent intent = new Intent(PushMessageClassifyActivity.this, PushWebActivity.class);
                    intent.putExtra("url", item.getHref());
                    intent.putExtra("title", "");
                    startActivity(intent);
                }
            }
            else if(item.getType().equals("follow_me")){//关注通知
                Intent intent = new Intent(PushMessageClassifyActivity.this,PersonalShowActivity.class);
                intent.putExtra("id", item.getPeer_id());
                startActivity(intent);
            }else {
                if(item.getEvent_id()!=null){//事件详情页
                    Intent intent = new Intent(PushMessageClassifyActivity.this, EventDetailActivity.class);
                    intent.putExtra("eventId", item.getEvent_id() + "");
                    startActivity(intent);
                }
            }
        }
        adapter.notifyDataSetChanged();
    }

    private void query_single_event(String event_id){
        progressDialog.progressDialog();
        Content.isPull = true;
        httpResponseUtils.postJson(httpPostParams.getPostParams(
                        PostMethod.query_single_event.name(), PostType.event.name(),
                        httpPostParams.query_single_event(event_id, SingleEventScope.base.name())),
                SingleEventInfoDAO.class,
                new PostCommentResponseListener() {
                    @Override
                    public void requestCompleted(Object response) throws JSONException {
                        progressDialog.cancleProgress();
                        Content.isPull = false;
                        if (response == null) return;
                        SingleEventInfoDAO queryEventInfoDAO = (SingleEventInfoDAO) response;
                        QueryEventDAO event = queryEventInfoDAO.getEvent();
                        operate(event);
                    }
                });

    }
    private void operate(QueryEventDAO item){
        if(item.getType() == 1){//专题
            Intent intent = new Intent(this, ForecastGroupActivity.class);
            intent.putExtra("group_id", item.getId()+"");
            intent.putExtra("title", item.getTitle());
            startActivity(intent);
        }else if(item.getType() == 2){//广告
            NewsUtil newsUtil = new NewsUtil();
            if(!newsUtil.clickListener(this,item.getLead())){
                Intent intent = new Intent(this, PushWebActivity.class);
                intent.putExtra("url", item.getLead());
                intent.putExtra("title", item.getTitle());
                startActivity(intent);
            }
        }else{
            //预测
            Intent intent = new Intent(this, EventDetailActivity.class);
            intent.putExtra("eventId", item.getId()+"");
            startActivity(intent);
        }
    }
}

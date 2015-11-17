package com.futureinst.home.article;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.TextView;

import com.futureinst.R;
import com.futureinst.baseui.BaseActivity;
import com.futureinst.home.find.ArticleDetailActivity;
import com.futureinst.model.comment.ArticleDAO;
import com.futureinst.model.comment.ArticleInfoDAO;
import com.futureinst.model.record.UserRecordDAO;
import com.futureinst.model.usermodel.UserDAO;
import com.futureinst.net.HttpPostParams;
import com.futureinst.net.HttpResponseUtils;
import com.futureinst.net.PostCommentResponseListener;
import com.futureinst.net.PostMethod;
import com.futureinst.net.PostType;
import com.futureinst.widget.list.PullListView;

import org.json.JSONException;

public class AritlceActivity extends BaseActivity implements PullListView.OnRefreshListener {
    private PullListView pull_listView;
    private ArticleAdapter adapter;
    private UserDAO user;
    private UserRecordDAO userRecord;
    private boolean isUser = true;
    private View article_top;
    private String title = "发表的观点文章";

    private TextView tv_total_award,tv_num_article,tv_num_read,tv_num_praise;
    @Override
    protected void localOnCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_aritlce);
        getLeftImageView().setImageDrawable(getResources().getDrawable(R.drawable.back));
        initView();
        if(isUser){
            initTopDate(userRecord);
        }
        onRefresh(true);
    }

    private void initView() {
        isUser = getIntent().getBooleanExtra("from", true);
        if(isUser){
            userRecord = (UserRecordDAO) getIntent().getSerializableExtra("user");
        }else{
            user = (UserDAO) getIntent().getSerializableExtra("user");
        }
        article_top = LayoutInflater.from(this).inflate(R.layout.article_top, null, false);
        tv_total_award = (TextView)article_top.findViewById(R.id.tv_total_award);
        tv_num_article = (TextView)article_top.findViewById(R.id.tv_num_article);
        tv_num_read = (TextView)article_top.findViewById(R.id.tv_num_read);
        tv_num_praise = (TextView)article_top.findViewById(R.id.tv_num_praise);

        pull_listView = (PullListView) findViewById(R.id.pull_listView);
        adapter = new ArticleAdapter(this);
        if(!isUser){
            setTitle(user.getName().toString().trim() + title);
        }else{
            setTitle("我"+title);
            pull_listView.addHeaderView(article_top);
        }
        pull_listView.setAdapter(adapter);
        pull_listView.setonRefreshListener(this);
        pull_listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ArticleDAO item = null;
                if(isUser){
                    if (position < 2) return;
                    item = (ArticleDAO) adapter.getItem(position - 2);
                }else{
                    if (position < 1) return;
                   item = (ArticleDAO) adapter.getItem(position - 1);
                }

                Intent intent = new Intent(AritlceActivity.this, ArticleDetailActivity.class);
                intent.putExtra("article_id", item.getId()+"");
                startActivity(intent);
            }
        });
    }
    //初始化我的文章头部
    private void initTopDate(UserRecordDAO userRecord){
        if(userRecord == null ) return;
        tv_total_award.setText(userRecord.getArticleAward()+"");
        tv_num_article.setText(userRecord.getArticleNum()+"");
        tv_num_read.setText(userRecord.getArticleReadNum()+"");
        tv_num_praise.setText(userRecord.getArticleLoveNum()+"");
    }

    @Override
    public void onRefresh(boolean isTop) {
        if (isTop) {
            if(isUser){
                query_user_article();
            }else{
                peer_info_query_user_article(user.getId()+"");
            }
        }
    }
    //查询用户自己观点
    private void query_user_article(){
        progressDialog.progressDialog();
        httpResponseUtils.postJson(httpPostParams.getPostParams(PostMethod.query_user_article.name(), PostType.article.name(),
                        httpPostParams.query_user_article(preferenceUtil.getID() + "", preferenceUtil.getUUid()) ),
                ArticleInfoDAO.class,
                new PostCommentResponseListener() {
                    @Override
                    public void requestCompleted(Object response) throws JSONException {
                        progressDialog.cancleProgress();
                        pull_listView.onRefreshComplete();
                        if(response == null) return;
                        ArticleInfoDAO articleInfoDAO = (ArticleInfoDAO) response;
                        adapter.setList(articleInfoDAO.getArticles());
                    }
                }
        );
    }
    //查询他人的观点
    private void peer_info_query_user_article(String peer_id){
        progressDialog.progressDialog();
        httpResponseUtils.postJson(httpPostParams.getPostParams(PostMethod.peer_info_query_user_article.name(), PostType.peer_info.name(),
                        httpPostParams.peer_info_query_user_article(preferenceUtil.getID() + "", preferenceUtil.getUUid(),peer_id) ),
                ArticleInfoDAO.class,
                new PostCommentResponseListener() {
                    @Override
                    public void requestCompleted(Object response) throws JSONException {
                        progressDialog.cancleProgress();
                        pull_listView.onRefreshComplete();
                        if(response == null) return;
                        ArticleInfoDAO articleInfoDAO = (ArticleInfoDAO) response;
                        adapter.setList(articleInfoDAO.getArticles());
                    }
                }
        );
    }

}

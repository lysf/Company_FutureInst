package com.futureinst.comment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.futureinst.R;
import com.futureinst.baseui.BaseActivity;
import com.futureinst.login.LoginActivity;
import com.futureinst.model.basemodel.BaseModel;
import com.futureinst.model.comment.CommentDAO;
import com.futureinst.model.comment.CommentDetailInfoDAO;
import com.futureinst.model.comment.CommentInfoDAO;
import com.futureinst.net.ArticleOperate;
import com.futureinst.net.CommentOperate;
import com.futureinst.net.PostCommentResponseListener;
import com.futureinst.net.PostMethod;
import com.futureinst.net.PostType;
import com.futureinst.personalinfo.other.PersonalShowActivity;
import com.futureinst.roundimageutils.RoundedImageView;
import com.futureinst.utils.ImageLoadOptions;
import com.futureinst.utils.TimeUtil;
import com.futureinst.widget.list.PullListView;
import com.nostra13.universalimageloader.core.ImageLoader;

import org.json.JSONException;

import java.util.List;

/**
 * Created by hao on 2015/10/29.
 */
public class CommentDetailSecondActivity extends BaseActivity implements PullListView.OnRefreshListener{
    private PullListView pull_listView;
    private CommentDetailSecondAdapter adapter;
    private boolean fromComment = true;//是否来自事件的评论（观点）
    private CommentDAO commentDAO;
    private CommentDAO comment;
    private LinearLayout ll_container;
    private List<String> loves;
    private boolean parentPraise = false;//父评论是否点过赞
    private CommentDeleteDialogUtil commentDeleteDialogUtil;
    RoundedImageView imageView;
    TextView tv_name ;
    TextView tv_attitude ;
    LinearLayout ll_agree;
    TextView tv_comment_num ;
    ImageView iv_agree ;
    TextView tv_time ;
    TextView tv_comment ;
    private View view_float_comment_edit;
    private EditText et_comment_apply;
    private Button btn_send;
    private String comment_id,event_id,article_id,parent_id;
    @Override
    protected void localOnCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_comment_detail_second);
        setTitle("评论");
        getLeftImageView().setImageDrawable(getResources().getDrawable(R.drawable.back));
        initView();
        initListViewTop();
        addListener();
        onRefresh(true);

    }

    private void initView() {
        commentDAO = (CommentDAO) getIntent().getSerializableExtra("value");
        comment_id = getIntent().getStringExtra("comment_id");
        event_id = getIntent().getStringExtra("event_id");
        parent_id = getIntent().getStringExtra("parent_id");
        article_id = getIntent().getStringExtra("article_id");
        if(commentDAO !=null){
            comment_id = commentDAO.getId()+"";
            event_id = commentDAO.getEventId()+"";
            article_id = commentDAO.getArticleId()+"";
            parent_id = commentDAO.getParentId()+"";
        }
        parentPraise = getIntent().getBooleanExtra("praise",false);
        fromComment = getIntent().getBooleanExtra("from", true);
        pull_listView = (PullListView)findViewById(R.id.pull_listView);
        View view_top = LayoutInflater.from(this).inflate(R.layout.item_comment_top,null,false);
        pull_listView.addHeaderView(view_top);
        adapter = new CommentDetailSecondAdapter(this);
        pull_listView.setAdapter(adapter);
        pull_listView.setonRefreshListener(this);
    }
    private void initListViewTop(){
        imageView = (RoundedImageView) findViewById(R.id.headImage);
        tv_name = (TextView)findViewById(R.id.tv_name);
        tv_attitude = (TextView)findViewById(R.id.tv_attitude);
        ll_agree = (LinearLayout)findViewById(R.id.ll_agree);
        tv_comment_num = (TextView)findViewById(R.id.tv_comment_num);
        tv_time = (TextView)findViewById(R.id.tv_time);
        tv_comment = (TextView)findViewById(R.id.tv_comment);
        iv_agree = (ImageView)findViewById(R.id.iv_agree);

        view_float_comment_edit = findViewById(R.id.view_float_comment_edit);
        et_comment_apply = (EditText) findViewById(R.id.et_comment_apply);
        btn_send = (Button) findViewById(R.id.btn_send);
        ll_container = (LinearLayout)findViewById(R.id.ll_container);
        ll_agree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (judgeIsLogin()) {
                    String operate = CommentOperate.unlike.name();
                        if(parentPraise){
                            operate = CommentOperate.unlike.name();
                            parentPraise = false;
//                            tv_comment_num.setText(commentDAO.getLikeNum()-1+"");
                        }else{
                            operate = CommentOperate.like.name();
                            parentPraise = true;
//                            tv_comment_num.setText(commentDAO.getLikeNum()+1+"");
                        }
                        operate_comment(comment_id, operate);
                    iv_agree.setSelected(parentPraise);
                    sendBroadcast(new Intent("praise"));
                }
            }
        });


    }
    private void addListener(){
        commentDeleteDialogUtil = CommentDeleteDialogUtil.newInstance();
        //点赞
        adapter.setOperateListener(new CommentDetailSecondAdapter.PraiseOperateListener() {
            @Override
            public void onClickListener(String com_id, String operate) {
                if (judgeIsLogin()) {
                        operate_comment(com_id, operate);
                }
            }
        });
        //回复
        adapter.setApplyCommentListener(new CommentDetailSecondAdapter.ApplyCommentListener() {
            @Override
            public void onClickListener(CommentDAO coment) {
                if (judgeIsLogin()) {
                    comment = coment;
                    view_float_comment_edit.setVisibility(View.VISIBLE);
                    et_comment_apply.setFocusable(true);
                    et_comment_apply.setFocusableInTouchMode(true);
                    et_comment_apply.requestFocus();
                    showSoftInputView(et_comment_apply);
                    et_comment_apply.setHint("回复 " + coment.getUser().getName());
                }
            }
        });
        //删除评论
        adapter.setDeleteCommentListener(new CommentDetailSecondAdapter.DeleteCommentListener() {
            @Override
            public void onClickListener(CommentDAO comment) {
                final CommentDAO dao = comment;
                commentDeleteDialogUtil.show(CommentDetailSecondActivity.this, new CommentDeleteDialogUtil.DelCommentListener() {
                    @Override
                    public void onClickListener() {
                        //删除评论
                        if (fromComment) {
                            operate_comment(dao.getId() + "", CommentOperate.delete.name());

                        } else {
                            operate_comment(dao.getId() + "", CommentOperate.delete.name());

                        }
                    }
                });
            }
        });
        ll_container.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (view_float_comment_edit.getVisibility() == View.VISIBLE) {
                    view_float_comment_edit.setVisibility(View.GONE);
                    et_comment_apply.setText("");
                    hideSoftInputView();
                    return true;
                }
                return false;
            }
        });


//        et_comment_apply.setOnKeyListener(new View.OnKeyListener() {
//            @Override
//            public boolean onKey(View v, int keyCode, KeyEvent event) {
//                if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN) {
//                    if (TextUtils.isEmpty(et_comment_apply.getText().toString().trim())) {
//                        showToast("输入内容不能为空");
//                    } else {
//                        Log.i("tag", "======parentId=" + comment.getParentId() + "==id=" + comment.getId());
//                        if (fromComment) {
//                            addComment(comment.getParentId() + "", comment.getId() + "", et_comment_apply.getText().toString().trim());
//                        } else {
//                            add_comment_for_article(commentDAO.getParentId() + "", commentDAO.getId() + "", et_comment_apply.getText().toString().trim());
//                        }
//
//                    }
//                    return true;
//                }
//                return false;
//            }
//        });
        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(et_comment_apply.getText().toString().trim())) {
                    showToast("输入内容不能为空");
                } else {
                    Log.i("tag", "======parentId=" + comment.getParentId() + "==id=" + comment.getId());
                    if (fromComment) {
                        addComment(comment.getParentId() + "", comment.getId() + "", et_comment_apply.getText().toString().trim());
                    } else {
                        add_comment_for_article(comment.getParentId() + "", comment.getId() + "", et_comment_apply.getText().toString().trim());
                    }

                }
            }
        });
    }
    private void initTop( final CommentDAO item){
        if(fromComment){
            if(item.getAttitude() == 1){
                tv_attitude.setText("看好");
                tv_attitude.setTextColor(getResources().getColor(R.color.gain_red));
                tv_attitude.setBackground(getResources().getDrawable(R.drawable.shap_back_good));
            }else{
                tv_attitude.setText("不看好");
                tv_attitude.setTextColor(getResources().getColor(R.color.gain_blue));
                tv_attitude.setBackground(getResources().getDrawable(R.drawable.shap_back_bad));
            }
        }else{
            tv_attitude.setVisibility(View.GONE);
        }
        parentPraise = !(loves == null || !loves.contains(item.getId() + ""));
        iv_agree.setSelected(parentPraise);

        tv_comment.setText(item.getContent());
        tv_comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (judgeIsLogin()) {
                    comment = item;
                    if (!(preferenceUtil.getID()+"").equals(item.getUser().getId()+"")) {//回复
                        comment.setParentId(item.getId());
                        comment.setId(0l);
                        view_float_comment_edit.setVisibility(View.VISIBLE);
                        et_comment_apply.setFocusable(true);
                        et_comment_apply.setFocusableInTouchMode(true);
                        et_comment_apply.requestFocus();
                        showSoftInputView(et_comment_apply);
                        et_comment_apply.setHint("回复 " + comment.getUser().getName());
                    } else {
                        commentDeleteDialogUtil.show(CommentDetailSecondActivity.this, new CommentDeleteDialogUtil.DelCommentListener() {
                            @Override
                            public void onClickListener() {
                                //删除评论
                                if (fromComment) {
                                    operate_comment(item.getId() + "", CommentOperate.delete.name());

                                } else {
                                    operate_comment(item.getId() + "", CommentOperate.delete.name());

                                }
                            }
                        });
                    }
                }
            }
        });

        tv_name.setText(item.getUser().getName());
        if(imageView.getTag() == null || !imageView.getTag().equals(item.getUser().getHeadImage())){
            ImageLoader.getInstance().displayImage(item.getUser().getHeadImage(), imageView, ImageLoadOptions.getOptions(R.drawable.logo));
            imageView.setTag(item.getUser().getHeadImage());
        }
        tv_time.setText(TimeUtil.getDescriptionTimeFromTimestamp(item.getCtime()));
        tv_comment_num.setText(item.getLikeNum()+"");
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ((preferenceUtil.getID()+"").equals(item.getUser().getId()+"")) {
                    return;
                }
                Intent intent = new Intent(CommentDetailSecondActivity.this, PersonalShowActivity.class);
                intent.putExtra("id", item.getUser().getId() + "");
                startActivity(intent);
            }
        });
        tv_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ((preferenceUtil.getID()+"").equals(item.getUser().getId()+"")) {
                    return;
                }
                Intent intent = new Intent(CommentDetailSecondActivity.this, PersonalShowActivity.class);
                intent.putExtra("id", item.getUser().getId() + "");
                startActivity(intent);
            }
        });
    }
    //获取事件评论
    private void getComment(String event_id,String parent_id) {
        httpResponseUtils.postJson(httpPostParams.getPostParams(PostMethod.query_event_for_comment.name(), PostType.comment.name(),
                        httpPostParams.query_event_for_comment(preferenceUtil.getID()+"",preferenceUtil.getUUid(),event_id, 0, parent_id)),
                CommentDetailInfoDAO.class,
                new PostCommentResponseListener() {
                    @Override
                    public void requestCompleted(Object response) throws JSONException {
                        pull_listView.onRefreshComplete();
                        progressDialog.cancleProgress();
                        if (response == null) return;
                        CommentDetailInfoDAO commentInfoDAO = (CommentDetailInfoDAO) response;
                        initTop(commentInfoDAO.getParentComment());
                        loves = commentInfoDAO.getLoves();
                        adapter.setList(commentInfoDAO.getChildComments(),commentInfoDAO.getLoves(),commentInfoDAO.getReplyToCommentMap());
                    }
                });
    }
    @Override
    public void onRefresh(boolean isTop) {
        if (isTop) {
            if(fromComment){
                getComment(event_id,comment_id);
            }else{
                query_comment_for_article(article_id,comment_id);
            }
        }
    }

    //点赞或取消点赞（删除）
    private void operate_comment(String com_id, String operate) {
        progressDialog.progressDialog();
        httpResponseUtils.postJson(httpPostParams.getPostParams(PostMethod.operate_comment.name(), PostType.comment.name(),
                        httpPostParams.operate_comment(preferenceUtil.getID() + "", preferenceUtil.getUUid(), com_id, operate)),
                BaseModel.class,
                new PostCommentResponseListener() {
                    @Override
                    public void requestCompleted(Object response) throws JSONException {
                        if (response == null) {
                            progressDialog.cancleProgress();
                            return;
                        }
                        handler.sendEmptyMessage(110);
                    }
                });
    }
    //添加评论
    private void addComment(String parent_id,String apply_id,String content){
        progressDialog.progressDialog();
        httpResponseUtils.postJson(httpPostParams.getPostParams(
                        PostMethod.add_comment.name(), PostType.comment.name(),
                        httpPostParams.add_comment(preferenceUtil.getID() + "", preferenceUtil.getUUid(),
                                event_id, parent_id, apply_id, content)),
                BaseModel.class,
                new PostCommentResponseListener() {
                    @Override
                    public void requestCompleted(Object response) throws JSONException {
                        progressDialog.cancleProgress();
                        if (response == null) return;
                        hideSoftInputView();
                        view_float_comment_edit.setVisibility(View.GONE);
                        et_comment_apply.setText("");
                        handler.sendEmptyMessage(110);
                    }
                });
    }
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 110:
                    notifyUpdate();
                    onRefresh(true);
                    break;
            }
        }
    };


    //观点部分
    //查询观点评论
    private void query_comment_for_article(String articleId,String parent_id) {
        progressDialog.progressDialog();
        httpResponseUtils.postJson(httpPostParams.getPostParams(PostMethod.query_comment_for_article.name(), PostType.article.name(),
                        httpPostParams.query_comment_for_article(preferenceUtil.getID()+"",preferenceUtil.getUUid(),articleId,parent_id)),
                CommentDetailInfoDAO.class, new PostCommentResponseListener() {
                    @Override
                    public void requestCompleted(Object response) throws JSONException {
                        progressDialog.cancleProgress();
                        pull_listView.onRefreshComplete();
                        if (response == null) return;
                        CommentDetailInfoDAO commentInfoDAO = (CommentDetailInfoDAO) response;
                        adapter.setList(commentInfoDAO.getChildComments(),commentInfoDAO.getLoves(),commentInfoDAO.getReplyToCommentMap());
                        loves = commentInfoDAO.getLoves();
                        initTop(commentInfoDAO.getParentComment());
                    }
                });
    }
    //添加评论
    private void add_comment_for_article(String parent_id,String reply_to,String content) {
        progressDialog.progressDialog();
        httpResponseUtils.postJson(httpPostParams.getPostParams(PostMethod.add_comment_for_article.name(), PostType.article.name(),
                        httpPostParams.add_comment_for_article(preferenceUtil.getID() + "", preferenceUtil.getUUid(), article_id, content, parent_id, reply_to)),
                BaseModel.class, new PostCommentResponseListener() {
                    @Override
                    public void requestCompleted(Object response) throws JSONException {
                        progressDialog.cancleProgress();
                        if (response == null) return;
                        hideSoftInputView();
                        view_float_comment_edit.setVisibility(View.GONE);
                        et_comment_apply.setText("");
                        handler.sendEmptyMessage(110);

                    }
                });
    }

    //判断是否已登录
    private boolean judgeIsLogin() {
        if (TextUtils.isEmpty(preferenceUtil.getUUid())) {
            Intent intent = new Intent(this, LoginActivity.class);
            intent.putExtra("login", true);
            startActivity(intent);
            return false;
        }
        return true;
    }
    private void notifyUpdate(){
        Intent intent = new Intent("commentUpdate");
        sendBroadcast(intent);
    }
}

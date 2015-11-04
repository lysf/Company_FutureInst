package com.futureinst.home.find;

import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.app.Activity;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.futureinst.R;
import com.futureinst.comment.CommentDeleteDialogUtil;
import com.futureinst.login.LoginActivity;
import com.futureinst.model.basemodel.BaseModel;
import com.futureinst.model.comment.ArticleDAO;
import com.futureinst.model.comment.CommentDAO;
import com.futureinst.model.comment.CommentInfoDAO;
import com.futureinst.model.homeeventmodel.QueryEventDAO;
import com.futureinst.model.point.ArticleDetailDAO;
import com.futureinst.model.point.PointDAO;
import com.futureinst.net.ArticleOperate;
import com.futureinst.net.HttpPath;
import com.futureinst.net.HttpPostParams;
import com.futureinst.net.HttpResponseUtils;
import com.futureinst.net.PostCommentResponseListener;
import com.futureinst.net.PostMethod;
import com.futureinst.net.PostType;
import com.futureinst.roundimageutils.RoundedImageView;
import com.futureinst.share.OneKeyShareUtil;
import com.futureinst.sharepreference.SharePreferenceUtil;
import com.futureinst.utils.DialogShow;
import com.futureinst.utils.ImageLoadOptions;
import com.futureinst.utils.MyProgressDialog;
import com.futureinst.utils.TimeUtil;
import com.futureinst.utils.Utils;
import com.futureinst.widget.list.MyListView;
import com.futureinst.widget.scrollview.MyScrollView;
import com.nostra13.universalimageloader.core.ImageLoader;

import org.json.JSONException;

import cn.sharesdk.sina.weibo.SinaWeibo;
import cn.sharesdk.wechat.friends.Wechat;
import cn.sharesdk.wechat.moments.WechatMoments;

public class ArticleDetailActivity extends Activity implements MyScrollView.ScrollViewListener{
    private CommentDeleteDialogUtil commentDeleteDialogUtil;
    private MyScrollView scroll;
    private ArticleDAO pointDAO;
    private AwradDialogUtil awradDialogUtil;
    private ImageView iv_back;
    private Button btn_award;
    private SharePreferenceUtil preferenceUtil;
    private HttpResponseUtils httpResponseUtils;
    private HttpPostParams httpPostParams;
    private MyProgressDialog progressDialog;
    private String articleId;
    private ImageView[] top_imageViews;
    private TextView tv_praise_num;
    //top
    private TextView tv_reward;
    private TextView tv_type, tv_title, tv_name, tv_time, tv_read;
    private RoundedImageView headImage_select;
    private TextView tv_artice_content;
    //评论
    private TextView tv_article_comment_num;
    private MyListView lv_article_comment;
    private ArticleCommentAdapter adapter;
    private LinearLayout ll_container;
    private CommentDAO comment;

    private View view_float_comment_edit;
    private EditText et_comment_apply;
    private Button btn_send;

    private BroadcastReceiver receiver;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    query_comment_for_article(articleId);
                    break;
                case 1:
                    getArticleRead(articleId);
                    break;

            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_detail);
        initView();

        progressDialog.progressDialog();
        getArticleRead(articleId);
        query_comment_for_article(articleId);
    }

    private void initView() {
        commentDeleteDialogUtil = CommentDeleteDialogUtil.newInstance();
        pointDAO = (ArticleDAO) getIntent().getSerializableExtra("point");
        articleId = pointDAO.getId() + "";
        preferenceUtil = SharePreferenceUtil.getInstance(this);
        httpPostParams = HttpPostParams.getInstace();
        httpResponseUtils = HttpResponseUtils.getInstace(this);
        progressDialog = MyProgressDialog.getInstance(this);
        awradDialogUtil = AwradDialogUtil.getInstance();

        scroll = (MyScrollView)findViewById(R.id.scroll);
        scroll.setScrollViewListener(this);
        iv_back = (ImageView) findViewById(R.id.iv_back);
        iv_back.setOnClickListener(clickListener);
        top_imageViews = new ImageView[3];
        top_imageViews[0] = (ImageView) findViewById(R.id.iv_comment);//评论
        top_imageViews[1] = (ImageView) findViewById(R.id.iv_praise);//点赞
        top_imageViews[2] = (ImageView) findViewById(R.id.iv_share);//分享
        tv_praise_num = (TextView)findViewById(R.id.tv_praise_num);
        top_imageViews[0].setOnClickListener(clickListener);
        top_imageViews[1].setOnClickListener(clickListener);
        top_imageViews[2].setOnClickListener(clickListener);




        lv_article_comment = (MyListView) findViewById(R.id.lv_article_comment);
        adapter = new ArticleCommentAdapter(this);
        lv_article_comment.setAdapter(adapter);
        initComment();
        initTop();
        receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if(intent.getAction().equals("praise")){
                    handler.sendEmptyMessage(0);
                }
            }
        };
        IntentFilter filter = new IntentFilter("praise");
        registerReceiver(receiver,filter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(receiver != null){
            unregisterReceiver(receiver);
        }
    }

    void initTop() {
        tv_artice_content = (TextView)findViewById(R.id.tv_artice_content);
        tv_type = (TextView) findViewById(R.id.tv_type);
        tv_time = (TextView) findViewById(R.id.tv_time);
        tv_title = (TextView) findViewById(R.id.tv_title);
        tv_name = (TextView) findViewById(R.id.tv_name);
        tv_read = (TextView) findViewById(R.id.tv_read);
        headImage_select = (RoundedImageView) findViewById(R.id.headImage_select);

        btn_award = (Button) findViewById(R.id.btn_award);
        tv_reward = (TextView)findViewById(R.id.tv_reward);
        btn_award.setOnClickListener(clickListener);

    }
    private void initTopDate(ArticleDetailDAO articleDetail){
        tv_praise_num.setText(articleDetail.getArticle().getLoveNum()+"");//文章点赞数量
        if(articleDetail.getLove() > 0){//已经点过赞
            top_imageViews[1].setSelected(true);
        }else{
            top_imageViews[1].setSelected(false);
        }
        if(articleDetail.getAward() > 0){
            tv_reward.setText("你已打赏"+articleDetail.getAward()+"未币");
            btn_award.setSelected(true);
            btn_award.setClickable(false);
        }
        tv_type.setText(articleDetail.getArticle().getEvent().getTagstr());
        tv_time.setText(TimeUtil.getDescriptionTimeFromTimestamp(articleDetail.getArticle().getMtime()));
        tv_title.setText(articleDetail.getArticle().getTitle());
        tv_name.setText(articleDetail.getArticle().getUser().getName());
        tv_artice_content.setText(articleDetail.getDetail());
        tv_read.setText(articleDetail.getArticle().getReadNum() + "人已阅读");
        if (headImage_select.getTag() == null || !headImage_select.getTag().equals(articleDetail.getArticle().getEvent().getImgsrc())) {
            ImageLoader.getInstance().displayImage(articleDetail.getArticle().getUser().getHeadImage(), headImage_select, ImageLoadOptions.getOptions(R.drawable.logo));
            headImage_select.setTag(articleDetail.getArticle().getEvent().getImgsrc());
        }
    }

    void initComment() {
        ll_container = (LinearLayout) findViewById(R.id.ll_container);
        lv_article_comment = (MyListView) findViewById(R.id.lv_article_comment);
        tv_article_comment_num = (TextView) findViewById(R.id.tv_article_comment_num);
        view_float_comment_edit = findViewById(R.id.view_float_comment_edit);
        et_comment_apply = (EditText) findViewById(R.id.et_comment_apply);
        btn_send = (Button)findViewById(R.id.btn_send);
        ll_container.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (view_float_comment_edit.getVisibility() == View.VISIBLE) {
                    hideSoftInputView();
                    view_float_comment_edit.setVisibility(View.GONE);
                    return true;
                }
                return false;
            }
        });
        lv_article_comment.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (view_float_comment_edit.getVisibility() == View.VISIBLE) {
                    view_float_comment_edit.setVisibility(View.GONE);
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
//                        Toast.makeText(ArticleDetailActivity.this, "输入内容不能为空", Toast.LENGTH_SHORT).show();
//                    } else {
//                        String parent_id = "0";
//                        if (comment != null) {//回复评论
//                            parent_id = comment.getId() + "";
//                        }
//                        add_comment_for_article(articleId, et_comment_apply.getText().toString().trim(), parent_id);
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
                    Toast.makeText(ArticleDetailActivity.this, "输入内容不能为空", Toast.LENGTH_SHORT).show();
                } else {
                    String parent_id = "0";
                    if (comment != null) {//回复评论
                        parent_id = comment.getId() + "";
                    }
                    add_comment_for_article(articleId, et_comment_apply.getText().toString().trim(), parent_id);
                }
            }
        });
        //点赞
        adapter.setOperateListener(new ArticleCommentAdapter.PraiseOperateListener() {
            @Override
            public void onClickListener(String com_id, String operate) {
                if (judgeIsLogin()) {
                    operate_comment(com_id, operate);
                }
            }
        });
        //回复
        adapter.setApplyCommentListener(new ArticleCommentAdapter.ApplyCommentListener() {
            @Override
            public void onClickListener(CommentDAO coment) {
                if (judgeIsLogin()) {
                    comment = coment;
                    view_float_comment_edit.setVisibility(View.VISIBLE);
                    et_comment_apply.setFocusable(true);
                    et_comment_apply.setFocusableInTouchMode(true);
                    et_comment_apply.requestFocus();
                    InputMethodManager inputManager =(InputMethodManager) et_comment_apply.getContext().getSystemService(INPUT_METHOD_SERVICE);
                    inputManager.showSoftInput(et_comment_apply, 0);
                    et_comment_apply.setHint("回复 " + coment.getUser().getName());
                }
            }
        });
        //删除评论
        adapter.setDeleteCommentListener(new ArticleCommentAdapter.DeleteCommentListener() {
            @Override
            public void onClickListener(final CommentDAO comment) {
                commentDeleteDialogUtil.show(ArticleDetailActivity.this, new CommentDeleteDialogUtil.DelCommentListener() {
                    @Override
                    public void onClickListener() {
                        //删除评论
                        if (judgeIsLogin()) {
                            operate_comment(comment.getId()+"", ArticleOperate.delete);
                        }
                    }
                });
            }
        });
    }

    View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.iv_back:
                    finish();
                    break;
                case R.id.iv_comment://评论
                    comment = null;
                    view_float_comment_edit.setVisibility(View.VISIBLE);
                    et_comment_apply.setFocusable(true);
                    et_comment_apply.setFocusableInTouchMode(true);
                    et_comment_apply.requestFocus();
                    InputMethodManager inputManager =(InputMethodManager) et_comment_apply.getContext().getSystemService(INPUT_METHOD_SERVICE);
                    inputManager.showSoftInput(et_comment_apply, 0);
                    break;
                case R.id.iv_praise://点赞
                    if(top_imageViews[1].isSelected()){
                        articleOperate(articleId, ArticleOperate.unlove, 0);
                    }else{
                        articleOperate(articleId, ArticleOperate.love, 0);
                    }
                    break;
                case R.id.iv_share://分享
                    share("分享观点", null, " 分享的观点内容", null, null);
                    break;
                case R.id.btn_award://打赏
                    if (judgeIsLogin()) {
                        awradDialogUtil.showDialog(ArticleDetailActivity.this,pointDAO.getUser().getHeadImage(), new AwradDialogUtil.AwardClickListener() {
                            @Override
                            public void onClickListner(int icons) {
                                articleOperate(articleId, ArticleOperate.award, icons);
                            }
                        });
                    }
                    break;
            }
        }
    };

    //判断是否已登录
    private boolean judgeIsLogin() {
        if (TextUtils.isEmpty(SharePreferenceUtil.getInstance(this).getUUid())) {
            Intent intent = new Intent(ArticleDetailActivity.this, LoginActivity.class);
            intent.putExtra("login", true);
            startActivity(intent);
            return false;
        }
        return true;
    }

    //观点操作(read(阅读),award(打赏,award:打赏金额),love(点赞),unlove(取消点赞))
    private void articleOperate(String id, final String operate, int award) {
        httpResponseUtils.postJson(httpPostParams.getPostParams(PostMethod.operate_article.name(), PostType.article.name(),
                        httpPostParams.operate_article(preferenceUtil.getID() + "", preferenceUtil.getUUid(), id, operate, award)),
                BaseModel.class, new PostCommentResponseListener() {
                    @Override
                    public void requestCompleted(Object response) throws JSONException {
                        progressDialog.cancleProgress();
                        if (response == null) return;
                        if(operate.equals(ArticleOperate.love) || operate.equals(ArticleOperate.unlove)
                                || operate.equals(ArticleOperate.award))
                        {
                            handler.sendEmptyMessage(1);
                        }
                    }
                });
    }
    //文章详情
    private void getArticleRead(String id) {
        httpResponseUtils.postJson(httpPostParams.getPostParams(PostMethod.operate_article.name(), PostType.article.name(),
                        httpPostParams.operate_article(preferenceUtil.getID() + "", preferenceUtil.getUUid(), id, ArticleOperate.read, 0)),
                ArticleDetailDAO.class, new PostCommentResponseListener() {
                    @Override
                    public void requestCompleted(Object response) throws JSONException {
                        progressDialog.cancleProgress();
                        if (response == null) return;
                        ArticleDetailDAO articleDetailDAO = (ArticleDetailDAO) response;
                        initTopDate(articleDetailDAO);

                    }
                });
    }

    //添加评论
    private void add_comment_for_article(final String article_id, String content, String parent_id) {
        progressDialog.progressDialog();
        httpResponseUtils.postJson(httpPostParams.getPostParams(PostMethod.add_comment_for_article.name(), PostType.article.name(),
                        httpPostParams.add_comment_for_article(preferenceUtil.getID() + "", preferenceUtil.getUUid(), article_id, content, parent_id, "0")),
                BaseModel.class, new PostCommentResponseListener() {
                    @Override
                    public void requestCompleted(Object response) throws JSONException {
                        progressDialog.cancleProgress();
                        if (response == null) return;
                        hideSoftInputView();
//                        view_float_comment_edit.setVisibility(View.GONE);
                        et_comment_apply.setText("");
                        et_comment_apply.setHint("写下你的评论吧！");
                        handler.sendEmptyMessage(0);

                    }
                });
    }

    //查询评论
    private void query_comment_for_article(String articleId) {
        progressDialog.progressDialog();
        httpResponseUtils.postJson(httpPostParams.getPostParams(PostMethod.query_comment_for_article.name(), PostType.article.name(),
                        httpPostParams.query_comment_for_article(preferenceUtil.getID()+"",preferenceUtil.getUUid(),articleId)),
                CommentInfoDAO.class, new PostCommentResponseListener() {
                    @Override
                    public void requestCompleted(Object response) throws JSONException {
                        progressDialog.cancleProgress();
                        if (response == null) return;
                        CommentInfoDAO commentInfoDAO = (CommentInfoDAO) response;
                        tv_article_comment_num.setText("("+commentInfoDAO.getComments().size()+")");
                        adapter.setList(commentInfoDAO.getComments(), commentInfoDAO.getLastChildCommentMap(),commentInfoDAO.getLoves());
                    }
                });
    }

    //点赞或取消点赞
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
                        handler.sendEmptyMessage(0);
                    }
                });
    }

    //分享界面
    private void share(String shareTitle,
                       String shareTitleUrl, String shareContents,
                       String shareLocalImageUrl, String shareImageUrl) {
        OneKeyShareUtil.showShare(this, null, 0, shareTitle, shareTitleUrl, shareContents, shareLocalImageUrl, shareImageUrl, true, null);
    }

    public void hideSoftInputView() {
        InputMethodManager manager = ((InputMethodManager) this.getSystemService(Activity.INPUT_METHOD_SERVICE));
        if (getWindow().getAttributes().softInputMode != WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN) {
            if (getCurrentFocus() != null)
                manager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    @Override
    public void onScrollChanged(int x, int y, int oldx, int oldy) {
        int[] location = new int[2];
        tv_article_comment_num.getLocationOnScreen(location);
        Log.i("", "================location=" + location[1]);
        if (location[1] < Utils.getScreenHeight(ArticleDetailActivity.this)) {
            view_float_comment_edit.setVisibility(View.VISIBLE);
            et_comment_apply.setHint("写下你的评论吧！");
        } else {
            view_float_comment_edit.setVisibility(View.GONE);
        }
    }
}

package com.futureinst.comment;


import com.futureinst.R;
import com.futureinst.baseui.BaseActivity;
import com.futureinst.login.LoginActivity;
import com.futureinst.model.basemodel.BaseModel;
import com.futureinst.model.comment.CommentDAO;
import com.futureinst.model.comment.CommentInfoDAO;
import com.futureinst.net.CommentOperate;
import com.futureinst.net.PostCommentResponseListener;
import com.futureinst.net.PostMethod;
import com.futureinst.net.PostType;
import com.futureinst.widget.list.PullListView;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;

import org.json.JSONException;

public class CommentActivity extends BaseActivity implements PullListView.OnRefreshListener {
    private CommentDAO comment;//要回复的评论
    private String event_id;
    private PopupWindow popupWindow;
    private PullListView lv_comment;
    private CommentDetailAdapter adapter;
    private int attitude = 0;// 1 表示支持， 2 表示反对，0 表示全部
    private View view_float_comment_edit;
    private EditText et_comment_apply;
    private Button btn_send;
    private CommentDeleteDialogUtil commentDeleteDialogUtil;
    private int total_comments,good_comments;
    private BroadcastReceiver receiver;
    private Button btn_comment_float;
    @Override
    protected void localOnCreate(Bundle savedInstanceState) {
        setTitle("评论");
        getLeftImageView().setImageDrawable(getResources().getDrawable(R.drawable.back));
        getRightImageView().setImageDrawable(getResources().getDrawable(R.drawable.comment_type));
        setContentView(R.layout.fragment_detail_comment);
        initView();
        getComment(event_id, attitude);
    }

    @Override
    protected void onLeftImageViewClick(View view) {
        super.onLeftImageViewClick(view);
        finish();
    }

    @Override
    protected void onRightImageViewClick(View view) {
        super.onRightImageViewClick(view);//选择评论类别
        if (popupWindow != null && popupWindow.isShowing()) {
            popupWindow.dismiss();
            popupWindow = null;
        } else {
            getPopupWindow();
            popupWindow.showAsDropDown(view);
        }
    }

    private void initView() {
        event_id = getIntent().getStringExtra("eventId");
        view_float_comment_edit = findViewById(R.id.view_float_comment_edit);
        et_comment_apply = (EditText) findViewById(R.id.et_comment_apply);
        btn_send = (Button) findViewById(R.id.btn_send);
        commentDeleteDialogUtil = CommentDeleteDialogUtil.newInstance();

        lv_comment = (PullListView) findViewById(R.id.listView_comment);
        lv_comment.setRefresh(true);
        lv_comment.setLoadMore(false);
        adapter = new CommentDetailAdapter(this);
        lv_comment.setAdapter(adapter);
        View emptyView = findViewById(R.id.view_empty);
        lv_comment.setEmptyView(emptyView);
        lv_comment.setonRefreshListener(this);

        btn_comment_float = (Button) findViewById(R.id.btn_comment_float);
        findViewById(R.id.btn_comment_total_float).setVisibility(View.INVISIBLE);
        btn_comment_float.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (judgeIsLogin()) {
                    Intent intent = new Intent(CommentActivity.this, AddCommentActivity.class);
                    intent.putExtra("eventId", event_id);
                    startActivity(intent);
                }
            }
        });



        //点赞
        adapter.setOperateListener(new CommentDetailAdapter.PraiseOperateListener() {
            @Override
            public void onClickListener(String com_id, String operate) {
                if (judgeIsLogin()) {
                    operate_comment(com_id, operate);
                }
            }
        });
        //回复
        adapter.setApplyCommentListener(new CommentDetailAdapter.ApplyCommentListener() {
            @Override
            public void onClickListener(CommentDAO coment) {
                if (judgeIsLogin()) {
                    comment = coment;
                    view_float_comment_edit.setVisibility(View.VISIBLE);
                    et_comment_apply.setFocusable(true);
                    et_comment_apply.setFocusableInTouchMode(true);
                    et_comment_apply.requestFocus();
                    showSoftInputView(et_comment_apply);
//                    InputMethodManager inputManager =(InputMethodManager) et_comment_apply.getContext().getSystemService(INPUT_METHOD_SERVICE);
//                    inputManager.showSoftInput(et_comment_apply, 0);
                    et_comment_apply.setHint("回复 " + coment.getUser().getName());
                }
            }
        });
        //删除评论
        adapter.setDeleteCommentListener(new CommentDetailAdapter.DeleteCommentListener() {
            @Override
            public void onClickListener(CommentDAO comment) {
                final CommentDAO dao = comment;
                commentDeleteDialogUtil.show(CommentActivity.this, new CommentDeleteDialogUtil.DelCommentListener() {
                    @Override
                    public void onClickListener() {
                        //删除评论
                        operate_comment(dao.getId() + "", CommentOperate.delete.name());
                    }
                });
            }
        });
        lv_comment.setOnTouchListener(new View.OnTouchListener() {
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
//                Log.i("tag", "============keycode=" + keyCode);
//                if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN) {
//                    if (TextUtils.isEmpty(et_comment_apply.getText().toString().trim())) {
//                        showToast("输入内容不能为空");
//                    } else {
//                        addComment(comment.getId() + "", "0", et_comment_apply.getText().toString().trim());
//                    }
//                    return true;
//                }
//                return false;
//            }
//        });
        btn_send.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(et_comment_apply.getText().toString().trim())) {
                    showToast("输入内容不能为空");
                } else {
                    addComment(comment.getId() + "", "0", et_comment_apply.getText().toString().trim());
                }
            }
        });

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

    OnClickListener clickListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            btns[attitude].setSelected(false);
            switch (v.getId()) {
                case R.id.btn_all://全部评论
                    attitude = 0;
                    break;
                case R.id.btn_good://看好评论
                    attitude = 1;
                    break;
                case R.id.btn_bad://不看好评论
                    attitude = 2;
                    break;
            }
            btns[attitude].setSelected(true);
            popupWindow.dismiss();
            progressDialog.progressDialog();
            getComment(event_id, attitude);

        }
    };

    /**
     * 创建PopupWindow
     */
    Button[] btns;
    protected void initPopuptWindow() {
        // TODO Auto-generated method stub
        // 获取自定义布局文件activity_popupwindow_left.xml的视图
        View popupWindow_view = getLayoutInflater().inflate(R.layout.view_comment_type_popwindow, null,
                false);
        popupWindow = new PopupWindow(popupWindow_view, RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT, true);
        // 点击其他地方消失
        popupWindow_view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // TODO Auto-generated method stub
                if (popupWindow != null && popupWindow.isShowing()) {
                    popupWindow.dismiss();
                    popupWindow = null;
                }
                return true;
            }
        });
        btns = new Button[3];
        btns[0] = (Button) popupWindow_view.findViewById(R.id.btn_all);
        btns[1] = (Button) popupWindow_view.findViewById(R.id.btn_good);
        btns[2] = (Button) popupWindow_view.findViewById(R.id.btn_bad);
        btns[attitude].setSelected(true);
        btns[0].setOnClickListener(clickListener);
        btns[1].setOnClickListener(clickListener);
        btns[2].setOnClickListener(clickListener);
        btns[0].setText("全部评论（"+total_comments+"）");
        btns[1].setText("看好评论（"+good_comments+"）");
        btns[2].setText("不看好评论（"+(total_comments-good_comments)+"）");

    }

    /***
     * 获取PopupWindow实例
     */
    private void getPopupWindow() {
        if (null != popupWindow) {
            popupWindow.dismiss();
            return;
        } else {
            initPopuptWindow();
        }
    }

    /**
     * 获取评论
     *
     * @throws
     * @Title: getComment
     * @Description: TODO
     * @author: huihaoyan
     * @param: @param event_id
     * @param: @param attitude 1 表示支持， 2 表示反对
     * @return: void
     */
    private void getComment(String event_id, int attitude) {
        httpResponseUtils.postJson(httpPostParams.getPostParams(PostMethod.query_event_for_comment.name(), PostType.comment.name(),
                        httpPostParams.query_event_for_comment(preferenceUtil.getID()+"",preferenceUtil.getUUid(),event_id, attitude,null)),
                CommentInfoDAO.class,
                new PostCommentResponseListener() {
                    @Override
                    public void requestCompleted(Object response) throws JSONException {
                        lv_comment.onRefreshComplete();
                        progressDialog.cancleProgress();
                        if (response == null) return;
                        CommentInfoDAO commentInfoDAO = (CommentInfoDAO) response;
                        total_comments = commentInfoDAO.getEvent().getAllComNum();
                        good_comments = commentInfoDAO.getEvent().getBuyComNum();
                        adapter.setList(commentInfoDAO.getComments(),commentInfoDAO.getLastChildCommentMap(),commentInfoDAO.getLoves());
                    }
                });
    }

    @Override
    public void onRefresh(boolean isTop) {
        if (isTop) {
            getComment(event_id, attitude);
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
                        handler.sendEmptyMessage(0);
                    }
                });
    }
    //添加评论
    private void addComment(String parent_id,String apply_id,String content){
        progressDialog.progressDialog();
        httpResponseUtils.postJson(httpPostParams.getPostParams(
                        PostMethod.add_comment.name(), PostType.comment.name(),
                        httpPostParams.add_comment(preferenceUtil.getID() + "", preferenceUtil.getUUid(),
                                event_id, parent_id,apply_id, content)),
                BaseModel.class,
                new PostCommentResponseListener() {
                    @Override
                    public void requestCompleted(Object response) throws JSONException {
                        progressDialog.cancleProgress();
                        if(response == null) return;
                        hideSoftInputView();
                        view_float_comment_edit.setVisibility(View.GONE);
                        et_comment_apply.setText("");
                        handler.sendEmptyMessage(0);
                    }
                });
    }
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    getComment(event_id, attitude);
                    break;
            }
        }
    };

    //判断是否已登录
    private boolean judgeIsLogin() {
        if (TextUtils.isEmpty(preferenceUtil.getUUid())) {
            Intent intent = new Intent(CommentActivity.this, LoginActivity.class);
            intent.putExtra("login", true);
            startActivity(intent);
            return false;
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        if (popupWindow != null && popupWindow.isShowing()) {
            popupWindow.dismiss();
        }
        super.onBackPressed();
    }
}

package com.futureinst.home.eventdetail;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.futureinst.R;
import com.futureinst.activitytransition.ActivityTransition;
import com.futureinst.baseui.BaseActivity;
import com.futureinst.comment.AddCommentActivity;
import com.futureinst.comment.AddPointActivity;
import com.futureinst.comment.CommentActivity;
import com.futureinst.comment.CommentDeleteDialogUtil;
import com.futureinst.comment.CommentDetailAdapter;
import com.futureinst.global.Content;
import com.futureinst.home.SystemTimeUtile;
import com.futureinst.home.eventdetail.eventdetailabout.EditCommentDialog;
import com.futureinst.home.eventdetail.eventdetailabout.EventPointAdapter;
import com.futureinst.home.eventdetail.eventdetailabout.EventRuleDialog;
import com.futureinst.home.eventdetail.eventdetailabout.OrderTip;
import com.futureinst.home.eventdetail.eventdetailabout.ShareCommentDialog;
import com.futureinst.home.find.ArticleDetailActivity;
import com.futureinst.login.LoginActivity;
import com.futureinst.model.basemodel.BaseModel;
import com.futureinst.model.comment.ArticleDAO;
import com.futureinst.model.comment.CommentDAO;
import com.futureinst.model.homeeventmodel.CommentAndArticleInfoDAO;
import com.futureinst.model.homeeventmodel.EventBuyDAO;
import com.futureinst.model.homeeventmodel.EventPriceDAOInfo;
import com.futureinst.model.homeeventmodel.EventPriceInfo;
import com.futureinst.model.homeeventmodel.EventRelatedInfo;
import com.futureinst.model.homeeventmodel.EventSellDAO;
import com.futureinst.model.homeeventmodel.QueryEventDAO;
import com.futureinst.model.order.SingleEventClearDAO;
import com.futureinst.model.order.SingleEventInfoDAO;
import com.futureinst.net.CommentOperate;
import com.futureinst.net.HttpPath;
import com.futureinst.net.PostCommentResponseListener;
import com.futureinst.net.PostMethod;
import com.futureinst.net.PostType;
import com.futureinst.net.SingleEventScope;
import com.futureinst.newbieguide.EventdetailGuide;
import com.futureinst.newbieguide.GuideClickInterface;
import com.futureinst.newbieguide.NewbieGuide;
import com.futureinst.newbieguide.NewbieGuide2;
import com.futureinst.personalinfo.other.PersonalShowActivity;
import com.futureinst.roundimageutils.RoundedImageView;
import com.futureinst.share.OneKeyShareUtil;
import com.futureinst.utils.DialogShow;
import com.futureinst.utils.ImageLoadOptions;
import com.futureinst.utils.LoginUtil;
import com.futureinst.utils.LongTimeUtil;
import com.futureinst.utils.MyProgressDialog;
import com.futureinst.utils.MyToast;
import com.futureinst.utils.TimeUtil;
import com.futureinst.utils.Utils;
import com.futureinst.utils.lottery.LotteryUtil;
import com.futureinst.widget.CustomView_Image_Text;
import com.futureinst.widget.PullLayout;
import com.futureinst.widget.list.MyListView;
import com.futureinst.widget.scrollview.MyScrollView;
import com.futureinst.widget.scrollview.PullScrollView;
import com.futureinst.widget.waterwave.CustomDraw;
import com.nostra13.universalimageloader.core.ImageLoader;

import org.json.JSONException;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import cn.sharesdk.sina.weibo.SinaWeibo;
import cn.sharesdk.wechat.friends.Wechat;
import cn.sharesdk.wechat.moments.WechatMoments;


@SuppressLint({"HandlerLeak", "DefaultLocale"})
public class EventDetailActivity extends BaseActivity implements PullLayout.ScrollViewListener {
    private BroadcastReceiver receiver;
    private PullLayout scroll;
    private RelativeLayout rl_top;

    private LazyBagFragment lazyBagFragment;
    private RefrenceFragment refrenceFragment;

    private SingleEventClearDAO singleEventClearDAO;
    private boolean isAttention;
    private EventPriceDAOInfo priceDAOInfo;
    private String event_id;
    private QueryEventDAO event;
    private boolean came;
    private int share_award = 50;
    //模式切换
    private View view_easy, view_advance;
    private Button btn_switch_easy, btn_switch_advance;
    private CustomView_Image_Text btn_easy_look_good, btn_easy_look_bad,
            btn_advance_look_good, btn_advance_look_bad;
    //评论
    private CommentDAO comment;
    private CustomView_Image_Text view_comment_total;
    private MyListView lv_comment;
    private TextView view_empty;
    private CommentDetailAdapter commentAdapter;

    private View view_float_comment_edit;
    private EditText et_comment_apply;
    private Button btn_send;
    private CommentDeleteDialogUtil commentDeleteDialogUtil;

    //精选观点
    private MyListView lv_point;
    private EventPointAdapter eventPointAdapter;


    private ImageView iv_editPoint;
    private LinearLayout ll_empty_point;

    //浮动
    private Button btn_comment_float, btn_comment_total_float;
    private View view_comment_float;
    private View view_comment, view_point_layout, view_reference_layout, view_lazyBag_layout;
    //头部
    private ImageView btn_invivate;

    private ImageView iv_image;
    private ImageView iv_refresh;
    private TextView tv_time, tv_event_title;

    private CustomDraw customDraw;//自定义价格动画
    private TextView tv_description;
    private TextView[] tv_buys_1, tv_buys_2, tv_buys_3, tv_sells_1, tv_sells_2, tv_sells_3;

    //单个事件账单
    private View view_single_event;
    private TextView tv_buy_2;
    private TextView tv_sell_2;
    private ImageView iv_share_order;
    private LinearLayout ll_event_buy, ll_event_sell;
    private TextView tv_eventdetail_gain_good, tv_eventdetail_gain_bad;

    private LinearLayout ll_detail_buy, ll_detail_sell;
    private int attitude = 0;//下单后提示评论(1:看好 3：不看好)

    private boolean timeIsStart, isDestroy;


    private Handler handler = new Handler() {
        @Override
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case 1:
                    Long time = event.getTradeTime() - SystemTimeUtile.getInstance(0L).getSystemTime();
                    if (time > 1000) {
                        tv_time.setText(LongTimeUtil.longTimeUtil(time));

                    } else {
                        tv_time.setText("待清算");
                    }
                    break;
                case 2:
                    getPrice();
                    break;
                case 3:
                    getCommentAndArticle();
                    break;
            }
        }

    };

    //显示新手引导
    private void showGuide() {
        if (preferenceUtil.getGuide2())
            return;
        new NewbieGuide2(this, isHavaPrice, 1);
        preferenceUtil.setGuide2();
    }

    @Override
    protected void onRightImageViewClick(View view) {
        super.onRightImageViewClick(view);
        if (event == null) return;
        showOperateDialog(event);
    }

    @Override
    protected void localOnCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        setContentView(R.layout.activity_event_detail_about);
        initView();
        progressDialog.progressDialog();
        getPrice();
        showGuide();
    }

    @Override
    protected void onResume() {
        super.onResume();
        getEvetnRealted();
        getCommentAndArticle();
//        handler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                getPrice();
//            }
//        }, 2000);

        if (!isPriceRefresh) {
            isPriceRefresh = true;
            refreshPrice();
        }
    }

    private void initView() {
        receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (intent.getAction().equals("priceClear")) {
                    attitude = intent.getIntExtra("attitude", 0);
                    progressDialog.progressDialog();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            getPrice();
                            query_single_event_clear();
                        }
                    }, 1000);
                } else if (intent.getAction().equals("praise")) {
                    getCommentAndArticle();
                }
            }
        };
        IntentFilter filter = new IntentFilter();
        filter.addAction("priceClear");
        filter.addAction("praise");
        registerReceiver(receiver, filter);
        getLeftImageView().setImageDrawable(getResources().getDrawable(R.drawable.back));
        getRightImageView().setImageDrawable(getResources().getDrawable(R.drawable.detail_operate));
        setTitle(R.string.event_detail);

        came = getIntent().getBooleanExtra("boolean", false);
        event_id = getIntent().getStringExtra("eventId");

        scroll = (PullLayout) findViewById(R.id.scroll);

        scroll.setScrollViewListener(this);
        scroll.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (et_comment_apply.getVisibility() == View.VISIBLE) {
                    hideSoftInputView();
                    view_float_comment_edit.setVisibility(View.GONE);
                }
                return false;
            }
        });
        ll_detail_buy = (LinearLayout) findViewById(R.id.ll_detail_buy);
        ll_detail_sell = (LinearLayout) findViewById(R.id.ll_detail_sell);
        ll_detail_buy.setOnClickListener(clickListener);
        ll_detail_sell.setOnClickListener(clickListener);
        tv_buys_1 = new TextView[3];
        tv_buys_2 = new TextView[3];
        tv_buys_3 = new TextView[3];
        tv_sells_1 = new TextView[3];
        tv_sells_2 = new TextView[3];
        tv_sells_3 = new TextView[3];

        customDraw = (CustomDraw) findViewById(R.id.wav);
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, Utils.getScreenWidth(this) * 3 / 5);
        customDraw.setLayoutParams(layoutParams);
        tv_time = (TextView) findViewById(R.id.tv_time);
        iv_image = (ImageView) findViewById(R.id.iv_image);
        iv_refresh = (ImageView) findViewById(R.id.iv_refresh);
        iv_refresh.setOnClickListener(clickListener);

        view_single_event = findViewById(R.id.view_singlev_event);
        view_single_event.setVisibility(View.GONE);
        tv_buy_2 = (TextView) findViewById(R.id.tv_event_buy_2);
        tv_sell_2 = (TextView) findViewById(R.id.tv_event_sell_2);
        ll_event_buy = (LinearLayout) findViewById(R.id.ll_event_buy);
        ll_event_sell = (LinearLayout) findViewById(R.id.ll_event_sell);
        btn_invivate = (ImageView) findViewById(R.id.btn_invivate_float);
        iv_share_order = (ImageView) findViewById(R.id.iv_share_order);
        btn_invivate.setOnClickListener(clickListener);
        tv_eventdetail_gain_good = (TextView) findViewById(R.id.tv_eventdetail_gain_good);
        tv_eventdetail_gain_bad = (TextView) findViewById(R.id.tv_eventdetail_gain_bad);

        tv_event_title = (TextView) findViewById(R.id.tv_event_title);
        tv_description = (TextView) findViewById(R.id.tv_description);

        view_point_layout = findViewById(R.id.view_point_layout);
        view_reference_layout = findViewById(R.id.view_reference_layout);
        view_lazyBag_layout = findViewById(R.id.view_lazyBag_layout);
        initPriceView();
        initSwitchModel();
        initFloatView();
        initOrder();
        initCommentView();
        initPoint();
        initLazyBagAndReference();
    }

    //init order
    private void initOrder() {
        btn_easy_look_good = (CustomView_Image_Text) findViewById(R.id.btn_easy_look_good);
        btn_easy_look_bad = (CustomView_Image_Text) findViewById(R.id.btn_easy_look_bad);
        btn_advance_look_good = (CustomView_Image_Text) findViewById(R.id.btn_advance_look_good);
        btn_advance_look_bad = (CustomView_Image_Text) findViewById(R.id.btn_advance_look_bad);
        btn_easy_look_good.setOnClickListener(clickListener);
        btn_easy_look_bad.setOnClickListener(clickListener);
        btn_advance_look_good.setOnClickListener(clickListener);
        btn_advance_look_bad.setOnClickListener(clickListener);
        btn_easy_look_good.setSelected(true);
        btn_easy_look_bad.setSelected(true);
        btn_advance_look_good.setSelected(true);
        btn_advance_look_bad.setSelected(true);
    }

    //评论
    private void initCommentView() {

        view_float_comment_edit = findViewById(R.id.view_float_comment_edit);
        et_comment_apply = (EditText) findViewById(R.id.et_comment_apply);
        btn_send = (Button) findViewById(R.id.btn_send);
        commentDeleteDialogUtil = CommentDeleteDialogUtil.newInstance();

        lv_comment = (MyListView) findViewById(R.id.lv_comment);
        view_empty = (TextView) findViewById(R.id.view_empty);
        view_comment_total = (CustomView_Image_Text) findViewById(R.id.view_comment_total);
        view_comment_total.setOnClickListener(clickListener);
        commentAdapter = new CommentDetailAdapter(this);
        lv_comment.setAdapter(commentAdapter);
        lv_comment.setEmptyView(view_empty);
        commentAdapter.setOperateListener(new CommentDetailAdapter.PraiseOperateListener() {
            @Override
            public void onClickListener(String com_id, String operate) {
                if (LoginUtil.judgeIsLogin(EventDetailActivity.this)) {//点赞
                    operate_comment(com_id, operate);
                }
            }
        });
        //回复
        commentAdapter.setApplyCommentListener(new CommentDetailAdapter.ApplyCommentListener() {
            @Override
            public void onClickListener(CommentDAO coment) {
                if (LoginUtil.judgeIsLogin(EventDetailActivity.this)) {
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
        commentAdapter.setDeleteCommentListener(new CommentDetailAdapter.DeleteCommentListener() {
            @Override
            public void onClickListener(CommentDAO comment) {
                final CommentDAO dao = comment;
                commentDeleteDialogUtil.show(EventDetailActivity.this, new CommentDeleteDialogUtil.DelCommentListener() {
                    @Override
                    public void onClickListener() {
                        //删除评论
                        operate_comment(dao.getId() + "", CommentOperate.delete.name());
                    }
                });
            }
        });

        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(et_comment_apply.getText().toString().trim())) {
                    showToast("输入内容不能为空");
                } else {
                    addComment(comment.getId() + "", "0", et_comment_apply.getText().toString().trim());
                }
            }
        });
    }

    //添加评论
    private void addComment(String parent_id, String apply_id, String content) {
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
                        handler.sendEmptyMessage(3);
                    }
                });
    }

    //精选观点
    private void initPoint() {

        lv_point = (MyListView) findViewById(R.id.lv_article);
        iv_editPoint = (ImageView) findViewById(R.id.iv_editPoint);
        iv_editPoint.setOnClickListener(clickListener);
        ll_empty_point = (LinearLayout) findViewById(R.id.ll_empty_point);
        ll_empty_point.setOnClickListener(clickListener);
        eventPointAdapter = new EventPointAdapter(this);
        lv_point.setAdapter(eventPointAdapter);
        lv_point.setEmptyView(ll_empty_point);

    }



    //init float
    private void initFloatView() {
        view_comment = findViewById(R.id.view_comment);
        view_comment_float = findViewById(R.id.view_comment_float);
        view_comment_float.setVisibility(View.GONE);
        btn_comment_float = (Button) findViewById(R.id.btn_comment_float);
        btn_comment_total_float = (Button) findViewById(R.id.btn_comment_total_float);
        btn_comment_float.setOnClickListener(clickListener);
        btn_comment_total_float.setOnClickListener(clickListener);
    }

    //初始化模式切换
    private void initSwitchModel() {

        view_easy = findViewById(R.id.view_easy);
        view_advance = findViewById(R.id.view_advance);
        btn_switch_advance = (Button) findViewById(R.id.btn_switch_advance);
        btn_switch_easy = (Button) findViewById(R.id.btn_switch_easy);

        btn_switch_advance.setOnClickListener(clickListener);
        btn_switch_easy.setOnClickListener(clickListener);
        if (preferenceUtil.getEasyModel()) {
            view_easy.setVisibility(View.VISIBLE);
            view_advance.setVisibility(View.GONE);
        } else {
            view_easy.setVisibility(View.GONE);
            view_advance.setVisibility(View.VISIBLE);
        }
    }

    private void initPriceView() {
        tv_buys_1[0] = (TextView) findViewById(R.id.tv_buy_1_1);
        tv_buys_1[1] = (TextView) findViewById(R.id.tv_buy_2_1);
        tv_buys_1[2] = (TextView) findViewById(R.id.tv_buy_3_1);

        tv_buys_2[0] = (TextView) findViewById(R.id.tv_buy_1_2);
        tv_buys_2[1] = (TextView) findViewById(R.id.tv_buy_2_2);
        tv_buys_2[2] = (TextView) findViewById(R.id.tv_buy_3_2);

        tv_buys_3[0] = (TextView) findViewById(R.id.tv_buy_1_3);
        tv_buys_3[1] = (TextView) findViewById(R.id.tv_buy_2_3);
        tv_buys_3[2] = (TextView) findViewById(R.id.tv_buy_3_3);

        tv_sells_1[0] = (TextView) findViewById(R.id.tv_sell_1_1);
        tv_sells_1[1] = (TextView) findViewById(R.id.tv_sell_2_1);
        tv_sells_1[2] = (TextView) findViewById(R.id.tv_sell_3_1);

        tv_sells_2[0] = (TextView) findViewById(R.id.tv_sell_1_2);
        tv_sells_2[1] = (TextView) findViewById(R.id.tv_sell_2_2);
        tv_sells_2[2] = (TextView) findViewById(R.id.tv_sell_3_2);

        tv_sells_3[0] = (TextView) findViewById(R.id.tv_sell_1_3);
        tv_sells_3[1] = (TextView) findViewById(R.id.tv_sell_2_3);
        tv_sells_3[2] = (TextView) findViewById(R.id.tv_sell_3_3);
    }

    //单个事件的账单
    private void initSingleEvent(SingleEventInfoDAO singleEventInfo) {
        if (attitude != 0) {//有下单，看是否提示
            OrderTip.orderTip(EventDetailActivity.this,event,singleEventInfo.getUser().getComment(), share_award,attitude);
        }
       final SingleEventClearDAO item = singleEventInfo.getUser().getEvent_clear();
        singleEventClearDAO = item;
        if (item.getBuyNum() == 0 && item.getSellNum() == 0) {
            view_single_event.setVisibility(View.GONE);
            return;
        }
        view_single_event.setVisibility(View.VISIBLE);
        String gain_good = String.format("%.2f", singleEventInfo.getUser().getIf_yes());
        String gain_bad = String.format("%.2f", singleEventInfo.getUser().getIf_no());
        tv_eventdetail_gain_good.setText(gain_good);
        tv_eventdetail_gain_bad.setText(gain_bad);
        if (singleEventInfo.getUser().getIf_yes() >= 0) {
            tv_eventdetail_gain_good.setText("+" + gain_good);
        }
        if (singleEventInfo.getUser().getIf_no() >= 0) {
            tv_eventdetail_gain_bad.setText("+" + gain_bad);
        }
        if (item.getBuyNum() > 0) {
            String text = getResources().getString(R.string.unhold_1_1) + String.format("%3d", item.getBuyNum()) + "份,均价" + String.format("%.2f", item.getBuyPrice());
            SpannableStringBuilder stringBuilder = new SpannableStringBuilder(text);
            ForegroundColorSpan redSpan = new ForegroundColorSpan(getResources().getColor(R.color.gain_red));
            stringBuilder.setSpan(redSpan, 2, 4, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            tv_buy_2.setText(stringBuilder);
            ll_event_buy.setVisibility(View.VISIBLE);
        } else {
            ll_event_buy.setVisibility(View.GONE);
        }
        if (item.getSellNum() > 0) {
            String text = getResources().getString(R.string.unhold_2) + String.format("%3d", item.getSellNum()) + "份,均价" + String.format("%.2f", item.getSellPrice());
            SpannableStringBuilder stringBuilder = new SpannableStringBuilder(text);
            ForegroundColorSpan redSpan = new ForegroundColorSpan(getResources().getColor(R.color.gain_blue));
            stringBuilder.setSpan(redSpan, 2, 4, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            tv_sell_2.setText(stringBuilder);
            ll_event_sell.setVisibility(View.VISIBLE);
        } else {
            ll_event_sell.setVisibility(View.GONE);
        }
        iv_share_order.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(item.getSellNum() > 0 && item.getBuyNum() > 0){//提示选择
                    ShareCommentDialog.showShareCheckDialog(EventDetailActivity.this, event, share_award);
                }else if(item.getSellNum() > 0){//不看好
                    ShareCommentDialog.showShareCommentDialog(EventDetailActivity.this,3,event,share_award);
                }else if(item.getBuyNum() > 0){//看好
                    ShareCommentDialog.showShareCommentDialog(EventDetailActivity.this,1,event,share_award);
                }
            }
        });
    }



    //初始化懒人包和相关新闻
    private void initLazyBagAndReference() {
        Bundle bundle = new Bundle();

        lazyBagFragment = new LazyBagFragment();
        bundle.putString("eventId", event_id);
        lazyBagFragment.setArguments(bundle);
        getSupportFragmentManager().beginTransaction().replace(R.id.container_incident, lazyBagFragment).commitAllowingStateLoss();


        refrenceFragment = new RefrenceFragment();
        bundle.putString("eventId", event_id);
        refrenceFragment.setArguments(bundle);
        getSupportFragmentManager().beginTransaction().replace(R.id.container_refrence, refrenceFragment).commitAllowingStateLoss();

    }

    //初始化数据
    private void initData(QueryEventDAO event) {
        tv_event_title.setText(event.getTitle());
        tv_description.setText(event.getDescription());
        btn_comment_total_float.setText(" " + event.getAllComNum());
        view_comment_total.setText(event.getAllComNum() + "");
        if (came || event.getStatusStr().equals("已清算")) {
            tv_description.setText(event.getAccord());
        }
        if (iv_image.getTag() == null || !iv_image.getTag().equals(event.getImgsrc())) {
            iv_image.setTag(event.getImgsrc());
            ImageLoader.getInstance().displayImage(event.getImgsrc(), iv_image, ImageLoadOptions.getOptions(R.drawable.image_top_default));
        }
        customDraw.setPrice(event.getCurrPrice());
        customDraw.setUpdate_price(event.getPriceChange());
        customDraw.start();

        tv_time.setText(event.getStatusStr());
        showTimeStatus();
    }

    OnClickListener clickListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.iv_refresh:
                    progressDialog.progressDialog();
                    attitude = 0;
                    getPrice();
                    if (preferenceUtil.getID() > 0) {
                        query_single_event_clear();
                    }
                    break;
                case R.id.ll_empty_point:
                case R.id.iv_editPoint://添加观点
                    if (LoginUtil.judgeIsLogin(EventDetailActivity.this)) {
                        Intent intent = new Intent(EventDetailActivity.this, AddPointActivity.class);
                        intent.putExtra("event_id", event_id);
                        startActivity(intent);
                    }
                    break;
                case R.id.view_comment_total://进入评论详情列表
                    Intent commentDetailIntent = new Intent(EventDetailActivity.this, CommentActivity.class);
                    commentDetailIntent.putExtra("eventId", event_id);
                    startActivity(commentDetailIntent);
                    break;
                case R.id.btn_comment_float://添加评论
                    if (LoginUtil.judgeIsLogin(EventDetailActivity.this)) {
                        Intent intent = new Intent(EventDetailActivity.this, AddCommentActivity.class);
                        intent.putExtra("eventId", event_id);
                        startActivity(intent);
                    }
                    break;
                case R.id.btn_comment_total_float://评论列表
                    Intent commentDetailFlotIntent = new Intent(EventDetailActivity.this, CommentActivity.class);
                    commentDetailFlotIntent.putExtra("eventId", event_id);
                    startActivity(commentDetailFlotIntent);
                    break;
                case R.id.btn_switch_advance://切换到专家模式
                    if (!preferenceUtil.getGuide3()) {
                        new NewbieGuide2(EventDetailActivity.this, isHavaPrice, 2);
                        preferenceUtil.setGuide3();
                    }
                    preferenceUtil.setEasyModel(false);
                    view_advance.setVisibility(View.VISIBLE);
                    view_easy.setVisibility(View.GONE);
                    break;
                case R.id.btn_switch_easy://切换到简易模式
                    preferenceUtil.setEasyModel(true);
                    view_advance.setVisibility(View.GONE);
                    view_easy.setVisibility(View.VISIBLE);
                    break;
                case R.id.btn_invivate_float://清算依据
                    if (event == null) return;
                    EventRuleDialog.showDialog(EventDetailActivity.this, event.getRule());
                    break;
                case R.id.btn_easy_look_good://简易模式看好
                    if (!LoginUtil.judgeIsLogin(EventDetailActivity.this) || event == null ||
                            priceDAOInfo.getSells() == null || priceDAOInfo.getSells().size() == 0)
                        return;

                    showBuyConfig(1, getPrice(1, priceDAOInfo));
                    break;
                case R.id.btn_easy_look_bad://简易模式不看好
                    if (!LoginUtil.judgeIsLogin(EventDetailActivity.this) || event == null ||
                            priceDAOInfo.getBuys() == null || priceDAOInfo.getBuys().size() == 0)
                        return;
                    showBuyConfig(3, getPrice(3, priceDAOInfo));
                    break;
                case R.id.btn_advance_look_good://专家模式看好
                    if (!LoginUtil.judgeIsLogin(EventDetailActivity.this) || event == null) return;
                    Intent intent1 = new Intent(EventDetailActivity.this, EventBuyActivity.class);
                    intent1.putExtra("buyOrSell", true);
                    intent1.putExtra("assure", singleEventClearDAO);
                    intent1.putExtra("event", event);
                    intent1.putExtra("price", priceDAOInfo);
                    startActivity(intent1);
                    break;
                case R.id.btn_advance_look_bad://专家模式不看好
                    if (!LoginUtil.judgeIsLogin(EventDetailActivity.this) || event == null) return;
                    Intent intent2 = new Intent(EventDetailActivity.this, EventBuyActivity.class);
                    intent2.putExtra("buyOrSell", false);
                    intent2.putExtra("assure", singleEventClearDAO);
                    intent2.putExtra("event", event);
                    intent2.putExtra("price", priceDAOInfo);
                    startActivity(intent2);
                    break;
                case R.id.ll_detail_buy://三档看好--不看好
                    if (!LoginUtil.judgeIsLogin(EventDetailActivity.this) || event == null ||
                            priceDAOInfo.getBuys() == null || priceDAOInfo.getBuys().size() == 0)
                        return;
                    Intent intent3 = new Intent(EventDetailActivity.this, EventBuyActivity.class);
                    intent3.putExtra("buyOrSell", false);
                    intent3.putExtra("assure", singleEventClearDAO);
                    QueryEventDAO eventDAO = event;
                    event.setCurrPrice(priceDAOInfo.getBuys().get(0).getPrice());
                    intent3.putExtra("event", eventDAO);
                    intent3.putExtra("number", priceDAOInfo.getBuys().get(0).getNum());
                    intent3.putExtra("price", priceDAOInfo);
                    startActivity(intent3);
                    break;
                case R.id.ll_detail_sell://三档不看好--看好
                    if (!LoginUtil.judgeIsLogin(EventDetailActivity.this) || event == null ||
                            priceDAOInfo.getSells() == null || priceDAOInfo.getSells().size() == 0)
                        return;
                    Intent intent4 = new Intent(EventDetailActivity.this, EventBuyActivity.class);
                    intent4.putExtra("buyOrSell", true);
                    intent4.putExtra("assure", singleEventClearDAO);
                    QueryEventDAO dao = event;
                    event.setCurrPrice(priceDAOInfo.getSells().get(0).getPrice());
                    intent4.putExtra("event", dao);
                    intent4.putExtra("number", priceDAOInfo.getSells().get(0).getNum());
                    intent4.putExtra("price", priceDAOInfo);
                    startActivity(intent4);
                    break;
            }
        }
    };

    //初始化价格数据
    private void initPrice(EventPriceDAOInfo info) {
        //价格三等对比
        List<EventBuyDAO> buys = info.getBuys();
        List<EventSellDAO> sells = info.getSells();
        DecimalFormat df = new DecimalFormat("##0.00");
        priceViewReset();
        for (int i = 0; i < buys.size(); i++) {
            tv_buys_1[i].setText(String.format("%3d", buys.get(i).getNum()) + "份");
            tv_buys_2[i].setVisibility(View.INVISIBLE);
            tv_buys_3[i].setText(df.format(buys.get(i).getPrice()));
        }
        for (int j = 0; j < sells.size(); j++) {
            tv_sells_1[j].setText(df.format(sells.get(j).getPrice()));
            tv_sells_2[j].setVisibility(View.INVISIBLE);
            tv_sells_3[j].setText(String.format("%3d", sells.get(j).getNum()) + "份");
        }
    }

    //重置三档价格
    private void priceViewReset() {
        for (int i = 0; i < tv_buys_1.length; i++) {
            tv_buys_1[i].setText("");
            tv_buys_3[i].setText("");
            tv_buys_2[i].setVisibility(View.VISIBLE);
            tv_sells_1[i].setText("");
            tv_sells_3[i].setText("");
            tv_sells_2[i].setVisibility(View.VISIBLE);
        }
    }

    //获取事件的价格走势
    private boolean priceClear;

    private void getPrice() {
        httpResponseUtils.postJson(httpPostParams.getPostParams(
                        PostMethod.query_single_event.name(), PostType.event.name(),
                        httpPostParams.query_single_event(event_id, SingleEventScope.price.name())),
                EventPriceInfo.class,
                new PostCommentResponseListener() {
                    @Override
                    public void requestCompleted(Object response) throws JSONException {
                        progressDialog.cancleProgress();
                        if (response == null) return;
                        EventPriceInfo eventPriceInfo = (EventPriceInfo) response;
                        event = eventPriceInfo.getEvent();
                        initData(event);
                        judgeIsClear(event);
                        initPrice(eventPriceInfo.getPrice());
                        priceDAOInfo = eventPriceInfo.getPrice();
                        SystemTimeUtile.getInstance(eventPriceInfo.getCurr_time()).setSystemTime(eventPriceInfo.getCurr_time());
                        if (!TextUtils.isEmpty(preferenceUtil.getUUid())
                                && event.getStatusStr() != null &&
                                !event.getStatusStr().equals("清算中")
                                && !priceClear) {
                            query_single_event_clear();
                        }
                    }
                });
    }

    //倒计时
    private void showTimeStatus() {
        if (event.getStatusStr().equals("交易中")) {
            if (event.getTradeTime() == null) return;
            Long time = event.getTradeTime() - SystemTimeUtile.getInstance(0L).getSystemTime();
            tv_time.setText(LongTimeUtil.longTimeUtil(time));

            if (!timeIsStart && !isDestroy) {
                timeIsStart = true;
                timeRunThread.start();
            }

        }
    }

    Thread timeRunThread = new Thread(new Runnable() {
        public void run() {
            while (timeIsStart) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Message message = Message.obtain();
                message.what = 1;
                handler.sendMessage(message);
            }
        }
    });
    //刷新价格
    private boolean isPriceRefresh = false;

    private void refreshPrice() {
        while (isPriceRefresh && Content.is_aoto_refresh_event_price) {
            long delayTime = (long) (Math.random() * 10 + Content.aoto_refresh_event_price_interval) * 1000;
            try {
                Thread.sleep(delayTime);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Message message = Message.obtain();
            message.what = 2;
            handler.sendMessage(message);
        }

    }

    //事件清算中不可下单
    private void judgeIsClear(QueryEventDAO event) {
        if (!event.getStatusStr().equals("交易中")) {
            btn_easy_look_good.setSelected(true);
            btn_easy_look_bad.setSelected(true);
            btn_advance_look_good.setSelected(true);
            btn_advance_look_bad.setSelected(true);

            btn_easy_look_good.setClickable(false);
            btn_easy_look_bad.setClickable(false);
            btn_advance_look_good.setClickable(false);
            btn_advance_look_bad.setClickable(false);

            btn_easy_look_good.setBackgroundResource(R.drawable.btn_gray);
            btn_easy_look_bad.setBackgroundResource(R.drawable.btn_gray);
            btn_advance_look_good.setBackgroundResource(R.drawable.btn_gray);
            btn_advance_look_bad.setBackgroundResource(R.drawable.btn_gray);
        }
    }

    //添加我的关注  follow表示关注,unfollow表示取消关注
    private void addMyAttention(final String type) {
        progressDialog.progressDialog();
        httpResponseUtils.postJson(httpPostParams.getPostParams(
                        PostMethod.operate_follow.name(), PostType.follow.name(),
                        httpPostParams.operate_follow(preferenceUtil.getID() + "", preferenceUtil.getUUid(), event_id, type)),
                BaseModel.class,
                new PostCommentResponseListener() {
                    @Override
                    public void requestCompleted(Object response) throws JSONException {
                        progressDialog.cancleProgress();
                        if (response == null) return;
                        if (type.equals("follow")) {
                            MyToast.getInstance().showToast(EventDetailActivity.this, "事件关注成功", 1);
                            isAttention = true;
                        } else {
                            MyToast.getInstance().showToast(EventDetailActivity.this, "事件取消关注成功", 1);
                            isAttention = false;
                        }
                    }
                });
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
                        handler.sendEmptyMessage(3);
                    }
                });
    }

    private boolean isHavaPrice;

    //查询用户对于该事件的操作结果，关注和清算单
    private void query_single_event_clear() {
        progressDialog.progressDialog();
        httpResponseUtils.postJson_1(httpPostParams.getPostParams(
                        PostMethod.query_single_event.name(), PostType.event.name(),
                        httpPostParams.query_single_event(preferenceUtil.getID() + "", preferenceUtil.getUUid(), event_id, SingleEventScope.user.name())),
                SingleEventInfoDAO.class,
                new PostCommentResponseListener() {
                    @Override
                    public void requestCompleted(Object response) throws JSONException {
                        progressDialog.cancleProgress();
                        if (response == null) {
                            view_single_event.setVisibility(View.GONE);
                            return;
                        }
                        priceClear = true;
                        SingleEventInfoDAO singleEventInfoDAO = (SingleEventInfoDAO) response;
                        if (singleEventInfoDAO.getUser().getFollow() == 1) {
                            isAttention = true;
                        }
                        share_award = singleEventInfoDAO.getUser().getShare_award();
                        if (singleEventInfoDAO.getUser().getEvent_clear() == null) {
                            view_single_event.setVisibility(View.GONE);
                            return;
                        }
                        isHavaPrice = true;
                        initSingleEvent(singleEventInfoDAO);
                    }
                });
    }


    //其他操作
    private void showOperateDialog(final QueryEventDAO event) {
        View view = LayoutInflater.from(this).inflate(R.layout.view_event_operate, null);
        String attentionText = null;
        if (isAttention) {
            attentionText = getResources().getString(R.string.event_detail_unfocus);
        } else {
            attentionText = getResources().getString(R.string.event_detail_focus);
        }
        final Dialog dialog = DialogShow.showDialog(this, view, Gravity.BOTTOM);
        Button btn_tips = (Button) view.findViewById(R.id.btn_tips);
        Button btn_edit_point = (Button) view.findViewById(R.id.btn_edit_point);
        Button btn_attention = (Button) view.findViewById(R.id.btn_attention);
        btn_attention.setText(attentionText);
        Button btn_guide = (Button) view.findViewById(R.id.btn_guide);
        Button btn_cancel = (Button) view.findViewById(R.id.btn_cancel);
        btn_tips.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {//分享
                if (event == null) return;
                ShareCommentDialog.showShareDialog(EventDetailActivity.this, event, 0, share_award);
                dialog.dismiss();
            }
        });
        btn_edit_point.setOnClickListener(new OnClickListener() {//写观点赚未币
            @Override
            public void onClick(View v) {
                if (LoginUtil.judgeIsLogin(EventDetailActivity.this)) {
                    Intent intent = new Intent(EventDetailActivity.this, AddPointActivity.class);
                    intent.putExtra("event_id", event_id);
                    startActivity(intent);
                    dialog.dismiss();
                }
            }
        });
        btn_attention.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {//关注
                if (LoginUtil.judgeIsLogin(EventDetailActivity.this)) {
                    String attention = null;
                    if (isAttention) {
                        attention = "unfollow";
                    } else {
                        attention = "follow";
                    }
                    addMyAttention(attention);
                    dialog.dismiss();
                }
            }
        });
        btn_guide.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {//引导
                new EventdetailGuide(EventDetailActivity.this);
                dialog.dismiss();
            }
        });
        btn_cancel.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    @Override
    protected void onPause() {
        super.onPause();
        isPriceRefresh = false;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(receiver);
        progressDialog.cancleProgress();
        timeIsStart = false;
        isPriceRefresh = false;
        isDestroy = true;
        System.gc();
    }

    @Override
    public void onScrollChanged(int x, int y, int oldx, int oldy) {
        int[] location_comment = new int[2];
        int[] location_point = new int[2];
        int[] location_refrence = new int[2];
        int[] location_lazyBag = new int[2];
        int screenHeight = Utils.getScreenHeight(EventDetailActivity.this);
        int statutsHeight = Utils.getStatusHeight(this);
        int location = statutsHeight + Utils.dip2px(this, 48);

        view_comment.getLocationOnScreen(location_comment);
        view_point_layout.getLocationOnScreen(location_point);
        view_reference_layout.getLocationOnScreen(location_refrence);
        view_lazyBag_layout.getLocationOnScreen(location_lazyBag);

        if (location_comment[1] < screenHeight) {
            view_comment_float.setVisibility(View.VISIBLE);
        } else {
            view_comment_float.setVisibility(View.GONE);
        }

    }

    // 获取评论和精选观点
    private void getCommentAndArticle() {
        httpResponseUtils.postJson(
                httpPostParams.getPostParams(PostMethod.query_single_event.name(), PostType.event.name(),
                        httpPostParams.query_single_event(preferenceUtil.getID() + "", preferenceUtil.getUUid(), event_id, SingleEventScope.comment_article.name())),
                CommentAndArticleInfoDAO.class, new PostCommentResponseListener() {
                    @Override
                    public void requestCompleted(Object response) throws JSONException {
                        progressDialog.cancleProgress();
                        if (response == null)
                            return;
                        CommentAndArticleInfoDAO eventRelatedInfo = (CommentAndArticleInfoDAO) response;
                        commentAdapter.setList(eventRelatedInfo.getComment().getComments(), eventRelatedInfo.getComment().getLastChildCommentMap(), eventRelatedInfo.getComment().getLoves());
                        eventPointAdapter.setList(eventRelatedInfo.getArticle().getArticles());
                    }
                });
    }

    //获取懒人包和相关新闻
    private void getEvetnRealted() {
        httpResponseUtils.postJson(httpPostParams.getPostParams(
                        PostMethod.query_single_event.name(), PostType.event.name(),
                        httpPostParams.query_single_event(event_id, SingleEventScope.refer_lazy.name())),
                EventRelatedInfo.class,
                new PostCommentResponseListener() {
                    @Override
                    public void requestCompleted(Object response) throws JSONException {
                        if (response == null) return;
                        EventRelatedInfo eventRelatedInfo = (EventRelatedInfo) response;
                        if (eventRelatedInfo.getLazybag() != null) {
                            Intent lazybagIntent = new Intent("lazybag");
                            lazybagIntent.putExtra("data", eventRelatedInfo.getLazybag());
                            sendBroadcast(lazybagIntent);
                        }
                        if (eventRelatedInfo.getRefer() != null) {
                            Intent referIntent = new Intent("refer");
                            referIntent.putExtra("data", eventRelatedInfo.getRefer());
                            sendBroadcast(referIntent);
                        }
                    }
                });
    }

    //添加订单
    private void addOrder(final int type, String price, int num) {
        progressDialog.progressDialog();
        Content.isPull = true;
        httpResponseUtils.postJson(httpPostParams.getPostParams(
                        PostMethod.add_order.name(), PostType.order.name(),
                        httpPostParams.add_order(preferenceUtil.getID() + "", preferenceUtil.getUUid(), type + "", price, num + "", event.getId() + "", "easy")),
                BaseModel.class,
                new PostCommentResponseListener() {
                    @Override
                    public void requestCompleted(Object response) throws JSONException {
                        progressDialog.cancleProgress();
                        Content.isPull = false;
                        if (response == null) return;
                        //交易成功
                        Intent intent = new Intent("priceClear");
                        intent.putExtra("attitude", type);
                        sendBroadcast(intent);
                    }
                });
    }

    //简易模式价格
    private String getPrice(int model, EventPriceDAOInfo priceDAOInfo) {
        String price = event.getCurrPrice() + "";
        switch (model) {
            case 1://看好
                for (EventSellDAO sellDAO : priceDAOInfo.getSells()) {
                    if (sellDAO.getNum() >= 10) {
                        price = sellDAO.getPrice() + "";
                        break;
                    }
                }
                break;
            case 3://不看好
                for (EventBuyDAO buyDAO : priceDAOInfo.getBuys()) {
                    if (buyDAO.getNum() >= 10) {
                        price = buyDAO.getPrice() + "";
                        break;
                    }
                }
                break;
        }
        return price;
    }

    //事件购买确认提示
    private void showBuyConfig(final int type, final String price) {
        View view = LayoutInflater.from(this).inflate(R.layout.view_event_order_config, null, false);
        Button btn_cancel = (Button) view.findViewById(R.id.btn_cancel);
        TextView tv_configMsg = (TextView) view.findViewById(R.id.tv_configMsg);
        String configMsg = "";
        switch (type) {//type 1-限价买进 2-市价买进 3-限价卖空 4-市价卖空
            case 1:
                configMsg = "确定看好";
                break;
            case 3:
                configMsg = "确定不看好";
                break;
        }
        tv_configMsg.setText(configMsg);
        Button btn_config = (Button) view.findViewById(R.id.btn_submit);
        final Dialog dialog = DialogShow.showDialog(this, view, Gravity.CENTER);
        btn_cancel.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });
        btn_config.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                addOrder(type, price, 10);
                dialog.cancel();
            }
        });
        dialog.show();
    }


}

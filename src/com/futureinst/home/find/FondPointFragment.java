package com.futureinst.home.find;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.futureinst.R;
import com.futureinst.baseui.BaseFragment;
import com.futureinst.login.LoginActivity;
import com.futureinst.model.basemodel.BaseModel;
import com.futureinst.model.comment.ArticleDAO;
import com.futureinst.model.point.PointInfoDAO;
import com.futureinst.net.HttpPostParams;
import com.futureinst.net.HttpResponseUtils;
import com.futureinst.net.PostCommentResponseListener;
import com.futureinst.net.PostMethod;
import com.futureinst.net.PostType;
import com.futureinst.push.PushWebActivity;
import com.futureinst.roundimageutils.RoundedImageView;
import com.futureinst.sharepreference.SharePreferenceUtil;
import com.futureinst.utils.ImageLoadOptions;
import com.futureinst.utils.MyProgressDialog;
import com.futureinst.utils.TimeUtil;
import com.futureinst.widget.list.PullListView;
import com.nostra13.universalimageloader.core.ImageLoader;

import org.json.JSONException;


/**
 * 发现-观点
 */
public class FondPointFragment extends BaseFragment implements PullListView.OnRefreshListener {
    private SharePreferenceUtil preferenceUtil;
    private MyProgressDialog progressDialog;
    private HttpPostParams httpPostParams;
    private HttpResponseUtils httpResponseUtils;
    private PullListView lv_article;
    private PointAdapter adapter;
    private String topImageUrl = null;
    //today_article
    private View view_top;
    private ImageView iv_article_top;
    private ImageView iv_event;
    private TextView tv_event_type,tv_event_title;
    RoundedImageView headImage_select;
    private TextView tv_name,tv_time,tv_read,tv_praise,tv_comment;
    private TextView tv_select_point_title;//精选观点标题
    private TextView tv_select_point_content;//精选观点内容
    private View view_select_point;
    private ArticleDAO ArticleDAO;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 111:
                    query_top_article();
                    break;
            }
        }
    };

    @Override
    protected void localOnCreate(Bundle savedInstanceState) {
        setContentView(R.layout.fragment_fond_attitude);
        initView();
        initTop();
        progressDialog.progressDialog();
        query_top_article();
    }

    private void initView() {
        preferenceUtil = SharePreferenceUtil.getInstance(getContext());
        progressDialog = MyProgressDialog.getInstance(getContext());
        httpPostParams = HttpPostParams.getInstace();
        httpResponseUtils = HttpResponseUtils.getInstace(getActivity());

        lv_article = (PullListView) findViewById(R.id.lv_article);
        view_top = LayoutInflater.from(getContext()).inflate(R.layout.view_article_top, null, false);
        view_select_point = view_top.findViewById(R.id.view_select_point);

        adapter = new PointAdapter(getContext());
        lv_article.addHeaderView(view_top);
        lv_article.setAdapter(adapter);
        lv_article.setonRefreshListener(this);

        lv_article.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(position < 2) return;
                com.futureinst.model.comment.ArticleDAO item = (ArticleDAO) adapter.getItem(position-2);
                Intent intent = new Intent(getContext(), ArticleDetailActivity.class);
                intent.putExtra("article_id", item.getId()+"");
                startActivity(intent);
            }
        });
        view_select_point.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ArticleDAO == null) return;
                Intent intent = new Intent(getContext(), ArticleDetailActivity.class);
                intent.putExtra("article_id", ArticleDAO.getId()+"");
                startActivity(intent);
            }
        });
    }
    private void initTop(){


        iv_event = (ImageView)view_top.findViewById(R.id.iv_event);
        iv_article_top = (ImageView)view_top.findViewById(R.id.iv_article_top);
        tv_event_type = (TextView)view_top.findViewById(R.id.tv_event_type);
        tv_event_title = (TextView)view_top.findViewById(R.id.tv_event_title);
        headImage_select = (RoundedImageView)view_top.findViewById(R.id.headImage_select);
        tv_name = (TextView)view_top.findViewById(R.id.tv_name);
        tv_time = (TextView)view_top.findViewById(R.id.tv_time);
        tv_read = (TextView)view_top.findViewById(R.id.tv_read);
        tv_praise = (TextView)view_top.findViewById(R.id.tv_praise);
        tv_comment = (TextView)view_top.findViewById(R.id.tv_comment);

        tv_select_point_title = (TextView)view_top.findViewById(R.id.tv_select_point_title);
        tv_select_point_content = (TextView)view_top.findViewById(R.id.tv_select_point_content);

        iv_article_top.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(topImageUrl != null){
                    Intent intent = new Intent(getActivity(), PushWebActivity.class);
                    intent.putExtra("url", topImageUrl);
                    intent.putExtra("title", "未来研究所");
                    startActivity(intent);
                }
            }
        });
    }
    //初始化观点精选
    private void initDate(ArticleDAO today_article){
        if(today_article == null ){
            return;
        }
        if(iv_event.getTag() == null || !iv_event.getTag().equals(today_article.getEvent().getImgsrc())){
            ImageLoader.getInstance().displayImage(today_article.getEvent().getImgsrc(),iv_event, ImageLoadOptions.getOptions(R.drawable.image_top_default));
            iv_event.setTag(today_article.getEvent().getImgsrc());
        }
        tv_event_title.setText(today_article.getEvent().getLead());
        tv_event_type.setText(today_article.getEvent().getTagstr());
        if(headImage_select.getTag() == null || !headImage_select.getTag().equals(today_article.getUser().getHeadImage())){
            ImageLoader.getInstance().displayImage(today_article.getUser().getHeadImage(), headImage_select, ImageLoadOptions.getOptions(R.drawable.logo));
            headImage_select.setTag(today_article.getUser().getHeadImage());
        }
        tv_name.setText(today_article.getUser().getName());
        tv_read.setText(today_article.getReadNum()+"人已阅读");
        tv_praise.setText(" "+today_article.getLoveNum());
        tv_comment.setText(" "+today_article.getCommentNum());
        tv_select_point_title.setText(today_article.getTitle());
        tv_select_point_content.setText(today_article.getAbstr());
        tv_time.setText(TimeUtil.getDescriptionTimeFromTimestamp(today_article.getMtime()));

    }

    //查询观点
    private void query_top_article(){
        httpResponseUtils.postJson(httpPostParams.getPostParams(
                        PostMethod.query_top_article.name(), PostType.article.name(),
                        httpPostParams.query_top_article(preferenceUtil.getID() + "", preferenceUtil.getUUid())),
                PointInfoDAO.class,
                new PostCommentResponseListener() {
                    @Override
                    public void requestCompleted(Object response) throws JSONException {
                        progressDialog.cancleProgress();
                        lv_article.onRefreshComplete();
                        if (response == null) return;
                        PointInfoDAO pointInfoDAO = (PointInfoDAO) response;
                        ArticleDAO = pointInfoDAO.getToday_article();
                        adapter.setList(pointInfoDAO.getArticles());
                        initDate(pointInfoDAO.getToday_article());
                        topImageUrl = pointInfoDAO.getTop_url();
                    }
                });
    }
    //观点操作(read(阅读),award(打赏,award:打赏金额),love(点赞),unlove(取消点赞))
    private void articleOperate(String id, String operate, int award) {
        httpResponseUtils.postJson(httpPostParams.getPostParams(PostMethod.operate_article.name(), PostType.article.name(),
                        httpPostParams.operate_article(preferenceUtil.getID() + "", preferenceUtil.getUUid(), id, operate, award)),
                BaseModel.class, new PostCommentResponseListener() {
                    @Override
                    public void requestCompleted(Object response) throws JSONException {
                        if (response == null) {
                            progressDialog.cancleProgress();
                            return;
                        }
                        handler.sendEmptyMessage(111);
                    }
                });
    }
    //判断是否已登录
    private boolean judgeIsLogin() {
        if (TextUtils.isEmpty(SharePreferenceUtil.getInstance(getContext()).getUUid())) {
            Intent intent = new Intent(getContext(), LoginActivity.class);
            intent.putExtra("login", true);
            startActivity(intent);
            return false;
        }
        return true;
    }

    @Override
    public void onRefresh(boolean isTop) {
        if(isTop){
            query_top_article();
        }
    }
}

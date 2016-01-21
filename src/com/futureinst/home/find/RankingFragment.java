package com.futureinst.home.find;

import org.json.JSONException;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.futureinst.R;
import com.futureinst.baseui.BaseFragment;
import com.futureinst.home.HomeActivity;
import com.futureinst.model.record.UserRecordDAO;
import com.futureinst.model.record.UserSearchInfo;
import com.futureinst.model.usermodel.RankDAO;
import com.futureinst.model.usermodel.RankInfo;
import com.futureinst.model.usermodel.UserInformationInfo;
import com.futureinst.model.usermodel.UserTagRecordDAO;
import com.futureinst.model.usermodel.UserTagRecordInfoDAO;
import com.futureinst.net.HttpPostParams;
import com.futureinst.net.HttpResponseUtils;
import com.futureinst.net.PostCommentResponseListener;
import com.futureinst.net.PostMethod;
import com.futureinst.net.PostType;
import com.futureinst.personalinfo.other.PersonalShowActivity;
import com.futureinst.roundimageutils.RoundedImageView;
import com.futureinst.sharepreference.SharePreferenceUtil;
import com.futureinst.utils.ImageLoadOptions;
import com.futureinst.utils.MyProgressDialog;
import com.futureinst.utils.MyToast;
import com.futureinst.widget.IconSlidingTabView;
import com.futureinst.widget.clearedittext.ClearEditText;
import com.futureinst.widget.list.PullListView;
import com.futureinst.widget.list.PullListView.OnRefreshListener;
import com.futureinst.widget.IconMonthAndDayView;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

public class RankingFragment extends BaseFragment implements OnRefreshListener {
    private SharePreferenceUtil preferenceUtil;
    private HttpResponseUtils httpResponseUtils;
    private HttpPostParams httpPostParams;
    private MyProgressDialog progressDialog;
    private PullListView pullListView;
    private TextView tv_userName, tv_prophet, tv_ranking;
    private ImageView iv_ranking;
    private RankingAdapter adapter;
    private RoundedImageView iv_headImg;
    private LinearLayout ll_type, ll_ranking_type;
    private ListView lv_type;
    private IconSlidingTabView icon_type;
    private RankingTypeAdapter rankingTypeAdapter;
    private TextView tv_type;
    private int index = 0;
    private BroadcastReceiver receiver;
    private ListView lv_user_search;
    private SearchAdapter searchAdapter;
    private ImageView iv_search;
    private ClearEditText et_user_search;

    private LinearLayout ll_month_day_ranking,ll_ranking_month_day;
    private ListView lv_month_day_ranking;
    private RankingMonthAndDayAdapter rankingMonthAndDayAdapter;
    private IconMonthAndDayView icon_month_day;
    private TextView tv_month_day;

    @Override
    protected void localOnCreate(Bundle savedInstanceState) {
        setContentView(R.layout.fragment_home_ranking);
        initView();
        get_rank();
        query_user_record();
    }

    private void initView() {
        receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (intent.getAction().equals("relation")) {
                    onRefresh(true);
                }
            }
        };
        IntentFilter filter = new IntentFilter();
        filter.addAction("relation");
        getActivity().registerReceiver(receiver, filter);

        preferenceUtil = SharePreferenceUtil.getInstance(getContext());
        httpResponseUtils = HttpResponseUtils.getInstace(getActivity());
        httpPostParams = HttpPostParams.getInstace();
        progressDialog = MyProgressDialog.getInstance(getContext());

        ll_ranking_type = (LinearLayout) findViewById(R.id.ll_ranking_type);
        icon_type = (IconSlidingTabView) findViewById(R.id.icon_type);
        tv_type = (TextView) findViewById(R.id.tv_type);
        ll_type = (LinearLayout) findViewById(R.id.ll_type);
        lv_type = (ListView) findViewById(R.id.lv_type);
        ll_ranking_type.setOnClickListener(onClickListener);
        ll_type.setOnClickListener(onClickListener);
        rankingTypeAdapter = new RankingTypeAdapter(getContext());
        lv_type.setAdapter(rankingTypeAdapter);

        lv_type.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String item = (String) rankingTypeAdapter.getItem(i);
                String[] type = item.split("-");
                tv_type.setText(type[0]);
                icon_type.setText(type[1]);
                ll_type.setVisibility(View.GONE);
                rankingTypeAdapter.setIndex(i);
                if (i == 0) {
                    index = i;
                    get_rank();
                    query_user_record();
                } else {
                    index = i + 1;
                    get_tag_rank(index);
                    query_user_record(index);
                }
                pullListView.setSelection(1);
            }
        });

        pullListView = (PullListView) findViewById(R.id.pull_listView);
        adapter = new RankingAdapter(getContext());
        pullListView.setAdapter(adapter);
        tv_userName = (TextView) findViewById(R.id.tv_userName);
        tv_prophet = (TextView) findViewById(R.id.tv_prophet);
        tv_ranking = (TextView) findViewById(R.id.tv_ranking);
        iv_ranking = (ImageView) findViewById(R.id.iv_ranking);
        iv_headImg = (RoundedImageView) findViewById(R.id.iv_headImg);
        pullListView.setonRefreshListener(this);
        pullListView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position < 1) return;
                RankDAO item = (RankDAO) adapter.getItem(position - 1);
                if (item.getUser_id() == preferenceUtil.getID()) {//本人
                    ((HomeActivity) getContext()).setTab(3);
                } else {
                    Intent intent = new Intent(getActivity(), PersonalShowActivity.class);
                    intent.putExtra("id", item.getUser_id() + "");
                    startActivity(intent);
                }

            }
        });

        lv_user_search = (ListView) findViewById(R.id.lv_user_search);
        searchAdapter = new SearchAdapter(getContext());
        lv_user_search.setAdapter(searchAdapter);
        et_user_search = (ClearEditText) findViewById(R.id.et_user_search);
        iv_search = (ImageView) findViewById(R.id.iv_search);
        iv_search.setOnClickListener(onClickListener);
        et_user_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (TextUtils.isEmpty(et_user_search.getText().toString().trim())) {
                    lv_user_search.setVisibility(View.INVISIBLE);
                }
            }
        });
        lv_user_search.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                UserRecordDAO item = (UserRecordDAO) searchAdapter.getItem(position);
                if (item.getUserId() == preferenceUtil.getID()) {//本人
                    ((HomeActivity) getContext()).setTab(3);
                } else {
                    Intent intent = new Intent(getActivity(), PersonalShowActivity.class);
                    intent.putExtra("id", item.getUserId() + "");
                    startActivity(intent);
                }
            }
        });

        icon_month_day = (IconMonthAndDayView) findViewById(R.id.icon_month_day);
        tv_month_day = (TextView) findViewById(R.id.tv_month_day);
        ll_ranking_month_day = (LinearLayout) findViewById(R.id.ll_ranking_month_day);
        ll_month_day_ranking = (LinearLayout) findViewById(R.id.ll_month_day_ranking);
        lv_month_day_ranking = (ListView) findViewById(R.id.lv_month_day_ranking);
        rankingMonthAndDayAdapter = new RankingMonthAndDayAdapter(getContext());
        lv_month_day_ranking.setAdapter(rankingMonthAndDayAdapter);
        ll_ranking_month_day.setOnClickListener(onClickListener);
        ll_month_day_ranking.setOnClickListener(onClickListener);

        lv_month_day_ranking.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String item = (String) rankingMonthAndDayAdapter.getItem(i);
                String[] type = item.split("-");
                tv_month_day.setText(type[0]);
                icon_month_day.setText(type[1]);
                ll_month_day_ranking.setVisibility(View.GONE);
                rankingMonthAndDayAdapter.setIndex(i);
//                if (i == 0) {
//                    index = i;
//                    get_rank();
//                    query_user_record();
//                } else {
//                    index = i + 1;
//                    get_tag_rank(index);
//                    query_user_record(index);
//                }
//                pullListView.setSelection(1);
            }
        });
    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.ll_ranking_type:
                    ll_month_day_ranking.setVisibility(View.GONE);
                    if (ll_type.getVisibility() == View.VISIBLE) {
                        ll_type.setVisibility(View.GONE);
                    } else {
                        ll_type.setVisibility(View.VISIBLE);
                    }
                    break;
                case R.id.ll_type:
                    ll_type.setVisibility(View.GONE);
                    break;
                case R.id.ll_ranking_month_day:
                    ll_type.setVisibility(View.GONE);
                    if (ll_month_day_ranking.getVisibility() == View.VISIBLE) {
                        ll_month_day_ranking.setVisibility(View.GONE);
                    } else {
                        ll_month_day_ranking.setVisibility(View.VISIBLE);
                    }
                    break;
                case R.id.ll_month_day_ranking:
                    ll_month_day_ranking.setVisibility(View.GONE);
                    break;
                case R.id.iv_search://查找用户
                    String key = et_user_search.getText().toString().trim();
                    if(TextUtils.isEmpty(key)) return;
                    find_user(key);
                    break;
            }
        }
    };

    @Override
    public void onResume() {
        if (((HomeActivity) getActivity()).getCurrentTab() == 1) {
//			get_rank();
//			query_user_record();
        }
        super.onResume();
    }

    private void initMyRanking(UserRecordDAO userInformationDAO) {
        tv_userName.setText(userInformationDAO.getUser().getName());
        tv_prophet.setText(userInformationDAO.getForeIndexNew() + "");
        if (iv_headImg.getTag() == null || !iv_headImg.getTag().equals(userInformationDAO.getUser().getHeadImage())) {
            ImageLoader.getInstance().displayImage(userInformationDAO.getUser().getHeadImage(), iv_headImg, ImageLoadOptions.getOptions(R.drawable.logo));
            iv_headImg.setTag(userInformationDAO.getUser().getHeadImage());
        }
        tv_ranking.setText(userInformationDAO.getRank() + "  ");
        if (userInformationDAO.getRank() == 0) {
            tv_ranking.setText("-  ");
        }
        if (userInformationDAO.getRank() < userInformationDAO.getLastRank()) {
            iv_ranking.setImageDrawable(getResources().getDrawable(R.drawable.iv_up));
        } else if (userInformationDAO.getRank() > userInformationDAO.getLastRank()) {
            iv_ranking.setImageDrawable(getResources().getDrawable(R.drawable.iv_down));
        } else {
            iv_ranking.setImageDrawable(getResources().getDrawable(R.drawable.ranking_balance_2));
        }
    }

    private void initMyTagRanking(UserTagRecordDAO userInformationDAO) {
        tv_prophet.setText(userInformationDAO.getForeIndexNew() + "");
        tv_ranking.setText(userInformationDAO.getRank() + "  ");
        if (userInformationDAO.getRank() == 0) {
            tv_ranking.setText("-  ");
        }
        if (userInformationDAO.getRank() < userInformationDAO.getLastRank()) {
            iv_ranking.setImageDrawable(getResources().getDrawable(R.drawable.iv_up));
        } else if (userInformationDAO.getRank() > userInformationDAO.getLastRank()) {
            iv_ranking.setImageDrawable(getResources().getDrawable(R.drawable.iv_down));
        } else {
            iv_ranking.setImageDrawable(getResources().getDrawable(R.drawable.ranking_balance_2));
        }
    }

    //获取排名
    private void get_rank() {
        httpResponseUtils.postJson(httpPostParams.getPostParams(
                        PostMethod.get_rank.name(), PostType.common.name(),
                        httpPostParams.get_rank(preferenceUtil.getID() + "", preferenceUtil.getUUid())),
                RankInfo.class,
                new PostCommentResponseListener() {
                    @Override
                    public void requestCompleted(Object response) throws JSONException {
                        pullListView.onRefreshComplete();
                        if (response == null) return;
                        RankInfo rankInfo = (RankInfo) response;
                        adapter.setList(rankInfo.getRanks(), rankInfo.getFollows(), rankInfo.getFriends());
//							pullListView.setSelection(1);
                    }
                });
    }

    //获取分类排名
    private void get_tag_rank(int tag) {
        httpResponseUtils.postJson(httpPostParams.getPostParams(
                        PostMethod.get_tag_rank.name(), PostType.common.name(),
                        httpPostParams.get_tag_rank(preferenceUtil.getID() + "", preferenceUtil.getUUid(), tag)),
                RankInfo.class,
                new PostCommentResponseListener() {
                    @Override
                    public void requestCompleted(Object response) throws JSONException {
                        pullListView.onRefreshComplete();
                        if (response == null) return;
                        RankInfo rankInfo = (RankInfo) response;
                        adapter.setList(rankInfo.getRanks(), rankInfo.getFollows(), rankInfo.getFriends());
                    }
                });
    }

    //查找用户
    private void find_user(String key) {
        progressDialog.progressDialog();
        httpResponseUtils.postJson_1(httpPostParams.getPostParams(
                        PostMethod.find_user.name(), PostType.user_info.name(),
                        httpPostParams.find_user(key)),
                UserSearchInfo.class,
                new PostCommentResponseListener() {
                    @Override
                    public void requestCompleted(Object response) throws JSONException {
                        progressDialog.cancleProgress();
                        if (response == null) return;
                        UserSearchInfo userSearchInfo = (UserSearchInfo) response;
                        if(userSearchInfo.getUserRecords() !=null
                                && userSearchInfo.getUserRecords().size()>0){
                            searchAdapter.setList(userSearchInfo.getUserRecords());
                            lv_user_search.setVisibility(View.VISIBLE);
                        }else{
                            MyToast.getInstance().showToast(getActivity(),"查找失败",0);
                        }
                    }
                });
    }

    //获取个人信息
    private void query_user_record() {
        httpResponseUtils.postJson(httpPostParams.getPostParams(
                        PostMethod.query_user_record.name(), PostType.user_info.name(),
                        httpPostParams.query_user_record(preferenceUtil.getID() + "", preferenceUtil.getUUid())),
                UserInformationInfo.class,
                new PostCommentResponseListener() {
                    @Override
                    public void requestCompleted(Object response) throws JSONException {
                        if (response == null) return;
                        UserInformationInfo userInformationInfo = (UserInformationInfo) response;
                        initMyRanking(userInformationInfo.getUser_record());
                    }
                });
    }

    //个人分类排名
    private void query_user_record(final int tag) {
        httpResponseUtils.postJson(httpPostParams.getPostParams(
                        PostMethod.query_user_tag_record.name(), PostType.user_info.name(),
                        httpPostParams.query_user_tag_record(preferenceUtil.getID() + "", preferenceUtil.getUUid())),
                UserTagRecordInfoDAO.class,
                new PostCommentResponseListener() {
                    @Override
                    public void requestCompleted(Object response) throws JSONException {
                        if (response == null) return;
                        UserTagRecordInfoDAO userTagRecordInfoDAO = (UserTagRecordInfoDAO) response;
                        getTagRecord(userTagRecordInfoDAO.getTag_records(), tag);
                    }
                });
    }

    private void getTagRecord(List<UserTagRecordDAO> list, int tag) {
        if (list == null)
            return;
        for (UserTagRecordDAO userTagRecordDAO : list) {
            if (userTagRecordDAO.getTag() == tag) {
                initMyTagRanking(userTagRecordDAO);
                break;
            }
        }

    }

    @Override
    public void onRefresh(boolean isTop) {
        // TODO Auto-generated method stub
        if (isTop) {
            if (index == 0) {
                query_user_record();
                get_rank();
            } else {
                query_user_record(index);
                get_tag_rank(index);
            }
        }
    }
}

package com.futureinst.home.forecast;

import com.futureinst.R;
import com.futureinst.baseui.BaseFragment;
import com.futureinst.db.PushMessageCacheUtil;
import com.futureinst.home.HomeActivity;
import com.futureinst.home.pushmessage.PushMessageActivity;
import com.futureinst.model.homeeventmodel.QueryEventDAO;
import com.futureinst.model.homeeventmodel.QueryEventInfoDAO;
import com.futureinst.net.HttpPostParams;
import com.futureinst.net.HttpResponseUtils;
import com.futureinst.net.PostCommentResponseListener;
import com.futureinst.net.PostMethod;
import com.futureinst.net.PostType;
import com.futureinst.utils.LoginUtil;
import com.futureinst.utils.Utils;
import com.futureinst.viewpagerindicator.CirclePageIndicator;
import com.futureinst.widget.CustomViewPager;
import com.futureinst.widget.PagerSlidingTabStrip;
import com.futureinst.widget.autoviewpager.AutoScrollViewPager;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.json.JSONException;

import java.util.List;

public class ForecastFragment extends BaseFragment implements OnPageChangeListener{
    private CustomViewPager viewPager;
    private Long recordTime;
    private String[] titles1,titles2;
    private int[] tabTag;
    private PagerSlidingTabStrip slidingTab;
    private ForecastViewPagerAdapter adapter;
    private boolean isStart;

    private  BannerAdapter bannerAdapter;
    private String[] orders;
    private int order = 0;
    private PopupWindow popupWindow;

    //message
    private TextView tv_message_count;
    private ImageView iv_message;
    private PushMessageCacheUtil messageCacheUtil;
    private BroadcastReceiver receiver ;

    //autoviewpager
    private View view_auto_viewpager;
    private AutoScrollViewPager autoScrollViewPager;
    private CirclePageIndicator circlePageIndicator;
    private TextView tv_sort;
    private LinearLayout ll_indictor;

    @Override
    protected void localOnCreate(Bundle savedInstanceState) {
        setContentView(R.layout.fragment_forecast);
        initView();
        getBanner();
        recordTime = System.currentTimeMillis();
        getMessageCount();
        isStart = true;
    }
    private void initView() {
        messageCacheUtil = PushMessageCacheUtil.getInstance(getContext());
        orders = getActivity().getResources().getStringArray(R.array.home_seond_title_order);
        slidingTab = (PagerSlidingTabStrip) findViewById(R.id.id_stickynavlayout_indicator);
        viewPager = (CustomViewPager) findViewById(R.id.id_stickynavlayout_viewpager);
        titles1 = getResources().getStringArray(R.array.home_icon_title_1);
        titles2 = getResources().getStringArray(R.array.home_icon_title_2);
        tabTag = new int[]{0,1,4,2,3,7,6,5,8};
        adapter = new ForecastViewPagerAdapter(getChildFragmentManager(), titles1);
        viewPager.setAdapter(adapter);
        slidingTab.setViewPager(viewPager);
        slidingTab.setIndicatorColor(getResources().getColor(R.color.tab_text_selected));
        slidingTab.setOnTableClickListener(new PagerSlidingTabStrip.OnTableClickListener() {
            @Override
            public void onTableClickListener(int position, View tab) {
                if(position >= 5){
                    if(position == 5){
                        if(adapter.getCount() > 6){
                            adapter.setTitles(titles1);
                            slidingTab.notifyDataSetChanged();
                            viewPager.setCurrentItem(0);
                        }else{
                            adapter.setTitles(titles2);
                            slidingTab.notifyDataSetChanged();
                            viewPager.setCurrentItem(5);
                        }
                    }else{
                        if(viewPager.getCurrentItem() == position-1){
                            Intent intent = new Intent("top");
                            intent.putExtra("position",tabTag[position - 1]);
                            getActivity().sendBroadcast(intent);
                        }else{
                            viewPager.setCurrentItem(position - 1);
                        }
                    }

                }else {
                    if(viewPager.getCurrentItem() == position){
                        Intent intent = new Intent("top");
                        intent.putExtra("position",tabTag[position]);
                        getActivity().sendBroadcast(intent);
                    }else{
                        viewPager.setCurrentItem(position);
                    }
                }

            }
        });

        autoScrollViewPager = (AutoScrollViewPager)findViewById(R.id.auto_viewpager);
        circlePageIndicator = (CirclePageIndicator)findViewById(R.id.top_pager_indicator);
        ll_indictor = (LinearLayout)findViewById(R.id.ll_indictor);

        autoScrollViewPager.setScrollDurationFactor(5);
        autoScrollViewPager.setInterval(4000);
        autoScrollViewPager.setStopScrollWhenTouch(true);
        autoScrollViewPager.setCycle(true);
        autoScrollViewPager.addOnPageChangeListener(this);

        view_auto_viewpager = findViewById(R.id.view_auto_viewpager);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, Utils.getScreenWidth(getContext())*346/750);
        view_auto_viewpager.setLayoutParams(layoutParams);

        bannerAdapter = new BannerAdapter(getChildFragmentManager());
        autoScrollViewPager.setAdapter(bannerAdapter);
        tv_sort = (TextView) findViewById(R.id.tv_sort);
        tv_sort.setOnClickListener(clickListener);

        tv_message_count = (TextView) findViewById(R.id.tv_message_count);
        iv_message = (ImageView) findViewById(R.id.iv_message);
        iv_message.setOnClickListener(clickListener);
        receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                 if (intent.getAction().equals("newPushMessage")) {
                    getMessageCount();
                }
            }
        };
        IntentFilter filter = new IntentFilter();
        filter.addAction("newPushMessage");
        getContext().registerReceiver(receiver, filter);
    }
    // 获取未读消息数量
    private void getMessageCount() {
        int count = messageCacheUtil.getUnReadMessage();
        if (count > 0) {
            tv_message_count.setText(count + "");
            tv_message_count.setVisibility(View.VISIBLE);
        } else {
            tv_message_count.setText("0");
            tv_message_count.setVisibility(View.INVISIBLE);
        }
    }
    View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.tv_sort://筛选
                    if (popupWindow != null && popupWindow.isShowing()) {
                        popupWindow.dismiss();
                        popupWindow = null;
                    } else {
                        getPopupWindow();
                        popupWindow.showAsDropDown(v);
                    }
                    break;
                case R.id.iv_message:
                    if(LoginUtil.judgeIsLogin(getActivity())){
                        startActivity(new Intent(getActivity(), PushMessageActivity.class));
                        tv_message_count.setVisibility(View.INVISIBLE);
                    }
                    break;
            }
        }
    };
    private void initPopuptWindow(){
        View pop_view = LayoutInflater.from(getContext()).inflate(R.layout.view_home_order_popwindow,null,false);
        popupWindow = new PopupWindow(pop_view, RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT,true);
        pop_view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (popupWindow != null && popupWindow.isShowing()) {
                    popupWindow.dismiss();
                    popupWindow = null;
                }
                return true;
            }
        });
        ListView lv_order = (ListView) pop_view.findViewById(R.id.lv_order);
        final OrderAdapter adapter = new OrderAdapter(getContext(),order);
        lv_order.setAdapter(adapter);
        lv_order.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                order = position;
                adapter.setOrder(order);
                Intent intent = new Intent("order");
                intent.putExtra("order",order);
                getActivity().sendBroadcast(intent);
                popupWindow.dismiss();
            }
        });

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

    @Override
    public void onPause() {
        super.onPause();
        if(bannerAdapter!=null){
            autoScrollViewPager.stopAutoScroll();
        }
    }

    @Override
    public void onResume() {
        if (((HomeActivity) getActivity()).getCurrentTab() == 0
                && isStart) {

            getMessageCount();

            if(bannerAdapter!=null){
                autoScrollViewPager.startAutoScroll();
            }
            adapter.getItem(viewPager.getCurrentItem()).setUserVisibleHint(true);
            if(System.currentTimeMillis() - recordTime > 60*60*1000){
                recordTime = System.currentTimeMillis();
                getBanner();
            }
        }
        super.onResume();
    }
    @Override
    public void onPageScrollStateChanged(int arg0) {
    }
    @Override
    public void onPageScrolled(int arg0, float arg1, int arg2) {
//        ((ForecastContainerTypeFragment)adapter.getItem(arg0)).setIsHaveTop(false);
    }
    @Override
    public void onPageSelected(int arg0) {
        for(int i = 0; i<bannerAdapter.getListSize();i++){
            (ll_indictor.getChildAt(i)).setSelected(false);
        }
        if(bannerAdapter.getListSize() > 0)
        (ll_indictor.getChildAt( arg0 % bannerAdapter.getListSize())).setSelected(true);
//        adapter.getItem(arg0).setUserVisibleHint(true);
//        ((ForecastContainerTypeFragment)adapter.getItem(arg0)).setIsHaveTop(true);
    }

    public void getBanner(){
        HttpResponseUtils.getInstace(getActivity()).postJson(HttpPostParams.getInstace().getPostParams(
                        PostMethod.query_top_banner.name(), PostType.event.name(), "{}"),
                QueryEventInfoDAO.class,
                new PostCommentResponseListener() {
                    @Override
                    public void requestCompleted(Object response) throws JSONException {
                        if (response == null) return;
                        QueryEventInfoDAO eventInfoDAO = (QueryEventInfoDAO) response;
                        if (eventInfoDAO.getEvents() != null && eventInfoDAO.getEvents().size() > 0) {
                            bannerAdapter.setList(eventInfoDAO.getEvents());
                            circlePageIndicator.setViewPager(autoScrollViewPager);
                            initIndictor(eventInfoDAO.getEvents());
                            autoScrollViewPager.startAutoScroll(4000);
                        }
                    }
                }
        );
    }

    private void initIndictor(List<QueryEventDAO> list){
        if(null == list) return;
        ll_indictor.removeAllViews();
        for(int i = 0; i< list.size() ;i++){
            PagerIndictorView view = new PagerIndictorView(getContext());
            if(i == 0) {
                view.setSelected(true);
            }else{
                view.setSelected(false);
            }
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(15,15);
            layoutParams.setMargins(2,2,2,2);
            view.setLayoutParams(layoutParams);
            view.setTag(i);
            ll_indictor.addView(view,i);
        }
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        if (receiver != null) {
            getContext().unregisterReceiver(receiver);
        }
    }


}

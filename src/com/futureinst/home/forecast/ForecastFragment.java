package com.futureinst.home.forecast;

import com.futureinst.R;
import com.futureinst.baseui.BaseFragment;
import com.futureinst.model.homeeventmodel.QueryEventInfoDAO;
import com.futureinst.net.HttpPostParams;
import com.futureinst.net.HttpResponseUtils;
import com.futureinst.net.PostCommentResponseListener;
import com.futureinst.net.PostMethod;
import com.futureinst.net.PostType;
import com.futureinst.utils.Utils;
import com.futureinst.viewpagerindicator.CirclePageIndicator;
import com.futureinst.widget.CustomViewPager;
import com.futureinst.widget.PagerSlidingTabStrip;
import com.futureinst.widget.autoviewpager.AutoScrollViewPager;
import com.futureinst.widget.scrollview.HomeLayout;

import android.os.Bundle;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import org.json.JSONException;

public class ForecastFragment extends BaseFragment implements OnPageChangeListener{
    private CustomViewPager viewPager;
//    private HomeLayout home_layout;
    private String[] titles;
    private PagerSlidingTabStrip slidingTab;
    private ForecastViewPagerAdapter adapter;

    private  BannerAdapter bannerAdapter;

    //autoviewpager
    private View view_auto_viewpager;
    private AutoScrollViewPager autoScrollViewPager;
    private CirclePageIndicator circlePageIndicator;

    @Override
    protected void localOnCreate(Bundle savedInstanceState) {
        setContentView(R.layout.fragment_forecast);
        initView();
        getBanner();
    }
    private void initView() {
//        home_layout = (HomeLayout)findViewById(R.id.home_layout);
//        home_layout.setOnScrollListener(this);
        slidingTab = (PagerSlidingTabStrip) findViewById(R.id.id_stickynavlayout_indicator);
        viewPager = (CustomViewPager) findViewById(R.id.id_stickynavlayout_viewpager);
        titles = getResources().getStringArray(R.array.home_icon_title);
        adapter = new ForecastViewPagerAdapter(getChildFragmentManager(), titles);
        viewPager.setAdapter(adapter);
        slidingTab.setViewPager(viewPager);
        slidingTab.setIndicatorColor(getResources().getColor(R.color.tab_text_selected));


        autoScrollViewPager = (AutoScrollViewPager)findViewById(R.id.auto_viewpager);
        circlePageIndicator = (CirclePageIndicator)findViewById(R.id.top_pager_indicator);
        autoScrollViewPager.setScrollDurationFactor(5);
        autoScrollViewPager.setInterval(2000);
        autoScrollViewPager.setStopScrollWhenTouch(true);
        autoScrollViewPager.setCycle(true);

        view_auto_viewpager = findViewById(R.id.view_auto_viewpager);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, Utils.getScreenWidth(getContext())*346/750);
        view_auto_viewpager.setLayoutParams(layoutParams);


    }


    @Override
    public void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
        adapter.getItem(viewPager.getCurrentItem()).setUserVisibleHint(true);
    }
    @Override
    public void onPageScrollStateChanged(int arg0) {
    }
    @Override
    public void onPageScrolled(int arg0, float arg1, int arg2) {
        ((ForecastContainerTypeFragment)adapter.getItem(arg0)).setIsHaveTop(false);
    }
    @Override
    public void onPageSelected(int arg0) {
        adapter.getItem(arg0).setUserVisibleHint(true);
        ((ForecastContainerTypeFragment)adapter.getItem(arg0)).setIsHaveTop(true);
    }

    public void getBanner(){
        HttpResponseUtils.getInstace(getActivity()).postJson(HttpPostParams.getInstace().getPostParams(
                        PostMethod.query_top_banner.name(), PostType.event.name(),"{}"),
                QueryEventInfoDAO.class,
                new PostCommentResponseListener() {
                    @Override
                    public void requestCompleted(Object response) throws JSONException {
                        if(response == null) return;
                        QueryEventInfoDAO eventInfoDAO = (QueryEventInfoDAO)response;
                        bannerAdapter = new BannerAdapter(getChildFragmentManager(),eventInfoDAO.getEvents());
                        autoScrollViewPager.setAdapter(bannerAdapter);
                        circlePageIndicator.setViewPager(autoScrollViewPager);
                        autoScrollViewPager.startAutoScroll(4000);
                    }
                }
        );
    }




}

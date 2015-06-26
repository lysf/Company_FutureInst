package com.futureinst.home;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.futureinst.R;
import com.futureinst.baseui.BaseFragment;
import com.futureinst.home.groupevent.GroupEventActivity;
import com.futureinst.home.title.PrimaryTitleActivity;
import com.futureinst.home.title.SecondTitleActivity;
import com.futureinst.model.homeeventmodel.EventGroupDAO;
import com.futureinst.model.homeeventmodel.EventGroupInfo;
import com.futureinst.model.homeeventmodel.QueryEventInfoDAO;
import com.futureinst.net.HttpPostParams;
import com.futureinst.net.HttpResponseUtils;
import com.futureinst.net.PostCommentResponseListener;
import com.futureinst.net.PostMethod;
import com.futureinst.net.PostType;
import com.futureinst.widget.WheelView.OnItemSelectListener;

@SuppressLint({ "ValidFragment", "HandlerLeak" })
public class HomeTypeContainerFragment extends BaseFragment {
	
	private com.futureinst.widget.WheelView wheelView2;
	private int position = 0;
	private int position_second = 0; 
	private String[] title_1;
	private String[] title_2;
	private String[] orders;
	private TextView tv_title_1,tv_title_2,tv_company;
	private LinearLayout ll_container,ll_back;
	private ViewPager viewPager;
	private int[] picIds;
	private List<EventGroupDAO> groupEvents;
	public  HomeTypeContainerFragment (int position,int position_second){
		this.position = position;
		this.position_second = position_second;
	}
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		picIds = new int[]{R.drawable.a_hp_1_bg_yuan,R.drawable.a_hp_2_bg_yuan,R.drawable.a_hp_3_bg_yuan,R.drawable.a_hp_4_bg_yuan,
				R.drawable.a_hp_5_bg_yuan,R.drawable.a_hp_6_bg_yuan,R.drawable.a_hp_7_bg_yuan,R.drawable.a_hp_8_bg_yuan,R.drawable.a_hp_9_bg_yuan};
		
		title_1 = getActivity().getResources().getStringArray(R.array.home_title);
		title_2 = getActivity().getResources().getStringArray(R.array.home_second_title);
		orders = getActivity().getResources().getStringArray(R.array.home_seond_title_order);
	}
	@Override
	protected void localOnCreate(Bundle savedInstanceState) {
		setContentView(R.layout.fragment_home);
		initView();
		setBackGround(position);
		setTextColor(position);
		getData(position+1+"", orders[position_second]);
		getEventGroupData();
	}
	private void initView() {
		wheelView2 = (com.futureinst.widget.WheelView) findViewById(R.id.wheelView_1);
		wheelView2.setOffset(1);
		 wheelView2.setOnItemSelectListener(new OnItemSelectListener() {
				@Override
				public void onItemSelect(int position) {
					Intent intent = new Intent(getActivity(), GroupEventActivity.class);
					intent.putExtra("groupId", groupEvents.get(position).getId()+"");
					intent.putExtra("groupEventTitle", groupEvents.get(position).getTitle());
					startActivity(intent);
				}
			});
		
		
		ll_back = (LinearLayout)findViewById(R.id.ll_back);
		ll_container = (LinearLayout) findViewById(R.id.ll_container);
		tv_title_1 = (TextView) findViewById(R.id.tv_title_1);
		tv_title_2 = (TextView) findViewById(R.id.tv_title_2);
		tv_company = (TextView) findViewById(R.id.tv_company);
		viewPager = (ViewPager) findViewById(R.id.viewpager);
		viewPager.setClickable(false);
		// 1.设置幕后item的缓存数目  
		viewPager.setOffscreenPageLimit(3);   
		// 2.设置页与页之间的间距  
		viewPager.setPageMargin(40);  
		// 3.将父类的touch事件分发至viewPgaer，否则只能滑动中间的一个view对象  
		ll_container.setOnTouchListener(new View.OnTouchListener() {  
		    @Override  
		    public boolean onTouch(View v, MotionEvent event) {  
		        return viewPager.dispatchTouchEvent(event);  
		    } 
		});
		
		viewPager.setOnPageChangeListener(new MyOnPageChangeListener());
		tv_title_1.setText(title_1[position]);
		tv_title_2.setText(title_2[position_second]);
		tv_title_1.setOnClickListener(clickListener);
		tv_title_2.setOnClickListener(clickListener);
		
	}
	OnClickListener clickListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.tv_title_1://一级标题
				Intent intent_primary = new Intent(getActivity(), PrimaryTitleActivity.class);
				intent_primary.putExtra("primaryTitle", position);
				intent_primary.putExtra("target", position_second);
				startActivity(intent_primary);
				break;
			case R.id.tv_title_2://二级标题
				Intent intent = new Intent(getActivity(), SecondTitleActivity.class);
				intent.putExtra("primaryTitle", position);
				intent.putExtra("target", position_second);
				startActivity(intent);
				break;
			}
			
		}
	};
	//初始化viewpager
	private void initViewPager(QueryEventInfoDAO queryEventInfoDAO){
		ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getChildFragmentManager(),queryEventInfoDAO);
		viewPager.setAdapter(viewPagerAdapter);
		viewPager.setVisibility(View.VISIBLE);
		
	}
	//更新第二条目
	public void upData(int secondTitle){
		this.position_second = secondTitle;
		tv_title_2.setText(title_2[position_second]);
		getData(position+1+"", orders[position_second]);
	}
	//viewpager适配
	private class ViewPagerAdapter extends FragmentStatePagerAdapter{ 
		QueryEventInfoDAO queryEventInfoDAO;
	    public ViewPagerAdapter(FragmentManager fm) {
			super(fm);
		}
	    public ViewPagerAdapter(FragmentManager fm,QueryEventInfoDAO queryEventInfoDAO) {
	    	super(fm);
	    	this.queryEventInfoDAO = queryEventInfoDAO;
	    }
		@Override   
	    public int getCount() {  
	    	if(queryEventInfoDAO.getEvents()!=null || queryEventInfoDAO.getEvents().size()>0){
	    		return queryEventInfoDAO.getEvents().size();
	    	}else{
	    		return 0;
	    	}
	    	
	    }  
	   @Override
	   public void destroyItem(View container, int position, Object object) {
		  ((EventsFragment)getItem(position)).onDestroyView();
	   }
	    @Override  
	    public int getItemPosition(Object object) {  
	        return POSITION_NONE;  
	    }
		@Override
		public Fragment getItem(int arg0) {
			EventsFragment eventsFragment = new EventsFragment();
			Bundle bundle = new Bundle();
			bundle.putSerializable("event", queryEventInfoDAO.getEvents().get(arg0));
			bundle.putLong("time", queryEventInfoDAO.getCurr_time());
			eventsFragment.setArguments(bundle);
			return eventsFragment;
		}  
	}
	 public class MyOnPageChangeListener implements OnPageChangeListener {
	        @Override
	        public void onPageSelected(int position) {
	        	
	        }
	        @Override
	        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
	            // to refresh frameLayout
	            if (ll_container != null) {
	                ll_container.invalidate();
	            }
	        }
	        @Override
	        public void onPageScrollStateChanged(int arg0) {
	        }
	    }
	 //获取事件数据
	 private void getData(String tag,String order){
		 HttpResponseUtils.getInstace(getActivity()).postJson(
				 HttpPostParams.getInstace().getPostParams(PostMethod.query_event.name(), PostType.event.name(), HttpPostParams.getInstace().query_event(tag, order)), 
				 QueryEventInfoDAO.class, 
				 new PostCommentResponseListener() {
					@Override
					public void requestCompleted(Object response) throws JSONException {
						if(response == null) return;
						QueryEventInfoDAO queryEventInfoDAO = (QueryEventInfoDAO) response;
						SystemTimeUtile.getInstance(queryEventInfoDAO.getCurr_time());
						initViewPager(queryEventInfoDAO);
					}
				});
	 }
	 //获取事件组数据
	 private void getEventGroupData(){
		 HttpResponseUtils.getInstace(getActivity()).postJson(
				 HttpPostParams.getInstace().getPostParams(PostMethod.query_event_group.name(), PostType.event_group.name(), HttpPostParams.getInstace().query_event_group("")), 
				 EventGroupInfo.class, 
				 new PostCommentResponseListener() {
					 @Override
					 public void requestCompleted(Object response) throws JSONException {
						 if(response == null) return;
						 EventGroupInfo eventGroupInfo = (EventGroupInfo) response;
						 groupEvents = eventGroupInfo.getGroups();
						 List<String> items = new ArrayList<String>();
						 for(EventGroupDAO dao : eventGroupInfo.getGroups()){
							 items.add(dao.getTitle());
						 }
						 wheelView2.setItems(items);
						
					 }
				 });
	 }
	 private void setTextColor(int position){
		 if(position == 4 || position == 6 || position == 7 || position == 8){
			 tv_title_1.setTextColor(getResources().getColor(R.color.text_color_3));
			 Drawable drawable = getResources().getDrawable(R.drawable.home_down_2);
			 drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
			 tv_title_1.setCompoundDrawables(null, null, drawable, null);
			 tv_title_2.setCompoundDrawables(null, null, drawable, null);
			 tv_title_2.setTextColor(getResources().getColor(R.color.text_color_3));
			 tv_company.setTextColor(getResources().getColor(R.color.text_color_3));
			 drawable = null;
			 wheelView2.setTextColorID(R.color.text_color_3);
		 }
	 }
	//设置背景
	private void setBackGround(int primaryTitle){
		ll_back.setBackgroundResource(picIds[primaryTitle]);
		}
		@Override
	public void onDestroy() {
			super.onDestroy();
			viewPager.removeAllViews();
			viewPager = null;
			ll_back.setBackground(null);
		ll_back =null;
		System.gc();
	}
	
}

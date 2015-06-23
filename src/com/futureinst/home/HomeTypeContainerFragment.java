package com.futureinst.home;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.futureinst.R;
import com.futureinst.baseui.BaseFragment;
import com.futureinst.home.eventdetail.EventDetailActivity;
import com.futureinst.home.eventdetail.MyThread;
import com.futureinst.home.groupevent.GroupEventActivity;
import com.futureinst.home.title.PrimaryTitleActivity;
import com.futureinst.home.title.SecondTitleActivity;
import com.futureinst.model.homeeventmodel.EventGroupDAO;
import com.futureinst.model.homeeventmodel.EventGroupInfo;
import com.futureinst.model.homeeventmodel.QueryEventDAO;
import com.futureinst.model.homeeventmodel.QueryEventInfoDAO;
import com.futureinst.net.HttpPostParams;
import com.futureinst.net.HttpResponseUtils;
import com.futureinst.net.PostCommentResponseListener;
import com.futureinst.net.PostMethod;
import com.futureinst.net.PostType;
import com.futureinst.utils.ImageLoadOptions;
import com.futureinst.utils.LongTimeUtil;
import com.futureinst.widget.WheelView.OnItemSelectListener;
import com.nostra13.universalimageloader.core.ImageLoader;

@SuppressLint({ "ValidFragment", "HandlerLeak" })
public class HomeTypeContainerFragment extends BaseFragment {
	
	private com.futureinst.widget.WheelView wheelView2;
	private Long currentTime;
	private int position = 0;
	private int position_second = 0; 
	private String[] title_1;
	private String[] title_2;
	private String[] orders;
	private TextView tv_title_1,tv_title_2,tv_company;
	private LinearLayout ll_container,ll_back;
	private ViewPager viewPager;
	private List<View> list;
	private ListView lv_groupEvents;
	private int[] picIds;
	private HomeTitleListViewAdapter adapter;
	private List<QueryEventDAO> events;
	private List<EventGroupDAO> groupEvents;
	
	private Handler handler = new Handler(){
		@Override
		public void handleMessage(android.os.Message msg) {
			for(int i = 0;i<events.size();i++){
				if(msg.what == i){
					Long time = msg.getData().getLong("time");
					TextView tv_time = (TextView)msg.obj;
					tv_time.setText(LongTimeUtil.longTimeUtil(time));
				}
			}
		};
	};
	private  HomeTypeContainerFragment (int position,int position_second){
		this.position = position;
		this.position_second = position_second;
	}
	public static HomeTypeContainerFragment getInstance(int position,int position_second){
		return new HomeTypeContainerFragment(position, position_second);
	}
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		picIds = new int[]{R.drawable.a_hp_1_bg_yuan,R.drawable.a_hp_2_bg_yuan,R.drawable.a_hp_3_bg_yuan,R.drawable.a_hp_4_bg_yuan,
				R.drawable.a_hp_5_bg_yuan,R.drawable.a_hp_6_bg_yuan,R.drawable.a_hp_7_bg_yuan,R.drawable.a_hp_8_bg_yuan,R.drawable.a_hp_9_bg_yuan};
		events = new ArrayList<QueryEventDAO>(); 
		
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
		
		lv_groupEvents  = (ListView) findViewById(R.id.lv_groupEvents);
		adapter = new HomeTitleListViewAdapter(getActivity(),position);
		lv_groupEvents.setAdapter(adapter);
		lv_groupEvents.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
			EventGroupDAO item = (EventGroupDAO) adapter.getItem(arg2);
				Intent intent = new Intent(getActivity(), GroupEventActivity.class);
				intent.putExtra("groupId", item.getId()+"");
				intent.putExtra("groupEventTitle", item.getTitle());
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
		list  = new ArrayList<View>();
		
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
	private void initViewPager(List<QueryEventDAO> events){
		list.clear();
		for(int i = 0;i<events.size();i++){
			final QueryEventDAO item = events.get(i);
			View view = LayoutInflater.from(getContext()).inflate(R.layout.item_home_pager, null, false);
			ImageView imageView = (ImageView) view.findViewById(R.id.image);
			TextView tv_time = (TextView) view.findViewById(R.id.tv_time);
			TextView tv_title = (TextView) view.findViewById(R.id.tv_title);
			TextView tv_currPrice = (TextView) view.findViewById(R.id.tv_currPrice);
			TextView tv_priceChange = (TextView) view.findViewById(R.id.tv_priceChange);
			TextView tv_involve = (TextView) view.findViewById(R.id.tv_involve);
			Button btn_forecast = (Button) view.findViewById(R.id.btn_forecast);
			ImageLoader.getInstance().displayImage(item.getImgsrc(), imageView, ImageLoadOptions.getOptions(R.drawable.view_shap));
			tv_title.setText(item.getTitle());
			tv_currPrice.setText(String.format("%.1f", item.getCurrPrice()));
			if(item.getPriceChange() >= 0){
				tv_priceChange.setText("+"+String.format("%.1f", item.getPriceChange()));
				tv_priceChange.setBackgroundColor(getResources().getColor(R.color.gain_red));
			}else{
				tv_priceChange.setText("-"+String.format("%.1f", Math.abs(item.getPriceChange())));
				tv_priceChange.setBackgroundColor(getResources().getColor(R.color.gain_blue));
			}
			tv_involve.setText(item.getInvolve()+"");
			tv_time.setText(item.getStatusStr());
			//倒计时
			if(item.getStatusStr().equals("交易中")){
				 Long time = item.getTradeTime() - currentTime;
				tv_time.setText(LongTimeUtil.longTimeUtil(time));
				new Thread(new MyThread(tv_time, time, i,handler)).start();
				
			}
			imageView.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					//预测
					Intent intent = new Intent(getActivity(), EventDetailActivity.class);
					intent.putExtra("event", item);
					startActivity(intent);
				}
			});
			btn_forecast.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					//预测
					Intent intent = new Intent(getActivity(), EventDetailActivity.class);
					intent.putExtra("event", item);
					startActivity(intent);
				}
			});
			list.add(view);
		}
		ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(list);
		viewPager.setAdapter(viewPagerAdapter);
		viewPager.setVisibility(View.VISIBLE);
	}
	//viewpager适配
	private class ViewPagerAdapter extends PagerAdapter{ 
	    private List<View> list;  
	  
	    public ViewPagerAdapter(List<View> list) {  
	        this.list = list;  
	    }  
	    @Override  
	    public int getCount() {  
	  
	        if (list != null && list.size() > 0) {  
	            return list.size();  
	        } else {  
	            return 0;  
	        }  
	    }  
	    @Override  
	    public boolean isViewFromObject(View arg0, Object arg1) {  
	        return arg0 == arg1;  
	    }  
	    @Override  
	    public void destroyItem(ViewGroup container, int position, Object object) {  
	        container.removeView((View) object);  
	    }  
	    @Override  
	    public Object instantiateItem(ViewGroup container, int position) {  
	        container.addView(list.get(position));  
	        return list.get(position);  
	    }  
	  
	    @Override  
	    public int getItemPosition(Object object) {  
	        return POSITION_NONE;  
	    }  
	}
	 public class MyOnPageChangeListener implements OnPageChangeListener {
	        @Override
	        public void onPageSelected(int position) {}
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
						currentTime = queryEventInfoDAO.getCurr_time();
						events = queryEventInfoDAO.getEvents();
						initViewPager(events);
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
						 adapter.setList(eventGroupInfo.getGroups());
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
	 Bitmap bitmap;
	//设置背景
	private void setBackGround(int primaryTitle){
		 BitmapFactory.Options opts = new BitmapFactory.Options();    
		 opts.inSampleSize = 2;    //这个的值压缩的倍数（2的整数倍），数值越小，压缩率越小，图片越清晰    
		 //返回原图解码之后的bitmap对象    
		  bitmap = BitmapFactory.decodeResource(getContext().getResources(), picIds[primaryTitle], opts);
			ll_back.setBackground(new BitmapDrawable(bitmap));
		}
		@Override
	public void onDestroy() {
			super.onDestroy();
		if(bitmap!=null && !bitmap.isRecycled()){
			bitmap.recycle();
		}
		System.gc();
	}
}

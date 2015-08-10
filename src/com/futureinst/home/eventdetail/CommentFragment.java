package com.futureinst.home.eventdetail;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;

import com.futureinst.R;
import com.futureinst.baseui.BaseFragment;
import com.futureinst.comment.AddCommentActivity;
import com.futureinst.comment.CommentActivity;
import com.futureinst.login.LoginActivity;
import com.futureinst.model.comment.CommentDAO;
import com.futureinst.model.homeeventmodel.EventRelatedInfo;
import com.futureinst.net.HttpPostParams;
import com.futureinst.net.HttpResponseUtils;
import com.futureinst.net.PostCommentResponseListener;
import com.futureinst.net.PostMethod;
import com.futureinst.net.PostType;
import com.futureinst.net.SingleEventScope;
import com.futureinst.roundimageutils.RoundedImageView;
import com.futureinst.sharepreference.SharePreferenceUtil;
import com.futureinst.utils.ImageLoadOptions;
import com.futureinst.utils.TimeUtil;
import com.nostra13.universalimageloader.core.ImageLoader;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class CommentFragment extends BaseFragment {
	private HttpResponseUtils httpResponseUtils;
	private HttpPostParams httpPostParams;
	private LinearLayout ll_comment;
	private String event_id;
	private TextView tv_addComment;
	private TextView tv_moreComment;
	private List<CommentDAO> list;
	private boolean isVisiable;
	private Animation animation;
	private SharePreferenceUtil preferenceUtil;
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		event_id = getArguments().getString("eventId");
		preferenceUtil = SharePreferenceUtil.getInstance(activity);
		httpResponseUtils = HttpResponseUtils.getInstace(activity);
		httpPostParams = HttpPostParams.getInstace();
		animation = AnimationUtils.loadAnimation(activity, R.anim.comment_prise);
	}
	@Override
	protected void localOnCreate(Bundle savedInstanceState) {
		setContentView(R.layout.fragment_comment);
		initView();
		getEvetnRealted();
		isVisiable = true;
	}
	@Override
	public void setUserVisibleHint(boolean isVisibleToUser) {
		super.setUserVisibleHint(isVisibleToUser);
		if(isVisibleToUser && isVisiable){
			getEvetnRealted();   
		}
	}
	private void initView() {
		ll_comment = (LinearLayout) findViewById(R.id.ll_comment);
		tv_addComment = (TextView) findViewById(R.id.tv_addComment);
		tv_addComment.setOnClickListener(clickListener);
		tv_moreComment = (TextView)findViewById(R.id.tv_moreComment);
		tv_moreComment.setOnClickListener(clickListener);
	}

	OnClickListener clickListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.tv_addComment:
				if(judgeIsLogin()){
					Intent intent = new Intent(getActivity(), AddCommentActivity.class);
					intent.putExtra("eventId", event_id);
					startActivity(intent);
				}
				break;
			case R.id.tv_moreComment://更多评论
				Intent intent2 = new Intent(getActivity(), CommentActivity.class);
				intent2.putExtra("eventId", event_id);
				startActivity(intent2);
				break;
			}
		}
	};
	//判断是否已登录
		private boolean judgeIsLogin(){
			if(TextUtils.isEmpty(preferenceUtil.getUUid())){
				Intent intent = new Intent(getActivity(), LoginActivity.class);
				intent.putExtra("login", true);
				startActivity(intent);
				return false;
			}
			return true;
		}
	// 获取事件相关信息
	private void getEvetnRealted() {
		httpResponseUtils.postJson(
				httpPostParams.getPostParams(PostMethod.query_single_event.name(), PostType.event.name(),
						httpPostParams.query_single_event(event_id, SingleEventScope.related.name())),
				EventRelatedInfo.class, new PostCommentResponseListener() {
					@Override
					public void requestCompleted(Object response) throws JSONException {
						if (response == null)
							return;
						EventRelatedInfo eventRelatedInfo = (EventRelatedInfo) response;
						if(eventRelatedInfo.getRelated().getComment().getSize() == 0) return;
						list = eventRelatedInfo.getRelated().getComment().getComments();
						formatCommentList(list);
					}
				});
	}
	private void formatCommentList(List<CommentDAO> list){
		List<CommentDAO> goodList = new ArrayList<CommentDAO>();
		List<CommentDAO> badList = new ArrayList<CommentDAO>();
		List<CommentDAO> commentList = new ArrayList<CommentDAO>();
		
		for(CommentDAO comment : list){
			if(comment.getAttitude() == 1){//看好
				goodList.add(comment);
			}else if(comment.getAttitude() == 2){//不看好
				badList.add(comment);
			}
		}
		for(int i = 0;i < list.size();i++){
			if(goodList.size()> i) commentList.add(goodList.get(i));
			if(badList.size() > i) commentList.add(badList.get(i));
		}
		ll_comment.removeAllViews();
		for(int i = 0; i<commentList.size();i++){
			if(i>3) return;
			CommentDAO item = commentList.get(i);
			View view = LayoutInflater.from(getContext()).inflate(R.layout.view_comment, null);
			View view1 = view.findViewById(R.id.view_1);
			View view2 = view.findViewById(R.id.view_2);
			RoundedImageView imageView = (RoundedImageView) view.findViewById(R.id.headImage);
			TextView tv_name = (TextView) view.findViewById(R.id.tv_name);
			TextView tv_comment = (TextView) view.findViewById(R.id.tv_comment);
			TextView tv_time = (TextView) view.findViewById(R.id.tv_time);
			TextView tv_prise = (TextView) view.findViewById(R.id.tv_prise);
			final TextView tv_prise_add = (TextView) view.findViewById(R.id.tv_prise_add);
			ImageView iv_prise = (ImageView) view.findViewById(R.id.iv_prise);
			String comment = item.getContent();
			int color = 0;
			if(item.getAttitude() == 1){
				comment = "[看好]"+comment;
				color = getResources().getColor(R.color.gain_red);
				view1.setBackgroundColor(color);
				view2.setBackgroundColor(color);
			}else{
				comment = "[不看好]"+comment;
				color = getResources().getColor(R.color.gain_blue);
				view1.setBackgroundColor(color);
				view2.setBackgroundColor(color);
			}
			tv_name.setText(item.getUser().getName());
			ImageLoader.getInstance().displayImage(item.getUser().getHeadImage(), imageView, ImageLoadOptions.getOptions(R.drawable.image_top_default));
			tv_time.setText(TimeUtil.getDescriptionTimeFromTimestamp(item.getCtime()));
			SpannableStringBuilder stringBuilder = new SpannableStringBuilder(comment);
			stringBuilder.setSpan(new ForegroundColorSpan(color), 0, comment.indexOf("]")+1, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
			tv_comment.setText(stringBuilder);
			iv_prise.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					tv_prise_add.setVisibility(View.VISIBLE);
					tv_prise_add.startAnimation(animation);
					new Handler().postDelayed(new  Runnable() {
						public void run() {
							tv_prise_add.setVisibility(View.GONE);
						}
					}, 1000);
				}
			});
			ll_comment.addView(view,i);
		}
	}
}

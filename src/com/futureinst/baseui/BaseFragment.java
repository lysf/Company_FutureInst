package com.futureinst.baseui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.futureinst.R;
import com.futureinst.widget.TitleButton;
import com.umeng.analytics.MobclickAgent;

public abstract class BaseFragment extends Fragment {

	public static final int RESULT_OK = Activity.RESULT_OK;
	private View mContentView;
	private ViewGroup mGroupContent = null;
	private ViewGroup titleBarView;
	private TextView mTextTitle;
	private TitleButton mBackBtn;
	private TitleButton mRightBtn;
	private ImageView mRightImage;
	private ImageView mLeftImage;
	private ProgressBar mProgressBar;
	
	private OnClickListener mClickListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			if (v == mBackBtn) {
				if (!onLeftClick(v))
					getActivity().onBackPressed();
			} else if (v == mRightBtn) {
				onRightClick(v);
			}else if( v == mRightImage){
				onRightImageViewClick(v);
			}else if( v == mLeftImage){
				onLeftImageViewClick(v);
			}
		}
	};
	private Intent mIntent = new Intent();

	/**
	 * @see android.support.v4.app.Fragment#setArguments(android.os.Bundle)
	 */
	@Override
	public void setArguments(Bundle args) {
		super.setArguments(args);
		mIntent.replaceExtras(args);
	}
	/**
	 * 左侧Button点击时回调,如果返回true不会执行onback
	 * 
	 * @param view
	 */
	protected boolean onLeftClick(View view) {
		return false;
	}

	/**
	 * 右侧Button点击时回调
	 * 
	 * @param view
	 */
	protected void onRightClick(View view) {

	}
	/**
	 * 右侧ImageView点击时回调
	 * 
	 * @param view
	 */
	protected void onRightImageViewClick(View view) {

	}
	protected void onLeftImageViewClick(View view) {

	}

	/**
	 * @return TitleBar中间的TextView，获得后可以设置点击事件等相关操作
	 */
	protected TextView getTitleView() {
		titleBarView.setVisibility(View.VISIBLE);
		return mTextTitle;
	}

	/**
	 * @return the titleBarView
	 */
	public ViewGroup getTitleBarView() {
		return titleBarView;
	}

	/**
	 * @return the mBackBtn
	 */
	public TitleButton getLeftButton() {
		mBackBtn.setVisibility(View.VISIBLE);
		return mBackBtn;
	}
	public ImageView getRightImageView(){
		mRightImage.setVisibility(View.VISIBLE);
		return mRightImage;
	}
	public ImageView getLeftImageView(){
		return mLeftImage;
	}
	/**
	 * @return the mRightBtn
	 */
	public TitleButton getRightButton() {
		mRightBtn.setVisibility(View.VISIBLE);
		return mRightBtn;
	}

	/**
	 * 如果不为空将显示TitleBar，否则会隐藏
	 */
	public void setTitle(CharSequence title) {
		titleBarView.setVisibility(TextUtils.isEmpty(title) ? View.GONE : View.VISIBLE);
		mTextTitle.setText(title);
	}

	public void setTitle(int titleId) {
		setTitle(getString(titleId));
	}

	/**
	 * 设置后左边的Button会显示content，可以为空不调用次方法左边的button不会显示
	 * 
	 * @param content
	 */
	public void setLeft(String content) {
		mBackBtn.setVisibility(View.VISIBLE);
		mBackBtn.setText(content);
	}

	/**
	 * 设置后右边的Button会显示content，可以为空不调用次方法右边的button不会显示
	 * 
	 * @param content
	 */
	public void setRight(String content) {
		mRightBtn.setVisibility(View.VISIBLE);
		mRightBtn.setText(content);
		if (content != null) {
			//            mRightBtn.setBackgroundResource(R.drawable.login_bt_selector);
			mRightBtn.getLayoutParams().height = LayoutParams.WRAP_CONTENT;
			//            ((MarginLayoutParams)mRightBtn.getLayoutParams()).rightMargin = getContext().getResources().getDimensionPixelOffset(R.dimen.normal_margin);
			mRightBtn.requestLayout();
		}
	}

	public void setRight(int id) {
		setRight(getContext().getString(id));
	}

	public void setTitleProgressVisibility(boolean show) {
		mProgressBar.setVisibility(show ? View.VISIBLE : View.GONE);
	}

	protected void runOnUiThread(Runnable runnable) {
		new Handler(Looper.getMainLooper()).post(runnable);
	}
	protected Intent getIntent()
	{
		return mIntent;
	}

	//	public void changeTheme() {
	//		mBlFlag = LoginUtils.getThemeFlag(getContext());
	//		if (!mBlFlag) {
	//			getContext().setTheme(R.style.DingDingThemeDefault);
	//		} else {
	//			getContext().setTheme(R.style.DingDingThemeNight);
	//		}
	//	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		if (mContentView  == null) {
			//        	mBlFlag = LoginUtils.getThemeFlag(getContext());
			//        	if (!mBlFlag) {
			//    			getContext().setTheme(R.style.DingDingThemeDefault);
			//    		} else {
			//    			getContext().setTheme(R.style.DingDingThemeNight);
			//    		}
			mContentView = initContentView(R.layout.layout_activity_base, inflater, container);
			initTitleBarView();
			localOnCreate(savedInstanceState);
		}
		return mContentView;
	}

	protected View initContentView(int resid,LayoutInflater inflater, ViewGroup container) {
		return inflater.inflate(resid, container, false);
	}

	@Override
	public void onDestroyView() {
		super.onDestroyView();
		((ViewGroup)mContentView.getParent()).removeView(mContentView);
	}

	@Override
	public void onPause() {
		 MobclickAgent.onPageEnd(getClass().getName()); 
		super.onPause();
		closeInput();
	}

	@Override
	public void onResume() {
		 MobclickAgent.onPageStart(getClass().getName()); 
		super.onResume();
		closeInput();
		
	}

	protected String getPageName() {
		return (String) getTitleView().getText();
	}

	protected BaseFragmentActivity getContext() {
		if (mContentView == null) {
			return BaseApplication.getInstance().getTop();
		}
		return (BaseFragmentActivity) mContentView.getContext();
	}
	protected void initTitleBarView() {
		titleBarView = (ViewGroup) mContentView.findViewById(R.id.title_bar);
		mBackBtn = (TitleButton) mContentView.findViewById(R.id.btn_left_base);
		mBackBtn.setPadding(15, 15, 15, 15);
		mRightBtn = (TitleButton) mContentView.findViewById(R.id.btn_right_base);
		mTextTitle = (TextView) mContentView.findViewById(R.id.txt_title_base);
		mGroupContent = (ViewGroup) mContentView.findViewById(R.id.activity_content);
		mProgressBar = (ProgressBar) mContentView.findViewById(R.id.progressBar1);
		mBackBtn.setOnClickListener(mClickListener);
		mRightBtn.setOnClickListener(mClickListener);
		mRightImage = (ImageView)mContentView.findViewById(R.id.iv_right);
		mRightImage.setOnClickListener(mClickListener);
		mLeftImage = (ImageView)mContentView.findViewById(R.id.iv_left);
		mLeftImage.setOnClickListener(mClickListener);

	}


    protected abstract void localOnCreate(Bundle savedInstanceState);
    public void setContentView(int resid)
    {
    	View view = View.inflate(getContext(), resid, mGroupContent);
        view.setClickable(true);
    }
    public void setContentView(View child)
    {
    	 mGroupContent.addView(child);
    }
    protected View findViewById(int id) {
        return mContentView.findViewById(id);
    }
    
    protected void closeInput() {
    	InputMethodManager inputMethodManager = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
		if (inputMethodManager != null && getContext().getCurrentFocus() != null) {
			inputMethodManager.hideSoftInputFromWindow(getContext().getCurrentFocus()
					.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
		}
	}
    
    public View getContentView(){
    	return mContentView;
    }
}

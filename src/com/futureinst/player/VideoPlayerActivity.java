/*****************************************************************************
 * Email:support@lansongtech.com
 * VideoPlayerActivity.java
 * 
 * 这个程序仅仅是演示版本，仅是功能上的呈现，不保证性能和适用性。如正好满足您的项目，我们深感荣幸。
 * 我们有更专业稳定强大的发行版本，期待和您进一步的合作。
 *  
 *Email: support@lansongtech.com 
 * 
 * 
 * 
 *****************************************************************************/

package com.futureinst.player;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.AnimationSet;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.VideoView;


import com.futureinst.R;
import com.futureinst.player.util.NetStateUtils;
import com.futureinst.player.util.PlayTimeUtil;
import com.futureinst.utils.MyToast;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;


public class VideoPlayerActivity extends Activity {
	private RelativeLayout rl_video;
	private RelativeLayout rl_top;
	private SurfaceView surfaceView;
	private VideoPlayer player;
	private SeekBar seekBar;
	private TextView time_play, time_total;
	private ImageView iv_play,iv_max;
	private AlphaAnimation mHiddenAction;
	private AnimationSet animationSet;
	private TextView title_text;
	private int currentPlayTime;
	private TextView tv_netStatus;

	private ProgressBar progressBar;
	private int netStatus;
	private String url;

	    @Override
	    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
	    protected void onCreate(Bundle savedInstanceState) {
			this.requestWindowFeature(Window.FEATURE_NO_TITLE);//去掉标题栏
			this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);//去掉信息栏
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.view_player);
			initView();
			new Handler().postDelayed(new Runnable() {
				@Override
				public void run() {
//					player.playUrl("http://182.92.183.219/videos/future_events/test.mp4");
					player.playUrl(url);
				}
			}, 300);
//			startAnim();
	    }

	private void initView() {
		url = getIntent().getStringExtra("url");
		animationSet = new AnimationSet(true);
		mHiddenAction = new AlphaAnimation(1, 0);
		mHiddenAction.setDuration(6000);
		animationSet.addAnimation(mHiddenAction);
		rl_video = (RelativeLayout) findViewById(R.id.rl_video);
		tv_netStatus = (TextView) findViewById(R.id.tv_netStatus);
		progressBar = (ProgressBar) findViewById(R.id.progress);
		findViewById(R.id.iv_back).setOnClickListener(clickListener);
		title_text = (TextView) findViewById(R.id.title_text);
		title_text.setText(url);
		rl_top = (RelativeLayout)this.findViewById(R.id.top);
		surfaceView = (SurfaceView)this.findViewById(R.id.surfaceView);
		time_play = (TextView)findViewById(R.id.tv_playTime);
		time_total = (TextView)findViewById(R.id.tv_totalTime);
		iv_play = (ImageView)findViewById(R.id.iv_play);
		iv_max = (ImageView)findViewById(R.id.iv_max);
		iv_play.setOnClickListener(clickListener);
		iv_max.setOnClickListener(clickListener);
		seekBar = (SeekBar)this.findViewById(R.id.seekBar);
		seekBar.setMax(1000);
		player = new VideoPlayer(this,surfaceView, seekBar,progressBar);
		seekBar.setOnSeekBarChangeListener(changeListener);
		rl_video.setOnTouchListener(new View.OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
//				startAnim();
				return false;
			}
		});
		player.mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
			@Override
			public void onCompletion(MediaPlayer mp) {
				finish();
				videoDestroy();
			}
		});
		player.mediaPlayer.setOnErrorListener(new MediaPlayer.OnErrorListener() {
			@Override
			public boolean onError(MediaPlayer mp, int what, int extra) {
				MyToast.getInstance().showToast(VideoPlayerActivity.this, "播放出错！", 0);
				finish();
				videoDestroy();
				return false;
			}
		});

	}
	private void startAnim(){
		rl_top.setVisibility(View.VISIBLE);
		rl_top.startAnimation(animationSet);
		new Handler().postDelayed(new Runnable() {
			public void run() {
				rl_top.setVisibility(View.GONE);
			}
		}, 3900);
	}
	View.OnClickListener clickListener = new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			switch (v.getId()){
				case R.id.iv_back:
					finish();
					videoDestroy();
					break;
				case R.id.iv_play://play或pause

					if (player != null) {
						if (player.mediaPlayer.isPlaying()) {
							iv_play.setImageDrawable(getResources().getDrawable(R.drawable.playsmall_btn));
							player.pause();
						} else {
							iv_play.setImageDrawable(getResources().getDrawable(R.drawable.pausesmall_btn));
							player.play();
						}
					}
					break;
				case R.id.iv_max://全屏或最小
//					if(!isMax){
////                        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
////                        isMax = true;
//					}else{
//						player.stop();
//						setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
//						isMax = false;
//
//					}
					break;
			}
		}
	};
	SeekBar.OnSeekBarChangeListener changeListener = new SeekBar.OnSeekBarChangeListener() {
		int progress;
		@Override
		public void onStopTrackingTouch(SeekBar seekBar) {
			player.mediaPlayer.seekTo(progress);
		}
		@Override
		public void onStartTrackingTouch(SeekBar seekBar) {
		}

		@Override
		public void onProgressChanged(SeekBar seekBar, int progress,
									  boolean fromUser) {
			// TODO Auto-generated method stub
			this.progress = progress * player.mediaPlayer.getDuration()
					/ seekBar.getMax();
			currentPlayTime = this.progress / 1000;
			if(currentPlayTime >=0){
//                MyProgressDialog.cancleProgress();
			}

			time_total.setText(PlayTimeUtil.getPlayTime(player.mediaPlayer.getDuration() / 1000));
			time_play.setText(PlayTimeUtil.getPlayTime(currentPlayTime));
		}
	};
	static String millisToString(long millis) {
	        boolean negative = millis < 0;
	        millis = Math.abs(millis);

	        millis /= 1000;
	        int sec = (int) (millis % 60);
	        millis /= 60;
	        int min = (int) (millis % 60);
	        millis /= 60;
	        int hours = (int) millis;

	        String time;
	        DecimalFormat format = (DecimalFormat) NumberFormat.getInstance(Locale.US);
	        format.applyPattern("00");

	            if (millis > 0)
	                time = (negative ? "-" : "") + hours + ":" + format.format(min) + ":" + format.format(sec);
	            else
	                time = (negative ? "-" : "") + min + ":" + format.format(sec);
	        return time;
	    }

	@Override
	protected void onResume() {
		super.onResume();
		if(!NetStateUtils.isNetworkConnected(this)){
			MyToast.getInstance().showToast(this,"网络错误!",0);
			finish();
			return;
		}
		 netStatus = NetStateUtils.getConnectedType(this);
		if(netStatus == ConnectivityManager.TYPE_MOBILE){//网络
			tv_netStatus.setVisibility(View.VISIBLE);
		}else{
			tv_netStatus.setVisibility(View.GONE);
		}
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		videoDestroy();
	}
	private void videoDestroy(){
		if (player != null) {
			player.setClose(true);
			player.stop();
			player = null;
		}
	}
}

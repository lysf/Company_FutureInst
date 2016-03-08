package com.futureinst.player;

import java.io.IOException;  
import java.util.Timer;  
import java.util.TimerTask;  


import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.media.AudioManager;  
import android.media.MediaPlayer;  
import android.media.MediaPlayer.OnBufferingUpdateListener;  
import android.media.MediaPlayer.OnCompletionListener;  
import android.net.Uri;
import android.os.Handler;  
import android.os.Message;
import android.util.Log;
import android.view.Gravity;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.SeekBar;

import com.futureinst.utils.Utils;

@SuppressLint("HandlerLeak")
public class VideoPlayer implements OnBufferingUpdateListener,  
        OnCompletionListener, MediaPlayer.OnPreparedListener,  
        SurfaceHolder.Callback {
    private ProgressDialog progressDialog;
    private ProgressBar progressBar;
    private int videoWidth;  
    private int videoHeight;  
    public MediaPlayer mediaPlayer;  
    private SurfaceHolder surfaceHolder;  
    private SeekBar skbProgress;
    private int progress;
    private boolean isClose;
    private Timer mTimer=new Timer();
    private SurfaceView surfaceView;
    private Context context;
    @SuppressWarnings("deprecation")
	public VideoPlayer(Context context,SurfaceView surfaceView,SeekBar skbProgress,ProgressBar progressBar)
    {
        this.context = context;
        this.progressBar = progressBar;
        this.skbProgress=skbProgress;
        this.surfaceView = surfaceView;
//        progressDialog = new ProgressDialog(context);
//        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
//        progressDialog.setIndeterminate(false);
//        progressDialog.setCancelable(true);

        mediaPlayer = new MediaPlayer();
        surfaceHolder=surfaceView.getHolder();  
        surfaceHolder.addCallback(this);  
        surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);  
        mTimer.schedule(mTimerTask, 0, 1000);
//        mediaPlayer.setOnVideoSizeChangedListener(mOnVideoSizeChangedListener);
    }  
      
    public boolean isClose() {
		return isClose;
	}

	public void setClose(boolean isClose) {
		this.isClose = isClose;
	}

	/******************************************************* 
     * 通过定时器和Handler来更新进度条 
     ******************************************************/  
    TimerTask mTimerTask = new TimerTask() {  
        @Override  
        public void run() {  
            if(mediaPlayer==null)  
                return;  
            if (mediaPlayer.isPlaying() && skbProgress.isPressed() == false) {
                handleProgress.sendEmptyMessage(0);  
            }  
        }  
    };  
      
    Handler handleProgress = new Handler() {  
        public void handleMessage(Message msg) {
            switch(msg.what){
                case 0:
                if(mediaPlayer==null) return;
                int position = mediaPlayer.getCurrentPosition();
                int duration = mediaPlayer.getDuration();

                if (duration > 0) {
                    long pos = skbProgress.getMax() * position / duration;
                    skbProgress.setProgress((int) pos);
                }
                    break;
            }

        }
    };  
    //*****************************************************  
      
    public void play()  
    {  
        mediaPlayer.start();  
    }  
      
    public void playUrl(String videoUrl)  
    {
        try {
            mediaPlayer.reset();  
            mediaPlayer.setDataSource(videoUrl);  
            mediaPlayer.prepare();//prepare之后自动播放
            //mediaPlayer.start();  
        } catch (IllegalArgumentException e) {  
            // TODO Auto-generated catch block  
            e.printStackTrace();  
        } catch (IllegalStateException e) {  
            // TODO Auto-generated catch block  
            e.printStackTrace();  
        } catch (IOException e) {  
            // TODO Auto-generated catch block  
            e.printStackTrace();  
        }  
    }

    public void setTime(int progress){
        this.progress = progress;
    }
    public int getTime(){
        return this.progress;
    }

    public int getLength(){
        if(mediaPlayer!=null){
            return  mediaPlayer.getDuration();
        }
        return 0;
    }


    public void creatPlay(Context context,Uri uri)  
    {  
    	try {  
    		mediaPlayer.reset(); 
    		mediaPlayer.setDataSource(context, uri);
    		mediaPlayer.prepare();//prepare之后自动播放  
    		//mediaPlayer.start();  
    	} catch (IllegalArgumentException e) {  
    		// TODO Auto-generated catch block  
    		e.printStackTrace();  
    	} catch (IllegalStateException e) {  
    		// TODO Auto-generated catch block  
    		e.printStackTrace();  
    	} catch (IOException e) {  
    		// TODO Auto-generated catch block  
    		e.printStackTrace();  
    	}  
    }  
  
      
    public void pause()  
    {  
        mediaPlayer.pause();  
    }  
      
    public void stop()  
    {  
        if (mediaPlayer != null) {   
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }
    public boolean isPlaying(){
        if(mediaPlayer!=null)
        return mediaPlayer.isPlaying();
        else return false;
    }
      
    @Override  
    public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2, int arg3) {  
        Log.e("mediaPlayer", "surface changed");  
    }  
  
    @Override  
    public void surfaceCreated(SurfaceHolder arg0) {
        try {
            mediaPlayer.setDisplay(surfaceHolder);
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mediaPlayer.setOnBufferingUpdateListener(this);
            mediaPlayer.setOnPreparedListener(this);
        } catch (Exception e) {
            Log.e("mediaPlayer", "error", e);
        }
        Log.e("mediaPlayer", "surface created");
        progressBar.setVisibility(View.VISIBLE);
//            progressDialog.show(context,"","加载中...");

    }  
  
    @Override  
    public void surfaceDestroyed(SurfaceHolder arg0) {  
        Log.e("mediaPlayer", "surface destroyed");  
    }  
  
      
    @Override  
    /**  
     * 通过onPrepared播放  
     */  
    public void onPrepared(MediaPlayer arg0) {  
        videoWidth = mediaPlayer.getVideoWidth();  
        videoHeight = mediaPlayer.getVideoHeight();  
        if (videoHeight != 0 && videoWidth != 0) {
            int screenHeight = Utils.getScreenHeight(context);
            int screenWidth = Utils.getScreenWidth(context);
//            videoHeight = videoHeight > screenHeight ? screenHeight : videoHeight;
//            videoWidth = screenHeight*videoWidth/videoHeight;
//            videoWidth = videoWidth > screenWidth ? screenHeight : videoHeight;
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(screenWidth,screenWidth);
//            params.leftMargin = (screenWidth - videoWidth)/2;
            params.topMargin = (screenHeight -screenWidth)/2;
            surfaceView.setLayoutParams(params);
            arg0.start();  
        }  
        Log.e("mediaPlayer", "onPrepared");  
    }
//    MediaPlayer.OnVideoSizeChangedListener mOnVideoSizeChangedListener = new MediaPlayer.OnVideoSizeChangedListener() {
//        @Override
//        public void onVideoSizeChanged(MediaPlayer mp, int width, int height) {
//            setFitToFillAspectRatio(mp, width, height);
//        }
//    };
//    private void setFitToFillAspectRatio(MediaPlayer mp, int videoWidth, int videoHeight)
//    {
//        if(mp != null)
//        {
//            Integer screenWidth = Utils.getScreenWidth(context);
//            Integer screenHeight = Utils.getScreenWidth(context);
////            android.view.ViewGroup.LayoutParams videoParams = getLayoutParams();
//
//            RelativeLayout.LayoutParams videoParams = new RelativeLayout.LayoutParams(videoWidth,videoHeight);
//            videoParams.leftMargin = (screenWidth - videoWidth)/2;
//            videoParams.topMargin = (screenHeight -videoHeight)/2;
//            surfaceView.setLayoutParams(videoParams);
//            if (videoWidth > videoHeight)
//            {
//                videoParams.width = screenWidth;
//                videoParams.height = screenWidth * videoHeight / videoWidth;
//            }
//            else
//            {
//                videoParams.width = screenHeight * videoWidth / videoHeight;
//                videoParams.height = screenHeight;
//            }
//            surfaceView.setLayoutParams(videoParams);
//        }
//    }


  
    @Override  
    public void onCompletion(MediaPlayer arg0) {  
        // TODO Auto-generated method stub
        arg0.stop();
        arg0.release();
    }

    @Override  
    public void onBufferingUpdate(MediaPlayer arg0, int bufferingProgress) {  
        skbProgress.setSecondaryProgress(bufferingProgress);  
        int currentProgress=skbProgress.getMax()*mediaPlayer.getCurrentPosition()/mediaPlayer.getDuration();  
        Log.e(currentProgress+"% play", bufferingProgress + "% buffer");
        progressBar.setVisibility(View.GONE);
//        progressDialog.cancel();
//        progressDialog.dismiss();
    }  
  
}  

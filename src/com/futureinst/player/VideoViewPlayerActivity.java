/*
 * Copyright (C) 2013 yixia.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.futureinst.player;


import android.app.Activity;
import android.os.Bundle;
import android.view.View;


import com.futureinst.R;
import com.futureinst.utils.MyProgressDialog;

import io.vov.vitamio.MediaPlayer;
import io.vov.vitamio.Vitamio;
import io.vov.vitamio.widget.MediaController;
import io.vov.vitamio.widget.VideoView;

public class VideoViewPlayerActivity extends Activity {

	/**
	 * TODO: Set the path variable to a streaming video URL or a local media file
	 * path.
	 */

	boolean ifUpdate;;
	private MyProgressDialog progressDialog;
	String path = "";

	@Override
	public void onCreate(Bundle icicle) {
		super.onCreate(icicle);

		Vitamio.isInitialized(this);
		
		setContentView(R.layout.videoview);
		progressDialog = MyProgressDialog.getInstance(VideoViewPlayerActivity.this);
		progressDialog.progressDialog();
		path = getIntent().getStringExtra("url");
		findViewById(R.id.iv_back).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		playfunction();	

	}

	
	void playfunction(){

		 VideoView mVideoView;
		mVideoView = (VideoView) findViewById(R.id.surface_view);

//		path="http://dlqncdn.miaopai.com/stream/MVaux41A4lkuWloBbGUGaQ__.mp4";
//		path = "http://182.92.183.219/videos/future_events/test.mp4";
      if (path == "") {
			// Tell the user to provide a media file URL/path.
			return;
		} else {
			/*
			 * Alternatively,for streaming media you can use
			 * mVideoView.setVideoURI(Uri.parse(URLstring));
			 */
		  	mVideoView.setVideoQuality(MediaPlayer.VIDEOQUALITY_HIGH);
			mVideoView.setVideoPath(path);
		  	MediaController controller = new MediaController(this);
			mVideoView.setMediaController(controller);
			mVideoView.requestFocus();
			mVideoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
				@Override
				public void onPrepared(MediaPlayer mediaPlayer) {
					// optional need Vitamio 4.0
					mediaPlayer.setPlaybackSpeed(1.0f);
				}
			});
		  mVideoView.setBufferSize(512 * 1024);
		  mVideoView.setOnBufferingUpdateListener(new MediaPlayer.OnBufferingUpdateListener() {
			  @Override
			  public void onBufferingUpdate(MediaPlayer mp, int percent) {
//				  android.util.Log.i("buffer", "========percent==" + percent);
				  if (percent == 0) {
					  progressDialog.progressDialog();
				  } else if (percent >= 99) {
					  progressDialog.cancleProgress();
				  }
			  }
		  });
		  mVideoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
			  @Override
			  public void onCompletion(MediaPlayer mp) {
				  finish();
			  }
		  });
		}
	}
	
}

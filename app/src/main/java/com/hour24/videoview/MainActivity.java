package com.hour24.videoview;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.VideoView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private VideoView mVideoView;
    private ProgressBar mProgressBar;
    private Button mStart;
    private Button mPause;
    private Button mStop;

    private String mUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initLayout();
        initVariable();
        initEventListener();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.start:
                try {
                    mProgressBar.setVisibility(View.VISIBLE);
//                    mUrl = "http://www.html5videoplayer.net/videos/toystory.mp4";
                    mUrl = "http://clips.vorwaerts-gmbh.de/big_buck_bunny.mp4";
                    mVideoView.setVideoURI(Uri.parse(mUrl));
                    mVideoView.start();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;

            case R.id.pause:
                mVideoView.pause();
                break;

            case R.id.stop:
                mVideoView.stopPlayback();
                mVideoView.resume();
                break;
        }
    }


    private void initLayout() {
        // View
        mVideoView = (VideoView) findViewById(R.id.vidoeview);
        mProgressBar = (ProgressBar) findViewById(R.id.progressbar);
        mStart = (Button) findViewById(R.id.start);
        mPause = (Button) findViewById(R.id.pause);
        mStop = (Button) findViewById(R.id.stop);

        mStart.setOnClickListener(this);
        mPause.setOnClickListener(this);
        mStop.setOnClickListener(this);
    }

    private void initVariable() {
    }

    private void initEventListener() {

        mVideoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                Log.e("sjjang", "onCompletion");
            }
        });


        mVideoView.setOnErrorListener(new MediaPlayer.OnErrorListener() {
            @Override
            public boolean onError(MediaPlayer mp, int what, int extra) {
                Log.e("sjjang", "onError");
                return false;
            }
        });

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            mVideoView.setOnInfoListener(new MediaPlayer.OnInfoListener() {
                @Override
                public boolean onInfo(MediaPlayer mp, int what, int extra) {

                    Log.e("sjjang", "what : " + what + " / extra : " + extra);

                    switch (what) {
                        case MediaPlayer.MEDIA_INFO_BUFFERING_END:
                            mProgressBar.setVisibility(View.GONE);
                            break;
                    }
                    return false;
                }
            });
        } else {
            mVideoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    Log.e("sjjang", "onPrepared");
                    mProgressBar.setVisibility(View.GONE);
                }
            });
        }
    }

//    private void startVideo() {
//
//
//        StreamVideo video = new StreamVideo();
//        video.execute(mUrl);
//    }
//
//    // StreamVideo AsyncTask
//    private class StreamVideo extends AsyncTask<String, Void, Void> {
//
//        @Override
//        protected void onPreExecute() {
//            super.onPreExecute();
//            mProgressBar.setVisibility(View.VISIBLE);
//        }
//
//        @Override
//        protected Void doInBackground(String... voids) {
//            return null;
//        }
//
//        @Override
//        protected void onPostExecute(Void args) {
//
//            try {
//                mVideoView.setVideoURI(Uri.parse(mUrl));
//                mVideoView.start();
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//
//            mProgressBar.setVisibility(View.GONE);
//        }
//    }
}
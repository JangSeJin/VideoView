package com.hour24.videoview;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

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
                mVideoView.start();
                break;

            case R.id.pause:
                mVideoView.pause();
                break;

            case R.id.stop:
                mVideoView.stop();
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
//        mUrl = "http://www.html5videoplayer.net/videos/toystory.mp4";
        mUrl = "http://clips.vorwaerts-gmbh.de/big_buck_bunny.mp4";
        mVideoView.setVideoUrl(mUrl);
    }

    private void initEventListener() {

        mVideoView.setOnVideoViewEventListener(new VideoView.OnVideoViewEventListener() {
            @Override
            public void status(int status) {
                switch (status) {
                    case VideoView.Status.START:
                        break;

                    case VideoView.Status.PAUSE:
                        break;

                    case VideoView.Status.STOP:
                        break;

                    case VideoView.Status.COMPLETE:
                        break;

                    case VideoView.Status.ERROR:
                        break;

                }
            }

            @Override
            public void buffering(int status) {
                switch (status) {
                    case VideoView.Buffering.START:
                        mProgressBar.setVisibility(View.VISIBLE);
                        break;

                    case VideoView.Buffering.END:
                        mProgressBar.setVisibility(View.GONE);
                        break;
                }
            }
        });
    }
}
package com.hour24.videoview;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.transition.Transition;
import android.util.AttributeSet;
import android.util.Log;

/**
 * Created by N16326 on 2018. 7. 19..
 */

public class VideoView extends android.widget.VideoView {

    private OnVideoViewEventListener mListener;

    public VideoView(Context context) {
        super(context);
    }


    public VideoView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public VideoView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

    }

    @Override
    public void start() {
        super.start();
        if (mListener != null) {
            mListener.status(Status.START);
        }
    }


    @Override
    public void pause() {
        super.pause();
        if (mListener != null) {
            mListener.status(Status.PAUSE);
        }
    }

    public void stop() {
        try {
            if (mListener != null) {
                stopPlayback();
                resume();

                mListener.status(Status.STOP);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setVideoUrl(String path) {
        if (path != null) {
            setVideoURI(Uri.parse(path));
        } else {
            if (mListener != null) {
                mListener.status(Status.ERROR);
            }
        }
    }

    // Listener
    private MediaPlayer.OnInfoListener mInfoListener = new MediaPlayer.OnInfoListener() {
        @Override
        public boolean onInfo(MediaPlayer mp, int what, int extra) {
            Log.e("sjjang", "what : " + what);
            switch (what) {
                case MediaPlayer.MEDIA_INFO_BUFFERING_START:
                    if (mListener != null) {
                        mListener.buffering(Buffering.START);
                    }
                    break;

                case MediaPlayer.MEDIA_INFO_BUFFERING_END:
                    if (mListener != null) {
                        mListener.buffering(Buffering.END);
                    }
                    break;

            }

            return false;
        }
    };

    private MediaPlayer.OnPreparedListener mPreparedListener = new MediaPlayer.OnPreparedListener() {

        @Override
        public void onPrepared(MediaPlayer mp) {

            if (mListener != null) {
                mListener.buffering(Buffering.END);
            }
        }
    };

    private MediaPlayer.OnCompletionListener mCompletionListener = new MediaPlayer.OnCompletionListener() {

        @Override
        public void onCompletion(MediaPlayer mp) {
            if (mListener != null) {
                mListener.status(Status.COMPLETE);
            }

            stop();
        }
    };

    private MediaPlayer.OnErrorListener mErrorListener = new MediaPlayer.OnErrorListener() {

        @Override
        public boolean onError(MediaPlayer mp, int what, int extra) {
            if (mListener != null) {
                mListener.status(Status.ERROR);
            }
            return false;
        }
    };

    // 상태값
    public @interface Status {
        int START = 0; // 시작
        int PAUSE = 1; // 중지
        int STOP = 2; // 정지
        int COMPLETE = 3; // 완료
        int ERROR = 4; // 에러
    }

    // 버퍼링값
    public @interface Buffering {
        int START = 0; // 버퍼링 시작
        int END = 1; // 버퍼링 끝
    }

    public interface OnVideoViewEventListener {

        void status(int status); // start, pause, stop

        void buffering(int status);

    }

    public void setOnVideoViewEventListener(OnVideoViewEventListener listener) {
        try {

            this.mListener = listener;

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                setOnInfoListener(mInfoListener);
            } else {
                setOnPreparedListener(mPreparedListener);
            }

            setOnCompletionListener(mCompletionListener);
            setOnErrorListener(mErrorListener);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

package com.chappie.ladang.helper;

import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;

public abstract class CountDownTimerPausable {
    /**
     * Millis since epoch when alarm should stop.
     */
    private long mMillisInFuture;

    /**
     * The interval in millis that the user receives callbacks
     */
    private long mCountdownInterval;

    private long mStopTimeInFuture;

    private long mPauseTime;

    private boolean mCancelled = false;

    private boolean mPaused = false;

    /**
     * @param millisInFuture The number of millis in the future from the call
     *   to {@link #start()} until the countdown is done and {@link #onFinish()}
     *   is called.
     * @param countDownInterval The interval along the way to receive
     *   {@link #onTick(long)} callbacks.
     */
    public CountDownTimerPausable(long millisInFuture, long countDownInterval) {
        mMillisInFuture = millisInFuture;
        mCountdownInterval = countDownInterval;
    }

    /**
     * Cancel the countdown.
     *
     * Do not call it from inside CountDownTimer threads
     */
    public final void cancel() {
        mHandler.removeMessages(MSG);
        mCancelled = true;
    }

    /**
     * Start the countdown.
     * @return
     */
    public synchronized final CountDownTimerPausable start() {
        if (mMillisInFuture <= 0) {
            onFinish();
            return this;
        }
        mStopTimeInFuture = SystemClock.elapsedRealtime() + mMillisInFuture;
        mHandler.sendMessage(mHandler.obtainMessage(MSG));
        mCancelled = false;
        mPaused = false;
        return this;
    }

    /**
     * @param millisInFuture The number of millis in the future from the call
     *   to {@link #start()} until the countdown is done and {@link #onFinish()}
     *   is called.
     * @param countDownInterval The interval along the way to receive
     *   {@link #onTick(long)} callbacks.
     * Restart the countdown
     * @return
     */
    public synchronized final CountDownTimerPausable restart(long millisInFuture, long countDownInterval){
        mMillisInFuture = millisInFuture;
        mCountdownInterval = countDownInterval;
        if (mMillisInFuture <= 0) {
            onFinish();
            return this;
        }
        mStopTimeInFuture = SystemClock.elapsedRealtime() + mMillisInFuture;
        mHandler.sendMessage(mHandler.obtainMessage(MSG));
        mCancelled = false;
        mPaused = false;
        return this;
    }

    /**
     * Pause the countdown.
     */
    public long pause() {
        mPauseTime = mStopTimeInFuture - SystemClock.elapsedRealtime();
        mPaused = true;
        return mPauseTime;
    }

    /**
     * Resume the countdown.
     */
    public long resume() {
        mStopTimeInFuture = mPauseTime + SystemClock.elapsedRealtime();
        mPaused = false;
        mHandler.sendMessage(mHandler.obtainMessage(MSG));
        return mPauseTime;
    }

    /**
     * Return Paused
     * **/
    public boolean isPaused(){
        return mPaused;
    }
    /**
     * Callback fired on regular interval.
     * @param millisUntilFinished The amount of time until finished.
     */
    public abstract void onTick(long millisUntilFinished);

    /**
     * Callback fired when the time is up.
     */
    public abstract void onFinish();


    private static final int MSG = 1;


    // handles counting down
    private Handler mHandler;

    {
        mHandler = new Handler() {

            @Override
            public void handleMessage(Message msg) {

                synchronized (CountDownTimerPausable.this) {
                    if (!mPaused) {
                        final long millisLeft = mStopTimeInFuture - SystemClock.elapsedRealtime();

                        if (millisLeft <= 0) {
                            onFinish();
                        } else if (millisLeft < mCountdownInterval) {
                            // no tick, just delay until done
                            sendMessageDelayed(obtainMessage(MSG), millisLeft);
                        } else {
                            long lastTickStart = SystemClock.elapsedRealtime();
                            onTick(millisLeft);

                            // take into account user's onTick taking time to execute
                            long delay = lastTickStart + mCountdownInterval - SystemClock.elapsedRealtime();

                            // special case: user's onTick took more than interval to
                            // complete, skip to next interval
                            while (delay < 0) delay += mCountdownInterval;

                            if (!mCancelled) {
                                sendMessageDelayed(obtainMessage(MSG), delay);
                            }
                        }
                    }
                }
            }
        };
    }
}

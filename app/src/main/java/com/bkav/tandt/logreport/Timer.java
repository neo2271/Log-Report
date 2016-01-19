package com.bkav.tandt.logreport;

import android.os.Handler;

/**
 * Created by tandt on 1/19/16.
 */
public class Timer {
    public final int REFRESH_RATE = 100;
    public Handler mHandler = new Handler();
    public long startTime;
    public long elapsedTime;
    public String hours, minutes, seconds, milliseconds;
    public long secs, mins, hrs, msecs;
    public boolean stopped = false;

    private Runnable startTimer = new Runnable() {
        public void run() {
            elapsedTime = System.currentTimeMillis() - startTime;
            updateTimer(elapsedTime);
            mHandler.postDelayed(this, REFRESH_RATE);
        }
    };

    public void startTimer() {
        if (stopped) {
            startTime = System.currentTimeMillis() - elapsedTime;
        } else {
            startTime = System.currentTimeMillis();
        }
        mHandler.removeCallbacks(startTimer);
        mHandler.postDelayed(startTimer, 0);
    }

    public void stopTimer() {
        mHandler.removeCallbacks(startTimer);
        stopped = true;
    }

    public void resetTimer() {
        stopped = false;
        MainActivity.tvTimerLabel.setText(null);
        MainActivity.tvTimer.setText(null);
        MainActivity.tvTimerMs.setText(null);
    }

    private void updateTimer(float time) {
        secs = (long) (time / 1000);
        mins = (long) ((time / 1000) / 60);
        hrs = (long) (((time / 1000) / 60) / 60);

		/* Convert the seconds to String
         * and format to ensure it has
		 * a leading zero when required
		 */
        secs = secs % 60;
        seconds = String.valueOf(secs);
        if (secs == 0) {
            seconds = "00";
        }
        if (secs < 10 && secs > 0) {
            seconds = "0" + seconds;
        }

		/* Convert the minutes to String and format the String */

        mins = mins % 60;
        minutes = String.valueOf(mins);
        if (mins == 0) {
            minutes = "00";
        }
        if (mins < 10 && mins > 0) {
            minutes = "0" + minutes;
        }

    	/* Convert the hours to String and format the String */

        hours = String.valueOf(hrs);
        if (hrs == 0) {
            hours = "00";
        }
        if (hrs < 10 && hrs > 0) {
            hours = "0" + hours;
        }

    	/* Although we are not using milliseconds on the timer in this example
         * I included the code in the event that you wanted to include it on your own
    	 */
        milliseconds = String.valueOf((long) time);
        if (milliseconds.length() == 2) {
            milliseconds = "0" + milliseconds;
        }
        if (milliseconds.length() <= 1) {
            milliseconds = "00";
        }
        milliseconds = milliseconds.substring(milliseconds.length() - 3, milliseconds.length() - 2);

		/* Setting the timer text to the elapsed time */
        MainActivity.tvTimerLabel.setText("Timer: ");
        MainActivity.tvTimer.setText(hours + ":" + minutes + ":" + seconds);
        MainActivity.tvTimerMs.setText("." + milliseconds);
    }

}

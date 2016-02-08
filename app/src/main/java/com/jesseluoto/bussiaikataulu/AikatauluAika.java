package com.jesseluoto.bussiaikataulu;

import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class AikatauluAika {

    private int departTimeMinSinceMidnight;
    private String code;

    public AikatauluAika(int departTimeFunnyTimestamp, String code) {
        this.departTimeMinSinceMidnight = (int) ((departTimeFunnyTimestamp % 100) + (Math.floor(departTimeFunnyTimestamp/100) * 60));
        this.code = code;

        //Log.i("jesse", String.format("time: %d, currTime: %d so diff is %d", this.departTimeMinSinceMidnight, this.getCurrMinutesSinceMindnight(), this.getTimeDiffMinutes(departTimeMinSinceMidnight)));
    }

    public String getRelativeDepartTime() {
        return String.format("%dmin", getTimeDiffMinutes(departTimeMinSinceMidnight));
    }

    public String getCode() {
        return code;
    }

    private int getTimeDiffMinutes(int time) {
        return (time + (60 * 24) - getCurrMinutesSinceMindnight()) % (60 * 24);
    }

    private int getCurrMinutesSinceMindnight() {
        return Calendar.getInstance().get(Calendar.HOUR_OF_DAY) * 60 + Calendar.getInstance().get(Calendar.MINUTE);
    }
}

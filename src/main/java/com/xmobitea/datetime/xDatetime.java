package com.xmobitea.datetime;

import java.util.Date;

public final class xDatetime {
    public final static int ONE_SECOND_MILLISECOND = 1 * 1000;
    public final static int ONE_MINUTE_MILLISECOND = ONE_SECOND_MILLISECOND * 60;
    public final static int ONE_HOUR_MILLISECOND = ONE_MINUTE_MILLISECOND * 60;
    public final static int ONE_DAY_MILLISECOND = ONE_HOUR_MILLISECOND * 24;
    public final static int ONE_WEEK_MILLISECOND = ONE_DAY_MILLISECOND * 7;
    public final static int ONE_MONTH_MILLISECOND = ONE_DAY_MILLISECOND * 30;
    public final static int ONE_YEAR_MILLISECOND = ONE_DAY_MILLISECOND * 365;

    public static long getCurrentMilliseconds() {
        return System.currentTimeMillis();
    }

    public static long getMilliseconds(Date date) {
        return date.getTime();
    }

    public static Date getDateAtMilliseconds(long milliseconds) {
        return new Date(milliseconds);
    }

    public static long getToday0hMilliseconds(long milliseconds) {
        var now = getDateAtMilliseconds(getCurrentMilliseconds());
        var today0h = new Date(now.getYear(), now.getMonth(), now.getDate());

        return getMilliseconds(today0h);
    }
}

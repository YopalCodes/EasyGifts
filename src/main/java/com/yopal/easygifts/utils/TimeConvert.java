package com.yopal.easygifts.utils;

import org.bukkit.ChatColor;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class TimeConvert {
    public static String getFutureDate(int days, int hours, int minutes) {
        Calendar c = Calendar.getInstance();

        TimeZone timeZone = TimeZone.getTimeZone("UTC");
        Date date = Calendar.getInstance(timeZone).getTime();
        c.setTime(date);

        c.add(Calendar.DAY_OF_MONTH, days);
        c.add(Calendar.HOUR, hours);
        c.add(Calendar.MINUTE, minutes);

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat();
        return ChatColor.GRAY + simpleDateFormat.format(c.getTime()) + " (UTC)";
    }

    public static Date getDateFromFormat(String string) throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat();
        return simpleDateFormat.parse(string.replace(" (UTC)", ""));
    }
}

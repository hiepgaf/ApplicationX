package com.hieptran.applicationx.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Point;
import android.os.Build;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by hiepth on 26/04/2016.
 */
public class Utils {
    public static int getScreenWidth(Context context) {
        int width = 0;
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            Point size = new Point();
            display.getSize(size);
            width = size.x;
        } else {
            width = display.getWidth();  // deprecated
        }

        return width;
    }
    public static String getTimeString(String fromdate) {

        long then=0;
        //  then = fromdate.getTime();
        DateFormat newDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);

        try {
            Date start = newDateFormat.parse(fromdate);
            then = start.getTime();


        } catch (ParseException e) {
            e.printStackTrace();
        }
        Date date = new Date(then);

        StringBuffer dateStr = new StringBuffer();

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        Calendar now = Calendar.getInstance();

        int days = daysBetween(calendar.getTime(), now.getTime());
        int minutes = hoursBetween(calendar.getTime(), now.getTime());
        int hours = minutes / 60;
        if (days == 0) {

            int second = minuteBetween(calendar.getTime(), now.getTime());
            if (minutes > 60) {

                if (hours >= 1 && hours <= 24) {
                    dateStr.append(hours).append(" giờ trước");
                }

            } else {

                if (second <= 10) {
                    dateStr.append(" Vừa xong");
                } else if (second > 10 && second <= 30) {
                    dateStr.append(" Vài giây trước");
                } else if (second > 30 && second <= 60) {
                    dateStr.append(second).append(" giây trước");
                } else if (second >= 60 && minutes <= 60) {
                    dateStr.append(minutes).append(" phút trước");
                }
            }
        } else

        if (hours > 24 && days <= 7) {
            dateStr.append(days).append("ngày trước");
        } else {
            dateStr.append(newDateFormat.format(date));
        }
Log.d("HiepTHb","ket qua time "+dateStr.toString());
        return dateStr.toString();
    }

    public static int minuteBetween(Date d1, Date d2) {
        return (int) ((d2.getTime() - d1.getTime()) / DateUtils.SECOND_IN_MILLIS);
    }

    public static int hoursBetween(Date d1, Date d2) {
        return (int) ((d2.getTime() - d1.getTime()) / DateUtils.MINUTE_IN_MILLIS);
    }

    public static int daysBetween(Date d1, Date d2) {
        return (int) ((d2.getTime() - d1.getTime()) / DateUtils.DAY_IN_MILLIS);
    }

    public static Bitmap loadBitmapFromView(View mView) {
        Bitmap b = Bitmap.createBitmap(
                mView.getWidth(),
                mView.getHeight(),
                Bitmap.Config.ARGB_8888);
Log.d("HiepTHb","Check loadbitmap from View : w "+ mView.getWidth()+ " h "+mView.getHeight());
        Canvas c = new Canvas(b);

        // With the following, screen blinks
        //v.layout(0, 0, v.getLayoutParams().width, v.getLayoutParams().height);

        mView.draw(c);

        return b;
    }

    public static String removeTags(String in)
    {
        int index=0;
        int index2=0;
        while(index!=-1)
        {
            index = in.indexOf("<");
            index2 = in.indexOf(">", index);
            if(index!=-1 && index2!=-1)
            {
                in = in.substring(0, index).concat(in.substring(index2+1, in.length()));
            }
        }
        return in;
    }


}

package com.timore.appegroup;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.SparseIntArray;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by ZEID on 5/19/2015.
 */

class MyNotificationMangaer {
    public static final String TAG = "com.parse.ParseNotificationManager";
    private final Object lock = new Object();
    private final AtomicInteger notificationCount = new AtomicInteger(0);
    private volatile boolean shouldShowNotifications = true;
    private SparseIntArray iconIds = new SparseIntArray();

    MyNotificationMangaer() {
    }

    public static MyNotificationMangaer getInstance() {
        return Singleton.INSTANCE;
    }

    public boolean getShouldShowNotifications() {
        return this.shouldShowNotifications;
    }

    public void setShouldShowNotifications(boolean show) {
        this.shouldShowNotifications = show;
    }

    public int getNotificationCount() {
        return this.notificationCount.get();
    }


    public Notification createNotification(Context context, String title, String body,
                                           Class<? extends Activity> clazz, int iconId, Bundle extras) {
        Notification notification = null;


        if (iconId == 0) {
//            Parse.logE("com.parse.ParseNotificationManager", "Could not find a valid icon id for this app. This is required to create a Notification object to show in the status bar. Make sure that the <application> in in your Manifest.xml has a valid android:icon attribute.");
        } else if (context != null && title != null && body != null && clazz != null && iconId != 0) {
            long when = System.currentTimeMillis();
            ComponentName name = new ComponentName(context, clazz);
            Intent intent = new Intent();
            intent.setComponent(name);
            intent.setFlags(268435456);
            if (extras != null) {
                intent.putExtras(extras);
            }

            PendingIntent contentIntent = PendingIntent.getActivity(context, (int) when, intent, 0);
            notification = new Notification(iconId, body, when);
            notification.flags |= 16;
            notification.defaults |= -1;
            notification.setLatestEventInfo(context, title, body, contentIntent);
        } else {
//            Parse.logE("com.parse.ParseNotificationManager", "Must specify non-null context, title, body, and activity class to show notification.");
        }

        return notification;
    }

    public void showNotification(Context context, Notification notification) {
        if (context != null && notification != null) {
            this.notificationCount.incrementAndGet();
            if (this.shouldShowNotifications && context != null && notification != null) {
                NotificationManager nm = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
                int notificationId = (int) System.currentTimeMillis();

                try {
                    nm.notify(notificationId, notification);
                } catch (SecurityException var6) {
                    notification.defaults = 5;
                    nm.notify(notificationId, notification);
                }
            }
        }

    }

    public void showNotification(Context context, String title, String body, Class<? extends Activity> cls, int iconId, Bundle extras) {
        this.showNotification(context, this.createNotification(context, title, body, cls, iconId, extras));
    }

    public static class Singleton {
        private static final MyNotificationMangaer INSTANCE = new MyNotificationMangaer();

        public Singleton() {
        }
    }
}

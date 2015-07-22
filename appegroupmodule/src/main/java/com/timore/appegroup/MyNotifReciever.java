package com.timore.appegroup;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.RemoteViews;

import com.parse.ParseAnalytics;
import com.parse.ParsePushBroadcastReceiver;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Random;


/**
 * Created by ZEID on 5/19/2015.
 */
public class MyNotifReciever extends ParsePushBroadcastReceiver {
    public static String IMAGE_URL;
    private JSONObject pushData;
    private String extraString;
    public static Class<?> callbackClass;


    @Override
    public void onReceive(Context context, Intent intent) {
//        super.onReceive(context, intent);
        System.err.println("##############################################");
        System.err.println("##############################################");
        System.err.println("###################RECIEVE NOTIFIACATION##################");
        System.err.println("##############################################");
        String intentAction = intent.getAction();
        if ("com.parse.push.intent.RECEIVE".equals(intentAction)) {
            try {
                extraString=intent.getStringExtra("com.parse.Data");
                pushData = new JSONObject(extraString);
            } catch (JSONException var7) {
                Log.e("ParsePushReceiver", "Unexpected JSONException when receiving push data: " + var7);
            }
            Log.e("NOTIFICATION JSON", "====" + pushData);
            String type = "";
            try {
                type = pushData.getString("action");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            if (type.equals("image")) {
                Log.e("notif reciever", "SHOW IMAGE");
                onImgPushReceive(context, intent);
            } else {
                onPushReceive(context, intent);
            }

        } else if ("com.parse.push.intent.DELETE".equals(intentAction)) {
            this.onPushDismiss(context, intent);
        } else if ("com.parse.push.intent.OPEN".equals(intentAction)) {
            this.onPushOpen(context, intent);
        }
    }

    protected Class<? extends Activity> getActivity(Context context, Intent intent) {

        Class cls = null;

        try {
            cls = Class.forName(callbackClass.getName());
        } catch (ClassNotFoundException var8) {

        }

        return cls;

    }

    protected void onImgPushReceive(Context context, Intent intent) {


        String action = null;
        if (pushData != null) {
            action = pushData.optString("action", (String) null);
        }

        if (action != null) {
            Bundle notification = intent.getExtras();
            Intent broadcastIntent = new Intent();
            broadcastIntent.putExtras(notification);
            broadcastIntent.setAction(action);
            broadcastIntent.setPackage(context.getPackageName());
            context.sendBroadcast(broadcastIntent);
        }


        RemoteViews expandedView = new RemoteViews(context.getPackageName(),
                R.layout.layout_notif);

        createNotification(context, expandedView,intent);


    }

    public void createNotification(final Context context, final RemoteViews view,Intent e) {
        // Prepare intent which is triggered if the notification is selected
        final int notificationId = (int) System.currentTimeMillis();
        Intent resultIntent = new Intent(context, callbackClass);

        Bundle extras = e.getExtras();
        Random random = new Random();
        int contentIntentRequestCode = random.nextInt();
        int deleteIntentRequestCode = random.nextInt();
        String packageName = context.getPackageName();
        Intent contentIntent = new Intent("com.parse.push.intent.OPEN");
        contentIntent.putExtras(extras);
        contentIntent.setPackage(packageName);
        Intent deleteIntent = new Intent("com.parse.push.intent.DELETE");
        deleteIntent.putExtras(extras);
        deleteIntent.setPackage(packageName);
        final PendingIntent pIntent = PendingIntent.getBroadcast(context, contentIntentRequestCode,
                contentIntent, 0);

//        final PendingIntent pIntent = PendingIntent.getActivity(context, 0, resultIntent, 0);
        // Build notification Actions are just fake
       if (pushData != null) {
            Log.e("IMAGE URL", ":" + pushData.optString("alert", ""));
            view.setTextViewText(R.id.notif_title, pushData.optString("title", context.getString(R.string.app_name)));

            new AsyncTask<Void, Void, Bitmap>() {
                Notification noti = null;

                @Override
                protected Bitmap doInBackground(Void... params) {
                    Bitmap remote_picture = null;
                    System.err.println("IMAGE URL------------------------");
                    System.err.println("***"+  pushData.optString("alert", ""));
                    try {
                        remote_picture = BitmapFactory.decodeStream((InputStream) new URL(IMAGE_URL+
                               pushData.optString("alert", "")).getContent());

                    } catch (IOException e) {
                        e.printStackTrace();

                    }
                    return remote_picture;
                }

                @Override
                protected void onPostExecute(Bitmap b) {
                    super.onPostExecute(b);
                    Log.e("onPostExecute", "Imageloaded");
                    view.setImageViewBitmap(R.id.notif_image, b);

                    if (Build.VERSION.SDK_INT < 16) {
                        noti = new Notification.Builder(context)
                                .setContentIntent(pIntent).setContent(view).setOngoing(false)
                                .setSmallIcon(R.drawable.ic_launcher)
                                .getNotification();

                    } else {
                        noti = new Notification.Builder(context)
                                .setSmallIcon(R.drawable.ic_launcher)
                                .setContentIntent(pIntent)
                                .build();
                        noti.bigContentView = view;
                    }


                    NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
                    noti.flags |= Notification.FLAG_AUTO_CANCEL;
                    notificationManager.notify(notificationId, noti);

                }
            }.execute();

        }
    }



    protected void onPushReceive(Context context, Intent intent) {
        JSONObject pushData = null;

        try {
            pushData = new JSONObject(intent.getStringExtra("com.parse.Data"));
        } catch (JSONException var7) {
            Log.e("ParsePushReceiver", "Unexpected JSONException when receiving push data: " + var7);
        }

        String action = null;
        if (pushData != null) {
            action = pushData.optString("action", (String) null);
        }

        if (action != null) {
            Bundle notification = intent.getExtras();
            Intent broadcastIntent = new Intent();
            broadcastIntent.putExtras(notification);
            broadcastIntent.setAction(action);
            broadcastIntent.setPackage(context.getPackageName());
            context.sendBroadcast(broadcastIntent);
        }

        Notification notification1 = this.getNotification(context, intent);
        if (notification1 != null) {
            MyNotificationMangaer.getInstance().showNotification(context, notification1);
        }

    }

    protected void onPushDismiss(Context context, Intent intent) {
    }

    protected void onPushOpen(Context context, Intent intent) {
        ParseAnalytics.trackAppOpened(intent);
        String uriString = null;

        try {
            JSONObject cls = new JSONObject(intent.getStringExtra("com.parse.Data"));
            uriString = cls.optString("uri");
        } catch (JSONException var6) {
            Log.e("ParsePushReceiver", "Unexpected JSONException when receiving push data: " + var6);
        }

        Class cls1 = this.getActivity(context, intent);
        Intent activityIntent = null;

        activityIntent = new Intent(context, cls1);


        activityIntent.putExtras(intent.getExtras());

        activityIntent.addFlags(268435456);
        activityIntent.addFlags(67108864);
        context.startActivity(activityIntent);


    }


    protected Bitmap getLargeIcon(Context context, Intent intent) {
        return null;
    }

    private JSONObject getPushData(Intent intent) {
        try {
            return new JSONObject(intent.getStringExtra("com.parse.Data"));
        } catch (JSONException var3) {
            Log.e("ParsePushReceiver", "Unexpected JSONException when receiving push data: " + var3);
            return null;
        }
    }


}

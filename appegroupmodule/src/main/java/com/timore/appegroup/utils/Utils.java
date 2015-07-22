package com.timore.appegroup.utils;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.transition.Explode;

/**
 * Created by ZEID on 7/14/2015.
 */
public class Utils {
    public static final String SERVER_URL = "http://pharmland.me/phs";
    public static final int APP_ID = 1;

    public static void startActivity(SuperActivity activity, Intent intent) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            activity.getWindow().setExitTransition(new Explode());
            activity.startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(activity).toBundle());
        } else {
            activity.startActivity(intent);
            activity.overridePendingTransition(android.R.anim.slide_in_left,android.R.anim.slide_in_left);
        }
    }
}

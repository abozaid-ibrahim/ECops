package com.timore.appegroup;

import android.content.Context;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;

import org.json.JSONObject;

import java.util.Map;

/**
 * Created by ZEID on 6/7/2015.
 */
public class DoNBackground {
    public static void postObj(final Context act, String url,Map params) {
        new AQuery(act).ajax(url, params,JSONObject.class, new AjaxCallback<JSONObject>() {
            @Override
            public void callback(String url, JSONObject json, AjaxStatus status) {
                System.err.println("=================== DO IN BACKGROUND ======================");
            }
        });
    }
    public static void getObj(final Context act, String url) {
        new AQuery(act).ajax(url, JSONObject.class, new AjaxCallback<JSONObject>() {
            @Override
            public void callback(String url, JSONObject json, AjaxStatus status) {
                System.err.println("=================== DO IN BACKGROUND ======================");
            }
        });
    }
}

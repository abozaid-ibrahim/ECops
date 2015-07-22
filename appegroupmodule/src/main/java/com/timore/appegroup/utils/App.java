package com.timore.appegroup.utils;

import android.app.Application;

import com.facebook.FacebookSdk;
import com.parse.Parse;
import com.parse.ParseFacebookUtils;
import com.parse.ParseTwitterUtils;

/**
 * Created by ZEID on 7/14/2015.
 */
public class App extends Application {
    @Override
    public void onCreate() {

        super.onCreate();
        initialParse();
    }

    private void initialParse() {
        FacebookSdk.sdkInitialize(getApplicationContext());

        Parse.initialize(this, "parse_app_id", "parse_client_id");
        ParseTwitterUtils.initialize("twitter_consumer_key", "twitter_consumer_secret");
        ParseFacebookUtils.initialize(this);
    }


}

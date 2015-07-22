package com.timore.abuzeidtimore.activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;


import com.timore.abuzeidtimore.ABZUtils;
import com.timore.abuzeidtimore.R;

public class FullScreenImageActivity extends Activity {

    public static final String EXTRA_IMAGE_URL = "image";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            requestWindowFeature(Window.FEATURE_NO_TITLE);
        } catch (Exception e) {


        }

        setContentView(R.layout.activity_full_screen_image);

        ABZUtils.image(R.id.fullscreen_iv_image,R.id.fullscreen_bar_progress,getIntent().getExtras().getString(EXTRA_IMAGE_URL));
        findViewById(R.id.fullscreen_btn_done).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

}

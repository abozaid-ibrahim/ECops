package co.ecops.platform.utils;

import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;

/**
 * Created by ZEID on 7/14/2015.
 */
public class SuperActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // enable transitions
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
            getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);

    }

    public void initToolbar(View toolbar) {
        setSupportActionBar((Toolbar) toolbar);
        final ActionBar ab = getSupportActionBar();
//        ab.setHomeAsUpIndicator(R.drawable.ic_menu);
        ab.setDisplayHomeAsUpEnabled(true);
    }
}

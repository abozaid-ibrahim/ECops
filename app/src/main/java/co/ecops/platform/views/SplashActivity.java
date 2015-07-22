package co.ecops.platform.views;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import co.ecops.platform.R;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new Handler().postDelayed(new Runnable() {
            Intent next;

            @Override
            public void run() {
                next = new Intent(getBaseContext(), EmailLoginActivity.class);
//                next=new Intent(getBaseContext(),MainActivity.class);
                startActivity(next);
            }
        }, 5000);
    }


}

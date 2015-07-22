package com.timore.abuzeidtimore;

import android.app.Activity;
import android.graphics.Point;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;


public class UI_Analyisis_Activity extends Activity {

    private TextView tvDim;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ui__analyisis_);
        tvDim = (TextView) findViewById(R.id.anaylises_screenDim_tv);
        getDimentions(tvDim);
    }

    private void getDimentions(TextView tvDim) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
       tvDim.setText("widthPixels= "+displayMetrics.widthPixels
               +" \n heightPixels= "+displayMetrics.heightPixels
               +" \n density= "+displayMetrics.density
               +" \n densityDpi= "+displayMetrics.densityDpi
               +" \n scaledDensity= "+displayMetrics.scaledDensity
               +" \n xdpi= "+displayMetrics.xdpi);
    }
    private void getDimentions2(TextView tvDim) {
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);

       tvDim.setText("X= "+size.x +" \n Y= "+size.y
               +" \n density: "+getResources().getDisplayMetrics().density);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_ui__analyisis_, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            getDimentions2(tvDim);
            return true;

        }

        return super.onOptionsItemSelected(item);
    }
}

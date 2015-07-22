package com.timore.abuzeidtimore.view;

import android.text.TextUtils;
import android.widget.EditText;

/**
 * Created by ZEID on 6/3/2015.
 */


public class ET {

    public static String obligatory(EditText et) {

        if (TextUtils.isEmpty(et.getText().toString())) {
            et.setError("Required");
            return null;
        } else {
            et.setError(null);

            return et.getText().toString();
        }
    }

    public static int obligatoryPN(EditText et) {

        if(TextUtils.isEmpty(et.getText().toString()))
        {
            et.setError("Required");
            return -1;
        }
        if (Integer.valueOf(et.getText().toString()) <= 0) {
            et.setError("Enter positive number");
            return -1;
        } else {
            et.setError(null);
            return Integer.valueOf(et.getText().toString());
        }
    }public static float obligatoryF(EditText et) {

        if(TextUtils.isEmpty(et.getText().toString()))
        {
            et.setError("Required");
            return -1;
        }
        if (Float.valueOf(et.getText().toString()) < 0) {
            et.setError("Enter positive number");
            return -1;
        } else {
            et.setError(null);
            return Float.valueOf(et.getText().toString());
        }
    }
}

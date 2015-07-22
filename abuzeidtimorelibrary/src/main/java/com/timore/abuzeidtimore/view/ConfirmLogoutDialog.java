package com.timore.abuzeidtimore.view;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;

import com.timore.abuzeidtimore.R;

public class ConfirmLogoutDialog {


    public ConfirmLogoutDialog(final Activity activity, final SharedPreferences pref) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle(activity.getString(R.string.logout));
        builder.setMessage(activity.getString(R.string.confirm_logout));

        builder.setNegativeButton(activity.getString(R.string.cancel), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();

            }
        });

        builder.setPositiveButton(activity.getString(R.string.logout), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                pref.edit().clear().commit();
                dialog.dismiss();
                activity.finish();
            }
        });


        builder.create().show();

    }


}

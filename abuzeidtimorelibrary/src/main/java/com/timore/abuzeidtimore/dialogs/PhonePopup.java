package com.timore.abuzeidtimore.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.timore.abuzeidtimore.ABZUtils;
import com.timore.abuzeidtimore.R;

import java.util.ArrayList;
import java.util.Arrays;


public class PhonePopup extends Dialog {

    public PhonePopup(final Context context, final ArrayList numbers) {
        super(context);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        setContentView(R.layout.dialog_phone);
        ABZUtils.setDialogFullWidthRes(this, Gravity.BOTTOM);
        findViewById(R.id.phone_dialog_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        ListView listview = (ListView) findViewById(R.id.phone_dialog_listview);
        listview.setAdapter(new PhoneAdapter(context, numbers));



    }

    public PhonePopup(Context context, String[] numbers) {
//        super(context);
        this(context, new ArrayList<String>(Arrays.asList(numbers)));
    }

    class PhoneAdapter extends BaseAdapter {


        private Context context;
        private ArrayList<String> data;

        public PhoneAdapter(Context context, ArrayList<String> objects) {
            this.context = context;
            this.data = objects;
        }

        @Override
        public int getCount() {
            return data.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.row_dialog_phone, parent, false);
            }
            Button number = (Button) convertView.findViewById(R.id.phone_dialog_lv_item);
            number.setText(data.get(position));
            number.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dismiss();
                    ABZUtils.call(context, data.get(position));

                }
            });
            return convertView;

        }
    }

}

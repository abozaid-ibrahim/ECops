package com.timore.abuzeidtimore.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.timore.abuzeidtimore.ABZUtils;
import com.timore.abuzeidtimore.R;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Abuzeid on 6/30/2015.
 */
public class TwoWayViewImageAdapter extends BaseAdapter {

    private Context context;
    private List data;

    public TwoWayViewImageAdapter(Context context, List resource) {
        this.context = context;
        this.data = resource;
    }

    public TwoWayViewImageAdapter(Context context, String[] resource) {
        this.context = context;

        this.data = Arrays.asList(resource);
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.row_twv_image, null);
            new ViewHodler(convertView);
        }
        ViewHodler holder = (ViewHodler) convertView.getTag();
        ABZUtils.image(holder.imageView, holder.progressBar,data.get(position).toString());


        return convertView;
    }

    class ViewHodler {
        ImageView imageView;
        ProgressBar progressBar;

        ViewHodler(View view) {
            imageView = (ImageView) view.findViewById(R.id.twv_ivImage);
            progressBar = (ProgressBar) view.findViewById(R.id.twv_progress);
            view.setTag(this);
        }
    }
}

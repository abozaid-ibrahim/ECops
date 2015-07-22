package com.timore.abuzeidtimore.samples;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.timore.abuzeidtimore.ABZUtils;
import com.timore.abuzeidtimore.R;

import java.util.List;


public class ViewPagerAdapter extends PagerAdapter {

    private LayoutInflater inflater;
    List<String> galleries;
    Context context;


    public ViewPagerAdapter(Context activity, List<String> data) {
        this.context = activity;
        this.galleries = data;
        inflater = LayoutInflater.from(context);

    }

    @Override
    public int getCount() {
        return galleries.size();
    }

    @Override
    public boolean isViewFromObject(View arg0, Object arg1) {
        return arg0 == arg1;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = inflater.inflate(R.layout.sample_row_vp_gallery, null, false);

        ImageView iv=(ImageView)view.findViewById(R.id.vp_iv_image);
        ProgressBar progress=(ProgressBar)view.findViewById(R.id.vp_iv_progress);
        ABZUtils.image(iv,progress,galleries.get(position));
        ((ViewPager) container).addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        ((ViewPager) container).removeView((FrameLayout) object);
    }


}

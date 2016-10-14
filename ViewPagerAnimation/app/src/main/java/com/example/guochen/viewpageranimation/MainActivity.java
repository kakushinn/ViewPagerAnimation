package com.example.guochen.viewpageranimation;

import android.app.Activity;
import android.media.Image;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends Activity {

    private int[] images = new int[]{R.mipmap.guide_image1,R.mipmap.guide_image2,R.mipmap.guide_image3};
    private List<ImageView> imageViewList = new ArrayList<ImageView>();
    public MyViewPager pager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        pager = (MyViewPager)findViewById(R.id.viewPager);
//        pager.setPageTransformer(true,new ZoomOutPageTransformer());
//        pager.setPageTransformer(true,new DepthPageTransformer());
//        pager.setPageTransformer(true,new RotatePageTransformer());
        pager.setAdapter(new PagerAdapter() {
            @Override
            public int getCount() {

                return images.length;
            }

            @Override
            public boolean isViewFromObject(View view, Object object) {
                return view == object;
            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                ImageView imgView = new ImageView(MainActivity.this);
                imgView.setImageResource(images[position]);
                imgView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                container.addView(imgView);
                pager.setViewForPosition(imgView,position);
                imageViewList.add(imgView);
                return imgView;
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                container.removeView(imageViewList.get(position));
                pager.removeViewForPosition(position);
            }
        });
    }
}

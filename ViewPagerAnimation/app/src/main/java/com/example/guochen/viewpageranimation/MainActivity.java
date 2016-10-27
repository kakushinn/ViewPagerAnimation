package com.example.guochen.viewpageranimation;

import android.app.Activity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import java.util.ArrayList;
import java.util.List;

import View.Animation.ZoomOutPageTransformer;


public class MainActivity extends Activity {

    private int[] images = new int[]{R.mipmap.guide_image1,R.mipmap.guide_image2,R.mipmap.guide_image3,R.mipmap.guide_image1,R.mipmap.guide_image2,R.mipmap.guide_image3,R.mipmap.guide_image1,R.mipmap.guide_image2,R.mipmap.guide_image3};
    private List<ImageView> imageViewList = new ArrayList<ImageView>();
    public ViewPager pager;
    public ViewPagerIndicator viewPagerIndicator;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        pager = (ViewPager)findViewById(R.id.viewPager);
//        pager.setPageTransformer(true,new ZoomOutPageTransformer());
//        pager.setPageTransformer(true,new DepthPageTransformer());
//        pager.setPageTransformer(true,new RotatePageTransformer());
        viewPagerIndicator = (ViewPagerIndicator)findViewById(R.id.indicator);
        viewPagerIndicator.setViewPager(pager);
        if(pager.getCurrentItem() == 0){
            viewPagerIndicator.highlightTextView(0);
        }
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
//                pager.setViewForPosition(imgView,position);
                imageViewList.add(imgView);
                return imgView;
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                container.removeView(imageViewList.get(position));
//                pager.removeViewForPosition(position);
            }


        });

        pager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                viewPagerIndicator.scroll(position, positionOffset);
            }

            @Override
            public void onPageSelected(int position) {
                viewPagerIndicator.resetTextColor();
                viewPagerIndicator.highlightTextView(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }


}

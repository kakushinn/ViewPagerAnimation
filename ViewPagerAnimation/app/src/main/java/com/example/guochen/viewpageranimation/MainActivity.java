package com.example.guochen.viewpageranimation;

import android.os.StrictMode;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

import Entities.News;
import Logic.TopNewsService;
import View.Animation.EntertainmentNewsFragment;
import View.Animation.FinanceNewsFragment;
import View.Animation.HotNewsFragment;
import View.Animation.JourneyNewsFragment;
import View.Animation.MilitaryNewsFragment;
import View.Animation.SportsNewsFragment;
import View.Animation.TechnoligyNewsFragment;


public class MainActivity extends FragmentActivity {

    public ViewPager pager;
    public List<Fragment> fragmentList = new ArrayList<Fragment>();
    public ViewPagerIndicator viewPagerIndicator;
    public android.support.v4.app.FragmentManager fm;
    public Fragment hotNewsFragment;
    public Fragment entertainmentNewsFragment;
    public Fragment journeyNewsFragment;
    public Fragment militaryNewsFragment;
    public Fragment sportsNewsFragment;
    public Fragment technoligyNewsFragment;
    public Fragment financeNewsFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fm = getSupportFragmentManager();
        pager = (ViewPager)findViewById(R.id.viewPager);
//        pager.setPageTransformer(true,new ZoomOutPageTransformer());
//        pager.setPageTransformer(true,new DepthPageTransformer());
//        pager.setPageTransformer(true,new RotatePageTransformer());
        viewPagerIndicator = (ViewPagerIndicator)findViewById(R.id.indicator);
        viewPagerIndicator.setViewPager(pager);

        initFragmentList();

        if(pager.getCurrentItem() == 0){
            viewPagerIndicator.highlightTextView(0);
        }

        pager.setAdapter(new FragmentPagerAdapter(fm) {
            @Override
            public Fragment getItem(int position) {
                return fragmentList.get(position);
            }

            @Override
            public int getCount() {
                return fragmentList.size();
            }

            @Override
            public void destroyItem(View container, int position, Object object) {
//                super.destroyItem(container, position, object);
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

    public void initFragmentList(){
        hotNewsFragment = new HotNewsFragment();
        fragmentList.add(hotNewsFragment);
        entertainmentNewsFragment = new EntertainmentNewsFragment();
        fragmentList.add(entertainmentNewsFragment);
        journeyNewsFragment = new JourneyNewsFragment();
        fragmentList.add(journeyNewsFragment);
        militaryNewsFragment = new MilitaryNewsFragment();
        fragmentList.add(militaryNewsFragment);
        sportsNewsFragment = new SportsNewsFragment();
        fragmentList.add(sportsNewsFragment);
        technoligyNewsFragment = new TechnoligyNewsFragment();
        fragmentList.add(technoligyNewsFragment);
        financeNewsFragment = new FinanceNewsFragment();
        fragmentList.add(financeNewsFragment);
    }


}

package com.example.guochen.viewpageranimation;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;

import com.nineoldandroids.view.ViewHelper;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by guochen on 2016/10/12.
 */
class MyViewPager extends ViewPager {

    private View mLeftView ;
    private View mRightView;

    private float mTrans;
    private float mScales;
    private static final float MIN_SCALE = 0.5f;

    private Map<Integer, View> mChild = new HashMap<Integer, View>();
    public void setViewForPosition(View view , int position){
        mChild.put(position,view);
    }

    public void removeViewForPosition(int position){
        mChild.remove(position);
    }
    public MyViewPager(Context context) {
        this(context,null);
    }

    public MyViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    
    @Override
    protected void onPageScrolled(int position, float offset, int offsetPixels) {
        mLeftView = mChild.get(position);
        mRightView = mChild.get(position + 1);
        animationStack(mLeftView, mRightView, offset, offsetPixels);
        super.onPageScrolled(position, offset, offsetPixels);
    }

    private void animationStack(View leftView, View rightView, float offset, int offsetPixels ){
        if(rightView != null){
            mScales = (1 - MIN_SCALE) * offset + MIN_SCALE;
            mTrans = -getWidth()-getPageMargin()+offsetPixels;

            ViewHelper.setScaleX(rightView,mScales);
            ViewHelper.setScaleY(rightView, mScales);

            ViewHelper.setTranslationX(rightView,mTrans);
        }

        if(leftView != null){
            leftView.bringToFront();
        }

    }
}

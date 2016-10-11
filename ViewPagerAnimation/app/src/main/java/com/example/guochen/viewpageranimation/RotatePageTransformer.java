package com.example.guochen.viewpageranimation;

import android.support.v4.view.ViewPager;
import android.view.View;

public class RotatePageTransformer implements ViewPager.PageTransformer {
    private static final float MAX_ROTATE = 20f;

    public void transformPage(View view, float position) {
        int pageWidth = view.getWidth();

        if (position < -1) { // [-Infinity,-1)
            // This page is way off-screen to the left.
//            view.setAlpha(0);
            view.setRotation(0);

        } else if (position <= 0) { // [-1,0]
            // Use the default slide transition when moving to the left page
            view.setPivotX(pageWidth/2);
            view.setPivotY(view.getMeasuredHeight());
            view.setRotation(position * MAX_ROTATE);
        } else if (position <= 1) { // (0,1]
            view.setPivotX(pageWidth/2);
            view.setPivotY(view.getMeasuredHeight());
            view.setRotation(position * MAX_ROTATE);

        } else { // (1,+Infinity]
            // This page is way off-screen to the right.
            view.setRotation(0);
        }
    }
}
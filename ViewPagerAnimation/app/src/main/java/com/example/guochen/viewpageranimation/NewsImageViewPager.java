package com.example.guochen.viewpageranimation;

import android.content.Context;
import android.graphics.Point;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by guochen on 2016/10/31.
 */
public class NewsImageViewPager extends ViewPager {
    Point downP = new Point();
    Point curP = new Point();

    public NewsImageViewPager(Context context) {
        super(context);
    }

    public NewsImageViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onTouchEvent(MotionEvent arg0) {
        //?次?行onTouch事件都??当前的按下的坐?
        if(getChildCount()<=1)
        {
            return super.onTouchEvent(arg0);
        }
        curP.x = (int) arg0.getX();
        curP.y = (int) arg0.getY();

        if(arg0.getAction() == MotionEvent.ACTION_DOWN)
        {

            //??按下?候的坐?
            //切?不可用 downP = curP ，??在改?curP的?候，downP也会改?
            downP.x = (int) arg0.getX();
            downP.y = (int) arg0.getY();
            //此句代?是?了通知他的父ViewPager?在?行的是本控件的操作，不要?我的操作?行干?
            getParent().requestDisallowInterceptTouchEvent(true);
        }

        if(arg0.getAction() == MotionEvent.ACTION_MOVE){
            //此句代?是?了通知他的父ViewPager?在?行的是本控件的操作，不要?我的操作?行干?
            getParent().requestDisallowInterceptTouchEvent(true);
        }
        super.onTouchEvent(arg0);
        return true;
    }

}

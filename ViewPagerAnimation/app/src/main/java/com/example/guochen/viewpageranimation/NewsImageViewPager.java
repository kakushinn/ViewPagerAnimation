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
        //??sonTouchs??OIΒΊIΏ?
        if(getChildCount()<=1)
        {
            return super.onTouchEvent(arg0);
        }
        curP.x = (int) arg0.getX();
        curP.y = (int) arg0.getY();

        if(arg0.getAction() == MotionEvent.ACTION_DOWN)
        {

            //??ΒΊ?σIΏ?
            //Ψ?sΒp downP = curP C??έό?curPI?σCdownPηοό?
            downP.x = (int) arg0.getX();
            downP.y = (int) arg0.getY();
            //εγ?₯?ΉΚmΌIViewPager?έ?sI₯{TIμCsv?δIμ?s±?
            getParent().requestDisallowInterceptTouchEvent(true);
        }

        if(arg0.getAction() == MotionEvent.ACTION_MOVE){
            //εγ?₯?ΉΚmΌIViewPager?έ?sI₯{TIμCsv?δIμ?s±?
            getParent().requestDisallowInterceptTouchEvent(true);
        }
        super.onTouchEvent(arg0);
        return true;
    }

}

package com.example.guochen.viewpageranimation;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.CornerPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.guochen.viewpageranimation.R;

/**
 * Created by guochen on 2016/10/19.
 */
public class ViewPagerIndicator extends LinearLayout {

    private int mTriangleWidth;

    private int mTriangleHeight;

    private int mInitTranslation;
    private static final float RADIO_TRIANGLE_WIDTH = 1/6F;
    private int mTranslationX;

    private Path mPath;

    private Paint mPaint;

    private int mTabVisibleCount;

    private ViewPager mViewPager;

    private boolean isFirstIndicator;

    private static final int DEFAULT_VISIBLE_COUNT = 4;
    public ViewPagerIndicator(Context context) {
        this(context, null);
    }

    public ViewPagerIndicator(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.ViewPagerIndicator);
        mTabVisibleCount = a.getInt(R.styleable.ViewPagerIndicator_visible_tab_count,DEFAULT_VISIBLE_COUNT);
        if(mTabVisibleCount < 0){
            mTabVisibleCount = DEFAULT_VISIBLE_COUNT;
        }
        a.recycle();
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.parseColor("#ff000000"));
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setPathEffect(new CornerPathEffect(3));
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        canvas.save();
        canvas.translate(mInitTranslation + mTranslationX, getHeight());
        canvas.drawPath(mPath, mPaint);
        canvas.restore();
        super.dispatchDraw(canvas);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mTriangleWidth = (int)(w/mTabVisibleCount * RADIO_TRIANGLE_WIDTH);
        mInitTranslation = (w/mTabVisibleCount- mTriangleWidth)/2;
        initTriangle();
    }

    private void initTriangle() {
        System.out.println(getWidth());
        mTriangleHeight = mTriangleWidth/2;
        mPath = new Path();
        mPath.moveTo(0, 0);
        mPath.lineTo(mTriangleWidth, 0);
        mPath.lineTo(mTriangleWidth / 2, -mTriangleHeight);
        mPath.close();
    }

    public void scroll(int position, float offset){
        int tabWidth = getWidth()/mTabVisibleCount;
        mTranslationX = (int)(tabWidth * (position + offset));

        //tab容器移动，当tab处于移动至最后一个时
        if(mTabVisibleCount != 1){
            if((position >= (mTabVisibleCount-2) && offset > 0 && getChildCount() > mTabVisibleCount) || mViewPager.getCurrentItem() >= 3){
                this.scrollTo((int) (((position - (mTabVisibleCount -2))+offset) * tabWidth), 0);
            }else {
                this.scrollTo(0,0);
            }
        }else{
            this.scrollTo((int)(tabWidth * (position + offset)), 0);
        }
        invalidate();
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        int childCount = getChildCount();
        if(childCount == 0){
            return;
        }else{
            for(int i = 0;i < childCount;i++){
                View view = getChildAt(i);
                LinearLayout.LayoutParams layoutParams = (LayoutParams) view.getLayoutParams();
                layoutParams.weight = 0;
                layoutParams.width = getScreenWidth() / mTabVisibleCount;
                view.setLayoutParams(layoutParams);
            }
        }
        onTabClickEvent();
    }

    private int getScreenWidth() {
        WindowManager wm = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics.widthPixels;
    }

    public void highlightTextView(int position){
        View view = this.getChildAt(position);
        if(view instanceof TextView){
            ((TextView) view).setTextColor(0xffffffff);
        }
    }

    public void resetTextColor(){
        for(int i=0;i<getChildCount();i++){
            View view = getChildAt(i);
            if(view instanceof TextView){
                ((TextView) view).setTextColor(0x77ffffff);
            }
        }
    }

    public void onTabClickEvent(){
        for(int i=0;i<getChildCount();i++){
            final int j = i;
            View view = getChildAt(i);
            view.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    mViewPager.setCurrentItem(j);
                }
            });
        }
    }

    public void setViewPager(ViewPager viewPager){
        this.mViewPager = viewPager;
        onTabClickEvent();
    }
}

package com.example.guochen.viewpageranimation;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.CornerPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.Toast;

/**
 * Created by guochen on 2016/10/19.
 */
public class ViewPagerIndicator extends LinearLayout {

    // �O�p�w����I?�x
    private int mTriangleWidth;
    // �O�p�w����I���x
    private int mTriangleHeight;
    // �O�p�w����I���n�ʒu
    private int mInitTranslation;
    // �O�p�w���푊?������?�x�I���
    private static final float RADIO_TRIANGLE_WIDTH = 1/6F;
    // �O�p�w����I��?�ʒu
    private int mTranslationX;

    private Path mPath;

    private Paint mPaint;


    public ViewPagerIndicator(Context context) {
        this(context, null);
    }

    public ViewPagerIndicator(Context context, AttributeSet attrs) {
        super(context, attrs);
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
        mTriangleWidth = (int)(w/3 * RADIO_TRIANGLE_WIDTH);
        mInitTranslation = (w/3- mTriangleWidth)/2;
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
        int tabWidth = getWidth()/3;
        mTranslationX = (int)(tabWidth * (position + offset));
        invalidate();
    }

}

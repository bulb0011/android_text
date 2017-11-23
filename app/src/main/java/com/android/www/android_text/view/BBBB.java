package com.android.www.android_text.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import com.android.www.android_text.R;


/**
 * Created by Administrator on 2017/9/28 0028.
 */

public class BBBB extends View{

    /**
     * 圈的宽度
     */
    private int circleWidth;

    /**
     * 第一圈的颜色
     */
    private int firstColor;

    /**
     * 第二全的颜色
     */
    private int secondColor;


    /**
     * 速度
     */
    private int speed;


    /**
     * 画笔
     */
    private Paint paint;


    /**
     * 当前进度
     */
    private int mProgress;


    private boolean isNext;

   private static MyThread myThread;

    public static boolean isStop=true;

    public BBBB(Context context) {
        super(context);
    }



    public BBBB(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context, attrs);
    }



    public BBBB(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        initView(context, attrs);

    }

    private void initView(Context context, @Nullable AttributeSet attrs) {
        TypedArray a =context.getTheme().obtainStyledAttributes(attrs, R.styleable.aaaaa,0,0);

        int indexCount=a.getIndexCount();

        for (int i=0; i<indexCount; i++) {

            switch (a.getIndex(i)){

                case R.styleable.aaaaa_circleWidth:

                    circleWidth =a.getDimensionPixelSize(a.getIndex(i), (int) TypedValue.applyDimension(
                            TypedValue.COMPLEX_UNIT_PX, 20, getResources().getDisplayMetrics()));
                    break;

                case R.styleable.aaaaa_firstColor:

                    firstColor =a.getColor(a.getIndex(i), Color.GREEN);
                break;

                case R.styleable.aaaaa_secondColor:

                    secondColor =a.getColor(a.getIndex(i),Color.RED);
                break;

                case R.styleable.aaaaa_speed:

                    speed =a.getInt(a.getIndex(i),20);

                break;


            }
        }

        a.recycle();

        paint = new Paint();

        myThread = new  MyThread();

        myThread.start();

    }

    public static void stp(){
        isStop=false;
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int centre=getWidth()/2;

        int radius= centre -circleWidth;

        paint.setStrokeWidth(circleWidth);

        paint.setAntiAlias(true);

        paint.setStyle(Paint.Style.STROKE); // 设置空心

        RectF rectF=new RectF(centre-radius,centre-radius,centre+radius,centre+radius);//用于定义的圆弧的形状和大小的界限

        if (!isNext)
        {// 第一颜色的圈完整，第二颜色跑
            paint.setColor(firstColor); // 设置圆环的颜色
            canvas.drawCircle(centre, centre, radius, paint); // 画出圆环
            paint.setColor(secondColor); // 设置圆环的颜色
            canvas.drawArc(rectF, -90, mProgress, false, paint); // 根据进度画圆弧
        } else
        {
            paint.setColor(secondColor); // 设置圆环的颜色
            canvas.drawCircle(centre, centre, radius, paint); // 画出圆环
            paint.setColor(firstColor); // 设置圆环的颜色
            canvas.drawArc(rectF, -90, mProgress, false, paint); // 根据进度画圆弧
        }


    }


    class MyThread extends Thread  {

        @Override
        public void run() {
            super.run();
            while (isStop){

                mProgress++;

                if (mProgress ==360){

                    mProgress=0;

                    if (isNext){
                        isNext=true;
                    }else {
                        isNext=false;
                    }

                }

                postInvalidate();

                try {
                    Thread.sleep(speed);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }
    }
}

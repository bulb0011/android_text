package com.android.www.android_text.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;

import com.android.www.android_text.R;


/**
 * Created by Administrator on 2017/9/30 0030.
 */

public class MeiNv extends View {

    /**
     *圆的宽度
     */
    private int circleWidth;

    /**
     *间隔的大小
     */
    private int splitSize;

    private int firstColor;

    /**
     *一共多少个
     */
    private int dotCount;

    /**
     * 当前的个数
     */
    public int mCurrentCount;

    private int secondColor;
    private Bitmap img;
    private Paint paint;
    private Rect rect;


    public MeiNv(Context context) {
        super(context);
    }



    public MeiNv(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        init(context,attrs);

    }


    void init (Context context, @Nullable AttributeSet attrs){

        TypedArray a=context.getTheme().obtainStyledAttributes(attrs, R.styleable.MeiNv,0,0);

        int n=a.getIndexCount();

        for (int i = 0; i < n; i++) {

            switch (a.getIndex(i)){

                case R.styleable.MeiNv_MeiNv_ircleWidth:

                    circleWidth = a.getDimensionPixelSize(a.getIndex(i),(int) TypedValue.applyDimension(
                            TypedValue.COMPLEX_UNIT_PX, 20, getResources().getDisplayMetrics()));

                    break;

                case R.styleable.MeiNv_splitSize:

                    splitSize =a.getInt(a.getIndex(i),20);
                    break;

                case R.styleable.MeiNv_dotCount:

                    dotCount =a.getInt(a.getIndex(i),20);
                    break;


                case R.styleable.MeiNv_MeiNv_firstColor:

                    firstColor =a.getColor(a.getIndex(i), Color.BLUE);

                    break;

                case R.styleable.MeiNv_MeiNv_secondColor:

                    secondColor =a.getColor(a.getIndex(i),Color.GRAY);

                    break;

                case R.styleable.MeiNv_bg:
                    img = BitmapFactory.decodeResource(getResources(),a.getResourceId(a.getIndex(i),0));
                    break;
            }

        }

        a.recycle();

        paint = new Paint();

        rect = new Rect();

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        setSaveEnabled(true);

        paint.setAntiAlias(true); // 消除锯齿
        paint.setStrokeWidth(circleWidth);// 设置圆环的宽度
        paint.setStrokeCap(Paint.Cap.ROUND);// 定义线段断电形状为圆头

        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.STROKE);// 设置空心

        int centre  =getWidth()/2; //获取圆心的x坐标
        int radius=centre-circleWidth/2;// 半径

        //画小块
        drawOval(canvas,centre,radius);

        /**
         * 计算内切正方形的位置
         */
        int relRadius = radius - circleWidth / 2;// 获得内圆的半径
        /**
         * 内切正方形的距离顶部 = mCircleWidth + relRadius - √2 / 2
         */
        rect.left = (int) (relRadius - Math.sqrt(2) * 1.0f / 2 * relRadius) + circleWidth;
        /**
         * 内切正方形的距离左边 = mCircleWidth + relRadius - √2 / 2
         */
        rect.top = (int) (relRadius - Math.sqrt(2) * 1.0f / 2 * relRadius) + circleWidth;
        rect.bottom = (int) (rect.left + Math.sqrt(2) * relRadius);
        rect.right = (int) (rect.left + Math.sqrt(2) * relRadius);

        /**
         * 如果图片比较小，那么根据图片的尺寸放置到正中心
         */
        if (img.getWidth() < Math.sqrt(2) * relRadius)
        {
            rect.left = (int) (rect.left + Math.sqrt(2) * relRadius * 1.0f / 2 - img.getWidth() * 1.0f / 2);
            rect.top = (int) (rect.top + Math.sqrt(2) * relRadius * 1.0f / 2 - img.getHeight() * 1.0f / 2);
            rect.right = (int) (rect.left + img.getWidth());
            rect.bottom = (int) (rect.top + img.getHeight());

        }
        // 绘图
        canvas.drawBitmap(img, null, rect, paint);


    }

    /**
     *画每个小块
     */
    void drawOval(Canvas canvas,int centre,int radius){

        /**
         * 根据需要画的个数以及间隙计算每个块块所占的比例*360
         */
        float itemSize = (360 * 1.0f - dotCount * splitSize) / dotCount;
        // 用于定义的圆弧的形状和大小的界限
        RectF oval = new RectF(centre - radius, centre - radius, centre + radius, centre + radius);
        
        paint.setColor(firstColor);

        for (int i = 0; i < dotCount; i++)
        {
            canvas.drawArc(oval, i * (itemSize + splitSize), itemSize, false, paint); // 根据进度画圆弧
        }

        paint.setColor(secondColor); // 设置圆环的颜色

        for (int i = 0; i < mCurrentCount; i++)
        {
            canvas.drawArc(oval, i * (itemSize + splitSize), itemSize, false, paint); // 根据进度画圆弧
        }

    }


    private int yDown,yUp,xDown,xUp;

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()){

            case MotionEvent.ACTION_DOWN:

                yDown = (int) event.getY();
                xDown=(int)event.getX();

            break;

            case MotionEvent.ACTION_MOVE:

            break;

            case MotionEvent.ACTION_UP:
                yUp = (int) event.getY();
                xUp=(int) event.getX();
                if (yUp > yDown)// 下滑
                {
                    down();
                } else
                {
                    up();
                }
            break;

        }


        return true;
    }

    /**
     * 当前数量+1
     */
    public void up()
    {
        if (mCurrentCount<=dotCount){

            mCurrentCount++;
        }
        /**
         *刷新控件
         */
        postInvalidate();
    }

   public void down(){
        if (mCurrentCount>=1){

            mCurrentCount--;
        }
       /**
        *刷新控件
        */
       postInvalidate();
    }

//    private int customState;//所谓状态其实就是数据

    @Nullable
    @Override
    protected Parcelable onSaveInstanceState() {

        Parcelable parcelable =super.onSaveInstanceState();

        SavedState ss = new SavedState(parcelable);

        ss.CurrentCount= mCurrentCount;
        return ss;
    }



    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        super.onRestoreInstanceState(state);

        if (!(state instanceof SavedState)){
            super.onRestoreInstanceState(state);
            return;
        }

        SavedState savedState =(SavedState)state;

        mCurrentCount = savedState.CurrentCount;


    }

    @SuppressLint("ParcelCreator")
    class  SavedState extends BaseSavedState{

        int CurrentCount;

        public SavedState(Parcelable superState) {
            super(superState);
        }

        public SavedState(Parcel source) {
            super(source);
            CurrentCount = source.readInt();
        }



        final Creator<SavedState> CREATOR = new Creator<SavedState>(){

            @Override
            public SavedState createFromParcel(Parcel source) {
                return new SavedState(source);
            }


            @Override
            public SavedState[] newArray(int size) {

                return new SavedState[size];
            }
        };



        @Override
        public void writeToParcel(Parcel out, int flags) {
            super.writeToParcel(out, flags);

            out.writeInt(CurrentCount);
        }




    }
}

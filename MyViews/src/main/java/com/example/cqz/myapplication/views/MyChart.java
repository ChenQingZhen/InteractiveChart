package com.example.cqz.myapplication.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Build;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Scroller;

import com.example.cqz.myapplication.R;

/**
 * Created by chenqingzhen on 2015/10/19.
 */
public class MyChart extends View{
    /**
     * Bit of {@link #getMeasuredWidthAndState()} and
     * {@link #getMeasuredWidthAndState()} that indicates the measured size
     * is smaller that the space the view would like to have.
     */
    public static final int MEASURED_STATE_TOO_SMALL = 0x01000000;
    /**
     * Bits of {@link #getMeasuredWidthAndState()} and
     * {@link #getMeasuredWidthAndState()} that provide the additional state bits.
     */
    public static final int MEASURED_STATE_MASK = 0xff000000;

    /**
     * The initial fling velocity is divided by this amount.
     */
    public static final int FLING_VELOCITY_DOWNSCALE = 4;


    private  boolean isShowText;
    private  int labelPosition;
    private Scroller mScroller;
    private GestureDetector mDetector;
    private RectF mPieBounds;
    private int mPieRotation;

    public MyChart(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray a= context.getTheme().obtainStyledAttributes(attrs, R.styleable.MyChart,0,0);

        try {
            isShowText= a.getBoolean(R.styleable.MyChart_chart_showText,false);
            labelPosition=a.getInt(R.styleable.MyChart_labelPosition, 0);
        } finally {
          a.recycle();
        }
        init();
    }

    public MyChart(Context context) {
        super(context);
        init();
    }

    private void init(){
        if(Build.VERSION.SDK_INT<11) {
            mScroller = new Scroller(getContext());
        }else{
            mScroller=new Scroller(getContext(),null,true);
        }
        mDetector=new GestureDetector(MyChart.this.getContext(),new GestureListener());

        }
    private int  getPieRotation(){
        return mPieRotation;
    }
    private void setPieRotation(int rotation){
        rotation=(rotation%360+360)%360;
        mPieRotation=rotation;
        rotateTo(rotation);
    }
    private void rotateTo(float preRotation){
       if(Build.VERSION.SDK_INT>=11){
           setRotation(preRotation);
       }else{
           invalidate();
       }

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int minw=getPaddingLeft()+getPaddingRight()+getSuggestedMinimumWidth();
        int w=resolveSizeAndState2(minw, widthMeasureSpec, 1);

        int minh= MeasureSpec.getSize(w)+getPaddingBottom()+getPaddingTop();
        int h=resolveSizeAndState2(minh, heightMeasureSpec, 0);
        setMeasuredDimension(w,h);
    }

    /**
     * Utility to reconcile a desired size and state, with constraints imposed
     * by a MeasureSpec.  Will take the desired size, unless a different size
     * is imposed by the constraints.  The returned value is a compound integer,
     * with the resolved size in the {@link #MEASURED_SIZE_MASK} bits and
     * optionally the bit {@link #MEASURED_STATE_TOO_SMALL} set if the resulting
     * size is smaller than the size the view wants to be.
     *
     * @param size How big the view wants to be
     * @param measureSpec Constraints imposed by the parent
     * @return Size information bit mask as defined by
     * {@link #MEASURED_SIZE_MASK} and {@link #MEASURED_STATE_TOO_SMALL}.
     */
    public static int resolveSizeAndState2(int size, int measureSpec, int childMeasuredState) {
        int result = size;
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize =  MeasureSpec.getSize(measureSpec);
        switch (specMode) {
            case MeasureSpec.UNSPECIFIED:
                result = size;
                break;
            case MeasureSpec.AT_MOST:
                if (specSize < size) {
                    result = specSize | MEASURED_STATE_TOO_SMALL;
                } else {
                    result = size;
                }
                break;
            case MeasureSpec.EXACTLY:
                result = specSize;
                break;
        }
        return result | (childMeasuredState&MEASURED_STATE_MASK);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        float xpad=getPaddingLeft()+getPaddingRight();
        float ypad=getPaddingBottom()+getPaddingTop();

        float ww=w-xpad;
        float hh=h-ypad;
        float diameter=Math.min(ww, hh);
        mPieBounds=new RectF(0.0f,0.0f,diameter,diameter);
        mPieBounds.offsetTo(getPaddingLeft(),getPaddingTop());
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        Paint arcPaint=new Paint();
        arcPaint.setAntiAlias(true);
        arcPaint.setColor(Color.BLUE);
        canvas.drawArc(mPieBounds, 0, 45, true, arcPaint);
        arcPaint.setColor(Color.RED);
        canvas.drawArc(mPieBounds, 45, 180, true, arcPaint);
        arcPaint.setColor(Color.CYAN);
        canvas.drawArc(mPieBounds, 225, 135, true, arcPaint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        boolean result= mDetector.onTouchEvent(event);
       if(!result){
           if(event.getAction()==MotionEvent.ACTION_UP){
               result=true;
           }
       }
        return result;
    }

    private class  GestureListener extends GestureDetector.SimpleOnGestureListener{
        @Override
        public boolean onDown(MotionEvent e) {
            return true;
        }

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            // Set the pie rotation directly.
            float scrollTheta = vectorToScalarScroll(
                    distanceX,
                    distanceY,
                    e2.getX() - mPieBounds.centerX(),
                    e2.getY() - mPieBounds.centerY());
            setPieRotation(getPieRotation() - (int) scrollTheta / FLING_VELOCITY_DOWNSCALE);
            return true;
        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            float scrollTheta = vectorToScalarScroll(
                    velocityX,
                    velocityY,
                    e2.getX() - mPieBounds.centerX(),
                    e2.getY() - mPieBounds.centerY());
            mScroller.fling(
                    0,
                    (int) getPieRotation(),
                    0,
                    (int) scrollTheta / FLING_VELOCITY_DOWNSCALE,
                    0,
                    0,
                    Integer.MIN_VALUE,
                    Integer.MAX_VALUE);

            // Start the animator and tell it to animate for the expected duration of the fling.
//            if (Build.VERSION.SDK_INT >= 11) {
//                mScrollAnimator.setDuration(mScroller.getDuration());
//                mScrollAnimator.start();
//            }
            return true;

        }
    }
    /**
     * Helper method for translating (x,y) scroll vectors into scalar rotation of the pie.
     *
     * @param dx The x component of the current scroll vector.
     * @param dy The y component of the current scroll vector.
     * @param x  The x position of the current touch, relative to the pie center.
     * @param y  The y position of the current touch, relative to the pie center.
     * @return The scalar representing the change in angular position for this scroll.
     */
    private static float vectorToScalarScroll(float dx, float dy, float x, float y) {
        // get the length of the vector
        float l = (float) Math.sqrt(dx * dx + dy * dy);

        // decide if the scalar should be negative or positive by finding
        // the dot product of the vector perpendicular to (x,y).
        float crossX = -y;
        float crossY = x;

        float dot = (crossX * dx + crossY * dy);
        float sign = Math.signum(dot);

        return l * sign;
    }

}

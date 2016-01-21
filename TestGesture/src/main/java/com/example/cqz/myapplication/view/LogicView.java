package com.example.cqz.myapplication.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import java.util.Random;

/**
 * Created by chenqingzhen on 2015/10/12.
 */
public class LogicView extends View{
    private Paint paint=new Paint();
    private float rx=0;
    private float sweepAngle=0;
    private MyThread thread;
    private RectF rectF=new RectF(0,60,100,160);
    public LogicView(Context context) {
        super(context);
    }

    public LogicView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        paint.setTextSize(30);
        canvas.drawText("LogicView", rx, 30, paint);
        canvas.drawArc(rectF, 0, sweepAngle, true, paint);
        if(thread==null){
            thread=new MyThread();
            thread.start();
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

    }

    private boolean running =true;

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        running=false;
    }

    private class MyThread extends Thread{
        private Random rand=new Random();
        @Override
        public void run() {
           super.run();
            while (running){
                rx+=3;
                sweepAngle++;
                if(sweepAngle>360){
                    sweepAngle=0;
                }
                if(rx>getWidth()){
                    rx=0-paint.measureText("LogicView");
                }
                int r= rand.nextInt(256);
                int g= rand.nextInt(256);
                int b= rand.nextInt(256);
                paint.setARGB(255,r,g,b);
                postInvalidate();
                try {
                    Thread.sleep(30);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}

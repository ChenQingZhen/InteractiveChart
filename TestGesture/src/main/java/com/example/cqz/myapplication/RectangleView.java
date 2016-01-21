package com.example.cqz.myapplication;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by chenqingzhen on 2015/10/12.
 */
public class RectangleView extends View{
    public RectangleView(Context context){
         super(context);
    }
    public RectangleView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        RectF rectF=new RectF(-1,-1,1,1);
        Paint p=new Paint();
        p.setColor(Color.RED);
        canvas.drawRect(rectF,p);
    }
}

package com.dozen.dozendemo.hello;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import com.dozen.dozendemo.R;

/**
 * Created by Dozen on 2019/06/03 21:24.
 * Describe:
 */
public class HelloView extends View {

    private String title;
    private int textSize;
    private float width=100;
    private float height=100;

    public HelloView(Context context) {
        this(context,null);
    }

    public HelloView(Context context, @androidx.annotation.Nullable AttributeSet attrs) {
        this(context, attrs,-1);
    }

    public HelloView(Context context, @androidx.annotation.Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context,attrs,defStyleAttr);
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr) {
        @SuppressLint("Recycle") TypedArray typedArray=context.obtainStyledAttributes(attrs, R.styleable.HelloView);

        title=typedArray.getString(R.styleable.HelloView_title);
        textSize= (int) typedArray.getDimension(R.styleable.HelloView_textSize,20);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        width=getWidth();
        height=getHeight();

        @SuppressLint("DrawAllocation") Paint paint=new Paint();
        paint.setColor(Color.BLACK);
        paint.setStrokeWidth(5);
        paint.setDither(true);
        paint.setStyle(Paint.Style.STROKE);
        paint.setAntiAlias(true);
        paint.setTextSize(textSize);

        canvas.drawText(title,width/2-paint.measureText(title)/2,height/2,paint);
    }
}

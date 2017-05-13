package com.example.kilgore.chtrack;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by kilgore on 2017. 05. 13..
 */

public class BarGraph extends View {

    protected final static int MAX_VALUES = 25;
    protected List<Float> values;

    protected Path line;
    protected Paint paint;

    public BarGraph(Context context) {

        super(context);
        init();
    }

    public BarGraph(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public BarGraph(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

//    public BarGraph(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
//        super(context, attrs, defStyleAttr, defStyleRes);
//        init();
//    }

    protected void init() {

        Random r = new Random();

        this.values = new ArrayList<>();

        for (int i = 0; i < MAX_VALUES; i++){

            this.values.add(r.nextFloat());
        }

        this.line = new Path();
        this.paint = new Paint();
        this.paint.setColor(Color.parseColor("#303F9F"));
        this.paint.setStyle(Paint.Style.STROKE);
        this.paint.setStrokeWidth(5);
        this.paint.setAntiAlias(true);
    }

    public void addValue(float value){

        this.values.remove(0);
        this.values.add(value);
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int width = getWidth();
        int height = getHeight();


        float widthStep = width / MAX_VALUES;
        float heightStep = widthStep;
        float heightOffset = height >> 1;

        paint.setStrokeWidth(widthStep-2);


        for (int i = 1; i < MAX_VALUES; i++){

            float x = i * widthStep;
            canvas.drawLine(x, height, x, values.get(i)*heightOffset, paint);

        }

        canvas.drawPath(line, paint);
    }


}

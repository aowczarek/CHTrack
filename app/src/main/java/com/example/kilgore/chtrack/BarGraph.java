package com.example.kilgore.chtrack;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import java.util.Random;

public class BarGraph extends View {

    protected final static int MAX_VALUES = 24;
    protected Float[] values;

    protected DBHandler dbHandler;

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

    protected void init() {

        Random r = new Random();

        this.values = new Float[MAX_VALUES];

        for (int i = 0; i < MAX_VALUES; i++){

            this.values[i] = 0.0f;
        }

        this.paint = new Paint();
        this.paint.setColor(Color.parseColor("#303F9F"));
        this.paint.setStyle(Paint.Style.STROKE);
        this.paint.setStrokeWidth(1);
        this.paint.setAntiAlias(true);

        dbHandler = new DBHandler(this.getContext());
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        for (int i = 0; i < MAX_VALUES; i++){

            this.values[i] = 0.0f;
        }

        Cursor cursor = dbHandler.readMeal();

        Float maxCH = 1f;

        while ( !cursor.isAfterLast()){

            int hour = cursor.getInt(cursor.getColumnIndex("hour"));
            Float consumed = cursor.getFloat(cursor.getColumnIndex("consumed"));

            values[hour] += consumed;

            if (values[hour] > maxCH){
                maxCH = values[hour];
            }

            cursor.moveToNext();
        }

        int width = getWidth();
        int height = getHeight();

        maxCH = maxCH > height ? height : maxCH;

        float widthStep = width / (MAX_VALUES + 1);
        float heightStep = height / maxCH;

        canvas.drawLine(0, height-20, width, height-20, paint);

        for (int i = 0; i < MAX_VALUES; i++){

            float x = widthStep + i * widthStep;

            paint.setStrokeWidth(widthStep-2);
            canvas.drawLine(x, height - 20, x, height - 20 - (values[i] * heightStep), paint);

            paint.setStrokeWidth(1);
            canvas.drawLine(x, height-20, x, height-30, paint);

            paint.setTextSize(10);
            String xT = Integer.toString(i);
            canvas.drawText(xT, x - (paint.measureText(xT))/2, height, paint);

        }
    }


}

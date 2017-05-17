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
    protected final static int MAX_CARB = 160;
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
        this.paint.setStrokeWidth(5);
        this.paint.setAntiAlias(true);

        dbHandler = new DBHandler(this.getContext());
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int width = getWidth();
        int height = getHeight();

        float widthStep = width / (MAX_VALUES + 1);
        float heightStep = height / MAX_CARB;

        for (int i = 0; i < MAX_VALUES; i++){

            this.values[i] = 0.0f;
        }

        Cursor cursor = dbHandler.readMeal();

        while ( !cursor.isAfterLast()){

            Float quantity = cursor.getFloat(cursor.getColumnIndex("quantity"));
            int hour = cursor.getInt(cursor.getColumnIndex("hour"));

            values[hour] += quantity;

            cursor.moveToNext();
        }

        for (int i = 0; i < MAX_VALUES; i++){

            float x = widthStep + i * widthStep;

            paint.setStrokeWidth(widthStep-2);

            canvas.drawLine(x, height - 20, x, height - 20 - (values[i] * heightStep), paint);
            paint.setStrokeWidth(1);
            canvas.drawText(Integer.toString(i), x-widthStep/4, height, paint);

        }
    }


}

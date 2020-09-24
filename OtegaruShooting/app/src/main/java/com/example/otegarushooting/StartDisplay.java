package com.example.otegarushooting;

import android.graphics.Canvas;
import android.graphics.Paint;

public class StartDisplay extends Task {
    private Paint myPaint = new Paint();
    @Override
    public void onDraw(Canvas canvas) {
        myPaint.setTextSize(50);
        canvas.drawText("Otegaru Shooting",(float)MainActivity.getWidthSize()/2-200,(float)MainActivity.getHeightSize()/2,myPaint);
        myPaint.setTextSize(40);
        canvas.drawText("TAP TITLE",(float)MainActivity.getWidthSize()/2-200,(float)MainActivity.getHeightSize()/2+80,myPaint);
    }
}

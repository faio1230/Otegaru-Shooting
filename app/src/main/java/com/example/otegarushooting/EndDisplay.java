package com.example.otegarushooting;

import android.graphics.Canvas;
import android.graphics.Paint;

public class EndDisplay extends Task {
    private Paint myPaint = new Paint();
    private int score;
    EndDisplay(int score){
        this.score=score;
    }
    @Override
    public void onDraw(Canvas canvas) {
        myPaint.setTextSize(50);
        canvas.drawText("YOUR SCORE : " + score,(float)MainActivity.getWidthSize()/2-200,(float)MainActivity.getHeightSize()/2,myPaint);
        myPaint.setTextSize(40);
        canvas.drawText("TAP",(float)MainActivity.getWidthSize()/2,(float)MainActivity.getHeightSize()/2+80,myPaint);
    }
}

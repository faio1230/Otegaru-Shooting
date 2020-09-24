package com.example.otegarushooting;

import android.graphics.Canvas;
import android.graphics.Paint;

public class Score extends Task {
    Paint myPaint = new Paint();
    int score;
    public void setScore(int score){
        this.score = score;
    }
    @Override
    public boolean onUpdate(){
        return true;
    }
    @Override
    public void onDraw(Canvas canvas){
        myPaint.setAntiAlias(true);
        myPaint.setTextSize(30);
        canvas.drawText("SCORE:"+score,10,40,myPaint);
    }
}

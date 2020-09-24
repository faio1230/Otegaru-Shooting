package com.example.otegarushooting;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;

import java.util.LinkedList;

public class Effect extends Task {
    private long startTime;
    private Point point = new Point();
    private Paint myPaint = new Paint();
    private LinkedList<EffectBall> effectBalls = new LinkedList<EffectBall>();

    Effect(float x,float y){
        this.point.x =(int) x;
        this.point.y =(int) y;
        startTime = System.currentTimeMillis();
        for (int i = 0; i < 12; i++) {
            effectBalls.add(new EffectBall(this.point,new Vec((float)Math.cos(Math.toRadians(i*30)),(float)Math.sin(Math.toRadians(i*30)))));
        }
    }

    @Override
    public boolean onUpdate() {
        if(startTime-System.currentTimeMillis()>=3000){
            return false;
        }
        for (EffectBall effectBall:
             effectBalls) {
            effectBall.onUpdate();
        }
        return true;
    }

    @Override
    public void onDraw(Canvas canvas) {
        for (EffectBall effectBall:
                effectBalls) {
            myPaint.setColor(Color.MAGENTA);
            canvas.drawCircle(effectBall.getCircle().x,effectBall.getCircle().y,effectBall.getCircle().r,myPaint);
        }
    }
}

package com.example.otegarushooting;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;

public class Enemy extends Task implements GameObject {
    private final static float SIZE = 20;
    private Circle circle;
    private Paint myPaint = new Paint();
    private Vec vec = new Vec();
    private final int type = 1;

    public Enemy(){
        circle = new Circle(240,240,SIZE);
        myPaint.setColor(Color.RED);
        myPaint.setAntiAlias(true);
    }

    public Enemy(float x,float y,float vecX,float vecY){
        circle = new Circle(x,y,SIZE);
        vec.x=vecX;
        vec.y=vecY;
        myPaint.setColor(Color.RED);
        myPaint.setAntiAlias(true);
    }

    private void move(){
        circle.x += vec.x;
        circle.y += vec.y;
    }

    @Override
    public Circle getCircle(){
        return circle;
    }

    @Override
    public boolean onUpdate(){
        move();
        if(circle.x<-100||circle.x>MainActivity.getWidthSize()+100||
                circle.y<-100||circle.y>MainActivity.getHeightSize()+100){
            Log.d("enemy","deleted x = "+circle.x+" y = "+circle.y);
            return false;
        }
        return true;
    }

    @Override
    public void onDraw( Canvas canvas ){
        canvas.drawCircle(circle.x, circle.y, circle.r, myPaint);
    }

    @Override
    public int getType(){
        return type;
    }
}

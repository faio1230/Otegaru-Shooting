package com.example.otegarushooting;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;

public class Bullet extends Task implements GameObject {
    private final static float SIZE = 5;
    private Circle circle = new Circle();
    private Paint myPaint = new Paint();
    private Vec vec;
    private final int type = 2;

    public Bullet(){}

    public Bullet(int x,int y,Vec vec){
        circle.x=x;
        circle.y=y;
        this.vec=vec;
        myPaint.setColor(Color.YELLOW);
        myPaint.setAntiAlias(true);
        //Log.d("Log","Bullet is made");
    }

    private void move(){
        circle.x += Math.cos((double)vec.getAngle())*3;
        circle.y += Math.sin((double)vec.getAngle())*3;
    }

    @Override
    public Circle getCircle(){
        return circle;
    }

    @Override
    public boolean onUpdate(){
        move();
        if(circle.x<0||circle.x>MainActivity.getWidthSize()||
        circle.y<0||circle.y>MainActivity.getHeightSize()){
            return false;
        }
        return true;
    }

    @Override
    public void onDraw( Canvas canvas ){
        canvas.drawCircle(circle.x, circle.y, SIZE, myPaint);
    }

    @Override
    public int getType(){
        return type;
    }
}

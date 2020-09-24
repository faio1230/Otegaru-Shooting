package com.example.otegarushooting;

import android.graphics.Point;

public class EffectBall {
    private Circle circle;
    private Vec vec;
    private float angle;
    EffectBall(Point circlePoint,Vec vec){
        circle = new Circle(circlePoint.x,circlePoint.y,20);
        this.vec=vec;
        this.angle = (float)Math.toDegrees(this.vec.getAngle());
    }
    public void onUpdate(){
        rotateVec();
        move();
        circle.r-=1;
    }
    private void move(){
        circle.x+=vec.x;
        circle.y+=vec.y;
    }
    private void rotateVec(){
        angle+=10;
        vec.x+=Math.cos(Math.toRadians(angle))*2;
        vec.y+=Math.sin(Math.toRadians(angle))*2;
    }
    public Circle getCircle(){
        return circle;
    }
}

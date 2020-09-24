package com.example.otegarushooting;

import android.graphics.Canvas;

public abstract class Task {
    private Circle circle=new Circle();
    private int type=999;
    public boolean onUpdate(){
        return true;
    }
    public void onDraw(Canvas canvas){}
    public Circle getCircle(){return circle;}
    public int getType(){ return type; }
}

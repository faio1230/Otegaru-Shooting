package com.example.otegarushooting;

import android.graphics.Point;

public class TouchState {
    public boolean state;
    public boolean hold;
    public boolean tap;
    public Point point = new Point();
    TouchState(){
        state = false;
        hold = false;
        tap = false;
        point.x=0;
        point.y=0;
    }
}

package com.example.otegarushooting;

import android.app.Activity;
import android.graphics.Point;
import android.view.Display;

public class DisplaySizeCheck {
    public static Point getDisplaySize(Activity activity){
        Display display = activity.getWindowManager().getDefaultDisplay();
        Point point = new Point();
        display.getSize(point);
        return point;
    }
}

package com.example.otegarushooting;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class Player extends Task implements GameObject {
    private final static float SIZE = 20; //自機サイズ
    private final static float MAX_SPEED = 10; //移動する最大スピード
    private Circle circle;
    private Paint myPaint = new Paint();
    private Vec vec = new Vec();
    private Vec sensorVec = new Vec();     //センサーのベクトル
    private final int type = 0;

    public Player(){
        circle = new Circle(350,800,SIZE);
        myPaint.setColor(Color.BLUE);
        myPaint.setAntiAlias(true);
    }

    private void setVec(){
        float x = -AcSensor.Inst().getX()*2;    //加速度センサーの情報を取得
        float y =  AcSensor.Inst().getY()*2;
        sensorVec.x = x < 0 ? -x*x : x*x;     //2乗して変化を大袈裟にする
        sensorVec.y = y < 0 ? -y*y : y*y;     //2乗すると+になるので、負ならマイナスを付ける
        sensorVec.setLengthCap(MAX_SPEED);     //ベクトルの大きさが最大スピード以上にならないようにする
        vec.blend( sensorVec, 0.05f );        //センサーのベクトル方向に実際の移動ベクトルを5%近づける
    }

    private void move(){
        if(circle.x>0+circle.r&&circle.x<MainActivity.getWidthSize()-circle.r){
            circle.x += vec.x;
        }
        else if(circle.x<0+circle.r)circle.x=1+circle.r;
        else if(circle.x>MainActivity.getWidthSize()-circle.r)circle.x=MainActivity.getWidthSize()-1-circle.r;

        if(circle.y>0+circle.r&&circle.y<MainActivity.getHeightSize()-circle.r){
            circle.y += vec.y;
        }
        else if(circle.y<0+circle.r)circle.y=1+circle.r;
        else if(circle.y>MainActivity.getHeightSize()-circle.r)circle.y=MainActivity.getHeightSize()-1-circle.r;
    }

    public Circle getCircle(){
        return circle;
    }

    @Override
    public boolean onUpdate(){
        setVec();
        move();
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

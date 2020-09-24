package com.example.otegarushooting;

public class Vec {
    public float x, y;

    Vec(){
        x = y = 0;
    }
    Vec(float x,float y){
        this.x=x;
        this.y=y;
    }
    public float getAngle(){
        return (float)Math.atan2(y, x);
    }
    float getLength(){
        return (float)Math.sqrt( x*x + y*y );
    }

    //引数の値より大きさが大きければ引数の値にする
    void setLengthCap( float maxLength ){
        float len = getLength();
        if( maxLength == 0 ){
            return; //0割防止
        }
        if( len > maxLength ){//maxLengthより大きければ大きさをmaxLengthにする
            float rate =len/maxLength;
            x /= rate;
            y /= rate;
        }
    }

    // vec方向の向きへrate率ほどブレンドする
    void blend( Vec vec, float rate ){
        float w = vec.x - x;
        float h = vec.y - y;
        x += w*rate;
        y += h*rate;
    }
}

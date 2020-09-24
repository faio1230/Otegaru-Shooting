package com.example.otegarushooting;

import android.content.Context;
import android.graphics.Canvas;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class GameSurfaceView extends SurfaceView implements SurfaceHolder.Callback,Runnable {
    private GameManager gameManager = new GameManager();
    private Thread thread;
    private static TouchState touchState = new TouchState();

    public GameSurfaceView(Context context) {
        super(context);
        getHolder().addCallback(this);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        //解像度情報変更通知
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        thread = new Thread(this);             //別スレッドでメインループを作る
        thread.start();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        thread = null;
    }

    @Override
    public void run() {
        while (thread!=null) { //メインループ
            gameManager.onUpdate();
            onDraw(getHolder());
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event){
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                touchState.state = true;
                touchState.point.x = (int) event.getX();
                touchState.point.y = (int) event.getY();
                break;
            case MotionEvent.ACTION_UP:
                touchState.state = false;
                touchState.hold = false;
                break;
            case MotionEvent.ACTION_MOVE:
                touchState.hold = true;
                touchState.point.x = (int) event.getX();
                touchState.point.y = (int) event.getY();
                break;
            case MotionEvent.ACTION_CANCEL:
                break;
        }
        return true;
    }

    private void onDraw(SurfaceHolder holder) {
        Canvas canvas = holder.lockCanvas();
        if(canvas == null){
            return;
        }
        gameManager.onDraw(canvas); //ここにゲームの描画処理を書く
        holder.unlockCanvasAndPost(canvas);
    }

    public static TouchState getTouchState(){
        return touchState;
    }

}

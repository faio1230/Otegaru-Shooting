package com.example.otegarushooting;

import androidx.appcompat.app.AppCompatActivity;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static int WIDTH,HEIGHT;
    private static List<EnemyData> enemyData;
    GameSurfaceView gameSurfaceView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        enemyData = new CsvReader().enemyReader(this);
        //Log.d("enemy","enemy x = "+enemyData.get(0).x);
        setContentView(gameSurfaceView = new GameSurfaceView(this));
        AcSensor.Inst().onCreate(this); // センサー初期化
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT); //縦画面固定
    }

    @Override
    protected void onResume() { // アクティビティが動き始める時呼ばれる
        super.onResume();
        AcSensor.Inst().onResume();// 開始時にセンサーを動かし始める
    }

    @Override
    protected void onPause() { // アクティビティの動きが止まる時呼ばれる
        super.onPause();
        AcSensor.Inst().onPause();// 中断時にセンサーを止める
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus){
        super.onWindowFocusChanged(hasFocus);
        setDisplaySize(gameSurfaceView);
    }

    private void setDisplaySize(View view){
        WIDTH = view.getWidth();
        HEIGHT = view.getHeight();
        Log.d("tag","width=" + WIDTH);
        Log.d("tag","height=" + HEIGHT);
    }
    public static int getWidthSize(){
        return WIDTH;
    }
    public static int getHeightSize(){
        return HEIGHT;
    }

    public static List<EnemyData> getEnemyData() {
        return enemyData;
    }
}

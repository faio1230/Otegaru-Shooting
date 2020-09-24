package com.example.otegarushooting;

import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class CsvReader {
    static List<EnemyData> objects = new ArrayList<EnemyData>();
    public List<EnemyData> enemyReader(Context context) {
        AssetManager assetManager = context.getResources().getAssets();
        try {
            // CSVファイルの読み込み
            InputStream inputStream = assetManager.open("enemy_data.csv");
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader bufferReader = new BufferedReader(inputStreamReader);
            String line;
            while ((line = bufferReader.readLine()) != null) {

                //カンマ区切りで１つづつ配列に入れる
                EnemyData data = new EnemyData();
                String[] RowData = line.split(",");

                //CSVの左([0]番目)から順番にセット
                data.x=Integer.parseInt(RowData[0]);
                Log.d("enemy",RowData[0]);
                data.y=Integer.parseInt(RowData[1]);
                data.vecX=Integer.parseInt(RowData[2]);
                data.vecY=Integer.parseInt(RowData[3]);
                data.time=Integer.parseInt(RowData[4]);

                objects.add(data);
            }
            bufferReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return objects;
    }
}

package com.example.otegarushooting;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Point;
import android.media.midi.MidiManager;
import android.util.Log;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class GameManager {

    private LinkedList<Task> taskList = new LinkedList<Task>();
    private Player player;
    private List<EnemyData> enemyList = new ArrayList<EnemyData>();
    private long startTime;
    private long endTime=30000;
    private long enemyTime;
    private int enemyCount=0;
    private boolean bulletCharge=true;
    private boolean gameFlag = false;
    private boolean gameStart = false;
    private boolean gameEnd = false;
    private boolean scoreFlag = false;
    private long chargeStart;
    private int score=0;
    private Score scoreText = new Score();
    //private LinkedList<LinkedList<GameObject>> colliderCell = new LinkedList<>(); //= new LinkedList[85];
    //private final Point UNIT_DISTANCE = new Point(MainActivity.getWidthSize()/8,MainActivity.getHeightSize()/8);



    GameManager(){
        taskList.add(scoreText);
        taskList.add(player = new Player());
        //taskList.add(new Enemy());
        enemyList = MainActivity.getEnemyData();

    }

    public boolean onUpdate() {

        if(!gameFlag&&!gameEnd){
            if(!gameStart){
                taskList.add(new StartDisplay());
                gameStart=true;
            }
            if(GameSurfaceView.getTouchState().state&&!GameSurfaceView.getTouchState().hold&&touchAreaCheck(GameSurfaceView.getTouchState().point,
                    MainActivity.getWidthSize()/2-100,MainActivity.getHeightSize()/2-50,MainActivity.getWidthSize()/2+100,MainActivity.getHeightSize()/2+50)){
                gameFlag=true;
                taskList.remove(taskList.size()-1);
                startTime = System.currentTimeMillis();
                enemyTime = System.currentTimeMillis();
                enemyCount=0;
                Log.d("game","GAME START");
            }
            return true;
        }

        if(!gameFlag&&gameEnd){
            if(!scoreFlag){
                taskList.add(new EndDisplay(score));
                scoreFlag=true;
            }
            if(GameSurfaceView.getTouchState().state&&!GameSurfaceView.getTouchState().hold){
                taskList.remove(taskList.size()-1);
                gameEnd=false;
                gameStart=false;
                score=0;
                scoreText.setScore(score);
                for (int i = 0; i < taskList.size(); i++){
                    if(taskList.get(i).getClass()==new Bullet().getClass()||taskList.get(i).getClass()==new Enemy().getClass()){
                        taskList.remove(i);
                        i--;
                    }
                }
            }
            return true;
        }


        if(gameFlag) {
            //Log.d("enemy","number is "+enemyCount  );
            if (enemyCount < enemyList.size() && System.currentTimeMillis() - enemyTime > enemyList.get(enemyCount).time * 1000) {
                taskList.add(new Enemy(enemyList.get(enemyCount).x, enemyList.get(enemyCount).y,
                        enemyList.get(enemyCount).vecX, enemyList.get(enemyCount).vecY));
                enemyCount++;
                if (enemyCount == enemyList.size()) {
                    enemyCount = 0;
                    enemyTime = System.currentTimeMillis();
                }
            }

            if (System.currentTimeMillis() - chargeStart > 500) bulletCharge = true;
            if (GameSurfaceView.getTouchState().state && bulletCharge) { //make Bullets
                TouchState temp = GameSurfaceView.getTouchState();
                Point tempPoint = new Point();
                tempPoint.x = (int) player.getCircle().x;
                tempPoint.y = (int) player.getCircle().y;
                taskList.add(makeBullet(tempPoint, new Vec(temp.point.x - tempPoint.x, temp.point.y - tempPoint.y)));
                bulletCharge = false;
                chargeStart = System.currentTimeMillis();
                //Log.d("Log","Touch x = "+ temp.point.x);
                //Log.d("Log","Touch y = "+ temp.point.y);
            }

            for (int i = 0; i < taskList.size(); i++) {
                int[] tempDeleteList = new int[2];
                if (taskList.get(i).onUpdate() == false) { //更新失敗なら
                    taskList.remove(i);              //そのタスクを消す
                    i--;
                }
                if (taskList.get(i).getType() == 2) {
                    for (int j = 0; j < taskList.size(); j++) {
                        //Log.d("calc","do");
                        if ((taskList.get(i).getCircle().r + taskList.get(j).getCircle().r) * (taskList.get(i).getCircle().r + taskList.get(j).getCircle().r) >
                                calcDintance2(taskList.get(i).getCircle().x, taskList.get(i).getCircle().y,
                                        taskList.get(j).getCircle().x, taskList.get(j).getCircle().y)) {
                            if (taskList.get(j).getType() == 1) {
                                if (taskList.size() >= i && taskList.size() >= j) {
                                    Log.d("DeleteLog", "i: " + i + " j: " + j + " taskListSize: " + taskList.size());
                                    taskList.add(new Effect(taskList.get(j).getCircle().x, taskList.get(j).getCircle().y));
                                    taskList.remove(i);
                                    taskList.remove(j);
                                    i -= 2;
                                    Log.d("Log", "HIT!");
                                    score++;
                                    scoreText.setScore(score);
                                    break;
                                }
                            }
                        }
                    }
                }
            }

            if(System.currentTimeMillis() - startTime > endTime ){
                gameFlag=false;
                gameEnd=true;
                scoreFlag=false;
            }

        }
        return true;

    }

    public void onDraw(Canvas canvas) {
        canvas.drawColor(Color.WHITE);       //白で塗りつぶす
        for(int i=0; i<taskList.size(); i++){
            taskList.get(i).onDraw(canvas);//描画
        }
    }

    private Task makeBullet(Point point, Vec vec){
        return new Bullet(point.x,point.y,vec);
    }

    private boolean colliderCheck(GameObject a,GameObject b){
        if(a.getCircle().r+b.getCircle().r>
        calcDintance2(a.getCircle().x,a.getCircle().x,
                b.getCircle().x,b.getCircle().y)){
            return true;
        }
        return false;
    }

    private double calcDintance2(float x1,float y1,float x2,float y2){
        return (x2-x1)*(x2-x1)+(y2-y1)*(y2-y1);
    }

    private boolean touchAreaCheck(Point touchPoint ,int sx,int sy,int ex,int ey){
        if(sx<touchPoint.x&&touchPoint.x<ex&&sy<touchPoint.y&&touchPoint.y<ey)return true;
        else return false;
    }
    /*
    private int get2DMortonNumber(int x,int y){
        return (bitSeparate32(x) | (bitSeparate32(y)<<1));
    }

    int mortonNumber2ListNumber(int level,int number){
        return number+(int)Math.pow(4,level);
    }

    private int bitSeparate32(int n){
        n=(n|(n<<8)) & 0x00ff00ff;
        n=(n|(n<<4)) & 0x0f0f0f0f;
        n=(n|(n<<2)) & 0x33333333;
        return (n|(n<<1)) & 0x55555555;
    }
    private int getColiderCellNumber(int left,int right){
        int tempNumber = left^right;
        for(int i=0;i<4;i++){
            if(tempNumber == 0x00000000){
                switch(i){
                    case 0:
                        return mortonNumber2ListNumber(0,right);
                    case 1:
                        return mortonNumber2ListNumber(1,right>>2);
                    case 2:
                        return mortonNumber2ListNumber(1,right>>4);
                    case 3:
                        return mortonNumber2ListNumber(1,right>>6);
                }
            }
            tempNumber=tempNumber>>2;
        }
        return tempNumber;
    }

    private void addColliderCell(int number,GameObject object){
        if(colliderCell.isEmpty()){
            for (int i = 0; i < 85; i++) {
                colliderCell.add(new LinkedList<GameObject>());
            }
            colliderCell.get(number).add(object);
        }
    }

    private void updateColliderCell(){
        for (LinkedList<GameObject> objects:
             colliderCell) {
            if(!objects.isEmpty()){
                int i=0;
                for (GameObject object:
                     objects) {
                    int left = get2DMortonNumber((int)(object.getCircle().x-object.getCircle().r),
                            (int)(object.getCircle().y-object.getCircle().r));
                    int right = get2DMortonNumber((int)(object.getCircle().x+object.getCircle().r),
                            (int)(object.getCircle().y+object.getCircle().r));
                    addColliderCell(getColiderCellNumber(left,right),object);
                    objects.remove(i);
                    i++;
                }
            }
        }
    }

    private void checkColliderCell(){

    }
    */
}

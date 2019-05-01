package edu.utep.cs.cs4330.jumpgame;

import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.media.MediaPlayer;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;


public class GameView extends SurfaceView implements Runnable {

    private static String TAG = "GAMEVIEW";


    final static int[] level1 = {1,1,1,1,1,1,1,6,6,6,6,6,6,6,6,1,1,1,1,1,7,6,6,6,6,7,
            6,6,6,6,6,6,8,6,6,6,6,6,8,6,6,6,6,6,7,7,6,6,6,6,6,6,8,8,6,6,6,1,1,1,1,1,1,
            1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1};

    private boolean isRunning;
    private Thread gameThread = null;

    private SurfaceHolder surfaceHolder;
    private Paint paint;
    private Canvas canvas;

    private Player player;
    private ObstacleFactory obstacleFactory;
    //private Queue<Obstacle> obstaclesOnScreen;
    private Obstacle[] obstaclesOnScreen;
    private int numObsInArray;
    private Obstacle obs1;
    private Floor floor;
    private Floor floor2;

    private int[] levelObstacles;
    private int levelCount;
    private boolean lost;
    private int frameTick;

    private MediaPlayer mediaPlayer;

    public GameView(Context context, Point screenSize){
        super(context);

        surfaceHolder = getHolder();
        paint = new Paint();
        canvas = new Canvas();

        //Point point = new Point(200,((screenSize.y - screenSize.y/10) -152));
        Point point = new Point(200,800);

        player = new Player(context, point);

        floor = new Floor(new Point(800, screenSize.y - screenSize.y/10), screenSize.x);
        floor2 = new Floor(new Point(800, screenSize.y/10), screenSize.x);

        //obs1 = new ObstacleSimpleSquare(context, new Point(1500, 550));
        //obs1 = new ObstaclePlatform(context, new Point(3000, 650));
        obstacleFactory = new ObstacleFactory(screenSize.x, screenSize.y);

        obstaclesOnScreen = new Obstacle[(screenSize.x / 100) + 2];
        numObsInArray = 0;

        for(int i = 0; i < 19; i++){
            obstaclesOnScreen[i] = new ObstaclePlatform(new Point(i*108, ((screenSize.y - screenSize.y/10) -51)));
            numObsInArray++;
        }
        //obstaclesOnScreen = new LinkedList<>();
        levelObstacles = level1;
        levelCount = 0;
        frameTick = 0;

        lost = false;
        mediaPlayer = MediaPlayer.create(context, R.raw.bgmusic);
        mediaPlayer.start();
    }

    @Override
    public void run(){
        //isRunning = true;

        while(isRunning){
            try{
                gameThread.sleep(10);
            }catch(InterruptedException e){}
            update();
            //Log.d(TAG, "No crash in update");

            draw();
            //Log.d(TAG, "No crash in draw");

        }
    }

    private void update(){
        this.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                switch(event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        performClick();
                        return true;
                    case MotionEvent.ACTION_UP:
                        return true;

                        case MotionEvent.AC
                }
                return false;
            }
        });
        this.setOnClickListener(view -> player.bufferJump());
        player.update();

        if(frameTick == 7) {
            updateObstaclesOnScreen();
            frameTick = 0;
        }
        for(int i = 0; i < numObsInArray; i++){
            obstaclesOnScreen[i].update();
        }
        if(levelCount >= levelObstacles.length){
            lose();
        }
        //obs1.update();
        floor.update();
        //Log.d(TAG, "about to collide");
        checkCollisions();
        frameTick++;

    }

    private void updateObstaclesOnScreen(){
        if(numObsInArray > 0 && obstaclesOnScreen[0].getPoint().x < -50){
            for(int i = 0; i < obstaclesOnScreen.length -1; i++){
                obstaclesOnScreen[i] = obstaclesOnScreen[i+1];
            }
            numObsInArray--;
        }
        if(!(levelCount >= levelObstacles.length) && levelObstacles[levelCount] > 0){
            obstaclesOnScreen[numObsInArray] = obstacleFactory.createObstacle(levelObstacles[levelCount]);
            numObsInArray++;
        }
        //Log.d(TAG, Integer.toString(levelCount));
        levelCount++;

    }

    @Override
    public boolean performClick(){
        super.performClick();
        player.bufferJump();
        return true;
    }

    private void checkCollisions(){
        //Log.d("GAMEUPDATE", "lost");

        Rect playerRect = player.getRect();
        ///// FIX ME
        if(Rect.intersects(player.getRect(), floor.getFloorLine()) || Rect.intersects(playerRect, floor2.getFloorLine())) {
            player.collidedWithFloor(floor);
            lose();
        }
        for(int i = 0; i < numObsInArray; i++) {
            //Log.d(TAG, "Wait a minuto");
            if(obstaclesOnScreen[i].getNumRects() > 1){
                //Log.d(TAG, "A double");
                for(int j = 1; j <= obstaclesOnScreen[i].getNumRects(); j++){
                    if (Rect.intersects(playerRect, obstaclesOnScreen[i].getRect(j))) {
                        //if(myCollision(playerRect, obs1.getRect())){
                        //obstaclesOnScreen.
                        if (obstaclesOnScreen[i].getIsPlatform()) {
                            player.collidedWithPlatform(obstaclesOnScreen[i].getRect(j));
                            if (Rect.intersects(playerRect, obstaclesOnScreen[i].getRect(j)))
                                lose();
                        } else {
                            lose();
                        }
                    }
                }
            }else{
                Log.d(TAG, Integer.toString(obstaclesOnScreen[i].getNumRects()));
                //Log.d(TAG, "Not double");
                if (Rect.intersects(playerRect, obstaclesOnScreen[i].getRect())) {
                    //if(myCollision(playerRect, obs1.getRect())){
                    //obstaclesOnScreen.
                    if (obstaclesOnScreen[i].getIsPlatform()) {
                        player.collidedWithPlatform(obstaclesOnScreen[i]);
                        if (Rect.intersects(playerRect, obstaclesOnScreen[i].getRect()))
                            lose();
                    } else {
                        lose();
                    }
                }
            }
        }
    }

    private void lose(){
        isRunning = false;
        lost = true;
        Log.d("GAMEUPDATE", "lost");
        getContext().startActivity(new Intent(getContext(), MainActivity.class));
    }

    public boolean getLost(){
        return this.lost;
    }

    private void draw(){
        if(surfaceHolder.getSurface().isValid()) {
            canvas = surfaceHolder.lockCanvas();
            canvas.drawColor(Color.rgb(255, 255, 255));
            player.draw(canvas);
            for(int i = 0; i < numObsInArray; i++){
                //for(int j = 0; j < obstaclesOnScreen[i].getNumRects; j++) {
                    obstaclesOnScreen[i].draw(canvas);
                //}
            }
            //Log.d(TAG, "No crash in draw");

            //obs1.draw(canvas);
            floor.draw(canvas);
            floor2.draw(canvas);
            surfaceHolder.unlockCanvasAndPost(canvas);
        }
    }

    public void pause(){
        isRunning = false;
        mediaPlayer.pause();
        try{
            gameThread.join();
        }catch(InterruptedException e){}
    }

    public void resume(){
        isRunning = true;
        gameThread = new Thread(this);
        gameThread.start();
        mediaPlayer.start();
        //run();
    }
}

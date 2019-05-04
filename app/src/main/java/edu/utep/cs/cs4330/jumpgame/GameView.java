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
import android.widget.TextView;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;


public class GameView extends SurfaceView implements Runnable {

    private static String TAG = "GAMEVIEW";


    final static int[] level1 = {5,1,1,1,1,1,5,1,1,6,6,6,6,6,6,6,6,1,1,1,1,1,7,6,6,6,6,7,
            6,6,6,6,6,6,8,6,6,6,6,6,8,6,6,6,6,6,7,7,6,6,6,6,6,6,8,8,6,6,6,1,1,1,1,1,1,
            1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1};

    private Context context;

    private boolean isRunning;
    public Thread gameThread = null;

    private SurfaceHolder surfaceHolder;
    private Paint paint;
    private Canvas canvas;
    private int screenWidth;
    private int screenHeight;

    private PauseButton pauseButton;
    private boolean gamePaused;
    private PauseMenu pauseMenu;

    private Player player;
    private ObstacleFactory obstacleFactory;
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

    private TextView attemptText;
    private int attemptCount;

    public GameView(Context context, Point screenSize){
        super(context);

        this.context = context;
        surfaceHolder = getHolder();
        paint = new Paint();
        canvas = new Canvas();

        screenHeight = screenSize.y;
        screenWidth = screenSize.x;

        Point point = new Point(200,800);

        player = new Player(context, point);

        floor = new Floor(new Point(screenSize.x/2, screenSize.y - screenSize.y/10), screenSize.x);
        floor2 = new Floor(new Point(screenSize.x/2, screenSize.y/10), screenSize.x);

        obstacleFactory = new ObstacleFactory(screenSize.x, screenSize.y);

        obstaclesOnScreen = new Obstacle[(screenSize.x / 100) + 2];
        numObsInArray = 0;

        for(int i = 0; i < 19; i++){
            obstaclesOnScreen[i] = new ObstaclePlatform(new Point(i*108, ((screenSize.y - screenSize.y/10) -51)));
            numObsInArray++;
        }

        levelObstacles = level1;
        levelCount = 0;
        frameTick = 0;


        attemptCount = 1;
        lost = false;

        pauseMenu = new PauseMenu(screenSize);
        gamePaused = false;
        pauseButton = new PauseButton(new Point(100, screenSize.y/10 - 50));
        this.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                switch(event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        if(!gamePaused) {
                            if (event.getX() >= pauseButton.getRect().left && event.getX() <= pauseButton.getRect().right
                                    && event.getY() <= pauseButton.getRect().bottom && event.getY() >= pauseButton.getRect().top)
                                gamePaused = true;
                            else
                                performClick();
                        }else{
                            if (event.getX() >= pauseMenu.getExitRect().left && event.getX() <= pauseMenu.getExitRect().right
                                    && event.getY() <= pauseMenu.getExitRect().bottom && event.getY() >= pauseMenu.getExitRect().top)
                                exitGame();
                            else if(event.getX() >= pauseMenu.getResumeRect().left && event.getX() <= pauseMenu.getResumeRect().right
                                    && event.getY() <= pauseMenu.getResumeRect().bottom && event.getY() >= pauseMenu.getResumeRect().top)
                                resumeGame();
                        }

                        return true;
                    case MotionEvent.ACTION_UP:
                        return true;
                }
                return false;
            }
        });

        mediaPlayer = MediaPlayer.create(context, R.raw.bgmusic);
        mediaPlayer.start();

    }

    @Override
    public void run(){
        //isRunning = true;

        while(isRunning){
            //Log.d(TAG, "Loop");
            if(!gamePaused) {
                try {
                    gameThread.sleep(10);
                } catch (InterruptedException e) {
                }
                update();
                //Log.d(TAG, "No crash in update");

                draw();

                if (lost)
                    lose();
                if (gamePaused)
                    pauseGame();
                //Log.d(TAG, "No crash in draw");
            }
        }
        Log.d(TAG, "Exit");
    }

    private void update(){
        player.update();

        if(frameTick == 7) {
            updateObstaclesOnScreen();
            frameTick = 0;
        }
        for(int i = 0; i < numObsInArray; i++){
            obstaclesOnScreen[i].update();
        }
        if(levelCount >= levelObstacles.length){
            lost = true;
        }
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
        levelCount++;

    }

    @Override
    public boolean performClick(){
        super.performClick();
        player.bufferJump();
        return true;
    }

    private void checkCollisions(){
        Rect playerRect = player.getRect();
        ///// FIX ME
        if(Rect.intersects(player.getRect(), floor.getFloorLine()) || Rect.intersects(playerRect, floor2.getFloorLine())) {
            player.collidedWithFloor(floor);
            lost = true;
        }
        for(int i = 0; i < numObsInArray; i++) {
            if(obstaclesOnScreen[i].getNumRects() > 1){
                for(int j = 1; j <= obstaclesOnScreen[i].getNumRects(); j++){
                    if (Rect.intersects(playerRect, obstaclesOnScreen[i].getRect(j))) {
                        ObstacleType obsType = obstaclesOnScreen[i].getType();

                        switch(obsType) {
                            case OBSTACLE:
                                lost = true;
                                break;
                            case PLATFORM:
                                player.collidedWithPlatform(obstaclesOnScreen[i].getRect(j));
                                if (Rect.intersects(playerRect, obstaclesOnScreen[i].getRect(j)))
                                    lost = true;
                                break;
                        }
                    }
                }
            }else{
                if (Rect.intersects(playerRect, obstaclesOnScreen[i].getRect())) {
                    ObstacleType obsType = obstaclesOnScreen[i].getType();
                    switch(obsType) {
                        case OBSTACLE:
                            lost = true;
                            break;
                        case PLATFORM:
                            player.collidedWithPlatform(obstaclesOnScreen[i].getRect());
                            if (Rect.intersects(playerRect, obstaclesOnScreen[i].getRect()))
                                lost = true;
                            break;
                    }
                }
            }
        }
    }

    private void lose(){
        Log.d("GAMEUPDATE", "lost");
        Point point = new Point(200,800);
        //player = new Player(context, point);
        player.reset();
        obstacleFactory.reset();
        numObsInArray = 0;

        mediaPlayer.stop();
        //mediaPlayer.reset();
        for(int i = 0; i < 19; i++){
            obstaclesOnScreen[i] = new ObstaclePlatform(new Point(i*108, ((screenHeight - screenHeight/10) -51)));
            numObsInArray++;
        }
        frameTick = 0;
        levelCount = 0;

        attemptCount++;

        lost = false;
        mediaPlayer.start();

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
                obstaclesOnScreen[i].draw(canvas);
            }
            floor.draw(canvas);
            floor2.draw(canvas);
            drawText();
            pauseButton.draw(canvas);
            //pauseMenu.draw(canvas);
            surfaceHolder.unlockCanvasAndPost(canvas);
        }
    }

    public void drawText(){

        Log.d(TAG, "Draw le text");
        Paint paint = new Paint();
        paint.setColor(Color.rgb(0,0,0));
        paint.setTextSize(48);
        canvas.drawText(("Attempt: " + attemptCount), screenWidth - 300,screenHeight/10 - 50, paint);

    }

    public void pauseGame(){
        mediaPlayer.pause();
        //isRunning = false;
        gamePaused = true;
        if(surfaceHolder.getSurface().isValid()) {
            canvas = surfaceHolder.lockCanvas();
            pauseMenu.draw(canvas);
            surfaceHolder.unlockCanvasAndPost(canvas);
        }
    }

    public void resumeGame(){
        gamePaused = false;
        mediaPlayer.start();
    }

    public void exitGame(){
        //getContext().startActivity(new Intent(getContext(), MainActivity.class));
        //finish();
        isRunning = false;
        Intent i = new Intent(context, MainActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        context.startActivity(i);
        //context.
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

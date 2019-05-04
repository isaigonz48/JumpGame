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
    private Thread gameThread = null;

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
        //Point point = new Point(200,((screenSize.y - screenSize.y/10) -152));
        Point point = new Point(200,800);

        player = new Player(context, point);

        floor = new Floor(new Point(screenSize.x/2, screenSize.y - screenSize.y/10), screenSize.x);
        floor2 = new Floor(new Point(screenSize.x/2, screenSize.y/10), screenSize.x);

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


        attemptCount = 1;
        //attemptText.
        //attemptText.setText("Attempt: " + attemptCount);
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
                        //performClick();

                        //case MotionEvent.ACTION_MOVE:
                        //  player.bufferGravity();

                        return true;

                    //case MotionEvent.

                    //case MotionEvent.AC
                }
                return false;
            }
        });


        //pauseButton.getRect()

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

        //this.setOnLongClickListener(view ->{
        //    player.bufferGravity();
        //    return true;
        //});
        //this.setOnClickListener(view -> player.bufferJump());
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
            lost = true;
        }
        for(int i = 0; i < numObsInArray; i++) {
            //Log.d(TAG, "Wait a minuto");
            if(obstaclesOnScreen[i].getNumRects() > 1){
                //Log.d(TAG, "A double");
                for(int j = 1; j <= obstaclesOnScreen[i].getNumRects(); j++){
                    if (Rect.intersects(playerRect, obstaclesOnScreen[i].getRect(j))) {
                        ObstacleType obsType = obstaclesOnScreen[i].getType();

                        switch(obsType) {
                            //if(myCollision(playerRect, obs1.getRect())){
                            //obstaclesOnScreen.
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
                //Log.d(TAG, Integer.toString(obstaclesOnScreen[i].getNumRects()));
                //Log.d(TAG, "Not double");
                if (Rect.intersects(playerRect, obstaclesOnScreen[i].getRect())) {
                    //if(myCollision(playerRect, obs1.getRect())){
                    //obstaclesOnScreen.
                    ObstacleType obsType = obstaclesOnScreen[i].getType();

                    switch(obsType) {
                        //if(myCollision(playerRect, obs1.getRect())){
                        //obstaclesOnScreen.
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
        //isRunning = false;
        //try {
            //Log.d(TAG, "WORK PLSSSS");
            //gameThread.join();
            //Log.d(TAG, "WORK PLSSSS");

        //}catch(InterruptedException e){}
        //lost = true;
        Log.d("GAMEUPDATE", "lost");
        Point point = new Point(200,800);
        //player = new Player(context, point);
        player.reset();
        obstacleFactory.reset();
        numObsInArray = 0;

        //obstaclesOnScreen = new Obstacle[(screenWidth / 100) + 2];

        //mediaPlayer.reset();
        for(int i = 0; i < 19; i++){
            obstaclesOnScreen[i] = new ObstaclePlatform(new Point(i*108, ((screenHeight - screenHeight/10) -51)));
            numObsInArray++;
        }
        frameTick = 0;
        levelCount = 0;

        attemptCount++;

        //attemptText.setText("Attempt: " + attemptCount);


        lost = false;

        //mediaPlayer.reset();


        //isRunning = true;

        //gameThread = new Thread(this);
        //gameThread.start();
        //try {
            //gameThread.;
        //}catch(InterruptedException e){}

        //gameThread = new Thread(this);
        //gameThread.start();
        //mediaPlayer.reset();
        //mediaPlayer.rese
        //gameThread.join();
        //getContext().startActivity(new Intent(getContext(), MainActivity.class));
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
            drawText();
            pauseButton.draw(canvas);
            //pauseMenu.draw(canvas);
            surfaceHolder.unlockCanvasAndPost(canvas);
        }
    }

    public void drawText(){

        Log.d(TAG, "Draw le text");
        //canvas = surfaceHolder.lockCanvas();
        Paint paint = new Paint();
        paint.setColor(Color.rgb(0,0,0));
        //canvas.drawColor(Color.rgb(0, 0, 0));
        paint.setTextSize(48);
        canvas.drawText(("Attempt: " + attemptCount), screenWidth - 300,screenHeight/10 - 50, paint);
        //canvas.drawText(("Attempt: + attemptCount"), 500,500, paint);

    }

    public void pauseGame(){
        mediaPlayer.pause();
        //isRunning = false;
        gamePaused = true;
        if(surfaceHolder.getSurface().isValid()) {
            canvas = surfaceHolder.lockCanvas();
            //canvas.drawColor(Color.rgb(255, 255, 255));
            pauseMenu.draw(canvas);
            surfaceHolder.unlockCanvasAndPost(canvas);
        }
    }

    public void resumeGame(){
        gamePaused = false;
        mediaPlayer.start();
    }

    public void exitGame(){
        getContext().startActivity(new Intent(getContext(), MainActivity.class));
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

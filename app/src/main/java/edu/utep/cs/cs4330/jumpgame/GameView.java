package edu.utep.cs.cs4330.jumpgame;

import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;


public class GameView extends SurfaceView implements Runnable {

    private static String TAG = "GAMEVIEW";

    final static int[] level1 = {2};

    private boolean isRunning;
    private Thread gameThread = null;

    private SurfaceHolder surfaceHolder;
    private Paint paint;
    private Canvas canvas;

    private Player player;
    private Obstacle obs1;
    private Floor floor;

    private int[] obstacles;
    private boolean lost;

    public GameView(Context context, Point screenSize){
        super(context);

        surfaceHolder = getHolder();
        paint = new Paint();
        canvas = new Canvas();

        Point point = new Point(200,550);
        player = new Player(context, point);

        floor = new Floor(new Point(800, 600), screenSize.x);
        //obs1 = new ObstacleSimpleSquare(context, new Point(1500, 550));
        obs1 = new ObstaclePlatform(context, new Point(3000, 550));
        this.obstacles = level1;

        lost = false;
    }

    @Override
    public void run(){
        //isRunning = true;

        while(isRunning){
            try{
                gameThread.sleep(5);
            }catch(InterruptedException e){}
            update();
            draw();

        }
    }
    /*@Override
    public boolean performClick() {
        player.bufferJump();

        return super.performClick();

    }*/


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
                }
                return false;
            }
        });
        this.setOnClickListener(view -> player.bufferJump());
        player.update();

        obs1.update();
        floor.update();
        checkCollisions();

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
        if(Rect.intersects(player.getRect(), floor.getFloorLine())) {
            player.collidedWithFloor();
        }
        if(Rect.intersects(playerRect, obs1.getRect())){
        //if(myCollision(playerRect, obs1.getRect())){
            if(obs1.getIsPlatform()){
                player.collidedWithPlatform(obs1);

                Log.d(TAG, "Player y: " + Integer.toString(player.getPoint().y));


                Log.d(TAG, "Player bottom: " + Integer.toString(playerRect.bottom));
                Log.d(TAG, "Player top: " + Integer.toString(playerRect.top));
                Log.d(TAG, "Player left: " + Integer.toString(playerRect.left));
                Log.d(TAG, "Player right: " + Integer.toString(playerRect.right));

                Log.d(TAG, Integer.toString(obs1.getPoint().y));

                Log.d(TAG, Integer.toString(obs1.getRect().bottom));
                Log.d(TAG, Integer.toString(obs1.getRect().top));

                Log.d(TAG, Integer.toString(obs1.getRect().left));
                Log.d(TAG, Integer.toString(obs1.getRect().right));


                if(Rect.intersects(playerRect, obs1.getRect()))
                    lose();
            }else{
                lose();
            }
        }


     /*   //Rect.intersects(player.getRect(),obs1.getRect())){
        if(obs1.getIsPlatform()){
            //if()
            player.collidedWithPlatform(obs1);

        }else{
            //if();
            ///// left top right bottom
            //player.getRectangle().intersects(obs1.getRect().left,obs1.getRect().top,obs1.getRect().right,obs1.getRect().bottom);
            lose();
            //player.setX(-100);
            //Log.d("GAMEUPDATE", "lost");
        }
    }*/
    }

    private boolean myCollision(Rect r1, Rect r2){
        if(r1.top <= r2.bottom && r1.bottom >= r2.top
                    && r1.left <= r2.right && r1.right >= r2.left)
            return true;

        return false;
    }

    private boolean directHit(Obstacle o){
        //player.getRect().
        Rect playerRect = player.getRect();
        Rect obsRect = o.getRect();
        if(playerRect.right > obsRect.left
                && playerRect.bottom > obsRect.top)
            return true;

        return false;
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
            obs1.draw(canvas);
            floor.draw(canvas);
            surfaceHolder.unlockCanvasAndPost(canvas);
        }
    }

    public void pause(){
        isRunning = false;
        try{
            gameThread.join();
        }catch(InterruptedException e){}
    }

    public void resume(){
        isRunning = true;
        gameThread = new Thread(this);
        gameThread.start();
        //run();
    }
}

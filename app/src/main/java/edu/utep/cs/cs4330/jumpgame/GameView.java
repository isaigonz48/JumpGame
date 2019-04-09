package edu.utep.cs.cs4330.jumpgame;

import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

public class GameView extends SurfaceView implements Runnable {

    private boolean isRunning;
    private Thread gameThread = null;

    private SurfaceHolder surfaceHolder;
    private Paint paint;
    private Canvas canvas;

    private Player player;
    private ObstacleSimpleSquare obs1;

    private boolean lost;

    public GameView(Context context){
        super(context);

        surfaceHolder = getHolder();
        paint = new Paint();
        canvas = new Canvas();

        Point point = new Point(200,550);
        player = new Player(context, point);
        obs1 = new ObstacleSimpleSquare(context, new Point(1500, 550));

        lost = false;
    }

    @Override
    public void run(){
        //isRunning = true;

        while(isRunning){
            try{
                gameThread.sleep(17);
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
        this.setOnClickListener(view ->{
            player.bufferJump();
        });
        player.update();
        obs1.update();
        checkCollision();

    }

    private void checkCollision(){
        //Log.d("GAMEUPDATE", "lost");

        if(Rect.intersects(player.getRect(),obs1.getRect())){
            ///// left top right bottom
            //player.getRectangle().intersects(obs1.getRect().left,obs1.getRect().top,obs1.getRect().right,obs1.getRect().bottom);
            lose();
            //player.setX(-100);
            //Log.d("GAMEUPDATE", "lost");
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
            obs1.draw(canvas);
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

package edu.utep.cs.cs4330.jumpgame;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
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

    public GameView(Context context){
        super(context);

        surfaceHolder = getHolder();
        paint = new Paint();
        canvas = new Canvas();

        Point point = new Point(100,550);
        player = new Player(context, point);
    }

    @Override
    public void run(){
        //isRunning = true;

        while(isRunning){
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
    }

    private void draw(){
        if(surfaceHolder.getSurface().isValid()) {
            canvas = surfaceHolder.lockCanvas();
            canvas.drawColor(Color.rgb(0, 0, 150));
            player.draw(canvas);
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

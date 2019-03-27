package edu.utep.cs.cs4330.jumpgame;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.view.SurfaceView;

public class GameView extends SurfaceView {

    private boolean isRunning;

    private Paint paint;
    private Canvas canvas;

    private Player player;

    public GameView(Context context){
        super(context);

        paint = new Paint();
        canvas = new Canvas();

        Point point = new Point(100,100);
        player = new Player(point);
    }

    public void run(){
        isRunning = true;

        while(isRunning){
            update();
            draw();

        }
    }

    private void update(){

    }

    private void draw(){
        canvas.drawColor(Color.rgb(0,0,0));
        player.draw(canvas);
    }
}

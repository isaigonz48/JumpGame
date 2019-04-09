package edu.utep.cs.cs4330.jumpgame;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;

public class Obstacle {
    //private Bitmap model;
    //private final int PLAYER_GRAVITY = 5;
    //private final int INITIAL_Y = 550;

    //private Rect rect;
    //private int rectColor;
    protected Point point;
    protected int color;

    //private boolean jumpBuffer;
    //private boolean isJumping;
    protected int xVel;
    protected int yVel;
    //protected double xVel;


    public Obstacle(){
        //this.rect = new Rect(100,500,200,600);
        color = Color.rgb(0,0,0);
        this.point = new Point(0,0);
       // new Po
        this.xVel = -10;
        this.yVel = 0;
    }

    public Obstacle(Point point){
        //this.rect = new Rect(100,500,200,600);
        color = Color.rgb(0,0,0);
        this.color = Color.rgb(0,0,0);
        this.point = point;
    }

    public Obstacle(Context context, Point point){
        //this.rect = new Rect(100,500,200,600);
        color = Color.rgb(0,0,0);
        this.color = Color.rgb(0,0,0);
        this.point = point;
    }

    public Obstacle(int color, Point point){
        //this.rect = rectangle;
        this.color = color;
        this.point = point;
    }

    public Point getPoint(){
        return this.point;
    }

    public int getYVel(){
        return this.yVel;
    }

    public int getXVel(){
        return this.xVel;
    }

    public void draw(Canvas canvas){
        //canvas.drawColor(rectColor);
        //Paint paint = new Paint();
        //paint.setColor(this.color);
        //this.rect.set(this.point.x-50,this.point.y+50,this.point.x+50, this.point.y-50);
        //canvas.drawRect(this.rect, paint);
    }

    public void update(){
        this.point.x += this.xVel;
        this.point.y += this.yVel;
    }
}

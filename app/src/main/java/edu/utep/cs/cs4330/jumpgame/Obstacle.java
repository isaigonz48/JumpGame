package edu.utep.cs.cs4330.jumpgame;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;

public class Obstacle {


    protected Rect rect;
    protected Point point;
    protected int color;

    protected int xVel;
    protected int yVel;

    protected boolean isPlatform;
    protected int numRects;


    public Obstacle(){

        color = Color.rgb(0,0,0);
        this.point = new Point(0,0);
        this.xVel = -25;
        this.yVel = 0;
        isPlatform = false;
        numRects = 1;
    }

    public Obstacle(Point point){
        //this.rect = new Rect(100,500,200,600);
        color = Color.rgb(0,0,0);
        this.color = Color.rgb(0,0,0);
        this.point = point;
        isPlatform = false;
        numRects = 1;
    }

    public Obstacle(Context context, Point point){
        //this.rect = new Rect(100,500,200,600);
        color = Color.rgb(0,0,0);
        this.color = Color.rgb(0,0,0);
        this.point = point;
        isPlatform = false;
        numRects = 1;
    }

    public Obstacle(int color, Point point){
        //this.rect = rectangle;
        this.color = color;
        this.point = point;
        isPlatform = false;
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

    public Rect getRect(){
        return this.rect;
    }

    public Rect getRect(int i){
        return this.rect;
    }

    public int getNumRects(){
        return this.numRects;
    }

    public boolean getIsPlatform(){
        return this.isPlatform;
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

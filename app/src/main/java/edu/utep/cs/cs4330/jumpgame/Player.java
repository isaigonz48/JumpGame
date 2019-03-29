package edu.utep.cs.cs4330.jumpgame;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.support.constraint.solver.widgets.Rectangle;

import java.util.Vector;

public class Player {
    //private Bitmap model;
    private final int PLAYER_GRAVITY = 5;
    private final int INITIAL_Y = 550;

    private Rect rect;
    private int rectColor;
    private Point point;

    private boolean jumpBuffer;
    private boolean isJumping;
    private int xVel;
    private int yVel;


    public Player(){
        this.rect = new Rect(100,500,200,600);
        rectColor = Color.rgb(255,0,0);
        this.point = new Point(0,0);
        this.jumpBuffer = false;
        this.isJumping = false;
        this.xVel = 0;
        this.yVel = 0;
    }

    public Player(Point point){
        this.rect = new Rect(100,500,200,600);
        rectColor = Color.rgb(255,0,0);
        this.point = point;
    }

    public Player(Rect rectangle, int color, Point point){
        this.rect = rectangle;
        this.rectColor = color;
        this.point = point;
    }

    public Rect getRectangle(){
        return this.rect;
    }

    public Point getPoint(){
        return this.point;
    }

    public void bufferJump(){
        this.jumpBuffer = true;
    }

    public void draw(Canvas canvas){
        //canvas.drawColor(rectColor);
        Paint paint = new Paint();
        paint.setColor(this.rectColor);
        this.rect.set(100,this.point.y+50,200, this.point.y-50);
        canvas.drawRect(this.rect, paint);
    }

    public void update(){
        if(!isJumping) {
            if(this.jumpBuffer) {
                yVel = -50;
                isJumping = true;
            }
        }else{
            jump();
        }
        jumpBuffer = false;
        this.point.y += yVel;
    }

    private void jump(){

        this.yVel += PLAYER_GRAVITY;
        if(this.point.y == INITIAL_Y) {
            isJumping = false;
            yVel = 0;
        }
    }
}

package edu.utep.cs.cs4330.jumpgame;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.support.constraint.solver.widgets.Rectangle;

public class Player {
    //private Bitmap model;
    private Rect rect;
    private int rectColor;
    private Point point;

    public Player(){
        this.rect = new Rect(100,100,200,200);
        rectColor = Color.rgb(255,0,0);
        this.point = new Point(0,0);
    }

    public Player(Point point){
        this.rect = new Rect(100,100,200,200);
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

    public void draw(Canvas canvas){
        //canvas.drawColor(rectColor);
        Paint paint = new Paint();
        paint.setColor(this.rectColor);
        canvas.drawRect(this.rect, paint);
    }


}

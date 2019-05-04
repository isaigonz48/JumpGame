package edu.utep.cs.cs4330.jumpgame;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;

public class PauseMenu {


    protected Rect menuRect;
    protected Point point;
    protected int color;
    protected int halfWidth;
    private int halfHeight;

    private Rect exitRect;

    private Rect resumeRect;



    public PauseMenu(Point screenSize){
        //super();
        this.halfWidth = screenSize.x / 3;
        this.halfHeight = screenSize.y / 4;
        //this.rect = new Rect(100,500,200,600);
        color = Color.rgb(255,0,0);
        this.color = Color.rgb(0,0,0);
        this.point = new Point(screenSize.x/2, screenSize.y/2);
        this.menuRect = new Rect(point.x - halfWidth, point.y - halfHeight,
                point.x + halfWidth, point.y + halfHeight);

        int buttonHalfWidth = halfWidth / 4;
        int buttonHalfHeight = halfHeight / 4;
        Point exitPoint = new Point(point.x - halfWidth/2, point.y + halfHeight/2);
        this.exitRect = new Rect(exitPoint.x - buttonHalfWidth, exitPoint.y - buttonHalfHeight,
                exitPoint.x + buttonHalfWidth, exitPoint.y + buttonHalfHeight);

        Point resumePoint = new Point(point.x + halfWidth/2, point.y + halfHeight/2);

        this.resumeRect = new Rect(resumePoint.x - buttonHalfWidth, resumePoint.y - buttonHalfHeight,
                resumePoint.x + buttonHalfWidth, resumePoint.y + buttonHalfHeight);

    }


    public Point getPoint(){
        return this.point;
    }


    public Rect getMenuRect(){
        return this.menuRect;
    }

    public Rect getExitRect(){
        return this.exitRect;
    }
    public Rect getResumeRect(){
        return this.resumeRect;
    }

    public void draw(Canvas canvas){
        Paint paint = new Paint();
        paint.setColor(this.color);
        canvas.drawRect(this.menuRect, paint);

        paint.setColor(Color.rgb(255,0,0));
        canvas.drawRect(this.exitRect,paint);
        canvas.drawRect(this.resumeRect,paint);
        //this.rect.set(this.point.x-50,this.point.y+50,this.point.x+50, this.point.y-50);
        //canvas.drawRect(this.rect, paint);
    }

    public void update(){


    }
}
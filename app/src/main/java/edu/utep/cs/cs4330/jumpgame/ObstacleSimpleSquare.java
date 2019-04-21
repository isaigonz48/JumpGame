package edu.utep.cs.cs4330.jumpgame;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.util.Log;

import java.time.LocalDate;

public class ObstacleSimpleSquare extends Obstacle {
    private Rect rect;
    private int halfWidth;


    public ObstacleSimpleSquare(Point point) {
        super(point);
        this.halfWidth = 50;
        this.color = Color.rgb(255,0,0);
        this.rect = new Rect(point.x - halfWidth, point.y - halfWidth,
                point.x + halfWidth, point.y + halfWidth);

        this.xVel = -15;

    }

    public ObstacleSimpleSquare(Context context, Point point) {
        super(context, point);
        this.halfWidth = 50;
        this.rect = new Rect(point.x - halfWidth, point.y - halfWidth,
                point.x + halfWidth, point.y + halfWidth);

        //this.rect = new Rect(point.x + 50, point.y - 50, point.x - 50, point.y + 50);
        this.xVel = -15;
    }

    @Override
    public void draw(Canvas canvas) {
        //super.draw(canvas);
        Paint paint = new Paint();
        paint.setColor(this.color);
        canvas.drawRect(this.rect, paint);
    }

    @Override
    public void update() {
        super.update();
        //this.rect.set(this.point.x-halfWidth,this.point.y+halfWidth,
          //      this.point.x+halfWidth, this.point.y-halfWidth);
        //this.rect.set(this.point.x-halfWidth,this.point.y+halfWidth,
          //      this.point.x+halfWidth, this.point.y-halfWidth);

        this.rect.left = point.x-halfWidth;
        this.rect.top = point.y-halfWidth;
        this.rect.right = point.x+halfWidth;
        this.rect.bottom = point.y+halfWidth;

        //Log.d("OBSUPDATE", ("" + this.rect.left));


    }

    public Rect getRect(){
        return this.rect;
    }
}

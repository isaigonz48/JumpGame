package edu.utep.cs.cs4330.jumpgame;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;

public class ObstacleDoublePlatform extends Obstacle {

    //private boolean isPlatform;
    private Rect rect2;
    private Point point2;
    private int halfWidth;


    public ObstacleDoublePlatform(Point point, Point point2) {
        super(point);
        this.point2 = point2;

        this.halfWidth = 50;
        this.rect = new Rect(point.x - halfWidth, point.y - halfWidth,
                point.x + halfWidth, point.y + halfWidth);

        this.rect2 = new Rect(point2.x - halfWidth, point2.y - halfWidth,
                point2.x + halfWidth, point2.y + halfWidth);

        //this.rect = new Rect(point.x + 50, point.y - 50, point.x - 50, point.y + 50);
        this.xVel = -15;
        isPlatform = true;
        numRects = 2;
    }

    public ObstacleDoublePlatform(Context context, Point point, Point point2) {
        super(context, point);
        this.point2 = point2;
        this.halfWidth = 50;
        this.rect = new Rect(point.x - halfWidth, point.y - halfWidth,
                point.x + halfWidth, point.y + halfWidth);

        this.rect2 = new Rect(point2.x - halfWidth, point2.y - halfWidth,
                point2.x + halfWidth, point2.y + halfWidth);
        //this.rect = new Rect(point.x + 50, point.y - 50, point.x - 50, point.y + 50);
        this.xVel = -15;
        isPlatform = true;
        numRects = 2;
    }

    @Override
    public void draw(Canvas canvas) {
        //super.draw(canvas);
        Paint paint = new Paint();
        paint.setColor(this.color);
        canvas.drawRect(this.rect, paint);
        canvas.drawRect(this.rect2, paint);
    }

    @Override
    public void update() {
        super.update();

        this.point2.x += xVel;
        this.point2.y += yVel;


        //this.rect.set(this.point.x-halfWidth,this.point.y+halfWidth,
        //      this.point.x+halfWidth, this.point.y-halfWidth);
        //this.rect.set(this.point.x-halfWidth,this.point.y+halfWidth,
        //      this.point.x+halfWidth, this.point.y-halfWidth);

        this.rect.left = point.x-halfWidth;
        this.rect.top = point.y-halfWidth;
        this.rect.right = point.x+halfWidth;
        this.rect.bottom = point.y+halfWidth;
        this.rect2.left = point2.x-halfWidth;
        this.rect2.top = point2.y-halfWidth;
        this.rect2.right = point2.x+halfWidth;
        this.rect2.bottom = point2.y+halfWidth;

        //Log.d("OBSUPDATE", ("" + this.rect.left));


    }

    public Rect getRect(int i){
        if(i == 2)
            return this.rect2;
        else
            return this.rect;
    }
}

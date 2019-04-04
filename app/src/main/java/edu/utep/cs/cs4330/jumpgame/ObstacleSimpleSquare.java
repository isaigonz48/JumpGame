package edu.utep.cs.cs4330.jumpgame;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;

public class ObstacleSimpleSquare extends Obstacle {
    private Rect rect;


    public ObstacleSimpleSquare(Point point) {
        super(point);
        this.rect = new Rect(point.x + 50, point.y - 50, point.x - 50, point.y + 50);

    }

    @Override
    public void draw(Canvas canvas) {
        //super.draw(canvas);
        Paint paint = new Paint();
        paint.setColor(this.color);
        this.rect.set(100,this.point.y+50,200, this.point.y-50);
        canvas.drawRect(this.rect, paint);
    }

    @Override
    public void update() {
        super.update();
    }
}

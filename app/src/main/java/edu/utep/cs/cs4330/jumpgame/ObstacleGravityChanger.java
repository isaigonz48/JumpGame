package edu.utep.cs.cs4330.jumpgame;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;

public class ObstacleGravityChanger extends Obstacle {

    private Bitmap baseModel;
    private Point basePoint;
    private int baseHalfWidth;

    public ObstacleGravityChanger(Context context, Point point, int dir) {
        Matrix matrix = new Matrix();
        if(dir == 0)
            this.model = BitmapFactory.decodeResource(context.getResources(), R.drawable.gravity_up);
        else
            this.model = BitmapFactory.decodeResource(context.getResources(), R.drawable.gravity_down);

        int bWidth = model.getWidth();
        int bHeight = model.getHeight();
        matrix.postScale((float)100/bWidth, (float) 100/bHeight);
        model = Bitmap.createBitmap(model,0,0,bHeight,bWidth,matrix,false);


        color = Color.rgb(0,0,0);
        this.color = Color.rgb(0,0,0);
        this.point = point;
        this.xVel = -22;
        //this.xVel = -15;


        type = ObstacleType.OBSTACLE;
        numRects = 1;
        //super(context,point);
        this.color = Color.rgb(0,255,0);

        basePoint = new Point();
        basePoint.x = point.x;
        if(dir == 0)
            basePoint.y = point.y + 100;
        else
            basePoint.y = point.y - 100;
        matrix = new Matrix();
        this.baseModel = BitmapFactory.decodeResource(context.getResources(), R.drawable.basic_platform);
        bWidth = baseModel.getWidth();
        bHeight = baseModel.getHeight();
        matrix.postScale((float)105/bWidth, (float) 105/bHeight);
        baseModel = Bitmap.createBitmap(baseModel,0,0,bHeight,bWidth,matrix,false);

        //super(context, point);
        this.baseHalfWidth = 50;

        this.halfWidth = 50;
        this.rect = new Rect(point.x - halfWidth, point.y - halfWidth,
                point.x + halfWidth, point.y + halfWidth);

        //this.rect = new Rect(point.x + 50, point.y - 50, point.x - 50, point.y + 50);
        //this.xVel = -15;
        type = ObstacleType.GRAVITY_CHANGER;

    }

    @Override
    public void draw(Canvas canvas){
        super.draw(canvas);
        Paint paint = new Paint();
        paint.setColor(this.color);

        canvas.drawBitmap(baseModel,basePoint.x - baseHalfWidth, basePoint.y - baseHalfWidth, paint);
    }

    @Override
    public void update(){
        super.update();
        this.basePoint.x += xVel;
        this.basePoint.y += yVel;
    }

}

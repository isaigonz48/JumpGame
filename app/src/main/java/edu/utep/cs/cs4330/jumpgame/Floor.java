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

public class Floor {
   // protected int screenWidth;

    protected Rect floorLine;
    protected int floorColor;

    protected int thickness;

    protected Point point;
    protected int yVel;

    private Bitmap model;

    public Floor(){
        this.thickness = 10;
        floorColor = Color.rgb(255,0,0);
        this.point = new Point(800, 605);
        this.floorLine = new Rect(0, point.y,
                1600, point.y + thickness);

        this.yVel = 0;
    }

    public Floor(Context context, Point point, int screenWidth){
        //Matrix matrix = new Matrix();
        //this.model = BitmapFactory.decodeResource(context.getResources(), R.drawable.floor);
        //int bWidth = model.getWidth();
        //int bHeight = model.getHeight();
        //matrix.postScale((float)screenWidth/bWidth, (float) 10/bHeight);
        //model = Bitmap.createBitmap(model,0,0,bHeight,bWidth,matrix,false);

        this.thickness = 10;
        floorColor = Color.rgb(0,153,204);
        this.point = point;
        this.floorLine = new Rect(0, point.y,
                screenWidth, point.y + thickness);

        this.yVel = 0;
    }

    public Rect getFloorLine(){
        return this.floorLine;
    }

    public void update(){

    }

    public void draw(Canvas canvas){
        //canvas.drawColor(rectColor);
        Paint paint = new Paint();
        paint.setColor(this.floorColor);
        canvas.drawRect(this.floorLine, paint);


        /////left top
        //canvas.drawBitmap(model, point.x, point.y, paint);
    }
}

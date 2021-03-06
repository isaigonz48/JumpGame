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
import android.widget.Button;

public class PauseButton{


    protected Rect rect;
    protected Point point;
    protected int color;
    protected int halfWidth;

    private Bitmap model;


    public PauseButton(){

        color = Color.rgb(0,0,0);
        this.point = new Point(0,0);
    }

    public PauseButton(Point point){
        //super();
        this.halfWidth = 50;
        //this.rect = new Rect(100,500,200,600);
        color = Color.rgb(0,0,0);
        this.color = Color.rgb(0,0,0);
        this.point = point;
        this.rect = new Rect(point.x - halfWidth, point.y - halfWidth,
                point.x + halfWidth, point.y + halfWidth);

    }


    public PauseButton(Context context, Point point){
        this.halfWidth = 50;
        //this.rect = new Rect(100,500,200,600);
        color = Color.rgb(0,0,0);
        this.color = Color.rgb(0,0,0);
        this.point = point;
        this.rect = new Rect(point.x - halfWidth, point.y - halfWidth,
                point.x + halfWidth, point.y + halfWidth);

        Matrix matrix = new Matrix();
        model = BitmapFactory.decodeResource(context.getResources(), R.drawable.pause_button);
        int bWidth = model.getWidth();
        int bHeight = model.getHeight();
        matrix.postScale((float)75/bWidth, (float) 80/bHeight);
        model = Bitmap.createBitmap(model,0,0,bHeight,bWidth,matrix,false);
    }

    public Point getPoint(){
        return this.point;
    }


    public Rect getRect(){
        return this.rect;
    }

    public Rect getRect(int i){
        return this.rect;
    }

    public void draw(Canvas canvas){
        Paint paint = new Paint();
        paint.setColor(this.color);
        //canvas.drawRect(this.rect, paint);
        canvas.drawBitmap(model, point.x-halfWidth+12, point.y-halfWidth+10, paint);
        //this.rect.set(this.point.x-50,this.point.y+50,this.point.x+50, this.point.y-50);
        //canvas.drawRect(this.rect, paint);
    }

    public void update(){


    }
}

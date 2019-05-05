package edu.utep.cs.cs4330.jumpgame;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;

public class PauseMenu {


    protected Rect menuRect;
    protected Point point;
    protected int color;
    protected int halfWidth;
    private int halfHeight;

    private Rect exitRect;

    private Rect resumeRect;
    private Context context;



    public PauseMenu(Point screenSize, Context context){
        //super();
        this.context = context;
        this.halfWidth = screenSize.x / 3;
        this.halfHeight = screenSize.y / 5;
        //this.rect = new Rect(100,500,200,600);
        //color = Color.rgb(255,0,0);
        this.color = Color.rgb(0,0,100);
        this.point = new Point(screenSize.x/2, screenSize.y/2);
        this.menuRect = new Rect(point.x - halfWidth, point.y - halfHeight,
                point.x + halfWidth, point.y + halfHeight);

        int buttonHalfWidth = halfWidth / 3;
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
        Rect bgRect = new Rect(point.x - (halfWidth+3), point.y - (halfHeight+3),
                point.x + (halfWidth+3), point.y + (halfHeight+3));

        paint.setColor(Color.rgb(0,0,255));

        canvas.drawRect(bgRect,paint);
        paint.setColor(this.color);
        paint.setColor(Color.rgb(0,0,0));
        canvas.drawRect(this.menuRect, paint);


        //Bitmap button = BitmapFactory.decodeResource(context.getResources(), R.drawable.custom_button_bg);

        paint.setColor(Color.rgb(255,0,0));
        //paint.setColor()
        canvas.drawRect(this.exitRect,paint);
        canvas.drawRect(this.resumeRect,paint);

        //Drawable drawable = ContextCompat.getDrawable(context,R.drawable.custom_button_bg);
        //Drawable drawable = context.getResources().getDrawable(R.drawable.custom_button_bg);

        //Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_4444);
        //canvas.setBitmap(bitmap);
        //drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
        //drawable.draw(canvas);
        //canvas.drawBitmap(bitmap,this.exitRect.left, this.exitRect.top, paint);


        //canvas.drawBitmap(button, this.exitRect.left, this.exitRect.top, paint);

        paint.setColor(Color.rgb(255,255,255));
        paint.setTextSize(96);
        canvas.drawText("Paused", point.x-150,point.y - halfHeight/3, paint);
        paint.setTextSize(72);

        canvas.drawText("Main Menu", exitRect.left+15, exitRect.bottom-25,paint);
        canvas.drawText("Resume", resumeRect.left+60, resumeRect.bottom-25,paint);

        //this.rect.set(this.point.x-50,this.point.y+50,this.point.x+50, this.point.y-50);
        //canvas.drawRect(this.rect, paint);
    }
}

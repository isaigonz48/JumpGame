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
import android.support.constraint.solver.widgets.Rectangle;

import java.util.Vector;


public class Player {

    private static final String NORMAL_PLAYER = "basic_square";
    private static final String ROTATE_ANIMATION_1 = "basic_square_rotate1";
    private static final String ROTATE_ANIMATION_2 = "basic_square_rotate2";
    private static final String ROTATE_ANIMATION_3 = "basic_square_rotate3";



    private final int[] jumpAnimation = {R.drawable.basic_square, R.drawable.basic_square_rotate1, R.drawable.basic_square_rotate2, R.drawable.basic_square_rotate3};

    private Bitmap model;
    private final int PLAYER_GRAVITY = 5;
    private final int INITIAL_Y = 550;

    private Rect rect;
    private int rectColor;
    private Point point;
    private Context context;

    private boolean jumpBuffer;
    private boolean isJumping;
    private int xVel;
    private int yVel;

    //private short jumpTick;


    public Player(){
        this.rect = new Rect(100,500,200,600);
        //this.model = new Bitmap();
        rectColor = Color.rgb(255,0,0);
        this.point = new Point(0,0);
        this.jumpBuffer = false;
        this.isJumping = false;
        this.xVel = 0;
        this.yVel = 0;
    }

    public Player(Context context){
        this.rect = new Rect(100,500,200,600);
        this.model = BitmapFactory.decodeResource(context.getResources(), R.drawable.basic_square);
        rectColor = Color.rgb(255,0,0);
        this.point = new Point(0,0);
        this.jumpBuffer = false;
        this.isJumping = false;
        this.xVel = 0;
        this.yVel = 0;
        this.context = context;
    }

    public Player(Context context, Point point){
        this.rect = new Rect(100,500,200,600);
        this.model = BitmapFactory.decodeResource(context.getResources(), R.drawable.basic_square);
        rectColor = Color.rgb(255,0,0);
        this.point = point;
        this.jumpBuffer = false;
        this.isJumping = false;
        this.xVel = 0;
        this.yVel = 0;
        //jumpTick = 0;
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
        canvas.drawRect(this.rect, paint);
        /////left top
        //canvas.drawBitmap(model, point.x - 50, point.y - 50, paint);
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
        this.rect.set(this.point.x - 50,this.point.y+50,this.point.x + 50, this.point.y-50);

    }



    private void jump(){
        //if(jumpTick++ > 3)
          //  jumpTick = 0;

        this.yVel += PLAYER_GRAVITY;
        //this.model = BitmapFactory.decodeResource(this.context.getResources(), jumpAnimation[jumpTick]);
        if(this.point.y == INITIAL_Y) {
            isJumping = false;
            yVel = 0;
            //jumpTick = 0;
        }
    }
}

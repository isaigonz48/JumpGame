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



    //private final int[] jumpAnimation = {R.drawable.basic_square, R.drawable.basic_square_rotate1, R.drawable.basic_square_rotate2, R.drawable.basic_square_rotate3};

    private Bitmap model;
    private final int PLAYER_GRAVITY = 4;
    private final int INITIAL_Y = 550;
    private final int MAX_FALL_SPEED = 52;

    private int halfWidth;
    private Rect rect;
    private int rectColor;
    private Point point;
    private Context context;

    private boolean jumpBuffer;
    private boolean isJumping;
    private boolean isFalling;
    private boolean canJump;
    private int xVel;
    private int yVel;

    //private int prevYVel;
    private int prevYPos;
    //private short jumpTick;
    private int startingY;


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
        this.halfWidth = 50;
        this.model = BitmapFactory.decodeResource(context.getResources(), R.drawable.basic_square);
        rectColor = Color.rgb(0,0,255);
        this.point = point;
        this.point.y = -2050;
        this.rect = new Rect(point.x - halfWidth, point.y - halfWidth,
                point.x + halfWidth, point.y + halfWidth);

        //this.rect = new Rect(point.x - halfWidth, 0,
        //        point.x + halfWidth, 100);

        this.jumpBuffer = false;
        this.isJumping = false;
        isFalling = true;
        canJump = false;
        this.xVel = 0;
        this.yVel = 0;
        this.startingY = point.y;
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

    public Rect getRect(){
        return this.rect;
    }

    public Point getPoint(){
        return this.point;
    }

    public void setX(int x){
        this.point.x = x;
    }

    public void setY(int y){
        this.point.y = y;
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
        //if(!isJumping && !isFalling) {
        if(canJump){
            if(this.jumpBuffer) {
                yVel = -48;
                isJumping = true;
                canJump = false;
            }
        }else if(isJumping){
            jump();
        }

        if(isFalling && this.yVel < MAX_FALL_SPEED){
            this.yVel += PLAYER_GRAVITY;
        }
        prevYPos = this.point.y;

        jumpBuffer = false;
        this.point.x += xVel;
        this.point.y += yVel;
        //this.rect.set(this.point.x-halfWidth,this.point.y+halfWidth,
          //      this.point.x+halfWidth, this.point.y-halfWidth);

        this.rect.left = point.x-halfWidth;
        this.rect.top = point.y-halfWidth;
        this.rect.right = point.x+halfWidth;
        this.rect.bottom = point.y+halfWidth;

    }



    private void jump(){
        //if(jumpTick++ > 3)
          //  jumpTick = 0;
        //canJump = false;
        this.yVel += PLAYER_GRAVITY;
        //this.model = BitmapFactory.decodeResource(this.context.getResources(), jumpAnimation[jumpTick]);
        if(this.yVel <= 0){//this.point.y == INITIAL_Y) {
            isJumping = false;
            isFalling = true;
            //yVel = 0;
            //jumpTick = 0;
        }
    }

    public void collidedWithFloor(){
        point.y = startingY;//prevYPos;
        yVel = 0;


        //this.rect.left = point.x-halfWidth;
        this.rect.top = point.y-halfWidth;
        //this.rect.right = point.x+halfWidth;
        this.rect.bottom = point.y+halfWidth;
        isFalling = false;
        canJump = true;
    }

    public void collidedWithPlatform(Obstacle o){
        if(prevYPos <= o.getPoint().y - 50)
            point.y = (o.getRect().top-1)-halfWidth;
        yVel = 0;

        //this.rect.left = point.x-halfWidth;
        this.rect.top = point.y-halfWidth;
        //this.rect.right = point.x+halfWidth;
        this.rect.bottom = point.y+halfWidth;
        //isFalling = false;
        canJump = true;


    }
}

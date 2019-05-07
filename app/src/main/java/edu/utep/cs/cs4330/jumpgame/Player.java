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

import android.util.Log;

import static java.lang.Math.abs;

public class Player {

    private static final String TAG = "PLAYERLOG";
    ///// Not used yet

    private static final String NORMAL_PLAYER = "basic_square";
    private static final String ROTATE_ANIMATION_1 = "basic_square_rotate1";
    private static final String ROTATE_ANIMATION_2 = "basic_square_rotate2";
    private static final String ROTATE_ANIMATION_3 = "basic_square_rotate3";

    //private final int[] jumpAnimation = {R.drawable.basic_square, R.drawable.basic_square_rotate1, R.drawable.basic_square_rotate2, R.drawable.basic_square_rotate3};

    private Bitmap model;
    private Bitmap models[];
    private final int PLAYER_GRAVITY = 4;
    private final int INITIAL_Y = 550;
    private final int MAX_FALL_SPEED = 52;
    private final int JUMP_POWER = 52;

    private int halfWidth;
    private Rect rect;
    private int rectColor;
    private Point point;
    private Context context;

    private int playerGravity;
    private boolean jumpBuffer;
    private boolean gravityBuffer;
    private boolean isJumping;
    private boolean isFalling;
    private boolean canJump;
    private int xVel;
    private int yVel;

    //private int prevYVel;
    private int prevYPos;
    //private short jumpTick;
    private int jumpTick;
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

        //models = new Bitmap[4];
        models = createJumpAnimation(context);
        //this.model = BitmapFactory.decodeResource()
        this.model = models[0];

        //models[0] = models[3];

                //Bitmap.createS
        //this.model.reconfigure(100,100, 0);
        rectColor = Color.rgb(0,0,255);
        this.point = point;
        //this.point.y = -2050;
        this.rect = new Rect(point.x - halfWidth, point.y - halfWidth,
                point.x + halfWidth, point.y + halfWidth);

        //this.rect = new Rect(point.x - halfWidth, 0,
        //        point.x + halfWidth, 100);

        this.jumpBuffer = false;
        this.gravityBuffer = false;
        this.isJumping = false;
        //isFalling = true;//false;
        canJump = false;//true;
        this.xVel = 0;
        this.yVel = 0;
        this.startingY = point.y;
        playerGravity = 3;
        jumpTick = 0;
    }

    private Bitmap[] createJumpAnimation(Context context){

        models = new Bitmap[12];
        models[0] = BitmapFactory.decodeResource(context.getResources(), R.drawable.basic_square);

        int bWidth = models[0].getWidth();
        int bHeight = models[0].getHeight();

        Matrix matrix = new Matrix();
        matrix.postScale((float)100/bWidth, (float) 100/bHeight);
        models[0] = Bitmap.createBitmap(models[0],0,0,bHeight,bWidth,matrix,false);


        matrix = new Matrix();
        this.models[1] = BitmapFactory.decodeResource(context.getResources(), R.drawable.basic_square_rotate1);
        bWidth = models[1].getWidth();
        bHeight = models[1].getHeight();
        matrix.postScale((float)116/bWidth, (float) 116/bHeight);
        models[1] = Bitmap.createBitmap(models[1],0,0,bHeight,bWidth,matrix,false);

        matrix = new Matrix();
        this.models[2] = BitmapFactory.decodeResource(context.getResources(), R.drawable.basic_square_rotate2);
        bWidth = models[2].getWidth();
        bHeight = models[2].getHeight();
        matrix.postScale((float)124/bWidth, (float) 124/bHeight);
        models[2] = Bitmap.createBitmap(models[2],0,0,bHeight,bWidth,matrix,false);

        matrix = new Matrix();
        this.models[3] = BitmapFactory.decodeResource(context.getResources(), R.drawable.basic_square_rotate3);
        bWidth = models[3].getWidth();
        bHeight = models[3].getHeight();
        matrix.postScale((float)132/bWidth, (float) 132/bHeight);
        models[3] = Bitmap.createBitmap(models[3],0,0,bHeight,bWidth,matrix,false);

        matrix = new Matrix();
        this.models[4] = BitmapFactory.decodeResource(context.getResources(), R.drawable.basic_square_rotate4);
        bWidth = models[4].getWidth();
        bHeight = models[4].getHeight();
        matrix.postScale((float)140/bWidth, (float) 140/bHeight);
        models[4] = Bitmap.createBitmap(models[4],0,0,bHeight,bWidth,matrix,false);

        matrix = new Matrix();
        this.models[5] = BitmapFactory.decodeResource(context.getResources(), R.drawable.basic_square_rotate5);
        bWidth = models[5].getWidth();
        bHeight = models[5].getHeight();
        matrix.postScale((float)144/bWidth, (float) 144/bHeight);
        models[5] = Bitmap.createBitmap(models[5],0,0,bHeight,bWidth,matrix,false);

        matrix = new Matrix();
        this.models[6] = BitmapFactory.decodeResource(context.getResources(), R.drawable.basic_square_rotate6);
        bWidth = models[6].getWidth();
        bHeight = models[6].getHeight();
        matrix.postScale((float)144/bWidth, (float) 144/bHeight);
        models[6] = Bitmap.createBitmap(models[6],0,0,bHeight,bWidth,matrix,false);

        matrix = new Matrix();
        this.models[7] = BitmapFactory.decodeResource(context.getResources(), R.drawable.basic_square_rotate7);
        bWidth = models[7].getWidth();
        bHeight = models[7].getHeight();
        matrix.postScale((float)144/bWidth, (float) 144/bHeight);
        models[7] = Bitmap.createBitmap(models[7],0,0,bHeight,bWidth,matrix,false);

        matrix = new Matrix();
        this.models[8] = BitmapFactory.decodeResource(context.getResources(), R.drawable.basic_square_rotate8);
        bWidth = models[8].getWidth();
        bHeight = models[8].getHeight();
        matrix.postScale((float)140/bWidth, (float) 140/bHeight);
        models[8] = Bitmap.createBitmap(models[8],0,0,bHeight,bWidth,matrix,false);

        matrix = new Matrix();
        this.models[9] = BitmapFactory.decodeResource(context.getResources(), R.drawable.basic_square_rotate9);
        bWidth = models[9].getWidth();
        bHeight = models[9].getHeight();
        matrix.postScale((float)132/bWidth, (float) 132/bHeight);
        models[9] = Bitmap.createBitmap(models[9],0,0,bHeight,bWidth,matrix,false);

        matrix = new Matrix();
        this.models[10] = BitmapFactory.decodeResource(context.getResources(), R.drawable.basic_square_rotate10);
        bWidth = models[10].getWidth();
        bHeight = models[10].getHeight();
        matrix.postScale((float)124/bWidth, (float) 124/bHeight);
        models[10] = Bitmap.createBitmap(models[10],0,0,bHeight,bWidth,matrix,false);

        matrix = new Matrix();
        this.models[11] = BitmapFactory.decodeResource(context.getResources(), R.drawable.basic_square_rotate11);
        bWidth = models[11].getWidth();
        bHeight = models[11].getHeight();
        matrix.postScale((float)116/bWidth, (float) 116/bHeight);
        models[11] = Bitmap.createBitmap(models[11],0,0,bHeight,bWidth,matrix,false);

        return models;
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

    public void bufferGravity(){
        this.gravityBuffer = true;
    }

    public void draw(Canvas canvas){
        //canvas.drawColor(rectColor);
        Paint paint = new Paint();

        paint.setColor(this.rectColor);
        //canvas.drawRect(this.rect, paint);
        /////left top
        //model.setHeight(100);
        //model.setWidth(100);

        //canvas.drawBitmap(model, paint);

        canvas.drawBitmap(model, point.x - halfWidth, point.y - halfWidth, paint);
    }




    public void update(){
        //if(!isJumping && !isFalling) {
        if(jumpBuffer)
        Log.d(TAG, ("buffering"));
        if(canJump){
            model = models[0];
            if(this.jumpBuffer) {
                Log.d(TAG, "Jump!");
                //playerGravity = -playerGravity;
                //Log.d(TAG, Integer.toString(playerGravity));
                if(playerGravity > 0)
                    yVel = -36;
                else
                    yVel = 36;
                //isJumping = true;
                //isFalling = true;
                canJump = false;
                jumpBuffer = false;
            }

        }
        if(this.gravityBuffer){
            playerGravity = -playerGravity;
            canJump = false;
            gravityBuffer = false;
        }

        if(++jumpTick > 11)
            jumpTick = 0;
        model = models[jumpTick];

        if(abs(this.yVel) < MAX_FALL_SPEED){
            //Log.d(TAG, "Adding vel");
            //this.yVel += PLAYER_GRAVITY;
            this.yVel += playerGravity;
            //Log.d(TAG, Integer.toString(yVel));
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

    public void collidedWithFloor(Floor f){
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
            point.y = (o.getRect().top)-halfWidth;
        else if(prevYPos >= o.getPoint().y + 50)
            point.y = (o.getRect().bottom+1)+halfWidth;
        yVel = 0;

        //this.rect.left = point.x-halfWidth;
        this.rect.top = point.y-halfWidth;
        //this.rect.right = point.x+halfWidth;
        this.rect.bottom = point.y+halfWidth;
        //isFalling = false;
        canJump = true;


    }
    public void collidedWithPlatform(Rect r){
        if(prevYPos <= r.centerY() - 50)
            point.y = (r.top)-halfWidth;
        else if(prevYPos >= r.centerY() + 50)
            point.y = (r.bottom+1)+halfWidth;
        yVel = 0;

        //this.rect.left = point.x-halfWidth;
        this.rect.top = point.y-halfWidth;
        //this.rect.right = point.x+halfWidth;
        this.rect.bottom = point.y+halfWidth;
        //isFalling = false;
        canJump = true;

        model = models[0];
        jumpTick = 0;


    }

    public void reset(){
        this.prevYPos = startingY;
        this.point.y = startingY;
        this.playerGravity = 3;

        this.rect.left = point.x-halfWidth;
        this.rect.top = point.y-halfWidth;
        this.rect.right = point.x+halfWidth;
        this.rect.bottom = point.y+halfWidth;

        this.jumpBuffer = false;
        this.gravityBuffer = false;
        this.isJumping = false;
    }
}

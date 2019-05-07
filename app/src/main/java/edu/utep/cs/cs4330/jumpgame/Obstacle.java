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

public class Obstacle {


    protected Bitmap model;
    protected Rect rect;
    protected Point point;
    protected int color;
    protected int halfWidth;


    protected int xVel;
    protected int yVel;

    protected ObstacleType type;
    protected int numRects;


    public Obstacle(){

        color = Color.rgb(0,0,0);
        this.point = new Point(0,0);
        this.xVel = -20;
        this.yVel = 0;
        type = ObstacleType.OBSTACLE;
        numRects = 1;
    }

    public Obstacle(Point point){
        //this.rect = new Rect(100,500,200,600);
        color = Color.rgb(0,0,0);
        this.color = Color.rgb(0,0,0);
        this.point = point;
        this.xVel = -22;
        //this.xVel = -15;

        type = ObstacleType.OBSTACLE;
        numRects = 1;
    }

    public Obstacle(Context context, Point point){
        //this.rect = new Rect(100,500,200,600);
        Matrix matrix = new Matrix();
        this.model = BitmapFactory.decodeResource(context.getResources(), R.drawable.basic_platform);
        int bWidth = model.getWidth();
        int bHeight = model.getHeight();
        matrix.postScale((float)105/bWidth, (float) 105/bHeight);
        model = Bitmap.createBitmap(model,0,0,bHeight,bWidth,matrix,false);


        color = Color.rgb(0,0,0);
        this.color = Color.rgb(0,0,0);
        this.point = point;
        this.xVel = -22;
        //this.xVel = -15;

        type = ObstacleType.OBSTACLE;
        numRects = 1;


    }

    public Obstacle(int color, Point point){
        //this.rect = rectangle;
        this.color = color;
        this.point = point;
        type = ObstacleType.OBSTACLE;
    }

    public Point getPoint(){
        return this.point;
    }

    public int getYVel(){
        return this.yVel;
    }

    public int getXVel(){
        return this.xVel;
    }

    public Rect getRect(){
        return this.rect;
    }

    public Rect getRect(int i){
        return this.rect;
    }

    public int getNumRects(){
        return this.numRects;
    }

    public ObstacleType getType() {
        return this.type;
    }

    public void draw(Canvas canvas){
        Paint paint = new Paint();
        paint.setColor(this.color);
        canvas.drawBitmap(model, point.x - halfWidth, point.y - halfWidth, paint);
        //canvas.drawRect(this.rect, paint);
        //this.rect.set(this.point.x-50,this.point.y+50,this.point.x+50, this.point.y-50);
        //canvas.drawRect(this.rect, paint);
    }

    public void update(){
        this.point.x += this.xVel;
        this.point.y += this.yVel;

        this.rect.left = point.x-halfWidth;
        this.rect.top = point.y-halfWidth;
        this.rect.right = point.x+halfWidth;
        this.rect.bottom = point.y+halfWidth;

    }
}

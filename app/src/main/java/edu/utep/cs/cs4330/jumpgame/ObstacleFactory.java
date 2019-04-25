package edu.utep.cs.cs4330.jumpgame;

import android.graphics.Point;
import android.util.Log;

public class ObstacleFactory {
    private static String TAG = "OBSFACTORYTAG";

    private int screenWidth;
    private int screenHeight;
    private static int currentHeight;
    private static int oppositeHeight;

    enum ObstacleType{
        PLATFORM_SAME_HEIGHT,
        PLATFORM_ONE_UP,
        PLATFORM_ONE_DOWN
    }
    public ObstacleFactory(int screenWidth, int screenHeight){

        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
        currentHeight = (screenHeight - screenHeight/10) -51;
        oppositeHeight = screenHeight - currentHeight;

        Log.d(TAG, Integer.toString(screenHeight));
        Log.d(TAG, Integer.toString(currentHeight));
        Log.d(TAG, Integer.toString(oppositeHeight));
        //currentHeight = 650;
    }

    public Obstacle createObstacle(int selection){
        Obstacle obs;
        switch(selection){
            ///// Platform on same height
            case 1:
                obs = new ObstaclePlatform(new Point(screenWidth + 50,currentHeight));
                return obs;
            ///// Platform one square up
            case 2:
                currentHeight -= 100;
                oppositeHeight += 100;
                obs = new ObstaclePlatform(new Point(screenWidth + 50,currentHeight));
                return obs;
            ///// Platform one square down
            case 3:
                currentHeight += 100;
                oppositeHeight -=100;
                obs = new ObstaclePlatform(new Point(screenWidth + 50,currentHeight));
                return obs;
            ///// Not platform same height
            case 4:
                obs = new ObstacleSimpleSquare(new Point(screenWidth + 50,currentHeight));
                return obs;

            case 5:
                obs = new ObstacleSimpleSquare(new Point(screenWidth + 50,currentHeight-100));
                return obs;

            case 6:
                obs = new ObstacleDoublePlatform(new Point(screenWidth + 50, currentHeight),
                        new Point(screenWidth + 50, oppositeHeight));
                Log.d(TAG, "No crash here");
            default:
                return new Obstacle();


        }
    }
}

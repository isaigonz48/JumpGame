package edu.utep.cs.cs4330.jumpgame;

import android.graphics.Point;

public class ObstacleFactory {
    private int screenWidth;
    private static int currentHeight;

    enum ObstacleType{
        PLATFORM_SAME_HEIGHT,
        PLATFORM_ONE_UP,
        PLATFORM_ONE_DOWN
    }
    public ObstacleFactory(int screenWidth){
        this.screenWidth = screenWidth;
        currentHeight = 650;
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
                obs = new ObstaclePlatform(new Point(screenWidth + 50,currentHeight));
                return obs;
            ///// Platform one square down
            case 3:
                currentHeight += 100;
                obs = new ObstaclePlatform(new Point(screenWidth + 50,currentHeight));
                return obs;
            ///// Not platform same height
            case 4:
                obs = new ObstacleSimpleSquare(new Point(screenWidth + 50,currentHeight));
                return obs;

            case 5:
                obs = new ObstacleSimpleSquare(new Point(screenWidth + 50,currentHeight-100));
                return obs;

            default:
                return new Obstacle();


        }
    }
}

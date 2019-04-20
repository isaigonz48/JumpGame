package edu.utep.cs.cs4330.jumpgame;

import android.graphics.Point;

public class ObstacleFactory {
    private int screenWidth;

    enum ObstacleType{
        PLATFORM_SAME_HEIGHT,
        PLATFORM_ONE_UP,
        PLATFORM_ONE_DOWN
    }
    public ObstacleFactory(int screenWidth){
        this.screenWidth = screenWidth;
    }

    public Obstacle createObstacle(int selection){
        Obstacle obs;
        switch(selection){
            ///// Platform on same level
            case 1:
                obs = new ObstaclePlatform(new Point(screenWidth + 50,550));
                return obs;
            ///// Platform one square up
            case 2:
                obs = new ObstaclePlatform(new Point(screenWidth + 50,550));
                return obs;
            ///// Platform one square down
            case 3:
                obs = new ObstaclePlatform(new Point(screenWidth + 50,550));
                return obs;

            default:
                return new Obstacle();


        }
    }
}

package edu.utep.cs.cs4330.jumpgame;

import android.content.Context;
import android.graphics.Point;
import android.util.Log;

public class ObstacleFactory {
    private static String TAG = "OBSFACTORYTAG";

    private int screenWidth;
    private int screenHeight;
    private static int currentHeight;
    private static int oppositeHeight;
    private Context context;

    public ObstacleFactory(int screenWidth, int screenHeight, Context context){

        this.context = context;
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
                obs = new ObstaclePlatform(context,new Point(screenWidth + 50,currentHeight));
                return obs;
            ///// Platform one square up
            case 2:
                currentHeight -= 100;
                oppositeHeight += 100;
                obs = new ObstaclePlatform(context,new Point(screenWidth + 50,currentHeight));
                return obs;
            ///// Platform one square down
            case 3:
                currentHeight += 100;
                oppositeHeight -=100;
                obs = new ObstaclePlatform(context,new Point(screenWidth + 50,currentHeight));
                return obs;
            ///// Not platform same height
            case 4:
                obs = new ObstacleSimpleSquare(context,new Point(screenWidth + 50,currentHeight));
                return obs;

            case 5:
                obs = new ObstacleSimpleSquare(context,new Point(screenWidth + 50,currentHeight-100));
                return obs;

            case 6:
                obs = new ObstacleDoublePlatform(new Point(screenWidth + 50, currentHeight),
                        new Point(screenWidth + 50, oppositeHeight));
                Log.d(TAG, "No crash here");
                return obs;

            case 7:
                currentHeight -= 100;
                oppositeHeight += 100;
                if(currentHeight < screenHeight/2){
                    //currentHeight += 200;
                    //oppositeHeight
                }

                obs = new ObstacleDoublePlatform(context,new Point(screenWidth + 50, currentHeight),
                        new Point(screenWidth + 50, oppositeHeight));
                return obs;

            case 8:
                currentHeight += 100;
                oppositeHeight -= 100;
                if(currentHeight < screenHeight/2){
                    //currentHeight += 200;
                    //oppositeHeight
                }

                obs = new ObstacleDoublePlatform(context,new Point(screenWidth + 50, currentHeight),
                        new Point(screenWidth + 50, oppositeHeight));
                return obs;

            case 9:
                ///// To do: grav
                obs = new ObstacleGravityChanger(context,new Point(screenWidth + 50, currentHeight-100));
                return obs;

            case 10:
                ///// opp platform
                obs = new ObstaclePlatform(context,new Point(screenWidth + 50,oppositeHeight));
                return obs;

            case 11:
                ///// opp plat one down
                currentHeight -= 100;
                oppositeHeight += 100;
                obs = new ObstaclePlatform(context,new Point(screenWidth + 50,oppositeHeight));
                return obs;

            case 12:
                ///// opp plat one up
                currentHeight += 100;
                oppositeHeight -=100;
                obs = new ObstaclePlatform(context,new Point(screenWidth + 50,oppositeHeight));
                return obs;

            case 13:
                ///// like 5 but opp
                obs = new ObstacleSimpleSquare(context,new Point(screenWidth + 50,oppositeHeight+100));
                return obs;

            case 14:
                obs = new ObstacleGravityChanger(context,new Point(screenWidth + 50, oppositeHeight+100));
                return obs;

            default:
                return new Obstacle();


        }
    }

    public void reset(){
        currentHeight = (screenHeight - screenHeight/10) -51;
        oppositeHeight = screenHeight - currentHeight;
    }
}

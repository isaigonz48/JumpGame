package edu.utep.cs.cs4330.jumpgame;

import android.graphics.Color;
import android.graphics.Point;
import android.graphics.Rect;

public class ObstacleGravityChanger extends Obstacle {

    public ObstacleGravityChanger(Point point) {
        super(point);
        this.color = Color.rgb(0,255,0);
        this.halfWidth = 50;
        this.rect = new Rect(point.x - halfWidth, point.y - halfWidth,
                point.x + halfWidth, point.y + halfWidth);

        //this.rect = new Rect(point.x + 50, point.y - 50, point.x - 50, point.y + 50);
        //this.xVel = -15;
        type = ObstacleType.GRAVITY_CHANGER;

    }

}

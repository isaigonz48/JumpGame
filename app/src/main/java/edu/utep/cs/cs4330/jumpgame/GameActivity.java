package edu.utep.cs.cs4330.jumpgame;

import android.graphics.Canvas;
import android.graphics.Point;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.SurfaceView;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

public class GameActivity extends AppCompatActivity {

    private static final String TAG = "GAMEACTIVITYLOG";
    //private ImageView player;
    //private FrameLayout frame;

    private GameView game;
    private Player player;
    private int x;
    private int y;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_game);
        Display display = getWindowManager().getDefaultDisplay();
        Point screenSize = new Point();
        display.getSize(screenSize);

        game = new GameView(this, screenSize);

        setContentView(game);

        if(game.getLost()){
            //Log.d(TAG, "Thinking");
            //game = new GameView(this, screenSize);
            finish();
        }

        //game = new GameView(this, screenSize);

        //game.run();

        //player = findViewById(R.id.playerView);
        //frame = findViewById(R.id.frameLayout);




    }

    @Override
    protected void onPause(){
        super.onPause();
        game.pause();
    }

    @Override
    protected void onResume(){
        super.onResume();
        game.resume();
    }
}

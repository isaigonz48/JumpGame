package edu.utep.cs.cs4330.jumpgame;

import android.graphics.Canvas;
import android.graphics.Point;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.SurfaceView;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

public class GameActivity extends AppCompatActivity {

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

        game = new GameView(this);

        setContentView(game);

        if(game.getLost()){
            finish();
        }

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

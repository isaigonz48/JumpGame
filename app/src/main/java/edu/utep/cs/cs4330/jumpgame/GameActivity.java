package edu.utep.cs.cs4330.jumpgame;

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

    private SurfaceView surface;
    private Player player;
    private int x;
    private int y;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        surface = findViewById(R.id.surfaceView);
        player = new Player();

        //player = findViewById(R.id.playerView);
        //frame = findViewById(R.id.frameLayout);




    }
}

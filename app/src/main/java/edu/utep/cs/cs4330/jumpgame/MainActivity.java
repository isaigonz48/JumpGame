package edu.utep.cs.cs4330.jumpgame;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {


    private Button startButton;
    private Button statsButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startButton = findViewById(R.id.startButton);
        statsButton = findViewById(R.id.mainStatsButton);

        startButton.setOnClickListener(view ->{
            Intent i = new Intent(this, GameActivity.class);
            startActivity(i);
        });

        statsButton.setOnClickListener(view ->{
            startActivity(new Intent(this, StatsActivity.class));
        });

    }
}

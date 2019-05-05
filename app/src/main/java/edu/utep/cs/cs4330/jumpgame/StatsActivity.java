package edu.utep.cs.cs4330.jumpgame;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class StatsActivity extends AppCompatActivity {

    private TextView level1Text;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);

        level1Text = findViewById(R.id.level1Text);

        SharedPreferences sharedPreferences = getSharedPreferences("userStats", Context.MODE_PRIVATE);

        int level1Attempts = sharedPreferences.getInt("level1", -1);

        if(level1Attempts <= 0 ){
            level1Text.setText("Level 1: Not passed");
        }else{
            level1Text.setText(("Level 1: " + level1Attempts + " attempts"));
        }
    }
}

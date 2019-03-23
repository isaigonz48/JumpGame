package edu.utep.cs.cs4330.jumpgame;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {


    private Button startButton;
    private Button settingsButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startButton = findViewById(R.id.startButton);
        settingsButton = findViewById(R.id.mainSettingsButton);

        startButton.setOnClickListener(view ->{
            Intent i = new Intent(this, GameActivity.class);
            startActivity(i);
        });
    }
}

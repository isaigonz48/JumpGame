package edu.utep.cs.cs4330.jumpgame;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;

public class LevelSelectActivity extends AppCompatActivity {

    private Button backButton;
    private Button level1Button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level_select);

        level1Button = findViewById(R.id.level1Button);

        level1Button.setOnClickListener(view->{
            Intent i = new Intent(this, GameActivity.class);
            i.putExtra("level", 1);
            startActivity(i);
        });

        backButton = findViewById(R.id.levelBackButton);

        backButton.setOnClickListener(view->{
            finish();
        });
    }
}

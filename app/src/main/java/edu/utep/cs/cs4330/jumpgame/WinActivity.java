package edu.utep.cs.cs4330.jumpgame;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

public class WinActivity extends AppCompatActivity {

    private TextView congratulationsText;
    private TextView attemptsText;
    private Button continueButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_win);

        Intent i = getIntent();
        int level = i.getIntExtra("level", 1);
        int attemptCount = i.getIntExtra("attemptCount", 1);

        SharedPreferences sharedPreferences = getSharedPreferences("userStats", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        if(level == 1) {
            if (sharedPreferences.getInt("level1", attemptCount + 1) > attemptCount) {
                editor.putInt("level1", attemptCount);
                editor.apply();
            }
        }

        congratulationsText = findViewById(R.id.congratulationsText);
        attemptsText = findViewById(R.id.attemptsText);
        continueButton = findViewById(R.id.continueButton);

        attemptsText.setText(("It only took you " + attemptCount + " attempts!"));

        continueButton.setOnClickListener(view ->{
            Intent newI = new Intent(this, MainActivity.class);
            newI.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(newI);
        });
    }
}

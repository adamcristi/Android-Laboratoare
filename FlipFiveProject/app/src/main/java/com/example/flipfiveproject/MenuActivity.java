package com.example.flipfiveproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        Button buttonStartGame = (Button) findViewById(R.id.button_start_game);
        Button buttonBestScore = (Button) findViewById(R.id.button_see_best_score);

        buttonStartGame.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent gameIntent = new Intent(MenuActivity.this, LoadingActivity.class);
                startActivity(gameIntent);
            }
        });

        buttonBestScore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(MenuActivity.this);
                int bestScore = sharedPref.getInt("best_score", 0);
                if (bestScore == 0) {
                    Toast toast = Toast.makeText(MenuActivity.this, "No best score set yet!", Toast.LENGTH_SHORT);
                    toast.show();
                } else if (bestScore > 0) {
                    Toast toast = Toast.makeText(MenuActivity.this, String.format("Best score obtained: %d", bestScore), Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
        });

    }
}

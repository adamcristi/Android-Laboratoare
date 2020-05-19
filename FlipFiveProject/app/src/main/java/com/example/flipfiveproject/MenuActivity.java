package com.example.flipfiveproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        Button buttonStartGame = (Button) findViewById(R.id.button_start_game);
        buttonStartGame.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                Intent gameIntent = new Intent(MenuActivity.this, GameActivity.class);
                startActivity(gameIntent);
            }
        });

    }
}

package com.example.lab7;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class AnotherActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_another);

        //Bundle extra = getIntent().getExtras();
        //String savedText = extra.getString("message");
        String savedText = getIntent().getStringExtra("message");
        TextView textView = (TextView) findViewById(R.id.text);
        textView.setText("Message sent from main activity: " + savedText);


    }

}

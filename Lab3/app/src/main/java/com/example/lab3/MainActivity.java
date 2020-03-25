package com.example.lab3;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ListView lista;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView textView = (TextView)findViewById(R.id.text);
        textView.setText("Some text");

        lista = (ListView) findViewById(R.id.lista1);
        final ArrayList<String> arrayList = new ArrayList<>();
        for (int index = 0; index < 15; index++) {
            arrayList.add("Item " + (index + 1));
        }

        ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, arrayList);

        lista.setAdapter(arrayAdapter);

        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast toast = Toast.makeText(MainActivity.this, "Description: " + arrayList.get(position).toString(), Toast.LENGTH_SHORT);
                toast.show();
            }
        });

        Log.d("TAG", "onCreate called");
    }

    protected  void onStart() {
        super.onStart();
        Log.d("TAG", "onStart called");
    }

    protected  void onResume() {
        super.onResume();
        Log.d("TAG", "onResume called");
    }

    protected  void onPause() {
        super.onPause();
        Log.d("TAG", "onPause called");
    }

    protected  void onStop() {
        super.onStop();
        Log.d("TAG", "onStop called");
    }

    protected  void onRestart() {
        super.onRestart();
        Log.d("TAG", "onRestart called");
    }

    protected  void onDestroy() {
        super.onDestroy();
        Log.d("TAG", "onDestroy called");

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.activity_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.option1:
                return true;
            case R.id.option2:
                return true;
            case R.id.option3:
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle instanceState) {
        TextView textView = (TextView)findViewById(R.id.text);
        instanceState.putString("TEXT", textView.getText().toString());
        super.onSaveInstanceState(instanceState);
    }

    @Override
    public void onRestoreInstanceState(Bundle instanceState) {
        String savedText = instanceState.getString("TEXT");
        TextView textView = (TextView)findViewById(R.id.text);
        textView.setText(savedText + " Restored");
    }
}

package com.example.lab6;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.preference.PreferenceManager;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.icu.text.UnicodeSetSpanner;
import android.os.Bundle;
import android.os.Environment;
import android.text.InputType;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ListView lista;
    public static final String FILE_NAME = "products_list.txt";

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

        Log.d("Status", "onCreate called");

        PreferenceManager.setDefaultValues(this, R.xml.preferences, false);

    }

    protected  void onStart() {
        super.onStart();
        Log.d("Status", "onStart called");

        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        Boolean backgroundPref = sharedPref.getBoolean(SettingsFragment.KEY_BACKGROUND, false);
        RelativeLayout layout = (RelativeLayout) findViewById(R.id.main);
        if(layout != null) {
            if (backgroundPref == true) {
                layout.setBackgroundColor(Color.CYAN);
            } else {
                layout.setBackgroundColor(Color.WHITE);
            }
        }
    }

    protected  void onResume() {
        super.onResume();
        Log.d("Status", "onResume called");
    }

    protected  void onPause() {
        super.onPause();
        Log.d("Status", "onPause called");
    }

    protected  void onStop() {
        super.onStop();
        Log.d("Status", "onStop called");
    }

    protected  void onRestart() {
        super.onRestart();
        Log.d("Status", "onRestart called");
    }

    protected  void onDestroy() {
        super.onDestroy();
        Log.d("Status", "onDestroy called");

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.activity_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.sensors:
                Intent sensorsIntent = new Intent(MainActivity.this, SensorsActivity.class);
                startActivity(sensorsIntent);
                return true;
            case R.id.coordinates:
                Intent coordinatesIntent = new Intent(MainActivity.this, CoordinatesActivity.class);
                startActivity(coordinatesIntent);
                return true;
            case R.id.settings:
                Intent settingsIntent = new Intent(MainActivity.this, SettingsActivity.class);
                startActivity(settingsIntent);
                return true;
            case  R.id.save_data:
                if(isExternalStorageWritable()) {
                    File file = new File(getExternalFilesDir(null).getAbsolutePath(), FILE_NAME);
                    try {
                        FileOutputStream fileOutputStream = new FileOutputStream(file);
                        fileOutputStream.write(getProductsList().getBytes());
                        fileOutputStream.close();

                        Toast.makeText(MainActivity.this, "Data saved", Toast.LENGTH_SHORT).show();
                        Log.d("Status", "Saved to " + getExternalFilesDir(null).getAbsolutePath() + "/" + FILE_NAME);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    Toast.makeText(MainActivity.this, "Cannot save data", Toast.LENGTH_SHORT).show();
                }
                return true;
            case R.id.option1:
                AlertDialog.Builder alert_option1 = new AlertDialog.Builder(this);
                alert_option1.setTitle("Approve option");
                alert_option1.setMessage("Are you sure you want to continue?");
                alert_option1.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(MainActivity.this, AnotherActivity.class);
                        intent.putExtra("message","ceva");
                        startActivity(intent);
                    }
                });
                alert_option1.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast toast = Toast.makeText(MainActivity.this, "Quit option!", Toast.LENGTH_SHORT);
                        toast.show();
                    }
                });
                alert_option1.create().show();
                return true;
            case R.id.option2:
                AlertDialog.Builder alert_option2 = new AlertDialog.Builder(this);
                alert_option2.setTitle("Dialog");
                alert_option2.setMessage("Username");
                final EditText input = new EditText(this);
                input.setInputType(InputType.TYPE_CLASS_TEXT);
                alert_option2.setView(input);
                alert_option2.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(MainActivity.this, AnotherActivity.class);
                        intent.putExtra("message", input.getText().toString());
                        startActivity(intent);
                    }
                });
                alert_option2.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                alert_option2.create().show();
                return true;
            case R.id.option3:
                AlertDialog.Builder alert_option3 = new AlertDialog.Builder(this);
                View view = getLayoutInflater().inflate(R.layout.dialog, null);
                alert_option3.setView(view);
                alert_option3.create().show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private String getProductsList() {
        String data = "";
        lista = (ListView) findViewById(R.id.lista1);
        int numberItems = lista.getAdapter().getCount();
        for(int pos=0; pos<numberItems; ++pos) {
            data += "Id item: " +  Long.toString(lista.getAdapter().getItemId(pos)) + " Name item: " + lista.getAdapter().getItem(pos).toString() + "\n";
        }
        return data;
    }

    private Boolean isExternalStorageWritable() {
        if(Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
            return true;
        } else {
            return false;
        }
    }

}

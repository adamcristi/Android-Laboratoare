package com.example.lab6;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.util.List;

/*public class SensorsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensors);
    }
}*/

public class SensorsActivity extends Activity implements SensorEventListener {
    private SensorManager mSensorManager;
    private Sensor mAccelerometer;
    private boolean accelerometerInitialized;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensors);

        mSensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);
        mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        accelerometerInitialized = false;

        List sensors = mSensorManager.getSensorList(Sensor.TYPE_ALL);
        for(int i=0; i<sensors.size(); ++i) {
            Log.d("Status", sensors.get(i).toString());
        }
    }

    /*public SensorsActivity() {
        mSensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);
        mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
    }*/

    protected void onResume() {
        super.onResume();
        mSensorManager.registerListener(this, mAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);
        Log.d("Status", this.mSensorManager.toString());
        Log.d("Status", this.mAccelerometer.toString());
    }

    protected void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(this);
    }

    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }

    public void onSensorChanged(SensorEvent event) {
        if(!accelerometerInitialized) {
            accelerometerInitialized = true;

            TextView textViewX = (TextView) findViewById(R.id.x_axis);
            TextView textViewY = (TextView) findViewById(R.id.y_axis);
            TextView textViewZ = (TextView) findViewById(R.id.z_axis);

            textViewX.setText("X value = " + Float.toString(event.values[0]));
            textViewY.setText("Y value = " + Float.toString(event.values[1]));
            textViewZ.setText("Z value = " + Float.toString(event.values[2]));

            Log.d("Status", Float.toString(event.values[0]));
            Log.d("Status", Float.toString(event.values[1]));
            Log.d("Status", Float.toString(event.values[2]));

        }
    }
}

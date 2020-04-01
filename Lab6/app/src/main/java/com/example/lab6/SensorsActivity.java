package com.example.lab6;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SensorsActivity extends AppCompatActivity implements SensorEventListener {
    private SensorManager sensorManager;
    private List<Sensor> sensors;
    private List<HashMap<String, String>> listSensors;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensors);

        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        ListView listView = findViewById(R.id.list_sensors);

        listSensors = new ArrayList<>();
        sensors = sensorManager.getSensorList(Sensor.TYPE_ALL);
        for (int index = 0; index < sensors.size(); ++index) {
            HashMap<String, String> sensor = new HashMap<>();
            sensor.put("Sensor Name", sensors.get(index).getName());
            sensor.put("Sensor Data", "");
            listSensors.add(sensor);
        }

        SimpleAdapter adapter = new SimpleAdapter(this, listSensors, R.layout.list_sensors,
                new String[]{"Sensor Name", "Sensor Data"},
                new int[]{R.id.name_sensor, R.id.data_sensor});

        listView.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();

        for (int index = 0; index < sensors.size(); ++index) {
            Sensor sensor = sensorManager.getDefaultSensor(sensors.get(index).getType());
            sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();

        sensorManager.unregisterListener(this);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        String dataChanged = "Data: ";
        for (int index = 0; index < event.values.length; ++index) {
            dataChanged += event.values[index] + "\t";
        }

        for (int index = 0; index < listSensors.size(); ++index) {
            if (listSensors.get(index).containsValue(event.sensor.getName())) {
                if (!listSensors.get(index).containsValue(dataChanged)) {
                    listSensors.get(index).put("Sensor Data", dataChanged);
                }
            }
        }

    }
}

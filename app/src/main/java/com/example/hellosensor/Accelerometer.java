package com.example.hellosensor;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class Accelerometer extends AppCompatActivity implements SensorEventListener {

    float x, y, z;
    private SensorManager sensorManager;
    Sensor accelerometer;
    TextView accelerometerValues;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accelerometer);
        accelerometerValues = findViewById(R.id.accelerometerValues);
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorManager.registerListener(Accelerometer.this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        x = event.values[0];
        y = event.values[1];
        z = event.values[2];
        accelerometerValues.setText("x: "+ x +"\ny: "+ y + "\nz: "+z);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
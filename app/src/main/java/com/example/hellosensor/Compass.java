package com.example.hellosensor;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.os.VibrationEffect;
import android.os.Vibrator;

import androidx.appcompat.app.AppCompatActivity;

public class Compass extends AppCompatActivity implements SensorEventListener {

    private ImageView img_compass;
    private TextView txt_azimuth;
    private int azimuth;
    private SensorManager sensorManager;
    private Sensor rotation, accelerometer, magnetometer;
    float[] mat= new float[9];
    float[] orientation = new float[9];
    private float[] tempAccelerometer= new float[3];
    private float[] tempMagnetometer= new float[3];
    private boolean haveSenseAcc = false, haveSenseMag= false;
    private boolean accSetValue= false;
    private boolean magSetValue= false;
    private String direction;


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compass);

        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        magnetometer = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        img_compass = (ImageView) findViewById(R.id.img_compass);
        txt_azimuth = (TextView) findViewById(R.id.txt_azimuth);
        Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);


    }
    @Override
    public void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);

    }
    @Override
    public void onResume() {
        super.onResume();
        sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_GAME);
        sensorManager.registerListener(this, magnetometer, SensorManager.SENSOR_DELAY_GAME);

    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if(event.sensor.getType() == Sensor.TYPE_ROTATION_VECTOR){
            SensorManager.getRotationMatrixFromVector(mat, event.values);
            azimuth= (int)(Math.toDegrees(SensorManager.getOrientation(mat, orientation)[0])+360) % 360;
        }
        if(event.sensor.getType() == Sensor.TYPE_ACCELEROMETER){
            System.arraycopy(event.values, 0 , tempAccelerometer, 0 ,event.values.length);
            accSetValue = true;
        }else if(event.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD){
            System.arraycopy(event.values, 0 , tempMagnetometer, 0, event.values.length);
            magSetValue = true;
        }
        if(accSetValue && magSetValue ){
            SensorManager.getRotationMatrix(mat, null, tempAccelerometer, tempMagnetometer);
            SensorManager.getOrientation(mat, orientation);
            azimuth = (int) (Math.toDegrees(SensorManager.getOrientation(mat, orientation)[0])+ 360) % 360;
        }
        azimuth = Math.round(azimuth);
        img_compass.setRotation(-azimuth);



        if(azimuth >= 350 || azimuth <= 10)
            direction= "N";

        if(azimuth < 350 && azimuth > 280)
            direction= "NW";

        if(azimuth <= 280 && azimuth > 260)
            direction= "W";

        if(azimuth <= 260 && azimuth > 190)
            direction= "SW";

        if(azimuth <= 190 && azimuth > 170)
            direction= "S";

        if(azimuth <= 170 && azimuth  > 100)
            direction= "SE";

        if(azimuth <= 100 && azimuth > 80)
            direction= "E";

        if(azimuth <= 80 && azimuth > 10)
            direction= "NE";


        txt_azimuth.setText(azimuth+ "° "+ direction);





    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}

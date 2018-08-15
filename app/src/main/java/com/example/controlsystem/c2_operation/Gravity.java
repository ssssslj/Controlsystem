package com.example.controlsystem.c2_operation;

import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;

import com.example.controlsystem.R;
import com.example.controlsystem.util.SendMessage;

/**
 * Created by 12969 on 2018/8/12.
 */

public class Gravity extends Activity implements SensorEventListener {
    private SensorManager sensorManager;
    EditText gravity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.c2_gravity);

        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
    }

    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(this,sensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY),SensorManager.SENSOR_DELAY_GAME);
    }

    @Override
    protected void onStop() {
        sensorManager.unregisterListener(this);
        super.onStop();
    }

    @Override
    protected void onPause() {
        sensorManager.unregisterListener(this);
        super.onPause();
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        float[] values = sensorEvent.values;
        final double a = 180/Math.PI;
        int angle;
        float max = Math.max(Math.abs(values[0]), Math.abs(values[1]));
        if (values[1] > 0){
            if(values[0] > 0){
                angle = (int)(Math.atan(values[0]/values[1])*a);
            }else {
                angle = 180 -(int) (Math.atan(values[0]/values[1])*a);
            }
        }else {
            if(values[0] > 0) {
                angle = 0-(int) (Math.atan(values[0] / values[1])*a);
            }else{
                angle = (int) (Math.atan(values[0]/values[1])*a)-180;
            }
        }
        SendMessage.send("    "+max+"--"+angle);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}

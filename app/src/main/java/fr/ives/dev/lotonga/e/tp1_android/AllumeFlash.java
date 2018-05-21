package fr.ives.dev.lotonga.e.tp1_android;

import android.content.Context;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorListener;
import android.hardware.SensorManager;
import android.hardware.camera2.CameraManager;
import android.os.Build;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class AllumeFlash extends AppCompatActivity implements SensorEventListener{

    private SensorManager sensorManager;
    private long lastUpdate;
    private Boolean flashOn = false;
    private RelativeLayout root;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.flash);
        this.root = (RelativeLayout) findViewById(R.id.main_layout);
        this.sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        this.sensorManager.registerListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL);
        this.lastUpdate = System.currentTimeMillis();
        Toast.makeText(this, "FLASH", Toast.LENGTH_SHORT).show();
    }

    /**
     * Eteindre le flash
     */
    @RequiresApi(api = Build.VERSION_CODES.M)
    private void switchOff()
    {
        CameraManager cm = (CameraManager) getSystemService(Context.CAMERA_SERVICE);
        try
        {
            String[] tmp = cm.getCameraIdList();
            String cameraId = tmp[0];
            cm.setTorchMode(cameraId, false);
            this.flashOn = false;
            this.root.setBackgroundColor(Color.BLACK);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    /**
     * Allume le flash
     */
    @RequiresApi(api = Build.VERSION_CODES.M)
    private void switchOn()
    {
        CameraManager cm = (CameraManager) getSystemService(Context.CAMERA_SERVICE);
        try
        {
            String[] tmp = cm.getCameraIdList();

            String cameraId = tmp[0];
            cm.setTorchMode(cameraId, true);
            this.flashOn = true;
            this.root.setBackgroundColor(Color.YELLOW);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onSensorChanged(SensorEvent event) {
        float[] values = event.values;
        // Movement
        float x = values[0];
        float y = values[1];
        float z = values[2];
        //get acceleration
        float accelationSquareRoot = (x * x + y * y + z * z) / (SensorManager.GRAVITY_EARTH * SensorManager.GRAVITY_EARTH);
        if( accelationSquareRoot > 4)
        {
            if(this.flashOn)
            {
                this.switchOff();
            }
            else
            {
                this.switchOn();
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            stopListening();
            Log.d("BACK", "back button pressed");
        }
        return super.onKeyDown(keyCode, event);
    }

    public void stopListening()
    {
        sensorManager.unregisterListener(this );
    }
}

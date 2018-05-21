package fr.ives.dev.lotonga.e.tp1_android;

import android.content.Context;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class Accelerometre extends AppCompatActivity implements SensorEventListener{

    private static final int CAMERA_REQUEST = 50;
    private SensorManager sensorManager;
    private static final int SENSOR_SENSITIVITY = 4;
    private long lastUpdate;
    private boolean color = false;
    RelativeLayout rL_Accelerometer;
    private float last_x, last_y, last_z;
    private static final int SHAKE_THRESHOLD = 800;
    private final static double FAIBLE = 1.3,  MOYENNE = 2.0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.accelerometre_couleur_background);
        this.rL_Accelerometer = (RelativeLayout) findViewById(R.id.main_layout);
        Toast.makeText(this, "ACCELEROMETRE", Toast.LENGTH_SHORT).show();
        this.sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        this.sensorManager.registerListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL);

    }

    /*
     *
     * On gere la couleur du background selon des valeur suivantes : FAIBLE, MOYENNE, HAUTE
     */
    @Override
    public void onSensorChanged(SensorEvent event) {

        float[] values = event.values;
        // Movement
        float x = values[0];
        float y = values[1];
        float z = values[2];
        //Calcul de l'acceleration
        float accelationSquareRoot = (x * x + y * y + z * z) / (SensorManager.GRAVITY_EARTH * SensorManager.GRAVITY_EARTH);
        //get current time
        Log.i("VALUES ", "ACCELATIONQUAREROOT: " + accelationSquareRoot);
        long actualTime = System.currentTimeMillis();
        if (accelationSquareRoot <= FAIBLE )
        {
            rL_Accelerometer.setBackgroundColor(Color.GREEN);
        }
        else if(accelationSquareRoot > FAIBLE && accelationSquareRoot <= MOYENNE )
        {
            rL_Accelerometer.setBackgroundColor(Color.BLACK);
        }

        else if( accelationSquareRoot > MOYENNE)
        {
            rL_Accelerometer.setBackgroundColor(Color.RED);

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

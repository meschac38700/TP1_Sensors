package fr.ives.dev.lotonga.e.tp1_android;

import android.content.Context;
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
import android.widget.TextView;
import android.widget.Toast;

public class Direction extends AppCompatActivity  implements SensorEventListener {

    private long lastUpdate;
    private TextView position;
    private SensorManager sensorManager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.direction_appareil);
        this.position = (TextView) findViewById(R.id.position);
        this.sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        this.sensorManager.registerListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL);
        this.lastUpdate = System.currentTimeMillis();
        Toast.makeText(this, "DIRECTION", Toast.LENGTH_SHORT).show();
    }
    /**
     * Calcule la position de l'appareil grace au coordonnées x et y retourner par SensorEvent
     * @param event
     */
    private void getDirection(SensorEvent event)
    {
        long curTime = System.currentTimeMillis();
        // only allow one update every 100ms.
        if ((curTime - lastUpdate) > 100)
        {
            long diffTime = (curTime - lastUpdate);
            lastUpdate = curTime;
            float x = event.values[SensorManager.DATA_X];
            float y = event.values[SensorManager.DATA_Y];
            if (y >= -9 && y <= -1)
            {
                this.position.setText("****** BAS ******");
                Log.i("POSITION_2", "BAS");
            }
            else if (y >= 2 && y <= 9)
            {
                this.position.setText("****** HAUT ******");
                Log.i("POSITION_2", "HAUT");
            }
            else if (x >= -9 && x <= -1)
            {
                this.position.setText("****** DROITE ******");
                Log.i("POSITION_2", "DROITE");;
            }
            else if (x >= 1 && x <= 9)
            {
                this.position.setText("****** GAUCHE ******");
                Log.i("POSITION_2", "GAUCHE");
            }
            else /*if(x >= -1.001 && x < 0.999 && y >= -1.001 && y < 0.999)*/
            {
                this.position.setText("****** PLAT ******");
                Log.i("POSITION_2", "PLAT");
            }
        }
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        this.getDirection(event);
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

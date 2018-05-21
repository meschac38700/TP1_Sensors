package fr.ives.dev.lotonga.e.tp1_android;

import android.content.Context;
import android.graphics.drawable.Drawable;
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
import android.widget.Toast;

public class CapteurProximite extends AppCompatActivity implements SensorEventListener {

    private ImageView image;
    private static final int SENSOR_SENSITIVITY = 4;
    private SensorManager sensorManager;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.proximite);
        this.image = (ImageView) findViewById(R.id.img_proximite);
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        sensorManager.registerListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY), SensorManager.SENSOR_DELAY_NORMAL);
        Toast.makeText(this, "PROXIMITE", Toast.LENGTH_SHORT).show();
    }

    /**
     * Change l'image de la vue selon le capteur de proximité
     */
    private void manageProximity(SensorEvent event)
    {
        //Detection object proche de l'appreil
        if (event.values[0] >= -SENSOR_SENSITIVITY && event.values[0] <= SENSOR_SENSITIVITY) {
            Log.i("PROXIMITY", "onSensorChanged: " + event.sensor.getType() + " / PROXY=> " + Sensor.TYPE_PROXIMITY + "ACCELERE => " + Sensor.TYPE_ACCELEROMETER);
            Drawable myIcon = getResources().getDrawable( R.drawable.proche );
            this.image.setImageDrawable(myIcon);
        }
        else
        {//Detection object loin de l'appreil
            Drawable myIcon = getResources().getDrawable( R.drawable.loin_img );
            this.image.setImageDrawable(myIcon);
        }
    }
    @Override
    public void onSensorChanged(SensorEvent event) {
        manageProximity(event);
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

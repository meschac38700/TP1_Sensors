package fr.ives.dev.lotonga.e.tp1_android;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.TextView;
import android.widget.Toast;

public class CapteursAbsents extends AppCompatActivity implements SensorEventListener {

    private TextView capteurs_absent;
    private SensorManager sensorManager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.liste_capteurs_absents);
        this.capteurs_absent = (TextView) findViewById(R.id.capteurs_absent);
        this.sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        displaySensorNotAvailable(Sensor.TYPE_MAGNETIC_FIELD, "MAGNETIC_FIELD");
        Toast.makeText(this, "CAPTEURS ABSENTS", Toast.LENGTH_SHORT).show();
    }

    /**
     * Affichage d'un sensor non supporté
     * @param sensorType
     * @param nameSensor
     */
    private void displaySensorNotAvailable(int sensorType, String nameSensor)
    {
        try
        {

            Sensor temperature = this.sensorManager.getDefaultSensor(sensorType);
            this.sensorManager.registerListener(this, sensorManager.getDefaultSensor(temperature.getType()), SensorManager.SENSOR_DELAY_NORMAL);
        }
        catch(Exception e)
        {
            this.capteurs_absent.setText("\tNAME : "+ nameSensor +" is not available !");
            //Toast.makeText(this,"\tNAME : "+ nameSensor +" is not available !" , Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onSensorChanged(SensorEvent event) {

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

}

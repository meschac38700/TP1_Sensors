package fr.ives.dev.lotonga.e.tp1_android;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class ListeCapteurs extends AppCompatActivity  implements SensorEventListener {
    private SensorManager sensorManager;
    private TextView tv;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.liste_capteurs);
        this.tv = (TextView) findViewById(R.id.affichageCapteur);
        this.sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        listSensor();
        Toast.makeText(this, "LISTE CAPTEURS", Toast.LENGTH_SHORT).show();
    }

    /**
     * Liste tous les capteurs disponible de l'appareil
     */
    private void listSensor() {
        // Trouver tous les capteurs de l'appareil :
        List<Sensor> sensors = sensorManager.getSensorList(Sensor.TYPE_ALL);
        // La chaîne descriptive de chaque capteur
        StringBuffer sensorDesc = new StringBuffer();
        Log.i("LENGTH SENSORS", "listSensor: " + sensors.size());
        // pour chaque capteur trouvé, construire sa chaîne descriptive
        for (Sensor sensor : sensors) {

            String dispo = null;
            sensorDesc.append("\tNAME : " + sensor.getName() + "\r\n\tType : " + getType(sensor.getType()) + "\r\n\tVersion : " + sensor.getVersion() + "\r\n\tDisponibilité : " + dispo + "\r\n");
            sensorDesc.append("*********************************");
            sensorDesc.append("\r\n");
            /*
            sensorDesc.append("\tVERSION: " + sensor.getVersion() + "\r\n");
            sensorDesc.append("\tRESOLUTION (in the sensor unit) : " + sensor.getResolution() + "\r\n");
            sensorDesc.append("\tPOWER IN MA USED BY THIS SENSOR WHILE IN USE " + sensor.getPower() + "\r\n");
            sensorDesc.append("\tVENDOR : " + sensor.getVendor() + "\r\n");
            sensorDesc.append("\tMAXIMUM RANGE OF THE SENSOR IN THE SENSOR's UNIT." + sensor.getMaximumRange() + "\r\n");
            sensorDesc.append("\tMINIMUM DELAY ALLOWED BETWEEN TWO EVENTS IN MICROSECOND" + " OR ZERO IF THIS SENSOR ONLY RETURNS A VALUE WHEN THE DATA IT'S MEASURING CHANGES" + sensor.getMinDelay() + "\r\n");*/
        }
        tv.setText(sensorDesc.toString());
    }

    /**
     * retourne le type du capteur selon son id
     * @param type
     * @return
     */
    private String getType(int type) {
        String strType;
        switch (type) {
            case Sensor.TYPE_ACCELEROMETER:
                strType = "TYPE_ACCELEROMETER";
                break;
            case Sensor.TYPE_GRAVITY:
                strType = "TYPE_GRAVITY";
                break;
            case Sensor.TYPE_GYROSCOPE:
                strType = "TYPE_GYROSCOPE";
                break;
            case Sensor.TYPE_LIGHT:
                strType = "TYPE_LIGHT";
                break;
            case Sensor.TYPE_LINEAR_ACCELERATION:
                strType = "TYPE_LINEAR_ACCELERATION";
                break;
            case Sensor.TYPE_MAGNETIC_FIELD:
                strType = "TYPE_MAGNETIC_FIELD";
                break;
            case Sensor.TYPE_ORIENTATION:
                strType = "TYPE_ORIENTATION";
                break;
            case Sensor.TYPE_PRESSURE:
                strType = "TYPE_PRESSURE";
                break;
            case Sensor.TYPE_PROXIMITY:
                strType = "TYPE_PROXIMITY";
                break;
            case Sensor.TYPE_ROTATION_VECTOR:
                strType = "TYPE_ROTATION_VECTOR";
                break;
            case Sensor.TYPE_TEMPERATURE:
                strType = "TYPE_TEMPERATURE";
                break;
            default:
                strType = "TYPE_UNKNOW";
                break;
        }
        return strType;
    }

    @Override
    public void onSensorChanged(SensorEvent event) {

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }


}

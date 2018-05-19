package fr.ives.dev.lotonga.e.tp1_android;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorListener;
import android.hardware.SensorManager;
import android.hardware.camera2.CameraManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import static fr.ives.dev.lotonga.e.tp1_android.R.drawable.proche;

public class MainActivity extends AppCompatActivity  implements SensorEventListener {
    private static final int CAMERA_REQUEST = 50;
    private TextView tv;
    private SensorManager sensorManager;
    private static final int SENSOR_SENSITIVITY = 4;
    private long lastUpdate;
    private boolean color = false;
    RelativeLayout rL_Accelerometer;
    private ImageView image;
    private float last_x, last_y, last_z;
    private static final int SHAKE_THRESHOLD = 800;
    private TextView position;
    private Boolean flashOn = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.position = (TextView) findViewById(R.id.position);
        this.tv = (TextView) findViewById(R.id.affichageCapteur);
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        rL_Accelerometer = (RelativeLayout) findViewById(R.id.rL_Accelerometer);
        rL_Accelerometer.setBackgroundColor(Color.GREEN);
        lastUpdate = System.currentTimeMillis();
        this.image = (ImageView) findViewById(R.id.image);
        this.listSensor();
        displaySensorNotAvailable(Sensor.TYPE_MAGNETIC_FIELD, "MAGNETIC_FIELD");
    }

    private void listSensor() {
        // Trouver tous les capteurs de l'appareil :
        List<Sensor> sensors = sensorManager.getSensorList(Sensor.TYPE_ALL);
        // La chaîne descriptive de chaque capteur
        StringBuffer sensorDesc = new StringBuffer();
        Log.i("LENGTH SENSORS", "listSensor: " + sensors.size());
        //Toast.makeText(this, "LENGTH ********** "+ sensors.size()+ " ******************", Toast.LENGTH_LONG).show();
        //sensorDesc.append(this.displaySensorNotAvailable(Sensor.TYPE_MAGNETIC_FIELD, "MAGNETIC_FIELD"));
        // pour chaque capteur trouvé, construire sa chaîne descriptive
        for (Sensor sensor : sensors) {
            //accelerometer = sensorMgr.registerListener(this,sensorMgr.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
            //boolean accelerometer;
            sensorManager.registerListener(this, sensorManager.getDefaultSensor(sensor.getType()), SensorManager.SENSOR_DELAY_NORMAL);
            String dispo = null;
            if (this.sensorManager.getDefaultSensor(sensor.getType()) != null) {
                dispo = "True";
            } else {
                dispo = "False";
            }
            //accelerometer = this.sensorManager.registerListener(this,this.sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER));
           // boolean supported = sensorManager.registerListener((SensorListener) this, sensor.ge);
            if (this.sensorManager.getDefaultSensor(sensor.getType()) == null){
                //disponibleDesc.append("\t"+String.valueOf(false)+"\n\r\t\n\r\t");
                dispo = "False";
            }
            else
            {
                dispo = "True";
                //disponibleDesc.append("\t"+String.valueOf(true)+"\n\r\n\r\r\n****************");
            }
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

        // Faire quelque chose de cette liste
        //Toast.makeText(this, sensorDesc.toString(), Toast.LENGTH_LONG).show();
        tv.setText(sensorDesc.toString());

        // Pour trouver un capteur spécifique&#160;:
        Sensor gyroscopeDefault = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
        // Pour trouver tous les capteurs d'un type fixé :
        List<Sensor> gyroscopes = sensorManager.getSensorList(Sensor.TYPE_GYROSCOPE);
    }


//METHODE APPELLE POUR AFFICHER LE TYPE DU CAPTEUR....

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

    public static float Round(float Rval, int Rpl) {
        float p = (float)Math.pow(10,Rpl);
        Rval = Rval * p;
        float tmp = Math.round(Rval);
        return (float)tmp/p;
    }
    // called when sensor value have changed
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            manageAccelerometer(event);

            long curTime = System.currentTimeMillis();
            // only allow one update every 100ms.
            if ((curTime - lastUpdate) > 100) {
                long diffTime = (curTime - lastUpdate);
                lastUpdate = curTime;
                float x = event.values[SensorManager.DATA_X];
                float y = event.values[SensorManager.DATA_Y];
               // float z = event.values[SensorManager.DATA_Z];
               //Log.i("POSITION_3", "onSensorChanged: => "+ x  +" / "+ y  +" / "+ z);
                if(y >= -9 && y <= -1 )
                {
                    this.position.setText("********** POSITION APPAREIL **********\n************** BAS ***************\n********** POSITION APPAREIL **********");
                    Log.i("POSITION_2", "BAS");
                    //Toast.makeText(this, "Bas", Toast.LENGTH_SHORT).show();
                }
                else if ( y >= 2 && y <= 9)
                {
                    this.position.setText("********** POSITION APPAREIL **********\r\n************** HAUT ***************\r\n********** POSITION APPAREIL **********");
                    Log.i("POSITION_2", "HAUT");
                    //Toast.makeText(this, "Haut", Toast.LENGTH_SHORT).show();
                }
                else if( x >= -9 && x <= -1 )
                {
                    this.position.setText("********** POSITION APPAREIL **********\n" +
                            "************** DROITE ***************\n" +
                            "********** POSITION APPAREIL **********");
                    Log.i("POSITION_2", "DROITE");
                    //Toast.makeText(this, "DROITE", Toast.LENGTH_SHORT).show();
                }
                else if( x >= 1 && x <= 9)
                {
                    this.position.setText("********** POSITION APPAREIL **********\n" +
                            "************** GAUCHE ***************\n" +
                            "********** POSITION APPAREIL **********");
                    Log.i("POSITION_2", "GAUCHE");
                    //Toast.makeText(this, "GAUCHE", Toast.LENGTH_SHORT).show();
                }
                else /*if(x >= -1.001 && x < 0.999 && y >= -1.001 && y < 0.999)*/
                {
                    this.position.setText("********** POSITION APPAREIL **********\n" +
                            "************** PLAT ***************\n" +"********** POSITION APPAREIL **********");
                    Log.i("POSITION_2", "PLAT");
                    // Toast.makeText(this, "DEVICE à plat", Toast.LENGTH_SHORT).show();
                }
            }
        }
        if (event.sensor.getType() == Sensor.TYPE_PROXIMITY) {
            if (event.values[0] >= -SENSOR_SENSITIVITY && event.values[0] <= SENSOR_SENSITIVITY) {
                Log.i("PROXIMITY", "onSensorChanged: " + event.sensor.getType() + " / PROXY=> " + Sensor.TYPE_PROXIMITY + "ACCELERE => " + Sensor.TYPE_ACCELEROMETER);
                //near
               // Log.i("PROXIMITY", "****************** near ******************");
                //Toast.makeText(getApplicationContext(), "****************** near ******************", Toast.LENGTH_SHORT).show();
                Drawable myIcon = getResources().getDrawable( R.drawable.proche );
                this.image.setImageDrawable(myIcon);
            }
            else
            {
                Drawable myIcon = getResources().getDrawable( R.drawable.loin_img );
                this.image.setImageDrawable(myIcon);
            }
        }

        /*if(event.sensor.getType() == Sensor.TYPE_SIGNIFICANT_MOTION)
        {
            float x = event.values[0];
            float y = event.values[1];
            for(float f : event.values)
            {
                Log.i("MOTION", "onSensorChanged: "+f);
            }
            Log.i("MOTION", "onSensorChanged: => "+ x + " / "+ y);
            long curTime = System.currentTimeMillis();
            if ((curTime - lastUpdate) > CHECK_INTERVAL) {
                lastUpdate = curTime;




            }

        }*/


    }

    private void displaySensorNotAvailable(int sensorType, String nameSensor)
    {
        try
        {

            Sensor temperature = this.sensorManager.getDefaultSensor(sensorType);
            this.sensorManager.registerListener(this, sensorManager.getDefaultSensor(temperature.getType()), SensorManager.SENSOR_DELAY_NORMAL);
        }
        catch(Exception e)
        {
            Toast.makeText(this,"\tNAME : "+ nameSensor +" is not available !" , Toast.LENGTH_SHORT).show();
        }

    }

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
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

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
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
    
    @RequiresApi(api = Build.VERSION_CODES.M)
    private void manageAccelerometer(SensorEvent event) {

        //Toast.makeText(this, "GetAccelerometer", Toast.LENGTH_SHORT).show();
        float[] values = event.values;
        // Movement
        float x = values[0];
        float y = values[1];
        float z = values[2];
        //get acceleration
        float accelationSquareRoot = (x * x + y * y + z * z) / (SensorManager.GRAVITY_EARTH * SensorManager.GRAVITY_EARTH);
        //get current time
        Log.i("VALUES ", "ACCELATIONQUAREROOT: " + accelationSquareRoot);
        long actualTime = System.currentTimeMillis();
        if (accelationSquareRoot <= 1.2 )
        {
            rL_Accelerometer.setBackgroundColor(Color.GREEN);
        }
        else if(accelationSquareRoot > 1.2 && accelationSquareRoot <= 1.8 )
        {
            rL_Accelerometer.setBackgroundColor(Color.BLACK);
        }

        else
        {
            rL_Accelerometer.setBackgroundColor(Color.RED);

        }
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
    protected void onResume()
    {
        super.onResume();
        List<Sensor> sensors = sensorManager.getSensorList(Sensor.TYPE_ALL);
        for (Sensor sensor : sensors) {

            sensorManager.registerListener(this,sensorManager.getDefaultSensor(sensor.getType()),SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    @Override
    protected void onPause() {
        // unregister listener
        super.onPause();
        sensorManager.unregisterListener(this);
    }

}

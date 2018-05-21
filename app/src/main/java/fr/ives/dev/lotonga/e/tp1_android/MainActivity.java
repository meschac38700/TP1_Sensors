package fr.ives.dev.lotonga.e.tp1_android;

import android.app.Activity;
import android.app.usage.UsageEvents;
import android.arch.lifecycle.Lifecycle;
import android.content.Context;
import android.content.Intent;
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
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import static android.provider.AlarmClock.EXTRA_MESSAGE;
import static fr.ives.dev.lotonga.e.tp1_android.R.drawable.proche;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button direction, flash, liste_capteurs, proximite, abs_pres_capteur, acceleromtre_bg_color;
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.direction= (Button) findViewById(R.id.direction);
        this.flash = (Button) findViewById(R.id.flash);
        this.liste_capteurs = (Button) findViewById(R.id.liste_capteurs);
        this.proximite= (Button) findViewById(R.id.proximite);
        this.acceleromtre_bg_color = (Button) findViewById(R.id.acceleromtre_bg_color);
        this.abs_pres_capteur = (Button) findViewById(R.id.abs_pres_capteur);

        this.direction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "DIRECTION", Toast.LENGTH_SHORT).show();
                directionView();
            }
        });

        this.flash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "FLASH", Toast.LENGTH_SHORT).show();
                allumeFlashView();
            }
        });

        this.liste_capteurs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "LISTE CAPTEURS", Toast.LENGTH_SHORT).show();
                listeCapteursView();
            }
        });

        this.proximite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "PROXIMITE", Toast.LENGTH_SHORT).show();
                capteurProximiteView();
            }
        });

        this.abs_pres_capteur.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "CAPTEURS NON SUPPORTES", Toast.LENGTH_SHORT).show();
                capteursAbsentsView();
            }
        });

        this.acceleromtre_bg_color.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "ACCELEROMETRE BACKGROUND COLOR", Toast.LENGTH_SHORT).show();
                accelerometreView();
            }
        });
    }


    /**
     * Creation de Intent pour l'activity Direction
     */
    private void directionView()
    {
        Intent intent = new Intent(this, Direction.class);
        startActivity(intent);
    }
    /**
     * Creation de Intent pour l'activity ListeCapteurs
     */
    private  void listeCapteursView()
    {
        Intent intent = new Intent(this, ListeCapteurs.class);
        startActivity(intent);
    }

    /**
     * Creation de Intent pour l'activity CapteursAbsents
     */
    private void capteursAbsentsView()
    {
        Intent dir = new Intent(this, CapteursAbsents.class);
        startActivity(dir);
    }
    /**
     * Creation de Intent pour l'activity CapteurProximite
     */
    private  void capteurProximiteView()
    {
        Intent intent = new Intent(this, CapteurProximite.class);
        startActivity(intent);
    }

    /**
     * Creation de Intent pour l'activity Accelerometre
     */
    private void accelerometreView()
    {
        Intent intent = new Intent(this, Accelerometre.class);
        startActivity(intent);
    }
    /**
     * Creation de Intent pour l'activity AllumeFlash
     */
    private  void allumeFlashView()
    {
        Intent intent = new Intent(this, AllumeFlash.class);
        startActivity(intent);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case  R.id.direction :
                Toast.makeText(this, "Directio n", Toast.LENGTH_SHORT).show();
                //Ce que tu veux faire lorsque tu cliques sur le bouton 1
                break;

            case R.id.flash :
                Toast.makeText(this, "Flash", Toast.LENGTH_SHORT).show();
                //Ce que tu veux faire lorsque tu cliques sur le bouton 2
                break;
        }
        Log.d("CLICK", "onClick: ");
        Toast.makeText(this, "Clique !!!!", Toast.LENGTH_SHORT).show();
    }


}

package com.controlderuta.guardianroute;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.controlderuta.guardianroute.Model.PreData;
import com.controlderuta.guardianroute.Model.RouteData;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RouteActivity extends AppCompatActivity {


    //variables firebase

    DatabaseReference databaseReference;

    //Botones y layout

    private Button btnSigRoute;
    private TextView textCodigo;
    private FloatingActionButton btnContactosRoute;


    // Variables


    private String PruUidCuatro;
    private String textcode;
    private String nameRoute="RUTA 1";
    private double longitud=0;
    private double latitud=0;
    private int tipRoute=0;
    private int estado=0;
    private int tipNotification=0;
    private int alertDist=1000;
    private float acumDist=0;
    private int time=0;
    private String link;


    //variables random


    private String cadenacod1;
    private String cadenacod2;
    private int random1;
    private int random2;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route);

        showToolbar(getResources().getString(R.string.layoutCreateRoute), false);//llamamos la toolbar

        textCodigo          = (TextView)findViewById(R.id.codigoruote);
        btnSigRoute         = (Button)findViewById(R.id.btnSigRoute);
        btnContactosRoute   = (FloatingActionButton)findViewById(R.id.fabcontactosroute);

        random1=(int)(Math.random()*100);
        cadenacod1 = String.valueOf(random1);
        random2=(int)(Math.random()*100);
        cadenacod2 = String.valueOf(random2);
        textcode = "GP"+random1+"AY"+random2;

        textCodigo.setText(textcode);


        btnSigRoute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                databaseReference = FirebaseDatabase.getInstance().getReference();//obtiene el enlace de la db "ejemplos-android:"
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();//Para extraeerr el Uid del cliente
                PruUidCuatro=user.getUid(); //Guardamos Uid en variable

                RouteData uproutedata = new RouteData(PruUidCuatro,textcode,nameRoute,longitud,latitud,tipRoute,estado,tipNotification,alertDist,acumDist,time);
                databaseReference.child("datosruta").child(uproutedata.getId()).setValue(uproutedata);


                Intent intent = new Intent(RouteActivity.this, MapsActivity.class);
                startActivity(intent);
                finish();


            }
        });

        btnContactosRoute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



              shareDeepLink(buildDeepLink(""));

            }
        });

    }



    private String buildDeepLink(String key){

        link="¡Unete a mi Ruta Guardian! Usa el codigo de invitación "+textcode+".Descarga la aquí la app:https://drive.google.com/open?id=0B3lY_7IS7EICeExHZUJfVFQyRFU";
        return link;
    }

    private void shareDeepLink(String deepLink){
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_SUBJECT,"Firebase DeepLink Share");
        intent.putExtra(Intent.EXTRA_TEXT, deepLink);
        startActivity(intent);
    }

    public void showToolbar (String tittle, boolean upButton){//Metoodo de la toolbar

        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(tittle);
        getSupportActionBar().setDisplayHomeAsUpEnabled(upButton);//habilitamos la visibilidad de botton de up
    }
}

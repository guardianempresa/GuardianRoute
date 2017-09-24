package com.controlderuta.guardianroute;

import android.content.Intent;
import android.content.res.Resources;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import es.dmoral.toasty.Toasty;

public class NewAlertActivity extends AppCompatActivity {

    Button btnalert;
    String Code;
    FloatingActionButton btnTraffic;
    FloatingActionButton btnEmergency;
    FloatingActionButton btnCrash;

    DatabaseReference databaseReference;

    String keyfecha;
    String ampm;
    String one;
    String two;
    String three;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_alert);

        Code=getIntent().getExtras().getString("parametro");

        one=getText(R.string.alertone).toString();
        two=getText(R.string.alerttwo).toString();
        three=getText(R.string.alertthree).toString();


        showToolbar("", false);//llamamos la toolbar

        btnCrash=(FloatingActionButton)findViewById(R.id.fabalertcrash);
        btnEmergency=(FloatingActionButton)findViewById(R.id.fabalertemergecy);
        btnTraffic=(FloatingActionButton)findViewById(R.id.fabalerttrafic);

        btnalert=(Button)findViewById(R.id.backalert);


        btnalert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SimpleDateFormat s = new SimpleDateFormat("hh:mm:ss");
                keyfecha = s.format(new Date());

                databaseReference = FirebaseDatabase.getInstance().getReference();//obtiene el enlace de la db "ejemplos-android:"
                databaseReference.child("alert").child(Code).child("type").setValue(0);
                databaseReference.child("alert").child(Code).child("hour").setValue(keyfecha);

                Intent intent = new Intent(NewAlertActivity.this, NewMapActivity.class);
                intent.putExtra("parametro", Code);
                startActivity(intent);
                finish();
            }
        });

        btnTraffic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SimpleDateFormat s = new SimpleDateFormat("hh:mm:ssaa");
                keyfecha = s.format(new Date());

                databaseReference = FirebaseDatabase.getInstance().getReference();//obtiene el enlace de la db "ejemplos-android:"
                databaseReference.child("alert").child(Code).child("type").setValue(1);
                databaseReference.child("alert").child(Code).child("hour").setValue(keyfecha);


                Toasty.Config.getInstance() //Configuracion del toasty

                        .setInfoColor(ContextCompat.getColor(getApplicationContext(),R.color.colorTraffic)) //Color de relleno
                        .setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.colorWhite))  //Color de letra
                        .apply();

                Toasty.info(NewAlertActivity.this,one, Toast.LENGTH_LONG, true).show();//info del toast

                Intent intent = new Intent(NewAlertActivity.this, NewMapActivity.class);
                intent.putExtra("parametro", Code);
                startActivity(intent);
                finish();



            }
        });

        btnEmergency.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DateFormat s = new SimpleDateFormat("hh:mm:ssaa");
                keyfecha = s.format(new Date());


                databaseReference = FirebaseDatabase.getInstance().getReference();//obtiene el enlace de la db "ejemplos-android:"
                databaseReference.child("alert").child(Code).child("type").setValue(2);
                databaseReference.child("alert").child(Code).child("hour").setValue(keyfecha);


                Toasty.Config.getInstance() //Configuracion del toasty

                        .setInfoColor(ContextCompat.getColor(getApplicationContext(),R.color.colorEmergency)) //Color de relleno
                        .setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.colorWhite))  //Color de letra
                        .apply();

                Toasty.info(NewAlertActivity.this,two, Toast.LENGTH_LONG, true).show();//info del toast

                Intent intent = new Intent(NewAlertActivity.this, NewMapActivity.class);
                intent.putExtra("parametro", Code);
                startActivity(intent);
                finish();


            }
        });

        btnCrash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SimpleDateFormat s = new SimpleDateFormat("hh:mm:ssaa");
                keyfecha = s.format(new Date());

                databaseReference = FirebaseDatabase.getInstance().getReference();//obtiene el enlace de la db "ejemplos-android:"
                databaseReference.child("alert").child(Code).child("type").setValue(3);
                databaseReference.child("alert").child(Code).child("hour").setValue(keyfecha);


                Toasty.Config.getInstance() //Configuracion del toasty

                        .setInfoColor(ContextCompat.getColor(getApplicationContext(),R.color.colorMechanich)) //Color de relleno
                        .setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.colorWhite))  //Color de letra
                        .apply();

                Toasty.info(NewAlertActivity.this,three,Toast.LENGTH_LONG, true).show();//info del toast

                Intent intent = new Intent(NewAlertActivity.this, NewMapActivity.class);
                intent.putExtra("parametro", Code);
                startActivity(intent);
                finish();

            }
        });

    }

    public void showToolbar (String tittle, boolean upButton){//Metoodo de la toolbar

        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbarselectlist);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(tittle);
        getSupportActionBar().setDisplayHomeAsUpEnabled(upButton);//habilitamos la visibilidad de botton de up
    }
}

package com.controlderuta.guardianroute;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.controlderuta.guardianroute.Model.AutoData;
import com.controlderuta.guardianroute.Model.PreData;
import com.controlderuta.guardianroute.Model.RouteData;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Map;

public class AutoActivity extends AppCompatActivity {


    //variables firebase

    DatabaseReference databaseReference;

    //variables de layout


    EditText textplaca;
    EditText textmarca;
    EditText textmodelo;
    Button btnSiguienteAutodata;



    private String placa;
    private String marca;
    private String modelo;
    private String PruUidTres;
    private int a=0, b=0, c=0, autovalidador;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auto);
        showToolbar(getResources().getString(R.string.layoutpredata), true);//llamamos la toolbar


        textplaca   = (EditText)findViewById(R.id.placa);
        textmarca   = (EditText)findViewById(R.id.marca);
        textmodelo  = (EditText)findViewById(R.id.modelo);


        databaseReference = FirebaseDatabase.getInstance().getReference();//obtiene el enlace de la db "ejemplos-android:"
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();//Para extraeerr el Uid del cliente
        PruUidTres=user.getUid(); //Guardamos Uid en variable

        btnSiguienteAutodata = (Button)findViewById(R.id.btnsiguienteAuto);

        btnSiguienteAutodata.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                placa = textplaca.getText().toString();
                marca = textmarca.getText().toString();
                modelo = textmodelo.getText().toString();


                if (placa.equals("")) {

                    Toast.makeText(AutoActivity.this, "Falta numero de matricula",
                            Toast.LENGTH_SHORT).show();
                    a=1;
                }else{
                    a=0;
                }

                if (modelo.equals("")) {

                    Toast.makeText(AutoActivity.this, "Faltan registrar modelo del vehículo",
                            Toast.LENGTH_SHORT).show();
                    a=1;
                }else{
                    a=0;
                }

                if (marca.equals("")) {

                    Toast.makeText(AutoActivity.this, "Faltan registrar la marca del vehículo",
                            Toast.LENGTH_SHORT).show();
                    a=1;
                }else{
                    a=0;
                }

                autovalidador=a+b+c;

                if (autovalidador==0){

                    AutoData upautodata = new AutoData(PruUidTres, placa, modelo, marca);
                    //sube la longitud y tatitud a la raiz de la id
                    databaseReference.child("datosvehiculo").child(upautodata.getId()).setValue(upautodata);


                    Intent intent = new Intent(AutoActivity.this, RouteActivity.class);
                    startActivity(intent);
                    finish();

                }else {
                    Toast.makeText(AutoActivity.this, "Debe registrar la totalidad de los campos para seguir",
                            Toast.LENGTH_SHORT).show();
                }


            }
        });


    }


    public void showToolbar (String tittle, boolean upButton){//Metoodo de la toolbar

        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(tittle);
        getSupportActionBar().setDisplayHomeAsUpEnabled(upButton);//habilitamos la visibilidad de botton de up


    }
}

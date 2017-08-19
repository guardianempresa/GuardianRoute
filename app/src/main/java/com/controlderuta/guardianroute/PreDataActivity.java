package com.controlderuta.guardianroute;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class PreDataActivity extends AppCompatActivity {


    //variables firebase

    DatabaseReference databaseReference;


    //variables de layout


    EditText textnameMonitor;
    EditText textlastnameMonitor;
    EditText textmobileMonitor;
    EditText textcodEmpresa;
    Button btnSiguientePredata;



    private String nameMonitor;
    private String lastnameMonitor;
    private String mobileMonitor;
    private String codEmpresa;
    private String PruUidDos;
    private int a=0, b=0, c=0, d=0, prevalidador;
    private String codeRuta;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pre_data);

        showToolbar(getResources().getString(R.string.layoutpredata), true);//llamamos la toolbar



        textnameMonitor     = (EditText)findViewById(R.id.monitorname);
        textlastnameMonitor = (EditText)findViewById(R.id.monitorlastname);
        textmobileMonitor   = (EditText)findViewById(R.id.monitorphone);
        textcodEmpresa      = (EditText)findViewById(R.id.idcodempresa);

        databaseReference = FirebaseDatabase.getInstance().getReference();//obtiene el enlace de la db "ejemplos-android:"
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();//Para extraeerr el Uid del cliente
        PruUidDos=user.getUid(); //Guardamos Uid en variable

        btnSiguientePredata = (Button)findViewById(R.id.btnsiguientePredata);



        btnSiguientePredata.setOnClickListener(new View.OnClickListener() {//Realiza acciones cuando damos clic
            @Override
            public void onClick(View v) {


                nameMonitor = textnameMonitor.getText().toString();
                lastnameMonitor = textlastnameMonitor.getText().toString();
                mobileMonitor = textmobileMonitor.getText().toString();
                codEmpresa = textcodEmpresa.getText().toString();
                codeRuta = "AAA001";

                if (nameMonitor.equals("")) {

                    Toast.makeText(PreDataActivity.this, "Faltan campos por registrar",
                            Toast.LENGTH_SHORT).show();
                    a=1;
                }else{
                    a=0;
                }

                if (lastnameMonitor.equals("")) {

                    Toast.makeText(PreDataActivity.this, "Faltan campos por registrar",
                            Toast.LENGTH_SHORT).show();
                    b=1;
                }else{
                    b=0;
                }

                if (mobileMonitor.equals("")) {

                    Toast.makeText(PreDataActivity.this, "Faltan campos por registrar",
                            Toast.LENGTH_SHORT).show();
                    c=1;
                }else   {
                    c=0;
                }

                if (codEmpresa.equals("")) {

                    Toast.makeText(PreDataActivity.this, "Faltan campos por registrar",
                            Toast.LENGTH_SHORT).show();
                    d=1;
                }       else {
                    d=0;
                }

                int prevalidador=a+b+c+d;

                if (prevalidador==0){


                    Intent intent = new Intent(PreDataActivity.this, AutoActivity.class);
                    startActivity(intent);
                    finish();

                }else {
                    Toast.makeText(PreDataActivity.this, "Debe registrar la totalidad de los campos para seguir",
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

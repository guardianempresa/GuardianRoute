package com.controlderuta.guardianroute;

import android.*;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

public class CallActivity extends AppCompatActivity {

    String Iduser;
    String Code;
    String Call;

    FloatingActionButton btnLlamada; //Declarar el boton de llamada

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_call);

        showToolbar("", true);//llamamos la toolbar

        Iduser=getIntent().getExtras().getString("id");
        Code=getIntent().getExtras().getString("parametro");
        Call=getIntent().getExtras().getString("llamada");

        btnLlamada =(FloatingActionButton)findViewById(R.id.fabPhoneCall);
        btnLlamada.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {




                //Codigo ejecucion de llamada

                Intent i = new Intent(Intent.ACTION_CALL, Uri.parse(Call)); //Aui toca definir variable para cambiar numero
                if(ActivityCompat.checkSelfPermission(CallActivity.this, android.Manifest.permission.CALL_PHONE)!=
                        PackageManager.PERMISSION_GRANTED)
                    return;
                startActivity(i);
            }
        });

    }

    public void showToolbar (String tittle, boolean upButton){//Metoodo de la toolbar

        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbarcall);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(tittle);
        getSupportActionBar().setDisplayHomeAsUpEnabled(upButton);//habilitamos la visibilidad de botton de up
    }
}


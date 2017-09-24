package com.controlderuta.guardianroute;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import es.dmoral.toasty.Toasty;

public class PersonalDataActivity extends AppCompatActivity {

    FloatingActionButton btnSigPerson;
    EditText edtNameDriver;
    EditText edtLastNameDriver;
    String nameconductor;
    String lastnameconductor;
    int a=0,b=0;
    int autovalidador;

    //firebase var

    DatabaseReference databaseReference;
    String PruUid; //key no llave generada por google


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_data);

        btnSigPerson        = (FloatingActionButton) findViewById(R.id.btnSigPersonal);
        edtNameDriver       = (EditText)findViewById(R.id.namePerson);
        edtLastNameDriver   = (EditText)findViewById(R.id.lastnamePerson);

        databaseReference = FirebaseDatabase.getInstance().getReference();//obtiene el enlace de la db "ejemplos-android:"
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();//Para extraeerr el Uid del cliente
        PruUid=user.getUid(); //Guardamos Uid en variable

        btnSigPerson.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                nameconductor       = edtNameDriver.getText().toString();
                lastnameconductor   = edtLastNameDriver.getText().toString();

                if (nameconductor.equals("")){
                    a=0;
                    Toasty.Config.getInstance() //Configuracion del toasty

                            .setInfoColor(ContextCompat.getColor(getApplicationContext(),R.color.colorPrimaryDark)) //Color de relleno
                            .setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.colorWhite))  //Color de letra
                            .apply();

                    Toasty.info(PersonalDataActivity.this,getText(R.string.validadorpersonalnamevacio), Toast.LENGTH_SHORT, true).show();//info del toasty

                }else {
                    a=1;
                }

                if (lastnameconductor.equals("")){
                    b=0;
                    Toasty.Config.getInstance() //Configuracion del toasty

                            .setInfoColor(ContextCompat.getColor(getApplicationContext(),R.color.colorPrimaryDark)) //Color de relleno
                            .setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.colorWhite))  //Color de letra
                            .apply();

                    Toasty.info(PersonalDataActivity.this,getText(R.string.validadorpersonallastnamevacio), Toast.LENGTH_SHORT, true).show();//info del toasty

                }else {
                    b=1;
                }

                autovalidador=a+b;




                if (autovalidador==2){

                    //como la base de datos ya aesta creada en el activity anterior solo refresacamos los datos de nombre y apellidosllegando al nodo

                    DatabaseReference ref =FirebaseDatabase.getInstance().getReference();
                    DatabaseReference mensajeRef = ref.child("datadriver").child(PruUid).child("lastnameconductor");
                    mensajeRef.setValue(lastnameconductor);

                    DatabaseReference mensajeRef2 = ref.child("datadriver").child(PruUid).child("nameconductor");
                    mensajeRef2.setValue(nameconductor);


                    Intent intent = new Intent(PersonalDataActivity.this, NewCreateRouteActivity.class);
                    startActivity(intent);
                    finish();

                }else {
                    Toasty.Config.getInstance() //Configuracion del toasty

                            .setInfoColor(ContextCompat.getColor(getApplicationContext(),R.color.colorBottonFloat)) //Color de relleno
                            .setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.colorWhite))  //Color de letra
                            .apply();

                    Toasty.info(PersonalDataActivity.this,getText(R.string.validadorpersonal), Toast.LENGTH_SHORT, true).show();//info del toasty
                }
            }

        });


    }
}

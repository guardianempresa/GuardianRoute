package com.controlderuta.guardianroute;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.controlderuta.guardianroute.Model.AutoData;
import com.controlderuta.guardianroute.Model.DataMonitorConductor;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import es.dmoral.toasty.Toasty;

public class PhoneActivity extends AppCompatActivity {

    Button btnSigPhone;
    EditText edtPhoneNumber;
    String mobileconductor;
    String nameconductor ="";
    String lastnameconductor="";
    int autovalidador;


    //firebase var

    DatabaseReference databaseReference;
    String PruUid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone);

        btnSigPhone     =(Button)findViewById(R.id.btnSigPhone);
        edtPhoneNumber  =(EditText) findViewById(R.id.phoneNumber);


        databaseReference = FirebaseDatabase.getInstance().getReference();//obtiene el enlace de la db "ejemplos-android:"
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();//Para extraeerr el Uid del cliente
        PruUid=user.getUid(); //Guardamos Uid en variable



        btnSigPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mobileconductor = edtPhoneNumber.getText().toString();
                autovalidador   = edtPhoneNumber.getText().length();



                if (autovalidador==10){
                    //usamos clase DataMonitorConductor
                    DataMonitorConductor updatamonitorconductor = new DataMonitorConductor( nameconductor, lastnameconductor, mobileconductor);
                    //sube la longitud y tatitud a la raiz de la id
                    databaseReference.child("datadriver").child(PruUid).setValue(updatamonitorconductor);


                    Intent intent = new Intent(PhoneActivity.this, PersonalDataActivity.class);
                    startActivity(intent);
                    finish();

                }else if(autovalidador==0){
                    Toasty.Config.getInstance() //Configuracion del toasty

                            .setInfoColor(ContextCompat.getColor(getApplicationContext(),R.color.colorPrimaryDark)) //Color de relleno
                            .setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.colorWhite))  //Color de letra
                            .apply();

                    Toasty.info(PhoneActivity.this,getText(R.string.validadorphonezero), Toast.LENGTH_SHORT, true).show();//info del toasty

                }else {
                    Toasty.Config.getInstance() //Configuracion del toasty

                            .setInfoColor(ContextCompat.getColor(getApplicationContext(),R.color.colorPrimaryDark)) //Color de relleno
                            .setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.colorWhite))  //Color de letra
                            .apply();

                    Toasty.info(PhoneActivity.this,getText(R.string.validadorphone), Toast.LENGTH_SHORT, true).show();//info del toasty
                }


            }
        });
    }
}

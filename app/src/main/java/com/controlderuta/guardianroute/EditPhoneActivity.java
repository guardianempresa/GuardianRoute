package com.controlderuta.guardianroute;

import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.controlderuta.guardianroute.Model.DataMonitorConductor;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import es.dmoral.toasty.Toasty;

public class EditPhoneActivity extends AppCompatActivity {

    String Code;
    String Phone;

    EditText phoneNumberEdit;
    Button btnCancel;
    Button btnOk;
    String mobileconductor;

    int autovalidador;


    //firebase var

    DatabaseReference databaseReference;
    String PruUid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_phone);

        Code=getIntent().getExtras().getString("parametro");
        Phone=getIntent().getExtras().getString("phone");

        phoneNumberEdit = (EditText)findViewById(R.id.phoneNumberEdit);
        btnCancel       = (Button)findViewById(R.id.btnCancel);
        btnOk           = (Button)findViewById(R.id.btnOk);

        phoneNumberEdit.setText(Phone);

        databaseReference = FirebaseDatabase.getInstance().getReference();//obtiene el enlace de la db "ejemplos-android:"
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();//Para extraeerr el Uid del cliente
        PruUid=user.getUid(); //Guardamos Uid en variable


        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(EditPhoneActivity.this, PerfilEditActivity.class);
                intent.putExtra("parametro", Code);
                intent.putExtra("phone", Phone);
                startActivity(intent);
                finish();

            }
        });

        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mobileconductor = "tel:"+phoneNumberEdit.getText().toString();
                autovalidador   = phoneNumberEdit.getText().length();



                if (autovalidador==10){


                    //sube la longitud y tatitud a la raiz de la id
                    databaseReference.child("datadriver").child(PruUid).child("mobileconductor").setValue(mobileconductor);


                    Intent intent = new Intent(EditPhoneActivity.this, PerfilEditActivity.class);
                    intent.putExtra("parametro", Code);
                    startActivity(intent);
                    finish();

                }else if(autovalidador==0){
                    Toasty.Config.getInstance() //Configuracion del toasty

                            .setInfoColor(ContextCompat.getColor(getApplicationContext(),R.color.colorPrimaryDark)) //Color de relleno
                            .setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.colorWhite))  //Color de letra
                            .apply();

                    Toasty.info(EditPhoneActivity.this,getText(R.string.validadorphonezero), Toast.LENGTH_SHORT, true).show();//info del toasty

                }else {
                    Toasty.Config.getInstance() //Configuracion del toasty

                            .setInfoColor(ContextCompat.getColor(getApplicationContext(),R.color.colorPrimaryDark)) //Color de relleno
                            .setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.colorWhite))  //Color de letra
                            .apply();

                    Toasty.info(EditPhoneActivity.this,getText(R.string.validadorphone), Toast.LENGTH_SHORT, true).show();//info del toasty
                }

            }
        });



    }
}

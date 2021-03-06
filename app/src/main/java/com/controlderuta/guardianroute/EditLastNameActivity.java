package com.controlderuta.guardianroute;

import android.content.Intent;
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

public class EditLastNameActivity extends AppCompatActivity {

    String Code;
    String Lastname;

    EditText lastnamePersonEdit;
    Button btnCancelLastname;
    Button btnOkLastname;
    String txtLastname;


    //firebase var

    DatabaseReference databaseReference;
    String PruUid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_last_name);

        Code=getIntent().getExtras().getString("parametro");
        Lastname=getIntent().getExtras().getString("lastname");


        lastnamePersonEdit      = (EditText)findViewById(R.id.lastnamePersonEdit);
        btnCancelLastname       = (Button)findViewById(R.id.btnCancelLastname);
        btnOkLastname           = (Button)findViewById(R.id.btnOkLastname);

        lastnamePersonEdit.setText(Lastname);

        databaseReference = FirebaseDatabase.getInstance().getReference();//obtiene el enlace de la db "ejemplos-android:"
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();//Para extraeerr el Uid del cliente
        PruUid=user.getUid(); //Guardamos Uid en variable

        btnCancelLastname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Intent intent = new Intent(EditLastNameActivity.this, PerfilEditActivity.class);
                //intent.putExtra("parametro", Code);
                //intent.putExtra("phone", Lastname);
                //startActivity(intent);
                finish();

            }
        });

        btnOkLastname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                txtLastname = lastnamePersonEdit.getText().toString();

                if (txtLastname.equals("")){


                    Toasty.Config.getInstance() //Configuracion del toasty

                            .setInfoColor(ContextCompat.getColor(getApplicationContext(),R.color.colorPrimaryDark)) //Color de relleno
                            .setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.colorWhite))  //Color de letra
                            .apply();

                    Toasty.info(EditLastNameActivity.this,getText(R.string.validadorpersonalnamevacio), Toast.LENGTH_SHORT, true).show();//info del toasty

                }else {

                    //sube la longitud y tatitud a la raiz de la id
                    databaseReference.child("datadriver").child(PruUid).child("lastnameconductor").setValue(txtLastname);

                    //Intent intent = new Intent(EditLastNameActivity.this, PerfilEditActivity.class);
                    //intent.putExtra("parametro", Code);
                    //startActivity(intent);
                    finish();
                }
            }

        });

    }
}

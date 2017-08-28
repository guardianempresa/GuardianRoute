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

public class EditNameActivity extends AppCompatActivity {

    String Code;
    String Name;

    EditText nameEditPerson;
    Button btnCancelName;
    Button btnOkName;
    String txtName;




    //firebase var

    DatabaseReference databaseReference;
    String PruUid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_name);

        Code=getIntent().getExtras().getString("parametro");
        Name=getIntent().getExtras().getString("name");

        nameEditPerson      = (EditText)findViewById(R.id.nameEditPerson);
        btnCancelName       = (Button)findViewById(R.id.btnCancelName);
        btnOkName           = (Button)findViewById(R.id.btnOkName);

        nameEditPerson.setText(Name);

        databaseReference = FirebaseDatabase.getInstance().getReference();//obtiene el enlace de la db "ejemplos-android:"
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();//Para extraeerr el Uid del cliente
        PruUid=user.getUid(); //Guardamos Uid en variable

        btnCancelName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(EditNameActivity.this, PerfilEditActivity.class);
                intent.putExtra("parametro", Code);
                intent.putExtra("phone", Name);
                startActivity(intent);
                finish();

            }
        });

        btnOkName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                txtName = nameEditPerson.getText().toString();



                if (txtName.equals("")){


                    Toasty.Config.getInstance() //Configuracion del toasty

                            .setInfoColor(ContextCompat.getColor(getApplicationContext(),R.color.colorPrimaryDark)) //Color de relleno
                            .setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.colorWhite))  //Color de letra
                            .apply();

                    Toasty.info(EditNameActivity.this,getText(R.string.validadorpersonalnamevacio), Toast.LENGTH_SHORT, true).show();//info del toasty



                }else {

                    //sube la longitud y tatitud a la raiz de la id
                    databaseReference.child("datadriver").child(PruUid).child("nameconductor").setValue(txtName);

                    Intent intent = new Intent(EditNameActivity.this, PerfilEditActivity.class);
                    intent.putExtra("parametro", Code);
                    startActivity(intent);
                    finish();

                }

            }

        });

    }
}

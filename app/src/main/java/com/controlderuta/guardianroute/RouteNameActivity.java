package com.controlderuta.guardianroute;

import android.content.Intent;
import android.provider.ContactsContract;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v4.media.VolumeProviderCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.controlderuta.guardianroute.Model.DataMonitorConductor;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import es.dmoral.toasty.Toasty;

public class RouteNameActivity extends AppCompatActivity {

    private DatabaseReference databaseReference;

    FloatingActionButton btnSigRouteName;
    EditText edtRouteName;
    String txtRouteName;

    int a=0;
    int autovalidador;

    String Code;
    String PruUid;

    String phoneDriver;
    String nameDriver;
    String lastnameDriver;
    String conectorname;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route_name);


        Code=getIntent().getExtras().getString("parametro");

        databaseReference =FirebaseDatabase.getInstance().getReference();  //raiz
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();//Para extraeerr el Uid del cliente
        PruUid=user.getUid(); //Guardamos Uid en variable

       // dataphoneRuta();


        btnSigRouteName =(FloatingActionButton) findViewById(R.id.btnSigRouteName);
        edtRouteName=(EditText)findViewById(R.id.routeName);

        btnSigRouteName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                txtRouteName = edtRouteName.getText().toString();

                if (txtRouteName.equals("")){
                    a=0;
                    Toasty.Config.getInstance() //Configuracion del toasty

                            .setInfoColor(ContextCompat.getColor(getApplicationContext(),R.color.colorPrimaryDark)) //Color de relleno
                            .setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.colorWhite))  //Color de letra
                            .apply();

                    Toasty.info(RouteNameActivity.this,getText(R.string.validadorroutename), Toast.LENGTH_SHORT, true).show();//info del toasty

                }else {
                    a=1;
                }

                if (a==1){

                    //como la base de datos ya aesta creada en el activity anterior solo refresacamos los datos de nombre y apellidosllegando al nodo

                    //DataMonitorConductor updatamonitorconductor = new DataMonitorConductor( nameDriver, lastnameDriver, phoneDriver);
                    //databaseReference.child("phoneroute").child(Code).setValue(updatamonitorconductor);


                    DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
                    DatabaseReference mensajeRef = ref.child("drivervstravel").child(PruUid).child(Code).child("name");
                    mensajeRef.setValue(txtRouteName);

                    DatabaseReference mensajeRef2 = ref.child("login").child(PruUid).child("login");
                    mensajeRef2.setValue("1");

                    DatabaseReference ref3 = FirebaseDatabase.getInstance().getReference();
                    DatabaseReference mensajeRef3 = ref3.child("travel").child(Code).child("nombre");
                    mensajeRef3.setValue(txtRouteName);

                    Intent intent = new Intent(RouteNameActivity.this, CodeActivity.class);
                    intent.putExtra("parametro", Code);
                    startActivity(intent);
                    finish();

                }else {

                }
            }

        });


    }


    public void dataphoneRuta(){

        databaseReference = FirebaseDatabase.getInstance().getReference(); ///Raiz
        databaseReference.child("datadriver").child(PruUid).addValueEventListener(new ValueEventListener() {


            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                DataMonitorConductor dataMonitorConductor = dataSnapshot.getValue(DataMonitorConductor.class);

                phoneDriver     = dataMonitorConductor.getMobileconductor();
                nameDriver      = dataMonitorConductor.getNameconductor();
                lastnameDriver  = dataMonitorConductor.getLastnameconductor();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }



}


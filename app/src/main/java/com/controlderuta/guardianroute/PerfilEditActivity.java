package com.controlderuta.guardianroute;

import android.content.Intent;
import android.location.Location;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.controlderuta.guardianroute.Model.CheckList;
import com.controlderuta.guardianroute.Model.DataListRoute;
import com.controlderuta.guardianroute.Model.EditPerfil;
import com.controlderuta.guardianroute.Model.Markets;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class PerfilEditActivity extends AppCompatActivity {

    Button btnBackPerfil;

    TextView txtnameDriver;
    TextView txtlastnameDriver;
    TextView txtphone;

    Button btneditName;
    Button btneditlastName;
    Button btneditPhone;



    private DatabaseReference databaseReference;

    String Code;
    String PruUid;
    String Name;
    String Lastname;
    String Phone;
    String Phonerecord;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil_edit);

        Code=getIntent().getExtras().getString("parametro");

        showToolbar("", false);//llamamos la toolbar


        txtnameDriver       = (TextView)findViewById(R.id.nameDriver);
        txtlastnameDriver   = (TextView)findViewById(R.id.lastnameDriver);
        txtphone            = (TextView)findViewById(R.id.editPhone);


        //imgPerfil           = (ImageView)findViewById(R.id.imagenperfiledit);


        databaseReference = FirebaseDatabase.getInstance().getReference(); ///Raiz
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();//Para extraeerr el Uid del cliente
        PruUid=user.getUid();

        DatabaseReference ref =FirebaseDatabase.getInstance().getReference();
        DatabaseReference mensajeRef = ref.child("datadriver").child(PruUid).child("nameconductor");

        mensajeRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Name =dataSnapshot.getValue(String.class);
                txtnameDriver.setText(Name);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        DatabaseReference mensajeRef2 = ref.child("datadriver").child(PruUid).child("lastnameconductor");

        mensajeRef2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Lastname =dataSnapshot.getValue(String.class);
                txtlastnameDriver.setText(Lastname);


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        DatabaseReference mensajeRef3 = ref.child("datadriver").child(PruUid).child("mobileconductor");

        mensajeRef3.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Phone =dataSnapshot.getValue(String.class);
                Phonerecord = Phone.substring(4);
                txtphone.setText(Phonerecord);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



        btnBackPerfil       = (Button)findViewById(R.id.backEditPerfil);
        btneditName         = (Button) findViewById(R.id.fabEditName);
        btneditlastName     = (Button) findViewById(R.id.fabEditLastName);
        btneditPhone        = (Button)findViewById(R.id.fabEditPhone);

        btneditName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(PerfilEditActivity.this, EditNameActivity.class);
                intent.putExtra("parametro", Code);
                intent.putExtra("name", Name);
                startActivity(intent);
                //finish();

            }
        });

        btneditlastName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(PerfilEditActivity.this, EditLastNameActivity.class);
                intent.putExtra("parametro", Code);
                intent.putExtra("lastname", Lastname);
                startActivity(intent);
                //finish();

            }
        });

        btneditPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(PerfilEditActivity.this, EditPhoneActivity.class);
                intent.putExtra("parametro", Code);
                intent.putExtra("phone", Phonerecord);
                startActivity(intent);
                //finish();

            }
        });



        btnBackPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Intent intent = new Intent(PerfilEditActivity.this,NewMapActivity.class);
                //intent.putExtra("parametro", Code);
                //startActivity(intent);
                finish();
            }
        });

    }


            public void showToolbar (String tittle, boolean upButton){//Metoodo de la toolbar

        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbaredit);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(tittle);
        getSupportActionBar().setDisplayHomeAsUpEnabled(upButton);//habilitamos la visibilidad de botton de up
    }
}

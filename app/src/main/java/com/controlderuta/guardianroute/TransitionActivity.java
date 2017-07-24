package com.controlderuta.guardianroute;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class TransitionActivity extends AppCompatActivity {

    String PruUid;
    String MasPru;
    String value;
    String nexo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transition);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();//Para extraeerr el Uid del cliente
        PruUid = user.getUid(); //Guardamos Uid en variable

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();//Raiz
        DatabaseReference mensajeRef = ref.child("rutas").child(PruUid).child("code");//Nodo


        mensajeRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                value = dataSnapshot.getValue(String.class);
                nexo=value+"2";//esto toco hacerlo para que reconociera el if

                if (nexo.equals("null2")) {

                    Intent intent = new Intent(TransitionActivity.this, PhotoActivity.class);
                    startActivity(intent);
                    finish();


                }else{

                    Intent intent = new Intent(TransitionActivity.this, MapsActivity.class);
                    startActivity(intent);
                    finish();

                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {


            }
        });

    }}
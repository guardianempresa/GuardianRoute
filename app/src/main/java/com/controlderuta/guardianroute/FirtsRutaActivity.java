package com.controlderuta.guardianroute;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Random;

public class FirtsRutaActivity extends AppCompatActivity {

    String codeRuta;
    String cadenacod1;
    String cadenacod2;
    int random1;
    int random2;
    TextView codigoruta;
    Button btnsiguiente;
    String datRaiz;


    private DatabaseReference databaseReference;//referencia de firebase
    String PruUid;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_firts_ruta);


        codigoruta=(TextView)findViewById(R.id.codigoruta);
        btnsiguiente=(Button)findViewById(R.id.btnsigprimero);

        random1=(int)(Math.random()*100);
        cadenacod1 = String.valueOf(random1);
        random1=(int)(Math.random()*100);
        cadenacod1 = String.valueOf(random1);

        codeRuta = "GP"+random1+"AY"+random2;

        codigoruta.setText(codeRuta);


        btnsiguiente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DatabaseReference ref =FirebaseDatabase.getInstance().getReference();

                datRaiz=ref.getKey();

                DatabaseReference mensajeRef = ref.child("recorrido").child(codeRuta).child("arranque");
                mensajeRef.setValue(0);
                DatabaseReference mensajeRef2 = ref.child("recorrido").child(codeRuta).child("id");
                mensajeRef2.setValue(datRaiz);

                Intent intent = new Intent(FirtsRutaActivity.this, MapsActivity.class);
                startActivity(intent);
                finish();


            }
        });




    }






}

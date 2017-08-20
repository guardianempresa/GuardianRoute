package com.controlderuta.guardianroute;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.controlderuta.guardianroute.Model.Recorridos;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Random;

public class NewCreateRouteActivity extends AppCompatActivity {

    Button btnCreate;
    Button btnSingRoute;


    String letra1,letra2,letra3,letra4,letra5,letra6,letra;
    int valor1,valor2,valor3,valor4,valor5,valor6;
    char a,b,c,d;

    private double  latitud=0;
    private double  longitud=0;
    private double  latitudllegada=0;
    private double  longitudllegada=0;
    private int estado=0;
    private int alerta=0;
    private String nameRoute="";
    private int alertDist=0;
    private float acumDist=0;
    private int time=0;
    private int tipRoute=0;

    private String link;
    String PruUid;

    //variables firebase

    DatabaseReference databaseReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_create_route);


        btnCreate = (Button)findViewById(R.id.btnCreateNew);
        btnSingRoute = (Button)findViewById(R.id.btnAddCode);

        //Random codigo aleatorio

        Random r1=new Random();
        valor1 =r1.nextInt(122 - 97)+97;
        a = (char)valor1;
        letra1=String.valueOf(a);

        Random r2=new Random();
        valor2 =r2.nextInt(122 - 97)+97;
        b = (char)valor2;
        letra2=String.valueOf(b);

        Random r3=new Random();
        valor3 =r3.nextInt(122 - 97)+97;
        c = (char)valor3;
        letra3=String.valueOf(c);

        Random r4=new Random();
        valor4 =r4.nextInt(122 - 97)+97;
        d = (char)valor4;
        letra4=String.valueOf(d);

        Random r5=new Random();
        valor5 =r5.nextInt(99 - 1)+1;
        letra5=String.valueOf(valor5);

        Random r6=new Random();
        valor6 =r6.nextInt(99 - 1)+1;
        letra6=String.valueOf(valor6);

        letra=(letra1+letra2+letra3+letra4+letra5+letra6).toUpperCase();


        //---------





        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                databaseReference = FirebaseDatabase.getInstance().getReference();//obtiene el enlace de la db "ejemplos-android:"
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();//Para extraeerr el Uid del cliente
                PruUid=user.getUid(); //Guardamos Uid en variable|

                databaseReference.child("drivervstravel").child(PruUid).setValue(letra);

                Recorridos recorridos = new Recorridos(latitud,longitud,latitudllegada,longitudllegada,estado,alerta,nameRoute,alertDist,acumDist,time,tipRoute);
                databaseReference.child("travel").child(letra).setValue(recorridos);



                Intent intent = new Intent(NewCreateRouteActivity.this, CodeActivity.class);
                intent.putExtra("parametro", letra);
                startActivity(intent);
                finish();

            }
        });

        btnSingRoute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(NewCreateRouteActivity.this, CodeActivity.class);
                startActivity(intent);
                finish();

            }
        });



    }
}

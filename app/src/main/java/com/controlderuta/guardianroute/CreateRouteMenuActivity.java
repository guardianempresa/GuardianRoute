package com.controlderuta.guardianroute;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.controlderuta.guardianroute.Model.DriverVsTravel;
import com.controlderuta.guardianroute.Model.Recorridos;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Random;

public class CreateRouteMenuActivity extends AppCompatActivity {


    Button btnCreate;
    Button btnSingRoute;
    Button btnMenu;



    String letra1,letra2,letra3,letra4,letra5,letra6,letra;
    int valor1,valor2,valor3,valor4,valor5,valor6;
    char a,b,c,d;

    private double  latitud=0.0;
    private double  longitud=0.0;
    private double  latitudllegada=0.0;
    private double  longitudllegada=0.0;
    private int estado=0;
    private int alerta=0;
    private String nameRoute="DEMO";
    private int alertDist=0;
    private float acumDist=0.0f;
    private int time=0;
    private int tipRoute=0;

    private String link;
    String PruUid;
    String Code;
    //variables firebase

    private String id;
    private String name="";

    DatabaseReference databaseReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_route_menu);

        showToolbar("", false);//llamamos la toolbar

        Code=getIntent().getExtras().getString("parametro");

        btnCreate = (Button)findViewById(R.id.btnCreateNewRoute);
        btnMenu=(Button)findViewById(R.id.backcreatemenu);

        btnMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent intent = new Intent(CreateRouteMenuActivity.this, NewMapActivity.class);
                //intent.putExtra("parametro", Code);
                //startActivity(intent);
                finish();
            }
        });


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


                DriverVsTravel driverVsTravel = new DriverVsTravel(letra,name);
                databaseReference.child("drivervstravel").child(PruUid).child(letra).setValue(driverVsTravel);


                Recorridos recorridos = new Recorridos(latitud,longitud,latitudllegada,longitudllegada,estado,alerta,nameRoute,alertDist,acumDist,time,tipRoute);
                databaseReference.child("travel").child(letra).setValue(recorridos);

                databaseReference.child("starandfinish").child(letra).child("estado").setValue(0);
                databaseReference.child("starandfinish").child(letra).child("tipo").setValue(0);


                Intent intent = new Intent(CreateRouteMenuActivity.this, RouteNameActivity.class);
                intent.putExtra("parametro", letra);
                startActivity(intent);
                finish();

            }
        });


    }

    public void showToolbar (String tittle, boolean upButton){//Metoodo de la toolbar

        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbarWhiteCreate);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(tittle);
        getSupportActionBar().setDisplayHomeAsUpEnabled(upButton);//habilitamos la visibilidad de botton de up
    }
}

package com.controlderuta.guardianroute;

import android.*;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.controlderuta.guardianroute.Model.UserList;

public class CallActivity extends AppCompatActivity {

    String Iduser;
    String Code;
    String Call;
    String Name;
    String Face;

    FloatingActionButton btnLlamada; //Declarar el boton de llamada
    TextView userName;
    ImageView faceimg;
    Button btncall;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_call);

        showToolbar("", false);//llamamos la toolbar

        btncall=(Button)findViewById(R.id.backcall);
        btncall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CallActivity.this, UserListCallActivity.class);
                intent.putExtra("parametro", Code);
                startActivity(intent);
                finish();
            }
        });

        Iduser=getIntent().getExtras().getString("id");
        Code=getIntent().getExtras().getString("parametro");
        Call=getIntent().getExtras().getString("llamada");
        Name=getIntent().getExtras().getString("name");
        Face=getIntent().getExtras().getString("face");


        userName=(TextView)findViewById(R.id.nameUser);
        userName.setText(Name);

        faceimg=(ImageView)findViewById(R.id.imagenperfil);


        if (Face.equals("R.drawable.avatarcir1")){

            faceimg.setImageResource(R.drawable.avatar1);

        }else if(Face.equals("R.drawable.avatarcir2")) {
            faceimg.setImageResource(R.drawable.avatar2);

        }else if(Face.equals("R.drawable.avatarcir3")) {
            faceimg.setImageResource(R.drawable.avatar3);

        }else if(Face.equals("R.drawable.avatarcir4")) {
        faceimg.setImageResource(R.drawable.avatar4);

        }else if(Face.equals("R.drawable.avatarcir5")) {
            faceimg.setImageResource(R.drawable.avatar5);

        }else if(Face.equals("R.drawable.avatarcir6")) {
            faceimg.setImageResource(R.drawable.avatar6);

        }else if(Face.equals("R.drawable.avatarcir7")) {
            faceimg.setImageResource(R.drawable.avatar7);

        }else if(Face.equals("R.drawable.avatarcir8")) {
            faceimg.setImageResource(R.drawable.avatar8);

        }else{

        }



        btnLlamada =(FloatingActionButton)findViewById(R.id.fabPhoneCall);
        btnLlamada.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {




                //Codigo ejecucion de llamada

                Intent i = new Intent(Intent.ACTION_CALL, Uri.parse(Call)); //Aui toca definir variable para cambiar numero
                if(ActivityCompat.checkSelfPermission(CallActivity.this, android.Manifest.permission.CALL_PHONE)!=
                        PackageManager.PERMISSION_GRANTED)
                    return;
                startActivity(i);
            }
        });

    }

    public void showToolbar (String tittle, boolean upButton){//Metoodo de la toolbar

        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbarcall);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(tittle);
        getSupportActionBar().setDisplayHomeAsUpEnabled(upButton);//habilitamos la visibilidad de botton de up
    }
}


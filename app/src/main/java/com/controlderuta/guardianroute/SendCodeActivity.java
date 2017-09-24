package com.controlderuta.guardianroute;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;

public class SendCodeActivity extends AppCompatActivity {

    String Code;
    private String link;

    //variables firebase

    DatabaseReference databaseReference;



    TextView textCode;
    FloatingActionButton btnCode;
    Button btnSend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_code);

        showToolbar("", false);//llamamos la toolbar

        Code=getIntent().getExtras().getString("parametro");

        btnSend=(Button)findViewById(R.id.backsend);

        textCode=(TextView)findViewById(R.id.textCode);
        textCode.setText(Code);


        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(SendCodeActivity.this, NewMapActivity.class);
                intent.putExtra("parametro", Code);
                startActivity(intent);
                finish();

            }
        });


        btnCode=(FloatingActionButton)findViewById(R.id.fabcontacSendRoute);



        btnCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                shareDeepLink(buildDeepLink(""));

            }
        });


    }

    private String buildDeepLink(String key){

        link="¡Unete a mi Ruta Guardian!. Descarga la aquí la app:https://drive.google.com/open?id=0B3lY_7IS7EICaFd4WGVaU1c0UWc"+" Usa el codigo de invitación "+Code;
        return link;
    }

    private void shareDeepLink(String deepLink){
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_SUBJECT,"Firebase DeepLink Share");
        intent.putExtra(Intent.EXTRA_TEXT, deepLink);
        startActivity(intent);
    }

    public void showToolbar (String tittle, boolean upButton){//Metoodo de la toolbar

        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbarSend);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(tittle);
        getSupportActionBar().setDisplayHomeAsUpEnabled(upButton);//habilitamos la visibilidad de botton de up
    }

}


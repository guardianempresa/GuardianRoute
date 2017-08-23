package com.controlderuta.guardianroute;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

public class ChatActivity extends AppCompatActivity {

    String Iduser;
    String Code;

    TextView idchat;
    TextView codigochat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        showToolbar("", true);//llamamos la toolbar


        Iduser=getIntent().getExtras().getString("id");
        Code=getIntent().getExtras().getString("parametro");

        idchat = (TextView)findViewById(R.id.codigochat);
        codigochat = (TextView)findViewById(R.id.idchat);

        idchat.setText(Iduser);
        codigochat.setText(Code);


    }

    public void showToolbar (String tittle, boolean upButton){//Metoodo de la toolbar

        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbarchat);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(tittle);
        getSupportActionBar().setDisplayHomeAsUpEnabled(upButton);//habilitamos la visibilidad de botton de up
    }
}

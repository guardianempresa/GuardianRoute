package com.controlderuta.guardianroute;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.controlderuta.guardianroute.Model.Recorridos;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Random;

public class CodeActivity extends AppCompatActivity {

    Button btnSigCode;
    FloatingActionButton btnContact;
    TextView txttextView;
    String Code;




    private String link;



    //variables firebase

    DatabaseReference databaseReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_code);

        txttextView =(TextView)findViewById(R.id.textView);
        btnContact =(FloatingActionButton)findViewById(R.id.fabcontacRoute);
        btnSigCode =(Button)findViewById(R.id.btnSigCode);


        Code=getIntent().getExtras().getString("parametro");

        txttextView.setText(Code);


        btnSigCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                databaseReference = FirebaseDatabase.getInstance().getReference();//obtiene el enlace de la db "ejemplos-android:"
                databaseReference.child("alert").child(Code).child("type").setValue("0");
                databaseReference.child("alert").child(Code).child("hour").setValue("0");


                Intent intent = new Intent(CodeActivity.this, PointEndActivity.class);
                intent.putExtra("parametro", Code);
                startActivity(intent);
                finish();



            }
        });


        btnContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                shareDeepLink(buildDeepLink(""));

            }
        });

    }

    private String buildDeepLink(String key){

        link="¡Unete a mi Ruta Guardian! Usa el codigo de invitación "+Code+".Descarga la aquí la app:https://drive.google.com/open?id=0B3lY_7IS7EICeExHZUJfVFQyRFU";
        return link;
    }

    private void shareDeepLink(String deepLink){
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_SUBJECT,"Firebase DeepLink Share");
        intent.putExtra(Intent.EXTRA_TEXT, deepLink);
        startActivity(intent);
    }

}

package com.controlderuta.guardianroute;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class AlertActivity extends AppCompatActivity implements View.OnClickListener {

    //Variable objetos, textview, buttom, etc

    Button btnMaps;
    //Button btnAlert;
    private String PruUid2; //Para traer token del otro activity

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alert);

       // PruUid2=getIntent().getExtras().getString("parametro2"); //recepcion del token del otro activity
        //Toast.makeText(getApplicationContext(),PruUid2,Toast.LENGTH_SHORT).show();
        btnMaps=(Button)findViewById(R.id.btnAlertRuta);
      //  btnAlert=(Button)findViewById(R.id.btnAlertAlertas);

        btnMaps.setOnClickListener(this);
        //btnAlert.setOnClickListener(this);



    }

    @Override
    public void onClick(View v) {

        Intent intent = new Intent(AlertActivity.this, MapsActivity.class);
        startActivity(intent);
        finish();
        }
}

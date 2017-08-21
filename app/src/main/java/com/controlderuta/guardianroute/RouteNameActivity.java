package com.controlderuta.guardianroute;

import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.controlderuta.guardianroute.Model.DataMonitorConductor;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Map;

import es.dmoral.toasty.Toasty;

public class RouteNameActivity extends AppCompatActivity {

    private DatabaseReference databaseReference;

    Button btnSigRouteName;
    EditText edtRouteName;
    String txtRouteName;
    String codintent;
    int a=0;
    int autovalidador;

    String PruUid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route_name);


        databaseReference =FirebaseDatabase.getInstance().getReference();  //raiz
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();//Para extraeerr el Uid del cliente
        PruUid=user.getUid(); //Guardamos Uid en variable

        codintent=getIntent().getExtras().getString("codigo");

        btnSigRouteName =(Button)findViewById(R.id.btnSigRouteName);
        edtRouteName=(EditText)findViewById(R.id.routeName);

        btnSigRouteName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                txtRouteName = edtRouteName.getText().toString();

                if (txtRouteName.equals("")){
                    a=0;
                    Toasty.Config.getInstance() //Configuracion del toasty

                            .setInfoColor(ContextCompat.getColor(getApplicationContext(),R.color.colorPrimaryDark)) //Color de relleno
                            .setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.colorWhite))  //Color de letra
                            .apply();

                    Toasty.info(RouteNameActivity.this,getText(R.string.validadorroutename), Toast.LENGTH_SHORT, true).show();//info del toasty

                }else {
                    a=1;
                }

                if (a==1){

                    //como la base de datos ya aesta creada en el activity anterior solo refresacamos los datos de nombre y apellidosllegando al nodo

                    DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
                    DatabaseReference mensajeRef = ref.child("drivervstravel").child(PruUid).child(codintent).child("name");
                    mensajeRef.setValue(txtRouteName);


                    Intent intent = new Intent(RouteNameActivity.this, RouteListActivity.class);
                    startActivity(intent);
                    finish();

                }else {

                }
            }

        });


    }
}

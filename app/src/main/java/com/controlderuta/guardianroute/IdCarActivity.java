package com.controlderuta.guardianroute;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;

public class IdCarActivity extends AppCompatActivity {


    Button btnSigIdCar;
    EditText edtMatricula;
    String matricula;
    int autovalidador;


    //firebase var

    DatabaseReference databaseReference;
    String PruUid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_id_car);
    }
}

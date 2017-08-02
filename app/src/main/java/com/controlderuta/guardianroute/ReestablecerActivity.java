package com.controlderuta.guardianroute;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ReestablecerActivity extends AppCompatActivity {


    private static final String TAG = "ReestablecerActivity";
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;



    private Button btnReestablecer;
    private EditText edtEmailRest;
    private String textemailres;
    int a=0;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reestablecer);

        showToolbar(getResources().getString(R.string.layoutReestablecer), true);//llamamos la toolbar

        edtEmailRest=(EditText)findViewById(R.id.resUsername);
        btnReestablecer =(Button)findViewById(R.id.btnreestablecer);

        btnReestablecer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                textemailres = edtEmailRest.getText().toString();


                FirebaseAuth auth = FirebaseAuth.getInstance();
                String emailAddress = textemailres;

                auth.sendPasswordResetEmail(emailAddress)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Log.d(TAG, "Email enviado");
                                }
                            }
                        });

            }
        });



    }


    public void showToolbar (String tittle, boolean upButton){//Metoodo de la toolbar

        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(tittle);
        getSupportActionBar().setDisplayHomeAsUpEnabled(upButton);//habilitamos la visibilidad de botton de up


    }




}

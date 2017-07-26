package com.controlderuta.guardianroute;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;


public class LoginActivity extends AppCompatActivity {

    private static final String TAG ="LoginActivity" ;
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;

    private Button btnLogin;
    private TextView btnCreate;

    private EditText edtEmail;
    private EditText edtPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btnLogin    = (Button)findViewById(R.id.btnIngresar);
        btnCreate   = (TextView)findViewById(R.id.textPulseAqui);

        edtEmail    = (EditText)findViewById(R.id.username);
        edtPassword = (EditText)findViewById(R.id.password);

        initialice();

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                signIn(edtEmail.getText().toString(),edtPassword.getText().toString());

            }
        });

        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent intent = new Intent(LoginActivity.this, CreateAccountActivity.class);
                startActivity(intent);
                finish();

            }
        });


            }


    private void initialice(){

        firebaseAuth= FirebaseAuth.getInstance();
        authStateListener =new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser firebaseUser =firebaseAuth.getCurrentUser();//detecta un cambio en la sission
                if (firebaseUser !=null){//si encuentra algo
                    Log.w(TAG,"onAuthStateChanged - signed_in"+ firebaseUser.getUid());//traer key
                    Log.w(TAG,"onAuthStateChanged - signed_in"+ firebaseUser.getEmail());//Traes correo
                }else{
                    Log.w(TAG,"onAuthStateChanged - signed_out");
                }

            }
        };

    }

    private void signIn(String email, String password){//Metodo de auteticacion

        firebaseAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful()){
                    Toast.makeText(LoginActivity.this, "Autenticado con exito",Toast.LENGTH_SHORT).show();//mnesaje
                    Intent intent = new Intent(LoginActivity.this, MapsActivity.class);
                    startActivity(intent);
                    finish();

                }else{
                    Toast.makeText(LoginActivity.this, "La cuenta no existe, crea una nueva",Toast.LENGTH_SHORT).show(); //Mensaje
                }

            }
        });

    }

    private void createAccount(String email, String password){ //Metodo de crear cuenta

        firebaseAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful()){
                    Toast.makeText(LoginActivity.this, "Cuenta creada con exito",Toast.LENGTH_SHORT).show();//mnesaje
                }else{
                    Toast.makeText(LoginActivity.this, "Cuenta no fue creada",Toast.LENGTH_SHORT).show(); //Mensaje
                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        firebaseAuth.addAuthStateListener(authStateListener);

    }

    @Override
    protected void onStop() {
        super.onStop();
        firebaseAuth.removeAuthStateListener(authStateListener);

    }
}


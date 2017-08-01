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
    private TextView btnSolicitarPassword;

    private EditText edtEmail;
    private EditText edtPassword;
    private int a=0, b=0, validalog;
    private String email,password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btnLogin             = (Button)findViewById(R.id.btnIngresar);
        btnCreate            = (TextView)findViewById(R.id.textPulseAqui);
        btnSolicitarPassword = (TextView)findViewById(R.id.textVeaAqui);

        edtEmail    = (EditText)findViewById(R.id.username);
        edtPassword = (EditText)findViewById(R.id.password);

        initialice();

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                email = edtEmail.getText().toString();
                password = edtPassword.getText().toString();


                if (email.equals("")) {//valida campos email no vacios

                    Toast.makeText(LoginActivity.this, "El campo email no puede ser vacio",
                            Toast.LENGTH_SHORT).show();
                    a=1;
                }else   {
                    a=0;
                }

                if (password.equals("")) {//valida campos password no vacios

                    Toast.makeText(LoginActivity.this, "Falta campo password, el cual debe ser mayor a 6 caracteres",
                            Toast.LENGTH_SHORT).show();
                    b=1;
                }else   {
                    b=0;
                }

                validalog=a+b;


                if (validalog==0){ //Valida que email y password no tenga campos vacios

                    signIn(edtEmail.getText().toString(),edtPassword.getText().toString());

                }else {
                    Toast.makeText(LoginActivity.this, "Debe registrar la totalidad de los campos para continuar registro",
                            Toast.LENGTH_SHORT).show();
                }

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


        btnSolicitarPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(LoginActivity.this, ReestablecerActivity.class);
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
                    Toast.makeText(LoginActivity.this, "El usuario y la contraseña no  cohiciden, puede ser que el usuario no exista o la contraseña sea incorrecta, si estas seguro que el usurio existe pide reestablecer la contraseña, recibiras un correo confirmando la contraseña.",Toast.LENGTH_LONG).show(); //Mensaje
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


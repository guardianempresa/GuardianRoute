package com.controlderuta.guardianroute;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.data.DataBuffer;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;

public class CreateAccountActivity extends AppCompatActivity {

    private static final String TAG ="LoginActivity" ;
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;


    private TextView btnCreate;
    private Button btnLoginCreate;

    private EditText edtEmailCreate;
    private EditText edtPasswordCreate;

    private String textemail;
    private String textpassword;
    int a=0, b=0, valida;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);



        btnCreate   = (TextView)findViewById(R.id.btnregistrar);

        edtEmailCreate    = (EditText)findViewById(R.id.crearUsername);
        edtPasswordCreate = (EditText)findViewById(R.id.crearPassword);

        initialice();



        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                textemail = edtEmailCreate.getText().toString();
                textpassword = edtPasswordCreate.getText().toString();


                if (textemail.equals("")) {//valida campos email no vacios

                    Toast.makeText(CreateAccountActivity.this, "El campo email no puede estar vacio",
                            Toast.LENGTH_SHORT).show();
                    a=1;
                }else   {
                    a=0;
                }

                if (textpassword.equals("")) {//valida campos password no vacios

                    Toast.makeText(CreateAccountActivity.this, "Falta campo password debe ser mayo a 6 caracteres",
                            Toast.LENGTH_SHORT).show();
                    b=1;
                }else   {
                    b=0;
                }

                valida=a+b;


                if (valida==0){ //Valida que email y password no tenga campos vacios

                    createAccount(edtEmailCreate.getText().toString(),edtPasswordCreate.getText().toString());
                    Intent intent = new Intent(CreateAccountActivity.this, PreDataActivity.class);
                    startActivity(intent);
                    finish();


                }else {
                    Toast.makeText(CreateAccountActivity.this, "Debe registrar la totalidad de los campos para continuar registro",
                            Toast.LENGTH_SHORT).show();
                }

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
                    Toast.makeText(CreateAccountActivity.this, "Autenticado con exito",Toast.LENGTH_SHORT).show();//mnesaje
                    Intent intent = new Intent(CreateAccountActivity.this, MapsActivity.class);
                    startActivity(intent);
                    finish();

                }else{
                    Toast.makeText(CreateAccountActivity.this, "La cuenta no existe, crea una nueva",Toast.LENGTH_SHORT).show(); //Mensaje
                }

            }
        });

    }

    private void createAccount(String email, String password){ //Metodo de crear cuenta

        firebaseAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful()){
                    Toast.makeText(CreateAccountActivity.this, "Cuenta creada con exito",Toast.LENGTH_SHORT).show();//mnesaje
                }else{
                    Toast.makeText(CreateAccountActivity.this, "Cuenta no fue creada",Toast.LENGTH_SHORT).show(); //Mensaje
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


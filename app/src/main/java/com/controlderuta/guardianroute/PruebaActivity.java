package com.controlderuta.guardianroute;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class PruebaActivity extends AppCompatActivity {

    DatabaseReference databaseReference;

    String codelist;
    String namelist;
    TextView Prueba;
    TextView Titulo;
    String PruUid;//key
    String Value;//Variable de consulta

    Button btnTituloToolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prueba);

        codelist=getIntent().getExtras().getString("codigo");
        namelist=getIntent().getExtras().getString("name");
        Prueba=(TextView)findViewById(R.id.prueba);
        btnTituloToolbar = (Button)findViewById(R.id.btntoolbar);




        databaseReference =FirebaseDatabase.getInstance().getReference();  //raiz
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();//Para extraeerr el Uid del cliente
        PruUid=user.getUid(); //Guardamos Uid en variable


        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
        DatabaseReference mensajeRef = ref.child("drivervstravel").child(PruUid).child(codelist).child("name");

        mensajeRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Value =dataSnapshot.getValue(String.class);
                Toast.makeText(PruebaActivity.this,"Ruta: "+ Value,Toast.LENGTH_SHORT).show();
                btnTituloToolbar.setText(Value);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        Prueba.setText(codelist);

    }



    public boolean onCreateOptionsMenu(Menu menu){

        getMenuInflater().inflate(R.menu.menu_option,menu);

        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item){

        switch (item.getItemId()){

            case R.id.mSignOut:

                break;

            case R.id.mAddRoute:
                    Toast.makeText(this,"Esto es prueba", Toast.LENGTH_SHORT).show();
                break;

        }
        return super.onOptionsItemSelected(item);
    }
}

package com.controlderuta.guardianroute;

import android.content.Intent;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.controlderuta.guardianroute.Model.Artist;
import com.controlderuta.guardianroute.Model.CheckList;
import com.controlderuta.guardianroute.Model.DataListRoute;
import com.controlderuta.guardianroute.Model.DataUsuarios;
import com.controlderuta.guardianroute.Model.UserList;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import es.dmoral.toasty.Toasty;

public class AbordajeActivity extends AppCompatActivity {


    private static final String TAG = "AbordajeActivity";
    private DatabaseReference databaseReference;

    private ListView lstArtist;
    private ArrayAdapter arrayAdapter;
    private List<String> artistNames;
    private List<CheckList> prueba;
    String codemaster;
    String comodin;

    String PruUid;
    String Code;
    String keyfecha;
    Button btnback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_abordaje);

        Code=getIntent().getExtras().getString("parametro");

        btnback=(Button)findViewById(R.id.backabordaje);
        btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(AbordajeActivity.this, NewMapActivity.class);
                intent.putExtra("parametro", Code);
                startActivity(intent);
                finish();
            }
        });

        showToolbar("", false);//llamamos la toolbar

        lstArtist = (ListView)findViewById(R.id.lstArtist);
        artistNames = new ArrayList<>();
        prueba=new ArrayList<>();
        arrayAdapter = new ArrayAdapter(this,R.layout.filas_lista,R.id.nombre_fila_lista,artistNames);
        lstArtist.setAdapter(arrayAdapter);



        //FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        databaseReference = FirebaseDatabase.getInstance().getReference(); ///Raiz
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();//Para extraeerr el Uid del cliente
        PruUid=user.getUid(); //Guardamos Uid en variable


        databaseReference.child("check").child(Code).addValueEventListener(new ValueEventListener() {

            @Override

            public void onDataChange(DataSnapshot dataSnapshot) {

                artistNames.clear();
                prueba.clear();

                if (dataSnapshot.exists()){
                    for (DataSnapshot snapshot:dataSnapshot.getChildren()) {
                        CheckList checkList = snapshot.getValue(CheckList.class);
                        Log.w(TAG,checkList.getName());
                        Log.w(TAG,checkList.getLastname());
                        artistNames.add((checkList.getName()+" "+checkList.getLastname()).toString());

                        prueba.add(checkList);

                        //Sube datos distancia

                        SimpleDateFormat s = new SimpleDateFormat("ddMMyyyyhhmmss");
                        keyfecha = s.format(new Date());

                    }
                }
                arrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



        lstArtist.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                String idPrueba = prueba.get(position).getId();
                databaseReference.child("usersvstravel").child(Code).child(idPrueba).child("check").setValue("s");
                databaseReference.child("checkconfirm").child(Code).child(idPrueba).child("date").setValue(keyfecha);

                prueba.remove(position);
                artistNames.remove(position);
                databaseReference.child("check").child(Code).child(idPrueba).removeValue();


                return true;
            }
        });
    }

    public void showToolbar (String tittle, boolean upButton){//Metoodo de la toolbar

        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbarselectlist);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(tittle);
        getSupportActionBar().setDisplayHomeAsUpEnabled(upButton);//habilitamos la visibilidad de botton de up
    }
}

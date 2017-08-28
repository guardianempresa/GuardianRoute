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

import com.controlderuta.guardianroute.Model.Artist;
import com.controlderuta.guardianroute.Model.DataListRoute;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class RemoveRouteActivity extends AppCompatActivity {

    private static final String TAG = "RemoveRouteActivity";
    private DatabaseReference databaseReference;

    private ListView lstArtist;
    private ArrayAdapter arrayAdapter;
    private List<String> artistNames;
    private List<DataListRoute> prueba;
    Button btnRemove;



    String PruUid;
    String Code;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remove_route);

        showToolbar("", false);//llamamos la toolbar

        Code=getIntent().getExtras().getString("parametro");

        btnRemove=(Button)findViewById(R.id.backremove);

        btnRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RemoveRouteActivity.this, NewMapActivity.class);
                intent.putExtra("parametro", Code);
                startActivity(intent);
                finish();
            }
        });

        lstArtist = (ListView)findViewById(R.id.lstRemove);
        artistNames = new ArrayList<>();
        prueba=new ArrayList<>();
        arrayAdapter = new ArrayAdapter(this,R.layout.fila_lista,R.id.nombre_fila_lista,artistNames);
        lstArtist.setAdapter(arrayAdapter);



        //FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        databaseReference = FirebaseDatabase.getInstance().getReference(); ///Raiz
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();//Para extraeerr el Uid del cliente
        PruUid=user.getUid(); //Guardamos Uid en variable


        databaseReference.child("drivervstravel").child(PruUid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                artistNames.clear();
                prueba.clear();

                if (dataSnapshot.exists()){
                    for (DataSnapshot snapshot:dataSnapshot.getChildren()) {
                        DataListRoute datalist = snapshot.getValue(DataListRoute.class);
                        Log.w(TAG,datalist.getName());
                        artistNames.add(datalist.getName());
                        prueba.add(datalist);

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
                prueba.remove(position);
                artistNames.remove(position);
                databaseReference.child("drivervstravel").child(PruUid).child(idPrueba).removeValue();
                databaseReference.child("alert").child(idPrueba).removeValue();
                databaseReference.child("chat").child(idPrueba).removeValue();
                databaseReference.child("travel").child(idPrueba).removeValue();
                databaseReference.child("datauser").child(idPrueba).removeValue();

                return true;
            }
        });

    }

    public void showToolbar (String tittle, boolean upButton){//Metoodo de la toolbar

        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbarremove);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(tittle);
        getSupportActionBar().setDisplayHomeAsUpEnabled(upButton);//habilitamos la visibilidad de botton de up
    }

}
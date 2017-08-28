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

import java.util.ArrayList;
import java.util.List;

import es.dmoral.toasty.Toasty;


public class UsersListActivity extends AppCompatActivity {

    private static final String TAG = "UsersListActivity";
    private DatabaseReference databaseReference;

    private ListView lstArtist;
    private ArrayAdapter arrayAdapter;
    private List<String> artistNames;
    private List<DataListRoute> prueba;
    String codemaster;
    String comodin;

    String PruUid;
    String Code;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users_list);



        Code=getIntent().getExtras().getString("parametro");

        showToolbar("", true);//llamamos la toolbar

        lstArtist = (ListView)findViewById(R.id.lstArtist);
        artistNames = new ArrayList<>();
        prueba=new ArrayList<>();
        arrayAdapter = new ArrayAdapter(this,R.layout.fila_listas,R.id.nombre_fila_lista,artistNames);
        lstArtist.setAdapter(arrayAdapter);



        //FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        databaseReference = FirebaseDatabase.getInstance().getReference(); ///Raiz
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();//Para extraeerr el Uid del cliente
        PruUid=user.getUid(); //Guardamos Uid en variable


        databaseReference.child("usersvstravel").child(Code).addValueEventListener(new ValueEventListener() {

            @Override

            public void onDataChange(DataSnapshot dataSnapshot) {

                artistNames.clear();
                prueba.clear();

                if (dataSnapshot.exists()){
                    for (DataSnapshot snapshot:dataSnapshot.getChildren()) {
                        DataListRoute datalist = snapshot.getValue(DataListRoute.class);
                        Log.w(TAG,datalist.getName());
                        Log.w(TAG,datalist.getLastname());
                        artistNames.add(R.drawable.avatar1,(datalist.getName()+" "+datalist.getLastname()));
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



        lstArtist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String iduserroute= prueba.get(position).getId();
                String coderoute= prueba.get(position).getCode();

                Intent intent = new Intent(UsersListActivity.this, ChatActivity.class);
                intent.putExtra("id", iduserroute);
                intent.putExtra("parametro", coderoute);
                startActivity(intent);
                finish();

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

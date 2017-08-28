package com.controlderuta.guardianroute;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

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

public class UserListCallActivity extends AppCompatActivity {

    private static final String TAG = "UserListCallActivity";
    private DatabaseReference databaseReference;

    private ListView lstArtist;
    private ArrayAdapter arrayAdapter;
    private List<String> artistNames;
    private List<DataListRoute> prueba;
    Button btnUserCall;

    String codemaster;
    String comodin;

    String PruUid;
    String Code;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_list_call);

        Code=getIntent().getExtras().getString("parametro");

        showToolbar("", false);//llamamos la toolbar

        btnUserCall=(Button)findViewById(R.id.backuserlistcall);

        btnUserCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserListCallActivity.this, NewMapActivity.class);
                intent.putExtra("parametro", Code);
                startActivity(intent);
                finish();
            }
        });

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
                        artistNames.add((datalist.getName()+" "+datalist.getLastname()));
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

                String iconface=prueba.get(position).getIconface();
                String iduserroute= prueba.get(position).getId();
                String coderoute= prueba.get(position).getCode();
                String phoneuser= prueba.get(position).getPhone();
                String nameuser=prueba.get(position).getName();
                String lastnameuser=prueba.get(position).getLastname();
                String completename = nameuser+" "+lastnameuser;

                Intent intent = new Intent(UserListCallActivity.this, CallActivity.class);
                intent.putExtra("id", iduserroute);
                intent.putExtra("parametro", coderoute);
                intent.putExtra("llamada", phoneuser);
                intent.putExtra("name", completename);
                intent.putExtra("face", iconface);

                startActivity(intent);
                finish();

            }
        });
    }

    public void showToolbar (String tittle, boolean upButton){//Metoodo de la toolbar

        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbarselectlistcall);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(tittle);
        getSupportActionBar().setDisplayHomeAsUpEnabled(upButton);//habilitamos la visibilidad de botton de up
    }
}


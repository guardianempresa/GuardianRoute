package com.controlderuta.guardianroute;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.controlderuta.guardianroute.Model.DataListRoute;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import android.text.format.DateFormat;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class ChatActivity extends AppCompatActivity {

    String Iduser;
    String Code;
    String PruUid;
    String textDate;

    TextView idchat;
    TextView codigochat;

    private static final String TAG = "ChatActivity";

    private DatabaseReference databaseReference;

    private ListView lstArtist;
    private ArrayAdapter arrayAdapter;
    private List<String> artistNames;
    private List<DataListRoute> prueba;

    FloatingActionButton btnSend;
    TextView textMensaje;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        showToolbar("", true);//llamamos la toolbar


        Iduser=getIntent().getExtras().getString("id");
        Code=getIntent().getExtras().getString("parametro");

        btnSend     =(FloatingActionButton)findViewById(R.id.fabsend);
        textMensaje =(TextView)findViewById(R.id.textMensaje);


        lstArtist = (ListView)findViewById(R.id.lstArtist);
        artistNames = new ArrayList<>();
        prueba=new ArrayList<>();
        arrayAdapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1,artistNames);
        lstArtist.setAdapter(arrayAdapter);






        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SimpleDateFormat s = new SimpleDateFormat("ddMMyyyyhhmmss");
                String keyfecha = s.format(new Date());


                SimpleDateFormat g = new SimpleDateFormat("hh:mm");
                String hora = g.format(new Date());



                DatabaseReference ref =FirebaseDatabase.getInstance().getReference();
                DatabaseReference mensajeRef = ref.child("chat").child(Code).child(Iduser).child(keyfecha).child("messageuser");
                String text=textMensaje.getText().toString();
                mensajeRef.setValue(text);

                DatabaseReference mensajeRef2 = ref.child("chat").child(Code).child(Iduser).child(keyfecha).child("time");
                String textseconds=textMensaje.getText().toString();
                mensajeRef2.setValue(hora);


            }
        });


        databaseReference = FirebaseDatabase.getInstance().getReference(); ///Raiz
        databaseReference.child("chat").child(Code).child(Iduser).addValueEventListener(new ValueEventListener() {

            @Override

            public void onDataChange(DataSnapshot dataSnapshot) {

                artistNames.clear();
                prueba.clear();

                if (dataSnapshot.exists()){
                    for (DataSnapshot snapshot:dataSnapshot.getChildren()) {
                        DataListRoute datalist = snapshot.getValue(DataListRoute.class);
                        Log.w(TAG,datalist.getMessageuser());
                        artistNames.add(datalist.getMessageuser());
                        prueba.add(datalist);

                    }
                }
                arrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



    }

    public void showToolbar (String tittle, boolean upButton){//Metoodo de la toolbar

        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbarchat);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(tittle);
        getSupportActionBar().setDisplayHomeAsUpEnabled(upButton);//habilitamos la visibilidad de botton de up
    }
}

package com.controlderuta.guardianroute;
import android.content.DialogInterface;
import android.content.Intent;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
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

import es.dmoral.toasty.Toasty;

public class RemoveRouteActivity extends AppCompatActivity {

    private static final String TAG = "RemoveRouteActivity";
    private DatabaseReference databaseReference;

    private ListView lstArtist;
    private ArrayAdapter arrayAdapter;
    private List<String> artistNames;
    private List<DataListRoute> prueba;
    Button btnRemove;

    String idPrueba;
    String PruUid;
    String Code;

    //Alert

    AlertDialog alert = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remove_route);

        showToolbar("", false);//llamamos la toolbar

        Code=getIntent().getExtras().getString("parametro");

        AlertRemove();

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

                idPrueba = prueba.get(position).getId();
                prueba.remove(position);
                artistNames.remove(position);
                AlertConfirm();

                return true;
            }
        });

    }

    private void AlertRemove(){
        final AlertDialog.Builder builder =new AlertDialog.Builder(this);
        builder.setMessage(getResources().getString(R.string.removeinfo))
                .setCancelable(false)
                .setPositiveButton(getResources().getString(R.string.helpConfirm), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(@SuppressWarnings("unused")final DialogInterface dialog, @SuppressWarnings("unused") final int which) {
                    }
                });
        alert = builder.create();
        alert.show();
    }

    private void AlertConfirm(){
        final AlertDialog.Builder builder =new AlertDialog.Builder(this);
        builder.setMessage(getResources().getString(R.string.removeconfirm))
                .setCancelable(false)
                .setPositiveButton(getResources().getString(R.string.yesGps), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(@SuppressWarnings("unused")final DialogInterface dialog, @SuppressWarnings("unused") final int which) {

                        databaseReference.child("drivervstravel").child(PruUid).child(idPrueba).removeValue();
                        databaseReference.child("starandfinish").child(idPrueba).child("estado").setValue(4);

                        Intent intent = new Intent(RemoveRouteActivity.this, RouteListActivity.class);
                        startActivity(intent);
                        finish();
                    }
                })

                .setNegativeButton(getResources().getString(R.string.noGps), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(final DialogInterface dialog, @SuppressWarnings("unused") int which) {

                        Intent intent = new Intent(RemoveRouteActivity.this, RemoveRouteActivity.class);
                        intent.putExtra("parametro", Code);
                        startActivity(intent);
                        finish();

                    }
                });
        alert = builder.create();
        alert.show();
    }


    @Override
    protected void onDestroy(){
        super.onDestroy();
        if(alert!=null)
        {
            alert.dismiss();
        }
    }

    public void showToolbar (String tittle, boolean upButton){//Metoodo de la toolbar

        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbarremove);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(tittle);
        getSupportActionBar().setDisplayHomeAsUpEnabled(upButton);//habilitamos la visibilidad de botton de up
    }

}
package com.controlderuta.guardianroute;

import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.ArrayAdapter;
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

public class RouteListActivity extends AppCompatActivity {

    private static final String TAG = "RouteListActivity";
    private DatabaseReference databaseReference;

    private ListView lstArtist;
    private ArrayAdapter arrayAdapter;
    private List<String> artistNames;

    String PruUid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route_list);

        lstArtist = (ListView)findViewById(R.id.lstArtist);
        artistNames = new ArrayList<>();
        arrayAdapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1,artistNames);
        lstArtist.setAdapter(arrayAdapter);



        //FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        databaseReference = FirebaseDatabase.getInstance().getReference(); ///Raiz
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();//Para extraeerr el Uid del cliente
        PruUid=user.getUid(); //Guardamos Uid en variable


        databaseReference.child("drivervstravel").child(PruUid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                artistNames.clear();
                if (dataSnapshot.exists()){
                    for (DataSnapshot snapshot:dataSnapshot.getChildren()) {
                        DataListRoute datalist = snapshot.getValue(DataListRoute.class);
                        Log.w(TAG,datalist.getName());
                        artistNames.add(datalist.getName());
                    }
                }
                arrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }

}
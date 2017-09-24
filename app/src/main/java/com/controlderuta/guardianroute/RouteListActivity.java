package com.controlderuta.guardianroute;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
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

public class RouteListActivity extends AppCompatActivity {

    private static final String TAG = "SelectListActivity";
    private DatabaseReference databaseReference;

    Location location;//objeto localition
    LocationManager locationManager;// objeto location manager
    LocationListener locationListener;

    AlertDialog alert = null;

    private ListView lstArtist;
    private ArrayAdapter arrayAdapter;
    private List<String> artistNames;
    private List<DataListRoute> prueba;

    TextView btnCreateRoute;


    String PruUid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route_list);
        conector();

        btnCreateRoute = (TextView)findViewById(R.id.textClick);

        btnCreateRoute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(RouteListActivity.this, NewCreateRouteActivity.class);
                startActivity(intent);
                finish();
            }
        });

        lstArtist = (ListView)findViewById(R.id.lstArtist);
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


        lstArtist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String coderoute = prueba.get(position).getId();


                if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)){
                    AlertNoGpsDos();
                }else {

                    databaseReference = FirebaseDatabase.getInstance().getReference();
                    databaseReference.child("starandfinish").child(coderoute).child("estado").setValue(0);
                    databaseReference.child("starandfinish").child(coderoute).child("tipo").setValue(0);

                    //Toast.makeText(RouteListActivity.this,coderoute,Toast.LENGTH_SHORT);
                    Intent intent = new Intent(RouteListActivity.this, NewMapActivity.class);
                    intent.putExtra("parametro", coderoute);
                    startActivity(intent);
                    finish();

                }




            }
        });


    }


    //

    public void conector() {


        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);//se asigna el servicio de localizacion

        //Alerta de GPS no encendido

        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)){
            AlertNoGps();
        }


        locationListener =new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {

                //muestraLoc(location);
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {



            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {


            }
        };
        geolocalizar();
    }



    //Gps permisos
    @Override
    public void onRequestPermissionsResult (int requestCode,String[] permission,int[] grantResult){
        switch (requestCode){
            case 10:
                geolocalizar();
                break;
            default:return;
        }
    }

    private void geolocalizar() {


            /*si a sdk es mayor a la 23 que en varcion code es M cheque permisos y si no es mayor no es requerido
            chequeo de permisos de locacalizacion no es solicitado en verciones menores a sdk 23
             si los permisos fueron denegados no retorna nada de lo contracion entrega la lastlocation*/

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED&&
                ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
        {
            if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M)
            {
                requestPermissions(new String[]{android.Manifest.permission.ACCESS_COARSE_LOCATION, android.Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission.INTERNET},10);
                location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                return;

            }else{
                location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                return;}
        }
        else{
            location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 8000, 100, locationListener);//actualizacion de ubicacion cada 15seg
        }
    }

    //liberacion de los servicios*
    protected void onPause(Bundle savedInstanceState) {
        super.onPause();
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M) {
            if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;}
            else {locationManager.removeUpdates(locationListener);}}
        else { locationManager.removeUpdates(locationListener);}
    }


    public void muestraLoc(Location Loc){

        if (location==null){

        }else{

        }

    }



    //Metodo alerta gps no encendido


    private void AlertNoGps(){
        final AlertDialog.Builder builder =new AlertDialog.Builder(this);
        builder.setMessage(getResources().getString(R.string.hacerGps))
                .setCancelable(false)
                .setPositiveButton(getResources().getString(R.string.textbtnOk), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(@SuppressWarnings("unused")final DialogInterface dialog, @SuppressWarnings("unused") final int which) {
                        startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                    }
                });

        alert = builder.create();
        alert.show();
    }

    //Metodo alerta gps no encendido


    private void AlertNoGpsDos(){
        final AlertDialog.Builder builder =new AlertDialog.Builder(this);
        builder.setMessage(getResources().getString(R.string.selectGps))
                .setCancelable(false)
                .setPositiveButton(getResources().getString(R.string.textbtnOk), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(@SuppressWarnings("unused")final DialogInterface dialog, @SuppressWarnings("unused") final int which) {
                        startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                    }
                });

        alert = builder.create();
        alert.show();
    }

    private void AlertNoGpsTres(){
        final AlertDialog.Builder builder =new AlertDialog.Builder(this);
        builder.setMessage(getResources().getString(R.string.proveedorGps))
                .setCancelable(false)
                .setPositiveButton(getResources().getString(R.string.textbtnOk), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(@SuppressWarnings("unused")final DialogInterface dialog, @SuppressWarnings("unused") final int which) {
                        startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
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


}
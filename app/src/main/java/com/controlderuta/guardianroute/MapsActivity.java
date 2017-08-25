package com.controlderuta.guardianroute;

import android.Manifest;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatAutoCompleteTextView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.controlderuta.guardianroute.Model.DataUsuarios;
import com.controlderuta.guardianroute.Model.UsuariosRecorrido;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback{


    private static final String TAG ="" ;
    Location location;//objeto localition
    LocationManager locationManager;// objeto location manager
    LocationListener locationListener;
    double latitud=0.0;
    double longitud=0.0;
    //Variables Geolocalizacion
    private GoogleMap mMap;

    //Variables FireBase
    private DatabaseReference databaseReference;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private String PruUid; //Para traer token del otro activity
    private DatabaseReference mensajeRef;
    private String padreid;
    private double latitudusuario;
    private double longitudusuario;






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        showToolbar(getResources().getString(R.string.title_bar_maps_rutas), false);//llamamos la toolbar


        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);



    }
    public void showToolbar (String tittle, boolean upButton) {//Metoodo de la toolbar

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(tittle);
        getSupportActionBar().setDisplayHomeAsUpEnabled(upButton);//habilitamos la visibilidad de botton de up

    }
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

            }else{
            location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            return;}
        }
        else{
        location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 15000, 0, locationListener);//actualizacion de ubicacion cada 15seg
            }
    }
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);//se asigna el servicio de localizacion
        locationListener =new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {

                escribirposicion(location);


            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {
                Intent intent=new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);//en caso que el proveedor este desactivado ejecuta para que el usuario lo active
                startActivity(intent);//se ejecuta en cualquier momento

            }
        };
        geolocalizar();
    }


    private void escribirposicion(Location location) {
        if (location!=null){
            //verifica que no se null
            //lee la variable location para que no sea null y obtiene la latitud y longitud
            //pilas no puede existir un valor null para obtener el latitud y longitud
            longitud = location.getLongitude();
            latitud=location.getLatitude();
            LatLng actual =new LatLng(latitud,longitud);
            mMap.clear();//limpia el mapa
            mMap.addMarker(new MarkerOptions().position(actual).title("mi pocicion").snippet(latitud+","+longitud));
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(actual,14));
            //se va actualizando la lat y long en la vase de datos
            databaseReference = FirebaseDatabase.getInstance().getReference();
            mensajeRef = databaseReference.child("travel").child("coderecorrido").child("latitudup");//Nodo
            mensajeRef.setValue(latitud);
            mensajeRef = databaseReference.child("travel").child("coderecorrido").child("longitudup");//Nodo
            mensajeRef.setValue(longitud);
            //---------------------------------------
            tokenpadres();
        }
    }
    private void tokenpadres(){//se consulta la base de datos de usuarios recorridos para obtener los token que le corresponden al recorrrido
        databaseReference.child("ususariosrecorrido").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        UsuariosRecorrido usuariosRecorrido = snapshot.getValue(UsuariosRecorrido.class);
                        padreid = usuariosRecorrido.getTokenusuarios();
                        if (padreid!=null) {
                        padredatos(padreid);
                        }
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void padredatos(String tokenpadre) {// se obtiene datos como la latitud y longitud y agrega el marcador
        databaseReference.child("datausuarios").child(tokenpadre).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapsh) {
                DataUsuarios dataUsuarios = dataSnapsh.getValue(DataUsuarios.class);
                if (dataUsuarios != null) {
                    latitudusuario = dataUsuarios.getLatitud();
                    longitudusuario = dataUsuarios.getLongitud();
                    LatLng padre = new LatLng(latitudusuario, longitudusuario);
                    mMap.addMarker(new MarkerOptions().position(padre));
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }

        });
    }
    //liberacion de los servicios*
    protected void onPause(Bundle savedInstanceState) {
        super.onPause();
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M) {
            if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;}
            else {locationManager.removeUpdates(locationListener);}}
        else { locationManager.removeUpdates(locationListener);}
    }
}


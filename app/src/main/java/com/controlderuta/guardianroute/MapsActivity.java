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

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback, View.OnClickListener {


    private static final String TAG ="" ;
    //Variables Geolocalizacion
    private GoogleMap mMap;
    Location location;//objeto localition
    LocationManager locationManager;// objeto location manager
    LocationListener locationListener;
    double latitud=0.0;
    double longitud=0.0;

    //Variables FireBase

    private DatabaseReference databaseReference;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private String PruUid; //Para traer token del otro activity




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
        if (location!=null){//verifica que no se null
            //lee la variable location para que no sea null y obtiene la latitud y longitud
            //pilas no puede existir un valor null para obtener el latitud y longitud
            longitud = location.getLongitude();
            latitud=location.getLatitude();
            LatLng actual =new LatLng(latitud,longitud);
            LatLng av_68 =new LatLng(4.686204,-74.080497);
            LatLng nqs_cll75 =new LatLng(4.670347,-74.071378);
            LatLng u_nacional =new LatLng(4.637391, -74.079281);
            LatLng Santa_isabel =new LatLng(4.601504, -74.102666);
            LatLng san_mateo =new LatLng(4.585796, -74.205717);
            mMap.clear();//limpia el mapa

            mMap.addMarker(new MarkerOptions().position(actual).title("mi pocicion").snippet(latitud+","+longitud).icon(BitmapDescriptorFactory.fromResource(R.drawable.transporte)));
            mMap.addMarker(new MarkerOptions().position(av_68).icon(BitmapDescriptorFactory.fromResource(R.drawable.marcadorpadre)));
            mMap.addMarker(new MarkerOptions().position(nqs_cll75).icon(BitmapDescriptorFactory.fromResource(R.drawable.marcadorpadre)));
            mMap.addMarker(new MarkerOptions().position(u_nacional).icon(BitmapDescriptorFactory.fromResource(R.drawable.marcadorpadre)));
            mMap.addMarker(new MarkerOptions().position(Santa_isabel).icon(BitmapDescriptorFactory.fromResource(R.drawable.marcadorpadre)));
            mMap.addMarker(new MarkerOptions().position(san_mateo).icon(BitmapDescriptorFactory.fromResource(R.drawable.marcadorpadre)));

            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(actual,14));

            //UpLocation upLocation =new UpLocation(PruUid,latitud,longitud);//sube la longitud y tatitud a la raiz de la id
            //databaseReference.child("rutas").child(upLocation.getId()).setValue(upLocation);
        }
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

    @Override
    public void onClick(View v) {


    }


    //metodo alarma


    private void starAlarm() { ///metodo notificacion

        AlarmManager manager =(AlarmManager)getSystemService(Context.ALARM_SERVICE);
        Intent myIntent;
        PendingIntent pendingIntent;

        myIntent=new Intent(MapsActivity.this,AlarmNotificationReceiver.class);
        pendingIntent=PendingIntent.getBroadcast(this,0,myIntent,0);
        manager.set(AlarmManager.RTC_WAKEUP, 0,pendingIntent);

    }



}


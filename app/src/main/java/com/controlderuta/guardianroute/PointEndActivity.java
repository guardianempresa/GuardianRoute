package com.controlderuta.guardianroute;

import android.*;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Point;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.preference.DialogPreference;
import android.provider.Settings;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Toast;

import com.controlderuta.guardianroute.Model.DataUsuarios;
import com.controlderuta.guardianroute.Model.UserList;
import com.controlderuta.guardianroute.Model.UsuariosRecorrido;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;


public class PointEndActivity extends AppCompatActivity implements OnMapReadyCallback {

    //Variables diego viejas

    private static final String TAG ="" ;
    Location location;//objeto localition
    LocationManager locationManager;// objeto location manager
    LocationListener locationListener;
    double latitud=0.0;
    double longitud=0.0;
    //Variables Geolocalizacion
    private GoogleMap mMap;


    //Alert

    AlertDialog alert = null;

    //Variables FireBase
    private DatabaseReference databaseReference;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private String PruUid; //Para traer token del otro activity
    private DatabaseReference mensajeRef;
    private String padreid;
    private double latitudusuario;
    private double longitudusuario;

    private double latitudllegada;
    private  double longitudllegada;

    //Button btnSigPoint;

    String Code;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_point_end);

        Code=getIntent().getExtras().getString("parametro");


            SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                    .findFragmentById(R.id.mapstar);
            mapFragment.getMapAsync(this);

            AlertHelp();


    }



    //Metodo implementado maps
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);//se asigna el servicio de localizacion

        //Alerta de GPS no encendido

        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)){
            AlertNoGps();
        }


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
                // Intent intent=new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);//en caso que el proveedor este desactivado ejecuta para que el usuario lo active
                //startActivity(intent);//se ejecuta en cualquier momento

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

    //Metodo alerta gps no encendido


    private void AlertNoGps(){
        final AlertDialog.Builder builder =new AlertDialog.Builder(this);
        builder.setMessage(getResources().getString(R.string.alertGps))
                .setCancelable(false)
                .setPositiveButton(getResources().getString(R.string.yesGps), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(@SuppressWarnings("unused")final DialogInterface dialog, @SuppressWarnings("unused") final int which) {
                        startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                    }
                })

                .setNegativeButton(getResources().getString(R.string.noGps), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(final DialogInterface dialog, @SuppressWarnings("unused") int which) {
                        dialog.cancel();
                    }
                });
        alert = builder.create();
        alert.show();
    }


    private void AlertHelp(){
        final AlertDialog.Builder builder =new AlertDialog.Builder(this);
        builder.setMessage(getResources().getString(R.string.helpPoint))
                .setCancelable(false)
                .setPositiveButton(getResources().getString(R.string.helpConfirm), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(@SuppressWarnings("unused")final DialogInterface dialog, @SuppressWarnings("unused") final int which) {
                        dialog.cancel();
                        AlertHelpTwo();
                    }

                });
        alert = builder.create();
        alert.show();
    }

    private void AlertHelpTwo(){
        final AlertDialog.Builder builder =new AlertDialog.Builder(this);
        builder.setMessage(getResources().getString(R.string.helpPointTwo))
                .setCancelable(false)
                .setPositiveButton(getResources().getString(R.string.helpConfirm), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(@SuppressWarnings("unused")final DialogInterface dialog, @SuppressWarnings("unused") final int which) {
                        dialog.cancel();
                        AlertHelpThree();
                    }

                });
        alert = builder.create();
        alert.show();
    }

    private void AlertHelpThree(){
        final AlertDialog.Builder builder =new AlertDialog.Builder(this);
        builder.setMessage(getResources().getString(R.string.helpPointThree))
                .setCancelable(false)
                .setPositiveButton(getResources().getString(R.string.helpConfirm), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(@SuppressWarnings("unused")final DialogInterface dialog, @SuppressWarnings("unused") final int which) {
                        dialog.cancel();
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



    //geolocalizar

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
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 3000000, 0, locationListener);//actualizacion de ubicacion cada 15seg
        }
    }



    //Escribe los datos en firebase travel


    private void escribirposicion(Location location) {
        if (location!=null){
            //verifica que no se null
            //lee la variable location para que no sea null y obtiene la latitud y longitud
            //pilas no puede existir un valor null para obtener el latitud y longitud
            longitud = location.getLongitude();
            latitud=location.getLatitude();
            LatLng actual =new LatLng(latitud,longitud);
            //mMap.clear();//limpia el mapa
            //mMap.addMarker(new MarkerOptions().position(actual).draggable(true).title("mi pocicion").snippet(latitud+","+longitud));
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(actual,13));
            //se va actualizando la lat y long en la vase de datos


            mMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
                @Override
                public void onMapLongClick(LatLng latLng) {
                    mMap.clear();//limpia el mapa
                    mMap.addMarker(new MarkerOptions()
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.marketend))
                    .position(latLng))
                    .isDraggable();

                    latitudllegada=latLng.latitude;
                    longitudllegada=latLng.longitude;

                }
            });

            //---------------------------------------

            mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                @Override
                public boolean onMarkerClick(Marker marker) {

                    databaseReference = FirebaseDatabase.getInstance().getReference();
                    mensajeRef = databaseReference.child("travel").child(Code).child("latitudllegada");//Nodo
                    mensajeRef.setValue(latitudllegada);
                    mensajeRef = databaseReference.child("travel").child(Code).child("longitudllegada");//Nodo
                    mensajeRef.setValue(longitudllegada);

                    Intent intent = new Intent(PointEndActivity.this, RouteNameActivity.class);
                    intent.putExtra("parametro", Code);
                    startActivity(intent);
                    finish();
                    return false;
                }
            });
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
}


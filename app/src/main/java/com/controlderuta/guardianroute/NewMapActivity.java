package com.controlderuta.guardianroute;

import android.*;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.preference.DialogPreference;
import android.provider.Settings;
import android.service.carrier.CarrierMessagingService;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.Toast;

import com.controlderuta.guardianroute.Model.ArrayMarkets;
import com.controlderuta.guardianroute.Model.CheckList;
import com.controlderuta.guardianroute.Model.DataListRoute;
import com.controlderuta.guardianroute.Model.DataUsuarios;
import com.controlderuta.guardianroute.Model.Markets;
import com.controlderuta.guardianroute.Model.Recorridos;
import com.controlderuta.guardianroute.Model.UserList;
import com.controlderuta.guardianroute.Model.UsuariosRecorrido;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.android.gms.common.api.GoogleApiClient;



import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import es.dmoral.toasty.Toasty;

public class NewMapActivity extends AppCompatActivity implements OnMapReadyCallback, NavigationView.OnNavigationItemSelectedListener, GoogleApiClient.OnConnectionFailedListener {


    //Variables diego viejas

    private static final String TAG ="NewMapActivity" ;
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
    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;
   // private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener firebaseAuthListener;
    private String PruUid; //Para traer token del otro activity
    private DatabaseReference mensajeRef;

    private static final int RC_SIGN_IN=1;
    private GoogleApiClient googleApiClient;


    //consulta de marcadores  usuarios

    double value;
    double value2;
    String Check;
    String Completename;
    String Code;
    String VarButtom;


    // Variables Markets
    String Cod;
    String Icon;
    double LatitudUser;
    double LongitudUser;
    String Id;
    String Name;
    String LastName;

    //variables de calculo de distancias
    float distance;
    LatLng actual;

    //Botones

    FloatingActionButton btnPlay;
    FloatingActionButton btnStop;
    String IdCheck;
    String IdCheckYes;
    String CheckPlay;
    String CheckStop;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_map);

        Code=getIntent().getExtras().getString("parametro");

        ///Firebase ultimo para logOut

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();


        googleApiClient=new GoogleApiClient.Builder(this)
                .enableAutoManage(this,this)
                .addApi(Auth.GOOGLE_SIGN_IN_API,gso)
                .build();



        firebaseAuth = FirebaseAuth.getInstance();//Firebase Ultimo
        firebaseAuthListener = new FirebaseAuth.AuthStateListener(){
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth){

                FirebaseUser user =firebaseAuth.getCurrentUser();
            }
        };
        //-----fin ///Firebase ultimo para logOut





        btnPlay = (FloatingActionButton) findViewById(R.id.fabMapPlay);
        btnStop = (FloatingActionButton) findViewById(R.id.fabMapStop);


        btnPlay.setVisibility(View.VISIBLE );
        btnStop.setVisibility(View.INVISIBLE );


        btnPlay.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                return false;
            }
        });

        //inicia eventos de botones

        btnPlay.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {


                btnPlay.setVisibility(View.INVISIBLE );
                btnStop.setVisibility(View.VISIBLE );

                databaseReference = FirebaseDatabase.getInstance().getReference(); ///Raiz
                databaseReference.child("usersvstravel").child(Code).addListenerForSingleValueEvent(new ValueEventListener() {


                    @Override

                    public void onDataChange(DataSnapshot dataSnapshot) {

                        //For que busca en firebase segun nodo y barre la base de datos
                        if (dataSnapshot.exists()){
                            for (DataSnapshot snapshot:dataSnapshot.getChildren()) {
                                Markets datacheck = snapshot.getValue(Markets.class);
                                IdCheck=datacheck.getId();
                                CheckPlay=datacheck.getCheck();


                                databaseReference.child("usersvstravel").child(Code).child(IdCheck).child("check").setValue("n");
                            }
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
                return false;
            }
        });

        btnStop.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {


                btnPlay.setVisibility(View.VISIBLE );
                btnStop.setVisibility(View.INVISIBLE );

                //------

                databaseReference = FirebaseDatabase.getInstance().getReference(); ///Raiz
                databaseReference.child("usersvstravel").child(Code).addListenerForSingleValueEvent(new ValueEventListener() {//callback Unico evento

                    @Override

                    public void onDataChange(DataSnapshot dataSnapshot) {

                        //For que busca en firebase segun nodo y barre la base de datos
                        if (dataSnapshot.exists()){
                            for (DataSnapshot snapshot:dataSnapshot.getChildren()) {
                                Markets datacheck = snapshot.getValue(Markets.class);

                                //Extraigo la variables

                                IdCheckYes=datacheck.getId();
                                CheckStop=datacheck.getCheck();

                                //subo datos

                                    databaseReference.child("usersvstravel").child(Code).child(IdCheckYes).child("check").setValue("s");

                            }
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
                return false;
            }}
        );

        //----- terminan los eventos de botinoes

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);



        //FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        databaseReference = FirebaseDatabase.getInstance().getReference(); ///Raiz
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();//Para extraeerr el Uid del cliente
        PruUid=user.getUid(); //Guardamos Uid en variable



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.new_map, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_perfil) {

            //Intent intent = new Intent(NewMapActivity.this, EditNameActivity.class);
            Intent intent = new Intent(NewMapActivity.this, PerfilEditActivity.class);
            intent.putExtra("parametro", Code);
            startActivity(intent);


            return true;
        }

        //if (id == R.id.action_settings) { Pendiente para el futuro
          //  return true;
        //}

        if (id == R.id.action_outlogin) {
            logOut();//Firebase Ultimo
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_change) {
            // Handle the camera action

            Intent intent = new Intent(NewMapActivity.this, SelectListActivity.class);
            intent.putExtra("parametro", Code);
            startActivity(intent);


        } else if (id == R.id.nav_plus) {

            Intent intent = new Intent(NewMapActivity.this, CreateRouteMenuActivity.class);
            intent.putExtra("parametro", Code);
            startActivity(intent);

        }else if (id == R.id.nav_minus) {

            Intent intent = new Intent(NewMapActivity.this, RemoveRouteActivity.class);
            intent.putExtra("parametro", Code);
            startActivity(intent);

        } else if (id == R.id.nav_alert) {

            Intent intent = new Intent(NewMapActivity.this, NewAlertActivity.class);
            intent.putExtra("parametro", Code);
            startActivity(intent);


        } /*else if (id == R.id.nav_chat) {

            Intent intent = new Intent(NewMapActivity.this, UsersListActivity.class);
            intent.putExtra("parametro", Code);
            startActivity(intent);


        } */else if (id == R.id.nav_call) {

            Intent intent = new Intent(NewMapActivity.this, UserListCallActivity.class);
            intent.putExtra("parametro", Code);
            startActivity(intent);

        } else if (id == R.id.nav_users) {

            Intent intent = new Intent(NewMapActivity.this, SendCodeActivity.class);
            intent.putExtra("parametro", Code);
            startActivity(intent);

        }/*else if (id == R.id.nav_drivers) {

        }*/


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
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
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 15000, 0, locationListener);//actualizacion de ubicacion cada 15seg
        }
    }



    //Escribe los datos en firebase travel


    private void escribirposicion(final Location location) {
        if (location!=null){
            //verifica que no se null
            //lee la variable location para que no sea null y obtiene la latitud y longitud
            //pilas no puede existir un valor null para obtener el latitud y longitud
            longitud = location.getLongitude();
            latitud=location.getLatitude();
            actual =new LatLng(latitud,longitud);
            mMap.clear();//limpia el mapa
            mMap.addMarker(new MarkerOptions().position(actual).icon(BitmapDescriptorFactory.fromResource(R.drawable.bus)).title("mi pocicion").snippet(latitud+","+longitud));
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(actual,16));





            //se va actualizando la lat y long en la vase de datos



            databaseReference = FirebaseDatabase.getInstance().getReference();
            mensajeRef = databaseReference.child("travel").child(Code).child("latitud");//Nodo
            mensajeRef.setValue(latitud);
            mensajeRef = databaseReference.child("travel").child(Code).child("longitud");//Nodo
            mensajeRef.setValue(longitud);


            //Ya funciona Marcadores puestos

            databaseReference = FirebaseDatabase.getInstance().getReference(); ///Raiz
            databaseReference.child("usersvstravel").child(Code).addValueEventListener(new ValueEventListener() {

                @Override

                public void onDataChange(DataSnapshot dataSnapshot) {

            //For que busca en firebase segun nodo y barre la base de datos
                    if (dataSnapshot.exists()){
                        for (DataSnapshot snapshot:dataSnapshot.getChildren()) {
                            Markets datalist = snapshot.getValue(Markets.class);

                            //Extraigo la variables

                            Cod = datalist.getCode();
                            Icon = datalist.getIcon();
                            LatitudUser = datalist.getLatitud();
                            LongitudUser = datalist.getLongitud();
                            Id=datalist.getId();
                            Name=datalist.getName();
                            LastName=datalist.getLastname();
                            Check=datalist.getCheck();

                            Markets user = new Markets(Cod, Icon, LatitudUser, LongitudUser,Id,Name,LastName,Check);
                            //aMarkets.addMarkets(user);
                            //Toast.makeText(NewMapActivity.this,"Prueba"+aMarkets.getMarkets(),Toast.LENGTH_LONG).show();

                            LatLng nuevo = new LatLng(LatitudUser, LongitudUser); ///Concateno para hacer marcador

                            Completename = Name+" "+LastName; //Concatenar para poner nombre de la persona en el marcador


                            Location locationA = new Location("punto A");

                            locationA.setLatitude(latitud);
                            locationA.setLongitude(longitud);

                            Location locationB = new Location("punto B");

                            locationB.setLatitude(LatitudUser);
                            locationB.setLongitude(LongitudUser);

                            distance = locationA.distanceTo(locationB); //calcula distancia entre la ruta y todos los usuarios



                            //Coloca el avatar segun el que tenga escogido el usuario

                            if (Icon.equals("avatar1")) {
                                mMap.addMarker(new MarkerOptions().position(nuevo).title(Completename).icon(BitmapDescriptorFactory.fromResource(R.drawable.avatar1)));

                            }else if (Icon.equals("avatar2")) {
                                mMap.addMarker(new MarkerOptions().position(nuevo).title(Completename).icon(BitmapDescriptorFactory.fromResource(R.drawable.avatar2)));

                            }else if (Icon.equals("avatar3")) {
                                mMap.addMarker(new MarkerOptions().position(nuevo).title(Completename).icon(BitmapDescriptorFactory.fromResource(R.drawable.avatar3)));

                            }else if (Icon.equals("avatar4")) {
                                mMap.addMarker(new MarkerOptions().position(nuevo).title(Completename).icon(BitmapDescriptorFactory.fromResource(R.drawable.avatar4)));

                            }else if (Icon.equals("avatar5")) {
                                mMap.addMarker(new MarkerOptions().position(nuevo).title(Completename).icon(BitmapDescriptorFactory.fromResource(R.drawable.avatar5)));

                            }else if (Icon.equals("avatar6")) {
                                mMap.addMarker(new MarkerOptions().position(nuevo).title(Completename).icon(BitmapDescriptorFactory.fromResource(R.drawable.avatar6)));

                            }else if (Icon.equals("avatar7")) {
                                mMap.addMarker(new MarkerOptions().position(nuevo).title(Completename).icon(BitmapDescriptorFactory.fromResource(R.drawable.avatar7)));

                            }else if (Icon.equals("avatar8")) {
                                mMap.addMarker(new MarkerOptions().position(nuevo).title(Completename).icon(BitmapDescriptorFactory.fromResource(R.drawable.avatar8)));

                            }else {
                                mMap.addMarker(new MarkerOptions().position(nuevo).title(Completename).icon(BitmapDescriptorFactory.fromResource(R.drawable.avatar9)));

                            }

                            //Si esta cerca suba datos

                            if (Check.equals("n")&&distance<100){

                                //Sube datos distancia



                                CheckList checking = new CheckList(distance,Name,LastName,Icon,Id);
                                databaseReference.child("check").child(Code).child(Id).setValue(checking);



                            }else{

                            }
                        }


                    }

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

/*
            //Marcador dde llegada se coloca en mapa longitud

            DatabaseReference ref =FirebaseDatabase.getInstance().getReference();
            DatabaseReference mensajeRef = ref.child("travel").child(Code).child("longitudllegada");

            mensajeRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                value =dataSnapshot.getValue(double.class);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });*/



         /*   //Marcador dde llegada se coloca en mapa latitud ---Provisional

            DatabaseReference ref2 =FirebaseDatabase.getInstance().getReference();
            DatabaseReference mensajeRef2 = ref2.child("travel").child(Code).child("latitudllegada");

            mensajeRef2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                value2 =dataSnapshot.getValue(double.class);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });*/

            LatLng llegada =new LatLng(value2,value);
            mMap.addMarker(new MarkerOptions().position(llegada).icon(BitmapDescriptorFactory.fromResource(R.drawable.marketend)));


            mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                @Override
                public boolean onMarkerClick(Marker marker) {

                    Intent intent = new Intent(NewMapActivity.this, AbordajeActivity.class);
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


    private void goLogInScreen(){///Firebase ultimo para logOut
        Intent intent=new Intent(this,SplashScreenActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }


    public void logOut(){///Firebase ultimo para logOut

        firebaseAuth.signOut();

        Auth.GoogleSignInApi.signOut(googleApiClient).setResultCallback(new ResultCallback<Status>() {
            @Override
            public void onResult(@NonNull Status status) {
                if (status.isSuccess()){
                    goLogInScreen();
                }else{
                    Toast.makeText(NewMapActivity.this,"La cague",Toast.LENGTH_SHORT);
                }
            }
        });
    }

    public void revoke(View view){///Firebase ultimo para logOut

        firebaseAuth.signOut();

        Auth.GoogleSignInApi.revokeAccess(googleApiClient).setResultCallback(new ResultCallback<Status>() {
            @Override
            public void onResult(@NonNull Status status) {
                if (status.isSuccess()){
                    goLogInScreen();
                }else{
                    Toast.makeText(NewMapActivity.this,"La cague mas revocado",Toast.LENGTH_SHORT);
                }
            }
        });
    }

    @Override///Firebase ultimo para logOut
    protected void onStart() {
        super.onStart();

        firebaseAuth.addAuthStateListener(firebaseAuthListener);
    }

    @Override///Firebase ultimo para logOut

    protected void onStop() {
        super.onStop();

        if (firebaseAuthListener!=null){
            firebaseAuth.removeAuthStateListener(firebaseAuthListener);
        }
    }

    @Override///Firebase ultimo para logOut
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}

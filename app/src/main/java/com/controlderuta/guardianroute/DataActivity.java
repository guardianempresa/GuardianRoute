package com.controlderuta.guardianroute;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.provider.Settings;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.controlderuta.guardianroute.Model.DataMonitorConductor;
import com.controlderuta.guardianroute.Model.Recorridos;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DataActivity extends AppCompatActivity {

    private static final String GUARDIAN = "guardian" ;
    ///   Link firebase
    private static final String PROYECT_ID ="https://wx3sk.app.goo.gl/";
    Button btndata;
    //AGREGO ESTO ---------------------------------------------
    DatabaseReference databaseReference;
    String PruUid;
    LocationManager locationManager;
    LocationListener locationListener;
    TextInputEditText textNombreRuta;
    //----------------------------------------------------------
    TextInputEditText textNombreConductor;
    TextInputEditText textMaticula;
    TextInputEditText textContacto;
    //validar
    int f=1;
    int a=1;
    int b=1;
    int c=1;
    int d=1;
    String codeRuta;
    String cadenacod1;
    String cadenacod2;
    int random1;
    int random2;


    //Random
    TextView codigoruta;
    private double latitud=0.0;
    private double longitud=0.0;
    private String NombreRuta =" ";
    private String NombreConductor = " ";
    private String Matricula = " ";
    private String Contacto = " ";
    private FloatingActionButton btncontactos;
    private String nameconductor;
    private String lastnameconductor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data);

        codigoruta=(TextView)findViewById(R.id.codigoruta);

        random1=(int)(Math.random()*100);
        cadenacod1 = String.valueOf(random1);
        random1=(int)(Math.random()*100);
        cadenacod2 = String.valueOf(random2);

        codeRuta = "GP"+random1+"AY"+random2;

        codigoruta.setText(codeRuta);




        btncontactos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                shareDeepLink(buildDeepLink(""));

            }
        });



        textNombreRuta=(TextInputEditText) findViewById(R.id.routename);
        textMaticula = (TextInputEditText) findViewById(R.id.idcar);
        textContacto= (TextInputEditText) findViewById(R.id.userphone);





        //AGREGO ESTO-----------------------------------------------
        databaseReference = FirebaseDatabase.getInstance().getReference();//obtiene el enlace de la db "ejemplos-android:"
        //ESTA ZONA ESTA EN RIESGO DE DAR UN VALOR NULL/
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();//Para extraeerr el Uid del cliente
        PruUid=user.getUid(); //Guardamos Uid en variable
        onMapReady();
        //----------------------------------------------------------




        showToolbar(getResources().getString(R.string.title_bar_maps_rutas), false);//llamamos la toolbar
    }
    //AGREGO ESTO ---------------------------------------------
    private void onMapReady()
    {//este se ejecuta primero

        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);//se asigna el servicio de localizacion
        locationListener =new LocationListener()
        {
            @Override
            public void onLocationChanged(Location location)
            {
                escribirposicion(location);
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras)
            {}

            @Override
            public void onProviderEnabled(String provider)
            {}

            @Override
            public void onProviderDisabled(String provider)
            {
                Intent intent=new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);//en caso que el proveedor este desactivado ejecuta para que el usuario lo active
                startActivity(intent);//se ejecuta en cualquier momento
            }
        };
        geolocalizar();
    }


    ///------------------------Link

    private String buildDeepLink(String key){

        return "?link=https://drive.google.com/open?id=0B3lY_7IS7EICeExHZUJfVFQyRFU";
    }

    private void shareDeepLink(String deepLink){
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_SUBJECT,"Firebase DeepLink Share");
        intent.putExtra(Intent.EXTRA_TEXT, deepLink);
        startActivity(intent);
    }





    //---------------------------------------------------------
    //AGREGO ESTO ---------------------------------------------
    @Override
    //esta es una revicion de permisos para que usuario acepte a dar su pocion
    public void onRequestPermissionsResult (int requestCode,String[] permission,int[] grantResult)
    {
        switch (requestCode)
        {
            case 10:
                geolocalizar();
                break;
            default:return;
        }
    }
    //--------------------------------------------------------
    //AGREGO ESTO ---------------------------------------------
    private void geolocalizar()
    {
        /*si a sdk es mayor a la 23 que en varcion code es M cheque permisos y si no es mayor no es requerido
         chequeo de permisos de locacalizacion no es solicitado en verciones menores a sdk 23
         si los permisos fueron denegados no retorna nada de lo contracion entrega la lastlocation*/
        //ESTOS SON LOS PERMISOS COMO TAL SON LOS MISMOS SOLICITADOS EN EL MANIFEST
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED&&
                ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
        {
            if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M)
            {
                requestPermissions(new String[]{android.Manifest.permission.ACCESS_COARSE_LOCATION, android.Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission.INTERNET},10);
            }
            else
            {   locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
                //actualizacion de ubicacion cada 15seg ejecuta la variable location listener
            }
        }
        else
        {
            locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
            //actualizacion de ubicacion cada 15seg ejecuta la variable location listener
        }
    }
    //----------------------------------------------------------
    //AGREGO ESTO ---------------------------------------------
    private void escribirposicion(Location location)
    {
        if (location != null)//verifica que no se null
        {
            //lee la variable location para que no sea null y obtiene la latitud y longitud
            //pilas no puede existir un valor null para obtener el latitud y longitud
            longitud = location.getLongitude();
            latitud = location.getLatitude();
            if (latitud != 0.0 && longitud != 0.0)//valida que la longitud y latitud si tengan valores
            {
                /*if (code.equals("")&& !nombre.equals("") && !apellido.equals("") && contacto1.equals("")&& contacto2.equals(""))
                { //se verifica que los campos no esten vacios
                    Toast.makeText(DataActivity.this, "Faltan campos por registrar",
                            Toast.LENGTH_SHORT).show();
                }
                else
                {*/
                //si los campos no estan vacios
                // se liberan los servicios de gps
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
                {
                    if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
                    {
                        return;
                    }
                    else
                    {
                        locationManager.removeUpdates(locationListener);
                    }
                }
                else
                {
                    locationManager.removeUpdates(locationListener);
                }//----------------------------------------
                //se activa el boton de siguiente
                btndata = (Button) findViewById(R.id.btnsigdata);
                btndata.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
                        //-----------------

                        NombreRuta = textNombreRuta.getText().toString();
                        NombreConductor = textNombreConductor.getText().toString();
                        Matricula = textMaticula.getText().toString();
                        Contacto = textContacto.getText().toString();



                        if (NombreRuta.equals("")) {

                            Toast.makeText(DataActivity.this, "Faltan campos por registrar",
                                    Toast.LENGTH_SHORT).show();
                            a=1;
                        }else{
                            a=0;
                        }

                        if (NombreConductor.equals("")) {

                            Toast.makeText(DataActivity.this, "Faltan campos por registrar",
                                    Toast.LENGTH_SHORT).show();
                            b=1;
                        }else{
                            b=0;
                        }

                        if (Matricula.equals("")) {

                            Toast.makeText(DataActivity.this, "Faltan campos por registrar",
                                    Toast.LENGTH_SHORT).show();
                            c=1;
                        }else   {
                            c=0;
                        }

                        if (Contacto.equals("")) {

                            Toast.makeText(DataActivity.this, "Faltan campos por registrar",
                                    Toast.LENGTH_SHORT).show();
                            d=1;
                        }       else {
                            d=0;
                        }

                        int res=a+b+c+d;

                        if (res==0){

                            //se suebn los datos
                           DataMonitorConductor dataMonitorConductor = new DataMonitorConductor(nameconductor, lastnameconductor, Contacto);
                            //sube la longitud y tatitud a la raiz de la id
                            databaseReference.child("datosruta").child(PruUid).setValue(dataMonitorConductor);


                            Intent intent = new Intent(DataActivity.this, MapsActivity.class);
                            startActivity(intent);
                            finish();

                        }else {
                            Toast.makeText(DataActivity.this, "Debe registrar la totalidad de los campos para seguir",
                                    Toast.LENGTH_SHORT).show();
                        }

                    }
                });

            }
        }
    }
    //--------------------------------------------------------------------




    public void showToolbar (String tittle, boolean upButton){//Metoodo de la toolbar

        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(tittle);
        getSupportActionBar().setDisplayHomeAsUpEnabled(upButton);//habilitamos la visibilidad de botton de up


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
package com.jdcasas.appmochilero;
import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.app.ActivityCompat;
import android.widget.EditText;
import android.widget.Toast;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.text.DateFormat;

public class Geolocalizacion {
    EditText et_latitud,et_longitud;
    private String latitud;
    private String longitud;
    Context context;
    private LocationListener locListener;
    private LocationManager locManager;
    private  int MY_PERMISSIONS_REQUEST_READ_CONTACTS  ;
    String id;
    int control=0;
    public Geolocalizacion(Context context,String id){
        this.context=context;
        this.id=id;
    }
    public Geolocalizacion(Context context, EditText et_latitud,EditText et_longitud){
        this.context=context;
        this.et_latitud=et_latitud;
        this.et_longitud=et_longitud;
        this.control=1;
    }
    ////////////////////////////////OBTENER COORDENAS/////////////////////////////////////////////////
    private void mostrarPosicion(Location loc) {
        if(loc != null) {
            setLatitud( String.valueOf(loc.getLatitude()));
            setLongitud(String.valueOf(loc.getLongitude()));
        }else {
            setLatitud( "sin datos" );
            setLongitud("sin datos");
        }
    }

    public  void comenzarLocalizacion() {
        locManager = (LocationManager) context.getSystemService(context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission( context, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions( (Activity) context,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    MY_PERMISSIONS_REQUEST_READ_CONTACTS);
            return;
        }
        Location loc = locManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        mostrarPosicion(loc);
        locListener = new LocationListener() {
            public void onLocationChanged(Location location) { mostrarPosicion(location); }
            public void onProviderDisabled(String provider) {  }
            public void onProviderEnabled(String provider) {  }
            public void onStatusChanged(String provider, int status, Bundle extras) {  }
        };
        locManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 0, locListener);
        if(getLatitud().equals("sin datos") || getLongitud().equals("sin datos")  || getLatitud().equals("") || getLongitud().equals("")   ) {
            Toast.makeText(context, "ACTIVA TU GPS", Toast.LENGTH_SHORT).show();
        }
        else if(!getLatitud().equals("sin datos") && !getLongitud().equals("sin datos")  && !getLatitud().equals("") && !getLongitud().equals("") )
        {   if(control==1){
            et_latitud.setText( getLatitud() );
            et_longitud.setText( getLongitud() );
            }else countDownTimer();
            Toast.makeText(context, "LISTO CONTINUA", Toast.LENGTH_SHORT).show();
        }
    }
    ///////////Se ejecuta cada 30 min y actualiza la localizacion del dispositivo
     //1 segundos=1 000    30min=1800segundos=1800000
    private void countDownTimer(){
        new CountDownTimer(1800000, 1000) {
            public void onTick(long millisUntilFinished) {
            }
            public void onFinish() {
                DatabaseReference mDatabase= FirebaseDatabase.getInstance().getReference();
                DateFormat fecha=new java.text.SimpleDateFormat("yyyy_MM_dd_HH_mm_ss");
                mDatabase.child( "users" ).child( id ).child("geocoor").child(fecha.format(new java.util.Date())).child("latitud").setValue(getLatitud());
                mDatabase.child( "users" ).child( id ).child("geocoor").child(fecha.format(new java.util.Date())).child("longitud").setValue(getLongitud());
                countDownTimer();
            }
        }.start();
    }

    public String getLatitud() {
        return latitud;
    }

    public void setLatitud(String latitud) {
        this.latitud = latitud;
    }

    public String getLongitud() {
        return longitud;
    }

    public void setLongitud(String longitud) {
        this.longitud = longitud;
    }
}

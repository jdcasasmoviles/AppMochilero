package com.jdcasas.appmochilero;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.view.View;

public class RegistroActivity extends AppCompatActivity {
    EditText et_apodo,et_email,et_password,et_longitud,et_latitud;
    Button bt_registrar,ButtonReset,ButtonVolver,btnLlenarsGPS;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_registro );
        //EDITTEXT
        this.et_apodo = (EditText) findViewById(R.id.et_apodo);
        this.et_email = (EditText) findViewById(R.id.et_email);
        this.et_password = (EditText) findViewById(R.id.et_password);
        this.et_longitud = (EditText) findViewById(R.id.et_longitud);
        this.et_latitud= (EditText) findViewById(R.id.et_latitud);
        //BOTON
        this.bt_registrar= (Button) findViewById(R.id.bt_registrar);
        this.ButtonReset = (Button) findViewById(R.id.ButtonReset);
        this.ButtonVolver = (Button) findViewById(R.id.ButtonVolver);
        this.btnLlenarsGPS = (Button) findViewById(R.id.btnLlenarsGPS);
        //BOTON ACTUALIZAR GPS O ACTIVAR
        btnLlenarsGPS = (Button) findViewById(R.id.btnLlenarsGPS);
        btnLlenarsGPS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Geolocalizacion gps=new Geolocalizacion(RegistroActivity.this,et_latitud,et_longitud);
                gps.comenzarLocalizacion();
            }
        });
        //BOTON PARA REGISTRAR
        bt_registrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String apodo=et_apodo.getText().toString().trim();
               String email=et_email.getText().toString().trim();
               String password=et_password.getText().toString().trim();
                String latitud=et_latitud.getText().toString().trim();
                String longitud=et_longitud.getText().toString().trim();
                Usuarios userMochilero=new Usuarios(RegistroActivity.this,apodo,email,password,latitud,longitud);
                userMochilero.registrar();
            }
        });
        //BOTON RESET
        ButtonReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                et_apodo.setText("");
                et_email.setText("");
                et_password.setText("");
            }
        });
        //BOTON VOLVER
        ButtonVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public void onStart(){
        super.onStart();
    }

}



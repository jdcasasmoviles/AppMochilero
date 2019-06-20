package com.jdcasas.appmochilero;
import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.text.DateFormat;
public class Usuarios {
    private String id;
    private String apodo;
    private String email;
    private String password;
    private String latitud;// x
    private String longitud;//y
    Context context;
    DialogKachuelo dialogo;
     FirebaseAuth mAuth;

    public Usuarios(Context context, String apodo, String email, String password, String latitud, String longitud) {
        this.id = id;
        this.apodo = apodo;
        this.email = email;
        this.password = password;
        this.latitud = latitud;
        this.longitud = longitud;
        this.context = context;
        dialogo=new DialogKachuelo( context );
    }

    public void registrar(){
        if(TextUtils.isEmpty(apodo)) dialogo.cargarDialogo( "COLOCAR APODO" );
        else if(TextUtils.isEmpty(email)) dialogo.cargarDialogo( "COLOCAR CORREO" );
        else if(TextUtils.isEmpty(password)) dialogo.cargarDialogo( "COLOCAR PASSWORD" );
        else if(password.length()<6) dialogo.cargarDialogo( "EL PASSWORD DEBE TENER AL MENOS 6 CARACTERES");
        else {// Initialize Firebase Auth
            final FirebaseAuth mAuth = FirebaseAuth.getInstance();
            mAuth.createUserWithEmailAndPassword( email, password )
                    .addOnCompleteListener( (Activity) context, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {//CREACION DE USUARIO EXITOSA
                                insertarUsuario( mAuth );
                            } else
                                dialogo.cargarDialogo( "ESTE CORREO  YA FUE USADO" );
                        }
                    } );
        }
    }

    private void insertarUsuario( FirebaseAuth mAuth){
        DatabaseReference mDatabase= FirebaseDatabase.getInstance().getReference();
        DateFormat fecha=new java.text.SimpleDateFormat("yyyy_MM_dd_HH_mm_ss");
        String id=mAuth.getCurrentUser().getUid();
        mDatabase.child( "users" ).child( id ).child("apodo").setValue( getApodo() );
        mDatabase.child( "users" ).child( id ).child("email").setValue( getEmail());
        mDatabase.child( "users" ).child( id ).child("password").setValue( getPassword());
        mDatabase.child( "users" ).child( id ).child("geocoor").child(fecha.format(new java.util.Date())).child("latitud").setValue(getLatitud());
        mDatabase.child( "users" ).child( id ).child("geocoor").child(fecha.format(new java.util.Date())).child("longitud").setValue(getLongitud());
        dialogo.cargarDialogo( "GRACIAS  POR REGISTRARTE" );
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getApodo() {
        return apodo;
    }

    public void setApodo(String apodo) {
        this.apodo = apodo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

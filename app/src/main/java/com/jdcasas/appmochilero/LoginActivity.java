package com.jdcasas.appmochilero;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.view.View;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {
    EditText et_usuario,et_password;
    Button ButtonIngresar,ButtonRegistrate;
    Intent intent;
    DatabaseReference myRef;
     FirebaseAuth lauth;
     DialogKachuelo dialogo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_login );
        et_usuario = (EditText) findViewById(R.id.et_usuario);
        et_password = (EditText) findViewById(R.id.et_password);
        ButtonIngresar = (Button) findViewById(R.id.ButtonIngresar);
        ButtonRegistrate = (Button) findViewById(R.id.ButtonRegistrate);
        ButtonIngresar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                ingresar();
            }
        });
        ButtonRegistrate.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                intent = new Intent( LoginActivity.this, RegistroActivity.class);
                startActivity(intent);
           }
        });
    }

    public void ingresar(){
        dialogo=new DialogKachuelo( LoginActivity.this);
        String usuario=et_usuario.getText().toString();
       String password=et_password.getText().toString();
        if(TextUtils.isEmpty(usuario)) dialogo.cargarDialogo( "COLOCAR CORREO" );
        else if(TextUtils.isEmpty(password)) dialogo.cargarDialogo( "COLOCAR PASSWORD" );
        else if(password.length()<6) dialogo.cargarDialogo( "EL PASSWORD DEBE TENER AL MENOS 6 CARACTERES");
        else {
            lauth = FirebaseAuth.getInstance();
            lauth.signInWithEmailAndPassword( usuario, password )
                    .addOnCompleteListener( LoginActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                enviarDatos();
                            }
                            else dialogo.cargarDialogo( "CORREO O PASSWORD INCORRECTOS" );
                        }
                    } );
        }
    }

    public void enviarDatos(){
        String id=lauth.getCurrentUser().getUid();
        myRef = FirebaseDatabase.getInstance().getReference().child("users").child(id);
        ValueEventListener eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Geolocalizacion gpsTemporal=new  Geolocalizacion(LoginActivity.this,lauth.getCurrentUser().getUid() );
                gpsTemporal.comenzarLocalizacion();
                String apodo=dataSnapshot.child("apodo").getValue().toString();
                intent = new Intent( LoginActivity.this, MainActivity.class );
                intent.putExtra( "id_usuario",lauth.getCurrentUser().getUid() );
                intent.putExtra( "creadoPor",apodo );
                startActivity( intent );
                finish();
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                dialogo.cargarDialogo( "ERROR "+ databaseError.toString());
            }
        };
        myRef.addListenerForSingleValueEvent(eventListener);
    }
}

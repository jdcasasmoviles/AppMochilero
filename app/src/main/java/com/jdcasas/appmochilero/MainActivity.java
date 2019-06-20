package com.jdcasas.appmochilero;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    String  id_usuario,creadoPor;
    Bundle extras;
    DialogKachuelo dialogo;
    RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );
       recibeDatosActividad();
        dialogo=new DialogKachuelo( MainActivity.this);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view_paisajes);
        recyclerView.setHasFixedSize( true );
        recyclerView.setLayoutManager(new LinearLayoutManager( MainActivity.this ));
        loadPaisajes();
    }

    public void recibeDatosActividad(){
        extras = getIntent().getExtras();
        if (extras != null) {
            id_usuario  = extras.getString("id_usuario");//id user
            creadoPor  = extras.getString("creadoPor");//apodo
        }else finish();
    }

    public  void loadPaisajes(){
        final List<Lugares>  LugaresList=new ArrayList<>(  );
        DatabaseReference myRef = FirebaseDatabase.getInstance().getReference().child("lugares");
        ValueEventListener eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot nodo : dataSnapshot.getChildren()) {
                    String key = nodo.getKey();
                    LugaresList.add( new Lugares(
                            dataSnapshot.child( key ).child("descripcion").getValue().toString(),
                            dataSnapshot.child( key ).child("imagen1").getValue().toString(),
                            dataSnapshot.child( key ).child("imagen2").getValue().toString(),
                            dataSnapshot.child( key ).child("creadoPor").getValue().toString()
                    ) );
                }
                RecyclerAdapterLugares adapter_ra=new RecyclerAdapterLugares( MainActivity.this,LugaresList );
                recyclerView.setAdapter(adapter_ra);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {}
        };
        myRef.addListenerForSingleValueEvent(eventListener);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.Compartir:
               Intent  intent = new Intent( MainActivity.this, CompartirActivity.class );
                intent.putExtra( "id_usuario",id_usuario);
                intent.putExtra( "creadoPor",creadoPor );
                startActivity( intent );
                finish();
                return true;
            case R.id.Acerca:
                dialogo.cargarDialogo( getResources().getString(R.string.mensajeAcercaDe) );;
                return true;
            case R.id.Salir:
                Toast.makeText(getApplicationContext(), "Saliendo.... ", Toast.LENGTH_LONG).show();
                finish();
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }
}

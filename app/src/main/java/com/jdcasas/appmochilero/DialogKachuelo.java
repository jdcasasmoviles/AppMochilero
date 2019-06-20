package com.jdcasas.appmochilero;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
public class DialogKachuelo {
    Context  context;
    private String latitud="";
    private String longitud="";
    private String opcion="";
    public DialogKachuelo(Context context){
        this.context=context;
    }

    public void cargarDialogo(String mensaje){
        final AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("INFO")
                .setMessage(mensaje)
                .setPositiveButton("Aceptar",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });
        builder.setIcon(R.drawable.ic_menu_white_24dp);
        builder.create().show();
    }
}

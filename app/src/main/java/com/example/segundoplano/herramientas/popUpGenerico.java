package com.example.segundoplano.herramientas;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

public class popUpGenerico {

    public popUpGenerico(Context context,String mensaje,String titulo) {
        super();
        AlertDialog.Builder builder= new AlertDialog.Builder(context);
        builder.setMessage(mensaje)
        .setTitle(titulo)
        .setNegativeButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        builder.show();
    }
}

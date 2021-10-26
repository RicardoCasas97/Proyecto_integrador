package com.example.segundoplano.Adapters;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.segundoplano.R;

public class HolderMensajes extends RecyclerView.ViewHolder {
    private TextView nombre,mensaje,hora;
    private ImageView foto;

    public HolderMensajes(@NonNull View itemView) {
        super(itemView);
        foto = itemView.findViewById(R.id.fotoPerfil);
        nombre= itemView.findViewById(R.id.nombre);
        mensaje = itemView.findViewById(R.id.mensaje);
        hora = itemView.findViewById(R.id.hora);
    }

    public TextView getNombre() {
        return nombre;
    }

    public void setNombre(TextView nombre) {
        this.nombre = nombre;
    }

    public TextView getMensaje() {
        return mensaje;
    }

    public void setMensaje(TextView mensaje) {
        this.mensaje = mensaje;
    }

    public TextView getHora() {
        return hora;
    }

    public void setHora(TextView hora) {
        this.hora = hora;
    }

    public ImageView getFoto() {
        return foto;
    }

    public void setFoto(ImageView foto) {
        this.foto = foto;
    }
}

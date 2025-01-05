package com.example.login;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class UsuariosAdapter extends ArrayAdapter<Usuario> {
    private Context mContext;
    private int mResource;

    public UsuariosAdapter(Context context, int resource, ArrayList<Usuario> usuarios) {
        super(context, resource, usuarios);
        mContext = context;
        mResource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(mResource, parent, false);
        }

        TextView textViewUsuario = convertView.findViewById(R.id.id1);
        TextView textViewContrasena = convertView.findViewById(R.id.nombre);

        Usuario usuario = getItem(position);
        if (usuario != null) {
            textViewUsuario.setText(usuario.nombre);
            textViewContrasena.setText(usuario.contrasena);
        }

        return convertView;
    }
}
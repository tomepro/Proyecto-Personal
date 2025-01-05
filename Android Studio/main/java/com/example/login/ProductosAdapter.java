package com.example.login;


import static android.content.Intent.getIntent;

import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;

import com.squareup.picasso.Picasso;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class ProductosAdapter extends ArrayAdapter<Producto> {
    private Context mContext;
    private int mResource;

    //String emailUsuario = getIntent().getStringExtra("EMAIL_USUARIO");

    public ProductosAdapter(Context context, int resource, ArrayList<Producto> productos) {
        super(context, resource, productos);
        mContext = context;
        mResource = resource;



    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(mResource, parent, false);
        }

        Button cestaS = convertView.findViewById(R.id.cestaS);
        Button cestaM = convertView.findViewById(R.id.cestaM);
        Button cestaL = convertView.findViewById(R.id.cestaL);

        //String emailUsuario = getIntent().getStringExtra("EMAIL_USUARIO");

        TextView textViewId = convertView.findViewById(R.id.id1);
        TextView textViewNombre = convertView.findViewById(R.id.nombre);
        TextView textViewPrecio = convertView.findViewById(R.id.precio);
        TextView textViewStock = convertView.findViewById(R.id.stock);

        String imageUrl = "https://jgc.cat/wp-content/uploads/2021/01/JGiC20200223-052.jpg";
        ImageView imageView = convertView.findViewById(R.id.miImageView);

        Producto producto = getItem(position);
        //if (producto != null) {
        //    textViewId.setText(producto.id);
        //    textViewNombre.setText(producto.nombre);
        //    textViewPrecio.setText(producto.precio);
        //    textViewStock.setText(producto.stock);
        //}

        if (producto != null) {
            Picasso.get()
                    .load(imageUrl)
                    .into(imageView);
            textViewId.setText(String.valueOf(producto.id));
            textViewNombre.setText(producto.nombre);
            textViewPrecio.setText(String.valueOf(producto.precio));
            textViewStock.setText(String.valueOf(producto.stock));
        }
        cestaS.setOnClickListener(v -> enviarDatosAlServidor(producto.id, "S",producto.precio));
        cestaM.setOnClickListener(v -> enviarDatosAlServidor(producto.id, "M",producto.precio));
        cestaL.setOnClickListener(v -> enviarDatosAlServidor(producto.id, "L",producto.precio));


        return convertView;
    }
    private void enviarDatosAlServidor(int productoId, String talla, int precio) {
        new InsertarProductoTask().execute(productoId, talla, precio);
        showDialogOptions("Producto añadido con exito!");
    }
    private class InsertarProductoTask extends AsyncTask<Object, Void, String> {


        @Override
        protected String doInBackground(Object... params) {
            int productoId = (int) params[0];
            String talla = (String) params[1];
            int precio = (int) params[2];
            String urlStr = "http://10.0.2.2/post_cesta.php";
            String resultado;

            try {
                URL url = new URL(urlStr);
                HttpURLConnection conexion = (HttpURLConnection) url.openConnection();
                conexion.setRequestMethod("POST");
                conexion.setDoOutput(true);



                String datos = "&producto_id=" + productoId + "&usuario_email=" + HomeActivity.emailUsuario + "&talla=" + talla + "&precio=" + precio;

                OutputStream salida = conexion.getOutputStream();
                byte[] bytes = datos.getBytes(StandardCharsets.UTF_8);
                salida.write(bytes);
                salida.flush();
                salida.close();

                InputStream entrada = conexion.getInputStream();
                BufferedReader lector = new BufferedReader(new InputStreamReader(entrada));
                StringBuilder respuesta = new StringBuilder();
                String linea;

                while ((linea = lector.readLine()) != null) {
                    respuesta.append(linea);
                }
                entrada.close();
                conexion.disconnect();

                resultado = respuesta.toString();


            } catch (Exception e) {
                e.printStackTrace();
                return "Error al insertar el producto";
            }

            return resultado;
        }



    }
    private void showDialogOptions(String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(ProductosAdapter.this.getContext());
        builder.setTitle("");
        builder.setMessage(message);

        builder.setNegativeButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        builder.create().show();
    }
}
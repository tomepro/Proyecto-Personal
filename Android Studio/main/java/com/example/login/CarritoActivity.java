package com.example.login;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class CarritoActivity extends AppCompatActivity {



    //AQUI DA ERROR**************
    //String usuarioSesion = getIntent().getStringExtra("usersesion");

    private ListView listView;
    private String emailUsuario;
    private String usersesion; // Aquí guardaremos el email del usuario en sesión

    private ImageButton Pedidos;

    private Button Comprar;

    private ImageButton Account;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carrito);

        listView = findViewById(R.id.listaCarrito);
        usersesion = getIntent().getStringExtra("usersesion"); // Obtener el email desde el Intent
        emailUsuario = getIntent().getStringExtra("usersesion");

        Pedidos = findViewById(R.id.imageButtonPedidos);
        Account = findViewById(R.id.imageButtonAccount);
        Comprar = findViewById(R.id.Comprar);


        Pedidos.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(CarritoActivity.this, HomeActivity.class);
                i.putExtra("usersesion", emailUsuario);
                startActivity(i);
            }
        });

        Account.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(CarritoActivity.this, AccountActivity.class);
                i.putExtra("usersesion", emailUsuario);
                startActivity(i);
            }
        });

        Comprar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(CarritoActivity.this, PedidosActivity.class);
                i.putExtra("usersesion", emailUsuario);
                startActivity(i);
            }
        });

        new VerProductos().execute(); // Iniciar la tarea de obtener los productos
    }

    // AsyncTask para obtener los productos desde el servidor
    private class VerProductos extends AsyncTask<Void, Void, ArrayList<Producto>> {

        @Override
        protected ArrayList<Producto> doInBackground(Void... params) {
            ArrayList<Producto> productList = new ArrayList<>();
            try {
                // URL de la solicitud
                URL url = new URL("http://10.0.2.2/get_cesta.php");
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("POST");
                urlConnection.setDoOutput(true);  // Permitir enviar datos en el cuerpo de la solicitud

                // Enviar la variable 'usersesion'
                //String usersesion = usuarioSesion;  // Este valor debe ser el email de la sesión activa del usuario
                String usersesion = emailUsuario;
                String postData = "usersesion=" + URLEncoder.encode(usersesion, "UTF-8");

                // Enviar los datos en la solicitud
                OutputStream os = urlConnection.getOutputStream();
                os.write(postData.getBytes("UTF-8"));
                os.close();

                // Verificar respuesta HTTP
                int responseCode = urlConnection.getResponseCode();
                if (responseCode != HttpURLConnection.HTTP_OK) {
                    Log.e("VerProductos", "Error en la respuesta del servidor: " + responseCode);
                    return productList;
                }

                // Procesar la respuesta
                InputStream inputStream = urlConnection.getInputStream();
                String jsonString = convertInputStreamToString(inputStream);
                Log.d("Respuesta Completa", jsonString);  // Depuración

                // Parsear el JSON

                    JSONArray jsonArray = new JSONArray(jsonString);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        int id = jsonObject.getInt("id");
                        String nombre = jsonObject.getString("nombre");
                        int precio = jsonObject.getInt("precio");
                        int stock = jsonObject.getInt("stock");

                        Producto producto = new Producto(id, nombre, precio, stock);
                        productList.add(producto);
                    }


                // Cerrar recursos
                inputStream.close();
                urlConnection.disconnect();

            } catch (IOException | JSONException e) {
                Log.e("VerProductos", "Error al obtener productos: " + e.getMessage(), e);
            }

            return productList;
        }




        @Override
        protected void onPostExecute(ArrayList<Producto> productList) {
            super.onPostExecute(productList);

            if (productList != null && !productList.isEmpty()) {
                // Crear un adaptador y configurar el ListView
                ProductosAdapter adapter = new ProductosAdapter(CarritoActivity.this, R.layout.list_product, productList);
                listView.setAdapter(adapter);
            } else {
                showDialogOptions("No se pudieron obtener los productos.");
            }
        }
    }

    // Método para convertir el InputStream a String
    private String convertInputStreamToString(InputStream inputStream) {
        StringBuilder stringBuilder = new StringBuilder();
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line).append('\n');
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }

    // Método para mostrar un cuadro de diálogo
    private void showDialogOptions(String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(CarritoActivity.this);
        builder.setTitle("Aviso");
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


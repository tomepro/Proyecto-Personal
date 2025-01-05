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
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {

    public ListView listView;
    public Button refresh;
    public ImageButton returnHome;
    public static String emailUsuario;

    public ImageButton cestaCompra;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        listView = findViewById(R.id.listaCarrito);
        refresh = findViewById(R.id.refreshHome);
        returnHome = findViewById(R.id.imageButtonAccountHome);
        cestaCompra = findViewById(R.id.imageButtonCestaHome);

        emailUsuario = getIntent().getStringExtra("usersesion");

        TextView emailTextView = findViewById(R.id.welcomeUser);
        emailTextView.setText(emailUsuario);

        refresh.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                new VerProductos().execute();
            }
        });

        returnHome.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(HomeActivity.this, AccountActivity.class);
                i.putExtra("usersesion", emailUsuario);
                startActivity(i);
            }
        });

        cestaCompra.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(HomeActivity.this, CarritoActivity.class);
                i.putExtra("usersesion", emailUsuario);
                startActivity(i);
            }
        });

        new VerProductos().execute();
    }

    private class VerProductos extends AsyncTask<Void, Void, ArrayList<Producto>> {

        @Override
        protected ArrayList<Producto> doInBackground(Void... params) {
            ArrayList<Producto> productList = new ArrayList<>();

            try {
                // Conexión al servidor
                URL url = new URL("http://10.0.2.2/get_productos.php");
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.setConnectTimeout(5000); // Tiempo de espera de conexión
                urlConnection.setReadTimeout(5000);    // Tiempo de espera de lectura

                // Verificar respuesta HTTP
                int responseCode = urlConnection.getResponseCode();
                if (responseCode != HttpURLConnection.HTTP_OK) {
                    Log.e("VerProductos", "Error en la respuesta del servidor: " + responseCode);
                    return productList;
                }

                // Procesar InputStream
                InputStream inputStream = urlConnection.getInputStream();
                String jsonString = convertInputStreamToString(inputStream);

                // Parsear JSON
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
                com.example.login.ProductosAdapter adapter = new com.example.login.ProductosAdapter(HomeActivity.this, R.layout.list_product, productList);
                listView.setAdapter(adapter);
                Log.i("VerProductos", "Productos cargados exitosamente: " + productList.size());
            } else {
                showDialogOptions("No se pudieron obtener los datos. Verifica el servidor o el archivo.");
                Log.e("VerProductos", "Lista de productos vacía o nula.");
            }
        }
    }

    private void showDialogOptions(String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(HomeActivity.this);
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
}

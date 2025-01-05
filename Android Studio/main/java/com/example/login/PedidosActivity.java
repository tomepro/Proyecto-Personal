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

public class PedidosActivity extends AppCompatActivity {

    public ListView listView;
    private ArrayList<Pedido> pedidos;
    private PedidoAdapter pedidoAdapter;
    private String emailUsuario; // Email del usuario logueado

    private Button Finalizar;

    private Button refresh;

    private ImageButton Pedidoss;

    public ImageButton cestaCompra;

    private ImageButton Account;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pedido); // Asegúrate de que el layout sea el correcto

        // Obtener el correo del usuario desde la actividad anterior
        emailUsuario = getIntent().getStringExtra("usersesion"); // Obtener el email del usuario logueado

        refresh = findViewById(R.id.refreshPedidos);

        Finalizar = findViewById(R.id.Terminar);

        // Inicializar la lista y el ListView
        listView = findViewById(R.id.listaPedidos); // Asegúrate de que el ID sea el correcto
        pedidos = new ArrayList<>();
        pedidoAdapter = new PedidoAdapter(this, R.layout.list_pedido, pedidos);
        //listaPedidos.setAdapter(pedidoAdapter);

        Pedidoss = findViewById(R.id.imageButtonPedidosPedidos);
        Account = findViewById(R.id.imageButtonAccountPedidos);
        cestaCompra = findViewById(R.id.imageButtonCestaPedidos);

        Pedidoss.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(PedidosActivity.this, HomeActivity.class);
                i.putExtra("usersesion", emailUsuario);
                startActivity(i);
            }
        });

        Account.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(PedidosActivity.this, AccountActivity.class);
                i.putExtra("usersesion", emailUsuario);
                startActivity(i);
            }
        });

        cestaCompra.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(PedidosActivity.this, CarritoActivity.class);
                i.putExtra("usersesion", emailUsuario);
                startActivity(i);
            }
        });

        Finalizar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(PedidosActivity.this, FinalActivity.class);
                i.putExtra("usersesion", emailUsuario);
                startActivity(i);
            }
        });

        refresh.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(PedidosActivity.this, PedidosActivity.class);
                i.putExtra("usersesion", emailUsuario);
                startActivity(i);
            }
        });

        // Llamada a la función para obtener los pedidos
        new ObtenerPedidosTask().execute();
    }

    private class ObtenerPedidosTask extends AsyncTask<Void, Void, ArrayList<Pedido>> {

        @Override
        protected ArrayList<Pedido> doInBackground(Void... params) {
            ArrayList<Pedido> listaPedidos = new ArrayList<>();

            try {
                // Conexión al servidor
                URL url = new URL("http://10.0.2.2/get_pedido.php");
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.setConnectTimeout(5000); // Tiempo de espera de conexión
                urlConnection.setReadTimeout(5000);    // Tiempo de espera de lectura

                // Verificar respuesta HTTP
                int responseCode = urlConnection.getResponseCode();
                if (responseCode != HttpURLConnection.HTTP_OK) {
                    Log.e("VerPedidos", "Error en la respuesta del servidor: " + responseCode);
                    return listaPedidos;
                }

                // Procesar InputStream
                InputStream inputStream = urlConnection.getInputStream();
                String jsonString = convertInputStreamToString(inputStream);

                // Parsear JSON principal
                JSONObject jsonObject = new JSONObject(jsonString);

                // Verificar el estado de la respuesta
                String status = jsonObject.getString("status");
                if (!"success".equals(status)) {
                    Log.e("VerPedidos", "Estado de la respuesta no exitoso: " + status);
                    return listaPedidos;
                }

                // Obtener el JSONArray "data"
                JSONArray dataArray = jsonObject.getJSONArray("data");
                for (int i = 0; i < dataArray.length(); i++) {
                    JSONObject pedidoObject = dataArray.getJSONObject(i);

                    // Extraer los datos del JSON
                    String productoId = pedidoObject.getString("producto_id");
                    String productoNombre = pedidoObject.getString("producto_nombre");
                    String talla = pedidoObject.getString("talla");
                    int cantidad = pedidoObject.getInt("cantidad");
                    int precioTotal = pedidoObject.getInt("precio_total");

                    // Crear un objeto Pedido
                    Pedido pedido = new Pedido(productoId, productoNombre, talla, cantidad, precioTotal);
                    // Agregar el pedido a la lista
                    listaPedidos.add(pedido);
                }

                // Cerrar recursos
                inputStream.close();
                urlConnection.disconnect();

            } catch (IOException | JSONException e) {
                Log.e("VerPedidos", "Error al obtener productos: " + e.getMessage(), e);
            }

            return listaPedidos;
        }


        @Override
        protected void onPostExecute(ArrayList<Pedido> listaPedidos) {
            super.onPostExecute(listaPedidos);

            if (listaPedidos != null && !listaPedidos.isEmpty()) {
                // Configurar el adaptador y cargar los datos en el ListView
                com.example.login.PedidoAdapter adapter = new com.example.login.PedidoAdapter(PedidosActivity.this, R.layout.list_pedido, listaPedidos);
                listView.setAdapter(adapter);
                Log.i("VerProductos", "Productos cargados exitosamente: " + listaPedidos.size());

                // Calcular el total
                int total = 0;
                for (Pedido pedido : listaPedidos) {
                    total += pedido.getPrecioTotal(); // Asegúrate de que el metodo getPrecioTotal esté correctamente implementado en la clase Pedido
                }

                // Mostrar el total en el TextView
                TextView textViewTotal = findViewById(R.id.text_view_total);
                textViewTotal.setText("Total: " + total + " $");
            } else {
                // Mostrar un diálogo si no se pudieron obtener los datos
                showDialogOptions("No se pudieron obtener los datos. Verifica el servidor o el archivo.");
                Log.e("VerProductos", "Lista de productos vacía o nula.");
            }
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

        private void showDialogOptions(String message) {
            AlertDialog.Builder builder = new AlertDialog.Builder(PedidosActivity.this);
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
}

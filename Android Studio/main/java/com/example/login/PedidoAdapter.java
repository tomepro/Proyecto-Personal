package com.example.login;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
//import androidx.tracing.perfetto.handshake.protocol.Response;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;  // Para Volley
import com.android.volley.VolleyError;  // Para Volley Error Listener

import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.androidgamesdk.gametextinput.Listener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class PedidoAdapter extends ArrayAdapter<Pedido> {
    private Context mContext;
    private int mResource;
    private ArrayList<Pedido> mPedidos;

    public PedidoAdapter(Context context, int resource, ArrayList<Pedido> pedidos) {
        super(context, resource, pedidos);
        mContext = context;
        mResource = resource;
        mPedidos = pedidos;
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(mResource, parent, false);
        }

        // Inicializar los componentes de la vista
        TextView textViewProductoId = convertView.findViewById(R.id.producto_id);
        TextView textViewProductoNombre = convertView.findViewById(R.id.producto_nombre);
        TextView textViewProductoTalla = convertView.findViewById(R.id.talla);
        TextView textViewProductoCantidad = convertView.findViewById(R.id.cantidad);
        TextView textViewProductoPrecio = convertView.findViewById(R.id.precio_total);
        Button btnEliminar = convertView.findViewById(R.id.btnEliminar); // Botón para eliminar

        final Pedido pedido = getItem(position);
        if (pedido != null) {
            // Asignar los datos del pedido a las vistas
            textViewProductoId.setText(pedido.producto_id);
            textViewProductoNombre.setText(pedido.producto_nombre);
            textViewProductoTalla.setText(pedido.talla);
            textViewProductoCantidad.setText(String.valueOf(pedido.cantidad));
            textViewProductoPrecio.setText(String.valueOf(pedido.precio_total));

            // Configuración del botón de eliminar
            btnEliminar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    eliminarPedido(pedido.producto_id, position);
                }
            });
        }

        return convertView;
    }

    // Metodo para eliminar el pedido
    private void eliminarPedido(final String productoId, final int position) {
        // Realizar una solicitud POST para eliminar el pedido
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://10.0.2.2/delete_pedidos.php",  // Asegúrate de que esta URL esté correcta
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            // Analizar la respuesta JSON del servidor
                            JSONObject jsonResponse = new JSONObject(response);
                            String status = jsonResponse.getString("status");
                            String message = jsonResponse.getString("message");

                            if (status.equals("success")) {
                                // Eliminar el pedido de la lista y actualizar la vista
                                mPedidos.remove(position);
                                notifyDataSetChanged();
                                Toast.makeText(mContext, "Pedido eliminado", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(mContext, "Error al procesar la respuesta", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Manejar el error de la solicitud
                        Toast.makeText(mContext, "Error en la conexión", Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected java.util.Map<String, String> getParams() {
                // Pasar el producto_id al servidor
                java.util.Map<String, String> params = new java.util.HashMap<>();
                params.put("producto_id", productoId);
                return params;
            }
        };

        // Crear la cola de solicitudes de Volley y añadir la solicitud
        RequestQueue requestQueue = Volley.newRequestQueue(mContext);
        requestQueue.add(stringRequest);
    }
}

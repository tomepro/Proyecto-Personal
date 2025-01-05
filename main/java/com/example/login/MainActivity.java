package com.example.login;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.login.db.dbLogin;

import com.google.android.material.textfield.TextInputLayout;
import com.google.android.material.textfield.TextInputEditText;

import org.w3c.dom.Document;
import org.xml.sax.InputSource;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class MainActivity extends AppCompatActivity {


    public Button login;
    public TextInputEditText usr;
    public Button wrong;
    public Button resetpassword;

    public TextInputEditText passwd;

    String id;
    String user;
    String contrasena;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        usr = findViewById(R.id.username);
        passwd = findViewById(R.id.password_account);
        login = findViewById(R.id.login_button);
        wrong = findViewById(R.id.viewAttemptsButton);
        resetpassword = findViewById(R.id.forgotpasswd);

        resetpassword.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, ResetPasswordActivity.class);
                startActivity(i);
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String username = usr.getText().toString();
                String password = passwd.getText().toString();
                if (username.isEmpty() || password.isEmpty()) {
                    showDialogOptions("Alguno de los campos está vacío.");
                } else if (password.length() < 4) {
                    showDialogOptions("La contraseña debe tener una longitud de minimo 4 caracteres.");
                } else if (password.length() > 12) {
                    showDialogOptions("La contraseña debe tener una longitud de maximo 12 caracteres.");
                } else {



                    new ValidarUsuarioTask().execute(username, password);

                }

            }

        });

        // Lógica para el botón de ver intentos fallidos
        wrong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

    }

    private void showDialogOptions(String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
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
    private static Document convertirStringToXMLDocument(String xmlString) {
        Log.d("RespuestaServidor", xmlString);

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = null;
        try {
            builder = factory.newDocumentBuilder();
            Document doc = builder.parse(new InputSource(new StringReader(xmlString)));
            return doc;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private class ValidarUsuarioTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            user = params[0];
            contrasena = params[1];
            String url = "http://10.0.2.2/validacuenta.php";
            String resultado = null;
            try {
                URL direccion = new URL(url);
                HttpURLConnection conexion = (HttpURLConnection) direccion.openConnection();
                conexion.setRequestMethod("POST");
                conexion.setDoOutput(true);
                String datos = "email=" + user + "&password=" + contrasena;

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
            }
            return resultado;
        }

        @Override
        protected void onPostExecute(String resultado) {
            if (resultado == null) {
                Toast.makeText(MainActivity.this, "Error en la conexión con el servidor", Toast.LENGTH_SHORT).show();
                return;
            }

            resultado = resultado.trim(); // Elimina espacios en blanco o saltos de línea

            if (resultado.equals("valid")) {
                // Acceso permitido
                Toast.makeText(MainActivity.this, "Inicio de sesión exitoso", Toast.LENGTH_SHORT).show();
                abrirNuevaActividad(true); // Enviar a HomeActivity




            } else if (resultado.equals("invalid")) {
                // Acceso denegado
                Toast.makeText(MainActivity.this, "Usuario o contraseña incorrectos", Toast.LENGTH_SHORT).show();
                registrarIntentoFallido(user, contrasena); // Registrar intento fallido
                abrirNuevaActividad(false); // Enviar a AttemptErrorActivity
            } else {
                // Manejo de respuestas inesperadas
                Log.e("ErrorServidor", "Respuesta inesperada del servidor: " + resultado);
                Toast.makeText(MainActivity.this, "Error en el servidor", Toast.LENGTH_SHORT).show();
            }
        }

        private void abrirNuevaActividad(boolean isValidUser) {
            Intent intent;
            if (isValidUser) {
                intent = new Intent(MainActivity.this, HomeActivity.class); // Usuario válido
                intent.putExtra("usersesion", user); // Pasa el email a la siguiente actividad
            } else {
                //SI EL USUARIO NO ES CORRECTO REDIRECCIONA A CREAR USUARIO!
                intent = new Intent(MainActivity.this, RegisterActivity.class); // Usuario inválido
            }
            startActivity(intent);
            finish(); // Opcional: cerrar la actividad actual
        }




        private void registrarIntentoFallido(String username, String password) {
            dbLogin dbLogin = new dbLogin(MainActivity.this);
            SQLiteDatabase db = dbLogin.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(dbLogin.COLUMN_USERNAME, username);
            values.put(dbLogin.COLUMN_PASSWORD, password);
            values.put(dbLogin.COLUMN_TIMESTAMP, obtenerHoraActual());
            db.insert(dbLogin.TABLE_ATTEMPTS, null, values);
            db.close();
        }

        private String obtenerHoraActual() {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
            Date date = new Date();
            return sdf.format(date);
        }
    }
}
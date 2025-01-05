package com.example.login;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import com.google.android.material.textfield.TextInputLayout;
import com.google.android.material.textfield.TextInputEditText;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class RegisterActivity extends AppCompatActivity {

    private Button signin;
    private Button createaccount;
    public TextInputEditText usr;
    public TextInputEditText mail;
    public TextInputEditText passwd;
    public TextInputEditText passwd_conf;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        usr = findViewById(R.id.name_user);
        mail = findViewById(R.id.email_user);
        passwd = findViewById(R.id.passwd_user);
        passwd_conf = findViewById(R.id.confirm_passwd);

        createaccount = findViewById(R.id.create_account);
        createaccount.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String username = usr.getText().toString();
                String email = mail.getText().toString();
                String password = passwd.getText().toString();
                String confirm_password = passwd_conf.getText().toString();

                if (username.isEmpty() || password.isEmpty()) {
                    showDialogOptions("Alguno de los campos está vacío.");
                } else if (!email.matches("^[\\w._%+-]+@[\\w.-]+\\.(es|com|org|net)$")) {
                    showDialogOptions("solo se permiten correos con .es, .com");
                } else if (username.length() > 49) {
                    showDialogOptions("Solo se permiten un maximo de 50 caracteres");
                } else if (password.length() < 4) {
                    showDialogOptions("La contraseña debe tener una longitud de mínimo 4 caracteres.");
                } else if (password.length() > 12) {
                    showDialogOptions("La contraseña debe tener una longitud de máximo 12 caracteres.");
                }else if (email.length() > 100) {
                    showDialogOptions("Correo con demasidados caracteres");
                }else if (!password.equals(confirm_password)) {
                    showDialogOptions("Las contraseñas no coinciden");
                } else {
                    new CrearUsuarioTask().execute(username, email, password);
                }
            }
        });

        signin = findViewById(R.id.signin);
        signin.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(RegisterActivity.this, MainActivity.class);
                startActivity(i);
            }
        });
    }

    private class CrearUsuarioTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            String user = params[0];
            String email = params[1];
            String contrasena = params[2];
            String url = "http://10.0.2.2/creacuenta.php";
            String resultado = null;
            try {
                URL direccion = new URL(url);
                HttpURLConnection conexion = (HttpURLConnection) direccion.openConnection();
                conexion.setRequestMethod("POST");
                conexion.setDoOutput(true);
                String datos = "name=" + user + "&email=" + email + "&password=" + contrasena;

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
            if (resultado != null) {
                showDialogOptions("Cuenta creada correctamente.");
            } else {
                showDialogOptions("Error al crear la cuenta.");
            }
        }
    }

    private void showDialogOptions(String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
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

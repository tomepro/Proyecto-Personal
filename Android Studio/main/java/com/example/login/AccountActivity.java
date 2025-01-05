package com.example.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;



public class AccountActivity extends AppCompatActivity {

    public Button refresh;

    public Button byebye;
    public ImageButton returnHome;
    public String emailUsuario;

    public ImageButton cestaCompra;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        refresh = findViewById(R.id.refreshAccount);
        returnHome = findViewById(R.id.imageButtonPedidosAccount);
        cestaCompra = findViewById(R.id.imageButtonCestaAcoount);
        byebye = findViewById(R.id.CloseSession);

        emailUsuario = getIntent().getStringExtra("usersesion");

        TextView emailTextView = findViewById(R.id.welcomeUserLogged);
        emailTextView.setText(emailUsuario);


        returnHome.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(AccountActivity.this, HomeActivity.class);
                i.putExtra("usersesion", emailUsuario);
                startActivity(i);
            }
        });

        byebye.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(AccountActivity.this, MainActivity.class);
                i.putExtra("usersesion", emailUsuario);
                startActivity(i);
            }
        });

        cestaCompra.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(AccountActivity.this, CarritoActivity.class);
                i.putExtra("usersesion", emailUsuario);
                startActivity(i);
            }
        });

    }
}

package com.example.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class FinalActivity extends AppCompatActivity {
    public Button Casa;

    private String emailUsuario;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final);

        Casa = findViewById(R.id.Casa);

        Casa.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(FinalActivity.this, HomeActivity.class);
                i.putExtra("usersesion", emailUsuario);
                startActivity(i);
            }
        });
    }
}

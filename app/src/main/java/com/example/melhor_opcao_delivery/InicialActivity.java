package com.example.melhor_opcao_delivery;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.auth.FirebaseAuth;

public class InicialActivity extends AppCompatActivity {

    private ProgressBar barraPros;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_intro);

        auth = FirebaseAuth.getInstance();
        barraPros = findViewById(R.id.barrapros);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);

            if (auth.getCurrentUser() != null) {

                barraPros.setVisibility(View.VISIBLE);

                startActivity(new Intent(InicialActivity.this, MainActivity.class));
                Toast.makeText(this, "Por favor espere, estamos verificando seus dados!!", Toast.LENGTH_SHORT).show();
                finish();
            }
            return insets;
        });
    }

    public void entraini(View view) {
        startActivity(new Intent(this, LoginActivity.class));
    }

    public void cadastraini(View view) {
        startActivity(new Intent(this, CadastrarActivity.class));
    }
}

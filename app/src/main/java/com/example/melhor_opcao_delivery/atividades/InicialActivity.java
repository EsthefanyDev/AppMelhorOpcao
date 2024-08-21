package com.example.melhor_opcao_delivery.atividades;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.melhor_opcao_delivery.MainActivity;
import com.example.melhor_opcao_delivery.R;
import com.google.firebase.auth.FirebaseAuth;

public class InicialActivity extends AppCompatActivity {

    private ProgressBar barraPros;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_inicial);

        auth = FirebaseAuth.getInstance();
        barraPros = findViewById(R.id.barrapros);

        // Verificar o estado de logout em SharedPreferences
        SharedPreferences preferences = getSharedPreferences("user_prefs", MODE_PRIVATE);
        boolean isLoggedOut = preferences.getBoolean("isLoggedOut", false);

        if (!isLoggedOut && auth.getCurrentUser() != null) {
            barraPros.setVisibility(View.VISIBLE);
            startActivity(new Intent(InicialActivity.this, MainActivity.class));
            Toast.makeText(this, "Por favor espere, estamos verificando seus dados!!", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            // Se o usuário fez logout, redefina o estado de logout para falso
            SharedPreferences.Editor editor = preferences.edit();
            editor.putBoolean("isLoggedOut", false);
            editor.apply();
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    public void entraini(View view) {
        startActivity(new Intent(this, LoginActivity.class));
    }

    public void cadastraini(View view) {
        startActivity(new Intent(this, CadastrarActivity.class));
    }

    // Método para realizar o logout
    public void logout(View view) {
        // Fazer logout do Firebase
        auth.signOut();

        // Redirecionar para a tela inicial (InicialActivity)
        Intent intent = new Intent(InicialActivity.this, InicialActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("fromLogout", true); // Indicar que vem de um logout
        startActivity(intent);
        finish(); // Finaliza a atividade atual
    }
}

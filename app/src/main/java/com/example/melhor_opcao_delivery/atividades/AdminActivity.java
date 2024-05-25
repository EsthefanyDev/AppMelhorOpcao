package com.example.melhor_opcao_delivery.atividades;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.melhor_opcao_delivery.R;

public class AdminActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
    }
    // Método chamado quando o botão "Ver Pedidos" é clicado
    public void viewOrdersButtonClick(View view) {
        startActivity(new Intent(this, ListaPedidosActivity.class));
        Toast.makeText(this, "Abrir lista de pedidos", Toast.LENGTH_SHORT).show();

    }
}

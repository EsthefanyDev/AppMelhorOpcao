package com.example.melhor_opcao_delivery.atividades;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.melhor_opcao_delivery.Model.CardModel;
import com.example.melhor_opcao_delivery.R;

import java.io.Serializable;
import java.util.List;

public class FinalBuyActivity extends AppCompatActivity {

    private TextView valorTotalCompra;
    private Button finalizarCompra;
    private List<CardModel> cardModelList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final_buy);

        // Inicializando componentes
        valorTotalCompra = findViewById(R.id.valorTotalcompra);
        finalizarCompra = findViewById(R.id.finalizar);

        // Recebendo a lista de itens do carrinho
        cardModelList = (List<CardModel>) getIntent().getSerializableExtra("listaItens");

        // Calculando e exibindo o valor total
        calcularValorTotal(cardModelList);

        // Configurando ação do botão de finalizar compra
        finalizarCompra.setOnClickListener(v -> {
            Intent intent = new Intent(FinalBuyActivity.this, PlacedOlderActivity.class);
            intent.putExtra("listaItens", (Serializable) cardModelList);
            startActivity(intent);
        });
    }

    @SuppressLint({"SetTextI18n", "DefaultLocale"})
    private void calcularValorTotal(List<CardModel> cardModelList) {
        double valorTotal = 0.0;
        for (CardModel cardModel : cardModelList){
            valorTotal += cardModel.getPrecoTotal();
        }
        valorTotalCompra.setText("R$" + String.format("%.2f", valorTotal));
    }
}

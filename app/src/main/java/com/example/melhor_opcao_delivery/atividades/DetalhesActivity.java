package com.example.melhor_opcao_delivery.atividades;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.melhor_opcao_delivery.Model.ViewAllModel;
import com.example.melhor_opcao_delivery.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Objects;

public class DetalhesActivity extends AppCompatActivity {

    TextView Quantiadde, detalhePreco, detalheDescri, totalPreco;
    int ttquantidade = 1; // Initial quantity is 1
    ImageView datalheImg, addItem, removeItem;
    Button addToCar;

    FirebaseFirestore firestore;
    FirebaseAuth auth;
    ViewAllModel viewAllModel = null;
    float precoUnitario = 0;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhes);

        firestore = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();

        Quantiadde = findViewById(R.id.quantidade);
        datalheImg = findViewById(R.id.detalhe_img);
        detalheDescri = findViewById(R.id.detalhe_descri);
        detalhePreco = findViewById(R.id.detalhe_preco);
        totalPreco = findViewById(R.id.totalpreco);
        addItem = findViewById(R.id.add_item);
        removeItem = findViewById(R.id.remove_item);

        // Receber o objeto ViewAllModel passado
        final Object object = getIntent().getSerializableExtra("detalhes");
        if (object instanceof ViewAllModel) {
            viewAllModel = (ViewAllModel) object;
        }

        if (viewAllModel != null) {
            Glide.with(getApplicationContext()).load(viewAllModel.getImg_url()).into(datalheImg);
            detalheDescri.setText(viewAllModel.getDescricao());
            precoUnitario = viewAllModel.getPreco();
            detalhePreco.setText("Preço: R$ " + precoUnitario + "/kg");
            updateTotalPrice();
        }

        addToCar = findViewById(R.id.add_to_car);
        addToCar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addToCar();
            }
        });

        addItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ttquantidade < 100) {
                    ttquantidade++;
                    Quantiadde.setText(String.valueOf(ttquantidade));
                    updateTotalPrice();
                }
            }
        });

        removeItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ttquantidade > 0) {
                    ttquantidade--;
                    Quantiadde.setText(String.valueOf(ttquantidade));
                    updateTotalPrice();
                }
            }
        });
    }

    private void addToCar() {
        String userId = Objects.requireNonNull(auth.getCurrentUser()).getUid();
        String itemName = viewAllModel.getNome();
        float unitPrice = viewAllModel.getPreco();
        int quantity = ttquantidade;
        float totalPrice = quantity * unitPrice;

        DocumentReference userCartRef = firestore.collection("UsuarioAtual")
                .document(userId).collection("Carrinho").document(itemName);

        userCartRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        // Item já existe, atualizar quantidade e preço total
                        int existingQuantity = Objects.requireNonNull(document.getLong("quantidadeTT")).intValue();
                        float existingTotalPrice = Objects.requireNonNull(document.getDouble("precoTotal")).floatValue();

                        userCartRef.update(
                                "quantidadeTT", existingQuantity + quantity,
                                "precoTotal", existingTotalPrice + totalPrice
                        ).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> updateTask) {
                                if (updateTask.isSuccessful()) {
                                    Toast.makeText(DetalhesActivity.this, "Carrinho atualizado", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(DetalhesActivity.this, "Erro ao atualizar carrinho", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    } else {
                        // Item não existe, adicionar novo documento
                        HashMap<String, Object> cartMap = new HashMap<>();
                        cartMap.put("nomeProduto", itemName);
                        cartMap.put("precoProduto", unitPrice);
                        cartMap.put("quantidadeTT", quantity);
                        cartMap.put("precoTotal", totalPrice);

                        userCartRef.set(cartMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> addTask) {
                                if (addTask.isSuccessful()) {
                                    Toast.makeText(DetalhesActivity.this, "Adicionado ao carrinho", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(DetalhesActivity.this, "Erro ao adicionar ao carrinho", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                } else {
                    Toast.makeText(DetalhesActivity.this, "Erro ao acessar carrinho", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    @SuppressLint({"SetTextI18n", "DefaultLocale"})
    private void updateTotalPrice() {
        float total = ttquantidade * precoUnitario;
        totalPreco.setText(String.format("R$ %.2f", total));
    }
}

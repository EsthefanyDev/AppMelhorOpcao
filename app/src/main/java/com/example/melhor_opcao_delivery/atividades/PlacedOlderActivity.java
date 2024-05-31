package com.example.melhor_opcao_delivery.atividades;

import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.melhor_opcao_delivery.Model.CardModel;
import com.example.melhor_opcao_delivery.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class PlacedOlderActivity extends AppCompatActivity {

    private FirebaseAuth auth;
    private FirebaseFirestore firestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_placed_older);

        auth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();

        List<CardModel> list = (ArrayList<CardModel>) getIntent().getSerializableExtra("listaItens");

        if(list != null && list.size() > 0){
            for (CardModel model : list){
                final HashMap<String, Object> cartMap = new HashMap<>();

                cartMap.put("nomeProduto", model.getNomeProduto());
                cartMap.put("precoProduto", model.getPrecoProduto());
                cartMap.put("quantidadeTT", model.getQuantidadeTT());
                cartMap.put("precoTotal", model.getPrecoTotal());

                firestore.collection("UsuarioAtual").document(Objects.requireNonNull(auth.getCurrentUser()).getUid())
                        .collection("MeuPedido").add(cartMap).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentReference> task) {
                                if(task.isSuccessful()){
                                    Toast.makeText(PlacedOlderActivity.this, "Seu pedido foi feito", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(PlacedOlderActivity.this, "Erro ao fazer o pedido", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        }
    }
}

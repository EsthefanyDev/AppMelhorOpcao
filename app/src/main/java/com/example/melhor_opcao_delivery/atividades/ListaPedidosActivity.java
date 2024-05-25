package com.example.melhor_opcao_delivery.atividades;
// Importe as classes necessárias
import android.os.Bundle;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.melhor_opcao_delivery.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import java.util.Map;

public class ListaPedidosActivity extends AppCompatActivity {

    private static final String TAG = "OrderListActivity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_pedidos);

        // Recupere os pedidos do usuário atual
        retrieveUserOrders();
    }

    private void retrieveUserOrders() {
        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        // Recupere o ID do usuário atualmente autenticado
        String userId = auth.getCurrentUser().getUid();

        // Construa a referência para o nó "MeuPedido" do usuário atual
        CollectionReference myOrdersRef = db.collection("Usuarios").document(userId).collection("MeuPedido");

        // Recupere os pedidos do usuário
        myOrdersRef.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                    // Para cada documento de pedido encontrado
                    String orderId = documentSnapshot.getId();
                    Map<String, Object> orderData = documentSnapshot.getData();

                    // Aqui você pode fazer o que quiser com os dados do pedido
                    Log.d(TAG, "Pedido ID: " + orderId + ", Dados: " + orderData.toString());
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.e(TAG, "Erro ao recuperar os pedidos do usuário: " + e.getMessage());
            }
        });
    }
}

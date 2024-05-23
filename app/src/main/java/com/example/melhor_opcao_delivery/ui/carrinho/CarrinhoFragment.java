package com.example.melhor_opcao_delivery.ui.carrinho;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.melhor_opcao_delivery.Model.CardModel;
import com.example.melhor_opcao_delivery.R;
import com.example.melhor_opcao_delivery.adapter.CardAdapter;
import com.example.melhor_opcao_delivery.atividades.PlacedOlderActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CarrinhoFragment extends Fragment {
    private FirebaseFirestore db;
    private FirebaseAuth auth;

    private TextView meuvalorTotal;
    RecyclerView recyclerView;
    private CardAdapter cardAdapter;
    private List<CardModel> cardModelList;

    Button buyNow;
    int valortt;

    public CarrinhoFragment(){
        // Construtor público vazio requerido
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_carrinho, container, false);

        // Inicializando Firestore e Auth
        db = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();

        // Configurando RecyclerView
        recyclerView = root.findViewById(R.id.recycleview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        // Configurando TextView do valor total
        meuvalorTotal = root.findViewById(R.id.valorTotal);
        LocalBroadcastManager.getInstance(requireActivity())
                .registerReceiver(mMessagerReciver, new IntentFilter("MeuValorTotal"));
        //chamando o comprar agora
        buyNow = root.findViewById(R.id.comprarAgoraBtn);


        // Carregando itens do carrinho
        cardModelList = new ArrayList<>();
        cardAdapter = new CardAdapter(getActivity(), cardModelList);
        recyclerView.setAdapter(cardAdapter);

        // Carregando dados do Firestore
        loadCartItems();

        return root;
    }

    private void loadCartItems() {
        db.collection("UsuarioAtual").document(Objects.requireNonNull(auth.getCurrentUser()).getUid())
                .collection("Carrinho").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @SuppressLint("NotifyDataSetChanged")
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (DocumentSnapshot documentSnapshot : task.getResult().getDocuments()) {

                                String documentId = documentSnapshot.getId();

                                CardModel cardModel = documentSnapshot.toObject(CardModel.class);

                                cardModel.setDocumnentId(documentId);

                                if (cardModel != null) {
                                    cardModelList.add(cardModel);
                                }
                            }
                            cardAdapter.notifyDataSetChanged();
                        }
                    }
                });

        buyNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), PlacedOlderActivity.class);
                intent.putExtra("listaItens", (Serializable) cardModelList);
                startActivity(intent);
            }
        });
    }

    private final BroadcastReceiver mMessagerReciver = new BroadcastReceiver() {
        @SuppressLint({"SetTextI18n", "DefaultLocale"})
        @Override
        public void onReceive(Context context, Intent intent) {
            float valorTotal = intent.getFloatExtra("valorTotal", 0);
            meuvalorTotal.setText(String.format("Valor Total: %.2f R$", valorTotal));
        }
    };
}

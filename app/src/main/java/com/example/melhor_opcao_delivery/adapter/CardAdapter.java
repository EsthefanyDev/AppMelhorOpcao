package com.example.melhor_opcao_delivery.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.melhor_opcao_delivery.Model.CardModel;
import com.example.melhor_opcao_delivery.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class CardAdapter extends RecyclerView.Adapter<CardAdapter.ViewHolder> {

    Context context;
    List<CardModel> cardModelList;
    float valorTotal;

    FirebaseFirestore firestore;
    FirebaseAuth auth;

    public CardAdapter(Context context, List<CardModel> cardModelList) {
        this.context = context;
        this.cardModelList = cardModelList;
        firestore = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_itens_detalhes, parent, false));
    }

    @SuppressLint("DefaultLocale")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        CardModel cardModel = cardModelList.get(position);

        holder.nome.setText(cardModel.getNomeProduto());
        holder.preco.setText(String.format("R$ %.2f", cardModel.getPrecoProduto())); // Convert float to formatted String
        holder.quantidade.setText(String.valueOf(cardModel.getQuantidadeTT())); // Convert int to String
        holder.precoTotal.setText(String.format("R$ %.2f", cardModel.getPrecoTotal())); // Convert float to formatted String

        holder.deleteItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firestore.collection("UsuarioAtual").document(auth.getCurrentUser().getUid())
                        .collection("Carrinho")
                        .document(cardModelList.get(position).getDocumnentId())
                        .delete()
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {

                                if (task.isSuccessful()){
                                    cardModelList.remove(cardModelList.get(position));
                                    notifyDataSetChanged();
                                    Toast.makeText(context, "Item deletado", Toast.LENGTH_SHORT).show();
                                }
                                else {
                                    Toast.makeText(context, "Error"+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });

        ///passando o preco totsl do produto

    }

    @Override
    public int getItemCount() {
        return cardModelList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView nome, preco, quantidade, precoTotal;
        ImageView deleteItem;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nome = itemView.findViewById(R.id.nomeProduto);
            preco = itemView.findViewById(R.id.precoProduto);
            quantidade = itemView.findViewById(R.id.quantidadeTT);
            precoTotal = itemView.findViewById(R.id.precoTotal);
            deleteItem = itemView.findViewById(R.id.deletar);
        }
    }
}

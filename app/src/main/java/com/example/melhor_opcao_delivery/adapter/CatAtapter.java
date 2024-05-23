package com.example.melhor_opcao_delivery.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.melhor_opcao_delivery.Model.CatModel;
import com.example.melhor_opcao_delivery.R;
import com.example.melhor_opcao_delivery.atividades.ViewAllActivity;

import java.util.List;

public class CatAtapter extends RecyclerView.Adapter<CatAtapter.ViewHolder> {

    private Context context;
    private List<CatModel> catModelList;

    public CatAtapter(Context context, List<CatModel> catModelList) {
        this.context = context;
        this.catModelList = catModelList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.categorias, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // Carregar a imagem usando Glide
        Glide.with(context).load(catModelList.get(position).getCat_imag()).into(holder.Cat_Imag);
        // Definir o nome da categoria
        holder.Cat_nome.setText(catModelList.get(position).getCat_nome());

        // Definir o listener de clique
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Obter a posição atual do adaptador
                int adapterPosition = holder.getAdapterPosition();
                if (adapterPosition != RecyclerView.NO_POSITION) {
                    // Usar a posição atual do adaptador para obter os dados
                    CatModel model = catModelList.get(adapterPosition);

                    // Criar e iniciar o intent
                    Intent intent = new Intent(context, ViewAllActivity.class);
                    intent.putExtra("tipo", model.getTipo());
                    context.startActivity(intent);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return catModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView Cat_Imag;
        TextView Cat_nome;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            Cat_Imag = itemView.findViewById(R.id.cat_imag); // Imagem da categoria
            Cat_nome = itemView.findViewById(R.id.catnome); // Nome da categoria
        }
    }
}

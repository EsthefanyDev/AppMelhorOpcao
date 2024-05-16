package com.example.melhor_opcao_delivery.adapter;

import android.content.Context;
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

import java.util.List;

public class CatAtapter extends RecyclerView.Adapter<CatAtapter.ViewHloder> {

    private Context context;
    private List<CatModel> catModelList;

    public CatAtapter(Context context, List<CatModel> catModelList) {
        this.context = context;
        this.catModelList = catModelList;
    }

    @NonNull
    @Override
    public ViewHloder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHloder(LayoutInflater.from(parent.getContext()).inflate(R.layout.categorias, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHloder holder, int position) {

        Glide.with(context).load(catModelList.get(position).getCat_imag()).into(holder.Cat_Imag);
        holder.Cat_nome.setText(catModelList.get(position).getCat_nome());
    }

    @Override
    public int getItemCount() {
        return catModelList.size();
    }

    public class ViewHloder extends RecyclerView.ViewHolder {
        ImageView Cat_Imag;
        TextView Cat_nome;
        public ViewHloder(@NonNull View itemView) {
            super(itemView);
            Cat_Imag = itemView.findViewById(R.id.cat_imag);
            Cat_nome = itemView.findViewById(R.id.catnome);
        }
    }
}

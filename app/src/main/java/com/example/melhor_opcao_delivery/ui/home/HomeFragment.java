package com.example.melhor_opcao_delivery.ui.home;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.melhor_opcao_delivery.Model.CatModel;
import com.example.melhor_opcao_delivery.Model.PopularModel;
import com.example.melhor_opcao_delivery.Model.ViewAllModel;
import com.example.melhor_opcao_delivery.R;
import com.example.melhor_opcao_delivery.adapter.CatAtapter;
import com.example.melhor_opcao_delivery.adapter.PopularAdapters;
import com.example.melhor_opcao_delivery.adapter.ViewAllAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    FirebaseFirestore db;

    // Categorias itens
    RecyclerView catRec;
    List<CatModel> catModelList;
    CatAtapter catatapter;

    /////////pesquisar produtos
    EditText pesquisa_box;
    private List<ViewAllModel> viewAllModelList;
    private RecyclerView recyclerViewpesquisa;
    private ViewAllAdapter viewAllAdapter;

    // Marcas populares
    RecyclerView popularRec;
    List<PopularModel> popularModelList;
    PopularAdapters popularAdapters;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        db = FirebaseFirestore.getInstance();

        // Configurar RecyclerView de categorias
        catRec = root.findViewById(R.id.categorias_rec);
        catRec.setLayoutManager(new GridLayoutManager(getActivity(), 3)); // 3 colunas
        catModelList = new ArrayList<>();
        catatapter = new CatAtapter(getActivity(), catModelList);
        catRec.setAdapter(catatapter);

        db.collection("CategoriasProd")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {
                                CatModel catModel = documentSnapshot.toObject(CatModel.class);
                                catModelList.add(catModel);
                                catatapter.notifyDataSetChanged();
                            }
                        } else {
                            Toast.makeText(getActivity(), "Error" + task.getException(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });

        // Configurar RecyclerView de marcas populares
        popularRec = root.findViewById(R.id.pop_rec);
        popularRec.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false));
        popularModelList = new ArrayList<>();
        popularAdapters = new PopularAdapters(getActivity(), popularModelList);
        popularRec.setAdapter(popularAdapters);

        db.collection("PopularProdutos")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {
                                PopularModel popularModel = documentSnapshot.toObject(PopularModel.class);
                                popularModelList.add(popularModel);
                                popularAdapters.notifyDataSetChanged();
                            }
                        } else {
                            Toast.makeText(getActivity(), "Error" + task.getException(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });

        /////////pesquisar produtos
        recyclerViewpesquisa = root.findViewById(R.id.pesquisa_rec);
        pesquisa_box = root.findViewById(R.id.pesquisa);
        viewAllModelList = new ArrayList<>();
        viewAllAdapter = new ViewAllAdapter(getContext(), viewAllModelList);
        recyclerViewpesquisa.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerViewpesquisa.setAdapter(viewAllAdapter);
        recyclerViewpesquisa.setHasFixedSize(true);
        pesquisa_box.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().isEmpty()) {
                    viewAllModelList.clear();
                    viewAllAdapter.notifyDataSetChanged();
                } else {
                    pesquidaProduto(s.toString());
                }
            }
        });

        return root;
    }

    private void pesquidaProduto(String query) {
        if (!query.isEmpty()) {
            db.collection("Produtos")
                    .orderBy("nome") // Assuming there is a "nome" field to order by
                    .startAt(query)
                    .endAt(query + "\uf8ff")
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @SuppressLint("NotifyDataSetChanged")
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful() && task.getResult() != null) {
                                viewAllModelList.clear();
                                for (DocumentSnapshot doc : task.getResult().getDocuments()) {
                                    ViewAllModel viewAllModel = doc.toObject(ViewAllModel.class);
                                    viewAllModelList.add(viewAllModel);
                                }
                                viewAllAdapter.notifyDataSetChanged();
                            }
                        }
                    });
        }
    }
}

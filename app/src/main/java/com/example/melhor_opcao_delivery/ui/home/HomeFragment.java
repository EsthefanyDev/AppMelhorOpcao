package com.example.melhor_opcao_delivery.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.melhor_opcao_delivery.Model.CatModel;
import com.example.melhor_opcao_delivery.Model.PopularModel;
import com.example.melhor_opcao_delivery.R;
import com.example.melhor_opcao_delivery.adapter.CatAtapter;
import com.example.melhor_opcao_delivery.adapter.PopularAdapters;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
//import com.example.melhor_opcao_delivery.databinding.FragmentHomeBinding;

public class HomeFragment extends Fragment {

    FirebaseFirestore db;

    //Catgorias itens
    RecyclerView catRec;
    List<CatModel> catModelList;
    CatAtapter catatapter;

    //Marcas populars
    RecyclerView popularRec;
    List<PopularModel> popularModelList;
    PopularAdapters popularAdapters;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_home, container, false);
        db = FirebaseFirestore.getInstance();

        popularRec = root.findViewById(R.id.pop_rec);
        catRec = root.findViewById(R.id.categorias_rec);

        //ITENS DA CATEGORIA
        catRec.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false));
        catModelList = new ArrayList<>();
        catatapter = new CatAtapter(getActivity(), catModelList);
        catRec.setAdapter(catatapter);

        db.collection("CategoriasProd")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()){
                            for (QueryDocumentSnapshot documentSnapshot : task.getResult()){

                                CatModel catModel = documentSnapshot.toObject(CatModel.class);
                                catModelList.add(catModel);
                                catatapter.notifyDataSetChanged();

                            }
                        }else {

                            Toast.makeText(getActivity(), "Error"+task.getException(), Toast.LENGTH_SHORT).show();

                        }
                    }
                });


        popularRec = root.findViewById(R.id.pop_rec);
        // POPULAR ITENS
        popularRec.setLayoutManager(new LinearLayoutManager(getActivity(),RecyclerView.HORIZONTAL,false));
        popularModelList = new ArrayList<>();
        popularAdapters = new PopularAdapters(getActivity(),popularModelList);
        popularRec.setAdapter(popularAdapters);

        db.collection("PopularProdutos")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()){
                            for (QueryDocumentSnapshot documentSnapshot : task.getResult()){

                                PopularModel popularModel = documentSnapshot.toObject(PopularModel.class);
                                popularModelList.add(popularModel);
                                popularAdapters.notifyDataSetChanged();

                            }
                        }else {

                            Toast.makeText(getActivity(),"Error"+task.getException(),Toast.LENGTH_SHORT).show();

                        }
                    }
                });
        return root;
    }
}
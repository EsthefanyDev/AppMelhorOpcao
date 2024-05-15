package com.example.melhor_opcao_delivery.ui.carrinho;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.melhor_opcao_delivery.R;
//import com.example.melhor_opcao_delivery.databinding.FragmentGalleryBinding;

public class CarrinhoFragment extends Fragment {

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_carrinho, container, false);
        return root;
    }
}
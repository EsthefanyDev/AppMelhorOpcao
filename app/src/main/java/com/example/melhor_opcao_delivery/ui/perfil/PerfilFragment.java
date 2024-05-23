package com.example.melhor_opcao_delivery.ui.perfil;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.melhor_opcao_delivery.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;

import de.hdodenhof.circleimageview.CircleImageView;

public class PerfilFragment extends Fragment {

    CircleImageView imgperfil;
    EditText nome, email, cpf, endereco, senha;

    Button updateBTN;

    FirebaseStorage storage;
    FirebaseAuth auth;
    FirebaseFirestore database;

    public View onCreateView(@NonNull LayoutInflater inflater,

                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_perfil,container, false);

        auth = FirebaseAuth.getInstance();

        imgperfil = root.findViewById(R.id.perfil_img);
        nome = root.findViewById(R.id.perfil_nome);
        email = root.findViewById(R.id.perfil_email);
        cpf = root.findViewById(R.id.perfil_cpf);
        endereco = root.findViewById(R.id.perfil_endereco);
        senha = root.findViewById(R.id.perfil_senha);
        updateBTN = root.findViewById(R.id.update_btn);

        imgperfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent  = new Intent();
                intent.setAction(Intent.ACTION_GET_CONTENT);
                intent.setType("imagem/*");
                startActivityForResult(intent, 33);
            }
        });

        updateBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateUserPerfil();
            }
        });

        return root;
    }

    private void updateUserPerfil() {

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (data.getData() != null){
            Uri perfilRUri = data.getData();
            imgperfil.setImageURI(perfilRUri);

        }
    }
}
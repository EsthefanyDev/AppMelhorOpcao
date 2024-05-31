package com.example.melhor_opcao_delivery.ui.perfil;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.melhor_opcao_delivery.Model.UserModel;
import com.example.melhor_opcao_delivery.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import de.hdodenhof.circleimageview.CircleImageView;

public class PerfilFragment extends Fragment {

    CircleImageView imgperfil;
    EditText nome, email, cpf, endereco, senha;

    Button updateBTN;

    FirebaseStorage storage;
    FirebaseAuth auth;
    FirebaseDatabase database;


    public View onCreateView(@NonNull LayoutInflater inflater,

                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_perfil,container, false);

        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        storage = FirebaseStorage.getInstance();

        imgperfil = root.findViewById(R.id.perfil_img);
        nome = root.findViewById(R.id.perfil_nome);
        email = root.findViewById(R.id.perfil_email);
        cpf = root.findViewById(R.id.perfil_cpf);
        endereco = root.findViewById(R.id.perfil_endereco);
        senha = root.findViewById(R.id.perfil_senha);
        updateBTN = root.findViewById(R.id.update_btn);

        database.getReference().child("Usuarios").child(FirebaseAuth.getInstance().getUid())
                        .addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                UserModel userModel = snapshot.getValue(UserModel.class);

                                Glide.with(getContext()).load(userModel.getImgPerfil()).into(imgperfil);
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });

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

            final StorageReference reference = storage.getReference().child("imagem_perfil")
                    .child(FirebaseAuth.getInstance().getUid());

            reference.putFile(perfilRUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Toast.makeText(getContext(), "Uploaded", Toast.LENGTH_SHORT).show();

                    reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {

                            database.getReference().child("Usuarios").child(FirebaseAuth.getInstance().getUid())
                                    .child("imgPerfil").setValue(uri.toString());
                            Toast.makeText(getContext(), "Imagem de Perfil baixada", Toast.LENGTH_SHORT).show();

                        }
                    });
                }
            });
        }
    }
}
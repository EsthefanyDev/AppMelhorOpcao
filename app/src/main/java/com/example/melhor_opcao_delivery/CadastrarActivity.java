package com.example.melhor_opcao_delivery;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.melhor_opcao_delivery.Model.UserModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

public class CadastrarActivity extends AppCompatActivity {

    FirebaseDatabase database;
    FirebaseAuth auth;

    ProgressBar barraPros;

    Button cadastrar;

    EditText nomeusuario, emailusuario, cpfusuario, enderecousuario, senhausuario;

    TextView entrar;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_cadastrar);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);

            barraPros = findViewById(R.id.barrapros);
            barraPros.setVisibility(View.GONE);

            auth =FirebaseAuth.getInstance();
            database = FirebaseDatabase.getInstance();

            cadastrar = findViewById(R.id.cadastrar_btn);

            nomeusuario = findViewById(R.id.nomeusuario);
            emailusuario =findViewById(R.id.emailusuario);
            cpfusuario = findViewById(R.id.cpfusuario);
            enderecousuario = findViewById(R.id.enderecousuario);
            senhausuario = findViewById(R.id.senhausuario);

            entrar = findViewById(R.id.entrar);

            barraPros.setVisibility(View.VISIBLE);

            entrar.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    startActivity(new Intent(CadastrarActivity.this, LoginActivity.class));
                }
            });
            cadastrar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    createUser();
                }
            });

            return insets;
        });
    }

    private void createUser() {
        String nomeUsuario = nomeusuario.getText().toString();
        String emailUsuario = emailusuario.getText().toString();
        String enderecoUsuario = enderecousuario.getText().toString();
        String cpfUsuario = cpfusuario.getText().toString();
        String senhaUsuario = senhausuario.getText().toString();

        if (TextUtils.isEmpty(nomeUsuario)){
            Toast.makeText(this, "Nome é Obrigatorio", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(emailUsuario)){
            Toast.makeText(this, "Email é Obrigatorio", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(enderecoUsuario)){
            Toast.makeText(this, "Endereço é Obrigatorio", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(cpfUsuario)){
            Toast.makeText(this, "CPF é Obrigatorio", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(senhaUsuario)){
            Toast.makeText(this, "Senha é Obrigatorio", Toast.LENGTH_SHORT).show();
        }
        if(senhaUsuario.length() < 6){
            Toast.makeText(this, "Crie uma senha com 6 digitos", Toast.LENGTH_SHORT).show();
            return;
        }
        //CRIAÇÂO DO USUÁRIO
        auth.createUserWithEmailAndPassword(emailUsuario, senhaUsuario)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                barraPros.setVisibility(View.GONE);
                                UserModel Model = new UserModel(nomeUsuario, emailUsuario, enderecoUsuario, cpfUsuario, senhaUsuario);
                                String id = Objects.requireNonNull(task.getResult().getUser()).getUid();
                                database.getReference().child("Usuarios").child(id).setValue(Model);

                                Toast.makeText(CadastrarActivity.this, "Cdastrado com sucesso!!", Toast.LENGTH_SHORT).show();
                            } else {
                                barraPros.setVisibility(View.GONE);
                                Toast.makeText(CadastrarActivity.this, "ERRO" + task.getException(), Toast.LENGTH_SHORT).show();
                            }
                        }
                });
    }
}
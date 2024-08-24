package com.example.melhor_opcao_delivery.atividades;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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
import com.example.melhor_opcao_delivery.R;
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
    EditText nomeusuario, emailusuario, telefoneusuario, enderecousuario, senhausuario;
    TextView entrar;

    boolean isPasswordVisible = false;

    @SuppressLint({"WrongViewCast", "ClickableViewAccessibility"})
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

            auth = FirebaseAuth.getInstance();
            database = FirebaseDatabase.getInstance();

            cadastrar = findViewById(R.id.cadastrar_btn);

            nomeusuario = findViewById(R.id.nomeusuario);
            emailusuario = findViewById(R.id.emailusuario);
            telefoneusuario = findViewById(R.id.telefoneusuario);
            enderecousuario = findViewById(R.id.enderecousuario);
            senhausuario = findViewById(R.id.senhausuario);

            entrar = findViewById(R.id.entrar);

            // Configurar a funcionalidade de mostrar/esconder senha com troca de ícone
            senhausuario.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    final int DRAWABLE_END = 2; // Ícone na extremidade direita
                    if (event.getAction() == MotionEvent.ACTION_UP) {
                        if (event.getRawX() >= (senhausuario.getRight() - senhausuario.getCompoundDrawables()[DRAWABLE_END].getBounds().width())) {
                            if (isPasswordVisible) {
                                // Esconder a senha e trocar o ícone para olho fechado
                                senhausuario.setTransformationMethod(PasswordTransformationMethod.getInstance());
                                senhausuario.setCompoundDrawablesWithIntrinsicBounds(
                                        R.drawable.chave, // Ícone de chave na esquerda
                                        0,                 // Nenhum ícone no topo
                                        R.drawable.olhos_cruzados, // Ícone de olho fechado na direita
                                        0);                // Nenhum ícone na parte inferior
                                isPasswordVisible = false;
                            } else {
                                // Mostrar a senha e trocar o ícone para olho aberto
                                senhausuario.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                                senhausuario.setCompoundDrawablesWithIntrinsicBounds(
                                        R.drawable.chave, // Ícone de chave na esquerda
                                        0,                 // Nenhum ícone no topo
                                        R.drawable.olho, // Ícone de olho aberto na direita
                                        0);                // Nenhum ícone na parte inferior
                                isPasswordVisible = true;
                            }
                            // Coloca o cursor no final do texto
                            senhausuario.setSelection(senhausuario.getText().length());
                            return true;
                        }
                    }
                    return false;
                }
            });

            // Aqui você configura o clique para voltar à tela inicial
            ImageView voltarInicial = findViewById(R.id.voltar_inicial);
            voltarInicial.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(CadastrarActivity.this, InicialActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    finish(); // Encerra a atividade atual para não voltar a ela
                }
            });

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
        String telefoneUsuario = telefoneusuario.getText().toString();
        String senhaUsuario = senhausuario.getText().toString();

        if (TextUtils.isEmpty(nomeUsuario)) {
            Toast.makeText(this, "Nome é Obrigatório", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(emailUsuario)) {
            Toast.makeText(this, "Email é Obrigatório", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(enderecoUsuario)) {
            Toast.makeText(this, "Endereço é Obrigatório", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(telefoneUsuario)) {
            Toast.makeText(this, "Telefone é Obrigatório", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(senhaUsuario)) {
            Toast.makeText(this, "Senha é Obrigatória", Toast.LENGTH_SHORT).show();
            return;
        }
        if (senhaUsuario.length() < 6) {
            Toast.makeText(this, "Crie uma senha com 6 dígitos", Toast.LENGTH_SHORT).show();
            return;
        }

        // Criação do usuário
        barraPros.setVisibility(View.VISIBLE);
        auth.createUserWithEmailAndPassword(emailUsuario, senhaUsuario)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        barraPros.setVisibility(View.GONE);
                        if (task.isSuccessful()) {
                            UserModel model = new UserModel(
                                    nomeUsuario,
                                    emailUsuario,
                                    enderecoUsuario,
                                    telefoneUsuario,
                                    senhaUsuario,
                                    false);
                            String id = Objects.requireNonNull(task.getResult().getUser()).getUid();
                            database.getReference().child("Usuarios").child(id).setValue(model);
                            Toast.makeText(CadastrarActivity.this, "Cadastrado com sucesso!", Toast.LENGTH_SHORT).show();
                            // Redirecionar para outra atividade, se necessário
                        } else {
                            Toast.makeText(CadastrarActivity.this, "Erro: " + task.getException(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}

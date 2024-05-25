package com.example.melhor_opcao_delivery.atividades;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
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

import com.example.melhor_opcao_delivery.MainActivity;
import com.example.melhor_opcao_delivery.Model.UserModel;
import com.example.melhor_opcao_delivery.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.DatabaseReference;

public class LoginActivity extends AppCompatActivity {
    FirebaseDatabase database;
    FirebaseAuth auth;

    ProgressBar barraPros;
    Button entrar;
    EditText emailusuario, senhausuario;
    TextView cadastrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);

            barraPros = findViewById(R.id.barrapros);
            barraPros.setVisibility(View.GONE);

            entrar = findViewById(R.id.login_btn);
            emailusuario = findViewById(R.id.emailusuario);
            senhausuario = findViewById(R.id.senhausuario);
            cadastrar = findViewById(R.id.casdastrar);

            auth =FirebaseAuth.getInstance();
            database = FirebaseDatabase.getInstance();

            cadastrar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(LoginActivity.this, CadastrarActivity.class));
                }
            });

            entrar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    login();
                }
            });

            return insets;
        });
    }

    private void login() {
        String emailUsuario = emailusuario.getText().toString();
        String senhaUsuario = senhausuario.getText().toString();

        barraPros.setVisibility(View.VISIBLE);

        if (TextUtils.isEmpty(emailUsuario)) {
            Toast.makeText(this, "Email é Obrigatorio", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(senhaUsuario)) {
            Toast.makeText(this, "Senha é Obrigatorio", Toast.LENGTH_SHORT).show();
            return;
        }
        if (senhaUsuario.length() < 6) {
            Toast.makeText(this, "Crie uma senha com 6 digitos", Toast.LENGTH_SHORT).show();
            return;
        }

        // Login do Usuário
        auth.signInWithEmailAndPassword(emailUsuario, senhaUsuario).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    barraPros.setVisibility(View.GONE);
                    Toast.makeText(LoginActivity.this, "Dados corretos", Toast.LENGTH_SHORT).show();
                    // Após o login bem-sucedido, verifique o status do administrador
                    checkAdminStatus();
                } else {
                    barraPros.setVisibility(View.GONE);
                    Toast.makeText(LoginActivity.this, "Dados incorretos", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void checkAdminStatus() {
        String userId = auth.getCurrentUser().getUid();
        DatabaseReference userRef = FirebaseDatabase.getInstance().getReference().child("Usuarios").child(userId);

        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // Verifica se os dados existem
                if (dataSnapshot.exists()) {
                    // Recupera o valor do campo "admin"
                    Boolean isAdmin = dataSnapshot.child("admin").getValue(Boolean.class);
                    if (isAdmin != null && isAdmin) {
                        Log.d("AdminStatus", "User is an admin");
                        // Se o usuário é admin, inicia a AdminActivity
                        startActivity(new Intent(LoginActivity.this, AdminActivity.class));
                    } else {
                        Log.d("AdminStatus", "User is not an admin");
                        // Se o usuário não é admin, inicia a MainActivity
                        startActivity(new Intent(LoginActivity.this, MainActivity.class));
                    }
                    // Finaliza a LoginActivity
                    finish();
                } else {
                    Log.d("AdminStatus", "Admin field is null");
                    // Se os dados não existem
                    // Aqui você pode lidar com o caso em que os dados do usuário não foram encontrados
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Trata o erro ao recuperar os dados do usuário
                Toast.makeText(LoginActivity.this, "Erro ao verificar o status de administrador", Toast.LENGTH_SHORT).show();
            }
        });
    }

}

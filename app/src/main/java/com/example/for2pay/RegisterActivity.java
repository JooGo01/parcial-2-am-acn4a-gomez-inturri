package com.example.for2pay;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {

    Button btn_registro;
    EditText correo, contrasenia;
    FirebaseFirestore mFireStore;

    FirebaseAuth mAuth;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mFireStore=FirebaseFirestore.getInstance();
        mAuth=FirebaseAuth.getInstance();

        correo=findViewById(R.id.correo_registro);
        contrasenia=findViewById(R.id.contrasenia_registro);
        btn_registro=findViewById(R.id.btn_registrar_usuario);

        btn_registro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String correoUsuario= correo.getText().toString().trim();
                String pwdUsuario=contrasenia.getText().toString().trim();

                if(correoUsuario.isEmpty() || pwdUsuario.isEmpty()){
                    Toast.makeText(RegisterActivity.this, "Complete los campos", Toast.LENGTH_SHORT).show();
                }else{
                    fncRegistrar(correoUsuario,pwdUsuario);
                }
            }
        });
    }

    private void fncRegistrar(String p_email, String p_pwd){
        mAuth.createUserWithEmailAndPassword(p_email, p_pwd).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    finish();
                    String id = mAuth.getCurrentUser().getUid();
                    Map<String, Object> mapUsuario = new HashMap<>();
                    mapUsuario.put("id",id);
                    mapUsuario.put("correo", p_email);
                    mapUsuario.put("contrasenia", p_pwd);
                    mFireStore.collection("user").document(id).set(mapUsuario).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            finish();
                            Toast.makeText(RegisterActivity.this, "Registro realizado con exito", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(RegisterActivity.this, "Error al registar el usuario", Toast.LENGTH_SHORT).show();
                        }
                    });
                }else{
                    Toast.makeText(RegisterActivity.this, "Contraseña o email invalido", Toast.LENGTH_SHORT).show();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(RegisterActivity.this, "Error al registrar usuario", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
package com.example.for2pay;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Firebase;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    EditText correo;
    EditText contrasenia;
    Button lgnButton;
    Button regButton;

    FirebaseAuth mAuth;

    SharedPreferences preferencias;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        setTheme(R.style.Base_Theme_For2Pay);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        correo = findViewById(R.id.correo);
        contrasenia=findViewById(R.id.contrasenia);
        lgnButton=findViewById(R.id.btn_login);
        regButton=findViewById(R.id.btn_registrarse);

        mAuth=FirebaseAuth.getInstance();

        lgnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String emailUser = correo.getText().toString().trim();
                String pwdUser =contrasenia.getText().toString().trim();
                if (emailUser.isEmpty() || pwdUser.isEmpty()) {
                    Toast.makeText(LoginActivity.this, "Complete los campos", Toast.LENGTH_SHORT).show();
                } else{
                    fncInicioSesion(emailUser, pwdUser);
                }
            }
        });

        regButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });

        preferencias = getSharedPreferences("sesion", Context.MODE_PRIVATE);
        if(preferencias.getBoolean("estado_usuario", false)==true){
            // iniciamos sesion automaticamente
            String sesionUser = preferencias.getString("sesion_mail", null);
            correo.setText(sesionUser);
            String sesionPwd = preferencias.getString("sesion_pwd",null);
            contrasenia.setText(sesionPwd);
            fncInicioSesion(sesionUser, sesionPwd);
        }
    }

    private void fncInicioSesion(String p_email, String p_pwd){
        mAuth.signInWithEmailAndPassword(p_email, p_pwd).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    finish();
                    guardarSesion(p_email,p_pwd);
                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                    Toast.makeText(LoginActivity.this, "Inicio sesion con exito", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(LoginActivity.this, "Contraseña o email invalido", Toast.LENGTH_SHORT).show();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(LoginActivity.this, "Error al iniciar sesion", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void guardarSesion(String p_email, String p_pwd){
        //guaradamos los parametros en caso de que el inicio haya sido correcto
        preferencias = getSharedPreferences("sesion", Context.MODE_PRIVATE);
        boolean estado=true;
        SharedPreferences.Editor editor = preferencias.edit();
        editor.putBoolean("estado_usuario", estado);
        editor.putString("sesion_mail", p_email);
        editor.putString("sesion_pwd", p_pwd);
        editor.commit();
    }

}
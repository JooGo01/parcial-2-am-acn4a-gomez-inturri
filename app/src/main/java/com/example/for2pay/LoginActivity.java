package com.example.for2pay;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    EditText correo;
    EditText contrasenia;
    Button lgnButton;
    Button regButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        correo = findViewById(R.id.correo);
        contrasenia=findViewById(R.id.contrasenia);
        lgnButton=findViewById(R.id.btn_login);
        regButton=findViewById(R.id.btn_registrarse);

        lgnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(correo.getText().toString().equals("admin") && contrasenia.getText().toString().equals("123")){
                    Toast.makeText(LoginActivity.this, "Login exitoso", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(LoginActivity.this, "Login erroneo", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}
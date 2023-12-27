package com.example.for2pay;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    FloatingActionButton fab;
    DrawerLayout drawerLayout;
    BottomNavigationView bnv;

    NavigationView nv;

    SharedPreferences preferencias;
    @SuppressLint("NonConstantResourceId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bnv=findViewById(R.id.bottomNavigationView);
        nv=(NavigationView) findViewById(R.id.nav_view);
        //nv.setNavigationItemSelectedListener(this);
        nv.setNavigationItemSelectedListener(this);

        //ELEMENTAL ESTA LINEA PARA QUE FUNCIONE EL NAVIGATION VIEW, SI NO NO TOMA EL CLICK DE LOS ELEMENTOS
        nv.bringToFront();
        //

        fab=findViewById(R.id.fab);
        drawerLayout=findViewById(R.id.drawer_layout);

        Toolbar toolbar=findViewById(R.id.toolbar);
        //
        setSupportActionBar(toolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open_nav, R.string.close_nav);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        if(savedInstanceState==null){
            getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, new HomeFragment()).commit();
            nv.setCheckedItem(R.id.nav_home);
            nv.setCheckedItem(R.id.nav_inicio);
        }

        replaceFragment(new HomeFragment());

        bnv.setBackground(null);
        //nv.setBackground(null);

        //cambio
        bnv.setOnItemSelectedListener(item -> {
            Log.i("Item_nombre: ", String.valueOf(item.getItemId()));
            if(item.getItemId()==R.id.nav_home){
                replaceFragment(new HomeFragment());
            }else if(item.getItemId()==R.id.nav_logout){
                //replaceFragment(new CerrarSesionFragment());
                cerrarSesionEvento();
            } else if (item.getItemId()==R.id.nav_settings) {
                replaceFragment(new ConfiguracionFragment());
            }
            return true;
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showBottomDialog();
            }
        });
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        //Toast.makeText(MainActivity.this,item.getItemId(),Toast.LENGTH_SHORT).show();

        if (item.getItemId() == R.id.nav_inicio) {
            replaceFragment(new HomeFragment());
        } else if (item.getItemId() == R.id.nav_configuracion) {
            replaceFragment(new ConfiguracionFragment());
        } else if (item.getItemId() ==R.id.nav_compartir) {
            Toast.makeText(MainActivity.this,"Compartir fue clickeado",Toast.LENGTH_SHORT).show();
        } else if (item.getItemId() ==R.id.nav_nosotros) {
            Toast.makeText(MainActivity.this,"Sobre nosotros fue clickeado",Toast.LENGTH_SHORT).show();
        }else if(item.getItemId() ==R.id.nav_cerrarsesion){
            //replaceFragment(new CerrarSesionFragment());
            cerrarSesionEvento();
        }

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
    private  void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, fragment);
        fragmentTransaction.commit();
    }

    @Override
    public void onBackPressed(){
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }else{
            super.onBackPressed();
        }
    }

    private void showBottomDialog() {

        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.bottomsheet);

        LinearLayout crearLayout = dialog.findViewById(R.id.crearEvento);
        ImageView cancelButton = dialog.findViewById(R.id.cancelButton);

        crearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                Toast.makeText(MainActivity.this,"Crear evento fue clickeado",Toast.LENGTH_SHORT).show();
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        dialog.show();
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        dialog.getWindow().setGravity(Gravity.BOTTOM);

    }

    private Dialog cerrarSesionDialog(){
        AlertDialog.Builder alerta = new AlertDialog.Builder(MainActivity.this);
        alerta.setTitle("Cerrar Sesion");
        alerta.setMessage("Esta seguro de querer cerrar sesion en este dispositivo?")
                .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        cerrarSesion();
                        startActivity(new Intent(MainActivity.this, LoginActivity.class));
                    }
                })
                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //Toast.makeText(MainActivity.this, "se apreto que se va a cancelar", Toast.LENGTH_SHORT).show();
                    }
                });
        return alerta.create();
    }

    private void cerrarSesionEvento(){
        Dialog dialogCerrarSesion=cerrarSesionDialog();
        dialogCerrarSesion.show();
    }

    private void cerrarSesion(){
        preferencias = getSharedPreferences("sesion", Context.MODE_PRIVATE);
        boolean estado=false;
        SharedPreferences.Editor editor = preferencias.edit();
        editor.putBoolean("estado_usuario", estado);
        editor.putString("sesion_mail", null);
        editor.putString("sesion_pwd", null);
        editor.commit();
    }
}
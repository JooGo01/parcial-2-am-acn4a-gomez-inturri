package com.example.for2pay;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AgregarFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AgregarFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    FirebaseFirestore mFireStore;

    FirebaseAuth mAuth;

    Button agregar_evento;
    EditText et_nombre_servicio;
    EditText et_descripcion;
    EditText et_precio;
    EditText et_dia;

    public AgregarFragment() {
        // Required empty public constructor
    }

    public static AgregarFragment newInstance(String param1, String param2) {
        AgregarFragment fragment = new AgregarFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mFireStore=FirebaseFirestore.getInstance();
        mAuth=FirebaseAuth.getInstance();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_agregar, container, false);
        agregar_evento=(Button) view.findViewById(R.id.btn_agg_evento);
        et_nombre_servicio=view.findViewById(R.id.txt_nombre_servicio);
        et_descripcion=view.findViewById(R.id.txt_descripcion);
        et_precio=view.findViewById(R.id.txt_precio);
        et_dia=view.findViewById(R.id.txt_dia);
        TextView tv;
        tv=view.findViewById(R.id.textView10);

        et_dia.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // No se necesita implementar
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // No se necesita implementar
            }

            @Override
            public void afterTextChanged(Editable s) {
                // Verifica si el texto ingresado está dentro del rango deseado
                if (!s.toString().isEmpty()) {
                    int value = Integer.parseInt(s.toString());
                    if (value < 1 || value > 31) {
                        // Si el valor está fuera del rango, muestra un mensaje de error
                        et_dia.setError("Dia de Pago (Aproximado) - ERROR - El valor debe estar entre 1 y 31");
                        agregar_evento.setEnabled(false);
                    } else {
                        // El valor está dentro del rango, borra el mensaje de error si existe
                        et_dia.setError(null);
                        agregar_evento.setEnabled(true);
                    }
                }
            }
        });
        agregar_evento.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                String nombre=et_nombre_servicio.getText().toString().trim();
                String descripcion=et_descripcion.getText().toString().trim();
                Double precio = Double.parseDouble(et_precio.getText().toString().trim());
                Integer dia = Integer.parseInt(et_dia.getText().toString().trim());
                fncRegistrarEvento(nombre,descripcion,precio,dia);
            }
        });
        // Inflate the layout for this fragment
        return view;
    }

    private void fncRegistrarEvento(String p_nombre, String p_descripcion, Double p_precio, Integer p_dia_pago){
        Map<String, Object> mapEvento = new HashMap<>();
        String user_id=obtenerUIDUsuarioActual();
        mapEvento.put("id_usuario",user_id);
        mapEvento.put("nombre", p_nombre);
        mapEvento.put("descripcion", p_descripcion);
        mapEvento.put("precio", p_precio);
        mapEvento.put("dia_pago", p_dia_pago);
        mapEvento.put("estado_baja", false);
        mFireStore.collection("evento").add(mapEvento).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                Toast.makeText(getActivity(), "Evento creado con exito", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getActivity(), "Error al crear el evento", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private String obtenerUIDUsuarioActual() {
        String id = mAuth.getCurrentUser().getUid();
        if(id.isEmpty()){
            id="";
        }
        return id;
    }
}
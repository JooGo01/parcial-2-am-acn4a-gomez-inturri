package com.example.for2pay.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;
import androidx.annotation.NonNull;

import com.example.for2pay.R;
import com.example.for2pay.model.Event;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

public class EventAdapter extends FirestoreRecyclerAdapter<Event, EventAdapter.ViewHolder> {
    /*
    * Create a new RecyclerView adapter that listens to a Firestone  Query. See {@link}
    *FirestoreRecyclerOptions} for configuration  options .
    *
    *@param options
    *
    */

    public EventAdapter (@NonNull FirestoreRecyclerOptions<Event> options) {

        super(options);
    }


    @Override
    protected void onBindViewHolder(@NonNull ViewHolder viewHolder, int i, @NonNull Event event ) {
        viewHolder.nombre.setText(event.getNombre());
        viewHolder.descripcion.setText(event.getDescripcion());
        viewHolder.dia_pago.setText(event.getDia_pago().toString());
        viewHolder.precio.setText(event.getPrecio().toString());
        viewHolder.btnVisualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nombreDescripcion = "Se visualiza el evento: " + event.getNombre() + " - " + event.getDescripcion();
                Toast.makeText(v.getContext(),nombreDescripcion, Toast.LENGTH_SHORT).show();
            }
        });

        viewHolder.btnEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nombreDescripcion = "Se elimina el evento: " + event.getNombre() + " - " + event.getDescripcion();
                Toast.makeText(v.getContext(),nombreDescripcion, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder (@NonNull ViewGroup parent, int viewType){
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.new_event_single, parent, false);
        return new ViewHolder(v);
    }


    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView nombre, descripcion, dia_pago, precio;
        Button btnEliminar, btnVisualizar;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nombre = itemView.findViewById(R.id.nombre);
            descripcion = itemView.findViewById(R.id.descripcion);
            dia_pago = itemView.findViewById(R.id.dia);
            precio = itemView.findViewById(R.id.precio);
            btnEliminar=itemView.findViewById(R.id.btn_eliminar);
            btnVisualizar=itemView.findViewById(R.id.btn_visualizar);
        }
    }

}

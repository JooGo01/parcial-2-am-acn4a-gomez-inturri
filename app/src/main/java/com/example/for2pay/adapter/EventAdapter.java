package com.example.for2pay.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;
import androidx.annotation.NonNull;

import com.example.for2pay.R;
import com.firebase.ui.firestone.FirestoneRecyclerOptions;
import com.example.for2pay.model.Event;
import com.firebase.ui.firestone.FirestoneRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;

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
        viewHolder.dia.setText(event.getDia());
        viewHolder.precio.setText(event.getPrecio());
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder (@NonNull ViewGroup parent, int viewType){
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.new_event_single, parent, false);
        return new ViewHolder(v);
    }


    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView nombre, descripcion, dia, precio;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nombre = itemView.findViewById(R.id.nombre);
            descripcion = itemView.findViewById(R.id.descripcion);
            dia = itemView.findViewById(R.id.dia);
            precio = itemView.findViewById(R.id.precio);

        }
    }

}

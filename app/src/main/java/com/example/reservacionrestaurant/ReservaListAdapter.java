package com.example.reservacionrestaurant;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.reservacionrestaurant.fragments.Reserva;

import java.util.List;

public class ReservaListAdapter extends ArrayAdapter<Reserva> {
    private final Context context;
    private final List<Reserva> reservasList;
    private ReservaListAdapterListener listener;


    public ReservaListAdapter(Context context, List<Reserva> reservasList) {
        super(context, R.layout.reserva_list_item, reservasList);
        this.context = context;
        this.reservasList = reservasList;
    }

    public interface ReservaListAdapterListener {
        void onEliminarReservaClick(Reserva reserva);
    }

    public void setListener(ReservaListAdapterListener listener) {
        this.listener = listener;
    }

    public interface OnReservaDeleteListener {
        void onReservaDelete(Reserva reserva);
    }

    private OnReservaDeleteListener deleteListener;

    public void setOnReservaDeleteListener(OnReservaDeleteListener listener) {
        this.deleteListener = listener;
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.reserva_list_item, parent, false);


        TextView nombreRestauranteTextView = rowView.findViewById(R.id.nombreRestauranteTextView);
        TextView nombreClienteTextView = rowView.findViewById(R.id.nombreClienteTextView);
        TextView mesasSeleccionadasTextView = rowView.findViewById(R.id.mesasSeleccionadasTextView);
        TextView fechaReservaTextView = rowView.findViewById(R.id.fechaReservaTextView);
        TextView horaReservaTextView = rowView.findViewById(R.id.horaReservaTextView);
        TextView fechadeReservaTextView = rowView.findViewById(R.id.fechadeReservaTextView);
        TextView horadeReservaTextView = rowView.findViewById(R.id.horadeReservaTextView);
        Button eliminarReserva = rowView.findViewById(R.id.btnEliminarReserva);

        Reserva reserva = reservasList.get(position);

        nombreRestauranteTextView.setText("Nombre del Restaurante: " + reserva.getNombreRestaurante());
        nombreClienteTextView.setText("Nombre del Cliente: " + reserva.getNombreCliente());
        mesasSeleccionadasTextView.setText("Mesas Seleccionadas: " + reserva.getMesasSeleccionadas());
        fechaReservaTextView.setText("Fecha cuando se hizo la Reserva: " + reserva.getFechaReserva());
        horaReservaTextView.setText("Hora cuando se hizo la Reserva: " + reserva.getHoraReserva());
        fechadeReservaTextView.setText("Fecha Reservada: " + reserva.getFechadeReserva());
        horadeReservaTextView.setText("Hora Reservada: " + reserva.getHoradeReserva());

        eliminarReserva.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                eliminarReserva(position);
            }
        });


        return rowView;


    }

    private void eliminarReserva(int position) {
        if (deleteListener != null) {
            deleteListener.onReservaDelete(getItem(position));
        }
    }
}

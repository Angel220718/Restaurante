package com.example.reservacionrestaurant;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
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

    // Interfaz para manejar eventos del adaptador
    public interface ReservaListAdapterListener {
        void onEliminarReservaClick(Reserva reserva);
    }

    public void setListener(ReservaListAdapterListener listener) {
        this.listener = listener;
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.reserva_list_item, parent, false);


        // Obtener referencias a los elementos de la interfaz de usuario en reserva_list_item
        TextView nombreRestauranteTextView = rowView.findViewById(R.id.nombreRestauranteTextView);
        TextView nombreClienteTextView = rowView.findViewById(R.id.nombreClienteTextView);
        TextView mesasSeleccionadasTextView = rowView.findViewById(R.id.mesasSeleccionadasTextView);
        TextView fechaReservaTextView = rowView.findViewById(R.id.fechaReservaTextView);
        TextView horaReservaTextView = rowView.findViewById(R.id.horaReservaTextView);

        // Obtener el objeto Reserva para la posición actual
        Reserva reserva = reservasList.get(position);

        // Configurar los elementos de la interfaz de usuario con los datos de la reserva
        nombreRestauranteTextView.setText("Nombre del Restaurante: " + reserva.getNombreRestaurante());
        nombreClienteTextView.setText("Nombre del Cliente: " + reserva.getNombreCliente());
        mesasSeleccionadasTextView.setText("Mesas Seleccionadas: " + reserva.getMesasSeleccionadas());
        fechaReservaTextView.setText("Fecha de Reserva: " + reserva.getFechaReserva());
        horaReservaTextView.setText("Hora de Reserva: " + reserva.getHoraReserva());


        return rowView;


    }
}

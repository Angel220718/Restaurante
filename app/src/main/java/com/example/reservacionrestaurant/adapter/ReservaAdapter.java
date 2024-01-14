package com.example.reservacionrestaurant.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.reservacionrestaurant.R;
import com.example.reservacionrestaurant.fragments.Reserva;
import com.example.reservacionrestaurant.fragments.ReservaViewHolder;

import java.util.List;

public class ReservaAdapter extends RecyclerView.Adapter<ReservaViewHolder> {

    private List<Reserva> reservas;


    public ReservaAdapter(List<Reserva> reservas) {
        this.reservas = reservas;
        Log.d("ReservaAdapter", "Cantidad de reservas: " + reservas.size());
    }

    @NonNull
    @Override
    public ReservaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_reserva, parent, false);
        return new ReservaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReservaViewHolder holder, int position) {
        Log.d("ReservaAdapter", "onBindViewHolder llamado para la posición: " + position);
        Reserva reserva = reservas.get(position);

        // Asignar los datos de la reserva a las vistas correspondientes
        holder.NombreRestaurante.setText("Nombre del Restaurante: " + reserva.getNombreRestaurante());
        holder.NombreCliente.setText("Nombre del Cliente: " + reserva.getNombreCliente());
        holder.FechaReserva.setText("Fecha de Reserva: " + reserva.getFechaReserva());
        holder.MesasReservadas.setText("Mesas Reservadas: " + obtenerMesasFormateadas(reserva.getMesasSeleccionadas()));
        holder.HoraReserva.setText("Hora de Reserva: " + reserva.getHoraReserva());

        // Puedes agregar más asignaciones según la información de tu reserva
    }

    @Override
    public int getItemCount() {
        return reservas.size();
    }

    // Método para formatear las mesas seleccionadas como una cadena
    private String obtenerMesasFormateadas(List<Integer> mesas) {
        StringBuilder mesasFormateadas = new StringBuilder();
        for (int mesa : mesas) {
            mesasFormateadas.append(mesa).append(", ");
        }
        // Eliminar la coma extra al final, si hay mesas
        if (mesasFormateadas.length() > 0) {
            mesasFormateadas.deleteCharAt(mesasFormateadas.length() - 2);
        }
        return mesasFormateadas.toString();
    }
}
